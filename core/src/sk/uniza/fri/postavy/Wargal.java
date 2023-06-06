package sk.uniza.fri.postavy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;

/**
 * Trieda Wargal slúži na pohyb nepriateľa wargal.
 * Od predka Nepriatel dedí všetky metódy.
 *
 * @author Pavol Tomčík
 * @version 20.5.2022
 */
public class Wargal extends Nepriatel {
    private final int sek;
    private float poziciaX;
    private float poziciaY;
    private int posunX;
    private int posunY;
    private int cas;

    /**
     * Konštruktor pre inicializovanie atribútov a vybranie náhodného času pre pohyb wargala.
     *
     * @param x súradnica X.
     * @param y súradnica Y.
     */
    public Wargal(float x, float y) {
        super(12, "wargal", x, y, 50);
        this.poziciaX = x;
        this.poziciaY = y;
        this.cas = 0;
        Random randomCas = new Random();
        this.sek = randomCas.nextInt(6) * 10;
    }

    /**
     * Metóda posun() prepisuje metódu presun z predka Nepriatel.
     * Tu je riešený pohyb wargala, ktorý je náhodne vygenerovaný.
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

        if (this.posunX < 25) {
            this.poziciaX += Gdx.graphics.getDeltaTime() * 250;
            this.setSmer(true);
        }
        if (this.posunX >= 25) {
            this.poziciaX -= Gdx.graphics.getDeltaTime() * 250;
            this.setSmer(false);
        }
        if (this.posunY < 25) {
            this.poziciaY -= Gdx.graphics.getDeltaTime() * 250;

        }
        if (this.posunY >= 25) {
            this.poziciaY += Gdx.graphics.getDeltaTime() * 250;

        }

        this.setHitbox(this.getHitbox());

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
     * Metóda dispose() odstráni onbjekty z pamäte
     */
    @Override
    public void dispose() {
        super.dispose();
    }
}
