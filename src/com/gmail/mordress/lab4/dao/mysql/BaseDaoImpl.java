package com.gmail.mordress.lab4.dao.mysql;

import java.sql.Connection;

public abstract class BaseDaoImpl {

    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
