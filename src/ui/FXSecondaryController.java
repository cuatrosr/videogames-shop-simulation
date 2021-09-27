package ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import model.data_structures.DefaultQueue;
import model.objects.Client;
import model.objects.Shop;

public class FXSecondaryController implements Initializable{
    
    /*JAVAFX FIELDS*/
    
    //Shelves

    @FXML
    private JFXListView<String> shelvesLV = new JFXListView<>();

    @FXML
    private JFXTextField shelfIDTF = new JFXTextField();

    @FXML
    private JFXButton addShelfBTN = new JFXButton();

    @FXML
    private JFXButton editShelfBTN = new JFXButton();

    @FXML
    private JFXButton rmShelfBTN = new JFXButton();

    @FXML
    private JFXTextField checkoutNumTF = new JFXTextField();
    
    //Games

    @FXML
    private JFXListView<String> gamesLV = new JFXListView<>();
    
    @FXML
    private JFXTextField gameNameTF = new JFXTextField();
    
    @FXML
    private JFXTextField gameIDTF = new JFXTextField();
    
    @FXML
    private JFXTextField gamePriceTF = new JFXTextField();
    
    @FXML
    private JFXTextField gameAmountTF = new JFXTextField();

    @FXML
    private JFXComboBox<String> gameShelvesCBX = new JFXComboBox<>();
    
    @FXML
    private JFXButton addGameBTN = new JFXButton();
    
    @FXML
    private JFXButton editGameBTN = new JFXButton();

    @FXML
    private JFXButton rmGameBTN = new JFXButton();
    
    //Clients
    
    @FXML
    private JFXListView<String> clientsLV = new JFXListView<>();
    
    @FXML
    private JFXTextField clientNameTF = new JFXTextField();
    
    @FXML
    private JFXTextField clientIDTF = new JFXTextField();
    
    @FXML
    private JFXListView<String> clientGamesLV = new JFXListView<>();
    
    @FXML
    private JFXButton addClientBTN = new JFXButton();
    
    @FXML
    private JFXButton editClientBTN = new JFXButton();
    
    @FXML
    private JFXButton rmClientBTN = new JFXButton();

    //Stage 1

    @FXML
    private JFXListView<String> stage1LV = new JFXListView<>();
    
    //Stage 2

    @FXML
    private JFXListView<String> stage2LV = new JFXListView<>();
    
    //Stage 3
    
    //Stage 4

    /*CLASS FIELDS*/
    
    private Shop shop;
    
    ArrayList<String> shelves = new ArrayList<>();

    ArrayList<String> games = new ArrayList<>();
    
    ArrayList<String> clients = new ArrayList<>();

    String params = "";
    
    private FXController controller;
    
    private int checkouts;
    
    private boolean simulated;
    
    /*METHODS*/
    
    //Initializers
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!simulated) {
            initShelves();
            initGames();
            initClients();
            initParams();
        } else {
            initStage1();
            initStage2();
        }
    }
    
    private void initShelves() {
        shelvesLV.setOnMouseClicked((event) -> {
            boolean btnsDisabled = shelvesLV.getSelectionModel().getSelectedItems().isEmpty();
            rmShelfBTN.setDisable(btnsDisabled);
            editShelfBTN.setDisable(btnsDisabled);
        });
        boolean btnsDisabled = shelvesLV.getSelectionModel().getSelectedItems().isEmpty();
        rmShelfBTN.setDisable(btnsDisabled);
        editShelfBTN.setDisable(btnsDisabled);
        for (String shelf : shelves) {
            for (String game : games) {
                String[] split = shelf.split(":");
                if (game.contains(split[0])) {
                    System.out.println(split[0]);
                    String sep = split[1].equals(" ") ? "" : ", ";
                    shelf += sep + game.split(" / ")[2];
                }
            }
            shelvesLV.getItems().add(shelf);
        }
    }
    
    private void initGames() {
        gamesLV.setOnMouseClicked((event) -> {
            boolean btnsDisabled = gamesLV.getSelectionModel().getSelectedItems().isEmpty();
            rmGameBTN.setDisable(btnsDisabled);
            editGameBTN.setDisable(btnsDisabled);
        });
        boolean btnsDisabled = gamesLV.getSelectionModel().getSelectedItems().isEmpty();
        rmGameBTN.setDisable(btnsDisabled);
        editGameBTN.setDisable(btnsDisabled);
        for (String game : games) {
            System.out.println("G:" + game);
            gamesLV.getItems().add(game);
        }
        for (String shelf : shelves) {
            String shelfCode = shelf.split(":")[0];
            gameShelvesCBX.getItems().add(shelfCode);
        }
        
        gameAmountTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            gameAmountTF.setText(newValue.replaceAll("[^\\d]", ""));
        });
        gamePriceTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            gamePriceTF.setText(newValue.replaceAll("[^\\d]", ""));
        });
        gameIDTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            gameIDTF.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }
    
    private void initClients() {
        clientsLV.setOnMouseClicked((event) -> {
            boolean btnsDisabled = clientsLV.getSelectionModel().getSelectedItems().isEmpty();
            rmClientBTN.setDisable(btnsDisabled);
            editClientBTN.setDisable(btnsDisabled);
        });
        boolean btnsDisabled = clientsLV.getSelectionModel().getSelectedItems().isEmpty();
        rmClientBTN.setDisable(btnsDisabled);
        editClientBTN.setDisable(btnsDisabled);
        clientGamesLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for (String client : clients) {
            clientsLV.getItems().add(client);
        }
        for (String game : games) {
            String[] gameRaw = game.split(" / ");
            String gameCode = gameRaw[2] + " (" + gameRaw[0] + ")";
            clientGamesLV.getItems().add(gameCode);
        }
        clientIDTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            clientIDTF.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }
    
    private void initParams() {
        checkoutNumTF.setEditable(false);
        checkoutNumTF.setText("Chechout #: " + checkouts);
    }
    
    //Post Sims
    
    private void initStage1() {
        DefaultQueue<Client> clientQueue = shop.getClientQueue();
        for (int i = 0; i < clients.size(); i++) {
            String client = clients.get(i).split(" / ")[0];
            if (!client.equals(clientQueue.front().getName())) {
                clientQueue.enqueue(clientQueue.dequeue());
                i--;
                continue;
            }
            int currKey = clientQueue.front().getKey();
            stage1LV.getItems().add("Generated code for client '" + client + "': " + currKey);
        }
    }
    
    private void initStage2() {
        Client[] clientsArr = shop.getClientQueue().toClientArray();
        for (Client client : clientsArr) {
            stage2LV.getItems().add("Client ordered list: " + client.getGames().toString() + " (Current time: " + client.getTime() + ")");
        }
        shop.getClientQueue().toQueue(clientsArr);
    }
    
    //Constructors
    
    public FXSecondaryController(Shop shop, FXController controller, boolean simulated) {
        this.simulated = simulated;
        this.shop = shop;
        this.controller = controller;
        checkouts = 1;
    }
    
    //Utility
    
    //Shelves
    
    
    @FXML
    void addShelf(ActionEvent event) {
        String newShelf = shelfIDTF.getText() + ": ";
        shelvesLV.getItems().add(newShelf);
        shelves.add(newShelf);
        shelfIDTF.setText("");
        shelvesLV.requestFocus();
    }
    
    @FXML
    void editShelf(ActionEvent event) {
        
    }

    @FXML
    void removeShelf(ActionEvent event) {
        
    }
    
    //Games
    
    @FXML
    void addGame(ActionEvent event) {
        String newGame = gameNameTF.getText() + " / $" + gamePriceTF.getText() + " / " + gameIDTF.getText() + " / " + gameShelvesCBX.getSelectionModel().getSelectedItem().split(" \\(")[0] + " / x" + gameAmountTF.getText();
        gamesLV.getItems().add(newGame);
        games.add(newGame);
        gameNameTF.setText("");
        gameIDTF.setText("");
        gamePriceTF.setText("");
        gameShelvesCBX.getSelectionModel().clearSelection();
        gameAmountTF.setText("");
        gamesLV.requestFocus();
    }
    
    @FXML
    void editGame(ActionEvent event) {
        addGameBTN.setText("Commit");
        addGameBTN.setOnAction((e) -> {
            commitGame(event);
        });
    }
    
    @FXML
    void commitGame(ActionEvent event) {
        addGameBTN.setText("Add");
        addGameBTN.setOnAction((e) -> {
            addGame(event);
        });
    }
    
    @FXML
    void removeGame(ActionEvent event) {
        
    }
    
    //Clients
    
    @FXML
    void addClient(ActionEvent event) {
        String gameList = clientGamesLV.getSelectionModel().getSelectedItems().toString().replaceAll("\\s(\\([^,]+\\))", "");
        String newClient = clientNameTF.getText() + " / " + clientIDTF.getText() + " / " + gameList;
        clientsLV.getItems().add(newClient);
        clients.add(newClient);
        clientNameTF.setText("");
        clientIDTF.setText("");
        clientGamesLV.getSelectionModel().clearSelection();
        clientsLV.requestFocus();
    }
    
    @FXML
    void commitClient(ActionEvent event) {
        addClientBTN.setText("Add");
        addClientBTN.setOnAction((e) -> {
            addClient(event);
        });
    }
    
    @FXML
    void editClient(ActionEvent event) {
        addClientBTN.setText("Commit");
        addClientBTN.setOnAction((e) -> {
            commitClient(event);
        });
    }
    
    @FXML
    void removeClient(ActionEvent event) {
        
    }
    
    @FXML
    void incrementCheckouts(MouseEvent event) {
        int checknum = Integer.parseInt(checkoutNumTF.getText().split(": ")[1]);
        checknum++;
        checkouts = checknum;
        checkoutNumTF.setText("Chechout #: " + checkouts);
    }
    
    @FXML
    void decrementCheckouts(MouseEvent event) {
        int checknum = Integer.parseInt(checkoutNumTF.getText().split(": ")[1]);
        if (checknum > 1) checknum--;
        checkouts = checknum;
        checkoutNumTF.setText("Chechout #: " + checkouts);
    }
    
    //POST-SIMS
    
    /*GETTERS N SHIT*/
    
    public JFXListView<String> getClientsLV() {
        return clientsLV;
    }
    
    public ArrayList<String> getClients() {
        return clients;
    }
    
    public ArrayList<String> getGames() {
        return games;
    }
    
    public ArrayList<String> getShelves() {
        return shelves;
    }

    public String getParams() {
        return params;
    }

    public void setSimulated(boolean simulated) {
        this.simulated = simulated;
    }
}
