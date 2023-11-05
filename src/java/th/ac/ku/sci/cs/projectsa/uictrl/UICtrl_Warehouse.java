package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
// ทดสอบ scrollPane
 import javafx.scene.shape.Rectangle;

public class UICtrl_Warehouse {
    @FXML 
    private TextField brandText, modelText, statusText;
    private String brand, model, status;

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
        brand = brandText.getText();
        System.out.println(brand);
    }

    @FXML private void onPressed_Button_findModel() throws java.io.IOException {
        model = modelText.getText();
        System.out.println(model);
    }

     @FXML private void onPressed_Button_findStatus() throws java.io.IOException {
        status = statusText.getText();
        System.out.println(status);
    }
}
