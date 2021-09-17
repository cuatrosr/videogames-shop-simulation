package src.model.data_structures;
import src.model.interfaces.*;

class HashNode<K, V> {

    K key;
    V value;

    HashNode<K, V> next;
    HashNode<K, V> prev;

    public HashNode(K key, V value) {
        this.key = key;
        this.value = value;
        next = null;
        prev = null;
    }

    public HashNode() {
        next = null;
        prev = null;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

public class DefaultHashTable<K, V> implements HashTable<K, V> {

    private HashNode<?, ?>[] table;
    private int size;
    private int arraySize;

    public DefaultHashTable(int arraySize) {
        table = new HashNode<?, ?>[arraySize];
        size = 0; // :)
        this.arraySize = arraySize;
    }

    @Override
    public int hash(K key) {
        String auxKey = String.valueOf(key);
        return hash(auxKey, auxKey.length(), 0) % arraySize;
    }

    private int hash(String key, int length, int value) {

        if (length != 0) {
            value += key.charAt(length - 1);
            length--;
            return hash(key, length, value);

        } else{
            return value;

        }
    }

    @Override
    public void insert(K key, V value) {
        table[hash(key)] = new HashNode<K, V>(key, value);
        size += 1;
    
    }

    @Override
    public void delete(K key) {
        table[hash(key)] = null;
        size--;
      
    }

    @Override
    public Object search(K key) {
       return  table[hash(key)].getValue();
    
    }

    @Override
    public boolean isEmpty() {
        return (size == 0)? true: false;
    }

    @Override
    public int size() {
        return size;
    }

    
}
