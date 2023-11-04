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
	private void initialize() {
		Object tmpt_obj= ((DatabaseMnm.Table)Main.globalVar.get("loggedinUserPartialTable")).cols[1].vals.get(0);
		DatabaseMnm.DataSpec.STATUS_User currentUserRole = DatabaseMnm.DataSpec.STATUS_User.values()[
				DatabaseMnm.convertIntegerAlikeSQLColToLong(
					tmpt_obj,
					tmpt_obj.getClass()
				).intValue()
		];
		if (currentUserRole==DatabaseMnm.DataSpec.STATUS_User.Employer) {

		} else {
			
		}

	}
	
}