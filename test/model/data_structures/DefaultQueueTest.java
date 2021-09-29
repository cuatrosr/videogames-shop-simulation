package model.data_structures;

import model.objects.Client;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultQueueTest {

    private DefaultQueue<Client> clientQueue;
    
    private void setUp1() {
        clientQueue = new DefaultQueue<>();

    }
    
    private void setUp2() {
        clientQueue = new DefaultQueue<>();
        clientQueue.enqueue(new Client(1006016477, 102, 3));
        clientQueue.enqueue(new Client(1006013423, 123, 4));

    }
    
    @Test
    void normalEnqueue() {
        setUp1();
        Client client;

        client = new Client(1006016477, 102, 3);
        clientQueue.enqueue(client);
        assertEquals(client, clientQueue.dequeue());

    }
    
    @Test
    void interestingEnqueue() {
        setUp1();

        Client client1 = new Client(1006014234, 110, 56);
        clientQueue.enqueue(client1);
        Client client2 = new Client(1006015435, 106, 2);
        clientQueue.enqueue(client2);
        Client client3 = new Client(1006013213, 1312, 231);
        clientQueue.enqueue(client3);

        assertEquals(client1, clientQueue.dequeue());
        assertEquals(client2, clientQueue.dequeue());
        assertEquals(client3, clientQueue.dequeue());


    }

    @Test
    void normalDequeue() {
        setUp2();

        assertNotEquals(null, clientQueue.dequeue());
        assertNotEquals(null, clientQueue.dequeue());
    }
    
    @Test
    void limitDequeue() {
        setUp2();

        assertDoesNotThrow(() -> clientQueue.dequeue());
        assertDoesNotThrow(() -> clientQueue.dequeue());
        assertThrows(NoSuchElementException.class, () -> clientQueue.dequeue());
        assertThrows(NoSuchElementException.class, () -> clientQueue.dequeue());



    }


}