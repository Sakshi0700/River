package river;

import java.awt.Color;
import java.util.*;

public class FarmerGameEngine extends AbstractGameEngine {

    // Private Fields
    private final Item BEANS = Item.ITEM_0;
    private final Item GOOSE = Item.ITEM_1;
    private final Item WOLF = Item.ITEM_2;
    private final Item FARMER = Item.ITEM_3;


    // Constructor
    public FarmerGameEngine() {
        itemMap = new EnumMap<>(Item.class);
        itemMap.put(BEANS, new GameObject("B", Location.START, Color.CYAN,false));
        itemMap.put(GOOSE, new GameObject("G", Location.START, Color.CYAN,false));
        itemMap.put(WOLF, new GameObject("W", Location.START, Color.CYAN,false));
        itemMap.put(FARMER, new GameObject("", Location.START, Color.MAGENTA,true));
        boatLocation = Location.START;
    }
    @Override
    public List<Item> getItems() {
//        return List.of(Item.ITEM_0, Item.ITEM_1, Item.ITEM_2, Item.ITEM_3);
        List<Item> items = new ArrayList<>();
        items.add(Item.ITEM_0);
        items.add(Item.ITEM_1);
        items.add(Item.ITEM_2);
        items.add(Item.ITEM_3);
        return items;
    }


    @Override
    public int numberOfItems() {
        return itemMap.size();
    }

    @Override
    public boolean getItemIsDriver(Item item) {
        return item==Item.ITEM_3;
    }


    @Override
    public void rowBoat() {
        assert (boatLocation != Location.BOAT);

        // Check if Farmer is on the boat
        if (getItemLocation(FARMER) == Location.BOAT) {
            if (boatLocation == Location.START) {
                boatLocation = Location.FINISH;
            } else {
                boatLocation = Location.START;
            }
        }
    }


    @Override
    public boolean gameIsLost() {
        if (getItemLocation(GOOSE) == Location.BOAT) {
            return false;
        }
        if (getItemLocation(GOOSE) == getItemLocation(FARMER)) {
            return false;
        }
        if (getItemLocation(GOOSE) == boatLocation) {
            return false;
        }
        if (getItemLocation(GOOSE) == getItemLocation(WOLF)) {
            return true;
        }
        if (getItemLocation(GOOSE) == getItemLocation(BEANS)) {
            return true;
        }
        return false;
    }

}
