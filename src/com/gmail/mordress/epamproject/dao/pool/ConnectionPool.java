package com.gmail.mordress.epamproject.dao.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import com.gmail.mordress.epamproject.exceptions.PersistentException;
import org.apache.log4j.Logger;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
/**
 * Contains realisation connection pool to database, for optimize perfomance.
 * @author Alexey Kardychko
 * @version 1.0
 */
public final class ConnectionPool {

    private static ConnectionPool instance = new ConnectionPool();

    private static Logger logger = Logger.getLogger(ConnectionPool.class);

    /** JDBC driver for database*/
    private String driverClass;

    private String dbURL;

    private String dbUser;

    private String dbPassword;

    private int maxPoolSize;

    private int minPoolSize;

    private int timeout;

    private BlockingQueue<PooledConnection> freeConnections = new LinkedBlockingQueue<>();

    private Set<PooledConnection> usedConnections = new ConcurrentSkipListSet<>();

    private ConnectionPool() {
        try {
            init();
        } catch (PersistentException e) {
            logger.fatal("Can not init connection pool");
            e.printStackTrace();
        }
    };

    public static ConnectionPool getInstance() {
        return instance;
    }

    public synchronized void init() throws PersistentException {
        try {
            destroy();
            this.driverClass = "com.mysql.jdbc.Driver";
            this.dbURL = "jdbc:mysql://localhost:3306/racing?useUnicode=true&characterEncoding=UTF-8";
            this.dbUser = "dev";
            this.dbPassword = "9958108";
            this.maxPoolSize = 100;
            this.minPoolSize = 50;
            this.timeout = 0;
            Class.forName(driverClass);
            for(int counter = 0; counter < minPoolSize; counter++) {
                freeConnections.put(createConnection());
            }
        } catch(ClassNotFoundException | SQLException | InterruptedException e) {
            logger.fatal("Can't initialize db connection pool.", e);
            throw new PersistentException(e.getMessage(), e.getCause());
        }
    }

    public synchronized Connection getConnection() throws PersistentException {
        PooledConnection connection = null;
        while(connection == null) {
            try {
                if(!freeConnections.isEmpty()) {
                    connection = freeConnections.take();
                    if(!connection.isValid(timeout)) {
                        try {
                            connection.getConnection().close();
                        } catch(SQLException e) {}
                        connection = null;
                    }
                } else if(usedConnections.size() < maxPoolSize) {
                    connection = createConnection();
                } else {
                    logger.error("The limit of number of database connections is exceeded" + usedConnections.size() + " usedConnection.Size()");
                    throw new PersistentException();
                }
            } catch(InterruptedException | SQLException e) {
                logger.error("Can't connect to a database", e);
                throw new PersistentException(e.getMessage(), e.getCause());
            }
        }
        usedConnections.add(connection);
        logger.debug(String.format("Connection was received from pool." +
                " Current pool size: %2d used connection(s); %2d free connection(s)",
                usedConnections.size(), freeConnections.size()));
        return connection;

    }

    public synchronized void destroy() {
        usedConnections.addAll(freeConnections);
        freeConnections.clear();
        for(PooledConnection connection : usedConnections) {
            try {
                connection.getConnection().close();
            } catch(SQLException e) {}
        }
        usedConnections.clear();
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
    }

    synchronized void freeConnection(PooledConnection connection) {
        try {
            if(connection.isValid(timeout)) {
                connection.clearWarnings();
                connection.setAutoCommit(true);
                usedConnections.remove(connection);
                freeConnections.put(connection);
                logger.debug(String.format("Connection was returned into pool." +
                        " Current pool size: %2d used connection(s); %2d free connection(s)",
                        usedConnections.size(), freeConnections.size()));
            }
        } catch(SQLException | InterruptedException e1) {
            logger.warn("It is impossible to return database connection into pool", e1);
            try {
                connection.getConnection().close();
            } catch(SQLException e2) {}
        }
    }

    private PooledConnection createConnection() throws SQLException {
        return new PooledConnection(DriverManager.getConnection(dbURL, dbUser, dbPassword));
    }
}

