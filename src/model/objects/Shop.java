package model.objects;

import java.io.BufferedReader;

import model.data_structures.*;

/**
 * Shop
 */
public class Shop {

    private int sellers;
    private Shelf[] shelves;
    private DefaultQueue<Client> clientQueue;
    private DefaultHashTable<Integer, DefaultStack<Integer>> gamesHash;
    private Tablet tablet;

    public Shop() {
        this.clientQueue = new DefaultQueue<>();
        tablet = new Tablet();
    }

    public Shop(int atm, int amoutShelf) {
        this.sellers = atm;
        this.shelves = new Shelf[amoutShelf];
        this.clientQueue = new DefaultQueue<>();
        tablet = new Tablet();
        this.gamesHash = null;
    }

    public int getSellers() {
        return this.sellers;
    }

    public void setSellers(int sellers) {
        this.sellers = sellers;
    }

    public Shelf[] getShelves() {
        return this.shelves;
    }

    public void setShelves(Shelf[] shelves) {
        this.shelves = shelves;
    }

    public DefaultQueue<Client> getClientQueue() {
        return this.clientQueue;
    }

    public void setClientQueue(DefaultQueue<Client> clientQueue) {
        this.clientQueue = clientQueue;
    }

    public DefaultHashTable<Integer,DefaultStack<Integer>> getgamesHash() {
        return this.gamesHash;
    }

    public void setgamesHash(DefaultHashTable<Integer,DefaultStack<Integer>> gamesHash) {
        this.gamesHash = gamesHash;
    }

    public Tablet getTablet() {
        return this.tablet;
    }

    public void setTablet(Tablet tablet) {
        this.tablet = tablet;
    }
   
   
    public void createShelf(BufferedReader br) throws NumberFormatException, Exception {
        for (int i = 0; i < this.getShelves().length; i++) {

            String code = br.readLine();
            int numGames = Integer.parseInt(code.split(" ")[1]);

            this.getShelves()[i] = new Shelf(code.split(" ")[0], numGames);

            //agrega los games a su shelf correspondiente
            for (int j = 0; j < numGames; j++) {
                String line = br.readLine();
                int gameCode = Integer.parseInt(line.split(" ")[0]);
                this.getShelves()[i].getGameHash().insert(gameCode, new Games(gameCode, Integer.parseInt(line.split(" ")[1]), Integer.parseInt(line.split(" ")[2])));

            }
        }
    }

    public void createClients(BufferedReader br) throws NumberFormatException, Exception {
        BufferedReader auxBr = br;

        int num = Integer.parseInt(auxBr.readLine());
        this.setgamesHash(new DefaultHashTable<Integer, DefaultStack<Integer>>(num));
        DefaultStack<Integer> stack;

        for (int i = 0; i < num; i++) {

            String[] line = auxBr.readLine().split(" ");
            stack  = new DefaultStack<Integer>();

            for (int j = 1; j < line.length; j++) {
                stack.push(Integer.parseInt(line[j]));
            }

            int key = this.getgamesHash().insert(Integer.parseInt(line[0]), stack);

            Client client = new Client(Integer.parseInt(line[0]), key, i + 1);

            
            this.getClientQueue().enqueue(client);
        }
    }

    public void selectionSort(){  
        Client[] arr = this.getClientQueue().toClientArray();

        for (int i = 0; i < arr.length - 1; i++)  {  
            int index = i;  
            for (int j = i + 1; j < arr.length; j++){  
                if (arr[j].getTime() < arr[index].getTime()){  
                    index = j;//searching for lowest index  
                }  
            }  
            Client smallerClient = arr[index];   
            arr[index] = arr[i];  
            arr[i] = smallerClient;  
        } 
        
        this.getClientQueue().toQueue(arr);
    } 


}
