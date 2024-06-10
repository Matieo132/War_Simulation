import java.awt.*;

/**
 * Klasa abstrakcyjna {@code ARangeUnit} reprezentuje abstrakcyjną jednostkę dystansową w symulacji bitwy.
 * Jednostki dystansowe posiadają zdolność atakowania z odległości.
 */
public abstract class ARangeUnit extends AArmy {

    /**
     * Konstruktor klasy ARangeUnit, inicjuje podstawowe parametry jednostki dystansowej.
     *
     * @param name  nazwa jednostki
     * @param color kolor jednostki
     */
    public ARangeUnit(String name, Color color) {
        super(name, color);
        this.attack = new Attack();
        this.stats.put(ArmyProperties.RANGE, 3.0);
    }
}
