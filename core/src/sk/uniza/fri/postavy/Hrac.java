package sk.uniza.fri.postavy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import sk.uniza.fri.HPbar;
import java.util.ArrayList;

/**
 * Trieda Hráč slúži na ovládanie hráča, pridávanie a odobratie života.
 * Trieda extenduje triedu Postava pre uberanie životov, animáciu a nastavenie hitboxu.
 *
 * @author Pavol Tomčík
 * @version 20.5.2022
 */
public class Hrac extends Postava {
    private float poziciaX;
    private float poziciaY;

    private final String nazov;
    private Texture hrac;
    private int pocitadlo;
    private boolean animacia;

    private final ArrayList<HPbar> hpBar = new ArrayList<>();

    /**
     * Konštruktor inicializuje atribúty a vytvorý počet životov
     *
     * @param nazov názov postavy
     * @param pocetZivotov počet životov postavy
     */
    public Hrac(String nazov, int pocetZivotov) {
        super(12, nazov, 600, 9300, pocetZivotov);
        this.nazov = nazov;
        this.poziciaX = 600;
        this.poziciaY = 9300;
        this.animacia = true;

        this.pridajZivot(pocetZivotov);

        this.stoj();


    }

    /**
     * Metóda vykresliZivoty() vykresluje životy pomocou parametra batch na daných pozíciach
     *
     * @param batch pre vykreslenie textury
     */
    public void vykresliZivoty(SpriteBatch batch) {
        for (int i = 0; i < this.hpBar.size(); i++) {
            batch.draw(this.hpBar.get(i).getSrdce(), this.getPredoslaPoziciaX() + (this.hpBar.get(i).getSrdce().getWidth() - (Gdx.graphics.getWidth()/2 + 100)) + 50 * i, this.getPredoslaPoziciaY() + Gdx.graphics.getHeight() / 2);

        }
    }

    /**
     * Metóda pohniSa() slúži na pohyb hráča zmenením suradníc podľa rýchlosti a času pre plynulejší pohyb.
     *
     * @return boolean stav ktorý určuje či sa hráč pohybuje
     */
    public boolean pohniSa() {
        this.dispose();
        this.setPredoslaPoziciaX(this.poziciaX);
        this.setPredoslaPoziciaY(this.poziciaY);



        boolean stav = false;
        float speed = 500.0f;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.poziciaY += Gdx.graphics.getDeltaTime() * speed;
            stav = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.poziciaY -= Gdx.graphics.getDeltaTime() * speed;
            stav = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.animacia = false;
            this.poziciaX -= Gdx.graphics.getDeltaTime() * speed;
            stav = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.animacia = true;
            this.poziciaX += Gdx.graphics.getDeltaTime() * speed;
            stav = true;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {

            stav = false;
        }


        this.setHitbox(this.getHitbox());
        return stav;
    }

    /**
     * Metóda stoj vráti textúru postavy v statickom postoji.
     *
     * @return Texture this.hrac vrati texturu danej postavy
     */
    public Texture stoj() {
        if (this.animacia) {
            this.hrac = new Texture("postavy/" + this.nazov + "/doprava/1.png");
        } else {
            this.hrac = new Texture("postavy/" + this.nazov + "/dolava/1.png");
        }
        return this.hrac;

    }

    /**
     * Metóda animacia() je prepisovaná metóda z predka Postava a má za úlohu prechadzaním všetkých dostupných obrazkov pre danú postavu vytvoriť animaciu chôdze.
     *
     * @return Texture this.hrac vráti textúru duanej postavy
     */
    @Override
    public Texture animacia() {
        this.pocitadlo++;
        int cislo = (this.pocitadlo % 12) + 1;
        if (this.animacia) {
            if (this.nazov.equals("muz")) {
                this.hrac = new Texture("postavy/muz/doprava/" + cislo + ".png");
            } else {
                this.hrac = new Texture("postavy/zena/doprava/2.png");
            }
        } else {
            if (this.nazov.equals("muz")) {
                this.hrac = new Texture("postavy/muz/dolava/" + cislo + ".png");
            } else {
                this.hrac = new Texture("postavy/zena/dolava/2.png");
            }

        }
        return this.hrac;
    }

    /**
     * Metóda uberZivot() je metóda predka Postava, ktorá má navyše vizuálne odbranie života.
     *
     * @param poskodenie o kolko sa má zmenšiť život
     */
    @Override
    public void uberZivot(int poskodenie) {
        super.uberZivot(poskodenie);
        this.hpBar.remove(0);
    }

    /**
     * Metóda pridajZivot() je metóda, ktorá pridá vizuálne život a aj ho o tolko navýši.
     *
     * @param pocetZivotov o kolko sa má pridat život
     */
    public void pridajZivot(int pocetZivotov) {
        this.setPocetZivotov(pocetZivotov);
        this.hpBar.removeAll(this.hpBar);
        for (int i = 0; i < pocetZivotov / 25; i++) {
            this.hpBar.add(new HPbar());
        }

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
     * Setter setPozíciaX() nastaví hodnotu atribútu podľa parametra.
     *
     * @param poziciaX o kolko nastaviť predošlú pozíciu X
     */
    public void setPoziciaX(float poziciaX) {
        this.poziciaX = poziciaX;
    }

    /**
     * Setter setPozíciaY() nastaví hodnotu atribútu podľa parametra.
     *
     * @param poziciaY o kolko nastaviť predošlú pozíciu Y
     */
    public void setPoziciaY(float poziciaY) {
        this.poziciaY = poziciaY;
    }

    /**
     * Metóda getHitbox() dedí od predka všetky vlastnosti.
     *
     * @return Rectangle super.getHitbox()
     */
    @Override
    public Rectangle getHitbox() {
        return super.getHitbox();
    }

    /**
     * Chránená metóda setHibox() dedí od predka všetky vlastnosti.
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
     * Metóda dispose() odstráni onbjekty z pamäte
     */
    @Override
    public void dispose() {
        super.dispose();
        this.hrac.dispose();
    }

}
