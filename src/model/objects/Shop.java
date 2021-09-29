package model.objects;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;

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

    public Shop(int checkouts, int amoutShelf) {
        this.sellers = checkouts;
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

    public void setGamesHash(DefaultHashTable<Integer,DefaultStack<Integer>> gamesHash) {
        this.gamesHash = gamesHash;
    }

    public Tablet getTablet() {
        return this.tablet;
    }

    public void setTablet(Tablet tablet) {
        this.tablet = tablet;
    }
   
   
    public void createShelfCLI(BufferedReader br) throws NumberFormatException, Exception {

        for (int i = 0; i < this.getShelves().length; i++) {

            String code = br.readLine();
            int numGames = Integer.parseInt(code.split(" ")[1]);

            this.getShelves()[i] = new Shelf(code.split(" ")[0], numGames);

            //agrega los games a su shelf correspondiente
            for (int j = 0; j < numGames; j++) {
                String line = br.readLine();
                int gameCode = Integer.parseInt(line.split(" ")[0]);
                this.getShelves()[i].getGameHash().insert(gameCode, new Game(gameCode, Integer.parseInt(line.split(" ")[1]), Integer.parseInt(line.split(" ")[2])));

            }
        }
    }

    public void createShelf(ArrayList<String> shelves, ArrayList<String> games) throws NumberFormatException, Exception {
        for (int i = 0; i < shelves.size(); i++) {
            String[] shelf = shelves.get(i).split(": ");
            String shelfCode = shelf[0];
            String[] gameList = shelf[1].split(", ");
            int numGames = gameList.length;
            this.getShelves()[i] = new Shelf(shelfCode, numGames);
        }
        addGamesToShelf(games);
    }

    private void addGamesToShelf(ArrayList<String> games) throws Exception {
        for (String game : games) {
            String[] meta = game.split(" / ");
            String gameShelf = meta[3];
            int gameCode = Integer.parseInt(meta[2]);
            double gamePrice = Double.parseDouble(meta[1].replaceAll("\\$", ""));
            int gameAmount = Integer.parseInt(meta[4].replaceAll("x", ""));
            String gameName = meta[0];
            for (Shelf shelf : getShelves()) {
                if (gameShelf.equals(shelf.getCode())) {
                    shelf.getGameHash().insert(gameCode, new Game(gameCode, gamePrice, gameAmount, gameName));
                }
            }
        }
    }

    public void createClientsCLI(BufferedReader br) throws NumberFormatException, Exception {
        BufferedReader auxBr = br;

        int num = Integer.parseInt(auxBr.readLine());
        this.setGamesHash(new DefaultHashTable<Integer, DefaultStack<Integer>>(num));
        DefaultStack<Integer> stack;
        
        for (int i = 0; i < num; i++) {
            
            String[] line = auxBr.readLine().split(" ");
            stack  = new DefaultStack<>();
            
            for (int j = 1; j < line.length; j++) {
                stack.push(Integer.parseInt(line[j]));
            }
            
            int key = this.getgamesHash().insert(Integer.parseInt(line[0]), stack);
            
            Client client = new Client(Integer.parseInt(line[0]), key, i + 1);
            
            
            this.getClientQueue().enqueue(client);
        }
    }
    
    public void createClients(String name, int cc, String gamesRaw, int num, int i, int sorting) throws NumberFormatException, Exception {
        String[] line = gamesRaw.split(", ");
        DefaultStack<Integer> stack  = new DefaultStack<>();

        for (int j = 0; j < line.length; j++) {
            stack.push(Integer.parseInt(line[j]));
        } 
        int key = this.getgamesHash().insert(cc, stack);
        Client client = new Client(name, cc, key, i + 1, sorting);
        this.getClientQueue().enqueue(client);
    }

    public void selectionSort(Client[] clients){
        this.getClientQueue().toQueue(clients);
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

    public void sellers(int clientSize){
        int k = 0;
        Client[] sellers = new Client[this.getSellers()];
        do {

            for (int i = 0; i < sellers.length; i++) {
                if (sellers[i] == null && k < clientSize) {
                    sellers[i] = this.getClientQueue().dequeue();
                    k++;
                }

            }

            for (int i = 0; i < sellers.length; i++) {

                if (sellers[i] != null) {
                    sellers[i].setAmountGames(sellers[i].getAmountGames() - 1);

                    if (sellers[i].getAmountGames() <= 0) {
                        this.getTablet().money(sellers[i], this.getShelves());
                        this.getClientQueue().enqueue(sellers[i]);
                        sellers[i] = null;

                    }

                }

                if (i == sellers.length - 1 && k != clientSize - 1) {

                    for (Client seller : sellers) {
                        if (seller == null) {
                            i = sellers.length;
                            break;
                        } else {
                            i = 0;
                        }

                    }

                }

            }

        } while (this.getClientQueue().size() < clientSize);


    }


}
