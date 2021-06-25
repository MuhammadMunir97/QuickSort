import java.time.*;
import java.util.*;

public class QuickSorter
{
    // Make the class constructor private
    private QuickSorter () 
    {

    }

    public enum PivotStrategy
    {
        FIRST_ELEMENT,
        RANDOM_ELEMENT,
        MEDIAN_OF_THREE_RANDOM_ELEMENTS,
        MEDIAN_OF_THREE_ELEMENTS
    }

    public static <E extends Comparable<E>> Duration timedQuickSort (ArrayList<E> list, PivotStrategy pivot) throws NullPointerException
    {
        if (list.size() == 0 || list == null)
        {
            throw new NullPointerException("Invalid List Provided");
        }

        long start = System.nanoTime();
    
        // If size of the list is less than 20 do insertion sort
        if (list.size() <= 20)
        {
            insertionSort (list);
            long finish = System.nanoTime();
            return Duration.ofNanos(finish - start);
        }

        switch (pivot)
        {
            case FIRST_ELEMENT:
                sortByFirstElement(list, 0, list.size() - 1);
                break;
            case RANDOM_ELEMENT:
                sortByRandomElement(list, 0, list.size() - 1);
                break;
            case MEDIAN_OF_THREE_RANDOM_ELEMENTS:
                sortByMedianOfThreeRandomElement(list, 0, list.size() - 1);
                break;
            case MEDIAN_OF_THREE_ELEMENTS:
                sortByMedianOfThreeElement(list, 0, list.size() - 1);
                break;
            default:
                throw new NullPointerException("Invalid Pivot Provided");
        }

        long finish = System.nanoTime();
        return Duration.ofNanos(finish - start);
    }

    private static <E extends Comparable<E>> void sortByFirstElement (ArrayList<E> list, int low, int high) 
    {
        if (high < low) 
        {
            return;
        }

        swap(list, low, high);
        int pivot = quickSort(list, low, high);
        sortByFirstElement (list, low, pivot - 1);
        sortByFirstElement (list, pivot + 1, high);
    }

    private static <E extends Comparable<E>> void sortByRandomElement (ArrayList<E> list, int low, int high) 
    {
        if (high < low) 
        {
            return;
        }

        int random = getRandomNumber (low, high);
        swap (list, random, high);
        
        int pivot = quickSort(list, low, high);
        sortByRandomElement (list, low, pivot - 1);
        sortByRandomElement (list, pivot + 1, high);
    }

    private static <E extends Comparable<E>> void sortByMedianOfThreeRandomElement (ArrayList<E> list, int low, int high) 
    {
        if (high < low) 
        {
            return;
        }

        int randomA = getRandomNumber (low, high);
        int randomB = getRandomNumber (low, high);
        int randomC = getRandomNumber (low, high);

        int median = median (list, randomA, randomB, randomC);
        swap (list, median, high);
        
        int pivot = quickSort(list, low, high);
        sortByMedianOfThreeRandomElement (list, low, pivot - 1);
        sortByMedianOfThreeRandomElement (list, pivot + 1, high);
    }

    private static <E extends Comparable<E>> void sortByMedianOfThreeElement (ArrayList<E> list, int low, int high) 
    {
        if (high < low) 
        {
            return;
        }

        // median of first, last and center
        int median = median (list, low, high, (low + high) / 2);
        swap (list, median, high);
        
        int pivot = quickSort(list, low, high);
        sortByMedianOfThreeElement (list, low, pivot - 1);
        sortByMedianOfThreeElement (list, pivot + 1, high);
    }

    private static <E extends Comparable<E>> int median (ArrayList<E> list, int a, int b, int c)
    {
        boolean isAGreaterThanB = list.get(a).compareTo(list.get(b)) >= 0;
        boolean isAGreaterThanC = list.get(a).compareTo(list.get(c)) >= 0;
        boolean isBGreaterThanC = list.get(b).compareTo(list.get(c)) >= 0;

        boolean isBTheMedian = (isAGreaterThanB && isBGreaterThanC) 
                            || (!isAGreaterThanB && !isBGreaterThanC);

        boolean isCTheMedian = (isAGreaterThanC && !isBGreaterThanC) 
                            || (!isAGreaterThanC && isBGreaterThanC);

        if (isBTheMedian)
        {
            return b;
        }

        if (isCTheMedian)
        {
            return c;
        }

        return a;
    }

    private static <E extends Comparable<E>> void swap (ArrayList<E> list, int i, int j)
    {
        if (i == j)
        {
            return;
        }

        E temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    private static <E extends Comparable<E>> int quickSort (ArrayList<E> list, int low, int high)
    {
        int i = low - 1;

        E pivot = list.get(high);
        for (int j = low; j < high; j++)
        {
            if (list.get(j).compareTo(pivot) <= 0)
            {
                i++;
                swap(list, i, j);
            }
        }
        i++;

        //put the pivot at it's correct place
        swap(list, i, high);
        return i;
    }

    private static <E extends Comparable<E>> void insertionSort(ArrayList<E> list)
    {
        E key;
        int x = 0;

        for (int i=1; i<list.size(); i++)
        {
            key = list.get(i);
            while (x >=0 && list.get(x).compareTo(key) > 0){
                list.set(x+1,list.get(i));
                x--;
            }
            list.set(x+1,key);
        }
    }

    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static ArrayList<Integer> generateRandomList(int size) throws IllegalArgumentException
    {
        if (size < 0)
        {
            throw new IllegalArgumentException("Invalid size");
        }

        ArrayList<Integer> list = new ArrayList<Integer>(size);
        Random random = new Random();

        for (int i = 0; i < size; i++)
        {
            int num = random.nextInt();
            list.add(num);
        }
        
        return list;
    }
}
