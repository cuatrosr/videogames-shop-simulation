package model;

import java.util.ArrayList;

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

public class DefaultHashTable<K, V> implements HashTable<K, V>{

    ArrayList<HashNode<K, V>> table;
    int size;

    public DefaultHashTable(int size) {
        table = new ArrayList<>();
        table.ensureCapacity(size);
        for (HashNode<K, V> hashNode : table) {
            hashNode = new HashNode<K, V>();
        }
    }

    @Override
    public int hash(K key) {
        switch (key.getClass().getSimpleName()) {
            case "Integer":
                return key % size;
        }
        return 0;
    }

    @Override
    public void insert(K key, V value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(K key) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void search(K key) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
