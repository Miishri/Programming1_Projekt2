package org.service;

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

        printBus(myNumbers);
    }

    private static void printBus(String[][] bussSeats) {
        printBusFront(bussSeats);
        for (int i = 0; i < bussSeats.length; i++) {
            System.out.println();
            for (int j = 0; j < 4; j++) {
                printBusSeats(bussSeats[i][j]);
            }
            if (i == 1) {
                System.out.print("\n<<------------>>");
            }else if (i == 3) {
                System.out.print("\n<<------------>>");
            }
        }
        printBusBack();
    }

    private static void printBusSeats(String seat) {
        String bussSeat = String.format("|%2s|", seat);
        System.out.print(bussSeat);
    }
    private static void printBusFront(String[][] bussSeats){
        System.out.println(
                "Night bus towards Japan." +
                "\n" +
                "Available seats = " + checkAvailableSeats(bussSeats));
        System.out.printf("   %3s%n", "\\-/    \\-/");
        System.out.print(" __>-<____>-<__");
        System.out.print("\n/___|----------\\");
        System.out.print("\n|_D_|__/   _===:");
    }
    private static void printBusBack(){
        System.out.println("\n|--------------|");
        System.out.println("\\--------------/");
        System.out.println(" \\-/_\\----/_\\-/");
    }

    private static int checkAvailableSeats(String[][] bussSeats) {
        int sum = 0;
        for (int i = 0; i < bussSeats.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (!bussSeats[i][j].equals("X")) {
                    sum += 1;
                }
            }
        }

        return sum;
    }
}