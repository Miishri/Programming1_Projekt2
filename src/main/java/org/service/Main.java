package org.service;

import java.sql.SQLOutput;
import java.time.Year;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String[][] busSeats = {
            {"1","2","3","4"},
            {"5","6","7","8"},
            {"9","10","11","12"},
            {"13","14","15","16"},
            {"17","18","19","20"}
    };
    public static void main(String[] args) {
        startBusService();
    }

    public static void startBusService() {
        while (true) {
            printBus();
            if (validateRole()) {
                busInspectorChoices();
            }else {
                customerBusSeatChoices();
            }
        }
    }

    public static void customerBusSeatChoices() {
        while (true) {
            System.out.println("0.Exit");
            System.out.println("1.Book seat");
            System.out.println("2.Find your booking ");
            System.out.print("> ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("Thank you for using our service!");
                break;
            }else if (choice == 1) {
                busWindowSeatChoice();
            }else if (choice == 2) {

            }
        }
    }

    public static void busInspectorChoices() {
        while (true) {
            System.out.println("0.Exit");
            System.out.println("1.Check profit");
            System.out.println("2.Sort customers");
            System.out.println("> ");

            int choice = scanner.nextInt();
            if (choice == 0) {
                break;
            }else if (choice == 1) {
                double profit = getTotalProfit(busSeats, 0, 0, 0.0);
                System.out.println("Current profit is " + profit + " KR");
            }else if (choice == 2) {

            }
        }
    }
    public static void sortCustomers() {

    }
    public static void busWindowSeatChoice() {
        while (true) {
            System.out.println("Window seats unbooked are:" + unbookedWindowSeats());
            System.out.println("1. Which seat to book? (1-20)");
            System.out.print("> ");

            int choice = scanner.nextInt();

            if (choice >= 1 && choice <= 20) {
                System.out.print("First name: ");
                String firstName = scanner.next();

                System.out.print("Last name: ");
                String lastName = scanner.next();

                System.out.print("Birth date(YYYYMMDD): ");
                String dateOfBirth = scanner.next();
                if (!(dateOfBirth.length() < 8)) {
                    String details = bookedSeatDetailsFormatted(firstName, lastName, dateOfBirth, choice);
                    bookSeat(details);
                    System.out.println("Thank you for the booking, " + getFullName(details));
                    System.out.println(details);
                    break;
                }else {
                    System.out.println("Details provided were incorrect.");
                }
            }
        }

    }

    public static void bookSeat(String seat) {
        for (int i = 0; i < busSeats.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (busSeats[i][j].equals(getSeat(seat))) {
                    busSeats[i][j] = seat;
                }
            }
        }
    }
    public static String unbookedWindowSeats() {
        StringBuilder windowSeats = new StringBuilder();
        for (int i = 0; i < busSeats.length; i++) {
            for (int j = 0; j < 4; j += 3) {
                if (!splittedString(busSeats[i][j])[0].equals("X")){
                    windowSeats.append(" ").append(busSeats[i][j]);
                }
            }
        }

        if (windowSeats.isEmpty()) {
            windowSeats.append(" No window seats are left.");
        }

        return windowSeats.toString();
    }

    public static boolean validateRole() {
        while (true) {
            System.out.println("1.Bus Inspector ");
            System.out.println("2.Customer ");
            System.out.print("> ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                return true;
            }else if (choice == 2) {
                return false;
            }
        }
    }
    public static String bookedSeatDetailsFormatted(String firstName, String lastName, String dateOfBirth, int busNumber) {
        return "X," + firstName + "," + lastName + "," + formatDateOfBirth(dateOfBirth) + "," + busNumber;
    }
    public static int checkAvailableSeats() {
        int availableSeats = 0;
        for (int i = 0; i < busSeats.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (!splittedString(busSeats[i][j])[0].equals("X")) {
                    availableSeats += 1;
                }
            }
        }
        return availableSeats;
    }
    public static String bookedSeatCheck(String seat) {
        return splittedString(seat)[0];
    }
    public static String getFullName(String seat) {
        return splittedString(seat)[1] + " " + splittedString(seat)[2];
    }
    public static int currentAgeCheck(String seat) {
        if (seat.length() > 2) {
            System.out.println(splittedString(seat)[3]);
            int dateOfBirth = Integer.parseInt(splittedString(seat)[3].split("-")[0]);
            int currentYear = Year.now().getValue();

            if (currentYear - dateOfBirth >= 18) {
                return 0;
            }else {
                return 1;
            }
        }

        return 2;
    }

    /**
     *
     * */
    public static String getSeat(String seat) {
        return splittedString(seat)[4];
    }

    /**
     * Takes in a string that is formatted in csv to extract date of birth (YYYYMMDD) and reformat to (YYYY-MM-DD)
     * @param dateOfBirth csv formatted details about the customer
     * @return returns the reformatted date of birth back
     * */
    public static String formatDateOfBirth(String dateOfBirth) {
        return dateOfBirth.substring(0, 4) + "-" +
                dateOfBirth.substring(4, 6) + "-" +
                dateOfBirth.substring(6, 8);
    }

    /**
     * Recursively calls itself to calculate the profit inside 2d array, uses currentAgeCheck() to calculate profit
     * @param rows row starts with the value of 0 till array.Length
     * @param columns each column inside the current value of row accessed through the 2d array
     * @param profit carries the total profit
     * @return returns the profit in a primitive type of double back
     * */
    public static double getTotalProfit(int rows, int columns, double profit) {
        if (rows >= busSeats.length) {
            return profit;
        }

        if (columns > 3) {
            return getTotalProfit(rows + 1, 0, profit);
        }

        if (currentAgeCheck(busSeats[rows][columns]) == 0) {
            return getTotalProfit(rows, columns + 1, profit + 149.90);
        }else if (currentAgeCheck(busSeats[rows][columns]) == 1){
            getTotalProfit(rows, columns + 1, profit + 299.90);
        }

        return getTotalProfit(rows, columns + 1, profit);
    }

    /**
     * This method splits String that has multiple values saved into it with csv format
     * @param seat takes in a csv format string
     * @return returns the string split into an array
     * */
    public static String[] splittedString(String seat) {
        return seat.split(",");
    }


    /**
     * Calls printBusFrontFrame(), printBusInteriorLayout() and printBusBackFrame() to create the bus layout including the seat numbers
     * */
    public static void printBus() {
        printBusFrontFrame();
        printBusInteriorLayout();
        printBusBackFrame();
    }

    /**
     * Print the bus seats by looping through each row and column inside the globalized 2D bus seats array.
     * <pre>
     *     Prints a new line in the first loop and calls printFormattedBusSeats() with each value of 2d array provided
     * </pre>
     *<pre>
     *     calls the printWheels() method after second loop ends
     *</pre>
     * */
    public static void printBusInteriorLayout() {
        for (int i = 0; i < busSeats.length; i++) {
            System.out.println();
            for (int j = 0; j < 4; j++) {
                printFormattedBusSeats(busSeats[i][j]);
            }
            printWheels(i);
        }
    }

    /**
     * Converts a string of seat that is either a number or X
     * <pre>
     *     It is formatted with minimum 2 spaces of width and printed out with System.out.printf()
     * </pre>
     *
     * @param seat
     */
    public static void printFormattedBusSeats(String seat) {
        if (seat.length() <= 2 && !seat.isEmpty()) {
            System.out.printf("|%2s|", seat);
        }else {
            System.out.printf("|%2s", bookedSeatCheck(seat));
        }
    }

    /**
     * Print the front of the bus through System.out.printf() and shows available seats using checkAvailableSeats()
     * */
    public static void printBusFrontFrame(){
        System.out.println(
                "Night bus towards Japan." +
                "\n" +
                "Available seats = " + checkAvailableSeats());
        System.out.printf("   %3s%n", "\\-/    \\-/");
        System.out.print(" __>-<____>-<__");
        System.out.print("\n/___|----------\\");
        System.out.print("\n|_D_|__/   _===:");
    }

    /**
     *Print out the wheels depending on the row.
     * <pre>
     *     If row == 0 then it prints after the first row,
     *      else if row == 3 it prints before the last row
     * </pre>
     *
     * @param row current row of the 2d array with seats
     */
    public static void printWheels(int row) {
        if (row == 0) {
            System.out.print("\n()------------()");
        }else if (row == 3) {
            System.out.print("\n()------------()");
        }
    }


    /**
     * Print the front of the bus through multiple System.out.println()
     * */
    public static void printBusBackFrame(){
        System.out.println("\n|--------------|");
        System.out.println("\\--------------/");
        System.out.println(" \\-/_\\----/_\\-/\n");
    }
}