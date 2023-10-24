package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class FarmerGameEngineTest {

    // Private Fields
    private final Item BEANS = Item.ITEM_0;
    private final Item GOOSE = Item.ITEM_1;
    private final Item WOLF = Item.ITEM_2;
    private final Item FARMER = Item.ITEM_3;
    private GameEngine engine;


    @Before
    public void setUp() {
        engine = new FarmerGameEngine();
    }


    private void transport(Item obj) {
        // Load the Farmer
        engine.loadBoat(FARMER);
        engine.loadBoat(obj);
        engine.rowBoat();
        engine.unloadBoat(obj);
    }


    @Test
    public void testObjectCallThroughs() {
        Assert.assertEquals(4,engine.numberOfItems());
        Assert.assertNotEquals(6,engine.numberOfItems());


        // Checking for Farmer
        Assert.assertEquals("", engine.getItemLabel(FARMER));
        Assert.assertEquals(Location.START, engine.getItemLocation(FARMER));
        Assert.assertEquals(Color.MAGENTA, engine.getItemColor(FARMER));
        Assert.assertEquals(4,engine.getItems().size());
        Assert.assertTrue(engine.getItemIsDriver(FARMER));

        // Checking for Wolf
        Assert.assertEquals("W", engine.getItemLabel(WOLF));
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(WOLF));
        Assert.assertFalse(engine.getItemIsDriver(BEANS));


        // Checking for Goose
        Assert.assertEquals("G", engine.getItemLabel(GOOSE));
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(GOOSE));

        // Checking for Beans
        Assert.assertEquals("B", engine.getItemLabel(BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        Assert.assertEquals(Color.CYAN, engine.getItemColor(BEANS));
    }


    @Test
    public void testMidTransportGoose() {
        // Checking for Goose
        Assert.assertEquals(Location.START, engine.getBoatLocation());
        Assert.assertEquals(Location.START, engine.getItemLocation(GOOSE));
        transport(GOOSE);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(GOOSE));
        Assert.assertEquals(Location.FINISH, engine.getBoatLocation());
    }


    @Test
    public void testMidTransportWolf() {
        // Checking for Wolf
        Assert.assertEquals(Location.START, engine.getBoatLocation());
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        transport(WOLF);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(WOLF));
        Assert.assertEquals(Location.FINISH, engine.getBoatLocation());
    }


    @Test
    public void testMidTransportBeans() {
        // Checking for Beans
        Assert.assertEquals(Location.START, engine.getBoatLocation());
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        transport(BEANS);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(BEANS));
        Assert.assertEquals(Location.FINISH, engine.getBoatLocation());
    }


    @Test
    public void testSetItemLocationAndReset() {
        // Checking for Beans
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        engine.setItemLocation(BEANS, Location.FINISH);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(BEANS));

        // Checking for Wolf
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
        engine.setItemLocation(WOLF, Location.FINISH);
        Assert.assertEquals(Location.FINISH, engine.getItemLocation(WOLF));

        // Reset Game should reset their positions
        engine.resetGame();
        Assert.assertEquals(Location.START, engine.getItemLocation(BEANS));
        Assert.assertEquals(Location.START, engine.getItemLocation(WOLF));
    }


    @Test
    public void testWinningGame() {
        // transport the goose
        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the wolf
        transport(WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back with the goose
        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport beans
        transport(BEANS);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the goose and unload the Farmer
        transport(GOOSE);
        engine.unloadBoat(FARMER);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
    }
    
    
    @Test
    public void testLosingGameOne() {
        // transport the goose
        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the wolf
        transport(WOLF);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    
    @Test
    public void testLosingGameTwo() {
        // load the goose
        engine.loadBoat(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the goose
        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the beans
        transport(BEANS);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }


    @Test
    public void testError() {
        // transport the goose
        transport(GOOSE);
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        Location beansLoc = engine.getItemLocation(BEANS);
        Location gooseLoc = engine.getItemLocation(GOOSE);
        Location wolfLoc = engine.getItemLocation(WOLF);
        Location farmerLoc = engine.getItemLocation(FARMER);

        // This action should do nothing since beans and wolf are not on the
        // same shore as the boat
        engine.loadBoat(BEANS);
        engine.loadBoat(WOLF);

        // check that the state has not changed
        Assert.assertEquals(beansLoc, engine.getItemLocation(BEANS));
        Assert.assertEquals(gooseLoc, engine.getItemLocation(GOOSE));
        Assert.assertEquals(wolfLoc, engine.getItemLocation(WOLF));
        Assert.assertEquals(farmerLoc, engine.getItemLocation(FARMER));
    }
}
