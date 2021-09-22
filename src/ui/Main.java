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
        //crea los clientes
        shop.createClients(br);

        System.out.println("----------------------------------------");

        Client[] clients = shop.getClientQueue().toClientArray();

        //crea la lista de compras y le dice al cliente el valor total de la compra
        for (Client client : clients) {
            //client.setShoppingList(shop.getTablet().order(client.getGamesStack().toArray(), shop.getShelf()));
            client.setShoppingList(shop.getTablet().orderSort(client.getGamesStack().toArray(), shop.getShelf()));
            shop.getTablet().money(client, shop.getShelf());

        }

        //Imprime 
        for (Client client : clients) {
            System.out.println(client.getCc() + " " + client.getTotalPurchase());

            for (int var : client.getShoppingList()) {
                System.out.print(var + " ");
            }

            System.out.println();
            

        }
        
        br.close();

    }

}
