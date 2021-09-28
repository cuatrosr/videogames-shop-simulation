package ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
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

    @FXML
    private Label shelfGamesLBL = new Label();

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

    @FXML
    private ToggleGroup sortingTGRP = new ToggleGroup();

    //Stage 1

    @FXML
    private JFXListView<String> stage1LV = new JFXListView<>();
    
    //Stage 2

    @FXML
    private JFXListView<String> stage2LV = new JFXListView<>();
    
    //Stage 3

    @FXML
    private JFXListView<?> stage3LV;

    //Stage 4

    /*CLASS FIELDS*/
    
    private Shop shop;
    
    ArrayList<String> shelves = new ArrayList<>();

    ArrayList<String> games = new ArrayList<>();
    
    ArrayList<String> clients = new ArrayList<>();
    
    private FXController controller;
    
    private int checkouts;
    
    private boolean simulated;

    private int selectedSortMethod;

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
            String shelfCode = shelf.replace(":", "");
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
        switch (selectedSortMethod) {
            case 1:
                sortingTGRP.selectToggle(sortingTGRP.getToggles().get(0));
                break;
            case 2:
                sortingTGRP.selectToggle(sortingTGRP.getToggles().get(1));
                break;
            case -1:
                sortingTGRP.selectToggle(sortingTGRP.getToggles().get(2));
                break;
            default:
                break;
        }
    }
    
    private void initParams() {
        checkoutNumTF.setEditable(false);
        checkoutNumTF.setText("Checkout #: " + checkouts);
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
            stage2LV.getItems().add("Client ordered list: " + Arrays.toString(client.getGames()) + " (Current time: " + client.getTime() + ")");
        }
        shop.getClientQueue().toQueue(clientsArr);
    }
    
    //Constructors
    
    public FXSecondaryController(Shop shop, FXController controller, boolean simulated) {
        this.simulated = simulated;
        this.shop = shop;
        this.controller = controller;
        checkouts = 1;
        selectedSortMethod = 0;
    }
    
    //Utility
    
    //Shelves

    boolean validateShelf() {
        return !shelfIDTF.getText().isEmpty();
    }

    @FXML
    void addShelf(ActionEvent event) {
        if (validateShelf()) {
            String newShelf = shelfIDTF.getText() + ":";
            shelvesLV.getItems().add(newShelf);
            shelves.add(newShelf);
            shelfIDTF.setText("");
        } else {
            try {
                controller.launchFXML("dialogue.fxml", this, "Unable to add shelf",
                        Modality.APPLICATION_MODAL, StageStyle.UNDECORATED, true, false);
            } catch (Exception ignored) {
                System.out.println("There will be consequences");
            }
        }
    }
    
    @FXML
    void editShelf(ActionEvent event) {
        addShelfBTN.setText("Commit");
        addShelfBTN.setOnAction((e) -> { // 6017441818 <- No borren esto xfa
            commitShelf(event);
        });
        shelfIDTF.setOnAction((e) -> {
            commitShelf(event);
        });
        String editing = shelvesLV.getSelectionModel().getSelectedItem();
        String shelfCode = editing.split(": ")[0].replace(":", "");
        String gameList;
        try {
            gameList = editing.split(": ")[1];
        } catch (IndexOutOfBoundsException iub) {
            gameList = "(empty)";
        }
        shelfIDTF.setText(shelfCode);
        shelfGamesLBL.setText("Current game list (Shelf " + shelfCode + "): " + gameList);
        shelvesLV.getItems().remove(editing);
        shelves.remove(editing);
    }

    @FXML
    void commitShelf(ActionEvent event) {
        if (validateShelf()) {
            String gameList = shelfGamesLBL.getText().contains("empty") ? "" : shelfGamesLBL.getText().replace("Current game list:", "");
            String newShelf = shelfIDTF.getText() + ":" + gameList;
            shelvesLV.getItems().add(newShelf);
            shelves.add(newShelf);
            shelfIDTF.setText("");
            shelfGamesLBL.setText("");
            addShelfBTN.setText("Add");
            addShelfBTN.setOnAction((e) -> {
                addShelf(event);
            });
            shelfIDTF.setOnAction((e) -> {
                addShelf(event);
            });
        } else {
            try {
                controller.launchFXML("dialogue.fxml", this, "Unable to add shelf",
                        Modality.APPLICATION_MODAL, StageStyle.UNDECORATED, true, false);
            } catch (Exception ignored) {
                System.out.println("There will be consequences");
            }
        }
    }

    void lookUpShelf(String code, String newCode, String mode) {
        switch (mode) {
            case "replace":
                for (String game : games) {
                    games.set(games.indexOf(game), game.replace(code, newCode));
                }
                break;
            case "remove":
                games.removeIf(game -> game.contains(code));
                break;
            default:
                throw new IllegalStateException("Illegal argument: " + mode);
        }
    }

    @FXML
    void removeShelf(ActionEvent event) {
        shelvesLV.getItems().remove(shelvesLV.getSelectionModel().getSelectedItem());
        String remCode = shelvesLV.getSelectionModel().getSelectedItem().split(": ")[0];
        lookUpShelf(remCode, remCode, "remove");
    }
    
    //Games
    
    @FXML
    void addGame(ActionEvent event) {
        String newGame = gameNameTF.getText() + " / $" + gamePriceTF.getText() + " / " + gameIDTF.getText() + " / " + gameShelvesCBX.getSelectionModel().getSelectedItem().split(" \\(")[0] + " / x" + gameAmountTF.getText();
        gamesLV.getItems().add(newGame);
        games.add(newGame);
        for (String shelf : shelves) {
            if (shelf.contains(gameShelvesCBX.getSelectionModel().getSelectedItem())) {
                String separator = !shelf.contains(" ") ? " ": ", ";
                int index = shelves.indexOf(shelf);
                shelves.set(index, shelf + separator + gameIDTF.getText());
                break;
            }
        }
        gameNameTF.setText("");
        gameIDTF.setText("");
        gamePriceTF.setText("");
        gameShelvesCBX.getSelectionModel().clearSelection();
        gameAmountTF.setText("");
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
        checkoutNumTF.setText("Checkout #: " + checkouts);
    }
    
    @FXML
    void decrementCheckouts(MouseEvent event) {
        int checknum = Integer.parseInt(checkoutNumTF.getText().split(": ")[1]);
        if (checknum > 1) checknum--;
        checkouts = checknum;
        checkoutNumTF.setText("Checkout #: " + checkouts);
    }

    @FXML
    void selectAlgorithmToggle(ActionEvent event) {
        JFXToggleNode clickedSort = ((JFXToggleNode)event.getSource());
        System.out.println(clickedSort.toString());
        selectedSortMethod = clickedSort.getText().contains("Random") ? -1 : Integer.parseInt(clickedSort.getText());
        for (Toggle node : sortingTGRP.getToggles()) {
            double glowVal = (node.isSelected()) ? 0.8 : 0;
            System.out.println(glowVal);
            ((JFXToggleNode) node).setEffect(new Glow(glowVal));
        }
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

    public int getCheckoutNum() {
        return checkouts;
    }

    public int getSelectedSortMethod() {
        return selectedSortMethod;
    }

    public void setSimulated(boolean simulated) {
        this.simulated = simulated;
    }
}
