package ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tooltip;
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
    
    /*CLASS FIELDS*/
    
    private Shop shop;
    
    private final ObservableList<String> gamesShelvesOL = FXCollections.observableArrayList("A1", "B1", "C1", "D1", "E1", "E2", "H1", "G1", "F1");
    
    private final ObservableList<String> clientsGamesOL = FXCollections.observableArrayList("435", "235", "231", "578", "102", "004", "743", "332");

    private final ObservableList<String> filler = FXCollections.observableArrayList("a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a");
    
    ArrayList<String> shelves = new ArrayList<>();

    ArrayList<String> games = new ArrayList<>();
    
    ArrayList<String> clients = new ArrayList<>();

    String params = "";

    private FXController controller;

    /*METHODS*/
    
    //Initializers

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(controller.getLoadedPane());
        initShelves();
        initGames();
        initClients();
        initParams();
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
            System.out.println("SC:" + shelfCode);
            gameShelvesCBX.getItems().add(shelfCode);
        }
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
            System.out.println("CL: " + client);
            clientsLV.getItems().add(client);
        }
        for (String game : games) {
            String[] gameRaw = game.split(" / ");
            String gameCode = gameRaw[2] + " (" + gameRaw[0] + ")";
            System.out.println("GC:" + gameCode);
            clientGamesLV.getItems().add(gameCode);
        }
    }

    private void initParams() {

    }
    
    //Constructors
    
    public FXSecondaryController(Shop shop, FXController controller) {
        this.shop = shop;
        this.controller = controller;
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
}
