package ui;

import model.objects.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws NumberFormatException, Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int numCase = Integer.parseInt(br.readLine());// pa que esssss?

        //crea la tienda
        Shop shop = new Shop(Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()));

        //crea los shelf
        shop.createShelf(br);
        //crea los clientes y suma el tiempo que tardaron en entrar
        shop.createClients(br);

        System.out.println("----------------------------------------");

        Client[] clients = shop.getClientQueue().toClientArray();

        //se le entregan los juegos al cliente y suma al tiempo lo que se demoraron buscando los juegos
        for (Client client : clients) {
            //client.setShoppingList(shop.getTablet().order(client.getGamesStack().toArray(), shop.getShelf()));
            client.setGames(shop.getTablet().orderInsertSort(shop.getgamesHash().search(client.getCc()).toArray(), shop.getShelves()));
            try {
                client.setAmountGames(client.getGames().length);
            } catch (NullPointerException e) {
                client.setAmountGames(0);
            }
            client.setTime(client.getTime() + client.getAmountGames());

        }

        //organiza los cliente por el tiempo en la cola y el tiempo que se demoraron buscando los juegos
        shop.getClientQueue().toQueue(clients);
        shop.selectionSort();

        //cajeros
        int k = 0;
        Client[] sellers = new Client[shop.getSellers()];
        do {

            for (int i = 0; i < sellers.length; i++) {
                if (sellers[i] == null && k < clients.length) {
                    sellers[i] = shop.getClientQueue().dequeue();
                    k++;
                }

            }

            for (int i = 0; i < sellers.length; i++) {

                if (sellers[i] != null) {
                    sellers[i].setAmountGames(sellers[i].getAmountGames() - 1);

                    if (sellers[i].getAmountGames() == 0) {
                        shop.getTablet().money(sellers[i], shop.getShelves());
                        shop.getClientQueue().enqueue(sellers[i]);
                        sellers[i] = null;

                    }

                }

                if (i == sellers.length - 1 && k != clients.length - 1) {

                    for (int l = 0; l < sellers.length; l++) {
                        if (sellers[l] == null) {
                            i = sellers.length;
                            break;
                        } else {
                            i = 0;
                        }

                    }

                }

            }

        } while (shop.getClientQueue().size() < clients.length);

        int size = shop.getClientQueue().size();

        //Imprime 
        for (int i = 0; i < size; i++) {
            Client client = shop.getClientQueue().dequeue();

            System.out.println(client.getCc() + " " + client.getTotalPurchase());

            Integer[] g = client.getGames();
            String msg = "";
            for (Integer g1 : g) {
                msg += (g1 != null) ? g1 + " " : "";
            }
            System.out.println(msg);
            /*
            for (int var : client.getGames()) {
                System.out.print(var + " ");
            }
             */

            //System.out.println();
        }

        br.close();

    }

}
