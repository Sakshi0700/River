package river;

import java.awt.Color;

public class GameObject {

    // Private Fields
    private final String label;
    private Location location;
    private final Color color;

    private boolean isDriver;

    // Constructor
    public GameObject(String label, Location location, Color color,boolean isDriver) {
        this.label = label;
        this.location = location;
        this.color = color;
        this.isDriver=false;
    }


    public String getLabel() {
        return label;
    }


    public Color getColor() {
        return color;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
