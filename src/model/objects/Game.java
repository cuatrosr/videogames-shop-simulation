package model.objects;

/**
 * Games
 */
public class Game {

    private int gameCode;
    private double gamePrice;
    private int amount;
    private String nameGame;

    public Game(int gameCode, double gamePrice, int amount) {
        this.gameCode = gameCode;
        this.gamePrice = gamePrice;
        this.amount = amount;
    }
    
    public Game(int gameCode, double gamePrice, int amount, String nameGame) {
        this.nameGame = nameGame;
        this.gameCode = gameCode;
        this.gamePrice = gamePrice;
        this.amount = amount;
    }

    public int getGameCode() {
        return this.gameCode;
    }

    public void setGameCode(int gameCode) {
        this.gameCode = gameCode;
    }

    public double getGamePrice() {
        return this.gamePrice;
    }

    public void setGamePrice(double gamePrice) {
        this.gamePrice = gamePrice;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNameGame() {
        return nameGame;
    }

    public void setNameGame(String nameGame) {
        this.nameGame = nameGame;
    }
}
