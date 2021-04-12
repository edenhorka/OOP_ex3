/**
 * This class represents a hash set of an closing hashing model, in which each cell contains at
 * most one item.
 * In this model, each cell in the hash table is a reference to an object (HashTableCell) that has a string
 * as a value and a boolean that marks if the cell is deleted.
 * When a new string is mapped to an occupied cell, there’s need to probe further in the array to find an
 * empty spot.
 * @author eden horka
 */


public class ClosedHashSet extends SimpleHashSet {

    /* An array of strings represents a closed hash set. */
    private HashTableCell[] closedHashSet;


    /* ===== Constructors: ==== */

    /**
     * A default constructor of a closed hash set.
     * Builds a new, empty table with default initial capacity, upper and lower load factor.
     */
    public ClosedHashSet() {
        closedHashSet = new HashTableCell[capacity];
        reset(closedHashSet);
    }


    /**
     * A constructor of a closed hash set.
     * Builds a new, empty table with the specified load factors, and the default initial capacity.
     *
     * @param upperLoadFactor - The upper load factor of the hash table.
     * @param lowerLoadFactor - The lower load factor of the hash table.
     */

    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        closedHashSet = new HashTableCell[capacity] ;
        reset(closedHashSet) ;
        lowLoadFactor = lowerLoadFactor;
        upLoadFactor = upperLoadFactor;
    }

    /**
     * A constructor of a closed hash set.
     * Builds the hash set by adding elements one by one (duplicate values should be ignored).
     * The new table has the default values.
     *
     * @param data - Values to add to the set.
     */
    public ClosedHashSet(String[] data) {
        closedHashSet = new HashTableCell[capacity];
        reset(closedHashSet);
        for (String value: data) {
            add(value);
        }
    }


    /* ==== Methods: ==== */

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(String newValue) {
        if (( newValue != null ) && ( ! contains(newValue) )) {
            for ( int i=0; i < capacity ; i++ ) {
                int index = findIndex(newValue, i);
                if ( isCellDeleted(index) ) {
                    closedHashSet[index].setValue(newValue);
                    numOfElements++ ;
                    reHashAfterAddition();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal - Value to search for
     * @return True - if searchVal is found in the set
     */
    public boolean contains(String searchVal){
        int index = findValue(searchVal);
        return  index != -1 ;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(String toDelete) {
        if (toDelete == null){
            return false;
        }
        int index = findValue(toDelete);
        if ( isIndexValid(index) ) {
            closedHashSet[index].delete() ;
            numOfElements-- ;
            reHashAfterDeletion();
            return true ;
            }
        return false ;
    }

    /**
     * Clamps hashing indices to fit within the current table capacity.
     * @param hashIndex -  the index before clamping.
     * @return an index properly clamped, -1 if there was a problem finding one.
     */
    protected int clamp(int hashIndex) {
        return hashIndex & (capacity - 1 ) ;
    }

    private boolean isCellDeleted(int index){
        return  isIndexValid(index) && closedHashSet[index].isDeleted() ;
    }

    /* calculates and returns the matching legal index in the hash set.  */
    private int findIndex(java.lang.String value, int i) {
        int hashIndex = value.hashCode() + (i + i * i) / 2;
        return clamp(hashIndex);
    }


    /* returns -1 if the value wasn't found (or was found in a deleted cell),
     * else returns its index in the hash set. */
    private int findValue( java.lang.String searchVal ) {
        int deletedCell = -1;
        int index;
        for (int i = 0; i < capacity ; i++) {
            index = findIndex(searchVal, i);
            if ( isIndexValid(index) ) {
                String cellValue = closedHashSet[index].getValue();
                if ((cellValue != null) && (cellValue.equals(searchVal) )) {
                    if (isCellDeleted(index)) {
                        return deletedCell;
                    }
                    else {
                        return index;
                    }
                }
            }
        }
        return deletedCell;
    }


    /* Rehashes all the elements in the open hash set. */
    private void reHash(){
        HashTableCell[] newHashSet = new HashTableCell[capacity] ;
        // resets the new hash table
        reset(newHashSet);
        for ( HashTableCell cell: closedHashSet ) {
            java.lang.String value = cell.getValue() ;
            // checks if the cell has a value and is not deleted:
            if ((value != null) && ( !cell.isDeleted() )) {
                for(int i=0; i<capacity ;i++) {
                    int newIndex = findIndex(value, i);
                    if (isIndexValid(newIndex)) {
                        newHashSet[newIndex].setValue(cell.getValue());
                        break;
                    }
                }
            }
        }
        closedHashSet = newHashSet;
    }

    /* reset the cells of the hash set to be empty (and not null) */
    private void reset(HashTableCell[] hashSet){
        for (int i=0; i<capacity ;i++){
            hashSet[i] = new HashTableCell() ;
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
        if ( loadFactor >= upLoadFactor ) {
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

