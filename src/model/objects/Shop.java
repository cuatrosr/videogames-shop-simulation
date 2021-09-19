package model.objects;

import java.io.BufferedReader;

import model.data_structures.DefaultQueue;

/**
 * Shop
 */
public class Shop {

    private int atm;
    private Shelf[] shelf;
    private DefaultQueue<Client> clientQueue;
    private Tablet tablet;

    public Shop(int atm, int amoutShelf) {
        this.atm = atm;
        this.shelf = new Shelf[amoutShelf];
        this.clientQueue = new DefaultQueue<>();
        tablet = new Tablet();
    }

    public int getAtm() {
        return this.atm;
    }

    public void setAtm(int atm) {
        this.atm = atm;
    }

    public Shelf[] getShelf() {
        return this.shelf;
    }

    public void setShelf(Shelf[] shelf) {
        this.shelf = shelf;
    }

    public DefaultQueue<Client> getClientQueue() {
        return this.clientQueue;
    }

    public void setClientQueue(DefaultQueue<Client> clientQueue) {
        this.clientQueue = clientQueue;
    }

    public Tablet getTablet() {
        return this.tablet;
    }

    public void setTablet(Tablet tablet) {
        this.tablet = tablet;
    }

    public void createShelf(BufferedReader br) throws NumberFormatException, Exception {
        for (int i = 0; i < this.getShelf().length; i++) {

            String code = br.readLine();
            int numGames = Integer.parseInt(code.split(" ")[1]);

            this.getShelf()[i] = new Shelf(code.split(" ")[0], numGames);

            //agrega los games a su shelf correspondiente
            for (int j = 0; j < numGames; j++) {
                String line = br.readLine();
                int gameCode = Integer.parseInt(line.split(" ")[0]);
                this.getShelf()[i].getGameHash().insert(gameCode, new Games(gameCode, Integer.parseInt(line.split(" ")[1]), Integer.parseInt(line.split(" ")[2])));

            }

        }

    }

    public void createClients(BufferedReader br) throws NumberFormatException, Exception {

        int num = Integer.parseInt(br.readLine());

        for (int i = 0; i < num; i++) {

            String[] line = br.readLine().split(" ");

            Client client = new Client(Integer.parseInt(line[0]));

            for (int j = 1; j < line.length; j++) {
                client.getGamesStack().push(Integer.parseInt(line[j]));

            }

            this.getClientQueue().enqueue(client);

        }

    }

}
