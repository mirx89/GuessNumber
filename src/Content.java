/**
 *  Class for file contents
 */
public class Content {
    /**
     * Player name from file
     */
    private String name;
    /**
     * steps from file
     */
    private final int steps;

    /**
     * Constructor from Content class
     * @param name player name
     * @param steps steps
     */

    public Content(String name, int steps) { // alt + insert tuleb Constructor
        this.name = name;
        this.steps = steps;
    }

    /**
     * Show file contents nicely
     */
    public void showData() {
        if (name.length() > 15) { // if length of the name
            name = name.substring(0, 15); // shorten name to 15 character
        } else {
            name = String.format("%-15s", name);
        }
        String n = String.format("%-10s", name);
        String s = String.format("%3d", steps);
        System.out.println(n + s);
    }
}

