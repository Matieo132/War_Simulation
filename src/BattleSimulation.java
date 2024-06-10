import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Klasa {@code BattleSimulation} reprezentuje prostą symulację bitwy.
 * Używa Swing do interfejsu graficznego i zarządza stanem oraz interakcjami dwóch armii na planszy.
 */
public class BattleSimulation extends JPanel {
    private static final int TILE_SIZE = 50;
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 10;
    private final AArmy[][] board = new AArmy[BOARD_WIDTH][BOARD_HEIGHT];
    private final List<AArmy> armies = new ArrayList<>();
    private final List<AArmy> armies2 = new ArrayList<>();
    private final Random random = new Random();
    private int roundCount = 0;
    private boolean battleEnded = false;
    private boolean simulationRunning = true;
    private int simulationSpeed = 2;
    private final Timer timer;

    /**
     * Konstruktor BattleSimulation, inicjuje planszę gry i uruchamia timer.
     */
    public BattleSimulation() {
        initializeBoard();
        timer = new Timer(1000 / simulationSpeed, _ -> {
            if (simulationRunning && !battleEnded) {
                moveUnits();
                roundCount++;
                if (roundCount % 25 == 0) {
                    healAllies();
                }
                repaint();
                checkForEndCondition();
            }
        });
        timer.start();

        // Ustawienie przycisków kontrolnych
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton speedUpButton = new JButton("Speed Up");
        JButton slowDownButton = new JButton("Slow Down");

        startButton.addActionListener(_ -> simulationRunning = true);
        stopButton.addActionListener(_ -> simulationRunning = false);
        speedUpButton.addActionListener(_ -> {
            simulationSpeed *= 2;
            adjustTimerDelay();
        });
        slowDownButton.addActionListener(_ -> {
            simulationSpeed /= 2;
            adjustTimerDelay();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(speedUpButton);
        buttonPanel.add(slowDownButton);

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Dostosowuje opóźnienie timera symulacji na podstawie bieżącej prędkości symulacji.
     */
    private void adjustTimerDelay() {
        int delay = 1000 / simulationSpeed;
        timer.setDelay(delay);
    }

    /**
     * Inicjuje planszę gry z jednostkami dla obu armii.
     */
    private void initializeBoard() {
        Infantry infantry1 = new Infantry("Infantry1", Color.RED);
        Cavalry cavalry1 = new Cavalry("Cavalry1", Color.RED);
        Doctor doctor1 = new Doctor("Doctor1", Color.RED);
        General general1 = new General("General1", Color.RED);
        Artillery artillery1 = new Artillery("Artillery1", Color.RED);

        board[0][0] = infantry1;
        board[2][0] = cavalry1;
        board[4][0] = doctor1;
        board[6][0] = general1;
        board[8][0] = artillery1;

        armies.add(infantry1);
        armies.add(cavalry1);
        armies.add(doctor1);
        armies.add(general1);
        armies.add(artillery1);

        Infantry infantry2 = new Infantry("Infantry2", Color.BLUE);
        Cavalry cavalry2 = new Cavalry("Cavalry2", Color.BLUE);
        Doctor doctor2 = new Doctor("Doctor2", Color.BLUE);
        General general2 = new General("General2", Color.BLUE);
        Artillery artillery2 = new Artillery("Artillery2", Color.BLUE);

        board[0][9] = infantry2;
        board[2][9] = cavalry2;
        board[4][9] = doctor2;
        board[6][9] = general2;
        board[8][9] = artillery2;

        armies2.add(infantry2);
        armies2.add(cavalry2);
        armies2.add(doctor2);
        armies2.add(general2);
        armies2.add(artillery2);
    }

    /**
     * Leczy wszystkich sojuszników dla każdej jednostki typu Doctor w armiach.
     */
    private void healAllies() {
        for (AArmy army : armies) {
            if (army instanceof Doctor) {
                List<AArmy> alliesToHeal = armies.stream()
                        .filter(unit -> unit != army && unit.getProperty(ArmyProperties.HP) > 0)
                        .toList();
                ((Doctor) army).healAllies(alliesToHeal);
            }
        }
    }

    /**
     * Przemieszcza wszystkie jednostki dla obu armii.
     */
    private void moveUnits() {
        moveArmy(armies, armies2);
        moveArmy(armies2, armies);
    }

    /**
     * Przemieszcza jednostki określonej armii.
     *
     * @param movingArmy armia, której jednostki mają zostać przemieszczone
     * @param opposingArmy przeciwna armia
     */
    private void moveArmy(List<AArmy> movingArmy, List<AArmy> opposingArmy) {
        for (AArmy army : movingArmy) {
            int currentX = -1, currentY = -1;

            // Znajdź bieżącą pozycję jednostki
            for (int x = 0; x < BOARD_WIDTH; x++) {
                for (int y = 0; y < BOARD_HEIGHT; y++) {
                    if (board[x][y] == army) {
                        currentX = x;
                        currentY = y;
                        break;
                    }
                }
            }

            if (currentX != -1) {
                int newX = currentX + random.nextInt(3) - 1;
                int newY = currentY + random.nextInt(3) - 1;

                if (newX >= 0 && newX < BOARD_WIDTH && newY >= 0 && newY < BOARD_HEIGHT) {
                    if (board[newX][newY] == null) {
                        board[newX][newY] = army;
                        board[currentX][currentY] = null;
                    } else if (opposingArmy.contains(board[newX][newY])) {
                        AArmy enemy = board[newX][newY];
                        if (!(army instanceof Doctor)) {
                            // Regularny atak
                            army.getAttack().setDamage(AttackTypes.NORMAL, army.getAttack().getDamage(AttackTypes.NORMAL));
                            enemy.acceptAttack(army.getAttack());
                        } else {
                            // Atak doktora (opcjonalnie): zmniejsza HP wroga bez wpływu na własne obrażenia
                            enemy.setProperty(ArmyProperties.HP, enemy.getProperty(ArmyProperties.HP) - 1);
                        }
                        if (enemy.getProperty(ArmyProperties.HP) <= 0) {
                            board[newX][newY] = null;
                            opposingArmy.remove(enemy);
                        }
                    } else if (movingArmy.contains(board[newX][newY])) {
                        if (army instanceof Doctor) {
                            Doctor doctor = (Doctor) army;
                            AArmy ally = board[newX][newY];

                            if (ally != army) {
                                doctor.heal(ally);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Sprawdza, czy bitwa się zakończyła na podstawie statusu armii.
     * Wyświetla komunikat i zapisuje wynik do pliku CSV, jeśli bitwa się zakończyła.
     */
    private void checkForEndCondition() {
        if (armies.isEmpty()) {
            battleEnded = true;
            JOptionPane.showMessageDialog(this, "Army 2 (Blue) wins!", "Battle Over", JOptionPane.INFORMATION_MESSAGE);
            writeScoreToCSV("Army 2 (Blue) wins");
            System.exit(0);
        } else if (armies2.isEmpty()) {
            battleEnded = true;
            JOptionPane.showMessageDialog(this, "Army 1 (Red) wins!", "Battle Over", JOptionPane.INFORMATION_MESSAGE);
            writeScoreToCSV("Army 1 (Red) wins");
            System.exit(0);
        }
    }

    /**
     * Zapisuje wynik bitwy do pliku CSV.
     *
     * @param winner zwycięzca bitwy
     */
    private void writeScoreToCSV(String winner) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("score.csv", true))) {
            writer.write(roundCount + "," + winner + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    /**
     * Rysuje planszę gry i jednostki na panelu.
     *
     * @param g obiekt Graphics używany do rysowania
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                int tileX = x * TILE_SIZE;
                int tileY = y * TILE_SIZE;

                g2d.setColor(Color.BLACK);
                g2d.drawRect(tileX, tileY, TILE_SIZE, TILE_SIZE);

                if (board[x][y] != null) {
                    AArmy army = board[x][y];

                    g2d.setColor(army.getColor());
                    g2d.fillRect(tileX, tileY, TILE_SIZE, TILE_SIZE);

                    g2d.setColor(Color.BLACK);
                    Font font = new Font("Arial", Font.BOLD, 12);
                    g2d.setFont(font);
                    String initialLetter = army.name.substring(0, 1);
                    int letterX = tileX + TILE_SIZE / 2 - g2d.getFontMetrics().stringWidth(initialLetter) / 2;
                    int letterY = tileY + TILE_SIZE / 2 + 5;
                    g2d.drawString(initialLetter, letterX, letterY);

                    String hpString = "HP: " + (int) army.getProperty(ArmyProperties.HP);
                    int hpStringWidth = g2d.getFontMetrics().stringWidth(hpString);
                    int hpX = tileX + TILE_SIZE / 2 - hpStringWidth / 2;
                    int hpY = tileY + TILE_SIZE - 5;
                    g2d.drawString(hpString, hpX, hpY);
                }
            }
        }
        g2d.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 12);
        g2d.setFont(font);
        String roundString = "Round: " + roundCount;
        g2d.drawString(roundString, 10, 20);
    }

    /**
     * Metoda główna służąca do uruchomienia symulacji bitwy.
     *
     * @param args argumenty wiersza poleceń
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Battle Simulation");
        BattleSimulation battleSimulation = new BattleSimulation();
        frame.add(battleSimulation);
        frame.setSize(BOARD_WIDTH * TILE_SIZE + 15, BOARD_HEIGHT * TILE_SIZE + 120);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
