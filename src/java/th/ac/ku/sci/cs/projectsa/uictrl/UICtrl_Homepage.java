package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.fun.MIDIPlayer;
import th.ac.ku.sci.cs.projectsa.fun.UnsafeStuff;

import javafx.fxml.*;
import javafx.scene.control.*;

public class UICtrl_Homepage {
	public static int misct_pint_1=0; 
	@FXML private Button  button_Misc1; 
	public void funcTestOFCaughtException(String[] args) {
	}


	// REMARK: รันทุกรอบ หากใช้ FXRouter.goto
	@FXML
	private void initialize() {
		try{ 
			if (misct_pint_1>0) {
				button_Misc1.setDisable(true);
				button_Misc1.setText("<MUTED>");
			} else if (misct_pint_1<0) {
				button_Misc1.setVisible(false);
			} else {
			}
			DatabaseMnm.DataSpec.STATUS_User currentUserRole =(DatabaseMnm.DataSpec.STATUS_User)Main.globalVar.get("loggedinUser_Role");
			if (currentUserRole==DatabaseMnm.DataSpec.STATUS_User.Employer) {
			} else { 
				button_Accounting.setDisable(true);
				button_ContactsList.setDisable(true);
				button_SellHist.setDisable(true);
				button_Misc1.setDisable(true);
			}
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
			throw e;
		}
	}
	
	@FXML private Button button_ContactsList;
	@FXML private void onPressed_Button_ContactsList() throws java.io.IOException {
		try{
			try {Main.switchToSpecificPagename("customer_list");} catch (java.io.IOException e1) {throw e1;}
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
			throw e;
		}
	}
	@FXML private Button button_Warehouse;
	@FXML private void onPressed_Button_Warehouse()  throws java.io.IOException {
		try{
			try {Main.switchToSpecificPagename("warehouse");} catch (java.io.IOException e1) {throw e1;}
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
			throw e;
		}
	}
	@FXML private Button button_BuyHist;
	@FXML private void onPressed_Button_BuyHist()  throws java.io.IOException {
		try{
			try {Main.switchToSpecificPagename("buy_history");} catch (java.io.IOException e1) {throw e1;}
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
			throw e;
		}
	}
	@FXML private Button button_SellHist;
	@FXML private void onPressed_Button_SellHist() throws java.io.IOException {
		try{
			try {Main.switchToSpecificPagename("sell_history");} catch (java.io.IOException e1) {throw e1;}
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
			throw e;
		}
	}
	@FXML private Button button_Accounting;
	@FXML private void onPressed_Button_Accounting()  throws java.io.IOException{
		try{
			try {Main.switchToSpecificPagename("money_accounting");} catch (java.io.IOException e1) {throw e1;}
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
			throw e;
		}
	}
		@FXML private void onpressed_Button_Misc1() {
		try{
			if (misct_pint_1==0) { 
			misct_pint_1=1;
			button_Misc1.setDisable(true);
			button_Misc1.setText("<MUTED>");
			MIDIPlayer.shutdown();
			}
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
			throw e;
		}
	}
	@FXML private void onpressed_Button_Misc2() {
		try{
			UnsafeStuff.crashJVMLamo();
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
			throw e;
		}
	}

	@FXML private void onpressed_Button_Logout() throws java.io.IOException{
		try{
			Main.globalVar.remove("loggedinUserPartialTable");
			try {Main.switchToSpecificPagename("login");} catch (java.io.IOException e1) {throw e1;}
		} catch (Throwable e) {
			MyExceptionHandling.handleFatalException(e);
			throw e;
		}
	}
}

	