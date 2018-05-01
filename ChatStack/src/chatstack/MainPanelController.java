package chatstack;

import static chatstack.ChatStack.sc;
import static chatstack.ChatStack.StageOpened;
import static chatstack.ChatStack.root;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainPanelController implements Initializable {

    ObservableList<String> items = FXCollections.observableArrayList("test1", "test2");
//    JFXListView<Object>list=JFXListView<Object>;
    @FXML
    private JFXListView<?> Channel_LV;
    @FXML
    private JFXListView<?> Online_LV;
    @FXML
    private AnchorPane AP;
    double oldW;
    double oldH;

    private Boolean resizebottom = false;
    private double dx;
    private double dy;
    private double xOffset;
    private double yOffset;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StageOpened.setWidth(1020);
        StageOpened.setHeight(800);
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
        Online_LV.setPrefHeight(AP.getPrefHeight() * (Online_LV.getPrefHeight() / oldH));
        Online_LV.setPrefWidth(AP.getPrefWidth() * (Online_LV.getPrefWidth() / oldW));

        Channel_LV.setLayoutY(AP.getPrefHeight() * (Channel_LV.getLayoutY() / oldH));
        Channel_LV.setLayoutX(AP.getPrefWidth() * (Channel_LV.getLayoutX() / oldW));
        Online_LV.setLayoutY(AP.getPrefHeight() * (Online_LV.getLayoutY() / oldH));
        Online_LV.setLayoutX(AP.getPrefWidth() * (Online_LV.getLayoutX() / oldW));
        System.out.println("new X Online_LV: " + Online_LV.getLayoutX());
        oldW = AP.getPrefWidth();
        oldH = AP.getPrefHeight();

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
    void maximize(MouseEvent event) {
        AP.prefHeightProperty().bind(sc.getWindow().heightProperty());
        AP.prefWidthProperty().bind(sc.getWindow().widthProperty());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setFullScreenExitHint(" ");
        stage.setFullScreen(true);
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
                StageOpened.setX(event.getScreenX() - xOffset);
                StageOpened.setY(event.getScreenY() - yOffset);
            } else {
                StageOpened.setWidth(event.getX() + dx);
                StageOpened.setHeight(event.getY() + dy);
            }
       adjustNodes();
    }
    //end of title bar code
//</editor-fold>

}
