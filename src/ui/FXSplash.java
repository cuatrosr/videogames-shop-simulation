package ui;

import java.io.File;
import java.io.IOException;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXSplash extends Preloader {

    //---------------------------- Attributes of FxSplash class ----------------------------\\
    @SuppressWarnings("FieldMayBeFinal")
    private FXController controller;
    private Stage preloaderStage;
    private Scene scene;

    //-------------------------- Constructor class --------------------------\\
    /**
     * FXSplash class constructor, initialize all relations.
     * @throws java.io.IOException
     */
    public FXSplash() throws IOException {
        controller = new FXController(FXMain.getShop());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/welcome.fxml"));
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        scene = new Scene(root);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;
        scene.setFill(Color.TRANSPARENT);
        preloaderStage.initStyle(StageStyle.TRANSPARENT);
        preloaderStage.setScene(scene);
        preloaderStage.setResizable(false);
        preloaderStage.getIcons().add(new Image(FXSplash.class.getResourceAsStream("resources/img/gamex.png")));
        preloaderStage.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {
        Preloader.StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_INIT:
                break;
            case BEFORE_START:
                preloaderStage.hide();
                break;
            case BEFORE_LOAD:
                break;
        }
    }
}
