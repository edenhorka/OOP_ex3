/**
 * This class measures the run-time performances of different of the following data structures:
 * OpenHashSet, ClosedHashSet, Java’s TreeSet1 ,Java’s LinkedList and Java’s HashSet.
 * @author eden horka
 */

import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

public class SimpleSetPerformanceAnalyzer {

    /**
     * Number of different sets to test
     */
    public static final int NUM_OF_SETS = 5;

    /**
     * First string from data1 to test the contains method.
     */
    public static final String STRING1 = "-13170890158";

    /**
     * Second string from data2 to test the contains method.
     */
    public static final String STRING2 = "23";

    /**
     * Second string from data1 and data2 to test the contains method.
     */
    public static final String STRING3 = "hi";

    /* Number of round to "warm up" analyzer */
    private static final int WARM_UP = 20000;

    /* Number of rounds to analyze time more accurately. */
    private static final int ROUNDS = 1000;

    /*== Indexes of the different sets in the sets array : ==*/
    private static final int OPEN_HASH = 0;

    private static final int CLOSED_HASH = 1;

    private static final int LINKED_LIST = 2;

    private static final int TREE_SET = 3;

    private static final int HASH_SET = 4;

    /* Message to print in case of an invalid input. */
    private static final String INVALID_MSG = "Input is invalid.";

    /* A string represents add method. */
    private static final String ADD = "add";

    /* A string represents contains method. */
    private static final String CONTAINS = "contains";

    /*== represents the data number: ==*/
    private static final int DATA1 = 1;

    private static final int DATA2 = 2;

    /* Array of strings represents the name of the different set types in the sets array. */
    private static final String[] setTypes = {"open hash set", "closed hash set", "linked list", "tree set"
            , "hash set"};

    /*== Sets to test: ==*/
    private static OpenHashSet openHashSet = new OpenHashSet();

    private static ClosedHashSet closedHashSet = new ClosedHashSet();

    private static CollectionFacadeSet linkedList = new CollectionFacadeSet(new LinkedList<String>());

    private static CollectionFacadeSet treeSet = new CollectionFacadeSet(new TreeSet<String>());

    private static CollectionFacadeSet hashSet = new CollectionFacadeSet(new HashSet<String>());

    /* An array of all the sets to test. */
    private static SimpleSet[] sets = {openHashSet, closedHashSet, linkedList, treeSet, hashSet};

    /* An array of stings represents the strings of the first data file. */
    private static String[] data1Array = Ex3Utils.file2array("data1.txt");

    /* An array of stings represents the strings of the second data file. */
    private static String[] data2Array = Ex3Utils.file2array("data2.txt");


    /* Analyzes the add method for a given number set and data, returns the average time.*/
    private static long analyzeAdd(int setNum, String[] data) {
        if (data == null) {
            System.out.println(INVALID_MSG);
            return -1;
        }
        long start;
        long sumOfTimes = 0;
        for(int i=0;i<ROUNDS ; i++) {
            start = System.currentTimeMillis();
            for (String str : data) {
                sets[setNum].add(str);
            }
            sumOfTimes = sumOfTimes +  System.currentTimeMillis() - start ;
        }
        System.out.println(sumOfTimes/ROUNDS);
        return sumOfTimes/ROUNDS;
    }

    /* Analyzes the contains method for a given set and string, returns the average time.*/
    private static long analyzeContains(int setNum, String str) {
        if (str == null) {
            System.out.println(INVALID_MSG);
            return -1;
        }
        long start;
        long sumOfTimes = 0;
        for (int i = 0; i < WARM_UP; i++) {
            sets[setNum].contains(str);
        }
        for (int i = 0; i < ROUNDS; i++) {
            start = System.nanoTime();
            sets[setNum].contains(str);
            sumOfTimes = sumOfTimes + (System.nanoTime() - start);
        }
        System.out.println(sumOfTimes / ROUNDS);
        return sumOfTimes / ROUNDS ;
    }

    private void initializeSets(){
        openHashSet = new OpenHashSet();
        closedHashSet = new ClosedHashSet();
        linkedList = new CollectionFacadeSet(new LinkedList<String>());
        treeSet = new CollectionFacadeSet(new TreeSet<String>());
        hashSet = new CollectionFacadeSet(new HashSet<String>());
        sets = new SimpleSet[]{openHashSet, closedHashSet, linkedList, treeSet, hashSet};
    }

    public void runAnalyzer(String[] data, int dataType, String str1, String str2) {
        for (int i = NUM_OF_SETS -1 ; i>0; i--) {
            System.out.println(setTypes[i]+ " - Testing ADD_DATA" + dataType);
            analyzeAdd(i, data);
            System.out.println(setTypes[i]+ " - Testing CONTAINS " +str1 );
            analyzeContains(i, str1);
            System.out.println(setTypes[i]+ " - Testing CONTAINS " +str2 );
            analyzeContains(i, str2);
            initializeSets();
        }
    }



    public static void main(String[] args) {
        SimpleSetPerformanceAnalyzer analyzer = new SimpleSetPerformanceAnalyzer();
       /* analyzer.runAnalyzer(data1Array,1, STRING1, STRING3);
        analyzer.initializeSets();
        */
        analyzer.runAnalyzer(data2Array,2, STRING2, STRING3);
    }
}




