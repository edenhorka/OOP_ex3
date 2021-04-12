/**
 * A class represents a cell in an closed hash table ( can only contain one string) .
 * Each cell has a value (String) and a boolean that indicates whether the cell is deleted/empty.
 * @author eden horka
 */

public class HashTableCell{

    /* the value that was/in the cell. */
    private java.lang.String value;

    /* true if this cell is a deleted cell, false otherwise. */
    private boolean isDeleted;

    /**
     * A default constructor of a hash table cell.
     */
    public HashTableCell() {
        isDeleted = true  ;
    }

    /**
     * Sets a new value for this cell.
     * @param newValue - a new value to set.
     */
    public void setValue(java.lang.String newValue) {
        if ( newValue != null ) {
            value = newValue ;
            isDeleted = false ;
        }
    }

    /**
     * delete this cell's value.
     */
    public void delete() {
        isDeleted = true ;
    }

    /**
     * @return the value of this cell.
     */
    public String getValue() {
        return value ;
    }

    /**
     * @return true if this cell is empty (deleted), false otherwise.
     */
    public boolean isDeleted() {
        return isDeleted ;
    }
}