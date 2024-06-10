/**
 * Interfejs {@code IAttack} definiuje operacje związane z atakiem jednostki w symulacji bitwy.
 */
public interface IAttack {

    /**
     * Metoda zwracająca obrażenia spowodowane danym typem ataku.
     *
     * @param type typ ataku
     * @return wartość obrażeń
     */
    double getDamage(AttackTypes type);

    /**
     * Metoda ustawiająca obrażenia dla danego typu ataku.
     *
     * @param type  typ ataku
     * @param value wartość obrażeń
     */
    void setDamage(AttackTypes type, double value);
}
