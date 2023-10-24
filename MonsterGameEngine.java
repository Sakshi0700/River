package river;

import java.awt.Color;
import java.util.*;

public class MonsterGameEngine extends AbstractGameEngine {

    // Private Fields
    private final Item MONSTER_1 = Item.ITEM_0;
    private final Item MUNCHKIN_1 = Item.ITEM_1;
    private final Item MONSTER_2 = Item.ITEM_2;
    private final Item MUNCHKIN_2 = Item.ITEM_3;
    private final Item MONSTER_3 = Item.ITEM_4;
    private final Item MUNCHKIN_3 = Item.ITEM_5;

    // Constructor
    public MonsterGameEngine() {
        itemMap = new EnumMap<>(Item.class);
        itemMap.put(MONSTER_1, new GameObject("M1", Location.START, Color.BLUE,true));
        itemMap.put(MUNCHKIN_1, new GameObject("K1", Location.START, Color.GREEN,true));
        itemMap.put(MONSTER_2, new GameObject("M2", Location.START, Color.BLUE,true));
        itemMap.put(MUNCHKIN_2, new GameObject("K2", Location.START, Color.GREEN,true));
        itemMap.put(MONSTER_3, new GameObject("M3", Location.START, Color.BLUE,true));
        itemMap.put(MUNCHKIN_3, new GameObject("K3", Location.START, Color.GREEN,true));
        boatLocation = Location.START;
    }


    @Override
    public int numberOfItems() {
        return itemMap.size();
    }

    @Override
    public boolean getItemIsDriver(Item item) {
        return false;
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
    public boolean gameIsLost() {
        int countMonstersStart = 0;
        int countMunchkinsStart = 0;
        int countMonstersFinish = 0;
        int countMunchkinsFinish = 0;

        // If boat is at start shore
        if (boatLocation == Location.START) {
            for (Item item : Item.values()) {
                if (!(item.ordinal() < numberOfItems())) break;

                // Calculate Positions of Monsters and Munchkins at both shores
                if ((getItemLocation(item) == Location.START)
                        || (getItemLocation(item) == Location.BOAT)) {
                    if (item.ordinal() % 2 == 0) countMonstersStart++;
                    else countMunchkinsStart++;
                } else {
                    if (item.ordinal() % 2 == 0) countMonstersFinish++;
                    else countMunchkinsFinish++;
                }
            }
        }

        // If boat is at finish shore
        else {
            for (Item item : Item.values()) {
                if (!(item.ordinal() < numberOfItems())) break;

                // Calculate Positions of Monsters and Munchkins at both shores
                if ((getItemLocation(item) == Location.FINISH)
                        || (getItemLocation(item) == Location.BOAT)) {
                    if (item.ordinal() % 2 == 0) countMonstersFinish++;
                    else countMunchkinsFinish++;
                } else {
                    if (item.ordinal() % 2 == 0) countMonstersStart++;
                    else countMunchkinsStart++;
                }
            }
        }
        return (countMunchkinsFinish > 0) && (countMunchkinsStart > 0) &&
                ((countMonstersStart > countMunchkinsStart) || (countMonstersFinish > countMunchkinsFinish));
    }
}
