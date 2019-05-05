package com.webbank.command.factory;



import com.webbank.command.Command;
import com.webbank.service.BusinessService;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandFactory {

    Command getCommand(String name, HttpServletRequest request, ModelMap model, BusinessService businessService) throws IllegalArgumentException;

}
