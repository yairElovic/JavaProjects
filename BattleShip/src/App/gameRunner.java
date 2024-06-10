package App;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class gameRunner {
    public static Scanner scanner;
    public static Random random;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        random = new Random();
        ArrayList<BattleShip> availableShipsPlayer = setUpShips();
        ArrayList<BattleShip> availableShipsOpponent = setUpShips();
        GameBoard playerBoard = new GameBoard();
        playerBoard = setShips(playerBoard, availableShipsPlayer);
        GameBoard opponentBoard = new GameBoard();
        opponentBoard = setOpponentShips(opponentBoard, availableShipsOpponent);
        playGame(playerBoard, opponentBoard);

    }

    /*
     * This method is the main game play method
     */
    public static void playGame(GameBoard player, GameBoard opponent) {
        boolean gameOver = false;
        System.out.println("You go first!");
        while (!gameOver) { // check to see if anyone has one,
            boolean playersTurn = true;
            boolean opponentsTurn = true;
            while (playersTurn) { // player gets to play
                opponent.printBoardOpponent();
                System.out.print("Type a character/number combination for your move: ");
                String selectedSpace = scanner.next();
                char spaceColumn = selectedSpace.charAt(0);
                int spaceRow = Integer.parseInt(selectedSpace.substring(1));
                boolean moveASuccess;
                sleep(); // called frequently so that answers arent coming in rapid fire fashion
                try {
                    moveASuccess = opponent.makeAMove(spaceRow, spaceColumn);// calls to see if the move is a success
                } catch (gameException e) {
                    System.out.println(e.getMessage());// catches an instance where a move has already been called
                    continue;
                }
                if (moveASuccess)
                    System.out.println("Hit! ");// called when theres a hit
                else
                    System.out.println("Miss, better luck next time");

                if (!opponent.shipStatus()) { // checks to see if all ships are still active if not then ends the game
                    System.out.println("Game over you won!!");
                    gameOver = true;
                    break;
                }
                sleep();
                playersTurn = false;
                break;
            }
            if (gameOver) {
                break;
            }
            System.out.println("Now its your opponenets turn");
            while (opponentsTurn) {// automated computer turn
                int opponentRow = 1 + random.nextInt(10);// gets random row num
                char opponentColumn = (char) ('A' + random.nextInt(10));// gets random collumn character
                boolean moveASuccess;
                try {
                    moveASuccess = player.makeAMove(opponentRow, opponentColumn); // makes the move
                } catch (gameException e) {
                    continue; // if the moves been made restart the opponenets turn
                }
                sleep();
                if (moveASuccess)
                    System.out.println("They Hit You ");
                else
                    System.out.println("They Missed");

                sleep();

                if (!player.shipStatus()) {
                    System.out.println("Game over you lost");
                    gameOver = true;
                    break;
                }
                player.printBoardPlayer();
                sleep();
                opponentsTurn = false;
                break;
            }

        }
    }

    /*
     * This method allows users to set up their ships
     */
    public static GameBoard setShips(GameBoard board, ArrayList<BattleShip> availableShipsPlayer) {
        while (!availableShipsPlayer.isEmpty()) { // loops through the availableShips array and for each ship allows the
                                                  // user to place it where it belongs
            board.printBoardPlayer();
            System.out.print("Please set the location of your " + availableShipsPlayer.get(0).getName()
                    + " Ship which takes up " + availableShipsPlayer.get(0).getSpaces() + " Spaces: ");
            String selectedSpace = scanner.next();// colelcts users input
            char spaceColumn = selectedSpace.charAt(0);
            int spaceRow = Integer.parseInt(selectedSpace.substring(1));
            if (spaceColumn < 'A' || spaceColumn > 'J' || spaceRow < 1 || spaceRow > 11) {
                System.out.println(selectedSpace + " is not a valid space please try again");
                continue;
            }
            System.out.println("Please select the direction of your ship:");
            System.out.println("1: Vertical");
            System.out.println("2: horizontal");
            System.out.println("3: diagonal");
            int direction = scanner.nextInt();
            ArrayList<BoardSpace> spaces = new ArrayList<BoardSpace>();
            boolean validToAdd = true;
            if (direction == 2) { // adds for horizontal by looping through the rows, keeping the leter the same
                                  // and adding if there are no ships there already
                int endRow = (spaceRow + availableShipsPlayer.get(0).getSpaces()) - 1;
                for (int i = spaceRow; i <= endRow; i++) {
                    BoardSpace currentSpace = board.findLocation(i, spaceColumn);
                    if (currentSpace == null || currentSpace.getHasShip()) {
                        validToAdd = false;
                        break;
                    }
                    spaces.add(currentSpace);

                }
            } else if (direction == 1) {// adds for vertical looping through the characters keeping the INT the same
                for (char i = spaceColumn; i < spaceColumn + availableShipsPlayer.get(0).getSpaces(); i++) {
                    BoardSpace currentSpace = board.findLocation(spaceRow, i);
                    if (currentSpace == null || currentSpace.getHasShip()) {
                        validToAdd = false;
                        break;
                    }
                    spaces.add(currentSpace);
                }
            } else if (direction == 3) { // Diagonal placement
                for (int i = 0; i < availableShipsPlayer.get(0).getSpaces(); i++) {
                    int newRow = spaceRow + i;
                    char newColumn = (char) (spaceColumn + i);
                    BoardSpace currentSpace = board.findLocation(newRow, newColumn);
                    if (currentSpace == null || currentSpace.getHasShip()) {
                        validToAdd = false;
                        break;
                    }
                    spaces.add(currentSpace);
                }
            }
            if (validToAdd) { // checks that all things were valid when adding
                for (BoardSpace currSpace : spaces) {
                    currSpace.setShip(availableShipsPlayer.get(0));
                }
                availableShipsPlayer.remove(0);
            } else {
                System.out.println("There was an issue adding your ship please try again");
            }

        }

        return board;
    }

    /*
     * Adds ships automatically using random generated values for the opponenet
     * board
     */
    public static GameBoard setOpponentShips(GameBoard board, ArrayList<BattleShip> availableShipsOpponent) {
        while (!availableShipsOpponent.isEmpty()) {
            char spaceColumn = (char) ('A' + random.nextInt(10));// random column
            int spaceRow = 1 + random.nextInt(10);// random row
            int direction = 1 + random.nextInt(3); // random direction
            ArrayList<BoardSpace> spaces = new ArrayList<BoardSpace>();
            boolean validToAdd = true;
            if (direction == 2) {// adds horizontally
                int endRow = (spaceRow + availableShipsOpponent.get(0).getSpaces()) - 1;
                for (int i = spaceRow; i <= endRow; i++) {
                    BoardSpace currentSpace = board.findLocation(i, spaceColumn);
                    if (currentSpace == null || currentSpace.getHasShip()) {
                        validToAdd = false;
                        break;
                    }
                    spaces.add(currentSpace);

                }
            } else if (direction == 1) { // adds vertically
                for (char i = spaceColumn; i < spaceColumn + availableShipsOpponent.get(0).getSpaces(); i++) {
                    BoardSpace currentSpace = board.findLocation(spaceRow, i);
                    if (currentSpace == null || currentSpace.getHasShip()) {
                        validToAdd = false;
                        break;
                    }
                    spaces.add(currentSpace);
                }
            } else if (direction == 3) { // Diagonal placement
                for (int i = 0; i < availableShipsOpponent.get(0).getSpaces(); i++) {
                    int newRow = spaceRow + i;
                    char newColumn = (char) (spaceColumn + i);
                    BoardSpace currentSpace = board.findLocation(newRow, newColumn);
                    if (currentSpace == null || currentSpace.getHasShip()) {
                        validToAdd = false;
                        break;
                    }
                    spaces.add(currentSpace);
                }
            }
            if (validToAdd) {
                for (BoardSpace currSpace : spaces) {
                    currSpace.setShip(availableShipsOpponent.get(0));
                }
                availableShipsOpponent.remove(0);
            }
        }
        return board;
    }

    /*
     * Sets up the ships with the types given by the guide to battleship
     */
    private static ArrayList<BattleShip> setUpShips() {
        ArrayList<BattleShip> ships = new ArrayList<BattleShip>();
        ships.add(new BattleShip(5, "Carrier"));
        ships.add(new BattleShip(4, "Battleship"));
        ships.add(new BattleShip(3, "Cruiser"));
        ships.add(new BattleShip(3, "Submarine"));
        ships.add(new BattleShip(2, "Destroyer"));

        return ships;
    }

    public static void sleep() {
        try {
            Thread.sleep(random.nextInt(1000) + 1000); // Sleep for 1000-2000 milliseconds (1-2 seconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
