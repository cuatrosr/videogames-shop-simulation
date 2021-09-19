package ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXController implements Initializable {

    /*JAVAFX FIELDS*/
    @FXML
    private BorderPane currentBP;

    //Preloader
    @FXML
    private ImageView iLogo = new ImageView();

    @FXML
    private BorderPane mainPaneBP = new BorderPane();

    //Lateral Menu
    @FXML
    private VBox lateralVBOX = new VBox();

    @FXML
    private JFXHamburger lateralHBG = new JFXHamburger();

    @FXML
    private JFXDrawer lateralMenuDW = new JFXDrawer();

    HamburgerSlideCloseTransition lateralHBGTransition;

    private boolean extended;

    private Rectangle2D screenBounds;

    /*CLASS FIELDS*/
    private FXSecondaryController secondaryController;

    private String loadedPane;

    /*METHODS*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        iLogo.setImage(new Image(new File("resources/image/baanccNegative.png").toURI().toString()));
        screenBounds = Screen.getPrimary().getBounds();
        double prefWidth = screenBounds.getWidth() * 0.9;
        double prefHeight = screenBounds.getHeight() * 0.85;
        mainPaneBP.setPrefSize(prefWidth, prefHeight);
        lateralHBGTransition = new HamburgerSlideCloseTransition(lateralHBG);
        lateralHBGTransition.setRate(-1.5);
        lateralHBG.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            lateralHBGTransition.setRate(lateralHBGTransition.getRate() * -1);
            lateralHBGTransition.play();
        });
//        lateralMenuDW.open();
    }

    //Constructors
    public FXController() {
        extended = false;
        loadedPane = "none";
    }

    //Utility
    public void preload() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/drawer.fxml"));
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
                stage.setMinHeight(screenBounds.getHeight() * 0.5);
                stage.setMinWidth(screenBounds.getWidth() * 0.45);
            }
            stage.initModality(modality);
//            stage.getIcons().add(new Image(new File("resources/image/baancc.png").toURI().toString()));
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
        if (lateralMenuDW.isClosed()) {
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
        KeyValue kv = new KeyValue(lateralVBOX.translateXProperty(), 0, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.2), kv);
        t.getKeyFrames().addAll(kf);
        t.play();
    }

    //Main pane
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
    void miscellaneousClicked(ActionEvent event) {
        if (!loadedPane.equals("misc")) {
            try {
                launchFXML("misc.fxml", secondaryController, "Miscellaneous Parameters", Modality.NONE, StageStyle.UNIFIED, false, true);
                loadedPane = "misc";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }
}
