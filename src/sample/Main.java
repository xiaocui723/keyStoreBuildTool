package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.controller.MainController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Main.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("签名生成工具");
        primaryStage.setScene(new Scene(root));
//        primaryStage.setResizable(false);
        primaryStage.show();

        MainController mainController = loader.getController();
        mainController.setStage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
