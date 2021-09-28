package model.data_structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultHashTableTest {

    DefaultHashTable<Integer, String> testHash;
    private final int SIZE = 3;

    void setUp1() {
        testHash = new DefaultHashTable<>(SIZE);
    }

    void setUp2() throws Exception {
        testHash = new DefaultHashTable<>(SIZE);
        testHash.insert(1, "Car");
        testHash.insert(3, "Cat");
        testHash.insert(6, "Dog");
    }

    @Test
    void normalInsert() throws Exception {
        setUp1();

        String name1 = "Car";
        int key1 = testHash.insert(234, name1);

        assertEquals(name1, testHash.search(key1));
    }

    @Test
    void limitInsert() {
        setUp1();

        assertDoesNotThrow(() -> testHash.insert(234, "Car"));
        assertDoesNotThrow(() -> testHash.insert(375, "House"));
        assertDoesNotThrow(() -> testHash.insert(426, "Dog"));
        assertThrows(Exception.class, () -> testHash.insert(426, "Cat"));
        assertThrows(Exception.class, () -> testHash.insert(426, "Lol"));
    }

    @Test
    void interestingInsert() throws Exception {
        setUp1();

        String name1 = "Car";
        int key1 = testHash.insert(234, name1);

        String name2 = "House";
        int key2 = testHash.insert(234, name2);

        String name3 = "Dog";
        int key3 = testHash.insert(234, name3);

        assertEquals(name1, testHash.search(key1));
        assertEquals(name2, testHash.search(key2));
        assertEquals(name3, testHash.search(key3));
    }

    @Test
    void normalDelete() throws Exception {
        setUp2();
        testHash.delete(3);

        assertNull(testHash.search(3));

    }

    @Test
    void limitDelete() throws Exception {
        setUp2();

        assertDoesNotThrow(() -> testHash.delete(1));
        assertDoesNotThrow(() -> testHash.delete(3));
        assertDoesNotThrow(() -> testHash.delete(6));
        assertThrows(Exception.class, () -> testHash.delete(1));
        assertThrows(Exception.class, () -> testHash.delete(3));
        assertThrows(Exception.class, () -> testHash.delete(6));

    }

    @Test
    void interestingDelete() throws Exception {

    }

    @Test
    void normalSearch() throws Exception {
        setUp1();

        String name1 = "Car";
        int key1 = testHash.insert(234, name1);

        assertEquals(name1, testHash.search(key1));

    }

    @Test
    void limitSearch() throws Exception {
        setUp2();
        assertNull(testHash.search(23425));

    }

    @Test
    void interestingSearch() throws Exception {

    }
}
