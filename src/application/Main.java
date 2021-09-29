package application;
	
import java.awt.Dimension;

import java.util.Hashtable;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.io.IOException;

import javafx.animation.*;
import javafx.application.Application;

import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.geometry.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class Main extends Application {
	Actions actions = new Actions();
	//ReadingWriting rw = new ReadingWriting();
	
	Currency currency = new Currency();
	Calculation calculation = new Calculation();
	DatesTimes dt = new DatesTimes();
	
	Booking booking;
	
	Hashtable<String, String> filialCodes;
	String[] sportartenAsString;
	int filiale = 0;
	int sportart = 0;
	ArrayList<Customer> listCustomer = new ArrayList<Customer>();
	ArrayList<Customer> listCustomer1 = new ArrayList<Customer>();
	ArrayList<Customer> listCustomer2 = new ArrayList<Customer>();
	int ecoHours;
	int primeHours;
	int customer = 2;
	
	Dimension size;
	Scene scene;
	StackPane stackFirst;
		BorderPane firstPane;
			GridPane leftSideText;
			FlowPane confirmButton;
				Button confirm;
			VBox secondPane;
				StackPane stackTop;
				GridPane top;//Height = 1, Width = 3
					VBox topLeft;
						VBox vboxCustomer;
							Label paneLabelTopLeft;
							Spinner<Integer> spinnerCustomers;
						HBox hboxTopLeftFamily;
							CheckBox checkBoxFamily;
						TopCenter tc;
					FlowPane topRight;
						FlowPane logoPane;
							Image logo;
							ImageView iv;
					VBox graphicLogo;
				StackPane stackBottom;
				GridPane bottom;//Height = 10, Width = 7
					GridPane[][] g;
					TextField[] player;
					Label[] headlineLabels;
					Label[] endlineLabels;
					CheckBox[] checkBoxBar;
					CheckBox[] checkBoxMember;
					CheckBox[] checkBoxHansefit;
					CheckBox[] checkBoxChild;
					CheckBox[] checkBoxStudent;
					CheckBox[] checkBoxTennisWA;
					CheckBox[] checkBoxTennisJS;
					Label[] sums;
			
		
	@Override
	public void start(Stage primaryStage) {
		try {
			//Groessen der Boxen
			size = Toolkit.getDefaultToolkit().getScreenSize();
			double topHeight = (int) Math.round(size.height * 0.98 / 5.0 * 2.0);
			double topWidth = (int) Math.round(size.width * 0.90);
			double bottomHeight = (int) Math.round(size.height * 0.98 / 5.0 * 3.0);
			double bottomWidth = (int) Math.round(size.width * 0.90);
			//Schriftgroesse ermitteln
			double fontSize = 0.00001 * size.width * size.height;
			Font font = new Font("Verdana", fontSize);
			
			
			sportartenAsString = new String[] {"Badminton", "Squash", "Tennis", /*"Tischtennis"*/};
			
			secondPane = new VBox();
			
			stackTop = new StackPane();
			top = new GridPane();
			top.setMinSize(topWidth, topHeight);
			
			spinnerCustomers = new Spinner<Integer>();
			spinnerCustomers.setEditable(false);
			spinnerCustomers.getEditor().setFont(font);
			SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 8, 2);
	        spinnerCustomers.setValueFactory(valueFactory);
	        spinnerCustomers.valueProperty().addListener((v, oldValue, newValue) -> {
	        	String chosenSportart = "Badminton";
	        	for (RadioButton rb : tc.rbSportart) {
	        		if (rb.isSelected()) {
	        			chosenSportart = rb.getText();
	        			break;
	        		}
	        	}
	        	if (spinnerCustomers.getValue() > 4 && (chosenSportart.equals("Squash") || chosenSportart.equals("Tennis"))) {
	        		spinnerCustomers.getValueFactory().setValue(4);
	        	}
	        	actions.changedCustomerNumber(spinnerCustomers.getValueFactory().getValue(), g);
	        	if (!oldValue.equals(spinnerCustomers.getValueFactory().getValue())) {
		        	for (int i = 1; i <= spinnerCustomers.getValueFactory().getValue(); i++) {
						int dur = i * 400;
						for (int j = 0; j < g[i].length; j++) {							
							animateCustomerNumber(g[i][j], dur);
						}
					}
	        	}
	        	liveCalculation(false);
	        });
	        
	        paneLabelTopLeft = new Label("Anzahl Spieler");
			paneLabelTopLeft.setFont(font);
			
			vboxCustomer = new VBox();
			vboxCustomer.setMinSize(topWidth/100.0*20.0, topHeight/100.0*30.0);
			vboxCustomer.setSpacing(topHeight/100.0*20.0/10.0);
			vboxCustomer.getChildren().addAll(paneLabelTopLeft, spinnerCustomers);
			
			checkBoxFamily = new CheckBox("Familie");
			checkBoxFamily.setPadding(new Insets(topHeight / 40.0, 0, topHeight / 40.0, 0));
			checkBoxFamily.setOnAction(e -> {
				liveCalculation(true);
			});
			
			topLeft = new VBox();
			topLeft.setMinSize(topWidth/100.0*20.0, topHeight);
			topLeft.getChildren().addAll(vboxCustomer, checkBoxFamily);
						
			tc = new TopCenter(topWidth / 2.5, topHeight, font);
			
			actionsTimeSpinners();
			
			logoPane = new FlowPane();
			logoPane.setAlignment(Pos.CENTER);
			URL urlLogo = getClass().getResource("bahamabig2.jpg");
			logo = new Image(urlLogo.toExternalForm(),
					topWidth * 0.4,
					topHeight,
					true,
					true);
			iv = new ImageView(logo);
			logoPane.getChildren().add(iv);
			topRight = new FlowPane();
			topRight.setAlignment(Pos.TOP_RIGHT);
			topRight.getChildren().add(logoPane);
			
			top.add(topLeft, 0, 0);
			top.add(tc.stickTogether(), 1, 0);
			
			//Hintergrund mittels radialen Gradienten
			FlowPane background = new FlowPane();
			Color greenStart = new Color(207/255d, 222/255d, 118/255d, 0);
			Color greenEnd = new Color(0/255d, 148/255d, 60/255d, 0.34);
			
			RadialGradient bgGradient =
					 new RadialGradient(
					 0, 1,
					 1, 0,
					 0.4,
					 true,
					 CycleMethod.REFLECT,
					 new Stop(0.2, greenStart),
					 new Stop(0.8, greenEnd));
			Rectangle r = new Rectangle(size.width, size.height);
			r.setFill(bgGradient);
			background.getChildren().add(r);
			StackPane stackBG = new StackPane();
			stackBG.getChildren().add(background);
			stackTop.getChildren().addAll(top);
			
			//Schatten fuer Schriftzug
			DropShadow ds = new DropShadow();
			ds.setOffsetY(-50.0);
			ds.setOffsetX(10.0);
			ds.setColor(Color.GRAY);
			
			//Text gedreht und mit radialem Gradienten
			Text bahama = new Text (0, 0, "Bahama-Sports  ---  Racket");
			bahama.setEffect(ds);
			bahama.setFont(Font.font(font.getSize()*3));
			bahama.getStyleClass().add("titlelabel");
			bahama.setStroke(new Color(233/255.0, 0/255.0, 125/255.0, 1));
			LinearGradient bahamaGradient = new LinearGradient(
					0.8, 0, 
					0, 0, 
					true, 
					CycleMethod.NO_CYCLE,
					new Stop(0.1, Color.WHITESMOKE),
					new Stop(0.5, new Color(233/255.0, 0/255.0, 125/255.0, 0.7)));
			bahama.setFill(bahamaGradient);
			bahama.setRotate(-90);
			
			leftSideText = new GridPane();
			leftSideText.setAlignment(Pos.CENTER);
			leftSideText.setMaxWidth(size.width/100.0*6.0);
			leftSideText.setMinWidth(size.width/100.0*6.0);
			leftSideText.getChildren().add(bahama);
			leftSideText.setPadding(new Insets(0, size.width*0.03, 0, size.width*0.01));
			
			bottom = new GridPane();
			bottom.setAlignment(Pos.CENTER);
			bottom.setMinSize(bottomWidth * 0.9, bottomHeight);
			g = new GridPane[10][9];
			for (int i = 0; i < g.length; i++) {
				for (int j = 0; j < g[i].length; j++) {
					g[i][j] = new GridPane();
				}
			}
			for (int i = 1; i < g[0].length; i++) {
				for (int j = 0; j < g.length; j++) {
					g[j][i].setAlignment(Pos.CENTER);
				}
			}
			for (int i = 0; i < g.length; i++) {
				g[i][g[0].length - 1].setAlignment(Pos.CENTER_RIGHT); 
			}
			
			player = new TextField[8];
			for (int i = 0; i < player.length; i++) {
				player[i] = new TextField("Spieler "+(i+1));
				player[i].setFont(font);
				player[i].getStyleClass().add("playertextfield");
			}
			
			String[] headlineStrings = new String[] {
					"Name",
					"Barzahler",
					"Mitglied/\nHansefit mit MG",
					"Hansefit",
					"Kind v. Mitglied/\n(bis 14 Jahre)",
					"Student",
					"Tennis WA",
					"Tennis JS",
					"Summe"
			};
			
			String[] endlineStrings = new String[] {
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"Gesamt: ",
					currency.currencyAsString(0.0f)
			};
			
			headlineLabels = new Label[headlineStrings.length];
			endlineLabels = new Label[endlineStrings.length];
			for (int i = 0; i < headlineLabels.length; i++) {
				headlineLabels[i] = new Label(headlineStrings[i]);
				headlineLabels[i].setTextAlignment(TextAlignment.CENTER);
				headlineLabels[i].setFont(font);
				endlineLabels[i] = new Label(endlineStrings[i]);
				endlineLabels[i].setFont(font);
				endlineLabels[i].setStyle("-fx-font-weight: bold");
			}
			
			checkBoxBar = new CheckBox[player.length];
			checkBoxMember = new CheckBox[player.length];
			checkBoxHansefit = new CheckBox[player.length];
			checkBoxChild = new CheckBox[player.length];
			checkBoxStudent = new CheckBox[player.length];
			checkBoxTennisWA = new CheckBox[player.length];
			checkBoxTennisJS = new CheckBox[player.length];
			for (int i = 0; i < checkBoxBar.length; i++) {
				checkBoxBar[i] = new CheckBox();
				checkBoxMember[i] = new CheckBox();
				checkBoxHansefit[i] = new CheckBox();
				checkBoxChild[i] = new CheckBox();
				checkBoxChild[i].setDisable(true);
				checkBoxStudent[i] = new CheckBox();
				checkBoxTennisWA[i] = new CheckBox();
				checkBoxTennisJS[i] = new CheckBox();
			}
			
			//Buttons fuer Spieler 1
			checkBoxBar[0].setOnAction(e -> {
				actions.changedCheckBoxBar(customerCheckBoxes(0));
				liveCalculation(true);
			});
			
			checkBoxMember[0].setOnAction(e -> {
				actions.changedCheckBoxMember(customerCheckBoxes(0));
				liveCalculation(true);
			});
			
			checkBoxHansefit[0].setOnAction(e -> {
				actions.changedCheckBoxHansefit(customerCheckBoxes(0));
				liveCalculation(true);
			});
			
			checkBoxChild[0].setOnAction(e -> {
				actions.changedCheckBoxChild(customerCheckBoxes(0));
				liveCalculation(true);
			});
			
			checkBoxStudent[0].setOnAction(e -> {
				actions.changedCheckBoxStudent(customerCheckBoxes(0));
				liveCalculation(true);
			});
			
			checkBoxTennisWA[0].setOnAction(e -> {
				actions.changedCheckBoxTennisWA(customerCheckBoxes(0));
				liveCalculation(true);
			});
			
			checkBoxTennisJS[0].setOnAction(e -> {
				actions.changedCheckBoxTennisJS(customerCheckBoxes(0));
				liveCalculation(true);
			});
			
			//Buttons fuer Spieler 2
			checkBoxBar[1].setOnAction(e -> {
				actions.changedCheckBoxBar(customerCheckBoxes(1));
				liveCalculation(true);
			});
			
			checkBoxMember[1].setOnAction(e -> {
				actions.changedCheckBoxMember(customerCheckBoxes(1));
				liveCalculation(true);
			});
			
			checkBoxHansefit[1].setOnAction(e -> {
				actions.changedCheckBoxHansefit(customerCheckBoxes(1));
				liveCalculation(true);
			});
			
			checkBoxChild[1].setOnAction(e -> {
				actions.changedCheckBoxChild(customerCheckBoxes(1));
				liveCalculation(true);
			});
			
			checkBoxStudent[1].setOnAction(e -> {
				actions.changedCheckBoxStudent(customerCheckBoxes(1));
				liveCalculation(true);
			});
			
			checkBoxTennisWA[1].setOnAction(e -> {
				actions.changedCheckBoxTennisWA(customerCheckBoxes(1));
				liveCalculation(true);
			});
			
			checkBoxTennisJS[1].setOnAction(e -> {
				actions.changedCheckBoxTennisJS(customerCheckBoxes(1));
				liveCalculation(true);
			});
			
			//Buttons fuer Spieler 3
			checkBoxBar[2].setOnAction(e -> {
				actions.changedCheckBoxBar(customerCheckBoxes(2));
				liveCalculation(true);
			});
			
			checkBoxMember[2].setOnAction(e -> {
				actions.changedCheckBoxMember(customerCheckBoxes(2));
				liveCalculation(true);
			});
			
			checkBoxHansefit[2].setOnAction(e -> {
				actions.changedCheckBoxHansefit(customerCheckBoxes(2));
				liveCalculation(true);
			});
			
			checkBoxChild[2].setOnAction(e -> {
				actions.changedCheckBoxChild(customerCheckBoxes(2));
				liveCalculation(true);
			});
			
			checkBoxStudent[2].setOnAction(e -> {
				actions.changedCheckBoxStudent(customerCheckBoxes(2));
				liveCalculation(true);
			});
			
			checkBoxTennisWA[2].setOnAction(e -> {
				actions.changedCheckBoxTennisWA(customerCheckBoxes(2));
				liveCalculation(true);
			});
			
			checkBoxTennisJS[2].setOnAction(e -> {
				actions.changedCheckBoxTennisJS(customerCheckBoxes(2));
				liveCalculation(true);
			});
			
			//Buttons fuer Spieler 4
			checkBoxBar[3].setOnAction(e -> {
				actions.changedCheckBoxBar(customerCheckBoxes(3));
				liveCalculation(true);
			});
			
			checkBoxMember[3].setOnAction(e -> {
				actions.changedCheckBoxMember(customerCheckBoxes(3));
				liveCalculation(true);
			});
			
			checkBoxHansefit[3].setOnAction(e -> {
				actions.changedCheckBoxHansefit(customerCheckBoxes(3));
				liveCalculation(true);
			});
			
			checkBoxChild[3].setOnAction(e -> {
				actions.changedCheckBoxChild(customerCheckBoxes(3));
				liveCalculation(true);
			});
			
			checkBoxStudent[3].setOnAction(e -> {
				actions.changedCheckBoxStudent(customerCheckBoxes(3));
				liveCalculation(true);
			});
			
			checkBoxTennisWA[3].setOnAction(e -> {
				actions.changedCheckBoxTennisWA(customerCheckBoxes(3));
				liveCalculation(true);
			});
			
			checkBoxTennisJS[3].setOnAction(e -> {
				actions.changedCheckBoxTennisJS(customerCheckBoxes(3));
				liveCalculation(true);
			});
			
			//Buttons fuer Spieler 5
			checkBoxBar[4].setOnAction(e -> {
				actions.changedCheckBoxBar(customerCheckBoxes(4));
				liveCalculation(true);
			});
			
			checkBoxMember[4].setOnAction(e -> {
				actions.changedCheckBoxMember(customerCheckBoxes(4));
				liveCalculation(true);
			});
			
			checkBoxHansefit[4].setOnAction(e -> {
				actions.changedCheckBoxHansefit(customerCheckBoxes(4));
				liveCalculation(true);
			});
			
			checkBoxChild[4].setOnAction(e -> {
				actions.changedCheckBoxChild(customerCheckBoxes(4));
				liveCalculation(true);
			});
			
			checkBoxStudent[4].setOnAction(e -> {
				actions.changedCheckBoxStudent(customerCheckBoxes(4));
				liveCalculation(true);
			});
			
			checkBoxTennisWA[4].setOnAction(e -> {
				actions.changedCheckBoxTennisWA(customerCheckBoxes(4));
				liveCalculation(true);
			});
			
			checkBoxTennisJS[4].setOnAction(e -> {
				actions.changedCheckBoxTennisJS(customerCheckBoxes(4));
				liveCalculation(true);
			});
			
			//Buttons fuer Spieler 6
			checkBoxBar[5].setOnAction(e -> {
				actions.changedCheckBoxBar(customerCheckBoxes(5));
				liveCalculation(true);
			});
			
			checkBoxMember[5].setOnAction(e -> {
				actions.changedCheckBoxMember(customerCheckBoxes(5));
				liveCalculation(true);
			});
			
			checkBoxHansefit[5].setOnAction(e -> {
				actions.changedCheckBoxHansefit(customerCheckBoxes(5));
				liveCalculation(true);
			});
			
			checkBoxChild[5].setOnAction(e -> {
				actions.changedCheckBoxChild(customerCheckBoxes(5));
				liveCalculation(true);
			});
			
			checkBoxStudent[5].setOnAction(e -> {
				actions.changedCheckBoxStudent(customerCheckBoxes(5));
				liveCalculation(true);
			});
			
			checkBoxTennisWA[5].setOnAction(e -> {
				actions.changedCheckBoxTennisWA(customerCheckBoxes(5));
				liveCalculation(true);
			});
			
			checkBoxTennisJS[5].setOnAction(e -> {
				actions.changedCheckBoxTennisJS(customerCheckBoxes(5));
				liveCalculation(true);
			});
			
			//Buttons fuer Spieler 7
			checkBoxBar[6].setOnAction(e -> {
				actions.changedCheckBoxBar(customerCheckBoxes(6));
				liveCalculation(true);
			});
			
			checkBoxMember[6].setOnAction(e -> {
				actions.changedCheckBoxMember(customerCheckBoxes(6));
				liveCalculation(true);
			});
			
			checkBoxHansefit[6].setOnAction(e -> {
				actions.changedCheckBoxHansefit(customerCheckBoxes(6));
				liveCalculation(true);
			});
			
			checkBoxChild[6].setOnAction(e -> {
				actions.changedCheckBoxChild(customerCheckBoxes(6));
				liveCalculation(true);
			});
			
			checkBoxStudent[6].setOnAction(e -> {
				actions.changedCheckBoxStudent(customerCheckBoxes(6));
				liveCalculation(true);
			});
			
			checkBoxTennisWA[6].setOnAction(e -> {
				actions.changedCheckBoxTennisWA(customerCheckBoxes(6));
				liveCalculation(true);
			});
			
			checkBoxTennisJS[6].setOnAction(e -> {
				actions.changedCheckBoxTennisJS(customerCheckBoxes(6));
				liveCalculation(true);
			});
			
			//Buttons fuer Spieler 8
			checkBoxBar[7].setOnAction(e -> {
				actions.changedCheckBoxBar(customerCheckBoxes(7));
				liveCalculation(true);
			});
			
			checkBoxMember[7].setOnAction(e -> {
				actions.changedCheckBoxMember(customerCheckBoxes(7));
				liveCalculation(true);
			});
			
			checkBoxHansefit[7].setOnAction(e -> {
				actions.changedCheckBoxHansefit(customerCheckBoxes(7));
				liveCalculation(true);
			});
			
			checkBoxChild[7].setOnAction(e -> {
				actions.changedCheckBoxChild(customerCheckBoxes(7));
				liveCalculation(true);
			});
			
			checkBoxStudent[7].setOnAction(e -> {
				actions.changedCheckBoxStudent(customerCheckBoxes(7));
				liveCalculation(true);
			});
			
			checkBoxTennisWA[7].setOnAction(e -> {
				actions.changedCheckBoxTennisWA(customerCheckBoxes(7));
				liveCalculation(true);
			});
			
			checkBoxTennisJS[7].setOnAction(e -> {
				actions.changedCheckBoxTennisJS(customerCheckBoxes(7));
				liveCalculation(true);
			});
			
			sums = new Label[player.length];
			for (int i = 0; i < sums.length; i++) {
				sums[i] = new Label(currency.currencyAsString(0.0f));
				sums[i].setFont(font);
				sums[i].setText("12,00 EUR");
			}
			
			for (int i = 0; i < g[0].length; i++) {
				g[0][i].add(headlineLabels[i], 0, 0);
			}
			
			for (int i = 1; i < player.length + 1; i++) {
				g[i][0].add(player[i-1], 0, 0);
				g[i][1].add(checkBoxBar[i-1], 0, 0);
				g[i][2].add(checkBoxMember[i-1], 0, 0);
				g[i][3].add(checkBoxHansefit[i-1], 0, 0);
				g[i][4].add(checkBoxChild[i-1], 0, 0);
				g[i][5].add(checkBoxStudent[i-1], 0, 0);
				g[i][6].add(checkBoxTennisWA[i-1], 0, 0);
				g[i][7].add(checkBoxTennisJS[i-1], 0, 0);
				g[i][8].add(sums[i-1], 0, 0);
			}
						
			g[9][8].add(endlineLabels[8], 0, 0);
			
			confirm = new Button("Bestätigen");
			confirm.setOnAction(e -> {
				//record();
				backend();				
			});
			confirm.setOnMouseEntered(e -> {
					primaryStage.getScene().getRoot().setCursor(Cursor.HAND);
			});
			confirm.setOnMouseExited(e -> {
				primaryStage.getScene().getRoot().setCursor(Cursor.DEFAULT);
			});
			
			confirm.getStyleClass().add("confirmBtn");
			confirm.setMinWidth(bottomWidth / 100.0 * 15.0);
			g[9][6].getChildren().clear();
			g[9][6].add(confirm, 0, 0, 2, 2);
			g[9][6].setVisible(true);
			
			
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 9; j++) {
					bottom.add(g[i][j], j, i);
				}
			}
			
			ColumnConstraints[] colWidthes = new ColumnConstraints[g[0].length];
			for (int i = 0; i < colWidthes.length; i++) {
				colWidthes[i] = new ColumnConstraints();
				colWidthes[i].setPercentWidth(100/colWidthes.length);
				bottom.getColumnConstraints().add(colWidthes[i]);
			}			
			
			for (int i = 0; i < g.length; i++) {
				for (int j = 0; j < g[i].length; j++) {
					g[i][j].setMinHeight(bottomHeight/100.0*8.0);
					g[i][j].setAlignment(Pos.CENTER);
				}
			}
			
			secondPane.getChildren().addAll(stackTop, bottom);
			stackFirst = new StackPane();
			firstPane = new BorderPane();
			firstPane.setPadding(new Insets(size.height * 0.01, size.width * 0.01, size.height * 0.01, size.width * 0.01));
			firstPane.setCenter(secondPane);
			firstPane.setLeft(leftSideText);
			
			stackFirst.getChildren().addAll(stackBG, topRight, firstPane);
			
			Scene sceneMain = new Scene(stackFirst, size.width, size.height);
			
			URL urlStyles = getClass().getResource("styles.css");
			sceneMain.getStylesheets().add(urlStyles.toExternalForm());
			primaryStage.setTitle("Racket-Rechner");
			primaryStage.setFullScreen(false);
			primaryStage.setScene(sceneMain);
			animateBahama();
			backend();
			primaryStage.show();			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void backend() {
		String time = dt.date_HHmm();
		Integer hour = Integer.parseInt(time.split(":")[0]);
		Integer minute = Integer.parseInt(time.split(":")[1]);
		Integer military = hour * 100 + minute;
		if ((dt.weekday().equals("Montag") || dt.weekday().equals("Dienstag") ||
				dt.weekday().equals("Mittwoch") || dt.weekday().equals("Donnerstag")) &&
				(military > 1630 && military < 1930)) {
			tc.spinnerPrime.getValueFactory().setValue(1);
		} else {
			tc.spinnerEco.getValueFactory().setValue(1);
		}
		
		ArrayList<Customer> arrList = new ArrayList<Customer>();
		arrList.add(new Customer("Spieler 1",
				false,//Mitglied
				false,//Hansefit
				false,//Kind v. Mitglied
				false,//Student
				false,//Tennis-WA
				false,//Tennis-JS
				false));//Familie
		arrList.add(new Customer("Spieler 2",
				false,//Mitglied
				false,//Hansefit
				false,//Kind v. Mitglied
				false,//Student
				false,//Tennis-WA
				false,//Tennis-JS
				false));//Familie
		
		Formation formation = new Formation(arrList);
		Booking booking = new Booking(formation,
				0,//0 = Lüstringen, 1 = Nahne, 2 = Oldenburg
				0,//0 = Badminton, 1 = Squash, 2 = Tennis, 3 = Tischtennis 
				0,//Anzahl der Eco-Stunden
				0);//Anzahl der Prime-Stunden
		checkBoxFamily.setSelected(false);
		Calculation calculation = new Calculation();
		ArrayList<Float> prices = new ArrayList<Float>();
		for (Float f : calculation.getPrices(booking)) {
			prices.add(f);
		}
		for (int i = 0; i < prices.size(); i++) {
			sums[i].setText(currency.currencyAsString(prices.get(i)));
		}
		
		spinnerCustomers.getValueFactory().setValue(arrList.size());
		if (arrList.get(0).getFamilie()) {
			checkBoxFamily.setSelected(true);
		} else {
			checkBoxFamily.setSelected(false);
		}
		for (int i = arrList.size() + 1; i < g.length - 1; i++) {
			for (int j = 0; j < g[i].length; j++) {
				g[i][j].setVisible(false);
			}
		}
		for (int i = 0; i < arrList.size(); i++) {
			checkBoxBar[i].setSelected(arrList.get(i).getBar());
			checkBoxMember[i].setSelected(arrList.get(i).getMember());
			checkBoxHansefit[i].setSelected(arrList.get(i).getHansefit());
			checkBoxChild[i].setSelected(arrList.get(i).getChild());
			checkBoxStudent[i].setSelected(arrList.get(i).getStudent());
			checkBoxTennisWA[i].setSelected(arrList.get(i).getTennisWA());
			checkBoxTennisJS[i].setSelected(arrList.get(i).getTennisJS());
		}
		
		if (booking.getSportart() != 2) {
			for (int i = 0; i < 8; i++) {
				checkBoxTennisWA[i].setDisable(true);
				checkBoxTennisJS[i].setDisable(true);
			}
		}
		liveCalculation(false);
	}
	
	private void liveCalculation(boolean sumAnimating) {
		
		ArrayList<Double> prices = new ArrayList<Double>();
		listCustomer = new ArrayList<Customer>();
		for (int i = 0; i < spinnerCustomers.getValueFactory().getValue(); i++) {
			listCustomer.add(new Customer("Spieler",
					checkBoxMember[i].isSelected(),
					checkBoxHansefit[i].isSelected(),
					checkBoxChild[i].isSelected(),
					checkBoxStudent[i].isSelected(),
					checkBoxTennisWA[i].isSelected(),
					checkBoxTennisJS[i].isSelected(),
					checkBoxFamily.isSelected()));
		}
		Formation f = new Formation(listCustomer);
		for (int i = 0; i < spinnerCustomers.getValueFactory().getValue(); i++) {
			f.getList().get(i).updateCustomer(checkBoxBar[i], 
					checkBoxMember[i],
					checkBoxHansefit[i],
					checkBoxChild[i],
					checkBoxStudent[i],
					checkBoxTennisWA[i],
					checkBoxTennisJS[i]);
		}
		
		for (RadioButton rb : tc.rbSportart) {
			if (rb.isSelected()) {
				if (rb.getText().equals("Badminton")) {
					sportart = 0;
				} else if (rb.getText().equals("Squash")) {
					sportart = 1;
				} else if (rb.getText().equals("Tennis")) {
					sportart = 2;
				} else {
					sportart = 3;
				}
			}
		}
		booking = new Booking(f, filiale, sportart, 
				tc.spinnerEco.getValueFactory().getValue(), 
				tc.spinnerPrime.getValueFactory().getValue());
		prices = calculation.getPricesAsDouble(booking);
		
		double total = 0;
		for (int i = 0; i < prices.size(); i++) {
			total += prices.get(i);
		}
		for (int i = 0; i < prices.size(); i++) {
			if (currency.currencyAsDouble(sums[i].getText()) != prices.get(i) && sumAnimating) {
				animateSum(sums[i], 500, prices.get(i));
			} else {
				sums[i].setText(currency.currencyAsString(prices.get(i)));
			}
		}
		if (!currency.currencyAsString(total).equals(endlineLabels[endlineLabels.length - 1].getText())) {
			animateSum(endlineLabels[endlineLabels.length - 1], 500, total);
		} else {
			endlineLabels[endlineLabels.length - 1].setText(currency.currencyAsString(total));
		}
	}
	
	public void animateBahama() {
		leftSideText.setTranslateY(size.height);
		KeyFrame k = new KeyFrame(Duration.millis(3), e -> {
			leftSideText.setTranslateY(leftSideText.getTranslateY() - 1);
		});
		Timeline t = new Timeline(k);
		t.setCycleCount(size.height);
		t.play();
	}
	
	public void animateSum(Label label, double millis, double newSum) {
		KeyValue vanish1 = new KeyValue(label.opacityProperty(), 1);
		KeyValue vanish2 = new KeyValue(label.opacityProperty(), 0);
		KeyFrame vanishNew = new KeyFrame(Duration.millis(millis), vanish1, vanish2);
		KeyFrame change = new KeyFrame(Duration.millis(1), e -> {
			label.setText(currency.currencyAsString(newSum));
		});
		KeyValue appear1 = new KeyValue(label.opacityProperty(), 0);
		KeyValue appear2 = new KeyValue(label.opacityProperty(), 0.98);
		KeyFrame appearNew = new KeyFrame(Duration.millis(millis), appear1, appear2);
		Timeline v = new Timeline(vanishNew);
		Timeline c = new Timeline(change);
		Timeline a = new Timeline(appearNew);
		SequentialTransition s = new SequentialTransition(v, c, a);
		s.play();
	}
	
	public void animateCustomerNumber(Node node, double millis) {		
		FadeTransition fade = new FadeTransition();
		fade.setDuration(Duration.millis(millis));
		fade.setCycleCount(1);
		fade.setAutoReverse(true);
		fade.setNode(node);
		fade.setFromValue(0);
		fade.setToValue(0.98);
		fade.play();
	}
	
	public CheckBox[] customerCheckBoxes(int index) {
		CheckBox[] currentCustomerCheckBoxes = new CheckBox[7];
		currentCustomerCheckBoxes[0] = checkBoxBar[index];
		currentCustomerCheckBoxes[1] = checkBoxMember[index];
		currentCustomerCheckBoxes[2] = checkBoxHansefit[index];
		currentCustomerCheckBoxes[3] = checkBoxChild[index];
		currentCustomerCheckBoxes[4] = checkBoxStudent[index];
		currentCustomerCheckBoxes[5] = checkBoxTennisWA[index];
		currentCustomerCheckBoxes[6] = checkBoxTennisJS[index];
		return currentCustomerCheckBoxes;
	}
	
	/* Herausgenommen wegen Problemen mit File bzw. Path bzw. URL
	public void record() {
		String toWrite = "";
		try {
			toWrite = rw.lesen("record.csv")[rw.lesen("record.csv").length - 1].split(";")[0];
		} catch (IOException e) {
			e.printStackTrace();
		}
		Integer number = 0;
		try {
			number = (Integer.parseInt(toWrite) + 1);
		} catch (Exception e) {
			number = 1;
		}
		
		for (int i = 0; i < spinnerCustomers.getValueFactory().getValue(); i++) {
			toWrite = number.toString()+";";
			toWrite += player[i].getText()+";";
			toWrite += dt.date_ddMMyyyy()+";";
			toWrite += dt.date_HHmm()+";";
			if (checkBoxFamily.isSelected()) {
				toWrite += "Familie;";
			} else {
				toWrite += spinnerCustomers.getValueFactory().getValue()+";";
			}
			try {
				toWrite += filialCodes.get(rw.lesen("settings/filiale.txt")[0])+";";
			} catch (IOException e) {
				toWrite += ";";
				e.printStackTrace();
			}
			
			for (RadioButton rb : tc.rbSportart) {
				if(rb.isSelected()) {
					toWrite += rb.getText()+";";
					break;
				}
			}
			toWrite += tc.spinnerEco.getValueFactory().getValue()+";";
			toWrite += tc.spinnerPrime.getValueFactory().getValue()+";";
			toWrite += checkBoxBar[i].isSelected()+";";
			toWrite += checkBoxMember[i].isSelected()+";";
			toWrite += checkBoxHansefit[i].isSelected()+";";
			toWrite += checkBoxChild[i].isSelected()+";";
			toWrite += checkBoxStudent[i].isSelected()+";";
			toWrite += checkBoxTennisWA[i].isSelected()+";";
			toWrite += checkBoxTennisJS[i].isSelected()+";";
			toWrite += sums[i].getText()+";";
			toWrite += endlineLabels[endlineLabels.length - 1].getText();
			rw.schreibenWeiter("record.csv", toWrite);
		}
	}
	*/
	
	public void actionsSportarten() {
		for (RadioButton rb : tc.rbSportart) {
			if (rb.getText().equals("Tennis")) {
				rb.setOnAction(e -> {
					for (int i = 0; i < checkBoxTennisWA.length; i++) {
						checkBoxTennisWA[i].setDisable(false);
						checkBoxTennisJS[i].setDisable(false);
					}
					if (spinnerCustomers.getValueFactory().getValue() > 4) {
						actions.changedCustomerNumber(4, g);
						spinnerCustomers.getValueFactory().setValue(4);
					}
					liveCalculation(true);
				});
				
				
			} else  if (rb.getText().equals("Squash")){
				rb.setOnAction(e -> {					
					for (int i = 0; i < checkBoxTennisWA.length; i++) {
						checkBoxTennisWA[i].setDisable(true);
						checkBoxTennisJS[i].setDisable(true);
					}
					if (spinnerCustomers.getValueFactory().getValue() > 4) {
						actions.changedCustomerNumber(4, g);
						spinnerCustomers.getValueFactory().setValue(4);
					}
					liveCalculation(true);
				});
			} else {
				rb.setOnAction(e -> {
					liveCalculation(true);
					for (int i = 0; i < checkBoxTennisWA.length; i++) {
						checkBoxTennisWA[i].setDisable(true);
						checkBoxTennisJS[i].setDisable(true);
					}
				});
			}
		}
	}
	
	public void actionsTimeSpinners() {
		//Damit die Spinner alle gleich gross sind
		tc.spinnerEco.widthProperty().addListener(e -> {
			spinnerCustomers.setMaxWidth(tc.spinnerEco.getWidth());
		});
		
		actionsSportarten();
		
		tc.spinnerEco.valueProperty().addListener((v, oldValue, newValue) -> {
			int old = oldValue;
			if (tc.spinnerPrime.getValueFactory().getValue() == 0 && tc.spinnerEco.getValueFactory().getValue() == 0) {
				tc.spinnerPrime.getValueFactory().setValue(1);
			}
			if (tc.spinnerPrime.getValueFactory().getValue() + newValue > 6) {
				tc.spinnerEco.getValueFactory().setValue(old);
			}
			liveCalculation(true);
		});
		
		tc.spinnerPrime.valueProperty().addListener((v, oldValue, newValue) -> {
			int old = oldValue;
			if (tc.spinnerPrime.getValueFactory().getValue() == 0 && tc.spinnerEco.getValueFactory().getValue() == 0) {
				tc.spinnerEco.getValueFactory().setValue(1);
			}
			if (tc.spinnerEco.getValueFactory().getValue() + newValue > 6) {
				tc.spinnerPrime.getValueFactory().setValue(old);
			}
			liveCalculation(true);
		});
	}
	
	public void sortCustomers() {
		ArrayList<Customer> unsortedCustomers = new ArrayList<Customer>();
		for (int i = 0; i < spinnerCustomers.getValueFactory().getValue(); i++) {
			unsortedCustomers.add(new Customer(player[i].getText(),
					checkBoxMember[i].isSelected(),
					checkBoxHansefit[i].isSelected(),
					checkBoxChild[i].isSelected(),
					checkBoxStudent[i].isSelected(),
					checkBoxTennisWA[i].isSelected(),
					checkBoxTennisJS[i].isSelected(),
					checkBoxFamily.isSelected()));
		}
		ArrayList<Customer> sortedCustomers = new ArrayList<Customer>();
		for (int i = 0; i < unsortedCustomers.size(); i++) {
			if (unsortedCustomers.get(i).getBar()) {
				sortedCustomers.add(unsortedCustomers.get(i));
				unsortedCustomers.remove(i);
				i--;
			}
		}
		for (int i = 0; i < unsortedCustomers.size(); i++) {
			if (unsortedCustomers.get(i).getStudent()) {
				sortedCustomers.add(unsortedCustomers.get(i));
				unsortedCustomers.remove(i);
				i--;
			}
		}
		for (int i = 0; i < unsortedCustomers.size(); i++) {
			if (unsortedCustomers.get(i).getChild()) {
				sortedCustomers.add(unsortedCustomers.get(i));
				unsortedCustomers.remove(i);
				i--;
			}
		}
		boolean tennis = false;
		for (RadioButton rb : tc.rbSportart) {
			if (rb.isSelected() && rb.getText().equals("Tennis")) {
				tennis = true;
			}
		}
		if (tennis) {
			for (int i = 0; i < unsortedCustomers.size(); i++) {
				if (unsortedCustomers.get(i).getTennisWA()) {
					sortedCustomers.add(unsortedCustomers.get(i));
					unsortedCustomers.remove(i);
					i--;
				}
			}
			for (int i = 0; i < unsortedCustomers.size(); i++) {
				if (unsortedCustomers.get(i).getTennisJS()) {
					sortedCustomers.add(unsortedCustomers.get(i));
					unsortedCustomers.remove(i);
					i--;
				}
			}
		}
		for (int i = 0; i < unsortedCustomers.size(); i++) {
			if (unsortedCustomers.get(i).getHansefit()) {
				sortedCustomers.add(unsortedCustomers.get(i));
				unsortedCustomers.remove(i);
				i--;
			}
		}
		for (int i = 0; i < unsortedCustomers.size(); i++) {
			if (unsortedCustomers.get(i).getMember()) {
				sortedCustomers.add(unsortedCustomers.get(i));
				unsortedCustomers.remove(i);
				i--;
			}
		}
		for (int i = 0; i < unsortedCustomers.size(); i++) {
			sortedCustomers.add(unsortedCustomers.get(i));
			unsortedCustomers.remove(i);
			i--;
		}
		
		for (int i = 0; i < sortedCustomers.size(); i++) {
			player[i].setText(sortedCustomers.get(i).getName());
			checkBoxBar[i].setSelected(sortedCustomers.get(i).getBar());
			checkBoxMember[i].setSelected(sortedCustomers.get(i).getMember());
			checkBoxHansefit[i].setSelected(sortedCustomers.get(i).getHansefit());
			checkBoxChild[i].setSelected(sortedCustomers.get(i).getChild());
			checkBoxStudent[i].setSelected(sortedCustomers.get(i).getStudent());
			checkBoxTennisWA[i].setSelected(sortedCustomers.get(i).getTennisWA());
			checkBoxTennisJS[i].setSelected(sortedCustomers.get(i).getTennisJS());
		}
	}
}
