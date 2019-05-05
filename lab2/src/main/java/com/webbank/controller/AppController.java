package com.webbank.controller;

import java.io.IOException;
import java.util.*;


import com.webbank.command.Command;
import com.webbank.command.factory.CommandFactory;
import com.webbank.command.factory.CommandFactoryImpl;
import com.webbank.model.*;
import com.webbank.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@RequestMapping("/")
@ComponentScan("com.webbank")
public class AppController {
   @Autowired
   UserService userService;
   @Autowired
   UserProfileServiceImpl userProfileService;
   @Autowired
   MessageSource messageSource;
   @Autowired
   SecurityService securityService;
   @Autowired
   PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
   @Autowired
   AuthenticationTrustResolver authenticationTrustResolver;
   @Autowired
   BusinessService businessService;


   @GetMapping(value = { "/", "/userPage" })
   public String userPage(HttpServletRequest request, ModelMap model) {

       //model.addAttribute("warning", "");
       //model.addAttribute("topup", "");

       String username = getPrincipal();
       User user = userService.findByUsername(username);
       for(UserProfile profile : user.getUserProfiles()){
           if(profile.getType().equals("ADMIN")){
               return "redirect:/adminPage";
           }
       }

       request.getSession().setAttribute("loggedinuser", username);
       request.getSession().setAttribute("User", user);
       List<Card> cards = businessService.getUsersCards(user);
       List<Account> accounts = businessService.getAccounts(cards);
       Map<Card, Account> cardAccountMap = new HashMap<>();
       for (int i = 0; i < cards.size(); i++) {
           cardAccountMap.put(cards.get(i), accounts.get(i));
       }
       request.getSession().setAttribute("cardAccountMap", cardAccountMap);


       List<String> payments = new ArrayList<>();
       for(Payment payment : businessService.getPayments(user)) {
           payments.add("ID: " + payment.getId() + ". Info: " + payment.getInfo() +
                   ". Spended money: " + payment.getMoney() + ". Card: " + payment.getCard());
       }
       request.getSession().setAttribute("payments", payments);

       return "userPage";
   }

    @GetMapping(value = {"/adminPage" })
    public String adminPage(HttpServletRequest request, ModelMap model) {
        String username = getPrincipal();
        request.getSession().setAttribute("loggedinuser", username);
        User user = userService.findByUsername(username);
        Bank bank = businessService.getBank(user);
        List<Account> accounts = bank.getAccounts();
        List<Integer> accountsId = new ArrayList<>();
        for(Account account : accounts){
            accountsId.add(account.getId());
        }
        request.getSession().setAttribute("blockedCards", businessService.getBlockedCards(accountsId));
        request.getSession().setAttribute("bankName", businessService.getBank(user).getName());
        return "adminPage";
    }
    @GetMapping(value = { "/newuser" })
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
       // model.addAttribute("edit", false);
       // model.addAttribute("loggedinuser", getPrincipal());
        return "registration";
    }

    @PostMapping(value = { "/newuser" })
    public String saveUser(@Valid User user, BindingResult result,
                           ModelMap model) {

        if (result.hasErrors()) {
            return "registration";
        }

        if(!userService.isUserNameUnique(user.getUsername())){
            FieldError duplicateError =new FieldError("user","id", messageSource.getMessage("non.unique.username", new String[]{user.getUsername()}, Locale.getDefault()));
            result.addError(duplicateError);
            return "registration";
        }
        UserProfile userProfile = userProfileService.findByType("USER");
        Set<UserProfile> roles = new HashSet<>();
        roles.add(userProfile);
        user.setUserProfiles(roles);
        String password = user.getPassword();
        userService.saveUser(user);
        securityService.autologin(user.getUsername(), password);
        return "redirect:/userPage";
    }

    @GetMapping(value = "/Access_Denied")
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        return "accessDenied";
    }
    @GetMapping(value = "/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        HttpServletRequest request, ModelAndView model) {
       // ModelAndView model = new ModelAndView();
         if (isCurrentAuthenticationAnonymous()) {
            /*if (error != null) {
                model.addObject("error", "Invalid username and password!");
                String targetUrl = getRememberMeTargetUrlFromSession(request);
                System.out.println(targetUrl);
                if(StringUtils.hasText(targetUrl)){
                    model.addObject("targetUrl", targetUrl);
                    model.addObject("loginUpdate", true);
                }
            }
            if (logout != null) {
                model.addObject("msg", "You've been logged out successfully.");
            }*/
            return "login";
        } else {
             String username = getPrincipal();
             User user = userService.findByUsername(username);
             for(UserProfile profile : user.getUserProfiles()){
                 if(profile.getType().equals("ADMIN")){
                     return "redirect:/adminPage";
                 }
             }
            return "redirect:/userPage";
        }

    }
    @GetMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            //new SecurityContextLogoutHandler().logout(request, response, auth);
            //persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }
    //@PostMapping(value="/")
    public String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @PostMapping(value="/operation")
    private String makeOperation(HttpServletRequest request, ModelMap model) {
        String commandName = request.getParameter("command");
        //model.addAttribute("warning", "");
        //model.addAttribute("topup", "");
        if (commandName == null) {
            model.addAttribute("warning", "wrong command");
        } else {
            CommandFactory factory = CommandFactoryImpl.getFactory();
            Command command = factory.getCommand(commandName, request, model, businessService);
            try {
                command.execute();
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
        String username = getPrincipal();
        User user = userService.findByUsername(username);
        for(UserProfile profile : user.getUserProfiles()){
            if(profile.getType().equals("ADMIN")){
                return "redirect:/adminPage";
            }
        }
        return "userPage";
    }
   /* private boolean isAdmin(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)((UserDetails)principal).getAuthorities();

            for(SimpleGrantedAuthority item : authorities){
                if(item.getAuthority().equals("ROLE_ADMIN")){
                    return true;
                }
            }
        }
        return false;
    }*/
   private String getRememberMeTargetUrlFromSession(HttpServletRequest request){
       String targetUrl = "";
       HttpSession session = request.getSession(false);
       if(session!=null){
           targetUrl = session.getAttribute("targetUrl")==null?""
                   :session.getAttribute("targetUrl").toString();
       }
       return targetUrl;
   }
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }
}