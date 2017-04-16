package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Bicycle on 2017/4/15.
 */
public class MainController implements Initializable{
    public AnchorPane mainMenuPane;
    public AnchorPane mainContentPane;
    public ListView<String> mainMenuList;

    private Stage mStage;

    public MainController() {

    }

    public void setStage(Stage stage) {
        mStage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init");
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("签名生成");
        items.add("包签名");
        this.mainMenuList.setItems(items);

        this.mainMenuList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        this.mainMenuList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(this.mainMenuList.getSelectionModel().getSelectedIndex());
            switch (this.mainMenuList.getSelectionModel().getSelectedIndex()) {
                case 0:
                    this.mainContentPane.getChildren().clear();
                    loadKeyStoreBuildView();
                    break;
                case 1:
                    this.mainContentPane.getChildren().clear();
                    break;
            }
        });
        this.mainMenuList.getSelectionModel().select(0);
    }

    private void loadKeyStoreBuildView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/KeyStoreBuild.fxml"));
        try {
            GridPane gridPane = loader.load();
            this.mainContentPane.getChildren().add(gridPane);
            AnchorPane.setBottomAnchor(gridPane, 0.0);
            AnchorPane.setLeftAnchor(gridPane, 0.0);
            AnchorPane.setRightAnchor(gridPane, 0.0);
            AnchorPane.setTopAnchor(gridPane, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
