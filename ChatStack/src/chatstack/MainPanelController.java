package chatstack;

import static chatstack.ChatStack.sc;
import static chatstack.ChatStack.StageOpened;
import static chatstack.ChatStack.root;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainPanelController implements Initializable {

    static ObservableList<Label> Onlineitems = FXCollections.observableArrayList();
    static ObservableList<String> Groupitems = FXCollections.observableArrayList();
//    JFXListView<Object>list=JFXListView<Object>;
    @FXML
    private JFXButton Join_btn;

    @FXML
    private Button maxIcon;

    @FXML
    private ImageView iv_stack;

    @FXML
    private Separator Seperator;

    @FXML
    private Separator Seperator_1;

    @FXML
    private JFXButton Chat_btn;

    @FXML
    private JFXListView<String> Channel_LV;

    @FXML
    private JFXButton Create_Btn;

    @FXML
    private Label Online_Label;

    @FXML
    private Button xIcon;

    @FXML
    private JFXListView<Label> Online_LV;

    @FXML
    private AnchorPane AP;

    @FXML
    private Button minIcon;

    @FXML
    private Label Channel_label;

    @FXML
    private MenuButton Menu;

    double oldW;
    double oldH;

    private Boolean resizebottom = false;
    private double dx;
    private double dy;
    private double xOffset;
    private double yOffset;
    boolean clicked = false;
    public static Image image;
    public static PeerChatThreadServer pcts;
    public static String chatWithUsername;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            pcts = new PeerChatThreadServer(new ServerSocket(55554), this);
            pcts.start();
        } catch (IOException ex) {
            System.out.println(ex);
        }

        Channel_LV.getStyleClass().add("Label1");
        
        StageOpened.setWidth(970);
        StageOpened.setHeight(600);
        StageOpened.setX(850);
        StageOpened.setY(190);
        StageOpened.setMinHeight(500);
        StageOpened.setMinWidth(600);
        oldW = AP.getPrefWidth();
        oldH = AP.getPrefHeight();
        System.out.println("New Height: " + AP.getPrefHeight());

        image = new Image(getClass().getResourceAsStream("test.png"));
        Menu.setText(ChatStack.client.getUserName());

        AP.setPrefHeight(800);
        AP.setPrefWidth(1280);
        AP.prefHeightProperty().bind(sc.getWindow().heightProperty());
        AP.prefWidthProperty().bind(sc.getWindow().widthProperty());
        adjustNodes();
        Online_LV.setItems(Onlineitems);
        Channel_LV.setItems(Groupitems);
        Online_LV.getStyleClass().add("ChatScroll");
        Channel_LV.getStyleClass().add("ChatScroll");

        try {
            showmembers();
            showGroups();
        } catch (Exception ex) {
            Logger.getLogger(MainPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void adjustNodes() {
//        AP.setPrefHeight(1080);
//        AP.setPrefWidth(2560);
        Channel_LV.setPrefHeight(AP.getPrefHeight() * (Channel_LV.getPrefHeight() / oldH));
        Channel_LV.setPrefWidth(AP.getPrefWidth() * (Channel_LV.getPrefWidth() / oldW));
        Channel_LV.setLayoutY(AP.getPrefHeight() * (Channel_LV.getLayoutY() / oldH));
        Channel_LV.setLayoutX(AP.getPrefWidth() * (Channel_LV.getLayoutX() / oldW));

        Online_LV.setPrefHeight(AP.getPrefHeight() * (Online_LV.getPrefHeight() / oldH));
        Online_LV.setPrefWidth(AP.getPrefWidth() * (Online_LV.getPrefWidth() / oldW));
        Online_LV.setLayoutY(AP.getPrefHeight() * (Online_LV.getLayoutY() / oldH));
        Online_LV.setLayoutX(AP.getPrefWidth() * (Online_LV.getLayoutX() / oldW));

        Channel_label.setPrefHeight(AP.getPrefHeight() * (Channel_label.getPrefHeight() / oldH));
        Channel_label.setPrefWidth(AP.getPrefWidth() * (Channel_label.getPrefWidth() / oldW));
        Channel_label.setLayoutY(AP.getPrefHeight() * (Channel_label.getLayoutY() / oldH));
        Channel_label.setLayoutX(AP.getPrefWidth() * (Channel_label.getLayoutX() / oldW));

//        MenuBox.setPrefHeight(AP.getPrefHeight() * (MenuBox.getPrefHeight() / oldH));
//        MenuBox.setPrefWidth(AP.getPrefWidth() * (MenuBox.getPrefWidth() / oldW));
        Menu.setLayoutY(AP.getPrefHeight() * (Menu.getLayoutY() / oldH));
        Menu.setLayoutX(AP.getPrefWidth() * (Menu.getLayoutX() / oldW));

        Online_Label.setPrefHeight(AP.getPrefHeight() * (Online_Label.getPrefHeight() / oldH));
        Online_Label.setPrefWidth(AP.getPrefWidth() * (Online_Label.getPrefWidth() / oldW));
        Online_Label.setLayoutY(AP.getPrefHeight() * (Online_Label.getLayoutY() / oldH));
        Online_Label.setLayoutX(AP.getPrefWidth() * (Online_Label.getLayoutX() / oldW));

        Seperator.setPrefHeight(AP.getPrefHeight() * (Seperator.getPrefHeight() / oldH));
        Seperator.setPrefWidth(AP.getPrefWidth() * (Seperator.getPrefWidth() / oldW));
        Seperator.setLayoutY(AP.getPrefHeight() * (Seperator.getLayoutY() / oldH));
        Seperator.setLayoutX(AP.getPrefWidth() * (Seperator.getLayoutX() / oldW));

        Seperator_1.setPrefHeight(AP.getPrefHeight() * (Seperator_1.getPrefHeight() / oldH));
        Seperator_1.setPrefWidth(AP.getPrefWidth() * (Seperator_1.getPrefWidth() / oldW));
        Seperator_1.setLayoutY(AP.getPrefHeight() * (Seperator_1.getLayoutY() / oldH));
        Seperator_1.setLayoutX(AP.getPrefWidth() * (Seperator_1.getLayoutX() / oldW));

        Create_Btn.setPrefHeight(AP.getPrefHeight() * (Create_Btn.getPrefHeight() / oldH));
        Create_Btn.setPrefWidth(AP.getPrefWidth() * (Create_Btn.getPrefWidth() / oldW));
        Create_Btn.setLayoutY(AP.getPrefHeight() * (Create_Btn.getLayoutY() / oldH));
        Create_Btn.setLayoutX(AP.getPrefWidth() * (Create_Btn.getLayoutX() / oldW));

        Join_btn.setPrefHeight(AP.getPrefHeight() * (Join_btn.getPrefHeight() / oldH));
        Join_btn.setPrefWidth(AP.getPrefWidth() * (Join_btn.getPrefWidth() / oldW));
        Join_btn.setLayoutY(AP.getPrefHeight() * (Join_btn.getLayoutY() / oldH));
        Join_btn.setLayoutX(AP.getPrefWidth() * (Join_btn.getLayoutX() / oldW));

        Chat_btn.setPrefHeight(AP.getPrefHeight() * (Chat_btn.getPrefHeight() / oldH));
        Chat_btn.setPrefWidth(AP.getPrefWidth() * (Chat_btn.getPrefWidth() / oldW));
        Chat_btn.setLayoutY(AP.getPrefHeight() * (Chat_btn.getLayoutY() / oldH));
        Chat_btn.setLayoutX(AP.getPrefWidth() * (Chat_btn.getLayoutX() / oldW));

        iv_stack.setFitHeight(AP.getPrefHeight() * (iv_stack.getFitHeight() / oldH));
        iv_stack.setFitWidth(AP.getPrefWidth() * (iv_stack.getFitWidth() / oldW));
        iv_stack.setLayoutY(AP.getPrefHeight() * (iv_stack.getLayoutY() / oldH));
        iv_stack.setLayoutX(AP.getPrefWidth() * (iv_stack.getLayoutX() / oldW));

        oldW = AP.getPrefWidth();
        oldH = AP.getPrefHeight();

    }

    public static void showmembers() throws Exception {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                try {
                    Onlineitems.clear();
                    ChatStack.users = ChatStack.db.getOnlineMemebers();

                    for (int i = 0; i < ChatStack.users.size(); i++) {
                        Label l = new Label(ChatStack.users.get(i));
                        l.getStyleClass().add("Label11");
                        l.setGraphic(new ImageView(image));
                        Onlineitems.add(l);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainPanelController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    static void showGroups() throws SQLException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Groupitems.clear();
                    ChatStack.groups = ChatStack.db.getGroups();
                    
                    Groupitems.addAll(ChatStack.groups);
                } catch (SQLException ex) {
                    Logger.getLogger(MainPanelController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

    }

    @FXML
    void createClicked(ActionEvent event) throws IOException {
        Stage newWindow = new Stage();
        Scene sx = new Scene(FXMLLoader.load(getClass().getResource("CreateGroup.fxml")));
        newWindow.setScene(sx);
        newWindow.setX(StageOpened.getX() + 200);
        newWindow.setY(StageOpened.getY() + 100);
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.show();

    }

    @FXML
    void joinClicked(ActionEvent event) throws SQLException, IOException {

        //TODO count number of users in group
        System.out.println(Channel_LV.getSelectionModel().getSelectedItem());
        ChatStack.client.joinGroup(Channel_LV.getSelectionModel().getSelectedItem());
        ChatStack.client.sendPacket(3);
        root = FXMLLoader.load(getClass().getResource("GroupChat.fxml"));
        sc = new Scene(root);
        StageOpened.setScene(sc);

    }

    @FXML
    void chatClicked(ActionEvent event) throws IOException {
        try{
        chatWithUsername = Online_LV.getSelectionModel().getSelectedItem().getText();
        System.out.println("Username is:"+chatWithUsername);
                Parent root2 = FXMLLoader.load(getClass().getResource("PrivateChat.fxml"));
                Stage st2 = new Stage();
                st2.setTitle("Private Chat");
                st2.initStyle(StageStyle.UNDECORATED);
                Scene sc2 = new Scene(root2);
                st2.setScene(sc2);
                st2.show();
        }
        catch(NullPointerException ex){
            
        }
        
    }

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
        pcts.closeConnection();
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
    void chatClicked() throws IOException {

        Platform.runLater(() -> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PrivateChat_1.fxml"));
                Stage st2 = new Stage();
                st2.setTitle("Private Chat");
                st2.initStyle(StageStyle.UNDECORATED);
                Scene sc2 = new Scene(root2);
                st2.setScene(sc2);
                st2.show();
            } catch (IOException ex) {
                Logger.getLogger(MainPanelController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    //end of title bar code
//</editor-fold>
}
