/**
 * An abstract class represents a simple hash set.
 * @author eden horka
 */

public abstract class SimpleHashSet implements SimpleSet{

    /* The default lower load factor of the hash table. */
    protected static final float DEFAULT_LOWER_CAPACITY = 0.25f ;
    /* The default upper load factor of the hash table. */
    protected static final float DEFAULT_HIGHER_CAPACITY = 0.75f ;
    /* The default size of the hash table. */
    protected static final int INITIAL_CAPACITY = 16 ;

    /* The current number of elements in the hash table. */
    protected int numOfElements ;
    /* The size of the hash table. */
    protected int capacity ;

    /* The current load factor of the hash table. */
    protected float loadFactor ;
    /* The lower load factor of the hash table. */
    protected float lowLoadFactor ;
    /* The upper load factor of the hash table. */
    protected float upLoadFactor ;

    /**
     * A default constructor of a simple hash set.
     */
    public SimpleHashSet() {
        numOfElements = 0;
        capacity = INITIAL_CAPACITY ;
        lowLoadFactor = DEFAULT_LOWER_CAPACITY;
        upLoadFactor = DEFAULT_HIGHER_CAPACITY;
    }

    /**
     * Constructs a new hash set with initial capacity.
     * @param upperLoadFactor the upper load factor before rehashing
     * @param lowerLoadFactor the upper load factor before rehashing
     */
    public SimpleHashSet(float upperLoadFactor,float lowerLoadFactor){
        numOfElements = 0 ;
        capacity = INITIAL_CAPACITY ;
        lowLoadFactor = lowerLoadFactor ;
        upLoadFactor = upperLoadFactor ;

    }

    /**
     * @return The current capacity (number of cells) of the table.
     */
    public int capacity() {
        return capacity ;
    }

    /**
     * Clamps hashing indices to fit within the current table capacity.
     * @param index -  the index before clamping.
     * @return an index properly clamped.
     */
    protected abstract int clamp(int index);

    /**
     * @return the current lower load factor of the hash set.
     */
    protected float getLowerLoadFactor(){
        return lowLoadFactor ;
    }

    /**
     * @return the current upper load factor of the hash set.
     */
    protected float getUpperLoadFactor(){
        return upLoadFactor ;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public abstract boolean add(java.lang.String newValue);

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public abstract boolean contains(java.lang.String searchVal);

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public abstract boolean delete(java.lang.String toDelete);

    /**
     * @return The number of elements currently in the set
     */
    public int size() {
        return numOfElements ;
    }

    /* returns true if a given index is valid, otherwise false. */
    protected boolean isIndexValid(int index) {
        return  (( index >= 0 ) && ( index < capacity )) ;
    }
}