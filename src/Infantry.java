import java.awt.*;

/**
 * Klasa {@code Infantry} reprezentuje jednostkę piechoty w symulacji bitwy.
 * Piechota to jednostka walcząca w zwarciu, posiadająca umiejętność atakowania z bliskiej odległości.
 */
public class Infantry extends AMeleeUnit {

    /**
     * Konstruktor klasy Infantry, inicjuje parametry jednostki.
     *
     * @param name  nazwa jednostki
     * @param color kolor jednostki
     */
    public Infantry(String name, Color color) {
        super(name, color);
        this.stats.put(ArmyProperties.HP, 100.0);
        this.attack.setDamage(AttackTypes.NORMAL, 15);
    }
}
