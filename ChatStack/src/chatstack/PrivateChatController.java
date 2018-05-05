/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatstack;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hossam
 */
public class PrivateChatController implements Initializable {

    @FXML
    private TextField txt_field;

    @FXML
    private VBox chat;
    
    @FXML
    private JFXButton Send_btn;
    
    @FXML
    private ScrollPane chatScroll;
    
    PeerChatThread pct;
    
     boolean testChat = false;
    
 public void send(ActionEvent e){
        chatScroll.setVvalue(1.0);
        if (testChat) {
            chat.getChildren().add(new SpeechBox(txt_field.getText(), SpeechDirection.LEFT));
            testChat = false;
        } else {
            chat.getChildren().add(new SpeechBox(txt_field.getText(), SpeechDirection.RIGHT));
            testChat = true;
        }
        pct.SendMessage("fuck this shit");
 }
 
     public void recieveGUI(String s) {
         chat.getChildren().add(new SpeechBox(s, SpeechDirection.LEFT));
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
        MainPanelController.pcts.closeConnection();
        System.out.println("Opening Connection");
        pct = new PeerChatThread(new Socket("41.176.48.84", 55554),this);
        System.out.println("Opening Thread");
        pct.start();
        }
        catch(IOException ex){
            System.out.println(ex);
        }
           chatScroll.getStyleClass().add("PChat");
    }    
    
    //<editor-fold defaultstate="collapsed" desc="SpeechBox">
    class SpeechBox extends HBox {

        private Color DEFAULT_SENDER_COLOR = Color.web("#03add7");
        private Color DEFAULT_RECEIVER_COLOR = Color.web("#6c7a9d");
        private Background DEFAULT_SENDER_BACKGROUND, DEFAULT_RECEIVER_BACKGROUND;

        private String message;
        private SpeechDirection direction;

        private Label displayedText;
        private SVGPath directionIndicator;

        public SpeechBox(String message, SpeechDirection direction) {
            this.message = message;
            this.direction = direction;
            initialiseDefaults();
            setupElements();
            

        }


        private void initialiseDefaults() {
            DEFAULT_SENDER_BACKGROUND = new Background(
                    new BackgroundFill(DEFAULT_SENDER_COLOR, new CornerRadii(5, 0, 5, 5, false), Insets.EMPTY));
            DEFAULT_RECEIVER_BACKGROUND = new Background(
                    new BackgroundFill(DEFAULT_RECEIVER_COLOR, new CornerRadii(0, 5, 5, 5, false), Insets.EMPTY));
        }

        private void setupElements() {
            displayedText = new Label(message);
            displayedText.setPadding(new Insets(5));
            displayedText.setWrapText(true);
            directionIndicator = new SVGPath();

            if (direction == SpeechDirection.LEFT) {
                configureForReceiver();
            } else {
                configureForSender();
            }
        }

        private void configureForSender() {

            displayedText.setBackground(DEFAULT_SENDER_BACKGROUND);
            displayedText.setAlignment(Pos.CENTER_RIGHT);
            displayedText.setFont(new Font("Verdana", 13));
            directionIndicator.setContent("M10 0 L0 10 L0 0 Z");
            directionIndicator.setFill(DEFAULT_SENDER_COLOR);
            
;
            HBox container = new HBox(displayedText, directionIndicator);
            
            //Use at most 75% of the width provided to the SpeechBox for displaying the message
            container.maxWidthProperty().bind(widthProperty().multiply(0.75));
            getChildren().setAll(container);
            setAlignment(Pos.CENTER_RIGHT);
        }

        private void configureForReceiver() {

            displayedText.setBackground(DEFAULT_RECEIVER_BACKGROUND);
            displayedText.setAlignment(Pos.CENTER_LEFT);
            displayedText.setFont(new Font("Verdana", 13));
            directionIndicator.setContent("M0 0 L10 0 L10 10 Z");
            directionIndicator.setFill(DEFAULT_RECEIVER_COLOR);


            
            HBox container = new HBox(directionIndicator, displayedText);
            //Use at most 75% of the width provided to the SpeechBox for displaying the message
            container.maxWidthProperty().bind(widthProperty().multiply(0.75));
            getChildren().setAll(container);
            setAlignment(Pos.CENTER_LEFT);
        }
    }

    enum SpeechDirection {
        LEFT, RIGHT
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="TitleBar code DO NOT EDIT">
    //Start of title bar code
    double x, y;

    @FXML
    void dragToolBar(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    void pressToolBar(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void maximize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setFullScreenExitHint(" ");
        stage.setFullScreen(true);
    }

    @FXML
    void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    //end of title bar code
//</editor-fold>

}



    