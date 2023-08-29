package org.service;

import java.time.Year;
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

            }
        }
    }
    public static void inputValidator(int choice) {
        switch (choice) {
            case 1:
            case 2:

        }
    }

    public static void customerBusSeatChoices() {
        while (true) {
            System.out.println("0.Exit");
            System.out.println("1.Book seat");
            System.out.println("2.Find your booking ");
            System.out.println("> ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("Thank you for using our service!");
                break;
            }else if (choice == 1) {
                busWindowSeatChoice();
                break;
            }else if (choice == 2) {

            }
        }
    }
    public static void busInspectorChoices() {
        while (true) {
            System.out.println("0.Exit");
            System.out.println("1.Check profit");
            System.out.println("2.Sort customers");

            int choice = scanner.nextInt();
            if (choice == 0) {
                break;
            }else if (choice == 1) {
                System.out.println("Current profit is " + getTotalProfit(busSeats, 0, 0, 0.0) + " KR");
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
            System.out.println("> ");

            int choice = scanner.nextInt();

            if (choice >= 1 && choice <= 20) {

            }
        }

    }

    public static String unbookedWindowSeats() {
        StringBuilder windowSeats = new StringBuilder();
        for (int i = 0; i < busSeats.length; i++) {
            for (int j = 0; j < 4; j += 3) {
                if (!busSeats[i][j].equals("X")){
                    windowSeats.append(" ").append(busSeats[i][j]);
                }
            }
        }

        return windowSeats.toString();
    }

    public static boolean validateRole() {
        while (true) {
            System.out.println("1.Bus Inspector ");
            System.out.println("2.Customer ");
            System.out.println("> ");
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
    public static boolean currentAgeCheck(String seat) {
        int dateOfBirth = Integer.parseInt(splittedString(seat)[3].split("-")[2]);
        int currentYear = Year.now().getValue();
        return currentYear - dateOfBirth >= 18;
    }
    public static int getSeat(String seat) {
        return Integer.parseInt(splittedString(seat)[4]);
    }
    public static String formatDateOfBirth(String dateOfBirth) {
        return dateOfBirth.substring(0, 4) + "-" +
                dateOfBirth.substring(4, 6) + "-" +
                dateOfBirth.substring(6, 8);
    }
    public static double getTotalProfit(String[][] bookedSeats, int rows, int columns, double profit) {
        if (rows > bookedSeats.length) {
            return profit;
        }

        if (!currentAgeCheck(bookedSeats[rows][columns])) {
            return getTotalProfit(bookedSeats, rows + 1, columns + 1, profit + 149.90);
        }

        return getTotalProfit(bookedSeats, rows + 1, columns + 1, profit + 299.90);
    }

    public static String[] splittedString(String seat) {
        return seat.split(",");
    }
    public static void printBus() {
        printBusFrontFrame();
        printBusInteriorLayout();
        printBusBackFrame();
    }
    public static void printBusInteriorLayout() {
        for (int i = 0; i < busSeats.length; i++) {
            System.out.println();
            for (int j = 0; j < 4; j++) {
                printFormattedBusSeats(busSeats[i][j]);
            }
            printWheels(i);
        }
    }
    public static void printFormattedBusSeats(String seat) {
        if (seat.length() <= 2 && !seat.isEmpty()) {
            System.out.printf("|%2s|", seat);
        }else {
            System.out.printf("|%2s", bookedSeatCheck(seat));
        }
    }
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
    public static void printWheels(int seat) {
        if (seat == 0) {
            System.out.print("\n()------------()");
        }else if (seat == 3) {
            System.out.print("\n()------------()");
        }
    }
    public static void printBusBackFrame(){
        System.out.println("\n|--------------|");
        System.out.println("\\--------------/");
        System.out.println(" \\-/_\\----/_\\-/\n");
    }
}