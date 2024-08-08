import java.util.Scanner;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MergeSort {
    // The merge sort is a sorting algorithm that follows the "divide and conquer" aproach.
    // It works by recursively dividing the input into smaller subarrays and then merging
    // them bak together to get the sorted array. 
    //
    // In simple terms, the process of merge sort is to divide the array into two halves, 
    // sort wach of them and then merge sorted halves back together.

    public static void main (String[] args){
        // Read the input array
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of file containing the array:");
        String fileDirectory = scanner.nextLine();
        int[] arr = readArrayFromFile(fileDirectory);
        
        // show the original array
        System.out.println("Origninal array:");
        printArray(arr);
        
        // Order the array
        mergeSort(arr, 0, arr.length - 1);
        
        // Show the sorted array
        System.out.println("Sorted array:");
        printArray(arr);
    }

    private static void mergeSort (int[] arr, int low, int high){
        if (low < high){
            int mid = (low + high) / 2;
            
            mergeSort(arr, low, mid);
            mergeSort(arr, mid + 1, high);

            // Merge two sorted halves
            merge(arr, low, high, mid);
        }
    }

    private static void merge (int[] arr, int low, int high, int mid){
        // Create two temporal subarrays to merge them
        int leftArrSize = mid - low + 1;
        int rigthArrSize = high - mid;

        int[] leftArr = new int[leftArrSize];
        int[] rigthArr = new int[rigthArrSize];

        // Copy values from original array to left array    
        for (int i = 0; i < leftArr.length; i++){
            leftArr[i] = arr[low + i];
        }
        // Copy values from original array to rigth array
        for (int i = 0; i < rigthArr.length; i++){
            rigthArr[i] = arr[mid + 1 + i];
        }

        // Now merge tow ordered halves into the original array
        int l = 0; // index for the left array
        int r = 0; // index for the rigth array
        int i = low; // index for the original array
    
        while (l < leftArr.length && r < rigthArr.length){
            if (leftArr[l] < rigthArr[r]){
                arr[i] = leftArr[l];
                l++;
            } else {
                arr[i] = rigthArr[r];
                r++;
            }
            i++;
        }
        while  (l < leftArr.length){
            arr[i] = leftArr[l];
            l++;
            i++;
        }
        while (r < rigthArr.length){
            arr[i] = rigthArr[r];
            r++;
            i++;
        }
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