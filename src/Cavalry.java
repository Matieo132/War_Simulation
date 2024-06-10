import java.awt.*;

/**
 * Klasa {@code Cavalry} reprezentuje jednostkę kawalerii w symulacji bitwy.
 * Kawaleria jest jednostką bliskiego zasięgu o wysokim obrażeniu.
 */
public class Cavalry extends AMeleeUnit {

    /**
     * Konstruktor klasy Cavalry, inicjuje parametry jednostki.
     *
     * @param name  nazwa jednostki
     * @param color kolor jednostki
     */
    public Cavalry(String name, Color color) {
        super(name, color);
        this.stats.put(ArmyProperties.HP, 90.0);
        this.attack.setDamage(AttackTypes.NORMAL, 20);
    }
}
