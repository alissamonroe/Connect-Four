package application;

/**
 * Final Project: Connect Four
 * 
 * @author alissamonroe
 *
 */
public class ConnectFour {

	/** Two dimensional array for the game board */
	private String[][] gameBoard;

	/** Variable to hold the token for the first player, which is red */
	public final String redToken;

	/** Variable to hold the token for the second player, which is black */
	public final String blackToken;

	/** Variable to hold empty token; indicates a spot is empty */
	public final String emptyToken;

	/** Variable to hold the number of rows for the game board */
	public static final int ROWNUM = 6;

	/** Variable to hold the number of columns for the game board */
	public static final int COLUMNNUM = 7;

	/** Variable to keep track of the turn of the token */
	private String turn;

	/** Variable for the winner */
	private String winner;

	/** Variable to count turns */
	private int countTurn;

	/**
	 * Array that stores the height/amount of tokens in each column; used to
	 * determine where the new token will fall
	 */
	private int[] columnFill;

	/** Default constructor */
	public ConnectFour() {
		redToken = " RED ";
		blackToken = " BLK ";
		emptyToken = "  -  ";
		turn = redToken;
		winner = emptyToken;

		gameBoard = new String[ROWNUM][COLUMNNUM];

		for (int i = ROWNUM - 1; i >= 0; i--) {
			for (int j = 0; j < COLUMNNUM; ++j) {
				gameBoard[i][j] = "  -  ";
			}
		}

		columnFill = new int[] { ROWNUM - 1, ROWNUM - 1, ROWNUM - 1, ROWNUM - 1, ROWNUM - 1, ROWNUM - 1, ROWNUM - 1 };
	}

	/**
	 * Returns true if the game is over (draw or win)
	 * 
	 * @return Whether the game is over or not
	 */
	public Boolean gameOver() {
		if (countTurn == ROWNUM * COLUMNNUM) {
			return true;
		} else if (winner.equals(redToken) || winner.equals(blackToken)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Insert a token in the position indicated provided it's valid
	 * 
	 * @param column
	 *            The column number of the new token
	 * @throws Exception
	 *             If the column number is invalid or if a particular column is
	 *             full
	 */
	public void makeMove(int column) throws Exception {
		if ((column < COLUMNNUM && column >= 0) && columnFill[column] >= 0) {
			countTurn += 1;
			gameBoard[columnFill[column]][column] = turn;
			result(columnFill[column], column);
			columnFill[column] -= 1;
			switchTurn();
		} else if ((column < COLUMNNUM && column >= 0) && columnFill[column] < 0) {
			throw new Exception("That column is full - Try again.");
		} else {
			throw new Exception("You must stay in bounds - Try again");
		}
	}

	/**
	 * Returns the token of the winner or returns the empty token if it's a draw
	 * 
	 * @return The token of the winner
	 */
	public void result(int row, int column) {
		findColumn(column);
		findRow(column);
		findDiagonalLeft(row, column);
		findDiagonalRight(row, column);
	}

	/**
	 * Getter for turn
	 * 
	 * @return The turn
	 */
	public String getTurn() {
		return turn;
	}

	/**
	 * Getter for winner
	 * 
	 * @return The winner
	 */
	public String getWinner() {
		return winner;
	}

	/**
	 * Method to switch turns
	 */
	private void switchTurn() {
		if (turn.equals(redToken)) {
			turn = blackToken;
		} else if (turn.equals(blackToken)) {
			turn = redToken;
		}
	}

	/**
	 * Method to get where the token is placed
	 */
	public int getHeight(int columnNum) {
		return columnFill[columnNum];
	}

	/**
	 * Method to find a vertical win
	 * 
	 * @param newColumn
	 *            The new column
	 */
	public void findColumn(int newColumn) {
		int winCount = 0;

		for (int i = columnFill[newColumn]; i < ROWNUM; i++) {
			if (gameBoard[i][newColumn].equals(turn))
				winCount++;
			else
				break;
		}

		if (winCount >= 4)
			winner = turn;
	}

	/**
	 * Method to find a horizontal win
	 * 
	 * @param newColumn
	 *            The new column
	 */
	public void findRow(int newColumn) {
		int winCount = 0;

		for (int i = newColumn; i >= 0; i--) {
			if (gameBoard[columnFill[newColumn]][i].equals(turn))
				winCount++;
			else
				break;
		}

		for (int i = newColumn + 1; i < COLUMNNUM; i++) {
			if (gameBoard[columnFill[newColumn]][i].equals(turn))
				winCount++;
			else
				break;
		}

		if (winCount >= 4)
			winner = turn;
	}

	/**
	 * Method to find a left diagonal win
	 * 
	 * @param newRow
	 *            The new row
	 * @param newColumn
	 *            The new column
	 */
	public void findDiagonalLeft(int newRow, int newColumn) {
		int winCountUp = -1;
		int col = newColumn;

		for (int i = newRow; i >= 0 && col <= ROWNUM; i--) {
			if (gameBoard[i][col].equals(turn)) {
				winCountUp++;
				col++;
			} else
				break;
		}

		col = newColumn;

		for (int i = newRow; i < ROWNUM && col >= 0; i++) {
			if (gameBoard[i][col].equals(turn)) {
				winCountUp++;
				col--;
			} else
				break;
		}

		if (winCountUp >= 4)
			winner = turn;
	}

	/**
	 * Method to find a right diagonal win
	 * 
	 * @param newRow
	 *            The new row
	 * @param newColumn
	 *            The new column
	 */
	public void findDiagonalRight(int newRow, int newColumn) {
		int winCountDown = -1;
		int col = newColumn;

		for (int i = newRow; i >= 0 && col >= 0; i--) {
			if (gameBoard[i][col].equals(turn)) {
				winCountDown++;
				col--;
			} else
				break;
		}

		col = newColumn;

		for (int i = newRow; i < ROWNUM && col < COLUMNNUM; i++) {
			if (gameBoard[i][col].equals(turn)) {
				winCountDown++;
				col++;
			} else
				break;
		}

		if (winCountDown >= 4)
			winner = turn;
	}

	/**
	 * toString method; prints out the game board
	 */
	public String toString() {
		System.out.print(" C  O  N  N  E  C  T    F  O  U  R\n" + "-----------------------------------");
		for (int i = 0; i < ROWNUM; i++) {
			System.out.println(" ");
			for (int j = 0; j < COLUMNNUM; j++) {
				System.out.print(gameBoard[i][j]);
			}
			System.out.println();
		}
		return "-----------------------------------" + "\n  1    2    3    4    5    6    7\n";
	}
}
