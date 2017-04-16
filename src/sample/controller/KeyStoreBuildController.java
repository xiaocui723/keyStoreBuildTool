package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;
import sample.bean.KeyStore;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

public class KeyStoreBuildController extends GridPane implements Initializable{

    private String jdkPath;
    private String keytoolPath;

    private Stage mStage;

    public Text actionTarget;
    public PasswordField passwordField;
    public TextField keyStorePath;
    public TextField aliasName;
    public TextField aliasPassword;
    public TextField validity;
    public TextField creatorName;
    public TextField keyPassword;

    public KeyStoreBuildController() {

        this.jdkPath = getJdkPath(System.getProperty("java.home"));
        this.keytoolPath = getKeytoolPath(this.jdkPath);
    }

    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        actionTarget.setText("Sign in button pressed : " + passwordField.getText());
    }

    public void handleOpenFileChooserButtonAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Save KeyStore File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("KeyStore", "*.keystore"));
        File file = fileChooser.showSaveDialog(mStage);
        keyStorePath.setText(file.getAbsolutePath());
    }

    public void setStage(Stage stage) {
        mStage = stage;
        actionTarget.setText(keytoolPath);
    }

    private String getJdkPath(String jdkPath) {

        String path = "";

        if (jdkPath == null || "".equals(jdkPath)) {
            return "";
        }

        File file = new File(jdkPath);
        String fileName = file.getName();
        if ("jre".equals(fileName)) {
            path = file.getAbsolutePath() + File.separator + ".." + File.separator + "bin";
        } else if ("bin".equals(fileName)) {
            path = file.getAbsolutePath();
        }
        return path;
    }

    private String getKeytoolPath(String jdkPath) {
        String path = "";
        File file = new File(jdkPath);
        path = file.getAbsolutePath() + File.separator + "keytool.exe";
        return path;
    }

    public void handleCreateButtonAction(ActionEvent actionEvent) {

        if (checkParamsOfNull()) {
            actionTarget.setText("Params is null!");
            return;
        }

        StringBuilder sb = new StringBuilder(keytoolPath);
        sb.append(" -genkey");
        sb.append(" -v");
        sb.append(" -keystore");
        sb.append(" " + keyStorePath.getText());
        sb.append(" -storepass");
        sb.append(" " + keyPassword.getText());
        sb.append(" -alias");
        sb.append(" " + aliasName.getText());
        sb.append(" -keypass");
        sb.append(" " + aliasPassword.getText());
        sb.append(" -dname");
        sb.append(" CN=" + creatorName.getText() + ",OU=,O=,L=,S=,C=");
        sb.append(" -keyalg RSA");
        sb.append(" -keysize 2048");
        sb.append(" -validity");
        sb.append(" " + validity.getText());

        actionTarget.setText(sb.toString());

        String ret = executeCmd(sb.toString());

        if (!"".equals(ret)) {
            errorMessageDialog(ret);
        } else {
            informationMessageDialog("成功");
        }
    }

    private boolean checkParamsOfNull() {

        if (aliasName.getText() == null || "".equals(aliasName.getText())) {
            return true;
        }

        if (aliasPassword.getText() == null || "".equals(aliasPassword.getText())) {
            return true;
        }

        if (keyPassword.getText() == null || "".equals(keyPassword.getText())) {
            return true;
        }

        if (validity.getText() == null || "".equals(validity.getText())) {
            return true;
        }

        if (creatorName.getText() == null || "".equals(creatorName.getText())) {
            return true;
        }

        if (keyStorePath.getText() == null || "".equals(keyStorePath.getText())) {
            return true;
        }

        return false;
    }

    private String executeCmd(String cmd) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            br = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            System.out.println(sb.toString());
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    private void errorMessageDialog(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.setHeaderText(null);
        alert.initOwner(mStage);
        alert.show();
    }

    private void informationMessageDialog(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.setHeaderText(null);
        alert.initOwner(mStage);
        alert.show();
    }

    private void saveKeyStoreInfoToFile(File file, String aliasName, String aliasPassword, String storePassword) {
        try {
            JAXBContext context = JAXBContext.newInstance(KeyStore.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            KeyStore keyStore = new KeyStore();
            keyStore.setAliasName(aliasName);
            keyStore.setAliasPassword(aliasPassword);
            keyStore.setStorePassword(storePassword);

            marshaller.marshal(keyStore, file);


        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
