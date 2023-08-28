package org.service;

import java.util.Arrays;

public class Main {
    
    final double adultTicket = 299.90;
    final double juvenileTicket = 149.90;

    public static void main(String[] args) {
        String[][] myNumbers = {
                {"1","2","3","4"},
                {"5","6","7","8"},
                {"9","10","11","12"},
                {"13","14","15","16"},
                {"17","18","19","20"}
        };

        printBussSeats(myNumbers);
    }

    private static void printBussSeats(String[][] bussSeats) {
        System.out.println("Night bus towards Japan.\n Available seats =");
        System.out.printf("   %3s%n", "\\-/   \\-/");
        System.out.print("___>-<___>-<___");
        System.out.print("\n|_|______  _===:");
        for (int i = 0; i < bussSeats.length; i++) {
            for (int j = 0; j < 4; j++) {

            }
        }
    }
}