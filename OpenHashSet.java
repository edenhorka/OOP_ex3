/**
 * This class represents a hash set of an open hashing model, which allows
 * several items to be hashed to the same cell.
 * In this model, each cell in the hash table is a list (Bucket), and an element with the hash k is added to
 * the k’th bucket (after being fitted to the legal index range, which will be discussed shortly).
 * @author eden horka
 */

import java.util.* ;

public class OpenHashSet extends SimpleHashSet{

    /* An array of buckets represents an open hash set. */
    private Bucket[] openHashSet ;

    /* ===== Constructors: ==== */

    /**
     * A default constructor of an open hash set.
     * Builds a new, empty table with default initial capacity, upper and lower load factor.
     */
    public OpenHashSet() {
        openHashSet = new Bucket[capacity] ;
        reset(openHashSet);
    }


    /**
     * A constructor of an open hash set.
     * Builds a new, empty table with the specified load factors, and the default initial capacity.
     * @param upperLoadFactor - The upper load factor of the hash table.
     * @param lowerLoadFactor - The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor ){
        openHashSet = new Bucket[capacity] ;
        reset(openHashSet);
        lowLoadFactor = lowerLoadFactor;
        upLoadFactor = upperLoadFactor;
    }

    /**
     * A constructor of an open hash set.
     * Builds the hash set by adding elements one by one (duplicate values should be ignored).
     * The new table has the default values.
     * @param data - Values to add to the set.
     */
    public OpenHashSet(java.lang.String[] data){
        openHashSet = new Bucket[capacity] ;
        reset(openHashSet);
        for (String value: data){
            add(value) ;
        }
    }


    /* ==== Methods: ==== */

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(java.lang.String newValue) {
        if (( newValue != null ) && ( ! contains(newValue) )) {
            numOfElements++ ;
            reHashAfterAddition();
            int index = clamp(newValue.hashCode()) ;
            openHashSet[index].add(newValue) ;
            return true ;
        }
        return false ;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete( java.lang.String toDelete){
        if ( ! contains(toDelete) ) {
            return false ;
        }
        if( toDelete!= null ) {
            int index = clamp(toDelete.hashCode()) ;
            if ( isIndexValid(index) ) {
                openHashSet[index].delete(toDelete) ;
                numOfElements -- ;
                reHashAfterDeletion();
                return true ;
            }
        }
        return false;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(java.lang.String searchVal){
        if (searchVal != null) {
            int index = clamp(searchVal.hashCode()) ;
            if ( isIndexValid(index) ) {
                if (openHashSet[index] == null) {
                    return false;
                }
                return openHashSet[index].contains(searchVal);
            }
        }
        return false;
    }

    /* calculates and returns the matching index in the hash set for a given string. */
    protected int clamp( int index ) {
        return index & ( capacity - 1 ) ;
    }


    /* Rehashes all the elements in the open hash set. */
    private void reHash(){
        Bucket[] newHashSet = new Bucket[capacity] ;
        reset(newHashSet);
        for ( Bucket cell: openHashSet ){
            ListIterator<String> iterCell = cell.bucketIterator();
            while ( iterCell.hasNext() )  {
                java.lang.String value = iterCell.next();
                int newIndex =  clamp(value.hashCode());
                if ( isIndexValid(newIndex) ){
                    newHashSet[newIndex].add(value) ;
                }
            }
        }
        openHashSet = newHashSet;
    }

    /* resets the given hash set cells to be empty. */
    private void reset(Bucket[] hashSet){
        for(int i=0; i< capacity;i++){
            hashSet[i] = new Bucket();
        }
    }


    /* Check if the current load factor exceeds the upper or the lower border
     * and if it does, then rehashes the hash set. */
    private void reHashAfterDeletion() {
        loadFactor = ((float) numOfElements) / capacity;
        if (loadFactor < lowLoadFactor) {
            minimizeSet();
        }
    }

    private void reHashAfterAddition(){
        loadFactor = ((float) numOfElements) / capacity;
        if ( loadFactor > upLoadFactor ) {
            enlargeSet() ;
        }
    }

    /* Minimize the capacity (size) of the hash set. */
    private void minimizeSet(){
        if ( capacity >= 2 ){
            capacity = capacity/2 ;
            reHash() ;
        }
    }

    /* Enlarge the capacity (size) of the hash set. */
    private void enlargeSet() {
        capacity = 2 * capacity ;
        reHash() ;
    }

}