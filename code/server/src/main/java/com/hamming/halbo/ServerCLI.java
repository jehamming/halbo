package com.hamming.halbo;


import com.hamming.halbo.factories.*;
import com.hamming.halbo.model.City;
import com.hamming.halbo.model.User;
import com.hamming.halbo.model.World;
import com.hamming.halbo.forms.serverAdminWindow;

import java.util.Scanner;

public class ServerCLI {

    private boolean runMenu = true;

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
            System.out.println("5. Load everything from files");
            System.out.println("6. Store everything to files");
            System.out.println("7. Start server window");
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
                showWorldsMenu();
                break;
            
            case 3:
                showContinentMenu();
                break;

            case 4:
                showCitiesMenu();
                break;

            case 5:
                loadEverything();
                break;

            case 6:
                storeEverything();
                break;

            case 7:
                startTestGUI();
                break;

            default:
                System.out.println("Please fill in a valid number from the menu selection");
                break;
        }
    }

    private void startTestGUI() {
        serverAdminWindow gui = new serverAdminWindow();
        gui.run();
    }

    private void showWorldsMenu() {
        while(runMenu){
            System.out.println("----- Welcome to the Halbo Worlds Menu! -----");
            System.out.println("1. Create a world");
            System.out.println("2. Search a world by World name");
            System.out.println("3. Delete a world by World name");
            System.out.println("4. Show the list of Worlds");
            System.out.println("9. Exit the menu");
            int selection = getUserInput();
            selectionWorldsMenu(selection);
        }
    }

    private void selectionWorldsMenu(int selection) {
        switch (selection){
            case 1: //Create a world
                createWorld();
                break;

            case 2: //Search a world by world name
                System.out.println(searchWorldByName());
                break;

            case 3: //Delete a world
                World toDeleteWorld = searchWorldByName();
                WorldFactory.getInstance().deleteWorld(toDeleteWorld);
                break;

            case 4:
                System.out.println(WorldFactory.getInstance().getWorldsAsString());
                break;

            case 9:
                runMenu = false;
                break;

                default:
                    System.out.println("Please choose a valid selection from the menu");
                    break;
        }
    }

    private World searchWorldByName() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What is the name of the world?");
        String worldName = userInput.nextLine();

        return WorldFactory.getInstance().getWorldByName(worldName);
    }

    private void createWorld() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Who will the creator be of the world?");
        User creator = searchUserByUsername();
        System.out.println("What will the name be of the world?");
        String worldName = userInput.nextLine();
        WorldFactory.getInstance().createWorld(creator,worldName);
    }

    private void showCitiesMenu() {
        while(runMenu){
            System.out.println("----- Welcome to the Halbo Cities Menu! -----");
            System.out.println("1. Create a city");
            System.out.println("2. Search a city by City name");
            System.out.println("3. Delete a city by City name");
            System.out.println("4. Show the list of cities");
            System.out.println("9. Exit the menu");
            int selection = getUserInput();
            selectionCitiesMenu(selection);
        }
    }



    private void selectionCitiesMenu(int selection) {
        switch (selection){

            case 1: //Create a city.
                createCity();
                break;

            case 2: //Search a city by name.
                System.out.println(searchCityByName());
                break;

            case 3: //Remove a city.
                City toDeleteCity = searchCityByName();
                CityFactory.getInstance().deleteCity(toDeleteCity);
                break;

            case 4: //Prints out all the cities.
                System.out.println(CityFactory.getInstance().getCitiesAsString());
                break;

            case 9: //Stop the menu
                runMenu = false;
                break;
            default:
                System.out.println("Please choose a valid selection from the menu");
                break;
        }
    }

    private void showContinentMenu() {
        while(runMenu){
            System.out.println("----- Welcome to the Halbo Continent Menu! -----");
            System.out.println("1. Create a continent");
            System.out.println("2. Show the list of continents");
            System.out.println("9. Exit the menu");
            int selection = getUserInput();
            selectionContinentMenu(selection);
        }
    }

    private void selectionContinentMenu(int selection) {
        switch (selection){
            case 1: //Create a continent
                createContinent();
                break;

            case 2: //Return a list of cities on the continent
                System.out.println(ContinentFactory.getInstance().getContinentsAsString());
                break;

            case 9: //Stop the menu
                runMenu = false;
                break;

                default: //The user has not made an valid choice.
                    System.out.println("Please choose a valid selection from the menu.");
                    break;
        }
    }

    private void createContinent() {

    }

    private City searchCityByName() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What is the name of the city?");
        String cityName = userInput.nextLine();
        City toSearchCity = CityFactory.getInstance().findCityByName(cityName);
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
        if ( creator != null ) {
           // CityFactory.getInstance().createCity(cityName, creator);
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
                                changeUsername(toUpdateUser);
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

    public void loadEverything() {
        // Load Config
        ServerConfig config = ServerConfig.getInstance();
        // Load Data
        UserFactory.getInstance().loadUsersFromFile(config.getUsersDataFile());
        WorldFactory.getInstance().loadWorldsFromFile(config.getWorldsDataFile());
        CityFactory.getInstance().loadCitiesFromFile(config.getCitiesDataFile());
        ContinentFactory.getInstance().loadContinentsFromFile(config.getContinentsDataFile());
        BaseplateFactory.getInstance().loadBaseplatesFromFile(config.getBaseplatesDataFile());
        System.out.println("Loaded Users, Worlds, Cities, Baseplates");
    }

    public void storeEverything() {
        ServerConfig config = ServerConfig.getInstance();
        UserFactory.getInstance().storeUsersInFile(config.getUsersDataFile());
        WorldFactory.getInstance().storeWorldsInFile(config.getWorldsDataFile());
        CityFactory.getInstance().storeCitiesInFile(config.getCitiesDataFile());
        ContinentFactory.getInstance().storeContinentsInFile(config.getContinentsDataFile());
        BaseplateFactory.getInstance().storeBaseplatesInFile(config.getBaseplatesDataFile());
        System.out.println("Stored Users, Worlds, Cities, Baseplates");
    }

    private void changeUserPassword(User toUpdateUser) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What will the new password be for the user");
        String newPassword = userInput.nextLine();
        toUpdateUser.setPassword(newPassword);
    }

    private void changeUsername(User toUpdateUser) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What will the new name of the user be?");
        String newFullname = userInput.nextLine();
        toUpdateUser.setName(newFullname);
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
            //It has not found a user
            System.out.println("User with username '" + username + "' not found!");
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
        System.out.println("What is their Email address?");
        String email = userInput.nextLine();
        UserFactory.getInstance().addUser(fullname, username, password, email);
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
    }
}
