package com.gmail.mordress.epamproject.action.administrator;

import com.gmail.mordress.epamproject.action.Action;
import com.gmail.mordress.epamproject.exceptions.PersistentException;
import com.gmail.mordress.epamproject.services.ifaces.RaceService;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Process input data from administrator about race for delete this race.
 * @author Alexey Kardychko
 * @version 1.0
 */
public class RaceDeleteAction extends AdministratorAction {

    private static Logger logger = Logger.getLogger(RaceDeleteAction.class);

    /** Deleting race using service layer.
     * @param request incapsulating of HTTP request.
     * @param response incapsulating of HTTP response.
     * @return forward to main page for administrator.
     * @throws PersistentException - if service and dao layers produce this exception. */
    @Override
    public Action.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward("/races/list.html");
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            RaceService service = factory.getService(RaceService.class);
            service.delete(id);
            logger.info("Race with id= " + id + " was deleted by administrator");
            forward.getAttributes().put("message", "Забег успешно удален");
        } catch (PersistentException | NullPointerException| NumberFormatException e) {
            logger.info("Can not delete race with id= " + request.getParameter("id"));
            forward.getAttributes().put("message", "Невозможно удалить такой забег");
        }
        return forward;
    }
}
