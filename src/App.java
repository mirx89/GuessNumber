/**
 * Main running file
 */
public class App {
    /**
     * Constructor
     */
    public App() {
        // new Model().showMenu(); // ühe reade
        Model model = new Model();
        model.showMenu();
    }

    /**
     * App main method
     * @param args arguments from command line
     */
    public static void main(String[] args) {
        new App();
    }
}
