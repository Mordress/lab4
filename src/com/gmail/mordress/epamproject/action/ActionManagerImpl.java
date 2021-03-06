package com.gmail.mordress.epamproject.action;

import com.gmail.mordress.epamproject.exceptions.PersistentException;
import com.gmail.mordress.epamproject.services.ifaces.ServiceFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Provides processing actions and transfer attributes between servlets.
 * @author Alexey Kardychko
 * @version 1.0
 */
public class ActionManagerImpl implements ActionManager {

    private ServiceFactory factory;

    public ActionManagerImpl(ServiceFactory factory) {
        this.factory = factory;
    }

    @Override
    public Action.Forward execute(Action action, HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        action.setFactory(factory);
        HttpSession session = request.getSession(false);
        if (session != null) {
            Map<String, Object> attributes = (Map<String, Object>) session.getAttribute("redirectedData");
            if (attributes != null) {
                for (String key : attributes.keySet()) {
                    request.setAttribute(key, attributes.get(key));
                }
                session.removeAttribute("redirectedData");
            }
        }
        Action.Forward forward = action.exec(request, response);
        if (session != null && forward != null && !forward.getAttributes().isEmpty()) {
            session.setAttribute("redirectedData", forward.getAttributes());
        }
        return forward;
    }

    @Override
    public void close() {
        factory.close();
    }
}
