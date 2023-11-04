package th.ac.ku.sci.cs.projectsa.uictrl;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.DatabaseMnm.DataSpec.STATUS_User;

import com.github.saacsos.FXRouter;

import javafx.fxml.*;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class UICtrl_Homepage {
	public void funcTestOFCaughtException(String[] args) {
	}


	@FXML
	// Exception Handling Mode: Fatal
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
	@FXML private void onPressed_Button_ContactsList() {System.out.println("RICKROLL");}
	@FXML private Button button_Planner;
	@FXML private void onPressed_Button_Planner() {System.out.println("RICKROLL");}
	@FXML private Button button_Warehouse;
	@FXML private void onPressed_Button_Warehouse() {System.out.println("RICKROLL");}
	@FXML private Button button_BuyHist;
	@FXML private void onPressed_Button_BuyHist() {System.out.println("RICKROLL");}
	@FXML private Button button_SellHist;
	@FXML private void onPressed_Button_SellHist() {System.out.println("RICKROLL");}
	@FXML private Button button_Accounting;
	@FXML private void onPressed_Button_Accounting() {System.out.println("RICKROLL");}

	
}