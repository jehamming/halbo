package com.hamming.halbo;

import com.hamming.halbo.datamodel.City;
import com.hamming.halbo.datamodel.User;
import com.hamming.halbo.factories.UserFactory;

import java.util.Scanner;

public class ServerCLI {

    boolean runMenu = true;
    ContinentServer cs = new ContinentServer();

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

            case 2:
                System.out.println("Not yet implemented.");
                break;
            
            case 3:
                showContinentMenu();
                break;
                
            default:
                System.out.println("Please fill in a valid number from the menu selection");
                break;
        }
    }

    private void showContinentMenu() {
        while(runMenu){
            System.out.println("----- Welcome to the Halbo User Menu! -----");
            System.out.println("1. Add a city to a continent");
            System.out.println("2. Search a city by City name");
            System.out.println("3. Delete a city by City name");
            System.out.println("4. Show the list of cities");
            System.out.println("9. Exit the menu");
            int selection = getUserInput();
            selectionContinentMenu(selection);
        }
    }

    private void selectionContinentMenu(int selection) {
        switch (selection){
            case 1: //Create a city
                createCity();
                break;


            case 2: //Search a city by city name
                System.out.println(searchCityByCityName());
                break;


            case 3://Delete a city
                City toDeleteCity = searchCityByCityName();
                cs.removeCityByCity(toDeleteCity);
                break;

            case 4: //Return a list of cities on the continent
                System.out.println(cs.getCitiesAsString());
                break;

            case 9: //Stop the menu
                runMenu = false;
                break;

                default: //The user has not made an valid choice.
                    System.out.println("Please choose a valid selection from the menu.");
                    break;
        }
    }

    private City searchCityByCityName() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What is the name of the city?");
        String cityName = userInput.nextLine();
        City toSearchCity = cs.findCityByName(cityName);
        if(toSearchCity != null){
            //It has found an city.
            return toSearchCity;
        }else {
            //It has not found an city.
            return null;
        }
    }

    private void createCity() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What will the name of the city be?");
        String cityName = userInput.nextLine();
        User creator = searchUserByUsername();
        cs.addCity(cityName,creator);
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
                System.out.println(UserFactory.getInstance().getUsersAsAString());
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
