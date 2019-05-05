package com.webbank.command;


import com.webbank.model.Account;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;


public class BlockCommand extends Command{
    @Override
    public void execute() throws IOException {
        int cardNumber = Integer.valueOf(req.getParameter("card"));
        Account account = getAccount(cardNumber);
        if(account == null){
            return;
        }
        account.setIsBlocked(true);
        businessService.blockAccount(account, true);

        refreshBlocked();
       // req.getSession().setAttribute("warning", "Warning: blocked");
        model.addAttribute("warning", "Warning: blocked");
        model.addAttribute("block", true);
    }
}
