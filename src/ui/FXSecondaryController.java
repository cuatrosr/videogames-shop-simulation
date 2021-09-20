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
import model.objects.Shop;

public class FXSecondaryController implements Initializable{
    
    /*JAVAFX FIELDS*/

    //Clients

    @FXML
    private JFXListView<String> clientsLV;

    @FXML
    private JFXTextField clientNameTF;

    @FXML
    private JFXTextField clientIDTF;

    @FXML
    private JFXComboBox<String> clientGamesCBX;

    @FXML
    private JFXButton addClientBTN;

    /*CLASS FIELDS*/

    ArrayList<String> clientsLVAL = new ArrayList<>();

    Shop shop;

    /*METHODS*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    //Constructors

    //Utility

    //Clients

    @FXML
    void addClient(ActionEvent event) {
        clientsLV.getItems().add(clientNameTF.getText() + " (" + clientIDTF.getText() + ")");
        clientNameTF.setText("");
        clientIDTF.setText("");
        clientGamesCBX.getSelectionModel().clearSelection();
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
}
