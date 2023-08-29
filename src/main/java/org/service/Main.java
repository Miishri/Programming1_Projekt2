package org.service;

import java.time.Year;
import java.util.Scanner;

public class Main {
    static final double adultBusTicket = 299.90;
    static final double juvenileBusTicket = 149.90;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        String[][] bussSeats = {
                {"1","2","3","4"},
                {"5","6","7","8"},
                {"9","10","11","12"},
                {"13","14","15","16"},
                {"17","18","19","20"}
        };
        startBusService(bussSeats);

    }

    public static void startBusService(String[][] bussSeats) {
        printBus(bussSeats);
    }
    public static void inputValidator(int choice) {
        switch (choice) {
            case 1:
            case 2:

        }
    }

    public static int customerBusSeatChoice() {
        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Book seat");
            System.out.println("2. Find your booking ");

            int choice = scanner.nextInt();
        }
    }
    public static void busWindowSeatChoice(int choice) {
        if (choice == 1) {
            System.out.println("Window seats unbooked are: " );
            System.out.println("1. Which seat to book? (1-20)");
        }
    }

    public static void unbookedWindowSeats(String[][] busSeats) {

    }

    public static int validateRole() {
        while (true) {
            System.out.println("1.Bus Inspector ");
            System.out.println("2.Customer ");

            int choice = scanner.nextInt();
            if (choice == 1) {
                return 1;
            }else if (choice == 2) {
                return 2;
            }
        }
    }

    public static void bookedSeatDetails(String firstName, String lastName, String dateOfBirth, int busNumber) {

    }
    public static int checkAvailableSeats(String[][] bussSeats) {
        int availableSeats = 0;
        for (int i = 0; i < bussSeats.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (!splittedString(bussSeats[i][j])[0].equals("X")) {
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
            return getTotalProfit(bookedSeats, rows + 1, columns + 1, profit + juvenileBusTicket);
        }

        return getTotalProfit(bookedSeats, rows + 1, columns + 1, profit + adultBusTicket);
    }

    public static String[] splittedString(String seat) {
        return seat.split(",");
    }

    public static void printBus(String[][] bussSeats) {
        printBusFrontFrame(bussSeats);
        printBusInteriorLayout(bussSeats);
        printBusBackFrame();
    }
    public static void printBusInteriorLayout(String[][] bussSeats) {
        for (int i = 0; i < bussSeats.length; i++) {
            System.out.println();
            for (int j = 0; j < 4; j++) {
                printFormattedBusSeats(bussSeats[i][j]);
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
    public static void printBusFrontFrame(String[][] bussSeats){
        System.out.println(
                "Night bus towards Japan." +
                "\n" +
                "Available seats = " + checkAvailableSeats(bussSeats));
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
        System.out.println(" \\-/_\\----/_\\-/");
    }
}