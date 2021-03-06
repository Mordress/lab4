package com.gmail.mordress.epamproject.controller;

import com.gmail.mordress.epamproject.action.*;
import com.gmail.mordress.epamproject.action.administrator.*;
import com.gmail.mordress.epamproject.action.bookmaker.BetFixListAction;
import com.gmail.mordress.epamproject.action.bookmaker.BetsFixedAction;
import com.gmail.mordress.epamproject.action.user.BetDeleteAction;
import com.gmail.mordress.epamproject.action.user.BetsCreateAction;
import com.gmail.mordress.epamproject.action.user.BetsListAction;
import com.gmail.mordress.epamproject.action.user.BetsNewSaveAction;
import org.apache.log4j.Logger;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Processes requested page and forwards to appropriate servlet.
 * @author Alexey Kardychko
 * @version 1.0
 */
public class ActionFromUriFilter implements Filter {

    private static Logger logger = Logger.getLogger(ActionFromUriFilter.class);

    /** Contains pairs URI-action.class */
    private static Map<String, Class<? extends Action>> actions = new ConcurrentHashMap<>();

    static {
        actions.put("/", MainAction.class);
        actions.put("/index", MainAction.class);

        actions.put("/login", LoginAction.class);
        actions.put("/logout", LogoutAction.class);
        actions.put("/registration", RegistrationAction.class);
        actions.put("/regsave", RegistrationSaveAction.class);
        actions.put("/profile/edit", ProfileEditAction.class);
        actions.put("/profile/save", ProfileSaveAction.class);
        actions.put("/horses", HorseListAction.class);

        actions.put("/races/list", RaceListAction.class);
        actions.put("/races/edit", RaceEditAction.class);
        actions.put("/races/save", RaceSaveAction.class);
        actions.put("/races/delete", RaceDeleteAction.class);

        actions.put("/horseraces/edit", HorseRaceEditAction.class);
        actions.put("/horseraces/resultedit", HorseRaceResultEditAction.class);
        actions.put("/horseraces/resultsave", HorseRaceResultSaveAction.class);

        actions.put("/bets/list", BetsListAction.class);
        actions.put("/bets/create", BetsCreateAction.class);
        actions.put("/bets/newsave", BetsNewSaveAction.class);
        actions.put("/bets/fix", BetFixListAction.class);
        actions.put("/bets/fixsave", BetsFixedAction.class);
        actions.put("/bets/delete", BetDeleteAction.class);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    /** Processes requested page and forwards to appropriate servlet.
     * @param request servlet request.
     * @param response servlet response.
     * @param chain servlet filter chain
     * @throws IOException - if process requested URI throws this exception.
     * @throws ServletException - if servlet can not do this operation correctly. */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest)request;
            String contextPath = httpRequest.getContextPath();
            String uri = httpRequest.getRequestURI();
            logger.debug(String.format("Starting of processing of request for URI \"%s\"", uri));
            int beginAction = contextPath.length();
            int endAction = uri.lastIndexOf('.');
            String actionName;
            if(endAction >= 0) {
                actionName = uri.substring(beginAction, endAction);
            } else {
                actionName = uri.substring(beginAction);
            }
            Class<? extends Action> actionClass = actions.get(actionName);
            try {
                Action action = actionClass.newInstance();
                action.setName(actionName);
                httpRequest.setAttribute("action", action);
                chain.doFilter(request, response);
            } catch (InstantiationException | IllegalAccessException | NullPointerException e) {
                logger.error("It is impossible to create action handler object", e);
                httpRequest.setAttribute("error", String.format("Запрошенный адрес %s не может быть обработан сервером", uri));
                httpRequest.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            }
        } else {
            logger.error("It is impossible to use HTTP filter");
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {}
}
