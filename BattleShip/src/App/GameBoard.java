package App;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class GameBoard {
    List<List<BoardSpace>> spaces;

    public GameBoard() {
        spaces = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<BoardSpace> row = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                row.add(new BoardSpace(i + 1, (char) ('A' + j)));
            }
            spaces.add(row);
        }
    }

    /*
     * Method to print the board for the player
     */
    public void printBoardPlayer() {
        System.out.println("Player's Board:");
        System.out.println("    1 2 3 4 5 6 7 8 9 10"); // Display column numbers
        System.out.println("  ---------------------");

        // Transpose the board
        for (int col = 0; col < spaces.get(0).size(); col++) {
            System.out.print((char) ('A' + col) + " | "); // displays row letters
            for (int row = 0; row < spaces.size(); row++) {
                BoardSpace space = spaces.get(row).get(col);
                if (space.getHasShip()) {
                    if (space.getIsHit()) {
                        System.out.print("X ");// if a ship was hit in this locations how x
                    } else {
                        System.out.print("S "); // if there is a ship in this location show S
                    }
                } else {
                    if (space.getCalled()) {
                        System.out.print("O "); // if spot was Called but didnt result in a hit show O
                    } else {
                        System.out.print("~ "); // empty no interaction shows ~
                    }
                }
            }
            System.out.println("|");
        }

        System.out.println("  ---------------------");
    }

    /*
     * Method to print the board for the opponent
     */
    public void printBoardOpponent() {
        System.out.println("Opponent's Board:");
        System.out.println("    1 2 3 4 5 6 7 8 9 10"); // Display column numbers
        System.out.println("  ---------------------");

        // Transpose the board
        for (int col = 0; col < spaces.get(0).size(); col++) {
            System.out.print((char) ('A' + col) + " | "); // displays row letters
            for (int row = 0; row < spaces.size(); row++) {
                BoardSpace space = spaces.get(row).get(col);
                if (space.getCalled()) {
                    if (space.getIsHit()) {
                        System.out.print("X "); // if a ship was hit in this location shows x
                    } else {
                        System.out.print("O ");// if a spot was called but didnt result in a hit shows o
                    }
                } else {
                    System.out.print("~ ");// empty spots with no interaction show ~
                }
            }
            System.out.println("|");
        }

        System.out.println("  ---------------------");
    }

    /*
     * BRUTE FORCE SEARCH finds location in the 2d list by row and column, first
     * searches the row then searches the columns within that row
     */
    public BoardSpace findLocation(int row, char column) {
        if (row >= 1 && row <= 10 && column >= 'A' && column <= 'J') {
            return spaces.get(row - 1).get(column - 'A');
        } else {
            return null; // Return null if the row or column is out of bounds
        }
    }

    /*
     * Search Algorithm checks all ships for spaces, puts them in a set to reduce
     * duplicates, then checks if all ships are alive, if all are dead, games over
     */
    public boolean shipStatus() {
        Set<BattleShip> ships = new HashSet<>();
        for (int col = 0; col < spaces.get(0).size(); col++) {
            for (int row = 0; row < spaces.size(); row++) {
                if (this.spaces.get(row).get(col).getShip() != null)
                    ships.add(this.spaces.get(row).get(col).getShip());
            }
        }
        boolean stillAlive = false;
        for (BattleShip ship : ships) {
            if (!ship.getIsSunk()) {
                stillAlive = true;
            }
        }

        return stillAlive;
    }

    /*
     * Method to make a move,again using the bruteForce algorithm above
     */
    public boolean makeAMove(int row, char column) throws gameException {
        BoardSpace space = findLocation(row, column);
        return space.trySpace();
    }

}
