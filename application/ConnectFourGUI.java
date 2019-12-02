package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class ConnectFourGUI extends Application implements EventHandler<ActionEvent> {

	/** Object for the game */
	private ConnectFour game;

	/** Array that holds the buttons for token insertion */
	private Button[] slotButton;

	/** Labels for the slots over the entire game board */
	private Circle[][] slotCircle;

	/** The reset button */
	private Button resetButton;

	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Connect Four");
			GridPane GP = new GridPane();
			GP.setAlignment(Pos.TOP_CENTER);
			Scene scene = new Scene(GP, 650, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			game = new ConnectFour();
			slotButton = new Button[ConnectFour.COLUMNNUM];
			slotCircle = new Circle[ConnectFour.ROWNUM][ConnectFour.COLUMNNUM];

			GP.add(createTopMostPane(), 1, 0);
			GP.add(createTopPane(), 1, 1);
			GP.add(createBottomPane(), 1, 2);

			Alert beginAlert = new Alert(Alert.AlertType.INFORMATION,
					"Welcome to Connect Four!\n\nPlayer 1: Red, Player 2: Black\nTo start the game over, press Start Over.\n\nPlayer 1, you may begin.");
			beginAlert.showAndWait();

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method for the top most grid pane, which includes the title and the reset
	 * button (START OVER) button
	 */
	private GridPane createTopMostPane() {
		GridPane topMostGP = new GridPane();
		Label titleLabel = new Label("     C   O   N   N   E   C   T      F   O   U   R");
		titleLabel.setFont(new Font("Courier New", 13));
		titleLabel.setTextFill(Color.ROYALBLUE);
		titleLabel.setPrefSize(440, 60);
		topMostGP.add(titleLabel, 0, 0);

		resetButton = new Button("Start Over");
		resetButton.setPrefSize(120, 40);
		resetButton.setOnAction(this);
		topMostGP.add(resetButton, 1, 0);
		return topMostGP;
	}

	/**
	 * Method for the top pane / middle pane, which includes the column buttons
	 * for token insertion
	 */
	private GridPane createTopPane() {
		GridPane topGP = new GridPane();
		for (int i = 0; i < ConnectFour.COLUMNNUM; i++) {
			slotButton[i] = new Button("" + (i + 1) + "");
			slotButton[i].setPrefSize(80, 40);
			slotButton[i].setOnAction(this);
			topGP.add(slotButton[i], i, 1);
		}
		return topGP;
	}

	/**
	 * Method for the bottom pane, which is just the game board of token slots
	 */
	private GridPane createBottomPane() {
		GridPane bottomGP = new GridPane();
		for (int i = 0; i < ConnectFour.ROWNUM; i++) {
			for (int j = 0; j < ConnectFour.COLUMNNUM; j++) {
				slotCircle[i][j] = new Circle(40);
				slotCircle[i][j].setFill(Color.LIGHTGREY);
				bottomGP.add(slotCircle[i][j], j, i);
			}
		}
		return bottomGP;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent event) {

		/** When the reset button (START OVER) is pressed */
		if (event.getSource() == resetButton) {
			resetBoard();
			game = new ConnectFour();
		}

		/** When any of the column buttons are pressed */
		if (game.gameOver() == false) {
			for (int i = 0; i < ConnectFour.COLUMNNUM; i++) {
				if (event.getSource() == slotButton[i]) {
					try {
						if (game.getTurn().equals(game.redToken)) {
							game.makeMove(i);
							slotCircle[game.getHeight(i) + 1][i].setFill(Color.RED);
						} else if (game.getTurn().equals(game.blackToken)) {
							game.makeMove(i);
							slotCircle[game.getHeight(i) + 1][i].setFill(Color.BLACK);
						}

						if (game.gameOver() == true) {
							if (game.getWinner().equals(game.emptyToken)) {
								Alert drawAlert = new Alert(Alert.AlertType.INFORMATION, "It's a draw!\n\nThe game is now over.\nPress Start Over to begin a new game.");
								drawAlert.showAndWait();
							} else if (game.getWinner().equals(game.redToken)) {
								Alert redWinnerAlert = new Alert(Alert.AlertType.INFORMATION,
										"Player 1 is the winner!\n\nThe game is now over.\nPress Start Over to begin a new game.");
								redWinnerAlert.showAndWait();
							} else if (game.getWinner().equals(game.blackToken)) {
								Alert blackWinnerAlert = new Alert(Alert.AlertType.INFORMATION,
										"Player 2 is the winner!\n\nThe game is now over.\nPress Start Over to begin a new game.");
								blackWinnerAlert.showAndWait();
							}
						}
					} catch (Exception e) {
						Alert errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
						errorAlert.showAndWait();
					}
				}
			}
		}
	}

	/** Method to reset the game board */
	private void resetBoard() {
		for (int i = 0; i < ConnectFour.ROWNUM; i++) {
			for (int j = 0; j < ConnectFour.COLUMNNUM; j++) {
				slotCircle[i][j].setFill(Color.LIGHTGREY);
			}
		}
	}

}
