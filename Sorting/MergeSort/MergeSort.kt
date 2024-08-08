import java.util.Scanner;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;


// The merge sort is a sorting algorithm that follows the "divide and conquer" aproach.
// It works by recursively dividing the input into smaller subarrays and then merging
// them bak together to get the sorted array. 
//
// In simple terms, the process of merge sort is to divide the array into two halves, 
// sort wach of them and then merge sorted halves back together.

fun main (){
    // Read input array from file
    val input = Scanner(System.`in`);
    println("Enter the name of the file containing the array:");
    val sourceFileName = input.nextLine();
    val arr = readArrayFromFile(sourceFileName);

    // Show the original array
    println("Original array:");
    printArray(arr);

    // Sort the array
    mergeSort(arr, 0, arr.size-1);

    // Show sorted array
    println("Sorted array:");
    printArray(arr);
}

fun mergeSort (arr: Array<Int>, low: Int, high: Int){
    if (low < high){
        val mid = (low + high) / 2;
        
        mergeSort(arr, low, mid);
        mergeSort(arr, mid + 1, high);

        // Merge two sorted halves
        merge (arr, low, high, mid)
    }
}

fun merge (arr: Array<Int>, low: Int, high: Int, mid: Int){
    // Create two temporal arrays to merge them
    val leftArrSize = mid - low + 1;
    val rightArrSize = high - mid;

    val leftArr = Array<Int>(leftArrSize){0};
    val rightArr = Array<Int>(rightArrSize){0};

    // Copy values from original array to left array
    for (i in 0 .. leftArr.size - 1){
        leftArr[i] = arr[low + i];
    }
    // Copy values from original array to right array
    for (i in 0 .. rightArr.size - 1){
        rightArr[i] = arr[mid + 1 + i];
    }

    // Merge two sorted halves into the original array
    var l = 0; // Index for the left array
    var r = 0; // Index for the right array
    var i = low; // Index for the original array

    while (l < leftArr.size && r < rightArr.size){
        if (leftArr[l] < rightArr[r]){
            arr[i] = leftArr[l];
            l++;
        } else {
            arr[i] = rightArr[r];
            r++;
        }
        i++;
    }
    while (l < leftArr.size){
        arr[i] = leftArr[l];
        l++;
        i++;
    }
    while (r < rightArr.size){
        arr[i] = rightArr[r];
        r++;
        i++;
    }
}




/*****************************
 * Utils                     *
 *****************************/
fun readArrayFromFile (fileDirectory: String): Array<Int>{
    val file = File(fileDirectory);
    var fileReader: FileReader?;

    try {
        fileReader = FileReader(file);
    } catch (ex: FileNotFoundException){
        throw RuntimeException("File was not found");
    }


    // Count the number of lines
    var lines: Int;
    val lineNumberReader = LineNumberReader(fileReader);
    // skip to the last line
    try {
        lineNumberReader.skip(Long.MAX_VALUE);
    } catch (ex: IOException){
        throw RuntimeException("IOException while using LineNumberReader");
    }
    lines = lineNumberReader.getLineNumber() + 1;

    // Read all lines in file to fill the array
    // Each line contains a number
    val arr = Array<Int>(lines){0}

    try {
        // Restart the file reader state to use it again
        fileReader = FileReader(file);
    } catch (ex: FileNotFoundException){
        throw RuntimeException("File was not found");
    }

    val bufferedReader = BufferedReader(fileReader);

    for (i in 0 .. lines-1){
        var line: String?;
        try {
            line = bufferedReader.readLine();
        } catch (ex: IOException){
            throw RuntimeException("Error while reading lines of file");
        }
        arr[i] = line.toInt();
    }

    return arr;
}

fun printArray (array: Array<Int>){
    print("[");

    // For the first n-1 elements print a comma after the number
    for (i in 0 .. array.size - 2){
        print(array[i].toString());
        print(", ");
    }
    // Print the last number without comma (if there is a last number)
    if (array.size > 0) print(array[array.size - 1].toString());

    println("]");
}