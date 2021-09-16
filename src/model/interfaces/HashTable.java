package src.model.interfaces;

/**
 * Interface representing the main operations of a Hash Table abstract data type. <br>
 */
public interface HashTable<K, V> {
    
    /**
     * The hashing algorithm. <br>
     * @param key The key to be hashed. <br>
     * @return The index to be set into the array. <br>
     */
    abstract public int hash(K key);

    /**
     * Inserts a new value into the table. <br>
     * @param key The key to calculate the index. <br>
     * @param value The value to be inserted. <br>
     */
    abstract public void insert(K key, V value);

    abstract public void delete(K key);

    abstract public Object search(K key);

    abstract public boolean isEmpty();

    abstract public int size();
}
