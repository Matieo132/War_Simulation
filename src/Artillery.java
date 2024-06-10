import java.awt.*;

/**
 * Klasa {@code Artillery} reprezentuje jednostkę artylerii w symulacji bitwy.
 * Artyleria to jednostka dystansowa o wysokim obrażeniu, jednakże posiadająca mniejszą ilość punktów życia.
 */
public class Artillery extends ARangeUnit {

    /**
     * Konstruktor klasy Artillery, inicjuje parametry jednostki.
     *
     * @param name  nazwa jednostki
     * @param color kolor jednostki
     */
    public Artillery(String name, Color color) {
        super(name, color);
        this.stats.put(ArmyProperties.HP, 90.0);
        this.attack.setDamage(AttackTypes.NORMAL, 25);
    }
}
