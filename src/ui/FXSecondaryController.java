package ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    //Clients

    @FXML
    private JFXListView<String> clientsLV = new JFXListView<>();

    @FXML
    private JFXTextField clientNameTF = new JFXTextField();

    @FXML
    private JFXTextField clientIDTF = new JFXTextField();

    @FXML
    private JFXListView<Integer> clientGamesLV = new JFXListView<>();

    @FXML
    private JFXButton addClientBTN = new JFXButton();

    @FXML
    private JFXButton editClientBTN = new JFXButton();

    @FXML
    private JFXButton rmClientBTN = new JFXButton();

    /*CLASS FIELDS*/

    private Shop shop;

    private final ObservableList<Integer> clientGamesOL = FXCollections.observableArrayList(435, 235, 231, 578, 102, 004, 743, 332);

    /*METHODS*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientsLV.setOnMouseClicked((event) -> {
            boolean btnsDisabled = clientsLV.getSelectionModel().getSelectedItems().isEmpty();
            rmClientBTN.setDisable(btnsDisabled);
            editClientBTN.setDisable(btnsDisabled);
        });
        boolean btnsDisabled = clientsLV.getSelectionModel().getSelectedItems().isEmpty();
        rmClientBTN.setDisable(btnsDisabled);
        editClientBTN.setDisable(btnsDisabled);
        clientGamesLV.setItems(clientGamesOL);
        clientGamesLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for (int i = 0; i < shop.getClientQueue().size(); i++) {
            Client cl = shop.getClientQueue().front();
            String newClient = cl.getName() + " (" + cl.getCc() + ")";
            clientsLV.getItems().add(newClient);
        }
    }

    //Constructors

    public FXSecondaryController(Shop shop) {
        this.shop = shop;
    }

    //Utility

    //Clients

    @FXML
    void addClient(ActionEvent event) {
        clientsLV.getItems().add(clientNameTF.getText() + " / " + clientIDTF.getText() + " / " + clientGamesLV.getSelectionModel().getSelectedItems().toString());
        clientNameTF.setText("");
        clientIDTF.setText("");
        clientGamesLV.getSelectionModel().clearSelection();
    }

    @FXML
    void commitClient(ActionEvent event) {
        addClientBTN.setText("Añadir");
        addClientBTN.setOnAction((e) -> {
            addClient(event);
        });
    }

    @FXML
    void editClient(ActionEvent event) {
        addClientBTN.setText("Confirmar");
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
}
