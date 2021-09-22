package model.objects;

import java.util.Arrays;
import model.data_structures.DefaultHashTable;

public class Tablet {

    public Tablet() {
    }

    public int[] order(Integer[] array, Shelf[] shelf) {
        int j = 0;
        int[] c = new int[array.length];
        for (Shelf var : shelf) {
            for (int i = 0; i < c.length; i++) {
                Games game = var.getGameHash().search(array[i]);
                if (game != null && array[i] == game.getGameCode() && game.getAmount() != 0) {
                    c[j] = array[i];
                    game.setAmount(game.getAmount() - 1);;
                    j++;
                }
            }
        }
        return c;
    }

    public Integer[] orderSort(Integer[] arr, Shelf[] shelf) {
        int n = arr.length;
        for (int j = 1; j < n; j++) {
            int key = arr[j];
            int i = j - 1;
            int compare = getShelf(arr[i], shelf).compareTo(getShelf(key, shelf));
            while ((i > -1) && (compare >= 0)) {
                arr[i + 1] = arr[i];
                i--;
            }
            arr[i + 1] = key;
        }
        return arr;
    }

    public String getShelf(int key, Shelf[] shelf) {
        for (int i = 0; i < shelf.length; i++) {
            DefaultHashTable<Integer, Games> gameHash = shelf[i].getGameHash();
            if (gameHash.search(key) != null) {
                return shelf[i].getCode();
            }
        }
        return null;
    }

    public void money(Client client, Shelf[] shelf) {
        int money = 0;
        Integer[] games = client.getShoppingList();
        for (Shelf var : shelf) {
            for (int i = 0; i < games.length; i++) {
                Games game = var.getGameHash().search(games[i]);
                if (game != null && games[i] == game.getGameCode() && game.getAmount() != 0) {
                    money += game.getGamePrice();
                }
            }
        }
        client.setTotalPurchase(money);
    }
}
