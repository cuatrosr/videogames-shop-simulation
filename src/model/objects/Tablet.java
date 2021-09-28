package model.objects;

import model.data_structures.DefaultHashTable;

public class Tablet {

    public Tablet() {
    }

    public Integer[] orderInsertSort(Integer[] arr, Shelf[] shelf) {
        int n = arr.length;
        if (n == 1 && getShelf(arr[0], shelf) == null) {
            return arr = new Integer[0];
        }
        for (int j = 1; j < n; j++) {
            int key = arr[j];
            int i = j - 1;
            String shelf1 = getShelf(arr[i], shelf);
            String shelf2 = getShelf(key, shelf);
            if (shelf1 == null || shelf2 == null) {
                arr[i] = (shelf1 == null) ? null : arr[i];
                arr[j] = (shelf2 == null) ? null : arr[j];
            }
            int compare = -1;
            try {
                compare = shelf1.compareTo(shelf2);
            } catch (NullPointerException e) {
            } finally {
                while ((i > -1) && (compare >= 0)) {
                    arr[i + 1] = arr[i];
                    i--;
                }
                arr[i + 1] = key;
            }
        }
        return arr;
    }
    
        public Integer[] orderSelectionSort(Integer[] arr, Shelf[] shelf) {
        int n = arr.length;
        if (n == 1 && getShelf(arr[0], shelf) == null) {
            return arr = new Integer[0];
        }
        for (int j = 1; j < n; j++) {
            int key = arr[j];
            int i = j - 1;
            String shelf1 = getShelf(arr[i], shelf);
            String shelf2 = getShelf(key, shelf);
            if (shelf1 == null || shelf2 == null) {
                arr[i] = (shelf1 == null) ? null : arr[i];
                arr[j] = (shelf2 == null) ? null : arr[j];
            }
            int compare = -1;
            try {
                compare = shelf1.compareTo(shelf2);
            } catch (NullPointerException e) {
            } finally {
                while ((i > -1) && (compare >= 0)) {
                    arr[i + 1] = arr[i];
                    i--;
                }
                arr[i + 1] = key;
            }
        }
        return arr;
    }

    public String getShelf(int key, Shelf[] shelf) {
        System.out.println(shelf);
        for (int i = 0; i < shelf.length; i++) {
            DefaultHashTable<Integer, Game> gameHash = shelf[i].getGameHash();
            if (gameHash.search(key) != null) {
                return shelf[i].getCode();
            }
        }
        return null;
    }

    public void money(Client client, Shelf[] shelf) {
        int money = 0;
        Integer[] games = client.getGames();
        for (Shelf var : shelf) {
            for (int i = 0; i < games.length; i++) {
                Game game = var.getGameHash().search(games[i]);
                if (game != null && games[i] == game.getGameCode() && game.getAmount() != 0) {
                    money += game.getGamePrice();
                }
            }
        }
        client.setTotalPurchase(money);
    }

    public void clientList(Client[] clients, Shop shop){
        for (Client client : clients) {
            //client.setShoppingList(shop.getTablet().order(client.getGamesStack().toArray(), shop.getShelf()));
            client.setGames(shop.getTablet().orderInsertSort(shop.getgamesHash().search(client.getCc()).toArray(), shop.getShelves()));
            client.setAmountGames(client.getGames().length);
            client.setTime(client.getTime() + client.getAmountGames());
        }
    }
}
