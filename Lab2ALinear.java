/***************************\
| Class: CSE 1322L          |
| Section: 03               |
| Term: Fall 2019           |
| Instructor: Kevin Markley |
| Name: Brendan Szymanski   |
| Lab #: 1B                 |
\***************************/
import java.util.Random;

public class Lab2ALinear {

    public static void main(String[] args) {
        final int[] nums = {1, 4, 13, 43, -25, 17, 22, -37, 29};

        System.out.println("Index of 4 in array nums: " + linearSearch(4, nums));
        System.out.println("Index of 100 in array nums: " + linearSearch(100, nums));

        final int[] data = new int[20];
        fillArray(data);

        int index = linearSearch(10, data);
        if (index != -1) {
            System.out.println("First instance of 10 in array data: " + index);
        } else {
            System.out.println("Value not found in the array");
        }
    }

    /**
     * Searches an array using a linear search algorithm that has a worst-case efficiency of O(n).
     *
     * @param key   The number to search for
     * @param array The array to be searched
     * @return The index in the parameter array that contains the first instance of the key value. Returns -1 if no
     * value was found in the array
     */
    private static int linearSearch(int key, int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == key) return i;
        }
        return -1;
    }

    /**
     * Fills an array with random values from -100 to 100
     *
     * @param array The array to be filled
     */
    private static void fillArray(int[] array) {
        final int MAX = 100;
        final int MIN = -100;
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = MIN + random.nextInt(MAX - MIN + 1);
        }
    }
}
