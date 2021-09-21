package model.objects;

import model.data_structures.DefaultStack;

/**
 * Client
 */
public class Client {

    private String name;
    private int cc;
    private DefaultStack<Integer> gamesStack;
    private int[] shoppingList;
    private int totalPurchase;

    public Client(int cc) {
        this.name = "name";
        this.cc = cc;
        this.gamesStack = new DefaultStack<>();
        this.totalPurchase = 0;
        this.shoppingList = null;
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

    public DefaultStack<Integer> getGamesStack() {
        return this.gamesStack;
    }

    public void setGamesStack(DefaultStack<Integer> gamesStack) {
        this.gamesStack = gamesStack;
    }

    public int[] getShoppingList() {
        return this.shoppingList;
    }

    public void setShoppingList(int[] shoppingList) {
        this.shoppingList = shoppingList;
    }

    public int getTotalPurchase() {
        return this.totalPurchase;
    }

    public void setTotalPurchase(int totalPurchase) {
        this.totalPurchase = totalPurchase;
    }


}
