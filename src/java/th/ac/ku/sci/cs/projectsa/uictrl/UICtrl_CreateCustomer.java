package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;

import javafx.fxml.*;
import javafx.scene.control.*;

public class UICtrl_CreateCustomer {
    @FXML
	private TextField textField_customerName,textField_customerPhone,textField_customerLocation;
    @FXML private void onPressed_Button_CreateCustomer() throws java.io.IOException {
		try {
			String customerName = textField_customerName.getText();
			String customerPhone = textField_customerPhone.getText();
            String customerLocation = textField_customerLocation.getText();
//todo  เก็บข้อมูลลูกค้าที่ฐานข้อมูล			
            Main.switchToSpecificPagename("customer_list");
}
    catch (Throwable e) {
    MyExceptionHandling.handleFatalException(e);
    throw e;
}
}
    @FXML private Button buttonBackCustomerList;
	@FXML private void onPress_Back_To_CustomerList() throws java.io.IOException {
		try{
			try {Main.switchToSpecificPagename("customer_list");} catch (java.io.IOException e1) {throw e1;}
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
			throw e;
		}
	}
}