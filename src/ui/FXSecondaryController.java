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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.data_structures.DefaultQueue;
import model.objects.Client;
import model.objects.Shop;

@SuppressWarnings("StringConcatenationInLoop")
public class FXSecondaryController implements Initializable{
    
    /*JAVAFX FIELDS*/

    //Error Pane

    @FXML
    private Label titleErrLBL = new Label();

    @FXML
    private Label msgErrLBL = new Label();

    //Shelves

    @FXML
    private JFXListView<String> shelvesLV = new JFXListView<>();

    @FXML
    private JFXTextField shelfIDTF = new JFXTextField();

    @FXML
    private JFXButton addShelfBTN = new JFXButton();

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
    private JFXListView<String> stage3LV;

    //Stage 4

    /*CLASS FIELDS*/

    private Shop shop;

    ArrayList<String> shelves = new ArrayList<>();

    ArrayList<String> games = new ArrayList<>();

    ArrayList<String> clients = new ArrayList<>();

    private FXController controller;

    private int checkouts;

    private boolean simulated;

    private String selectedSorting;

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
        });
        boolean btnsDisabled = shelvesLV.getSelectionModel().getSelectedItems().isEmpty();
        rmShelfBTN.setDisable(btnsDisabled);
        for (String shelf : shelves) {
            shelvesLV.getItems().add(shelf);
        }
    }

    private void initGames() {
        gamesLV.setOnMouseClicked((event) -> {
            boolean btnsDisabled = gamesLV.getSelectionModel().getSelectedItems().isEmpty();
            rmGameBTN.setDisable(btnsDisabled);
        });
        boolean btnsDisabled = gamesLV.getSelectionModel().getSelectedItems().isEmpty();
        rmGameBTN.setDisable(btnsDisabled);
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
        });
        boolean btnsDisabled = clientsLV.getSelectionModel().getSelectedItems().isEmpty();
        rmClientBTN.setDisable(btnsDisabled);
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
        selectedSorting = "Selected sort: ";
    }

    //Utility

    @FXML
    void close(ActionEvent event) {
        ((Stage) msgErrLBL.getScene().getWindow()).close();
    }

    //Shelves

    boolean validateShelf() {
        boolean codeFilled = !shelfIDTF.getText().isEmpty();
        boolean noRepeatedCode = true;
        for (String shelf: shelves) {
            if (shelf.contains(shelfIDTF.getText())) {
                noRepeatedCode = false;
                break;
            }
        }
        return codeFilled && noRepeatedCode;
    }

    @FXML
    void addShelf(ActionEvent event) {
        titleErrLBL.setText("Error: can't add shelf");
        msgErrLBL.setText("The shelf couldn't be added because either it already exists or the code is empty. Please check and try again");
        if (validateShelf()) {
            String newShelf = shelfIDTF.getText() + ":";
            shelvesLV.getItems().add(newShelf);
            shelves.add(newShelf);
            shelfIDTF.setText("");
        } else {
            System.out.println("Here");
            try {
                controller.launchFXML("dialogue.fxml", this, "Unable to add shelf",
                        Modality.APPLICATION_MODAL, StageStyle.DECORATED, true, false);
            } catch (Exception ignored) {
                System.out.println("There will be consequences");
            }
        }
    }

    void lookUpShelf(String code) {
        for (String game: games) {
            if (game.contains(code)) {
                games.remove(game);
                String gameCode = game.split(" / ")[2];
                for (String client: clients) {
                    clients.set(clients.indexOf(client), client.replace(", " + gameCode, ""));
                }
            }
        }
    }

    @FXML
    void removeShelf(ActionEvent event) {
        shelvesLV.getItems().remove(shelvesLV.getSelectionModel().getSelectedItem());
        String remCode = shelvesLV.getSelectionModel().getSelectedItem().split(": ")[0];
        lookUpShelf(remCode);
    }

    //Games

    boolean validateGame() {
        boolean allInfoPresent = !gameNameTF.getText().isEmpty() && !gameIDTF.getText().isEmpty() &&
                !gamePriceTF.getText().isEmpty() && !gameAmountTF.getText().isEmpty() && !gameShelvesCBX.getSelectionModel().isEmpty();
        boolean noRepeatedInfo = true;
        for (String game: games) {
            if (game.contains(gameNameTF.getText()) || game.contains(gameIDTF.getText())) {
                noRepeatedInfo = false;
                break;
            }
        }
        return allInfoPresent && noRepeatedInfo;
    }

    @FXML
    void addGame(ActionEvent event) {
        titleErrLBL.setText("Error: can't add game");
        msgErrLBL.setText("The game couldn't be added because either it already exists or the information is incomplete. Please check and try again");
        if (validateGame()) {
            String newGame = gameNameTF.getText() + " / $" + gamePriceTF.getText() + " / " + gameIDTF.getText() + " / " + gameShelvesCBX.getSelectionModel().getSelectedItem().split(" \\(")[0] + " / x" + gameAmountTF.getText();
            gamesLV.getItems().add(newGame);
            games.add(newGame);
            for (String shelf : shelves) {
                if (shelf.contains(gameShelvesCBX.getSelectionModel().getSelectedItem())) {
                    String separator = !shelf.contains(" ") ? " " : ", ";
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
        } else {
            try {
                controller.launchFXML("dialogue.fxml", this, "Unable to add game",
                        Modality.APPLICATION_MODAL, StageStyle.DECORATED, true, false);
            } catch (Exception ignored) {
                System.out.println("There will be consequences");
            }
        }
    }

    @FXML
    void removeGame(ActionEvent event) {
        gamesLV.getItems().remove(shelvesLV.getSelectionModel().getSelectedItem());
        String remCode = gamesLV.getSelectionModel().getSelectedItem().split(" / ")[2];
        lookUpGame(remCode);
    }

    void lookUpGame(String remCode) {
        for (String shelf: shelves) {
            if (shelf.contains(remCode)) {
                shelves.set(shelves.indexOf(shelf), shelf.replace(remCode + ", ", ""));
                break;
            }
        }
    }

    //Clients

    boolean validateClient() {
        boolean allInfoFilled = !clientNameTF.getText().isEmpty() && !clientIDTF.getText().isEmpty() &&
                !clientGamesLV.getSelectionModel().isEmpty();
        boolean noRepeatedInfo = true;
        for (String client: clients) {
            if (client.contains(clientNameTF.getText()) || client.contains(clientIDTF.getText())) {
                noRepeatedInfo = false;
                break;
            }
        }
        return allInfoFilled && noRepeatedInfo;
    }

    @FXML
    void addClient(ActionEvent event) {
        titleErrLBL.setText("Error: can't add client");
        msgErrLBL.setText("The client couldn't be added because either it already exists or the information is incomplete. Please check and try again");
        if (validateClient()) {
            String gameList = clientGamesLV.getSelectionModel().getSelectedItems().toString().replaceAll("\\s(\\([^,]+\\))", "");
            String newClient = clientNameTF.getText() + " / " + clientIDTF.getText() + " / " + gameList + " / " + selectedSorting;
            clientsLV.getItems().add(newClient);
            clients.add(newClient);
            clientNameTF.setText("");
            clientIDTF.setText("");
            clientGamesLV.getSelectionModel().clearSelection();
        } else {
            try {
                controller.launchFXML("dialogue.fxml", this, "Unable to add client",
                        Modality.APPLICATION_MODAL, StageStyle.DECORATED, true, false);
            } catch (Exception ignored) {
                System.out.println("There will be consequences");
            }
        }
    }

    @FXML
    void removeClient(ActionEvent event) {
        clientsLV.getItems().remove(clientsLV.getSelectionModel().getSelectedItem());
        String remCode = clientsLV.getSelectionModel().getSelectedItem().split(" / ")[1];
        lookUpClient(remCode);
    }

    void lookUpClient(String remCode) {
        for (String client: clients) {
            if (client.contains(remCode)) {
                clients.remove(client);
                break;
            }
        }
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
        for (Toggle node : sortingTGRP.getToggles()) {
            double glowVal = (node.isSelected()) ? 0.8 : 0;
            int index = sortingTGRP.getToggles().indexOf(node);
            String code = index == 0 ? "Insertion Sort" : index == 1 ? "Selection Sort" : "Random";
            if (glowVal == 0.8) selectedSorting += code;
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

    public void setSimulated(boolean simulated) {
        this.simulated = simulated;
    }
}
