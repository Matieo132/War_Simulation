import java.awt.*;

/**
 * Klasa abstrakcyjna {@code AMeleeUnit} reprezentuje abstrakcyjną jednostkę walczącą w zwarciu w symulacji bitwy.
 * Jednostki walczące w zwarciu to jednostki, które dokonują ataków w bezpośrednim kontakcie z wrogiem.
 */
public abstract class AMeleeUnit extends AArmy {

    /**
     * Konstruktor klasy AMeleeUnit, inicjuje podstawowe parametry jednostki walczącej w zwarciu.
     *
     * @param name  nazwa jednostki
     * @param color kolor jednostki
     */
    public AMeleeUnit(String name, Color color) {
        super(name, color);
        this.attack = new Attack();
    }
}
