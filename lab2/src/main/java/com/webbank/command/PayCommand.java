package com.webbank.command;

import com.webbank.command.Command;
import com.webbank.model.Account;
import com.webbank.model.Payment;
import com.webbank.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PayCommand  extends Command {
    @Override
    public void execute() throws IOException {
        int money = Integer.valueOf(req.getParameter("money"));
        String info = req.getParameter("info");
        int cardNumber = Integer.valueOf(req.getParameter("card"));
        Account account = getAccount(cardNumber);

        if(account == null){
            return;
        }

        int accountMoney = account.getMoneyAmount();
        if(account.getIsBlocked()){
            model.addAttribute("warning", "Warning: Account is blocked");
        }
        else if(accountMoney < money){
            model.addAttribute("warning", "Warning: not enough money");
        }
        else {
            account.setMoneyAmount(accountMoney - money);
            businessService.updateAccount(account);
            //HttpSession session = req.getSession();
            List<String> payments = (List<String>) req.getSession().getAttribute("payments");
            if(payments == null){
                payments = new ArrayList<>();
            }
            Payment payment = new Payment();
            payment.setMoney(money);
            payment.setInfo(info);
            payment.setCard(Integer.valueOf(cardNumber));
            User user = (User)req.getSession().getAttribute("User");
            payment.setClient(user.getId());
            //resp.getWriter().write(payment.getClient() + " " + payment.getMoney() + " " + payment.getInfo() + " " + payment.getCard());
            int paymentId = businessService.addPayment(payment);
            payments.add("ID: " + paymentId + ". Info: " + info +
                    ". Spended money: " + money + ". Card: " + cardNumber);
            req.getSession().setAttribute("payments", payments);
            model.addAttribute("testPayment", info + " " + money + " " + (accountMoney - money)
                    + " " +  cardNumber);//for test
        }
        //resp.sendRedirect("/test/client?command=Payments");
    }
}
