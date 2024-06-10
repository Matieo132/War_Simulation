import java.util.HashMap;
import java.util.Map;

/**
 * Klasa {@code Attack} implementuje interfejs {@link IAttack} i reprezentuje atak jednostki w symulacji bitwy.
 */
public class Attack implements IAttack {

    /**
     * Mapa przechowująca obrażenia dla poszczególnych typów ataku.
     */
    private final Map<AttackTypes, Double> attacks = new HashMap<>();

    /**
     * Metoda zwracająca obrażenia spowodowane danym typem ataku.
     *
     * @param type typ ataku
     * @return wartość obrażeń
     */
    @Override
    public double getDamage(AttackTypes type) {
        return attacks.getOrDefault(type, 0.0);
    }

    /**
     * Metoda ustawiająca obrażenia dla danego typu ataku.
     *
     * @param type  typ ataku
     * @param value wartość obrażeń
     */
    @Override
    public void setDamage(AttackTypes type, double value) {
        attacks.put(type, value);
    }
}
