package river;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Graphical interface for the River application
 *
 * @author aryapriyank
 */
public class RiverGUI extends JPanel implements MouseListener {

    // ==========================================================
    // Private Fields
    // ==========================================================

    private GameEngine engine; // Model
    private final EnumMap<Item, Rectangle> itemRectangleMap;
    private final List<Item> itemsOnBoat; // Stores Items present on boat
    private boolean restart = false;
    private Graphics g;

    // Rectangle Base Coordinates and Offsets
    private final int startBaseX = 20;
    private final int finishBaseX = 670;
    private final int baseY = 275;

    private int[] offsetX;
    private int[] offsetY;

    private final int startBoatX = 140;
    private final int finishBoatX = 550;
    private Rectangle boatRectangle;

    private final Rectangle farmerGameButton;
    private final Rectangle monsterGameButton;


    // ==========================================================
    // Constructor
    // ==========================================================

    public RiverGUI() {
        addMouseListener(this);

        itemsOnBoat = new LinkedList<>();
        farmerGameButton = new Rectangle(250, 120, 100, 30);
        monsterGameButton = new Rectangle(450, 120, 100, 30);
        itemRectangleMap = new EnumMap<>(Item.class);
        resetFarmer();
        resetRectangles();
    }


    private void resetFarmer() {
        engine = new FarmerGameEngine();
    }


    private void resetMonster() {
        engine = new MonsterGameEngine();
    }


    private void resetRectangles() {
        offsetX = new int[engine.numberOfItems()];
        offsetY = new int[engine.numberOfItems()];

        // Set Boat Rectangle
        boatRectangle = new Rectangle(startBoatX, baseY, 110, 50);

        // Mapping the X & Y Offsets
        for (Item item : Item.values()) {
            if (!(item.ordinal() < engine.numberOfItems())) {
                break;
            }

            if (item.ordinal() % 2 == 0) {
                offsetX[item.ordinal()] = 0;
                offsetY[item.ordinal()] = item.ordinal() * (-30);
            } else {
                offsetX[item.ordinal()] = 60;
                offsetY[item.ordinal()] = (item.ordinal() - 1) * (-30);
            }
        }

        // Mapping the rectangles to respective items
        for (Item item : Item.values()) {
            if (!(item.ordinal() < engine.numberOfItems())) {
                break;
            }

            itemRectangleMap.put(item, new Rectangle(startBaseX + offsetX[item.ordinal()],
                    baseY + offsetY[item.ordinal()], 50, 50));
        }
    }


    // ==========================================================
    // Paint Methods (View)
    // ==========================================================

    @Override
    public void paintComponent(Graphics g) {

        this.g = g;

        // Paint starting window
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Refresh Item and Boat Rectangles
        refreshItemRectangles();
        refreshBoatRectangle();

        // If game is Lost or Won
        String message = "";
        if (engine.gameIsLost()) {
            message = "You Lost!";
            restart = true;
        }
        if (engine.gameIsWon()) {
            message = "You Won!";
            restart = true;
        }
        if (restart) {
            paintMessage(message);
            paintRectangle(Color.PINK, "Farmer", farmerGameButton);
            paintRectangle(Color.PINK, "Monster", monsterGameButton);
        }
    }


    // Paint updated item rectangles
    private void refreshItemRectangles() {
        for (Item item : Item.values()) {
            if (!(item.ordinal() < engine.numberOfItems())) {
                break;
            }
            paintRectangle(engine.getItemColor(item), engine.getItemLabel(item),
                    itemRectangleMap.get(item));
        }
    }


    // Paint updated boat rectangle
    private void refreshBoatRectangle() {
        paintRectangle(Color.ORANGE, "", boatRectangle);
    }


    // Paint Rectangles
    private void paintRectangle(
            Color color, String label, Rectangle rectangle) {

        // paint border
        int borderWidth = 1;
        g.setColor(Color.BLACK);
        g.fillRect(rectangle.x - borderWidth, rectangle.y - borderWidth,
                rectangle.width + (2 * borderWidth),
                rectangle.height + (2 * borderWidth));

        // paint rectangle
        g.setColor(color);
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

        // paint label inside rectangle
        g.setColor(Color.BLACK);
        int fontsize = (rectangle.height >= 40) ? 26 : 18;
        g.setFont(new Font("Verdana", Font.BOLD, fontsize));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = rectangle.x + (rectangle.width / 2) - (fm.stringWidth(label) / 2);
        int strYCoord = rectangle.y + (rectangle.height / 2) + (fontsize / 2) - 4;
        g.drawString(label, strXCoord, strYCoord);
    }


    // Paint Win or Lost Message
    private void paintMessage(String message) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 36));
        FontMetrics fm = g.getFontMetrics();
        int strXCoord = 400 - fm.stringWidth(message) / 2;
        int strYCoord = 100;
        g.drawString(message, strXCoord, strYCoord);
    }


    // ==========================================================
    // Startup Methods
    // ==========================================================

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {

        // Create and set up the window
        JFrame frame = new JFrame("RiverCrossing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane
        RiverGUI newContentPane = new RiverGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        // Display the window
        frame.setSize(800, 600);
        frame.setVisible(true);
    }


    public static void main(String[] args) {

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(RiverGUI::createAndShowGUI);
    }


    // ==========================================================
    // MouseListener Methods (Controller)
    // ==========================================================

    @Override
    public void mouseClicked(MouseEvent e) {

        if (restart) {
            if (this.farmerGameButton.contains(e.getPoint())) {
                resetFarmer();
                resetRectangles();
                itemsOnBoat.clear();
                engine.resetGame();
                restart = false;
                repaint();
            } else if (this.monsterGameButton.contains(e.getPoint())) {
                resetMonster();
                resetRectangles();
                itemsOnBoat.clear();
                engine.resetGame();
                restart = false;
                repaint();
            }
            return;
        }

        // Updates Boat Rectangle if it contains MouseClick
        if (boatRectangle.contains(e.getPoint())) {
            engine.rowBoat();
            // Update passenger location and rectangles
            for (Item item : itemsOnBoat) {

                if (engine.getBoatLocation() == Location.START) {
                    boatRectangle = new Rectangle(startBoatX, baseY, 110, 50);
                    itemRectangleMap.put(item, new Rectangle(
                            startBoatX + (itemsOnBoat.indexOf(item) * 60),
                            baseY - 60, 50, 50));
                } else if (engine.getBoatLocation() == Location.FINISH) {
                    boatRectangle = new Rectangle(finishBoatX, baseY, 110, 50);
                    itemRectangleMap.put(item, new Rectangle(
                            finishBoatX + (itemsOnBoat.indexOf(item) * 60),
                            baseY - 60, 50, 50));
                }
            }
        }

        // Updates Items' Rectangles if they contain MouseClick
        for (Item item : Item.values()) {
            if (!(item.ordinal() < engine.numberOfItems())) {
                break;
            }
            if (itemRectangleMap.get(item).contains(e.getPoint())) {

                // If Item and Boat both are on Start shore
                if ((engine.getItemLocation(item) == Location.START)
                        && (engine.getBoatLocation() == Location.START)
                        && (itemsOnBoat.size() < 2)) {
                    itemsOnBoat.add(item);
                    engine.loadBoat(item);
                    itemRectangleMap.put(item, new Rectangle(startBoatX +
                            (itemsOnBoat.indexOf(item) * 60), baseY - 60, 50, 50));
                }

                // If Item and Boat both are on Finish shore
                else if ((engine.getItemLocation(item) == Location.FINISH)
                        && (engine.getBoatLocation() == Location.FINISH)
                        && (itemsOnBoat.size() < 2)) {
                    itemsOnBoat.add(item);
                    engine.loadBoat(item);
                    itemRectangleMap.put(item, new Rectangle(finishBoatX
                            + (itemsOnBoat.indexOf(item) * 60), baseY - 60, 50, 50));
                }

                // If Item is on Boat and Boat is on Start shore
                else if ((engine.getItemLocation(item) == Location.BOAT)
                        && (engine.getBoatLocation() == Location.START)) {

                    // Rearrange Item on Boat
                    if ((itemsOnBoat.indexOf(item) == 0) && itemsOnBoat.size() == 2) {
                        itemRectangleMap.put(itemsOnBoat.get(1),
                                new Rectangle(startBoatX, baseY - 60, 50, 50));
                    }

                    // Remove the selected item
                    itemsOnBoat.remove(item);
                    engine.unloadBoat(item);
                    itemRectangleMap.put(item, new Rectangle(startBaseX + offsetX[item.ordinal()],
                            baseY + offsetY[item.ordinal()], 50, 50));
                    break;
                }

                // If Item is on Boat and Boat is on Finish shore
                else if ((engine.getItemLocation(item) == Location.BOAT)
                        && (engine.getBoatLocation() == Location.FINISH)) {

                    // Rearrange Item on Boat
                    if ((itemsOnBoat.indexOf(item) == 0) && itemsOnBoat.size() == 2) {
                        itemRectangleMap.put(itemsOnBoat.get(1),
                                new Rectangle(finishBoatX, baseY - 60, 50, 50));
                    }

                    // Remove the selected item
                    itemsOnBoat.remove(item);
                    engine.unloadBoat(item);
                    itemRectangleMap.put(item, new Rectangle(finishBaseX + offsetX[item.ordinal()],
                            baseY + offsetY[item.ordinal()], 50, 50));
                    break;
                }
            }
        }

        repaint();
    }

    // ----------------------------------------------------------
    // None of these methods will be used
    // ----------------------------------------------------------


    @Override
    public void mousePressed(MouseEvent e) {
        //
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }


    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
}
