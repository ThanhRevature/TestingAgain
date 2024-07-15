package com.revature;

import com.revature.controller.UserController;
import com.revature.repository.BankAccountDao;
import com.revature.repository.SqLiteBankAccountDao;
import com.revature.repository.SqLiteUserDao;

import com.revature.repository.UserDao;
import com.revature.service.BankAccountService;
import com.revature.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try(Scanner scanner = new Scanner(System.in)){

            UserDao userDao = new SqLiteUserDao();

            UserService userService = new UserService(userDao);

            BankAccountDao bankAccountDao = new SqLiteBankAccountDao();

            BankAccountService bankAccountService = new BankAccountService(bankAccountDao);

            UserController userController = new UserController(scanner, userService, bankAccountService);

            Map<String, String> controlMap = new HashMap<>();
            controlMap.put("Continue Loop", "true");
            while(Boolean.parseBoolean(controlMap.get("Continue Loop"))){
                userController.promptUserForService(controlMap);
                if(controlMap.containsKey("User")){

                    scanner.nextLine();

                }
            }

        }
    }
}