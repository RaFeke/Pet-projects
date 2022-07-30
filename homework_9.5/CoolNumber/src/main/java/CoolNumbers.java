import java.util.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;


public class CoolNumbers {

    public static List<String> generateCoolNumbers() {

        TreeSet<String> setCoolNumbers = new TreeSet<>();
        List<String> listChar = new ArrayList<>();
        listChar.add("А");
        listChar.add("В");
        listChar.add("Е");
        listChar.add("К");
        listChar.add("М");
        listChar.add("Н");
        listChar.add("О");
        listChar.add("Р");
        listChar.add("С");
        listChar.add("Т");
        listChar.add("У");
        listChar.add("Х");

        for (; setCoolNumbers.size() < 2000000; ) {
            StringBuilder buildCoolNumber = new StringBuilder();
            buildCoolNumber.append(listChar.get((int) (Math.random() * 12) + 0));
            int number = (int) (Math.random() * 9) + 1;
            buildCoolNumber.append(number);
            buildCoolNumber.append(number);
            buildCoolNumber.append(number);
            buildCoolNumber.append(listChar.get((int) (Math.random() * 12) + 0));
            buildCoolNumber.append(listChar.get((int) (Math.random() * 12) + 0));
            int numberRegion = (int) (Math.random() * 200) + 1;
            StringBuilder stringBuilderRegion = new StringBuilder();
            if (numberRegion < 10) {
                stringBuilderRegion.append("0");
                stringBuilderRegion.append(numberRegion);
            } else {
                stringBuilderRegion.append(numberRegion);
            }
            buildCoolNumber.append(stringBuilderRegion);
            setCoolNumbers.add(buildCoolNumber.toString());
        }

        return new ArrayList<>(setCoolNumbers);
    }

    public static boolean bruteForceSearchInList(List<String> list, String number) {
        return list.contains(number);
    }

    public static boolean binarySearchInList(List<String> sortedList, String number) {
        if (Collections.binarySearch(sortedList, number) < 0){
            return false;
        }
        return true;
    }

    public static boolean searchInHashSet(HashSet<String> hashSet, String number) {
        return hashSet.contains(number);
    }

    public static boolean searchInTreeSet(TreeSet<String> treeSet, String number) {

        return treeSet.contains(number);
    }
}

