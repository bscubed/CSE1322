/***************************\
| Class: CSE 1322           |
| Section: 01               |
| Term: Fall 2019           |
| Instructor: Dexter Howard |
| Name: Brendan Szymanski   |
| Assignment #: 1B          |
\***************************/
import java.util.ArrayList;
import java.util.Scanner;

public class TestsMain {

    // A collection of valid inputs to handle when parsing input.
    enum InputType {
        SAMPLE_DATA,
        STUDENT_ENTRY,
        NAME_ENTRY
    }

    private static ArrayList<Tests> students; // List of students to store grade data in
    private static Scanner scanner;           // Scanner object to handle user input

    // A sample array of students for easy testing and debugging. You can use this if you wanna grade my project the
    // easy way.
    private static Tests[] sampleArray = {
            new Tests("Jack", "Johnson", 85, 83, 77, 91, 76),
            new Tests("Lisa", "Aniston", 80, 90, 95, 93, 48),
            new Tests("Andy", "Cooper", 78, 81, 11, 90, 73),
            new Tests("Ravi", "Gupta", 92, 83, 30, 69, 87),
            new Tests("Bonny", "Blair", 23, 45, 96, 38, 59),
            new Tests("Danny", "Clark", 60, 85, 45, 39, 67),
            new Tests("Samantha", "Kennedy", 77, 31, 52, 74, 83),
            new Tests("Robin", "Bronson", 93, 94, 89, 77, 97),
            new Tests("Sun", "Xie", 79, 85, 28, 93, 82),
            new Tests("Jack", "Johnson", 85, 72, 49, 75, 63)
    };

    public static void main(String[] args) {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);

        System.out.println("Would you like to use the sample data or enter your own?");
        System.out.println("\t1) Use sample data");
        System.out.println("\t2) Enter new data");
        do {
            // Prompts user for input. If user input is invalid, the parseInput function will return true, causing the
            // do-while loop to prompt the user for input again until a valid input is received.
            System.out.print("Select an option (1-2): ");
        } while (parseInput(InputType.SAMPLE_DATA, scanner.nextLine()));
    }

    /**
     * Parses user input received while inside of a while or do-while loop. This method parses the input string
     * differently depending on the InputType received as a parameter, handling each case accordingly. This method will
     * return true or false depending on whether the loop should continue or not.
     *
     * @param type  The situation to parse input for
     * @param input String to parse
     * @return Whether or not the loop calling this method should continue or not
     */
    private static boolean parseInput(InputType type, String input) {
        switch (type) {
            // User is currently being asked if they want to use sample data or enter new data
            case SAMPLE_DATA:
                try {
                    int selection = Integer.parseInt(input);
                    switch (selection) {
                        case 1: // If selection is 1, print the sample array
                            printClass(sampleArray);
                            return false;
                        case 2: // If selection is 2, user will enter their own data
                            enterData();
                            return false;
                        default: // Invalid input
                            System.out.println("Invalid input. Please try again.");
                            return true;
                    }
                } catch (NumberFormatException e) {
                    // If user entered something other than a number
                    System.out.println("Invalid input. Please try again.");
                    return true;
                }
                // User is currently being asked if they'd like to enter a new student's grades
            case STUDENT_ENTRY:
                if (input.equalsIgnoreCase("y")) {
                    newStudent();
                    return true;
                } else if (input.equalsIgnoreCase("n")) {
                    return false;
                } else {
                    // This scanner.nextLine is necessary to avoid a weird bug involving scanner where even before the
                    // user can type "y" or "n", "Invalid input. Please try again." is printed to console.
                    scanner.nextLine();
                    System.out.println("Invalid input. Please try again.");
                    return true;
                }
                // User is currently being asked to enter a name. If name entered is empty, user will be prompted to try again
            case NAME_ENTRY:
                if (input.isEmpty()) {
                    System.out.println("Invalid input. Please try again.");
                    return true;
                } else {
                    return false;
                }
            default:
                System.out.println("This should technically never print since only valid InputType enum objects" +
                        " can be passed into this method, so if you somehow end up seeing this message, good job on" +
                        " messing up my beautiful program.");
                return false;
        }
    }

    /**
     * Prompts the user to enter the data for a new student's test scores. The user can enter as many students as they
     * please, and when they're done, all students' scores and averages are printed to the console.
     */
    private static void enterData() {
        newStudent();
        do {
            System.out.print("Would you like to enter another student? (y/n): ");
        } while (parseInput(InputType.STUDENT_ENTRY, scanner.nextLine()));
        printClass(students.toArray(Tests[]::new));
    }

    /**
     * Prompts the user to enter data and test scores for a new student, then saves the student's scores to an array
     * of students. Data prompted includes first name, last name, and 5 test scores.
     */
    private static void newStudent() {
        String firstName, lastName = "";
        do {
            System.out.print("Enter student's first name: ");
            firstName = scanner.nextLine();
        } while (parseInput(InputType.NAME_ENTRY, firstName));
        do {
            System.out.print("Enter student's last name: ");
            lastName = scanner.nextLine();
        } while (parseInput(InputType.NAME_ENTRY, lastName));
        Tests student = new Tests(firstName, lastName);

        for (int i = 0; i < student.getScores().length; i++) {
            try {
                System.out.print("Enter score for test " + (i + 1) + ": ");
                // Instead of doing scanner.nextDouble, using scanner.nextLine() avoids an infinite loop due to the fact
                // that a Scanner object only advances when a double is received.
                double score = Double.parseDouble(scanner.nextLine());
                student.setScore(i, score);
            } catch (IllegalGradeException e) {
                // If user inputs a grade less than 0 or greater than 100, an error is thrown and the user is prompted
                // to try again.
                System.out.println(e.getMessage());
                i--;
            } catch (NumberFormatException e) {
                // If the user doesn't enter a number, an error is thrown, and the user is prompted to try again.
                System.out.println("Invalid input. Please try again.");
                i--;
            }
        }
        students.add(student);
    }

    /**
     * Prints an array of Tests in a table-like format along with the average student score.
     *
     * @param students The array of students to print
     */
    private static void printClass(final Tests[] students) {
        System.out.println("First Name\tLast Name\tTest1\tTest2\tTest3\tTest4\tTest5\tAverage\tGrade");
        double classTotal = 0;
        for (Tests student : students) {
            classTotal += student.getAverageScore();
            System.out.println(student);
        }
        System.out.println("\nClass Average = " + (classTotal / students.length));
    }
}
