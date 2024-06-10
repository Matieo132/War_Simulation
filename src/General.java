import java.awt.*;

/**
 * Klasa {@code General} reprezentuje jednostkę generała w symulacji bitwy.
 * Generał jest jednostką walczącą w zwarciu, posiadającą wyższe punkty życia i silniejszy atak.
 */
public class General extends AMeleeUnit {

    /**
     * Konstruktor klasy General, inicjuje parametry jednostki.
     *
     * @param name  nazwa jednostki
     * @param color kolor jednostki
     */
    public General(String name, Color color) {
        super(name, color);
        this.stats.put(ArmyProperties.HP, 150.0);
        this.attack.setDamage(AttackTypes.NORMAL, 30);
    }
}
