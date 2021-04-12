import java.util.TreeSet;

/**
 * A façade class, wraps an underlying Collection (such as linked list, tree set and hash set) and serves to
 * both simplify its API and give it a common type with the implemented SimpleHashSets.
 * @author eden horka
 */

public class CollectionFacadeSet implements SimpleSet{

    protected java.util.Collection<java.lang.String> collection;

    /* tree set as a helper to check duplicates in case of a linked list set, because the method contains is
    inefficient for linked lists and hash sets. */
    private TreeSet<String> helperTreeSet;

    /**
     * Creates a new facade wrapping the specified collection.
     * @param collection - the collection to wrap.
     */
    public CollectionFacadeSet(java.util.Collection<java.lang.String> collection) {
        this.collection = collection ;
        // prevents duplicates
        helperTreeSet = new TreeSet<>(collection);
        collection.clear();
        collection.addAll(helperTreeSet);
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(java.lang.String newValue){
        if (( newValue != null ) && (!contains(newValue) )){
            return collection.add(newValue);
        }
        return false ;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(java.lang.String searchVal){
        return collection.contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(java.lang.String toDelete) {
        return collection.remove(toDelete);
    }

    /**
     * @return The number of elements currently in the set
     */
    public int size(){
        return collection.size() ;
    }
}
