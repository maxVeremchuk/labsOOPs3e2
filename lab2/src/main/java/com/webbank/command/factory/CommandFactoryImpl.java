package com.webbank.command.factory;

import com.webbank.command.*;
import com.webbank.service.BusinessService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CommandFactoryImpl implements CommandFactory {

    private CommandFactoryImpl(){};
    private static CommandFactoryImpl factory=new CommandFactoryImpl();
    public static CommandFactory getFactory(){
        return factory;
    }
    @Override
    public Command getCommand(String name, HttpServletRequest request, ModelMap model, BusinessService businessService) throws IllegalArgumentException {
        if (name==null){
            throw new IllegalArgumentException("Name cannot be NULL") ;
        }
        else{
            Command command=null;

            //----------------------------//
            if (name.equals("Block")) {
                command = new BlockCommand();
            }
            else if (name.equals("TopUp")){
                command = new TopUpCommand();
            }
            else if(name.equals("Pay")){
                command = new PayCommand();
            }
            else if (name.equals("Unblock")){
                command = new UnblockCommand();
            }

            //---------------------------//


            if (command==null){
                throw new IllegalArgumentException("Wrong command name");
            }

            command.setModel(model);
            command.setReq(request);
            command.setBusinessService(businessService);
            return command;
        }


    }

}
