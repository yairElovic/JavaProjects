package App;

public class BattleShip {
    private int spaces;
    private String name;
    private int hits;
    private boolean isSunk;

    public BattleShip(int spaces, String name) {
        this.spaces = spaces;
        this.name = name;
        this.hits = 0;
        this.isSunk = false;
    }

    /*
     * This method updates the amount of hits a ship took and if it took more than
     * the allotted amount of hits, sinks the ship and prints to the console the
     * ships been sunk
     */
    public boolean setHits() {
        this.hits++;
        if (this.hits == this.spaces) {
            this.isSunk = true;
            System.out.println("You have sunk the " + this.getName() + " Ship!");
        }
        return this.isSunk;
    }

    /**
     * @return int return the spaces
     */
    public int getSpaces() {
        return spaces;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return int return the hits
     */
    public int getHits() {
        return hits;
    }

    /**
     * @return boolean return the isSunk
     */
    public boolean getIsSunk() {
        return isSunk;
    }

}
