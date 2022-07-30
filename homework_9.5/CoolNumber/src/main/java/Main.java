import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<String> collNumber = CoolNumbers.generateCoolNumbers();

        //>========================================================================

        CoolNumbers.binarySearchInList(collNumber, "Н555НН194");
        long startTime3 = System.nanoTime();
        collNumber.contains("Н555НН194");
        long endTime3 = System.nanoTime();
        System.out.println("Поиск бинарным способом: номер найден " + (endTime3-startTime3) + " ms");

        //>==========================================================================

        CoolNumbers.searchInHashSet(new HashSet<>(collNumber), "Х555ХХ199");
        long startTime = System.nanoTime();
        collNumber.contains("Х555ХХ199");
        long endTime = System.nanoTime();
        System.out.println("Поиск в HashSet: номер найден " + (endTime-startTime) + " ms");


        //>==========================================================================

        CoolNumbers.bruteForceSearchInList(collNumber, "О555ОО177");
        long startTime1 = System.nanoTime();
        collNumber.contains("О555ОО177");
        long endTime1 = System.nanoTime();
        System.out.println("Поиск перебором: номер найден " + (endTime1-startTime1) + " ms");

        //>==========================================================================

        CoolNumbers.searchInTreeSet(new TreeSet<>(collNumber), "М555ММ177");
        long startTime2 = System.nanoTime();
        collNumber.contains("М555ММ177");
        long endTime2 = System.nanoTime();
        System.out.println("Поиск в TreeSet: номер найден " + (endTime2-startTime2) + " ms");
    }
}

