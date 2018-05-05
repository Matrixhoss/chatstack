/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatstack;

import static chatstack.ChatStack.StageOpened;
import static chatstack.ChatStack.clip1;
import static chatstack.ChatStack.root;
import static chatstack.ChatStack.sc;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.sound.sampled.AudioSystem;

public class GroupChatController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Variables">
    double oldW;
    double oldH;

    private Boolean resizebottom = false;
    private double dx;
    private double dy;
    private double xOffset;
    private double yOffset;
    boolean clicked = false;

    @FXML
    private Label members_label;

    @FXML
    private TextField txt_field;

    @FXML
    private TextField Search_txt;

    @FXML
    private AnchorPane AP;

    @FXML
    private JFXListView<Label> member_LV;

    @FXML
    private Separator Seperator;
    @FXML
    private Separator Seperator_3;

    @FXML
    private Button maxIcon;

    @FXML
    private Separator Seperator_1;

    @FXML
    private Pane pane;

    @FXML
    private ScrollPane ChatScroll;

    @FXML
    private JFXButton Send_btn;

    @FXML
    private Label Name_Label;

    @FXML
    public VBox Vbox;
    @FXML
    private ImageView iv_stack;

    @FXML
    private MenuButton Menu;

    boolean testChat = true;
//</editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StageOpened.setWidth(870);
        StageOpened.setHeight(538);
        StageOpened.setX(850);
        StageOpened.setY(190);
        StageOpened.setMinHeight(500);
        StageOpened.setMinWidth(600);
        oldW = AP.getPrefWidth();
        oldH = AP.getPrefHeight();
        System.out.println("New Height: " + AP.getPrefHeight());

        Menu.setText(ChatStack.client.getUserName());

        AP.setPrefHeight(800);
        AP.setPrefWidth(1280);
        AP.prefHeightProperty().bind(sc.getWindow().heightProperty());
        AP.prefWidthProperty().bind(sc.getWindow().widthProperty());

        Vbox.setAlignment(Pos.TOP_CENTER);
        Label init = new Label("--- Group Chat Start ---");
        init.setAlignment(Pos.CENTER);
        init.setFont(new Font("Verdana", 14));
        init.setTextFill(Color.web("#6c7a9d"));
        Vbox.getChildren().add(init);
        adjustNodes();

        ChatScroll.getStyleClass().add("ChatScroll");
        member_LV.getStyleClass().add("List");
        member_LV.getStyleClass().add("List2");

        ChatStack.client.setVbox(Vbox);

        for (int i = 0; i < 10; i++) {
            Label x = new Label("Test +" + i);
            x.getStyleClass().add("Label11");
            Image image = new Image(getClass().getResourceAsStream("test.png"));
            x.setGraphic(new ImageView(image));
            member_LV.getItems().add(x);

        }

        //
    }

    public void playback3() {
        try {
            clip1 = AudioSystem.getClip();
            InputStream audioSrc = getClass().getResourceAsStream("text.wav");
            clip1.open(AudioSystem.getAudioInputStream(new BufferedInputStream(audioSrc)));
            clip1.start();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void playback4() {
        try {
            clip1 = AudioSystem.getClip();
            InputStream audioSrc = getClass().getResourceAsStream("send.wav");
            clip1.open(AudioSystem.getAudioInputStream(new BufferedInputStream(audioSrc)));
            clip1.start();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void playback5() {
        try {
            clip1 = AudioSystem.getClip();
            InputStream audioSrc = getClass().getResourceAsStream("recieve.wav");
            clip1.open(AudioSystem.getAudioInputStream(new BufferedInputStream(audioSrc)));
            clip1.start();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Send message">
    public void receiveMsg(String msg) {
        this.playback5();
        Vbox.getChildren().add(new SpeechBox(txt_field.getText(), SpeechDirection.LEFT));
        testChat = false;
    }

    @FXML
    public void hand(ActionEvent e) throws IOException, SQLException {
        this.playback3();
        ChatScroll.setVvalue(1.0);
        this.playback4();
        Vbox.getChildren().add(new SpeechBox(txt_field.getText(), SpeechDirection.RIGHT));
        ChatStack.client.sendGroupMsgPacket(4, txt_field.getText());
//            testChat = true;
        txt_field.setText("");

    }

    //
    @FXML
    void Enterhand(KeyEvent event) {
        this.playback3();
        ChatScroll.setVvalue(1.0);
        sc.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                System.out.println("ENTER PRESSED");
                this.playback4();
                Vbox.getChildren().add(new SpeechBox(txt_field.getText(), SpeechDirection.RIGHT));
                try {
                    ChatStack.client.sendGroupMsgPacket(4, txt_field.getText());
                } catch (IOException ex) {
                    Logger.getLogger(GroupChatController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(GroupChatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Resize code">
    public void adjustNodes() {
//        AP.setPrefHeight(1080);
//        AP.setPrefWidth(2560);

        members_label.setPrefHeight(AP.getPrefHeight() * (members_label.getPrefHeight() / oldH));
        members_label.setPrefWidth(AP.getPrefWidth() * (members_label.getPrefWidth() / oldW));
        members_label.setLayoutY(AP.getPrefHeight() * (members_label.getLayoutY() / oldH));
        members_label.setLayoutX(AP.getPrefWidth() * (members_label.getLayoutX() / oldW));

        txt_field.setPrefHeight(AP.getPrefHeight() * (txt_field.getPrefHeight() / oldH));
        txt_field.setPrefWidth(AP.getPrefWidth() * (txt_field.getPrefWidth() / oldW));
        txt_field.setLayoutY(AP.getPrefHeight() * (txt_field.getLayoutY() / oldH));
        txt_field.setLayoutX(AP.getPrefWidth() * (txt_field.getLayoutX() / oldW));

        Search_txt.setPrefHeight(AP.getPrefHeight() * (Search_txt.getPrefHeight() / oldH));
        Search_txt.setPrefWidth(AP.getPrefWidth() * (Search_txt.getPrefWidth() / oldW));
        Search_txt.setLayoutY(AP.getPrefHeight() * (Search_txt.getLayoutY() / oldH));
        Search_txt.setLayoutX(AP.getPrefWidth() * (Search_txt.getLayoutX() / oldW));

        member_LV.setPrefHeight(AP.getPrefHeight() * (member_LV.getPrefHeight() / oldH));
        member_LV.setPrefWidth(AP.getPrefWidth() * (member_LV.getPrefWidth() / oldW));
        member_LV.setLayoutY(AP.getPrefHeight() * (member_LV.getLayoutY() / oldH));
        member_LV.setLayoutX(AP.getPrefWidth() * (member_LV.getLayoutX() / oldW));

        Seperator.setPrefHeight(AP.getPrefHeight() * (Seperator.getPrefHeight() / oldH));
        Seperator.setPrefWidth(AP.getPrefWidth() * (Seperator.getPrefWidth() / oldW));
        Seperator.setLayoutY(AP.getPrefHeight() * (Seperator.getLayoutY() / oldH));
        Seperator.setLayoutX(AP.getPrefWidth() * (Seperator.getLayoutX() / oldW));

        Seperator_1.setPrefHeight(AP.getPrefHeight() * (Seperator_1.getPrefHeight() / oldH));
        Seperator_1.setPrefWidth(AP.getPrefWidth() * (Seperator_1.getPrefWidth() / oldW));
        Seperator_1.setLayoutY(AP.getPrefHeight() * (Seperator_1.getLayoutY() / oldH));
        Seperator_1.setLayoutX(AP.getPrefWidth() * (Seperator_1.getLayoutX() / oldW));

        Seperator_3.setPrefHeight(AP.getPrefHeight() * (Seperator_3.getPrefHeight() / oldH));
        Seperator_3.setPrefWidth(AP.getPrefWidth() * (Seperator_3.getPrefWidth() / oldW));
        Seperator_3.setLayoutY(AP.getPrefHeight() * (Seperator_3.getLayoutY() / oldH));
        Seperator_3.setLayoutX(AP.getPrefWidth() * (Seperator_3.getLayoutX() / oldW));

        pane.setPrefHeight(AP.getPrefHeight() * (pane.getPrefHeight() / oldH));
        pane.setPrefWidth(AP.getPrefWidth() * (pane.getPrefWidth() / oldW));
        pane.setLayoutY(AP.getPrefHeight() * (pane.getLayoutY() / oldH));
        pane.setLayoutX(AP.getPrefWidth() * (pane.getLayoutX() / oldW));

        ChatScroll.setPrefHeight(AP.getPrefHeight() * (ChatScroll.getPrefHeight() / oldH));
        ChatScroll.setPrefWidth(AP.getPrefWidth() * (ChatScroll.getPrefWidth() / oldW));
        ChatScroll.setLayoutY(AP.getPrefHeight() * (ChatScroll.getLayoutY() / oldH));
        ChatScroll.setLayoutX(AP.getPrefWidth() * (ChatScroll.getLayoutX() / oldW));
//
        Send_btn.setPrefHeight(AP.getPrefHeight() * (Send_btn.getPrefHeight() / oldH));
        Send_btn.setPrefWidth(AP.getPrefWidth() * (Send_btn.getPrefWidth() / oldW));
        Send_btn.setLayoutY(AP.getPrefHeight() * (Send_btn.getLayoutY() / oldH));
        Send_btn.setLayoutX(AP.getPrefWidth() * (Send_btn.getLayoutX() / oldW));

        Name_Label.setPrefHeight(AP.getPrefHeight() * (Name_Label.getPrefHeight() / oldH));
        Name_Label.setPrefWidth(AP.getPrefWidth() * (Name_Label.getPrefWidth() / oldW));
        Name_Label.setLayoutY(AP.getPrefHeight() * (Name_Label.getLayoutY() / oldH));
        Name_Label.setLayoutX(AP.getPrefWidth() * (Name_Label.getLayoutX() / oldW));

        Vbox.setPrefHeight(AP.getPrefHeight() * (Vbox.getPrefHeight() / oldH));
        Vbox.setPrefWidth(AP.getPrefWidth() * (Vbox.getPrefWidth() / oldW));
        Vbox.setLayoutY(AP.getPrefHeight() * (Vbox.getLayoutY() / oldH));
        Vbox.setLayoutX(AP.getPrefWidth() * (Vbox.getLayoutX() / oldW));

        iv_stack.setFitHeight(AP.getPrefHeight() * (iv_stack.getFitHeight() / oldH));
        iv_stack.setFitWidth(AP.getPrefWidth() * (iv_stack.getFitWidth() / oldW));
        iv_stack.setLayoutY(AP.getPrefHeight() * (iv_stack.getLayoutY() / oldH));
        iv_stack.setLayoutX(AP.getPrefWidth() * (iv_stack.getLayoutX() / oldW));

        Menu.setLayoutY(AP.getPrefHeight() * (Menu.getLayoutY() / oldH));
        Menu.setLayoutX(AP.getPrefWidth() * (Menu.getLayoutX() / oldW));

        oldW = AP.getPrefWidth();
        oldH = AP.getPrefHeight();

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
    void close(MouseEvent event) throws SQLException, IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        ChatStack.db.setMemeberOffline(ChatStack.client.getUserName());
        ChatStack.client.closeConnection();
    }

    @FXML
    void maximize(ActionEvent event) {
        if (clicked == false) {
            StageOpened.setFullScreenExitHint("Press ESC to exit Full Screen");
//            StageOpened.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            StageOpened.setFullScreen(true);
            clicked = true;
        } else if (clicked == true && StageOpened.isFullScreen()) {
            StageOpened.setFullScreen(false);
            clicked = false;
        }
        if (StageOpened.isFullScreen()) {
            sc.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ESCAPE) {
                    StageOpened.setFullScreen(false);
                    adjustNodes();
                    clicked = false;

                }
            });
        }
        adjustNodes();
    }

    @FXML
    void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void getCords(MouseEvent event) {
        if (event.getX() > StageOpened.getWidth() - 10
                && event.getX() < StageOpened.getWidth() + 10
                && event.getY() > StageOpened.getHeight() - 10
                && event.getY() < StageOpened.getHeight() + 10) {
            resizebottom = true;
            dx = StageOpened.getWidth() - event.getX();
            dy = StageOpened.getHeight() - event.getY();
        } else {
            resizebottom = false;
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        }
    }

    @FXML
    void adjustWindow(MouseEvent event) {
        if (resizebottom == false) {
//                StageOpened.setX(event.getScreenX() - xOffset);
//                StageOpened.setY(event.getScreenY() - yOffset);

        } else {

            if (!((event.getX() + dx) < 600)) {
                StageOpened.setWidth(event.getX() + dx);
            }
            if (!((event.getY() + dx) < 500)) {
                StageOpened.setHeight(event.getY() + dy);
            }
        }
        adjustNodes();
    }

    @FXML
    void menuLogout(ActionEvent event) throws IOException, SQLException {
        ChatStack.db.setMemeberOffline(ChatStack.client.getUserName());
        ChatStack.client.closeConnection();
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        sc = new Scene(root);
        StageOpened.setWidth(500);
        StageOpened.setHeight(375);
        StageOpened.setScene(sc);
    }

    @FXML
    void menuExit(ActionEvent event) throws SQLException, IOException {
        StageOpened.close();
        ChatStack.db.setMemeberOffline(ChatStack.client.getUserName());
        ChatStack.client.closeConnection();

    }

    @FXML
    void menuLeavegrp(ActionEvent event) throws SQLException, IOException {
        ChatStack.client.leaveGroup();
        
        root = FXMLLoader.load(getClass().getResource("MainPanel.fxml"));
        sc = new Scene(root);
        StageOpened.setScene(sc);
    }
    //end of title bar code
//</editor-fold>

}
//<editor-fold defaultstate="collapsed" desc="SpeechBox">

class SpeechBox extends HBox {

    private Color DEFAULT_SENDER_COLOR = Color.web("#03add7");
    private Color DEFAULT_RECEIVER_COLOR = Color.web("#6c7a9d");
    private Background DEFAULT_SENDER_BACKGROUND, DEFAULT_RECEIVER_BACKGROUND;

    private String message;
    private SpeechDirection direction;

    private Label displayedText;
    private Label name;
    private SVGPath directionIndicator;

    public SpeechBox(String message, SpeechDirection direction) {
        this.message = message;
        this.direction = direction;
        initialiseDefaults();
        setupElements();

    }

    public SpeechBox(String message, SpeechDirection direction, String name) {
        this.name = new Label(name);
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
        name = new Label("You:");
        name.setPadding(new Insets(0, 0, 0, 5));
        name.getStyleClass().add("name");
        name.setTextFill(Color.web("#414b66"));
        displayedText.setBackground(DEFAULT_SENDER_BACKGROUND);
        displayedText.setAlignment(Pos.CENTER_RIGHT);
        displayedText.setFont(new Font("Verdana", 13));
        directionIndicator.setContent("M10 0 L0 10 L0 0 Z");
        directionIndicator.setFill(DEFAULT_SENDER_COLOR);

        VBox msgWithName = new VBox(name, displayedText);
        msgWithName.maxHeightProperty().bind(heightProperty().multiply(0.75));
        msgWithName.setBackground(DEFAULT_SENDER_BACKGROUND);
        HBox container = new HBox(msgWithName, directionIndicator);

        //Use at most 75% of the width provided to the SpeechBox for displaying the message
        container.maxWidthProperty().bind(widthProperty().multiply(0.75));
        getChildren().setAll(container);
        setAlignment(Pos.CENTER_RIGHT);
    }

    private void configureForReceiver() {
//        name = new Label();
        name.setPadding(new Insets(0, 0, 0, 5));
        name.getStyleClass().add("name");
        name.setTextFill(Color.web("#3e50b4"));
        displayedText.setBackground(DEFAULT_RECEIVER_BACKGROUND);
        displayedText.setAlignment(Pos.CENTER_LEFT);
        displayedText.setFont(new Font("Verdana", 13));
        directionIndicator.setContent("M0 0 L10 0 L10 10 Z");
        directionIndicator.setFill(DEFAULT_RECEIVER_COLOR);

        VBox msgWithName = new VBox(name, displayedText);
        msgWithName.setBackground(DEFAULT_RECEIVER_BACKGROUND);
        msgWithName.maxHeightProperty().bind(heightProperty().multiply(0.75));

        HBox container = new HBox(directionIndicator, msgWithName);
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

