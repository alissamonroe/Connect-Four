import java.util.Scanner;

public class PlayConnectFour {
	public static void main(String[] args) {

		ConnectFour game = new ConnectFour();
		Scanner input = new Scanner(System.in);
		int column = -1;
		System.out.println(game);
		while (!game.gameOver()) {
			System.out.print("Player" + game.getTurn() + "- Enter the slot in which you will enter your token: ");
			column = input.nextInt();
			column -= 1;
			System.out.println();
			try {
				game.makeMove(column);
				System.out.println(game);
			} catch (Exception e) {
				System.out.println(e.getMessage() + "\n");
			}
		}

		if (game.getWinner().equals(game.emptyToken))
			System.out.println("IT'S A DRAW!");
		else
			System.out.println(game.getWinner() + "WON!");
	}
}
