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

        int[] array = shop.getTablet().order((shop.getClientQueue().dequeue().getGamesStack().toArray()), shop.getShelf());

        System.out.println("----------------------------------------");
        for (int var : array) {
            System.out.println(var);

        }

        br.close();

    }

}
