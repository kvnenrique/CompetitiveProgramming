import java.util.Scanner;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class QuickSort {
    public static void main (String args[]){
        // Read the input array
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of file containing the array:");
        String fileDirectory = scanner.nextLine();
        int[] arr = readArrayFromFile(fileDirectory);
 
        // Show the original array
        System.out.println("Original array:");
        printArray(arr);

        // Order the array
        quickSort(arr, 0, arr.length - 1);

        // Show sorted array
        System.out.println("Sorted array:");
        printArray(arr);
    }


    public static void quickSort (int[] arr, int low, int high){
        if (low < high){
            int pivotIndex = partition(arr, low, high);

            quickSort(arr, low, pivotIndex-1);
            quickSort(arr, pivotIndex+1, high);
        }
    }

    public static int partition (int[] arr, int low, int high){
        // The partition process puts elements that are smaller
        // than pivot on the left, the elements that are greater
        // than pivot on the right and the pivot betweeen them.
        int pivot = arr[high];

        // lastSmallerIndex will save the position of the last element
        // that is smaller than pivot, so pivot will end up after
        // last smaller and elements greater than pivot will end
        // up after the pivot.

        // Note that at the beginning there are not any element smaller
        // than the pivot, so the position of the last element smaller
        // than pivot is at the left side of the pivot (which would ended
        // up at the firt position of the array in this case).

        int lastSmallerIndex = low-1;

        for (int j = low; j < high; j++){
            if (arr[j] < pivot){
                // There is another elemet smaller than the pivot
                lastSmallerIndex ++;

                // swap (arr[lastSmallerIndex], arr[j])
                int aux = arr[lastSmallerIndex];
                arr[lastSmallerIndex] = arr[j];
                arr[j] = aux;
            }
        }

        // Put the pivot on its place
        int pivotIndex = lastSmallerIndex + 1;
        int aux = arr[pivotIndex];
        arr[pivotIndex] = pivot;
        arr[high] = aux;

        // Return the pivot index
        return pivotIndex;
    }




    /*************************************
     * Tools                             *
     *************************************/
    private static void printArray (int[] arr){
        System.out.print("[");

        for (int i = 0; i < arr.length - 1; i++){
            System.out.print(String.valueOf(arr[i]) + ", ");
        }
        if (arr.length > 0) System.out.print(arr[arr.length - 1]);

        System.out.println("]");
    }

    private static int[] readArrayFromFile (String fileDirectory){
        File file = new File(fileDirectory);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException ex){
            throw new RuntimeException("File was not found");
        }


        // Count the number of lines
        long lines = -1;
        LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
        // skipt to the last line
        try {
            lineNumberReader.skip(Long.MAX_VALUE);
        } catch (Exception ex){
            throw new RuntimeException("IOException using LineNumberRader.skip");
        }
        lines = lineNumberReader.getLineNumber() + 1;
        

        // Read all lines in file to fill the array
        // Each line contains a number
        int[] arr = new int[(int)lines];
        try {
            // Restart the file reader state to use it again
            fileReader = new FileReader(file);
        } catch (FileNotFoundException ex){
            throw new RuntimeException("File was not found");
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        for (int i=0; i < lines; i++){
            String line = "";
            try {
                line = bufferedReader.readLine();
            } catch (IOException ex){
                throw new RuntimeException("Error while reading lines of file");
            }
            arr[i] = Integer.parseInt(line);
        }

        return arr;
    }
}