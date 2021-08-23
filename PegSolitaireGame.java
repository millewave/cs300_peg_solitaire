import java.util.Scanner;

public class PegSolitaireGame 
{	
	private static int[][] moveTable = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

	/**
	 * This method is responsible for everything from displaying the opening 
	 * welcome message to printing out the final thank you.  It will clearly be
	 * helpful to call several of the following methods from here, and from the
	 * methods called from here.  See the Sample Runs below for a more complete
	 * idea of everything this method is responsible for.
	 * 
	 * @param args - any command line arguments may be ignored by this method.
	 */
	public static void main(String[] args)
	{
		System.out.println("WELCOME TO CS300 PEG SOLITAIRE!");
		System.out.println("===============================");
		System.out.println();

		Scanner scan = new Scanner(System.in);
		//Board Menu
		System.out.println("Board Style Menu");
		System.out.println(" 1) Cross");
		System.out.println(" 2) Circle");
		System.out.println(" 3) Triangle");
		System.out.println(" 4) Simple T");

		//User Input for Board pattern
		int i = readValidInt(scan, "Choose a board style: ", 1, 4);
		char[][] gameBoard = createBoard(i);

		//User Input for moving pegs on board
		while(countMovesAvailable(gameBoard) > 0){
			displayBoard(gameBoard);
			int[] userMove = readValidMove(scan, gameBoard);
			gameBoard = performMove(gameBoard, userMove[0], userMove[1], userMove[2]);
		}

		displayBoard(gameBoard);
		if(countPegsRemaining(gameBoard) == 1) {
			System.out.println("Congrats, you won!");
		} else {
			System.out.println("It looks like there are no more legal moves.  Please try again.");
		}

		System.out.println("==========================================");
		System.out.println("THANK YOU FOR PLAYING CS300 PEG SOLITAIRE!");


	}

	//Checks if the User's input falls within a given range	
	public static int readValidInt(Scanner in, String prompt, int min, int max)
	{
		System.out.print(prompt);
		while (true) {
			int i = -1;
			//checks if the User entered an integer
			try {
				String s = in.nextLine();
				i = Integer.parseInt(s);
			} catch (Exception e) {
				System.out.print("Please enter your choice as an integer between " + min + " and " + max + ": ");
				continue;
			}

			//checks if the integer falls within the min and max range
			if(i > max || i < min) { //These two error messages are redundant
				System.out.print("Please enter your choice as an integer between " + min + " and " + max + ": ");
				continue;
	
			}
			
			System.out.println();
			return i;
		}

	}

	//Initializes a board pattern specified by the User
	public static char[][] createBoard(int boardType)
	{
		//Initializes the 2D array
		switch(boardType) {
			case 1: { //Cross board
				char[][] board1 = 
				{{'#','#','#','@','@','@','#','#','#'},
				{'#','#','#','@','@','@','#','#','#'},
			    {'@','@','@','@','@','@','@','@','@'},
				{'@','@','@','@','-','@','@','@','@'},
				{'@','@','@','@','@','@','@','@','@'},
				{'#','#','#','@','@','@','#','#','#'},
				{'#','#','#','@','@','@','#','#','#'}};
				return board1;
			}
			case 2: { //Circle board
				char[][] board2 = 
				{{'#','-','@','@','-','#'},
				{'-','@','@','@','@','-'},
				{'@','@','@','@','@','@'},
				{'@','@','@','@','@','@'},
				{'-','@','@','@','@','-'},
				{'#','-','@','@','-','#'}};
				return board2;
			}
			case 3: { //Triangle board
				char[][] board3 = 
				{{'#','#','#','-','@','-','#','#','#'},
				{'#','#','-','@','@','@','-','#','#'},
				{'#','-','@','@','@','@','@','-','#'},
				{'-','@','@','@','@','@','@','@','-'}};
				return board3;
			}
			case 4: { //Simple T board
				char[][] board4 = 
				{{'-','-','-','-','-'},
				{'-','@','@','@','-'},
				{'-','-','@','-','-'},
				{'-','-','@','-','-'},
				{'-','-','-','-','-'}};
				return board4;
			}


		}
		return null;
	}
	
	//Prints out the board
	public static void displayBoard(char[][] board)
	{
		//The labels on the side of the board
		int rowLabel = 1;
		int columnLabel = 1;
		//prints out the board
		for(int row = 0; row <= board.length; row++)
		{
			for(int column = 0; column <= board[0].length; column++)
			{
				if(row == 0 && column == 0) { //prints the labels on the side of the board
					System.out.print(" "); //the space between row and column label

				} else if(row == 0) { //prints the value and then increments by 1
					System.out.print(columnLabel++);

				} else if(column == 0) {
					System.out.print(rowLabel++);

				} else { //prints out the pegs, empty holes, and spaces
					System.out.print(board[row - 1][column - 1]);

				}

			}
			System.out.println(); //jumps to the next row
		}
	}

	//Determines whether the player's move choices are valid
	public static int[] readValidMove(Scanner in, char[][] board)
	{
		//Runs until the user enters a valid move
		while(true) {
			//Takes in the User's input for their choice of peg and direction
			//Ensures that the User's input falls in the appropriate range
			int row = readValidInt(in, "Choose the ROW of a peg you'd like to move: ", 1, board.length);
			int column = readValidInt(in, "Choose the COLUMN of a peg you'd like to move: ", 1, board[0].length);
			int direction = readValidInt(in, "Choose a DIRECTION to move that peg 1) UP, 2) DOWN, 3) LEFT, or 4) RIGHT: ", 1, 4);

			//returns an array containing the User's moves if they are legal
			//checks if the location the User specified points torwards a peg
			//checks if the direction the User specified is a legal move
			if(isValidMove(board, row - 1, column - 1, direction)){
				int[] moveUser = {row - 1, column - 1, direction};
				return moveUser;
			}

			//converts the User's input for the direction into a label for the error message
			String directionLabel = " ";
			if(direction == 1) {
				directionLabel = "UP";

			} else if(direction == 2) {
				directionLabel = "DOWN";

			} else if(direction == 3) {
				directionLabel = "LEFT";

			} else if(direction == 4) {
				directionLabel = "RIGHT";

			}

			//Error message
			System.out.println("Moving a peg from row " + row + " and column " + column + " " + directionLabel + " is not currently a legal move.");

		}
	}
	
	//checks whether the User's move is valid depending on the current state of the board
	public static boolean isValidMove(char[][] board, int row, int column, int direction)
	{
		//calculates the row and column of the next two cells
		int rstep = moveTable[direction-1][0];
		int cstep = moveTable[direction-1][1];

		//the adjacent peg
		int r1 = row + rstep;
		int c1 = column + cstep;

		//target location
		int r2 = r1 + rstep;
		int c2 = c1 + cstep;

		//checks if the adjacent tiles fall within the board's boundary
		if (r2 < 0 || r2 >= board.length || c2 < 0 || c2 >= board[0].length) {
			return false;
		}

		// checks if the move is legal
		// is there a peg at board[row][column]
		// is there a peg at the adjacent tile
		// is there an empty hole at the tile the target peg will move onto
		return board[row][column] == '@' && board[r1][c1] == '@' && board[r2][c2] == '-';
	}
	
	/**
	 * The parameters of this method are the same as those of the isValidMove()
	 * method.  However this method changes the board state according to this
	 * move parameter (column + row + direction), instead of validating whether
	 * the move is valid.  If the move specification that is passed into this
	 * method does not represent a legal move, then do not modify the board.
	 * 
	 * @param board - the state of the board will be changed by this move.
	 * @param row - the vertical position that a peg will be moved from.
	 * @param column - the horizontal position that a peg will be moved from.
	 * @param direction - the direction of the neighbor to jump this peg over.
	 * @return - the updated board state after the specified move is taken.
	 */
	public static char[][] performMove(char[][] board, int row, int column, int direction)
	{
		int rstep = moveTable[direction-1][0];
		int cstep = moveTable[direction-1][1];
		int r1 = row + rstep;
		int c1 = column + cstep;
		int r2 = r1 + rstep;
		int c2 = c1 + cstep;

		board[row][column] = '-';
		board[r1][c1] = '-';
		board[r2][c2] = '@';

		return board;
	}
	
	/**
	 * This method counts up the number of pegs left within a particular board 
	 * configuration, and returns that number.
	 * 
	 * @param board - the board that pegs are counted from.
	 * @return - the number of pegs found in that board.
	 */
	public static int countPegsRemaining(char[][] board)
	{
		int numberOfPegs = 0;
		for(int row = 0; row < board.length; row++){
			for(int column = 0; column < board[0].length; column++){
				if(board[row][column] == '@') { numberOfPegs++;}

			}
		}
		return numberOfPegs;
	}
	
	/**
	 * This method counts up the number of legal moves that are available to be
	 * performed in a given board configuration.
	 * 
	 * HINT: Would it be possible to call the isValidMove() method for every
	 * direction and from every position within your board?  Counting up the
	 * number of these calls that return true should yield the total number of
	 * moves available within a specific board.
	 * 
	 * @param board - the board that possible moves are counted from.
	 * @return - the number of legal moves found in that board.
	 */
	public static int countMovesAvailable(char[][] board)
	{
		int numberOfMoves = 0;
		for(int row = 0; row < board.length; row++){
			for(int column = 0; column < board[0].length; column++){
				for(int direction = 0; direction < moveTable.length; direction++){
					if(isValidMove(board, row, column, direction+1)) { numberOfMoves++; }
				}

			}
		}
		
		return numberOfMoves;

	}	

}
