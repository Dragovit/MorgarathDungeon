package sk.uniza.fri;

import com.badlogic.gdx.graphics.Texture;

/**
 * Trieda HPbar je určná na inicializovanie atribútu a na jeho getter, s ktorým sa pracuje v triede Hrac.
 *
 * @author Pavol Tomčík
 * @version 20.5.2022
 */
public class HPbar {
    private final Texture srdce;

    /**
     * Konštruktor inicializuje atribút.
     */
    public HPbar() {
        this.srdce = new Texture("heart/heart.png");
    }

    /**
     * Getter pre atribút
     *
     * @return Texture this.srdce
     */
    public Texture getSrdce() {
        return this.srdce;
    }

    /**
     * Metóda dispose() odstráni onbjekty z pamäte
     */
    public void dispose() {
        this.srdce.dispose();
    }
}
