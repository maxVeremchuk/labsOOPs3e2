package com.webbank.command;

import com.webbank.model.Account;
import com.webbank.model.Card;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;


public class UnblockCommand extends Command{
    @Override
    public void execute() {
        int cardNumber = Integer.valueOf(req.getParameter("card"));
        Account account = businessService.getAccount(businessService.getCard(cardNumber));
        if(account == null){
            return;
        }
        businessService.blockAccount(account, false);
        List<Card> blockedCards = (List<Card>)req.getSession().getAttribute("blockedCards");
        for(int i = 0; i < blockedCards.size(); i++){
            if(blockedCards.get(i).getCardNumber() == cardNumber){
                blockedCards.remove(i);
                break;
            }
        }
        //refreshBlocked();
    }
}
