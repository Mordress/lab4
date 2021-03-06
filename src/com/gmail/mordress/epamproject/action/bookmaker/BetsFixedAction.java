package com.gmail.mordress.epamproject.action.bookmaker;

import com.gmail.mordress.epamproject.action.Action;
import com.gmail.mordress.epamproject.entities.Bet;
import com.gmail.mordress.epamproject.entities.User;
import com.gmail.mordress.epamproject.exceptions.PersistentException;
import com.gmail.mordress.epamproject.services.ifaces.BetService;
import com.gmail.mordress.epamproject.services.ifaces.UserService;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * Processing user's bets.
 * @author Alexey Kardychko
 * @version 1.0
 */
public class BetsFixedAction extends BookmakerAction {

    private static Logger logger = Logger.getLogger(BetsFixedAction.class);

    /** Sets winned bets, and size of win cash. Transfer win cash to user' cash.
     * @param request incapsulating of HTTP request.
     * @param response incapsulating of HTTP response.
     * @return main page for bookmaker.
     * @throws PersistentException - if service and dao layers produce this exception. */
    @Override
    public Action.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        Forward forward = new Forward("/bets/fix.html");
        try {
            String isWinner = request.getParameter("iswinner");
            Integer betId = Integer.parseInt(request.getParameter("betId"));
            BigDecimal winAmount = new BigDecimal(request.getParameter("winamount"));

            BetService betService = factory.getService(BetService.class);
            Bet bet = betService.read(betId);
            bet.setWinAmount(winAmount);
            if (isWinner.equals("yes")) {
                bet.setIsWinner(true);
            } else if (isWinner.equals("no")) {
                bet.setIsWinner(false);
            }
            betService.save(bet);
            /* Transfer cash to user cashAmount */
            if (bet.getIsWinner()) {
                UserService userService = factory.getService(UserService.class);
                User user = userService.findById(bet.getUser().getId());
                BigDecimal newCashAmount = user.getCashAmount().add(winAmount);
                userService.updateUserCash(user.getId(), newCashAmount);
            }
            forward.getAttributes().put("message", "Ставка успешно обработана");
        } catch (NumberFormatException e) {
            forward.getAttributes().put("message", "Невозможно обработать ставку с такими параметрами");
            logger.error("Can not parse input data from bookmaker for update bet");
        }
        return forward;
    }
}
