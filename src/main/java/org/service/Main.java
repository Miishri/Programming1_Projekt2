package org.service;

import java.time.Year;
import java.util.Scanner;

/**
 * The main class for the bus service program.
 */
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String[][] seats = {
            {"1","2","3","4"},
            {"5","6","7","8"},
            {"9","10","11","12"},
            {"13","14","15","16"},
            {"17","18","19","20"}
    };

    /**
     * Start point for the bus service program.
     * @param args CL arguments passed to the program.
     */
    public static void main(String[] args) {
        startBusService();
    }

    /**
     * Initiates the bus service and handles user interactions.
     * Displays the bus layout, validates user role, and directs to the corresponding actions.
     */
    public static void startBusService() {
        while (true) {
            printBus();
            switch (validateRole()) {
                case 1 -> busInspectorChoice();
                case 2 -> customerBusSeatChoice();
                case 0 -> {
                }
            }
        }
    }

    /**
     * Menu of choices for the customer's bus seat actions.
     * Displays menu options, takes user input, and performs actions according to it.
     */
    public static void customerBusSeatChoice() {
        while (true) {
            printCustomerChoices();
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 0 -> {
                        startBusService();
                        return;
                    }
                    case 1 -> busWindowSeatChoice();
                    case 2 -> findCustomerBooked();
                    case 3 -> printBus();
                    default -> printError("Customer: Please choose an appropriate action");
                }

            }catch (Exception e) {
                scanner.next();
                printError("customer");
            }
        }
    }

    public static void printCustomerChoices() {
        System.out.println("0.Exit");
        System.out.println("1.Book seat");
        System.out.println("2.Find your booking");
        System.out.println("3.Show bus");
        System.out.print("> ");
    }

    /**
     * Prompt the user for their birthdate and display any found customer booking data.
     */
    public static void findCustomerBooked() {
        while (true) {
            System.out.println("0.Exit");
            System.out.print("Please provide your birthdate(YYYYMMDD): ");
            String dateOfBirth = scanner.next();

            if (dateOfBirth.equals("0")) {
                break;
            }
            if (dateOfBirth.length() == 8) {
                System.out.println(findCustomerData(dateOfBirth));
            }
        }
    }
    /**
     * Finds and returns customer data depending on the provided birthdate.
     *
     * @param birthdate The birthdate of the customer to do the search with.
     * @return A formatted string containing customer details, or a message if no booking exists with the birthdate.
     */
    public static String findCustomerData(String birthdate) {
        for (String[] busSeat : seats) {
            for (int j = 0; j < 4; j += 3) {
                String seatDetails = busSeat[j];
                if (checkBookedSeat(seatDetails)) {
                    if (getBirthdate(seatDetails).equals(formatBirthdate(birthdate))) {
                        return getFormattedCustomerDetails(seatDetails);
                    }
                }
            }
        }

        return "No booking was found";
    }

    /**
     * Menu of choices for the Bus Inspector role.
     * Displays menu options, takes user input, and performs actions according to it.
     * Check profit calls recursive function to get the total profit made.
     * Sort customers
     */
    public static void busInspectorChoice() {
        while (true) {
            printBusInspectorChoices();
            try {
                int inspectorChoice = scanner.nextInt();
                switch (inspectorChoice) {
                    case 0 -> {
                        startBusService();
                        return;
                    }
                    case 1 -> {
                        double profit = calculateProfit(0, 0, 0.0);
                        System.out.println("Current profit is " + profit + " KR");
                    }
                    case 2 -> printSortedCustomers();
                    case 3 -> printBus();
                    default -> printError("Inspector: Please choose an appropriate action.");
                }

            }catch (Exception e) {
                scanner.nextLine();
                printError("inspector");
            }
        }
    }

    public static void printBusInspectorChoices() {
        System.out.println("0.Exit");
        System.out.println("1.Check profit");
        System.out.println("2.Sort customers");
        System.out.println("3.Show bus seats");
        System.out.print("> ");
    }


    public static String[] sortCustomers() {
        String[] customerArray = getCustomerSingleArray();
        for (int i = 0; i < customerArray.length; i++) {
            if (getAge(customerArray[i]) > getAge(customerArray[i + 1])) {
                String tempCustomer = customerArray[i];
                customerArray[i] = customerArray[i + 1];
                customerArray[i + 1] = tempCustomer;
            }
        }
        return customerArray;
    }

    public static String[] getCustomerSingleArray() {
        String[] singleArray = new String[0];
        for (String[] seat : seats) {
            for (int j = 0; j < 4; j++) {
                if (!checkBookedSeat(seat[j])) {
                    singleArray = add(seat[j], singleArray);
                }
            }
        }

        return singleArray;
    }



    public static String[] add(String seat, String[] sortedArray) {
        String[] array = new String[sortedArray.length + 1];
        for (int i = 0; i < array.length; i++) {
            if (i == sortedArray.length) {
                array[i] = seat;
            }else {
                array[i] = sortedArray[i];
            }
        }

        return array;
    }

    public static void printSortedCustomers() {
        for (String customer: sortCustomers()) {
            System.out.println(getFormattedCustomerDetails(customer));
        }
    }


    /**
     * Processes booking of a window seat in the bus.
     * Displays unbooked window seats, takes user input for seat choice, and processes customer details for booking the seat and books it.
     */
    public static void busWindowSeatChoice() {
        while (true) {
            printWindowSeatChoices();
            try {
                int choice = scanner.nextInt();

                if ((choice >= 1 && choice <= 20) && !checkSeatAlreadyBooked(choice)) {
                    System.out.print("First name: ");
                    String firstName = scanner.next();

                    System.out.print("Last name: ");
                    String lastName = scanner.next();

                    System.out.print("Birthdate(YYYYMMDD): ");
                    String dateOfBirth = scanner.next();
                    if (submitCustomerDetails(firstName, lastName, dateOfBirth, choice)) {
                        break;
                    }
                }
            } catch (Exception e) {
                scanner.next();
                printError("birthdate");
            }
        }

    }

    public static void printWindowSeatChoices() {
        System.out.println("Window seats unbooked are:" + getUnbookedWindowSeats());
        System.out.println("1. Which seat to book? (1-20)");
        System.out.print("> ");
    }

    public static boolean checkSeatAlreadyBooked(int choice) {
        for (String[] seat : seats) {
                for (int j = 0; j < 4; j++) {
                if (!checkBookedSeat(seat[j])) {
                    int busSeat = Integer.parseInt(getSeatNumber(seat[j]));
                    if (busSeat == choice) {
                        System.out.println("Seat number has already been booked.");
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Concatenates customer details for seat booking.
     * Validates format of date of birth and if valid, formats, prints and books the seat. Returns `true` on success
     *
     * @param firstName First name of the customer.
     * @param lastName Last name of the customer.
     * @param birthdate Date of birth of the customer.
     * @param choice Seat choice.
     * @return `true` if valid details and booked seat, otherwise `false`.
     */
    public static boolean submitCustomerDetails(String firstName, String lastName, String birthdate, int choice) {
        if (birthdate.length() == 8) {
            String customerDetails = formatCustomerDetails(firstName, lastName, birthdate, choice);
            bookSeat(customerDetails);
            System.out.println("Thank you for the booking, " + getFullName(customerDetails));
            System.out.println(getFormattedCustomerDetails(customerDetails));
            return true;
        }
        System.out.println("Incorrect details.");
        return false;
    }

    /**
     * Books a specific seat in the bus layout.
     * @param seat The seat to be booked.
     */
    public static void bookSeat(String seat) {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (seats[i][j].equals(getSeatNumber(seat))) {
                    seats[i][j] = seat;
                }
            }
        }
    }

    /**
     * Retrieves unbooked window seats from the bus layout.
     * @return An appended string containing unbooked window seats or a message if none are available.
     */
    public static String getUnbookedWindowSeats() {
        String windowSeats = "";
        for (String[] busSeat : seats) {
            for (int j = 0; j < 4; j += 3) {
                if (!checkBookedSeat(busSeat[j])) {
                    windowSeats += " " + busSeat[j];
                }
            }
        }

        if (windowSeats.isEmpty()) {
            windowSeats = "No window seats are left.";
        }

        return windowSeats;
    }

    /**
     * Validates and returns the user role.
     * Displays role options (1.Bus Inspector or 2.Customer), takes user input to return boolean values.
     *
     * @return `1` for Bus Inspector, `2` for Customer, '0' to Exit
     */
    public static int validateRole() {
        while (true) {
            try {
                printRoleOptions();
                int userRoleChoice = scanner.nextInt();
                switch (userRoleChoice) {
                    case 0 -> {return 0;}
                    case 1 -> {return 1;}
                    case 2 -> {return 2;}
                    default -> printError("Please choose an appropriate role.");
                }
            } catch (Exception e) {
                scanner.next();
                printError("role");
            }
        }
    }

    public static void printRoleOptions() {
        System.out.println("0.Exit ");
        System.out.println("1.Bus Inspector ");
        System.out.println("2.Customer ");
        System.out.print("> ");
    }

    /**
     * Formats and creates a booked seat CSV details string.
     *
     * @param firstName First name of the passenger.
     * @param lastName Last name of the passenger.
     * @param dateOfBirth Date of birth of the passenger. (YYYYMMDD)
     * @param busNumber Bus number associated with the booking.
     * @return Formatted string containing booked seat details.
     */
    public static String formatCustomerDetails(String firstName, String lastName, String dateOfBirth, int busNumber) {
        return "X," + firstName + "," + lastName + "," + formatBirthdate(dateOfBirth) + "," + busNumber;
    }

    /**
     * Counts the number of available seats in the bus.
     *
     * @return number of available seats.
     */
    public static int getAvailableSeats() {
        int availableSeats = 0;
        for (String[] busSeat : seats) {
            for (int j = 0; j < 4; j++) {
                if (!checkBookedSeat(busSeat[j])) {
                    availableSeats += 1;
                }
            }
        }
        return availableSeats;
    }


    public static String getBirthdate(String seat) {
        return getCustomerDetails(seat)[3].trim();
    }

    /**
     * Checks if a seat is booked.
     *
     * @param seat Seat CSV containing booking confirmation.
     * @return Booking status of the seat.
     */
    public static boolean checkBookedSeat(String seat) {
        return getCustomerDetails(seat)[0].equals("X");
    }

    /**
     * Takes in a CSV formatted String and calls the method splitString() for the array, then fetches the booked bus seat number.
     * @param seat CSV formatted String containing seat information.
     * @return Returns the booked seat number.
     * */
    public static String getSeatNumber(String seat) {
        return getCustomerDetails(seat)[4];
    }

    /**
     * Extracts full name from seat String CSV .
     *
     * @param seat Seat CSV containing name information.
     * @return Concatenated first name and last name.
     */
    public static String getFullName(String seat) {
        return getCustomerDetails(seat)[1] + " " + getCustomerDetails(seat)[2];
    }

    /**
     * Checks the age based on the birth year extracted from a csv seat details String.
     * If seat has more than 2 characters, it extracts the birth year from the seat information using the splittedString() method and calculates age.
     *
     * @param seat The seat representation containing CSV formatted details.
     * @return 0 if the calculated age is 18 or older, 1 if it's younger than 18, and 2 if unable to determine.
     */
    public static int currentAgeCheck(String seat) {

        if (seat.length() > 2) {
            int age = getAge(seat);

            if (age >= 18) {
                return 0;
            }else {
                return 1;
            }

        }
        return 2;
    }

    public static int getAge(String seat) {
        int birthYear = Integer.parseInt(getCustomerDetails(seat)[3].split("-")[0]);
        int currentYear = Year.now().getValue();

        return currentYear - birthYear;
    }


    /**
     * Prints full name, birthdate, and seat number formatted from customer details extracted from CSV string
     * @param csv CSV string containing customer details.
     * @return Formatted customer details in an order.
     */
    public static String getFormattedCustomerDetails(String csv) {
        return "Name: " + getFullName(csv) +
                "\nBirthdate: "  + getCustomerDetails(csv)[3]+
                "\nSeat: " + getSeatNumber(csv);
    }

    /**
     * Recursively calculates the profit within a 2D array using the currentAgeCheck() function.
     * @param currentRow The current currentRow index, starting from 0 up to array.length.
     * @param currentColumn The current currentColumn index within the currentRow, accessed through the 2D array.
     * @param profit The total profit calculated so far.
     * @return The calculated profit as a double value.
     */
    public static double calculateProfit(int currentRow, int currentColumn, double profit) {
        int ageValidator = currentAgeCheck(seats[currentRow][currentColumn]);

        if (currentRow >= seats.length) return profit;

        if (currentColumn > 3) return calculateProfit(currentRow + 1, 0, profit);

        if (ageValidator == 0) {
            return calculateProfit(currentRow, currentColumn + 1, profit + 149.90);
        }else if (ageValidator == 1){
            return calculateProfit(currentRow, currentColumn + 1, profit + 299.90);
        }

        return calculateProfit(currentRow, currentColumn + 1, profit);
    }

    /**
     * Splits a comma-separated value (CSV) formatted string into an array of individual values.
     * @param csvDetails The input string in CSV format.
     * @return An array containing the individual values split from the CSV string.
     */
    public static String[] getCustomerDetails(String csvDetails) {
        return csvDetails.split(",");
    }

    public static void printError(String error) {
        if (error.isEmpty()) {
            System.out.println("Please choose an appropriate number.");
        }else {
            System.out.println(error);
        }
    }

    /**
     * Takes in a string that is formatted in CSV to extract the date of birth (YYYYMMDD) and reformat it to (YYYY-MM-DD).
     * @param birthdate CSV formatted details about the customer.
     * @return Returns the reformatted date of birth.
     */
    public static String formatBirthdate(String birthdate) {
        return birthdate.substring(0, 4) + "-" +
                birthdate.substring(4, 6) + "-" +
                birthdate.substring(6, 8);
    }

    /**
     * Calls printBusFrontFrame(), printBusInteriorLayout() and printBusBackFrame() to create the bus layout including the seat numbers
     * */
    public static void printBus() {
        printBusFront();
        printBusSeats();
        printBusBack();
    }

    /**
     * Prints the arranged bus seats by iterating through each row and column in the static 2D bus seats array.
     * <pre>
     *     This method prints a new line at the beginning of each row iteration and invokes the printFormattedBusSeats() method for each value in the 2D array.
     * </pre>
     * After the second loop completes, the printWheels() method is called.
     */
    public static void printBusSeats() {
        for (int i = 0; i < seats.length; i++) {
            System.out.println();
            for (int j = 0; j < 4; j++) {
                printSeat(seats[i][j]);
            }
            printWheels(i);
        }
    }

    /**
     * Converts a seat string, which can be either a number or 'X', into a formatted string.
     * <pre>
     *     The formatted string is printed using System.out.printf() with a minimum width of 2 spaces.
     * </pre>
     * @param seat The seat representation to be converted and formatted.
     */
    public static void printSeat(String seat) {
        if (seat.length() <= 2 && !seat.isEmpty()) {
            System.out.printf("|%2s|", seat);
        }else {
            System.out.printf("|%2s", checkBookedSeat(seat));
        }
    }

    /**
     * Print the front of the bus through System.out.printf()/println() and shows available seats using checkAvailableSeats()
     * */
    public static void printBusFront(){
        System.out.println("Night bus towards Japan.");
        System.out.println("Available seats = " + getAvailableSeats());
        System.out.printf("   %3s%n", "\\-/    \\-/");
        System.out.println(" __>-<____>-<__");
        System.out.println("/___|----------\\");
        System.out.print("|_D_|__/   _===:");
    }

    /**
     * <pre>
     *     Prints out the wheels at specific rows in the arranged bus seats.
     * </pre>
     * <pre>
     *     If the provided row index is 0 or 3, the wheels are printed after the first row.
     * </pre>
     * @param row The current row index in the 2D seats array.
     */
    public static void printWheels(int row) {
        if (row == 0 || row == 3) {
            System.out.print("\n()------------()");
        }
    }

    /**
     * Print the front of the bus through multiple System.out.println()
     * */
    public static void printBusBack(){
        System.out.println("\n|--------------|");
        System.out.println("\\--------------/");
        System.out.println(" \\-/_\\----/_\\-/\n");
    }
}