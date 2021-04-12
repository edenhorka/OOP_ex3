/**
 * a wrapper class of linked list of strings that contains delegation calls.
 * This class represents a cell in an open hash set.
 * @author eden horka
 */

import java.util.*;


public class Bucket implements SimpleSet {

    public LinkedList<java.lang.String> bucket;

    public Bucket(){
        bucket = new LinkedList<java.lang.String>() ;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(java.lang.String newValue) {
        if ( newValue != null ){
            bucket.push(newValue);
            return true;
        }
        return false;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(java.lang.String searchVal){

        return bucket.contains(searchVal) ;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete - Value to delete.
     * @return True - if toDelete is found and deleted.
     */
    public boolean delete(java.lang.String toDelete){
        if ( toDelete != null) {
            return bucket.remove(toDelete);
        }
        return true;
    }

    /**
     * @return The number of elements currently in the set.
     */
    public int size(){
        return bucket.size();
    }

    /* returns an iterator of the bucket (starts at index 0) . */
    public ListIterator<String> bucketIterator(){
        return bucket.listIterator(0);
    }

}