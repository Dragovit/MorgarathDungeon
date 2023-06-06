package sk.uniza.fri.postavy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Abstraktná trieda Nepriatel slúži ako predok pre triedy Wargal a Morgarath.
 * Od predka Postava dedí všetky metódy.
 *
 * @author Pavol Tomčík
 * @version 20.5.2022
 */
public abstract class Nepriatel extends Postava {

    /**
     * Parametre inicializujú atribúty v predkovi Postava
     *
     * @param pocetObrazkov pocet obrázkov potrebných na animáciu.
     * @param nazov názov danej postavy.
     * @param x súradnica X.
     * @param y súradnica Y.
     * @param pocetZivotov počet životov postavy.
     */
    public Nepriatel(int pocetObrazkov, String nazov, float x, float y, int pocetZivotov) {
        super(pocetObrazkov, nazov, x, y, pocetZivotov);
    }

    /**
     * Metóda animacia() dedí všetky vlastnosti predka Postava.
     *
     * @return Texture super.animacia()
     */
    @Override
    public Texture animacia() {
        return super.animacia();
    }

    /**
     * Getter getPozíciaX() dedí všetky vlastnosti predka Postava.
     *
     * @return float super.getPozíciaX()
     */
    @Override
    public float getPoziciaX() {
        return super.getPoziciaX();
    }

    /**
     * Getter getPozíciaY() dedí všetky vlastnosti predka Postava.
     *
     * @return float super.getPozíciaY()
     */
    @Override
    public float getPoziciaY() {
        return super.getPoziciaY();
    }

    /**
     * Metóda getHitbox() dedí všetky vlastnosti predka Postava.
     *
     * @return Rectangle super.getHitbox()
     */
    @Override
    public Rectangle getHitbox() {
        return super.getHitbox();
    }

    /**
     * Chránená metóda setHibox() dedí všetky vlastnosti predka Postava.
     *
     * @param hitbox nastav hitbox
     */
    @Override
    protected void setHitbox(Rectangle hitbox) {

    }

    /**
     * Metóda posun() je prazdna ale pri jej zavolaní sa prepíše na metódu pohni v triede Wargal alebo v Morgarath.
     */
    public void posun() {
    }

    /**
     * Metóda uberZivot() dedí všetky vlastnosti predka Postava.
     *
     * @param poskodenie o kolko sa má zmenšiť život
     */
    @Override
    public void uberZivot(int poskodenie) {
        super.uberZivot(poskodenie);
    }

    /**
     * Metóda dispose() odstráni onbjekty z pamäte
     */
    @Override
    public void dispose() {
        super.dispose();
    }
}
