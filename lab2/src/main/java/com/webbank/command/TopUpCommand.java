package com.webbank.command;


import com.webbank.model.Account;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TopUpCommand  extends Command {
    @Override
    public void execute() throws IOException {
        int money = Integer.valueOf(req.getParameter("money"));
        int cardNumber = Integer.valueOf(req.getParameter("card"));

        Account account = getAccount(cardNumber);

        if(account == null){
            return;
        }
        if(account.getIsBlocked()){
            model.addAttribute("warning", "Warning: Account is blocked");
        }
        else {
            account.setMoneyAmount(account.getMoneyAmount() + money);
            businessService.updateAccount(account);
            model.addAttribute("topup", "+" + money + "$ to account.");
        }
        //resp.sendRedirect("/test/client?command=Payments");
    }
}
