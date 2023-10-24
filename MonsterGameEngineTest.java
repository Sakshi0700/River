package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class MonsterGameEngineTest {

    // Private Fields
    private final Item MONSTER_1 = Item.ITEM_0;
    private final Item MUNCHKIN_1 = Item.ITEM_1;
    private final Item MONSTER_2 = Item.ITEM_2;
    private final Item MUNCHKIN_2 = Item.ITEM_3;
    private final Item MONSTER_3 = Item.ITEM_4;
    private final Item MUNCHKIN_3 = Item.ITEM_5;
    private GameEngine engine;


    @Before
    public void setUp() {
        engine = new MonsterGameEngine();
    }


    private void transport(Item obj) {
        engine.loadBoat(obj);
        engine.rowBoat();
        engine.unloadBoat(obj);
    }


    @Test
    public void testObjectCallThroughs() {
        // Checking for Monster1
        Assert.assertEquals("M1", engine.getItemLabel(MONSTER_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(MONSTER_1));
        Assert.assertEquals(Color.BLUE, engine.getItemColor(MONSTER_1));


        // Checking for Munchkin1
        Assert.assertEquals("K1", engine.getItemLabel(MUNCHKIN_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(MUNCHKIN_1));
        Assert.assertEquals(Color.GREEN, engine.getItemColor(MUNCHKIN_1));

        // Checking for Monster2
        Assert.assertEquals("M2", engine.getItemLabel(MONSTER_2));
        Assert.assertEquals(Location.START, engine.getItemLocation(MONSTER_2));
        Assert.assertEquals(Color.BLUE, engine.getItemColor(MONSTER_2));

        // Checking for Munchkin2
        Assert.assertEquals("K2", engine.getItemLabel(MUNCHKIN_2));
        Assert.assertEquals(Location.START, engine.getItemLocation(MUNCHKIN_2));
        Assert.assertEquals(Color.GREEN, engine.getItemColor(MUNCHKIN_2));

        // Checking for Monster3
        Assert.assertEquals("M3", engine.getItemLabel(MONSTER_3));
        Assert.assertEquals(Location.START, engine.getItemLocation(MONSTER_3));
        Assert.assertEquals(Color.BLUE, engine.getItemColor(MONSTER_3));

        // Checking for Munchkin3
        Assert.assertEquals("K3", engine.getItemLabel(MUNCHKIN_3));
        Assert.assertEquals(Location.START, engine.getItemLocation(MUNCHKIN_3));
        Assert.assertEquals(Color.GREEN, engine.getItemColor(MUNCHKIN_3));
    }


    @Test
    public void testMidTransportMonster() {
        Assert.assertEquals(Location.START, engine.getBoatLocation());
        Assert.assertEquals(Location.START, engine.getItemLocation(MONSTER_1));
        transport(MONSTER_1);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(MONSTER_1));
        Assert.assertEquals(Location.FINISH, engine.getBoatLocation());
    }


    @Test
    public void testMidTransportMunchkin() {
        Assert.assertEquals(Location.START, engine.getBoatLocation());
        Assert.assertEquals(Location.START, engine.getItemLocation(MUNCHKIN_1));
        transport(MUNCHKIN_1);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(MUNCHKIN_1));
        Assert.assertEquals(Location.FINISH, engine.getBoatLocation());
    }


    @Test
    public void testWinningGame() {
        // Check at the beginning
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // Move Monsters on Finish and check
        engine.setItemLocation(MONSTER_1, Location.FINISH);
        engine.setItemLocation(MONSTER_2, Location.FINISH);
        engine.setItemLocation(MONSTER_3, Location.FINISH);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // Move Munchkins on Finish and check
        engine.setItemLocation(MUNCHKIN_1, Location.FINISH);
        engine.setItemLocation(MUNCHKIN_2, Location.FINISH);
        engine.setItemLocation(MUNCHKIN_3, Location.FINISH);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
    }


    @Test
    public void testLosingGame() {
        // Check at the beginning
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // Move Monsters on Finish and check
        engine.setItemLocation(MONSTER_1, Location.FINISH);
        engine.setItemLocation(MONSTER_2, Location.FINISH);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // Move only one Munchkin on Finish and check
        transport(MUNCHKIN_1);
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }


    @Test
    public void testSetItemLocationAndReset() {
        // Checking for Monster
        Assert.assertEquals(Location.START, engine.getItemLocation(MONSTER_1));
        engine.setItemLocation(MONSTER_1, Location.FINISH);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(MONSTER_1));

        // Checking for Munchkin
        Assert.assertEquals(Location.START, engine.getItemLocation(MUNCHKIN_1));
        engine.setItemLocation(MUNCHKIN_1, Location.FINISH);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(MUNCHKIN_1));

        // Reset Game should reset their positions
        engine.resetGame();
        Assert.assertEquals(Location.START, engine.getItemLocation(MONSTER_1));
        Assert.assertEquals(Location.START, engine.getItemLocation(MUNCHKIN_1));
    }

}
