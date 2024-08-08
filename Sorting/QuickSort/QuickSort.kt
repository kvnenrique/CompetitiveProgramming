import java.util.Scanner;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

fun main (){
    // Read input array from file
    val input = Scanner(System.`in`);
    println("Enter the name of the file containing the array:");
    val sourceFileName = input.nextLine();
    val arr = readArrayFromFile(sourceFileName);

    println("Original array:");
    printArray(arr);
    quickSort(arr, 0, arr.size-1);
    println("Sorted array:");
    printArray(arr);
}

fun quickSort (arr: Array<Int>, low: Int, high: Int){
    if (low < high){
        val pivotIndex = partition(arr, low, high);
        quickSort(arr, low, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, high)
    }
}

fun partition (arr: Array<Int>, low: Int, high: Int): Int{
    // The partition process puts elements that are smaller
    // than pivot on the left, the elements that are greater
    // than pivot on the right and the pivot betweeen them.

    val pivot = arr[high];

    // lastSmallerIndex will save the position of the last element
    // that is smaller than pivot, so pivot will end up after
    // last smaller and elements greater than pivot will end
    // up after the pivot.

    // Note that at the beginning there are not any element smaller
    // than the pivot, so the position of the last element smaller
    // than pivot is at the left side of the pivot (which would ended
    // up at the firt position of the array in this case).

    var lastSmallerIndex = low - 1;

    for (j in low .. high - 1){
        if (arr[j] < pivot){
            lastSmallerIndex ++;

            // swap (arr[j], arr[lastSmallerIndex])
            var aux = arr[lastSmallerIndex];
            arr[lastSmallerIndex] = arr[j];
            arr[j] = aux;
        }
    }

    // Put the pivot at its place
    val pivotIndex = lastSmallerIndex + 1;
    var aux = arr[pivotIndex];
    arr[pivotIndex] = pivot;
    arr[high] = aux;

    // Return the pivot index
    return pivotIndex;
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