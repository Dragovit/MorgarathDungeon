package sk.uniza.fri.postavy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Abstraktná trieda Popstava slúži ako predok pre triedu Hráč a abstraktnú triedu Nepriatel.
 * Tu sa určuje aká ostáva sa vykreslí, animácia postavy, hitboxy, kolízie, životy a pozícia.
 *
 * @author Pavol Tomčík
 * @version 20.5.2022
 */
public abstract class Postava {
    private float poziciaX;
    private float poziciaY;
    private int animacia;
    private final int pocetObrazkov;
    private Texture postava;
    private final String nazov;
    private boolean smer;
    private float predoslaPoziciaX;
    private float predoslaPoziciaY;
    private int pocetZivotov;
    private final Rectangle hitbox;

    /**
     * Konštruktor slúži na inicializáciu atribútov.
     *
     * @param pocetObrazkov pocet obrázkov potrebných na animáciu.
     * @param nazov názov danej postavy.
     * @param x súradnica X.
     * @param y súradnica Y.
     * @param pocetZivotov počet životov postavy.
     */
    public Postava(int pocetObrazkov, String nazov, float x, float y, int pocetZivotov) {
        this.pocetObrazkov = pocetObrazkov;
        this.nazov = nazov;
        this.smer = true;
        this.postava = new Texture("postavy/" + this.nazov + "/dolava/1.png");
        this.poziciaX = x;
        this.poziciaY = y;
        this.pocetZivotov = pocetZivotov;
        this.hitbox = new Rectangle((int)this.poziciaX, (int)this.poziciaY, this.postava.getWidth(), this.postava.getHeight());
    }

    /**
     * Metóda animacia() zobrazuje animaciu postavy tým, že prechádza obrazky od 1 po počet obrázkov.
     *
     * @return Texture this.postava
     */
    public Texture animacia() {
        this.animacia++;
        int cislo = (this.animacia % this.pocetObrazkov) + 1;
        if (this.smer) {
            this.postava = new Texture("postavy/" + this.nazov + "/doprava/" + cislo + ".png");
        } else {
            this.postava = new Texture("postavy/" + this.nazov + "/dolava/" + cislo + ".png");
        }
        return this.postava;
    }

    /**
     * Metóda kolizia() nastaví predšlú pozíciu na súčasnú.
     */
    public void kolizia() {
        this.poziciaX = this.predoslaPoziciaX;
        this.poziciaY = this.predoslaPoziciaY;
    }

    /**
     * Metóda uberZivot() uberie počet zivotov zadaných v parametri.
     *
     * @param poskodenie o kolko sa má zmenšiť život
     */
    public void uberZivot (int poskodenie) {
        this.pocetZivotov -= poskodenie;
    }

    /**
     * Metóda jeMrtvy() vráti pravdivostnú hodnotu ak počet životov je menší alebo rovný 0.
     *
     * @return boolean this.pocetZivotov
     */
    public boolean jeMrtvy() {
        return this.pocetZivotov <= 0;
    }

    /**
     * Getter getPozíciaX() vráti hodnotu atribútu this.poziciaX.
     *
     * @return float this.poziciaX
     */
    public float getPoziciaX() {
        return this.poziciaX;
    }

    /**
     * Getter getPozíciaY() vráti hodnotu atribútu this.poziciaY.
     *
     * @return float this.poziciaY
     */
    public float getPoziciaY() {
        return this.poziciaY;
    }

    /**
     * Getter getPredoslaPozíciaX() vráti hodnotu atribútu this.predoslaPoziciaX.
     *
     * @return float this.predoslaPoziciaX
     */
    public float getPredoslaPoziciaX() {
        return this.predoslaPoziciaX;
    }

    /**
     * Setter setPredoslaPozíciaX() nastaví hodnotu atribútu podľa parametra.
     *
     * @param predoslaPoziciaX o kolko nastaviť predošlú pozíciu X
     */
    public void setPredoslaPoziciaX(float predoslaPoziciaX) {
        this.predoslaPoziciaX = predoslaPoziciaX;
    }

    /**
     * Getter getPredoslaPozíciaY() vráti hodnotu atribútu this.predoslaPoziciaY.
     *
     * @return float this.predoslaPoziciaY
     */
    public float getPredoslaPoziciaY() {
        return this.predoslaPoziciaY;
    }

    /**
     * Setter setPredoslaPozíciaY() nastaví hodnotu atribútu podľa parametra.
     *
     * @param predoslaPoziciaY o kolko nastaviť predošlú pozíciu Y
     */
    public void setPredoslaPoziciaY(float predoslaPoziciaY) {
        this.predoslaPoziciaY = predoslaPoziciaY;
    }

    /**
     * Getter getHitbox() vráti hodnotu atribútu this.hitbox.
     *
     * @return Rectangle this.hitbox
     */
    public Rectangle getHitbox() {
        return this.hitbox;
    }

    /**
     * Chránený setter setHitbox nastaví hodnotu atribútu podľa parametra.
     *
     * @param hitbox nastav hitbox
     */
    protected abstract void setHitbox(Rectangle hitbox);

    /**
     * Setter setSmer() nastaví hodnotu atribútu podľa parametra.
     *
     * @param smer určuje smer kde sa postava pozerá
     */
    public void setSmer(boolean smer) {
        this.smer = smer;
    }

    /**
     * Setter setPocetZivotov() nastaví hodnotu atribútu podľa parametra.
     *
     * @param pocetZivotov nastaví počet zivotov na požadovanú hodnotu
     */
    public void setPocetZivotov(int pocetZivotov) {
        this.pocetZivotov = pocetZivotov;
    }

    /**
     * Metóda dispose() odstráni onbjekty z pamäte
     */
    public void dispose() {
        this.postava.dispose();
    }
}
