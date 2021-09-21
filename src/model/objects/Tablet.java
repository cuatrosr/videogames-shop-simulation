package model.objects;

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

    
    public void money(Client client, Shelf[] shelf) {
        int money = 0;
        int[] games = client.getShoppingList();

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
