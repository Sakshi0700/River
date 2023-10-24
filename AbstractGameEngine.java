package river;

import java.awt.Color;
import java.util.EnumMap;

public abstract class AbstractGameEngine implements GameEngine {

    protected EnumMap<Item, GameObject> itemMap;
    protected Location boatLocation;

    // Returns the number of Items on the boat
    private int getBoatCount() {
        int boatCount = 0;

        for (Item item : Item.values()) {
            if (!(item.ordinal() < numberOfItems())) {
                break;
            }
            if (itemMap.get(item).getLocation() == Location.BOAT) {
                boatCount++;
            }
        }

        return boatCount;
    }


    abstract public int numberOfItems();


    @Override
    public String getItemLabel(Item item) {
        return itemMap.get(item).getLabel();
    }


    @Override
    public Color getItemColor(Item item) {
        return itemMap.get(item).getColor();
    }


    @Override
    public Location getItemLocation(Item item) {
        return itemMap.get(item).getLocation();
    }


    @Override
    public void setItemLocation(Item item, Location location) {
        itemMap.get(item).setLocation(location);
    }


    @Override
    public Location getBoatLocation() {
        return boatLocation;
    }


    @Override
    public void loadBoat(Item item) {
        // Return if boat already full
        if (getBoatCount() == 2) {
            return;
        }

        // Check item and boat are on the same shore
        if (getItemLocation(item) == boatLocation) {
            setItemLocation(item, Location.BOAT);
        }
    }


    @Override
    public void unloadBoat(Item item) {
        if (getItemLocation(item) == Location.BOAT) {
            setItemLocation(item, boatLocation);
        }
    }


    @Override
    public void rowBoat() {
        assert (boatLocation != Location.BOAT);

        // Check that boat is not empty
        if (getBoatCount() > 0) {
            if (boatLocation == Location.START) {
                boatLocation = Location.FINISH;
            } else {
                boatLocation = Location.START;
            }
        }
    }


    @Override
    public boolean gameIsWon() {
        for (Item item : Item.values()) {
            if (!(item.ordinal() < numberOfItems())) {
                break;
            }
            if (getItemLocation(item) != Location.FINISH) {
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean gameIsLost() {
        return false;
    }


    @Override
    public void resetGame() {
        for (Item item : Item.values()) {
            if (!(item.ordinal() < numberOfItems())) {
                break;
            }
            setItemLocation(item, Location.START);
        }
        boatLocation = Location.START;
    }


}
