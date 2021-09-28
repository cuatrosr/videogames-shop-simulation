package ui;

import model.objects.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    @SuppressWarnings("unused")
    public static void main(String[] args) throws  Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int numCase = Integer.parseInt(br.readLine());

        Shop shop = new Shop(Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()));

        shop.createShelfCLI(br);

        shop.createClientsCLI(br);

        System.out.println("----------------------------------------");

        Client[] clients = shop.getClientQueue().toClientArray();

        shop.getTablet().clientList(clients, shop);

        shop.selectionSort(clients);

        shop.sellers(clients.length);

        output(shop.getClientQueue().size(), shop);

        br.close();
    }

    public static void output(int size, Shop shop){

        for (int i = 0; i < size; i++) {
            Client client = shop.getClientQueue().dequeue();

            System.out.println(client.getCc() + " " + client.getTotalPurchase());

            Integer[] g = client.getGames();
            String msg = "";
            for (Integer g1 : g) {
                msg += (g1 != null) ? g1 + " " : "";
            }
            System.out.println(msg);
        }

    }

}
