package chatstack;

import static chatstack.ChatStack.sc;
import static chatstack.ChatStack.StageOpened;
import static chatstack.ChatStack.root;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainPanelController implements Initializable {
    ObservableList<String> items = FXCollections.observableArrayList("test1", "test2");
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
    private JFXListView<?> Channel_LV;

    @FXML
    private JFXButton Create_Btn;

    @FXML
    private Label Online_Label;

    @FXML
    private Button xIcon;

    @FXML
    private JFXListView<?> Online_LV;

    @FXML
    private AnchorPane AP;

    @FXML
    private Button minIcon;

    @FXML
    private Label Channel_label;

    double oldW;
    double oldH;

    private Boolean resizebottom = false;
    private double dx;
    private double dy;
    private double xOffset;
    private double yOffset;
    boolean clicked = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StageOpened.setWidth(970);
        StageOpened.setHeight(600);
        StageOpened.setX(850);
        StageOpened.setY(190);
        StageOpened.setMinHeight(500);
        StageOpened.setMinWidth(600);
        oldW = AP.getPrefWidth();
        oldH = AP.getPrefHeight();
        System.out.println("New Height: " + AP.getPrefHeight());

        AP.setPrefHeight(800);
        AP.setPrefWidth(1280);
        AP.prefHeightProperty().bind(sc.getWindow().heightProperty());
        AP.prefWidthProperty().bind(sc.getWindow().widthProperty());
        adjustNodes();

    }

    @FXML
    public void adjustNodes() {
//        AP.setPrefHeight(1080);
//        AP.setPrefWidth(2560);

        System.out.println("old Height: " + oldH);
        System.out.println("New Height: " + AP.getPrefHeight());
        System.out.println("old X Online_LV: " + Online_LV.getLayoutX());
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

        System.out.println("new X Online_LV: " + Online_LV.getLayoutX());
        oldW = AP.getPrefWidth();
        oldH = AP.getPrefHeight();

    }

    @FXML
    void showmembers(ActionEvent event) throws Exception{
        ArrayList<String> x = new ArrayList<String>();
        x = ChatStack.db.getOnlineMemebers();
        //Online_LV.getItems().add(Online_LV.setItems().size(),x);
        //Online_LV.scrollTo(x);
        for (int i = 0; i < x.size(); i++) {
            //items;
        }
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
    void joinClicked(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("GroupChat.fxml"));
        sc = new Scene(root);
        StageOpened.setScene(sc);
    }

    @FXML
    void chatClicked(ActionEvent event) {

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
    void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
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

    //end of title bar code
//</editor-fold>
}
