import java.util.Scanner;



public class menu {
public static String firstName = "";
public static String surname= "";
public static String companyName= "";
public static int yearsAtCompany = 0;



    public static void introduction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello and welcome to the programme. Before we start, lets collect some data about you.");
        System.out.println("What is your first name?");
        firstName = scanner.nextLine();
        System.out.println("What is your surname?");
        surname = scanner.nextLine();
        System.out.println("Where do you work?");
        companyName = scanner.nextLine();
        System.out.println("How many years have you worked for " + companyName + "?");
        yearsAtCompany = scanner.nextInt();
        System.out.println("Thanks for the information, let's make a start...");
        System.out.printf("Welcome to the programme %s %s. Congratulations on working for %s for %d years.\nNow, let's begin…", firstName, surname, companyName, yearsAtCompany);
        
    }

    public static void menuSelector(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWithin the programme you can navigate to four areas...\nOption 1: See your full name." +
                "\nOption 2: Reminder of where you work.\nOption 3: See how many years you have left until retirement." +
                "\nOption 4: Hear some personalised words of encouragement.\nPlease enter a number to navigate to the desired area.");
                outerloop:
                while(true){
                    int navigationChoice = 0;   
                    try {
                        navigationChoice = scanner.nextInt();
                        }
                    catch (Exception e) {
                        System.out.println("Invalid Menu choice, please use type integer.");
                        }
                        scanner.nextLine();        
                    if (navigationChoice < 5 && navigationChoice > 0){
                        switch (navigationChoice){
                            case 1: System.out.println("Congratulations " + firstName + " " + surname + " you've selected option 1");
                            break;
                            case 2: System.out.println("You need to be reminded of this?! You work at " + companyName);
                            break;
                            case 3: System.out.println("Still got a few years left to go! Most people work for an average of 40 years, you have " + (40 - yearsAtCompany) + " years to go, enjoy!");
                            break;
                            case 4: System.out.println(firstName + " " + surname + ", you've got this!");
                        }
                        while (true){
                            System.out.println("Would you like to continue?  Y/N");
                            String continueOption = scanner.nextLine();
                            if ((continueOption.equals("N")) || (continueOption.equals("n"))){
                                break outerloop;
                            } else if ((continueOption.equals("Y")) || (continueOption.equals("y"))){
                                System.out.println("Please choose a menu option");
                                break;
                            } else{
                                System.out.println("Please choose a valid continue option");
                            }
                        }
                    }else{
                        System.out.println("Please choose a valid menu option");
                    }
        
                }
    }
}
