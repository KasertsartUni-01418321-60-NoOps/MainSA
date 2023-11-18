package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;

public class UICtrl_AddItem {

    @FXML private TextField productID;
    @FXML private TextArea repairDetail;
    @FXML private Spinner<Double> creatPrice;
    private String price;

    @FXML
    private void initialize() {
       // generateID()
        String randomeID = Misc.generateRandomID();
        productID.setEditable(false);
        productID.setText(randomeID);

        try{
            creatPrice.setEditable(true);
            creatPrice.setValueFactory(new DoubleSpinnerValueFactory(0.01, 99999999.99, 25000.0, 100.0));
            creatPrice.valueProperty().addListener((observable, oldValue, newValue) -> {
                price = newValue.toString();
            });
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

    @FXML private void onpressed_Button_Save() throws java.sql.SQLException, java.security.NoSuchAlgorithmException, Throwable {
        try {
            // TODO: save into database
            System.out.println(repairDetail.getText());
            System.out.println(price);
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
            throw e;
        }
    }
}
