package com.webbank.command;

import com.webbank.dao.AccountDao;
import com.webbank.model.Account;
import com.webbank.model.Card;
import com.webbank.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class Command {
    protected ModelMap model;
    protected HttpServletRequest req;
    protected BusinessService businessService;

    public void setModel(ModelMap model) {
        this.model = model;
    }
    public void setReq(HttpServletRequest req){ this.req = req; }
    public void setBusinessService(BusinessService businessService){ this.businessService = businessService; }

    public abstract void execute() throws ServletException,IOException;

    public void refreshBlocked(){
        Map<Card, Account> cardAccountMap =  (Map<Card, Account>)req.getSession().getAttribute("cardAccountMap");
        List<Card> cards = new ArrayList<>(cardAccountMap.keySet());
        List<Account> accounts = new ArrayList<>(cardAccountMap.values());
        List<Account> accountsBd = businessService.getAccounts(cards);
        boolean isSomeBlocked = false;
        for(int i = 0; i < accounts.size(); i++){
            for(int j = 0; j < accountsBd.size(); j++){
                Account old = accounts.get(i);
                Account fresh = accountsBd.get(i);
                if(old.getId() == fresh.getId()){
                    if(fresh.getIsBlocked()){
                        isSomeBlocked = true;
                    }
                    old.setIsBlocked(fresh.getIsBlocked());
                    break;
                }
            }
        }
        if(!isSomeBlocked){
            model.addAttribute("block", false);
        }
    }

    public Account getAccount(int cardNumber) throws IOException {
        if(cardNumber == 0){
            model.addAttribute("warning", "Warning: No card. Contact to support");
            return null;
        }
        Map<Card, Account> cardAccountMap =  (Map<Card, Account>)req.getSession().getAttribute("cardAccountMap");
        List<Card> cards = new ArrayList<>(cardAccountMap.keySet());

        Card card = null;
        for(Card item : cards){
            if(item.getCardNumber( )== cardNumber){
                card = item;
                break;
            }
        }
        if(card == null){
            return null;
        }
        Account account = cardAccountMap.get(card);
        if(account == null){
            req.getSession().setAttribute("warning",
                        "Warning: no account with " + cardNumber + ". Contact to support");
            model.addAttribute("warning", "Warning: no account with " + cardNumber + ". Contact to support");

            //resp.sendRedirect("/test/client?command=Payments");
            return null;
        }
        return account;
    }
}
