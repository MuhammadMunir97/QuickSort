import java.io.File;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        if (args.length != 4)
        {
            throw new IllegalArgumentException("Please provide 4 appropriate arguments");
        }

        int arraySize = Integer.parseInt(args[0]);

        try {
            File report = new File(args[1]);
            File unsortedArray = new File(args[2]);
            File sortedArray = new File(args[3]);

            report.createNewFile();
            unsortedArray.createNewFile();
            sortedArray.createNewFile();

            PrintWriter reportFileWriter = new PrintWriter(report);
            PrintWriter unsortedArrayFileWriter = new PrintWriter(unsortedArray);
            PrintWriter sortedArrayFileWriter = new PrintWriter(sortedArray);

            ArrayList<Integer> list = QuickSorter.generateRandomList(arraySize);
            unsortedArrayFileWriter.println(list);
            unsortedArrayFileWriter.close();

            // Deep Copy the array for later use
            ArrayList<Integer> listCopy1 = new ArrayList<Integer> (list);
            ArrayList<Integer> listCopy2 = new ArrayList<Integer> (list);
            ArrayList<Integer> listCopy3 = new ArrayList<Integer> (list);
            ArrayList<Integer> listCopy4 = new ArrayList<Integer> (list);

            Duration firstElementDuration,
                    randomElementDuration,
                    medianOfThreeRandomElementsDuration,
                    medianOfThreeElementsDuration;
            
            firstElementDuration = QuickSorter.timedQuickSort(listCopy1, QuickSorter.PivotStrategy.FIRST_ELEMENT);
            randomElementDuration = QuickSorter.timedQuickSort(listCopy2, QuickSorter.PivotStrategy.RANDOM_ELEMENT);
            medianOfThreeRandomElementsDuration = QuickSorter.timedQuickSort(listCopy3, QuickSorter.PivotStrategy.MEDIAN_OF_THREE_RANDOM_ELEMENTS);
            medianOfThreeElementsDuration = QuickSorter.timedQuickSort(listCopy4, QuickSorter.PivotStrategy.MEDIAN_OF_THREE_ELEMENTS);

            sortedArrayFileWriter.println(listCopy1);
            sortedArrayFileWriter.close();

            reportFileWriter.println("Array Size = " + arraySize);
            reportFileWriter.println("FIRST_ELEMENT = " + firstElementDuration);
            reportFileWriter.println("RANDOM_ELEMENT = " + randomElementDuration);
            reportFileWriter.println("MEDIAN_OF_THREE_RANDOM_ELEMENTS = " + medianOfThreeRandomElementsDuration);
            reportFileWriter.println("MEDIAN_OF_THREE_ELEMENTS = " + medianOfThreeElementsDuration);
            reportFileWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    } 
}
