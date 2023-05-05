import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * All game logic is here
 */
public class Model {
    // minimum ja maximum. konstant läbivad suured tähed ja ei saa muuta
    /**
     * Minimum random number
     */
    private final int MINIMUM = 1;
    /**
     * Maximum random number
     */
    private final int MAXIMUM = 100;
    /**
     * Scoreboard filename
     */
    private final String filename = "scoreboard.txt";
    /**
     * User input from command line (no name)
     */
    private final Scanner scanner = new Scanner(System.in);
    /**
     * PC random number
     */
    private int pcNumber;
    /**
     * How many steps is done to know right number
     */
    private int steps;
    /**
     * Game is over (true) or not (false)
     */
    private boolean gameOver;
    /**
     * Scoreboard file content in List
     */
    private List<Content> scoreboard = new ArrayList<>();

    /**
     * Constructor is empty - done for JavaDoc
     */
    public Model() {} // Done for JavaDoc
    /**
     * Show game menu
     */
    public void showMenu() { // void ei tagasta midagi
        System.out.println("1. Play");
        System.out.println("2. Scoreboard");
        System.out.println("3. Exit");
        System.out.print("Your choice will be?: ");
        int choice = scanner.nextInt();
        // System.out.println(choice); // Test
        switch (choice) {
            case 1 -> { // ei pea olema -> aga siis pean olema breake iga case lõpus
                // System.out.println("Play"); // Test
                setupGame();
                letsPlay();
            }
            case 2 -> {
                // System.out.println("Scoreboard"); // Test
                showScoreboard();
                showMenu();
            }
            case 3 -> {
                System.out.println("Bye, Bye"); // Test
                System.exit(0);
            }
            default -> // any wrong int
                    showMenu();
        }
    }

    /**
     * Setting up gae
     */

    private void setupGame() { // seadistame mängu algseisu.
        pcNumber = ThreadLocalRandom.current().nextInt(MINIMUM, MAXIMUM +1); // +1 tähendab kaasa arvatud
        steps = 0;
        gameOver = false; // mäng ei ole läbi
    }

    /**
     * Ask number and check
     */
    private void ask() { // sisesta nr ja kontrollid
        System.out.printf("Input number between %d-%d: ", MINIMUM, MAXIMUM); // souf - format input. %d ehk digital (täisarv)
        int userNumber = scanner.nextInt(); // ootab kuni kasutaja lisa numbri
        steps += 1; // vahet pole mida sisestatakse sammude arv +1
        if (userNumber > pcNumber && userNumber != 10000) {
            System.out.println("Smaller");
        } else if (userNumber < pcNumber && userNumber != 10000) {
            System.out.println("Bigger");
        } else if (userNumber == pcNumber && userNumber != 10000) {
            System.out.printf("You guessed the number in %d steps.%n", steps); // %n reavahetus
            gameOver = true; // Mäng läbi
        } else { // 10000 backdoor
            System.out.printf("You found back door. Corrent number is %d%n", pcNumber);
        }
    }

    /**
     * Play game method
     */
    private void letsPlay() {
        while (!gameOver) {
            ask();
        }
        askName();
        showMenu();
    }

    /**
     * Ask player name
     */
    private void askName() {
        System.out.print("Enter your name: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name;
        try { // proovib käsureapealt nime
            name = br.readLine();
            if(name.strip().length() >1) { // nimi peab olema vähemalt 1 märk
                writeToFile(name); // kirjuta faili
            }else {
                System.out.println("Name is to short.");
                askName(); // küsib uuesti nime kui nimi liiga lühike
            }
        } catch (IOException e) { // kinnipüüdmislause
            throw new RuntimeException(e);
        }
    }

    /**
     * write name and steps to file
     * @param name player name
     */
    private void writeToFile(String name) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) { // proovib kirjutada faili
            String line = name + ";" + steps;
            bw.write(line); // write to line
            bw.newLine(); // tee uus rida
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show scoreboeard on console
     */
    private void showScoreboard() {
        readFromFile();
        System.out.println(); //Empty line
        for (Content c : scoreboard){
            c.showData();
        }
        System.out.println(); //Empty line
    }

    /**
     * Read file and write to List scoardboard
     */
    private void readFromFile() {
        try(BufferedReader br  = new BufferedReader(new FileReader(filename))) {
            scoreboard = new ArrayList<>(); // Tühejdab list. Mäng loeb list'ist mitte failist?!
            for (String line; (line = br.readLine()) != null;) {
                String name = line.split(";")[0]; // Name from file line
                int steps = Integer.parseInt(line.split(";")[1]);// peab olema int - teeme stringi int´iks
                scoreboard.add(new Content(name, steps));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
