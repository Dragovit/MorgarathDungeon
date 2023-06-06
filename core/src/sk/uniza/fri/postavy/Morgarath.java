package sk.uniza.fri.postavy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;

/**
 * Trieda Wargal slúži na pohyb nepriateľa Morgarath.
 * Od predka Nepriatel dedí všetky metódy.
 *
 * @author Pavol Tomčík
 * @version 20.5.2022
 */
public class Morgarath extends Nepriatel {
    private final int sek;
    private float poziciaX;
    private float poziciaY;
    private int posunX;
    private int posunY;
    private int cas;

    /**
     * Konštruktor pre inicializovanie atribútov a vybranie náhodného času pre pohyb Morgaratha.
     *
     * @param x súradnica X.
     * @param y súradnica Y.
     */
    public Morgarath(float x, float y) {
        super(12, "morgarath", x, y, 400);
        this.poziciaX = x;
        this.poziciaY = y;
        this.cas = 0;
        Random randomCas = new Random();
        this.sek = randomCas.nextInt(4) * 10;
    }

    /**
     * Metóda posun() prepisuje metódu presun z predka Nepriatel.
     * Tu je riešený pohyb Morgaratha, ktorý je náhodne vygenerovaný.
     */
    @Override
    public void posun() {
        this.dispose();
        this.setPredoslaPoziciaX(this.poziciaX);
        this.setPredoslaPoziciaY(this.poziciaY);

        Random randomX = new Random();
        Random randomY = new Random();

        this.cas++;

        if (this.cas == this.sek) {

            this.posunX = randomX.nextInt(50);
            this.posunY = randomY.nextInt(50);
            this.cas = 0;
        }

        if (this.posunX <= 25) {
            this.poziciaX += Gdx.graphics.getDeltaTime() * 500;
            this.setSmer(true);
        }
        if (this.posunX > 25) {
            this.poziciaX -= Gdx.graphics.getDeltaTime() * 500;
            this.setSmer(false);
        }
        if (this.posunY <= 25) {
            this.poziciaY -= Gdx.graphics.getDeltaTime() * 500;

        }
        if (this.posunY > 25) {
            this.poziciaY += Gdx.graphics.getDeltaTime() * 500;
        }

        this.setHitbox(this.getHitbox());

    }

    /**
     * Chránená metóda setHibox() dedí všetky vlastnosti predka Postava.
     *
     * @param hitbox nastav hitbox
     */
    @Override
    protected void setHitbox(Rectangle hitbox) {
        hitbox.setPosition((int)this.poziciaX, (int)this.poziciaY);
    }

    /**
     * Metóda kolizia() nastaví predšlú pozíciu na súčasnú.
     */
    @Override
    public void kolizia() {
        this.poziciaX = this.getPredoslaPoziciaX();
        this.poziciaY = this.getPredoslaPoziciaY();
    }

    @Override
    public Rectangle getHitbox() {
        return super.getHitbox();
    }

    /**
     * Getter getPozíciaX() vráti hodnotu atribútu this.poziciaX.
     *
     * @return float this.poziciaX
     */
    @Override
    public float getPoziciaX() {
        return this.poziciaX;
    }

    /**
     * Getter getPozíciaY() vráti hodnotu atribútu this.poziciaY.
     *
     * @return float this.poziciaY
     */
    @Override
    public float getPoziciaY() {
        return this.poziciaY;
    }

    /**
     * Metóda uberZivot() uberie počet zivotov zadaných v parametri.
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
