package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec.STATUS_User;

import com.github.saacsos.FXRouter;

import javafx.fxml.*;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
// ทดสอบ scrollPane
 import javafx.scene.shape.Rectangle;

public class UICtrl_Warehouse {
    @FXML 
    private TextField brand, model, status;
    @FXML
    private ScrollPane scrollPaneWarehouse;

    @FXML
    private void initialize(){
        // ทดสอบ scrollPane
        Rectangle rect = new Rectangle(200, 200, Color.RED);
        scrollPaneWarehouse.setContent(rect);
    }

    @FXML private void onBack_Button() throws java.sql.SQLException, java.security.NoSuchAlgorithmException, Throwable {
        try {
            Main.switchToSpecificPagename("homepage");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    @FXML private void onPressed_Button_addProduct() throws java.io.IOException {
        try {
            Main.switchToSpecificPagename("add_item");
        } catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

    @FXML private void onPressed_Button_findBrand() throws java.io.IOException {
        System.out.println("finding brand");
    }

    @FXML private void onPressed_Button_findModel() throws java.io.IOException {
        System.out.println("finding model");
    }

     @FXML private void onPressed_Button_findStatus() throws java.io.IOException {
        System.out.println("finding status");
    }
}
