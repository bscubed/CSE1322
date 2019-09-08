/***************************\
| Class: CSE 1322           |
| Section: 01               |
| Term: Fall 2019           |
| Instructor: Dexter Howard |
| Name: Brendan Szymanski   |
| Assignment #: 1B          |
\***************************/
public class Tests {

    // I didn't include averageScore and finalGrade as variables since they are dynamically changing depending on
    // the contents of the scores array. It would make more sense to simply return the calculated result from a method
    // instead of change the averageScore and finalGrade variables every time the scores array is modified.
    private String firstName;
    private String lastName;
    private double[] scores;

    /**
     * Initializes a new <code>Tests</code> object with the input parameters firstName and lastName, and a default
     * array of scores with a size of 5.
     * Postconditions: A new Tests object has been initialized with correct values.
     *
     * @param firstName The first name of the student.
     * @param lastName  The last name of the student
     */
    public Tests(String firstName, String lastName) {
        this(firstName, lastName, new double[5]);
    }

    /**
     * Initializes a new <code>Tests</code> object with the input parameters firstName, lastName, and an existing array
     * of scores.
     * Preconditions: The scores object has been initialized and is greater than zero
     * Postconditions: A new Tests object has been initialized with correct values
     *
     * @param firstName The first name of the student
     * @param lastName  The last name of the student
     * @param scores    Existing test scores
     */
    public Tests(String firstName, String lastName, double... scores) {
        if (scores == null || scores.length == 0) throw new EmptyTestException();
        for (double score : scores) if (score < 0 || score > 100) throw new IllegalGradeException();
        this.firstName = firstName;
        this.lastName = lastName;
        this.scores = scores;
    }

    /* Mutators */

    /**
     * Changes the first name of the Tests object
     *
     * @param firstName The new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Changes the last name of the Tests object
     *
     * @param lastName The new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Changes the score of a specific test
     * Preconditions: The new score is between 0 and 100 and the test index is between 0 and scores.length - 1
     *
     * @param index The index of the test's score to be changed
     * @param score The new score
     */
    public void setScore(int index, double score) {
        // If score or index is invalid, an exception is thrown.
        if (score < 0 || score > 100) throw new IllegalGradeException();
        if (index < 0 || index >= scores.length) throw new InvalidTestException(scores.length);
        scores[index] = score;
    }

    /**
     * Changes all scores to an array of new scores
     * Preconditions: The scores array contains only valid scores (0 - 100)
     *
     * @param scores The new array of scores
     */
    public void setScores(double[] scores) {
        // If the scores array contains an invalid score, an exception is thrown.
        for (double score : scores) if (score < 0 || score > 100) throw new IllegalGradeException();
        this.scores = scores;
    }

    /* Accessors */

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the score of a test at specified index
     * Preconditions: The index is a valid index between 0 and scores.length - 1
     *
     * @param index The index of the test score to be returned
     * @return The score at specified index
     */
    public double getScore(int index) {
        // If the index is invalid, an exception is thrown.
        if (index < 0 || index >= scores.length) throw new InvalidTestException(scores.length);
        return scores[index];
    }

    public double[] getScores() {
        return scores;
    }

    /* Instance methods */

    /**
     * Sums up all scores stored in the current <code>Tests</code> object and divides the sum
     * by the length of scores
     *
     * @return A double value representing the average test score.
     */
    public double getAverageScore() {
        double total = 0;
        for (double score : scores) {
            total += score;
        }
        return total / scores.length;
    }

    /**
     * Returns the average test score in the form of a letter grade.
     *
     * @return The letter grade of the <code>Tests</code> object based on their average scores.
     */
    public char getFinalGrade() {
        // Dividing the average score by 10 and casting to an int results in even intervals
        // of 10, perfect for determining letter grades.
        switch ((int) getAverageScore() / 10) {
            case 10:
            case 9: // 90-100
                return 'A';
            case 8: // 80-89
                return 'B';
            case 7: // 70-79
                return 'C';
            case 6: // 60-69
                return 'D';
            default: // 0-59
                return 'F';
        }
    }

    /**
     * Converts a Tests object to a printable String in the format:
     * [First Name] [Last Name] [Test 1] [Test 2] ... [Test N] [Average] [Final Grade]
     * where N is the amount of tests contained in the scores array.
     *
     * @return The Tests object converted to a human-readable String.
     */
    @Override
    public String toString() {
        // Use StringBuilder as string concatenation in a for loop is memory inefficient
        StringBuilder output = new StringBuilder(firstName + "\t" + lastName + "\t");
        for (double score : scores) {
            output.append(score).append("\t");
        }
        output.append(getAverageScore()).append("\t");
        output.append(getFinalGrade());
        return output.toString();
    }
}

/**
 * An exception to be thrown when an empty array of tests is attempted to be set to a Tests object.
 */
class EmptyTestException extends RuntimeException {
    @Override
    public String getMessage() {
        return "A test object must contain an initialized, non-empty array of scores.";
    }
}

/**
 * An exception to be thrown when an invalid grade is attempted to be added to the array of scores in a Tests object.
 */
class IllegalGradeException extends IllegalArgumentException {
    @Override
    public String getMessage() {
        return "Grades must be between 0 and 100.";
    }
}

/**
 * An exception to be thrown when an invalid test is selected from an array of scores. Similar to an
 * IndexOutOfBoundsException.
 */
class InvalidTestException extends IndexOutOfBoundsException {
    private int numTests;

    InvalidTestException(int numTests) {
        this.numTests = numTests;
    }

    @Override
    public String getMessage() {
        return "Test number must be between 0 and " + (numTests - 1) + ".";
    }
}
