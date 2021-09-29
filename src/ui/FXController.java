package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import java.io.File;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.data_structures.DefaultHashTable;
import model.data_structures.DefaultStack;
import model.objects.Client;
import model.objects.Shelf;
import model.objects.Shop;

public class FXController implements Initializable {

    /*JAVAFX FIELDS*/
    @FXML
    private BorderPane currentBP;

    //Preloader
    @FXML
    private ImageView iLogo;

    @FXML
    private BorderPane mainPaneBP = new BorderPane();

    //Lateral Menu
    @FXML
    private VBox lateralVBOX = new VBox();

    @FXML
    private JFXHamburger lateralHBG = new JFXHamburger();

    @FXML
    private JFXDrawer lateralMenuDW = new JFXDrawer();

    HamburgerNextArrowBasicTransition lateralHBGTransition;

    private boolean extended;

    private Rectangle2D screenBounds;

    /*CLASS FIELDS*/

    private FXSecondaryController secondaryController;

    private String loadedPane;

    private Shop shop;

    private String currDrawer;

    private boolean simulated;

    /*METHODS*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        screenBounds = Screen.getPrimary().getBounds();
        double prefWidth = screenBounds.getWidth() * 0.9;
        double prefHeight = screenBounds.getHeight() * 0.85;
        mainPaneBP.setPrefSize(prefWidth, prefHeight);
        lateralHBGTransition = new HamburgerNextArrowBasicTransition(lateralHBG);
        lateralHBGTransition.setRate(-1.5);
        lateralHBG.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            lateralHBGTransition.setRate(lateralHBGTransition.getRate() * -1);
            lateralHBGTransition.play();
        });
        lateralVBOX.setPrefWidth(50);
    }

    //Constructors
    public FXController(Shop shop) {
        simulated = false;
        currDrawer = "fxml/drawer.fxml";
        this.shop = shop;
        extended = false;
        loadedPane = "none";
        secondaryController = new FXSecondaryController(shop, this, simulated);
    }

    //Utility
    public void preload() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(currDrawer));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            lateralMenuDW.setSidePane(root);
        } catch (IOException ignored) {
        }
    }

    public void launchFXML(String fxml, Object controller, String title, Modality modality, StageStyle style, boolean windowed, boolean resizable) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/" + fxml));
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        if (windowed) {
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            if (resizable) {
                stage.setMinHeight(750);
                stage.setMinWidth(1400);
            }
            stage.initModality(modality);
            stage.getIcons().add(new Image(FXController.class.getResourceAsStream("resources/img/gamex.png")));
            stage.initStyle(style);
            stage.setTitle(title);
            stage.setResizable(resizable);
            stage.show();
        } else {
            ((Stage) mainPaneBP.getScene().getWindow()).setTitle(title);
            ((Stage) mainPaneBP.getScene().getWindow()).setResizable(resizable);
            currentBP.setCenter(root);
        }
    }

    //Lateral Menu
    @FXML
    void mainToggleHamburger(MouseEvent event) {
        preload();
        int contractedWidth = 50;
        int extendedWidth = 130;
        int width = extended ? contractedWidth : extendedWidth;
        lateralVBOX.setPrefWidth(width);
        lateralMenuAnimation(width);
        if (lateralMenuDW.isClosed() && width != contractedWidth) {
            lateralMenuDW.open();
        } else {
            lateralMenuDW.close();
        }
        extended = !extended;
    }

    private void lateralMenuAnimation(int width) {
        if (!extended) {
            width *= -1;
        }
        lateralVBOX.translateXProperty().set(width);
        Timeline t = new Timeline();
        KeyValue kv = new KeyValue(lateralVBOX.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.2), kv);
        t.getKeyFrames().addAll(kf);
        t.play();
    }

    //Main pane

    @FXML
    void shelvesClicked(ActionEvent event) {
        if (!loadedPane.equals("shelves")) {
            try {
                launchFXML("shelves.fxml", secondaryController, "Setup Shelves", Modality.NONE, StageStyle.UNIFIED, false, true);
                loadedPane = "shelves";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void gamesClicked(ActionEvent event) {
        if (!loadedPane.equals("games")) {
            try {
                launchFXML("games.fxml", secondaryController, "Setup Games", Modality.NONE, StageStyle.UNIFIED, false, true);
                loadedPane = "games";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void clientsClicked(ActionEvent event) {
        if (!loadedPane.equals("clients")) {
            try {
                launchFXML("clients.fxml", secondaryController, "Setup Clients", Modality.NONE, StageStyle.UNIFIED, false, true);
                loadedPane = "clients";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void startSimulation(ActionEvent event) throws NumberFormatException, Exception {
        //Setup
        shop.setSellers(secondaryController.getCheckoutNum());
        ArrayList<String> shelves = secondaryController.getShelves();
        int amountShelf = shelves.size();
        shop.setShelves(new Shelf[amountShelf]);
        shop.createShelf(shelves, secondaryController.getGames());
        stage1();
        stage2();
        stage3();
        stage4();
        //Launch
        try {
            simulated = !simulated;
            secondaryController.setSimulated(simulated);
            currDrawer = "fxml/drawer-post.fxml";
            ((Stage)((Node) event.getSource()).getScene().getWindow()).close();
            launchFXML("main.fxml", this, "Results", Modality.WINDOW_MODAL, StageStyle.DECORATED, true, true);
            mainToggleHamburger(null);
        } catch (Exception e) {
            System.out.println("Can't open.");
            e.printStackTrace();
        }
    }
    
    void stage1() {
        ArrayList<String> clientListRaw = secondaryController.getClients();
        int num = clientListRaw.size();
        shop.setGamesHash(new DefaultHashTable<Integer, DefaultStack<Integer>>(num));
        for (int i = 0; i < num; i++) {
            String[] curr = clientListRaw.get(i).split(" / ");
            String gamesRaw = curr[2].replaceAll("\\[|\\]", "");
            String name = curr[0];
            int sorting = curr[3].contains("Insertion") ? 1 : 2;
            int cc = Integer.parseInt(curr[1].trim());
            System.out.println("Client #" + (i + 1) + ": Name: " + name + ", ID: " + cc + ", Games: [" + gamesRaw + "], Time: " + (i + 1));
            try {
                shop.createClients(name, cc, gamesRaw, num, i, sorting);
                shop.getClientQueue().toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    void stage2() {
        Client[] clients = shop.getClientQueue().toClientArray();
        for (Client client : clients) {
            client.setGames(shop.getTablet().orderInsertSort(shop.getgamesHash().search(client.getCc()).toArray(), shop.getShelves()));
            System.out.println(shop.getgamesHash().search(client.getCc()));
            client.setAmountGames(client.getGames().length);
            client.setTime(client.getTime() + client.getAmountGames());
        }
        shop.selectionSort(clients);
        recurSwitch(clients);
    }

    void recurSwitch(Client[] clients) {
        for (Client client : clients) {
            int param = client.getSelectedSortingMethod();
            switch (param) {
                case 1:
                    shop.getTablet().orderInsertSort(shop.getgamesHash().search(client.getCc()).toArray(), shop.getShelves());
                    break;
                case 2:
                    shop.getTablet().orderSelectionSort(shop.getgamesHash().search(client.getCc()).toArray(), shop.getShelves());
                    break;
                default:
                    throw new IllegalStateException("How: " + param);
            }
        }
    }

    void stage3() {
        Client[] clients = shop.getClientQueue().toClientArray();
        shop.getTablet().clientList(clients, shop);
        shop.selectionSort(clients);
    }

    void stage4() {
        shop.sellers(shop.getClientQueue().size());
    }
    
    //Post Simulation
    
    @FXML
    void stage1Clicked(ActionEvent event) {
        if (!loadedPane.equals("Stage-1")) {
            try {
                launchFXML("stage-1.fxml", secondaryController, "Stage 1 Results", Modality.NONE, StageStyle.UNIFIED, false, true);
                loadedPane = "Stage-1";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    void stage2Clicked(ActionEvent event) {
        if (!loadedPane.equals("Stage-2")) {
            try {
                launchFXML("stage-2.fxml", secondaryController, "Stage 2 Results", Modality.NONE, StageStyle.UNIFIED, false, true);
                loadedPane = "Stage-2";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    void stage3Clicked(ActionEvent event) {
        if (!loadedPane.equals("Stage-3")) {
            try {
                launchFXML("stage-3.fxml", secondaryController, "Stage 3 Results", Modality.NONE, StageStyle.UNIFIED, false, true);
                loadedPane = "Stage-3";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    void stage4Clicked(ActionEvent event) {
        if (!loadedPane.equals("Stage-4")) {
            try {
                launchFXML("stage-4.fxml", secondaryController, "Stage 4 Results", Modality.NONE, StageStyle.UNIFIED, false, true);
                loadedPane = "Stage-4";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    void finishClicked(ActionEvent event) {
        try {
            simulated = !simulated;
            secondaryController.setSimulated(simulated);
            currDrawer = "fxml/drawer.fxml";
            ((Stage)((Node) event.getSource()).getScene().getWindow()).close();
            launchFXML("main.fxml", this, "Configure simulation", Modality.WINDOW_MODAL, StageStyle.DECORATED, true, true);
            mainToggleHamburger(null);
        } catch (Exception e) {
            System.out.println("Can't open.");
            e.printStackTrace();
        }
    }
    
    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }
    
    /*GETTERS*/
    
    public String getLoadedPane() {
        return loadedPane;
    }

    public Rectangle2D getScreenBounds() {
        return screenBounds;
    }
}
