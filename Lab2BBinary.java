/***************************\
| Class: CSE 1322L          |
| Section: 03               |
| Term: Fall 2019           |
| Instructor: Kevin Markley |
| Name: Brendan Szymanski   |
| Lab #: 1B                 |
\***************************/
import java.util.Random;

public class Lab2BBinary {

    public static void main(String[] args) {
        final int[] nums = {1, 4, 13, 43, -25, 17, 22, -37, 29};
        quickSort(nums);

        System.out.println("Index of 4 in array nums: " + binarySearch(nums, 4));
        System.out.println("Index of 100 in array nums: " + binarySearch(nums, 100));

        final int[] data = new int[20];
        fillArray(data);
        quickSort(data);

        int index = binarySearch(data, 10);
        if (index != -1) {
            System.out.println("First instance of 10 in array data: " + index);
        } else {
            System.out.println("Value not found in the array");
        }
    }

    /**
     * Recursively searches an array using a binary search algorithm which has a worst-case efficiency of O(log(n)).
     * This method provides simpler parameters as an entry point for searches of an entire array.
     *
     * @param array The array to be searched
     * @param key   The number to search for
     * @return The index in the parameter array that contains the first instance of the key value. Returns -1 if no
     * value was found in the array
     */
    private static int binarySearch(int[] array, int key) {
        return binarySearch(array, key, 0, array.length - 1);
    }

    /**
     * Recursively searches an array using a binary search algorithm which has a worst-case efficiency of O(log(n)).
     *
     * @param array The array to be searched
     * @param key   The number to search for
     * @param min   The minimum index the key could be
     * @param max   The maximum index the key could be
     * @return The index in the parameter array that contains the first instance of the key value. Returns -1 if no
     * value was found in the array
     */
    private static int binarySearch(int[] array, int key, int min, int max) {
        if (min > max) return -1; // Base case; key does not exist in the array
        int middle = (min + max) / 2; // Find the middle point between min and max
        if (array[middle] == key) return middle; // We've found the index of our key!
        else if (array[middle] > key) {
            // Key must be between min and middle. Search again
            return binarySearch(array, key, min, middle - 1);
        } else {
            // Key must be between middle and max. Search again
            return binarySearch(array, key, middle + 1, max);
        }
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
            // Since random.nextInt(num) generates a random number from 0 to (num - 1), the formula below ensures a
            // random number in the correct range is being generated.
            array[i] = MIN + random.nextInt(MAX - MIN + 1);
        }
    }

    /**
     * Sorts an array from smallest to largest using the quick sort algorithm. This method uses simpler parameters for
     * searches of an entire array as opposed to a subset of an array.
     *
     * @param array The array to sort
     */
    private static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    /**
     * Sorts an array from smallest to largest using the quick sort algorithm. This method is mainly to be used
     * recursively.
     *
     * @param array The array to sort
     * @param min   Starting index
     * @param max   Ending index
     */
    private static void quickSort(int[] array, int min, int max) {
        if (min < max) {
            int index = partition(array, min, max);
            // Sort elements before and after the partition element recursively
            quickSort(array, min, index - 1);
            quickSort(array, index + 1, max);
        }
        // When min is greater than max, we're done sorting, and the function exits the current recursive branch.
    }

    /**
     * Chooses the value at array[max] as the pivot (the last element between min and max) and places it in its final
     * sorted position while also sorting elements larger or smaller than it after or before it, respectively.
     *
     * @param array The array to partition
     * @param min   Starting index
     * @param max   Ending index
     * @return The new index to partition from next
     */
    private static int partition(int[] array, int min, int max) {
        int pivot = array[max]; // Always use last element as pivot
        int i = (min - 1);
        // Places all values less than the pivot element before it in the array and all values greater than it after it
        // in the array
        for (int j = min; j < max; j++) {
            if (array[j] < pivot) {
                i++;
                // Swaps the lower element with the higher element if out of order in the array
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        // Places the pivot element at the correct place in the array. This is confirmed its final position because all
        // values after the pivot element in the array are greater than it, and all values before the pivot element are
        // less than the pivot
        int temp = array[i + 1];
        array[i + 1] = array[max];
        array[max] = temp;
        // Returns the new index to partition from next
        return i + 1;
    }
}
