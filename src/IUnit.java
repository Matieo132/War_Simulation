/**
 * Interfejs {@code IUnit} definiuje podstawowe operacje, które mogą być wykonywane na jednostkach w symulacji bitwy.
 */
public interface IUnit {

    /**
     * Metoda zwracająca wartość określonej właściwości jednostki.
     *
     * @param property właściwość jednostki do pobrania
     * @return wartość właściwości
     */
    double getProperty(ArmyProperties property);

    /**
     * Metoda zwracająca interfejs ataku jednostki.
     *
     * @return interfejs ataku
     */
    IAttack getAttack();

    /**
     * Metoda obsługująca atak na jednostkę.
     *
     * @param attack atak do obsłużenia
     */
    void acceptAttack(IAttack attack);
}
