package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec.STATUS_Product;

import javax.activation.DataSource;

import javafx.fxml.*;
import javafx.scene.control.*;

public class UICtrl_ProductDetail {
    @FXML private TextField productID,brandText,modelText,purchaseID,quotationID,priceText;
    @FXML private TextArea maintanaceID;
    
    DataSpec.STATUS_Product statusChoice;
    @FXML private ChoiceBox<STATUS_Product> changeStatus;

    @FXML 
    private void initialize() throws java.sql.SQLException {
        
        changeStatus.getItems().addAll(statusChoice);
        changeStatus.setOnAction(event -> {
            //selected = changeStatus.getValue();
            //System.out.println(selected);
            System.out.println("Test");
        });
    }
    @FXML private void onCreatQuotation_Button() throws java.sql.SQLException, java.security.NoSuchAlgorithmException, Throwable{
        try {
            Main.switchToSpecificPagename("quotation");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
    @FXML private void onBack_Button() throws java.sql.SQLException, java.security.NoSuchAlgorithmException, Throwable {
        try {
            Main.switchToSpecificPagename("warehouse");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }

}
