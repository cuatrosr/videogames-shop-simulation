package model;

/**
 * A custom implementation of the Stack generic data type. <br>
 */
public class Stack<T> {
    
    private Node<T> top;
    private int size;

    /**
     * Main constructor of the class. Crates an empty stack. <br>
     */
    public Stack() {
        top = null;
        size = 0;
    }

    /**
     * Pushes the given data to the top of the Stack, and makes it the only data accesible from the Stack. <br>
     * @param data The data to be inserted into the Stack. <b>Must be of type T and can't be null</b>. <br>
     */
    public void push(T data) {
        Node<T> element = new Node<>(data);
        element.setNext(top);
        top = element;
        size++;
    }

    /**
     * Deletes (pops) the first element of the Stack, moves the next element to the top of the stack and returns the deleted one upon deletion. <br>
     * @return The data of the element that was just deleted. <br>
     */
    public T pop() {
        Node<T> trash = top;
        top = top.next();
        size--;
        return trash.data();
    }

    /**
     * @return The top of the stack
     */
    public T top() {
        return top.data();
    }

    /**
     * @return `true` if there are no elements in the stack, `false` otherwise. <br>
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return The ammount of elements in the stack. <br>
     */
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> head = top;
        sb.append("(");
        String prefix = "";
        while (head != null) {
            sb.append(prefix).append(head.data());
            prefix = ", ";
            head = head.next();
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Reverses the Stack,such that the topmost element is now at the bottom. <br>
     * @return
     */
    public Stack<T> reverse() {
        Stack<T> reversed = new Stack<>();
        while (!isEmpty()) {
            reversed.push(pop());
        }
        return reversed;
    }

    /**
     * Returns a string representation of the Stack, as a comma separated list with no other characters or whitespace. <br>
     * @return A trimmed comma separated values list that reresents the stack. <br>
     */
    public String toStringNoFormat() {
        StringBuilder sb = new StringBuilder();
        Node<T> head = top;
        String prefix = "";
        while (head != null) {
            sb.append(prefix).append(head.data());
            prefix = ",";
            head = head.next();
        }
        return sb.toString();
    }
}
