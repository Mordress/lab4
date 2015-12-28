package com.gmail.mordress.epamproject.action.administrator;

import com.gmail.mordress.epamproject.action.Action;
import com.gmail.mordress.epamproject.entities.HorseRace;
import com.gmail.mordress.epamproject.entities.Race;
import com.gmail.mordress.epamproject.exceptions.PersistentException;
import com.gmail.mordress.epamproject.services.ifaces.HorseRaceService;
import com.gmail.mordress.epamproject.services.ifaces.RaceService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HorseRaceEditAction extends AdministratorAction {

    private static Logger logger = Logger.getLogger(HorseRaceEditAction.class);

    @Override
    public Action.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        try {
            Integer id = (Integer) request.getAttribute("id");
            if (id == null) {
                id = Integer.parseInt(request.getParameter("id"));
            }
            RaceService service = factory.getService(RaceService.class);
            Race race = service.findById(id);
            if (race != null) {
                request.setAttribute("race", race);
            }
            HorseRaceService horseRaceService = factory.getService(HorseRaceService.class);
            List<HorseRace> horseRaces = horseRaceService.findByRace(race);
            if (horseRaces != null) {
                request.setAttribute("horseRaces", horseRaces);
            }
        } catch (NumberFormatException e) {}
        return null;
    }
}