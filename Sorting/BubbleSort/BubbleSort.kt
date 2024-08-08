import java.util.Scanner;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

fun main (){
    // Read the input array
    val input = Scanner(System.`in`);
    println("Enter the name of file containing the array:");
    val sourceFileName = input.nextLine();
    var numbers = readArrayFromFile(sourceFileName);

    // Show the original arraay
    println("Original array:");
    printArray(numbers);

    // Order the array
    numbers = bubbleSort(numbers);

    // Show the ordered array
    println("Sorted array:");
    printArray(numbers);
}

fun bubbleSort (numbers: Array<Int>): Array<Int>{
    var i = 0;
    while (i < numbers.size - 1){
        var j = 0;
        /*
         * Note that on the first iteration the largest element
         * goes to the last position of the array, for the next
         * iteration we will have to compare only n-1 elements, 
         * and so on. Thats why the while condition compares: 
         * j < numbers.size - 1 - i
         * */
        while (j < numbers.size - 1 - i){
            if (numbers[j] > numbers[j+1]){
                var aux = numbers[j];
                numbers[j] = numbers[j+1];
                numbers[j+1] = aux;
            }
            j++;
        }
        i++;
    }

    return numbers;
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