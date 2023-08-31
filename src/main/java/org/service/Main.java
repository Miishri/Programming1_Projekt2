package org.service;

import java.sql.SQLOutput;
import java.time.Year;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The main class for the bus service program.
 */
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String[][] busSeats = {
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
            int roleValidator = validateRole();
            if (roleValidator == 1) {
                busInspectorChoices();
            }else if (roleValidator == 2){
                customerBusSeatChoices();
            }else {
                System.out.println("Thank you for using our service!");
                break;
            }
        }
    }

    /**
     * Menu of choices for the customer's bus seat actions.
     * Displays menu options, takes user input, and performs actions according to it.
     */
    public static void customerBusSeatChoices() {
        while (true) {
            System.out.println("0.Exit");
            System.out.println("1.Book seat");
            System.out.println("2.Find your booking ");
            System.out.println("3.Show bus");
            System.out.print("> ");
            try {
                int choice = scanner.nextInt();

                if (choice == 0) {
                    System.out.println("Thank you for using our service!");
                    break;
                }else if (choice == 1) {
                    busWindowSeatChoice();
                }else if (choice == 2) {
                    findCustomerBooked();
                }else if (choice == 3) {
                    printBus();
                }
            }catch (Exception e) {
                System.out.println("Please choose an appropriate actions.");
            }
        }
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
        StringBuilder stringBuilder = new StringBuilder();
        for (String[] busSeat : busSeats) {
            for (int j = 0; j < 4; j += 3) {
                String data = busSeat[j];
                if (splittedString(data)[0].equals("X")) {
                    if (splittedString(data)[3].trim().equals(formatDateOfBirth(birthdate))) {
                        stringBuilder.append(formattedCustomerDetails(data));
                        break;
                    }
                }
            }
        }

        if (stringBuilder.isEmpty()) {
            stringBuilder.append("No booking was found");
        }

        return stringBuilder.toString();
    }

    /**
     * Menu of choices for the Bus Inspector role.
     * Displays menu options, takes user input, and performs actions according to it.
     * Check profit calls recursive function to get the total profit made.
     * Sort customers
     */
    public static void busInspectorChoices() {
        while (true) {
            System.out.println("0.Exit");
            System.out.println("1.Check profit");
            System.out.println("2.Sort customers");
            System.out.println("3.Show bus seats");
            System.out.println("> ");

            try {
                int choice = scanner.nextInt();
                if (choice == 0) {
                    break;
                }else if (choice == 1) {
                    double profit = getTotalProfit(0, 0, 0.0);
                    System.out.println("Current profit is " + profit + " KR");
                }else if (choice == 2) {

                }else if (choice == 3) {
                    printBus();
                }
            }catch (Exception e) {
                System.out.println("Please choose an action.");
            }
        }
    }


    public static void sortCustomers() {

    }


    /**
     * Processes booking of a window seat in the bus.
     * Displays unbooked window seats, takes user input for seat choice, and processes customer details for booking the seat and books it.
     */
    public static void busWindowSeatChoice() {
        while (true) {
            System.out.println("Window seats unbooked are:" + unbookedWindowSeats());
            System.out.println("1. Which seat to book? (1-20)");
            System.out.print("> ");

            int choice = scanner.nextInt();

            if (choice >= 1 && choice <= 20 && !seatAlreadyExists(choice)) {
                System.out.print("First name: ");
                String firstName = scanner.next();

                System.out.print("Last name: ");
                String lastName = scanner.next();

                System.out.print("Birth date(YYYYMMDD): ");
                String dateOfBirth = scanner.next();
                if (customerDetails(firstName, lastName, dateOfBirth, choice)) {
                    break;
                }
            }
        }

    }

    public static boolean seatAlreadyExists(int choice) {
        for (String[] seat : busSeats) {
            for (int j = 0; j < 4; j++) {
                if (splittedString(seat[j])[0].equals("X")) {
                    int busSeat = Integer.parseInt(splittedString(seat[j])[4]);
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
     * @param dateOfBirth Date of birth of the customer.
     * @param choice Seat choice.
     * @return `true` if valid details and booked seat, otherwise `false`.
     */
    public static boolean customerDetails(String firstName, String lastName, String dateOfBirth, int choice) {
        if (!(dateOfBirth.length() < 8)) {
            String details = bookedSeatDetailsFormatted(firstName, lastName, dateOfBirth, choice);
            bookSeat(details);
            System.out.println("Thank you for the booking, " + getFullName(details));
            System.out.println(formattedCustomerDetails(details));;
            return true;
        }
        System.out.println("Details provided were incorrect.");
        return false;
    }


    /**
     * Books a specific seat in the bus layout.
     * @param seat The seat to be booked.
     */
    public static void bookSeat(String seat) {
        for (int i = 0; i < busSeats.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (busSeats[i][j].equals(getSeat(seat))) {
                    busSeats[i][j] = seat;
                }
            }
        }
    }


    /**
     * Retrieves unbooked window seats from the bus layout.
     * @return An appended string containing unbooked window seats or a message if none are available.
     */
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

    /**
     * Validates and returns the user role.
     * Displays role options (1.Bus Inspector or 2.Customer), takes user input to return boolean values.
     *
     * @return `1` for Bus Inspector, `2` for Customer, '0' to Exit
     */
    public static int validateRole() {
        while (true) {
            System.out.println("0.Exit ");
            System.out.println("1.Bus Inspector ");
            System.out.println("2.Customer ");
            System.out.print("> ");
            try {
                int choice = scanner.nextInt();
                if (choice == 1) {
                    return 1;
                }else if (choice == 2) {
                    return 2;
                }else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Choose a number please.");
            }
        }
        return 0;
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
    public static String bookedSeatDetailsFormatted(String firstName, String lastName, String dateOfBirth, int busNumber) {
        return "X," + firstName + "," + lastName + "," + formatDateOfBirth(dateOfBirth) + "," + busNumber;
    }

    /**
     * Counts the number of available seats in the bus.
     *
     * @return number of available seats.
     */
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


    /**
     * Checks if a seat is booked.
     *
     * @param seat Seat CSV containing booking confirmation.
     * @return Booking status of the seat.
     */
    public static String bookedSeatCheck(String seat) {
        return splittedString(seat)[0];
    }

    /**
     * Extracts full name from seat String CSV .
     *
     * @param seat Seat CSV containing name information.
     * @return Concatenated first name and last name.
     */
    public static String getFullName(String seat) {
        return splittedString(seat)[1] + " " + splittedString(seat)[2];
    }

    /**
     * Checks the age based on the birth year extracted from a csv seat details String.
     *
     * If seat has more than 2 characters, it extracts the birth year from the
     * seat information using the splittedString() method and calculates age.
     *
     * @param seat The seat representation containing CSV formatted details.
     * @return 0 if the calculated age is 18 or older, 1 if it's younger than 18, and 2 if unable to determine.
     */
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
     * Takes in a CSV formatted String and calls the method splitString() for the array, then fetches the booked bus seat number.
     * @param seat CSV formatted String containing seat information.
     * @return Returns the booked seat number.
     * */
    public static String getSeat(String seat) {
        return splittedString(seat)[4];
    }

    /**
     * Takes in a string that is formatted in CSV to extract the date of birth (YYYYMMDD) and reformat it to (YYYY-MM-DD).
     * @param dateOfBirth CSV formatted details about the customer.
     * @return Returns the reformatted date of birth.
     */
    public static String formatDateOfBirth(String dateOfBirth) {
        return dateOfBirth.substring(0, 4) + "-" +
                dateOfBirth.substring(4, 6) + "-" +
                dateOfBirth.substring(6, 8);
    }

    /**
     * Prints full name, birthdate, and seat number formatted from customer details extracted from CSV string
     * @param csvString CSV string containing customer details.
     * @return Formatted customer details in an order.
     */
    public static String formattedCustomerDetails(String csvString) {
        return "Name: " + getFullName(csvString) +
                "\nBirthdate: "  + splittedString(csvString)[3]+
                "\nSeat: " + getSeat(csvString);
    }

    /**
     * Recursively calculates the profit within a 2D array using the currentAgeCheck() function.
     * @param row The current row index, starting from 0 up to array.length.
     * @param column The current column index within the row, accessed through the 2D array.
     * @param profit The total profit calculated so far.
     * @return The calculated profit as a double value.
     */
    public static double getTotalProfit(int row, int column, double profit) {
        if (row >= busSeats.length) {
            return profit;
        }

        if (column > 3) {
            return getTotalProfit(row + 1, 0, profit);
        }

        if (currentAgeCheck(busSeats[row][column]) == 0) {
            return getTotalProfit(row, column + 1, profit + 149.90);
        }else if (currentAgeCheck(busSeats[row][column]) == 1){
            getTotalProfit(row, column + 1, profit + 299.90);
        }

        return getTotalProfit(row, column + 1, profit);
    }

    /**
     * Splits a comma-separated value (CSV) formatted string into an array of individual values.
     * @param csvSeatDetails The input string in CSV format.
     * @return An array containing the individual values split from the CSV string.
     */
    public static String[] splittedString(String csvSeatDetails) {
        return csvSeatDetails.split(",");
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
     * Prints the arranged bus seats by iterating through each row and column in the static 2D bus seats array.
     * <pre>
     *     This method prints a new line at the beginning of each row iteration and invokes the printFormattedBusSeats() method for each value in the 2D array.
     * </pre>
     * After the second loop completes, the printWheels() method is called.
     */
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
     * Converts a seat string, which can be either a number or 'X', into a formatted string.
     * <pre>
     *     The formatted string is printed using System.out.printf() with a minimum width of 2 spaces.
     * </pre>
     * @param seat The seat representation to be converted and formatted.
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
     * <pre>
     *     Prints out the wheels at specific rows in the arranged bus seats.
     * </pre>
     * <pre>
     *     If the provided row index is 0, the wheels are printed after the first row.
     *     If the row index is 3, the wheels are printed before the last row.
     * </pre>
     * @param row The current row index in the 2D seats array.
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