package src.model.objects;
import src.model.data_structure.*;

/**
 * Shelving
 */
public class Shelving {

    private int code;
    private int amountGames;
    private DefaultHashTable<Integer, Games> gameHash;

    public Shelving(int code, int amountGames, DefaultHashTable<Integer,Games> gameHash) {
        this.code = code;
        this.amountGames = amountGames;
        this.gameHash = gameHash;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getAmountGames() {
        return this.amountGames;
    }

    public void setAmountGames(int amountGames) {
        this.amountGames = amountGames;
    }

    public DefaultHashTable<Integer,Games> getGameHash() {
        return this.gameHash;
    }

    public void setGameHash(DefaultHashTable<Integer,Games> gameHash) {
        this.gameHash = gameHash;
    }


    
}
