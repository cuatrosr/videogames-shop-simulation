package src.ui;
import src.model.data_structure.*;

public class Main{

    public static void main(String[] args) {
        DefaultHashTable<String, String> table = new DefaultHashTable<>(2);
        DefaultHashTable<String, Integer> table2 = new DefaultHashTable<>(5);

        table.insert("1A", "Hola samu");
        table.insert("1B", "Hola David");
        table2.insert("1A", 1);
        table2.insert("1B", 2);
        table2.insert("1C", 3);
        table2.insert("1D", 4);
        table2.insert("1E", 5);

        System.out.println(table.search("1A"));
        System.out.println(table.search("1B"));
        System.out.println(table2.search("1A"));
        System.out.println(table2.search("1B"));
        System.out.println(table2.search("1C"));
        System.out.println(table2.search("1D"));
        System.out.println(table2.search("1E"));
        
        
        
    }



}