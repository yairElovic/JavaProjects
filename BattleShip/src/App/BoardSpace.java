package App;

public class BoardSpace {
    private int column;
    private char row;
    private boolean hasShip;
    private BattleShip ship;
    private boolean isHit;
    private boolean called;

    public BoardSpace(int column, char row) {
        this.column = column;
        this.row = row;
        this.hasShip = false;
        this.called = false;
        this.isHit = false;
    }

    public int getColumn() {
        return this.column;
    }

    public char getRow() {
        return this.row;
    }

    public boolean getHasShip() {
        return this.hasShip;
    }

    public boolean getCalled() {
        return this.called;
    }

    public boolean getIsHit() {
        return this.isHit;
    }

    public void setShip(BattleShip addedShip) {
        this.ship = addedShip;
        this.hasShip = true;
    }

    public BattleShip getShip() {
        if (this.ship == null) {
            return null;
        }
        return this.ship;
    }

    /*
     * Method that tries a space, if its been called sends a exception,
     * if there is a ship there, the system updates the amount of hits that happened
     * on the ship,
     * updates that spots ishit to true and returns true
     */
    public boolean trySpace() throws gameException {
        if (called) {
            throw new gameException("This space has already been called try another one");
        } else {
            this.called = true;
            if (!this.hasShip) {
                return false;
            } else {
                this.ship.setHits();
                this.isHit = true;
                return true;
            }
        }
    }

}
