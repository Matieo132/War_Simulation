import java.awt.*;
import java.util.List;

/**
 * Klasa {@code Doctor} reprezentuje jednostkę medyczną w symulacji bitwy.
 * Doktor potrafi leczyć swoich sojuszników, przywracając im punkty życia.
 */
public class Doctor extends AMeleeUnit {

    /**
     * Konstruktor klasy Doctor, inicjuje parametry jednostki.
     *
     * @param name  nazwa jednostki
     * @param color kolor jednostki
     */
    public Doctor(String name, Color color) {
        super(name, color);
        this.stats.put(ArmyProperties.HP, 120.0);
        this.attack.setDamage(AttackTypes.HEAL, 5);
    }

    /**
     * Metoda heal służy do leczenia pojedynczego sojusznika.
     *
     * @param ally sojusznik, którego należy uleczyć
     */
    public void heal(AArmy ally) {
        double currentHP = ally.getProperty(ArmyProperties.HP);
        double healAmount = 5.0;
        ally.setProperty(ArmyProperties.HP, currentHP + healAmount);
    }

    /**
     * Metoda healAllies służy do leczenia wszystkich sojuszników w danej liście.
     *
     * @param allies lista sojuszników do uleczenia
     */
    public void healAllies(List<AArmy> allies) {
        for (AArmy ally : allies) {
            if (ally != this) {
                heal(ally);
            }
        }
    }
}
