package model.objects;

import model.data_structures.DefaultHashTable;

/**
 * Shelving
 */
public class Shelf {

    private String code;
    private int amountGames;
    private DefaultHashTable<Integer, Game> gameHash;

    public Shelf(String code, int numGames) {
        this.code = code;
        this.amountGames = numGames;
        this.gameHash = new DefaultHashTable<Integer, Game>(numGames);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAmountGames() {
        return this.amountGames;
    }

    public void setAmountGames(int amountGames) {
        this.amountGames = amountGames;
    }

    public DefaultHashTable<Integer, Game> getGameHash() {
        return this.gameHash;
    }

    public void setGameHash(DefaultHashTable<Integer, Game> gameHash) {
        this.gameHash = gameHash;
    }
}
