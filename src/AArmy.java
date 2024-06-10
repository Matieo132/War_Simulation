import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa abstrakcyjna {@code AArmy} reprezentuje abstrakcyjną jednostkę armii w symulacji bitwy.
 * Jest implementacją interfejsu {@link IUnit}.
 */
public abstract class AArmy implements IUnit {

    /**
     * Mapa przechowująca statystyki jednostki.
     */
    protected Map<ArmyProperties, Double> stats = new HashMap<>();

    /**
     * Nazwa jednostki.
     */
    protected String name;

    /**
     * Kolor jednostki.
     */
    protected Color color;

    /**
     * Interfejs ataku jednostki.
     */
    protected IAttack attack;

    /**
     * Czy jednostka wykonała już ruch.
     */
    private final boolean hasMoved;

    /**
     * Współrzędna x jednostki.
     */
    private final int x;

    /**
     * Współrzędna y jednostki.
     */
    private final int y;

    /**
     * Konstruktor klasy AArmy, inicjuje podstawowe parametry jednostki armii.
     *
     * @param name  nazwa jednostki
     * @param color kolor jednostki
     */
    public AArmy(String name, Color color) {
        this.name = name;
        this.color = color;
        this.stats.put(ArmyProperties.HP, 100.0); // Domyślne HP
        this.hasMoved = false;
        this.x = 0;
        this.y = 0;
    }

    /**
     * Metoda zwracająca kolor jednostki.
     *
     * @return kolor jednostki
     */
    public Color getColor() {
        return color;
    }

    /**
     * Metoda zwracająca wartość określonej właściwości jednostki.
     *
     * @param property właściwość jednostki do pobrania
     * @return wartość właściwości
     */
    @Override
    public double getProperty(ArmyProperties property) {
        return stats.getOrDefault(property, 40.0);
    }

    /**
     * Metoda ustawiająca wartość określonej właściwości jednostki.
     *
     * @param property właściwość jednostki do ustawienia
     * @param value    wartość właściwości
     */
    public void setProperty(ArmyProperties property, double value) {
        stats.put(property, value);
    }

    /**
     * Metoda zwracająca interfejs ataku jednostki.
     *
     * @return interfejs ataku
     */
    @Override
    public IAttack getAttack() {
        return attack;
    }

    /**
     * Metoda obsługująca atak na jednostkę.
     *
     * @param attack atak do obsłużenia
     */
    @Override
    public void acceptAttack(IAttack attack) {
        double damage = attack.getDamage(AttackTypes.NORMAL);
        if (damage > 0) {
            double currentHP = this.stats.get(ArmyProperties.HP);
            this.stats.put(ArmyProperties.HP, currentHP - damage);
            System.out.println(this.name + " received " + damage + " damage. Remaining HP: " + (currentHP - damage));
        }
    }
}
