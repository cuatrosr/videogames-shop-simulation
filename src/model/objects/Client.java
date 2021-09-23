package model.objects;

/**
 * Client
 */
public class Client {

    private String name;
    private int cc;
    private int key;
    private Integer[] games;
    private int amountGames;
    private int totalPurchase;
    private int time;

    public Client(int cc, int code, int time) {
        this.name = "name";
        this.cc = cc;
        this.key = code;
        this.totalPurchase = 0;
        this.games = null;
        this.time = time;
        this.amountGames = 0;
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

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Integer[] getGames() {
        return this.games;
    }

    public void setGames(Integer[] games) {
        this.games = games;
    }

    public int getAmountGames() {
        return this.amountGames;
    }

    public void setAmountGames(int amountGames) {
        this.amountGames = amountGames;
    }

    public int getTotalPurchase() {
        return this.totalPurchase;
    }

    public void setTotalPurchase(int totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }
 




}
