package model.data_structures;

import model.objects.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DefaultStackTest {

    DefaultStack<Client> clientStack;

    @BeforeEach
    private void setUp1() {
        clientStack = new DefaultStack<>();

    }

    @BeforeEach
    private void setUp2() {
        clientStack = new DefaultStack<>();
        clientStack.push(new Client(1006016477, 102, 3));
        clientStack.push(new Client(1006013423, 123, 4));
    }

    @Test
    void normalPush() {
        setUp1();
        Client client;

        client = new Client(1006016477, 102, 3);
        clientStack.push(client);
        assertEquals(client, clientStack.pop());
    }

    @Test
    void interestingPush() {
        setUp1();

        Client client1 = new Client(1006014234, 110, 56);
        clientStack.push(client1);
        Client client2 = new Client(1006015435, 106, 2);
        clientStack.push(client2);
        Client client3 = new Client(1006013213, 1312, 231);
        clientStack.push(client3);

        assertEquals(client3,  clientStack.pop());
        assertEquals(client2, clientStack.pop());
        assertEquals(client1, clientStack.pop());
    }



    @Test
    void normalPop() {
        setUp2();

        assertNotEquals(null, clientStack.pop());
        assertNotEquals(null, clientStack.pop());
    }

    @org.junit.jupiter.api.Test
    void limitPop() {
        setUp2();

        assertDoesNotThrow(() -> clientStack.pop());
        assertDoesNotThrow(() -> clientStack.pop());
        assertThrows(NoSuchElementException.class, () -> clientStack.pop());
        assertThrows(NoSuchElementException.class, () -> clientStack.pop());

    }


}