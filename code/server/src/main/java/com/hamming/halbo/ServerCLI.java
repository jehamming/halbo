package com.hamming.halbo;

import com.hamming.halbo.datamodel.User;
import com.hamming.halbo.factories.UserFactory;

import java.util.Scanner;

public class ServerCLI {

    boolean runMenu = true;

    //                  Create Read Update Delete.
    //TODO Write methods to CRUD :
    // Users
    // Worlds
    // Continents
    // Cities
    public void run(){
        while(true){
            runMenu = true;
            System.out.println("----- Welcome to the Halbo Menu! -----");
            System.out.println("1. User Options");
            System.out.println("2. Worlds Options");
            System.out.println("3. Continents Options");
            System.out.println("4. Cities Options");
            int selection = getUserInput();
            menuSelection(selection);
        }
    }

    private void menuSelection(int selection) {
        switch (selection){
            case 1:
                showUserMenu();
                break;

            default:
                System.out.println("Please fill in a valid number from the menu selection");
                break;
        }
    }


    private void showUserMenu() {

        while(runMenu){
            System.out.println("----- Welcome to the Halbo User Menu! -----");
            System.out.println("1. Create a user");
            System.out.println("2. Search a user by username");
            System.out.println("3. Update a user");
            System.out.println("4. Delete a user");
            System.out.println("5. Show a list of users");
            System.out.println("9. Exit the menu");
            int selection = getUserInput();
            selectionUserMenu(selection);
        }
    }

    private void selectionUserMenu(int selection) {
        switch (selection){
            case 1: //Create a user.
                createUser();
                break;

            case 2: //Search a user.
                System.out.println(searchUserByUsername());
                break;

            case 3: //Update a user.
                User toUpdateUser = searchUserByUsername();
                if(toUpdateUser != null){
                    int userSelection = selectionUpdateUserMenu();
                    switch (userSelection){
                        case 1: //Change fullname
                                changeUserFullname(toUpdateUser);
                            break;

                        case 2: //Change password
                                changeUserPassword(toUpdateUser);
                            break;
                    }
                }
                break;

            case 4: //Delete a user.
                System.out.println("Not implemented yet");
                break;

            case 5: //print out a list of users.
                System.out.println(UserFactory.getInstance().getUsers());
                break;

            case 9: //Stop the user menu
                runMenu = false;
                break;
            default: //User made an invalid choice.
                System.out.println("Please choose a valid number from the menu.");
                break;
        }
    }

    private void changeUserPassword(User toUpdateUser) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What will the new password be for the user");
        String newPassword = userInput.nextLine();
        toUpdateUser.setPassword(newPassword);
    }

    private void changeUserFullname(User toUpdateUser) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What will the new fullname of the user be?");
        String newFullname = userInput.nextLine();
        toUpdateUser.setFullName(newFullname);
    }

    private int selectionUpdateUserMenu() {
        System.out.println("----- What would you like to do with the user? -----");
        System.out.println("1. Change fullname");
        System.out.println("2. Change password");
        return getUserInputWithMax(2);
    }

    private User searchUserByUsername() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What is the username of the user?");
        String username = userInput.nextLine();
        User searchedUser = UserFactory.getInstance().findUserByUsername(username);
        if(searchedUser != null){
            //It has found an user.
            return searchedUser;
        }else {
            //It has not found a user.
            return null;
        }
    }

    private void createUser() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What is the fullname of the user?");
        String fullname = userInput.nextLine();
        System.out.println("What will their username be?");
        String username = userInput.nextLine();
        System.out.println("What is their password?");
        String password = userInput.nextLine();
        UserFactory.getInstance().addUser(fullname, username, password);
        System.out.println("Succes!");
    }

    private int getUserInput() {
        Scanner userSelection = new Scanner(System.in);
        while(!userSelection.hasNextInt()){
            System.out.println("please fill in a number");
            userSelection.next();
        }
        return userSelection.nextInt();
    }

    private int getUserInputWithMax(int maxInput) {
        Scanner userSelection = new Scanner(System.in);
        int userInput = 0;
        do{
            if(!userSelection.hasNextInt()){
                System.out.println("Enter a valid number");
                userSelection.next();
            }else{
                userInput = userSelection.nextInt();
            }
        }
        while(!isIntegerOutOfBounds(userInput,maxInput));

        return userInput;
    }

    private boolean isIntegerOutOfBounds(int tempNumber, int maxInput) {
        if(tempNumber <= maxInput){
            //Number is inbounds
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) {
        ServerCLI menu = new ServerCLI();
        menu.run();
        // UserFactory.getInstance().addUser(); creates an user.
    }
}
