package ui;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.objects.Shop;
import java.io.IOException;

public class FXMain extends Application {

    public static boolean loaded = false;
    private FXController controller;
    private static final int COUNT_LIMIT = 30000;
    private static Shop shop;

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
        primaryStage.setMinHeight(750);
        primaryStage.setMinWidth(1400);
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

    public static Shop getShop() {
        return shop;
    }
}
