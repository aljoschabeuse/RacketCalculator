package application;
import javafx.scene.control.*;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
public class Actions {
	public void changedCustomerNumber(int number, GridPane[][] g) {
		for (int i = 1; i < g.length - 1; i++) {
			for (int j = 0; j < g[i].length; j++) {
				if (i <= number) {
					g[i][j].setVisible(true);
				} else {
					g[i][j].setVisible(false);
				}
			}
		}
	}
	
	
	//Aendern der Spieler
	public void changePlayerNumber(Booking b, int newNumber) {
		if (b.getFormation().getSize() < newNumber) {
			b.getFormation().enlargeList(new Customer(
					"Neuer Spieler",
					false,
					false,
					false,
					false,
					false,
					false,
					false));
		} else {
			b.getFormation().shortenList();
		}
	}
	
	public void changedCheckBoxBar(CheckBox[] checkBoxes) {
		if (checkBoxes[0].isSelected()) {
			for (CheckBox cb : checkBoxes) {
				cb.setSelected(false);
			}
		}
		checkBoxes[0].setSelected(true);
	}
	
	public void changedCheckBoxMember(CheckBox[] checkBoxes) {
		if (checkBoxes[1].isSelected()) {
			checkBoxes[3].setDisable(false);
			checkBoxes[0].setSelected(false);
		} else {
			checkBoxes[3].setSelected(false);
			checkBoxes[3].setDisable(true);
		}
		if (!checkBoxes[1].isSelected() && !checkBoxes[2].isSelected() && !checkBoxes[4].isSelected() && !checkBoxes[5].isSelected() && !checkBoxes[6].isSelected()) {
			checkBoxes[0].setSelected(true);
		}
	}
	
	public void changedCheckBoxHansefit(CheckBox[] checkBoxes) {
		if (checkBoxes[2].isSelected()) {
			checkBoxes[0].setSelected(false);
		} else {
			if (!checkBoxes[1].isSelected() && !checkBoxes[4].isSelected() && !checkBoxes[5].isSelected() && !checkBoxes[6].isSelected()) {
				checkBoxes[0].setSelected(true);
			}
		}
	}
	
	public void changedCheckBoxChild(CheckBox[] checkBoxes) {
		if (checkBoxes[3].isSelected()) {
			checkBoxes[0].setSelected(false);
		} else {
			if (!checkBoxes[1].isSelected() && !checkBoxes[2].isSelected() && !checkBoxes[4].isSelected() && !checkBoxes[5].isSelected() && !checkBoxes[6].isSelected()) {
				checkBoxes[0].setSelected(true);
			}
		}
	}
	
	public void changedCheckBoxStudent(CheckBox[] checkBoxes) {
		if (checkBoxes[4].isSelected()) {
			checkBoxes[0].setSelected(false);
		} else {
			if (!checkBoxes[1].isSelected() && !checkBoxes[2].isSelected() && !checkBoxes[5].isSelected() && !checkBoxes[6].isSelected()) {
				checkBoxes[0].setSelected(true);
			}
		}
	}
	
	public void changedCheckBoxTennisWA(CheckBox[] checkBoxes) {
		if(checkBoxes[5].isSelected()) {
			checkBoxes[0].setSelected(false);
			checkBoxes[6].setSelected(false);
		} else {
			if (!checkBoxes[1].isSelected() && !checkBoxes[2].isSelected() && !checkBoxes[4].isSelected() && !checkBoxes[6].isSelected()) {
				checkBoxes[0].setSelected(true);
			}
		}
	}
	
	public void changedCheckBoxTennisJS(CheckBox[] checkBoxes) {
		if(checkBoxes[6].isSelected()) {
			checkBoxes[0].setSelected(false);
			checkBoxes[5].setSelected(false);
		} else {
			if (!checkBoxes[1].isSelected() && !checkBoxes[2].isSelected() && !checkBoxes[4].isSelected() && !checkBoxes[5].isSelected()) {
				checkBoxes[0].setSelected(true);
			}
		}
	}
}
