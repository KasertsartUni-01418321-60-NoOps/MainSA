package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec.STATUS_User;
import th.ac.ku.sci.cs.projectsa.fun.MIDIPlayer;
import th.ac.ku.sci.cs.projectsa.fun.UnsafeStuff;

import com.github.saacsos.FXRouter;

import javafx.fxml.*;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class UICtrl_Homepage {
	private static boolean misct_bool_1=false; 
	public void funcTestOFCaughtException(String[] args) {
	}


	// REMARK: รันทุกรอบ หากใช้ FXRouter.goto
	@FXML
	private void initialize() {
		try{ 
			Object tmpt_obj= ((DatabaseMnm.Table)Main.globalVar.get("loggedinUserPartialTable")).cols[1].vals.get(0);
			DatabaseMnm.DataSpec.STATUS_User currentUserRole = DatabaseMnm.DataSpec.STATUS_User.values()[
					DatabaseMnm.convertIntegerAlikeSQLColToLong(
						tmpt_obj,
						tmpt_obj.getClass()
					).intValue()
			];
			if (currentUserRole==DatabaseMnm.DataSpec.STATUS_User.Employer) {
				
			} else {
				// TODO: EASY configure ตามความจำเป็น 
				button_Accounting.setDisable(true);
				button_BuyHist.setDisable(true);
				button_SellHist.setDisable(true);
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
	@FXML private Button button_Planner;
	@FXML private void onPressed_Button_Planner() throws java.io.IOException {
		try{
			throw new MyExceptionHandling.UserRuntimeException("Planner is not available LAMO");
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
			if (misct_bool_1==false) {
				MIDIPlayer.shutdown();
				misct_bool_1=true;
			} else {
				MIDIPlayer.main();
				misct_bool_1=false;
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
}

	