package src.ui;
import src.model.objects.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main{

    public static void main(String[] args) throws NumberFormatException, Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int numCase = Integer.parseInt(br.readLine());// pa que esssss?

        //crea la tienda
        Shop shop = new Shop(Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()));

        //crea los shelf
        shop.createShelf(br);
        //crea los clientes
        shop.createClients(br);

        System.out.println("hola");
 
    
        br.close();
    }



}