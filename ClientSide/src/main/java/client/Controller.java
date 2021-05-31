package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Network network;

    @FXML
    TextField msgField;

    @FXML
    TextArea mainArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        network = new Network((args) -> {
            mainArea.appendText((String)args[0]);
        }); // лучше соединяться с сервером при авотризации, а не при открытии окна
    }

   /* public void sendMsgAction(ActionEvent actionEvent) {
        network.sendMessage(msgField.getText());
        msgField.clear();
        msgField.requestFocus();
    }*/

    public void pressAuthButton(ActionEvent actionEvent) {
        network.sendMessage("/Auth " + msgField.getText());
        msgField.clear();
        mainArea.clear();
        msgField.requestFocus();
    }
    public void pressMkDirButton(ActionEvent actionEvent) {
        network.sendMessage("/Make " + msgField.getText());
        msgField.clear();
        msgField.requestFocus();
    }
    public void pressDelButton(ActionEvent actionEvent) {
        network.sendMessage("/Del " + msgField.getText());
        msgField.clear();
        msgField.requestFocus();
    }
    public void pressUploadButton(ActionEvent actionEvent) {
        network.sendMessage("/Upload " + msgField.getText());
        msgField.clear();
        msgField.requestFocus();
    }
    public void pressDownloadButton(ActionEvent actionEvent) {
        network.sendMessage("/Download " + msgField.getText());
        msgField.clear();
        msgField.requestFocus();
    }
    public void pressSizeButton(ActionEvent actionEvent) {
        network.sendMessage("/Size " + msgField.getText());
        msgField.clear();
        msgField.requestFocus();
    }
    public void pressChangeButton(ActionEvent actionEvent) {
        network.sendMessage("/Change " + msgField.getText());
        msgField.clear();
        msgField.requestFocus();
    }
    public void pressSearchButton(ActionEvent actionEvent) {
        network.sendMessage("/Search " + msgField.getText());
        msgField.clear();
        msgField.requestFocus();
    }
    public void pressSortButton(ActionEvent actionEvent) {
        network.sendMessage("/Sort " + msgField.getText());
        msgField.clear();
        msgField.requestFocus();
    }
    public void exitAction() {
        network.close();
        Platform.exit();
    }
}