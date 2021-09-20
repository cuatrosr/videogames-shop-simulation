package ui;

import com.sun.javafx.application.LauncherImpl;
import java.io.File;
import java.io.IOException;
import javafx.application.Preloader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.objects.Shop;

public class FXMain extends Application {

    public static boolean loaded = false;
    private FXController controller;
    private static final int COUNT_LIMIT = 30000;
    private Shop shop;

    public FXMain() throws IOException {
        shop = new Shop();
        controller = new FXController(shop);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/main.fxml"));
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
//        primaryStage.getIcons().add(new Image(new File("resources/image/baancc.png").toURI().toString()));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Configure simulation");
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        for (int i = 0; i < COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
        }
    }

    public static void main(String[] args) {
        LauncherImpl.launchApplication(FXMain.class, FXSplash.class, args);
    }

    @Override
    public void stop() throws Exception {
        System.out.println("App stopped.");
    }
}
