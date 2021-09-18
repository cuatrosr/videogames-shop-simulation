package src.model.objects;
import src.model.data_structures.*;

/**
 * Client
 */
public class Client {

    private String name;
    private int cc;
    private DefaultStack<Games> gamesStack;

    public Client(String name, int cc, DefaultStack<Games> gamesStack) {
        this.name = name;
        this.cc = cc;
        this.gamesStack = gamesStack;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCc() {
        return this.cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public DefaultStack<Games> getGamesStack() {
        return this.gamesStack;
    }

    public void setGamesStack(DefaultStack<Games> gamesStack) {
        this.gamesStack = gamesStack;
    }



    
}
