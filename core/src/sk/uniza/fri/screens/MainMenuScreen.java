package sk.uniza.fri.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import sk.uniza.fri.Hra;

/**
 * Trieda MainMenuScreen slúži na zobrazenie hlavného menu kde sa vyberá hráčska postava a tlačidla Play(pre spustenie hry) a Exit(pre ukončenie hry).
 * Trieda implenemtuje interface Screen pre získanie metód: show(konštruktor), render(pre vkresľovanie objektov), resize, pause, resume, hide a dispose(pre odstránenie objektov z pamäte).
 *
 * @author Pavol Tomčík
 * @version 20.5.2022
 */

public class MainMenuScreen implements Screen {
    private final Hra game;

    private final Texture manButtonActive;
    private final Texture manButtonInactive;
    private final Texture womanButtonActive;
    private final Texture womanButtonInactive;
    private final Texture playActive;
    private final Texture playInactive;
    private final Texture exitActive;
    private final Texture exitInactive;

    private final Texture nadpis;
    private Texture instrukcia;

    private String postava;
    private boolean jePostava;

    /**
     * Konštruktor slúži na inicializáciu atributov textúr.
     *
     * @param game sa získa z triedy Hra a umožnuje prístup ku knižnici SpriteBatch pre vykreslenie textur.
     */

    public MainMenuScreen(Hra game) {
        this.game = game;

        this.jePostava = false;

        this.nadpis = new Texture("obrazovka/nadpis.png");
        this.instrukcia = new Texture("obrazovka/instrukcia_biela.png");

        this.manButtonActive = new Texture("obrazovka/buttons/muz_active.png");
        this.manButtonInactive = new Texture("obrazovka/buttons/muz_inactive.png");
        this.womanButtonActive = new Texture("obrazovka/buttons/zena_active.png");
        this.womanButtonInactive = new Texture("obrazovka/buttons/zena_inactive.png");
        this.playInactive = new Texture("obrazovka/buttons/playButtonInactive.png");
        this.playActive = new Texture("obrazovka/buttons/playButtonActive.png");
        this.exitInactive = new Texture("obrazovka/buttons/exitButtonInactive.png");
        this.exitActive = new Texture("obrazovka/buttons/exitButtonActive.png");


    }

    @Override
    public void show() {

    }

    /**
     * Metóda render sluzi na vykonavanie činnosti v cykle.
     * Vyčistí obrazovku a vykreslí ju danou farbou. Potom sa zavolá getter triedy Hra (getBatch()) pre vykreslenie textúr.
     * Následne sa zavolajú privátne metódy vyberPostvy() a tlacidlaAkcie()
     *
     * @param delta pre čas
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.2f, 0.2f, 1);
        this.game.getBatch().begin();

        this.game.getBatch().draw(this.nadpis, Gdx.graphics.getWidth() / 2f - this.nadpis.getWidth() / 2f, 3 * Gdx.graphics.getHeight() / 4f);
        this.game.getBatch().draw(this.instrukcia, Gdx.graphics.getWidth() / 2f - this.instrukcia.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - this.instrukcia.getHeight() / 2f);

        this.vyberPostavy();
        this.tlacidlaAkcie();

        this.game.getBatch().end();
    }

    /**
     * Privátna metóda vyberPostavy() zistuje či bola vybratá postava ak áno tak zvýrazni vybratú postavu.
     * Na to aby sa dala vybrat postava je potrebne sledovat suradnice myši a ak sa myš nachadza v poli pre vybratie postavy tak sa postava zvýrazni.
     * Ak sa klikne na danú postavu tak postava ostane zvýraznena a do atributov postava a jePostava sa nastaví pozadovaná hodnota.
     */
    private void vyberPostavy() {
        if (!this.jePostava) {
            this.game.getBatch().draw(this.manButtonInactive, Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 2f);
            this.game.getBatch().draw(this.womanButtonInactive, 3 * (Gdx.graphics.getWidth()) / 4f - this.womanButtonInactive.getWidth(), Gdx.graphics.getHeight() / 2f);
        } else {
            if (this.postava.equals("muz")) {
                this.game.getBatch().draw(this.manButtonActive, Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 2f);
                this.game.getBatch().draw(this.womanButtonInactive, 3 * (Gdx.graphics.getWidth()) / 4f - this.womanButtonInactive.getWidth(), Gdx.graphics.getHeight() / 2f);
            } else {
                this.game.getBatch().draw(this.womanButtonActive, 3 * (Gdx.graphics.getWidth()) / 4f - this.womanButtonInactive.getWidth(), Gdx.graphics.getHeight() / 2f);
                this.game.getBatch().draw(this.manButtonInactive, Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 2f);
            }
        }

        if (Gdx.input.getX() > Gdx.graphics.getWidth() / 4 && Gdx.graphics.getWidth() / 4 + this.manButtonInactive.getWidth() > Gdx.input.getX() &&
                Gdx.input.getY() > Gdx.graphics.getHeight() / 2 - this.manButtonInactive.getHeight() && Gdx.graphics.getHeight() / 2 > Gdx.input.getY()) {
            this.game.getBatch().draw(this.manButtonActive, Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 2f);
            if (Gdx.input.isTouched()) {
                this.postava = "muz";
                this.jePostava = true;
            }
        }
        if (Gdx.input.getX() > 3 * (Gdx.graphics.getWidth()) / 4  - this.womanButtonInactive.getWidth() && 3 * (Gdx.graphics.getWidth()) / 4 > Gdx.input.getX() &&
                Gdx.input.getY() > Gdx.graphics.getHeight() / 2 - this.womanButtonInactive.getHeight() && Gdx.graphics.getHeight() / 2 > Gdx.input.getY()) {
            this.game.getBatch().draw(this.womanButtonActive, 3 * (Gdx.graphics.getWidth()) / 4f - this.womanButtonInactive.getWidth(), Gdx.graphics.getHeight() / 2f);
            if (Gdx.input.isTouched()) {
                this.postava = "zena";
                this.jePostava = true;
            }
        }

    }

    /**
     * Privátna metóda tlacidlaAkcie() spravuje tlačidlá Play a Exit.
     * Rovnako ako metóda vyberPstavy() aj táto metóda zvýraznuje tlačidla ale nenechá ich zvýraznené. Namiesto toho vykoná danú akciu, ktorá prislúcha danému tlačidlu.
     * Po kliknutí na tlačidlo Play sa obrazovka prepne na GameScreen len vtedy ak je vybratá postava, inak sa zvýrazni hláška aby si hráč vybral postavu.
     * Po kliknutí na tlačidlo Exit sa aplikacia vypne.
     */
    private void tlacidlaAkcie() {
        this.game.getBatch().draw(this.playInactive, Gdx.graphics.getWidth() / 2f - this.playInactive.getWidth() / 2f, Gdx.graphics.getHeight() / 4f + 20);
        this.game.getBatch().draw(this.exitInactive, Gdx.graphics.getWidth() / 2f - this.exitInactive.getWidth() / 2f, Gdx.graphics.getHeight() / 4f - this.playInactive.getHeight());

        if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2 - this.playInactive.getWidth() / 2 && Gdx.graphics.getWidth() / 2 + this.playInactive.getWidth() / 2 > Gdx.input.getX() &&
                Gdx.input.getY() > 3 * Gdx.graphics.getHeight() / 4 - this.playInactive.getHeight() - 20 && 3 * Gdx.graphics.getHeight() / 4 - 20 > Gdx.input.getY()) {
            this.game.getBatch().draw(this.playActive, Gdx.graphics.getWidth() / 2f - this.playInactive.getWidth() / 2f, Gdx.graphics.getHeight() / 4f + 20);
            if (Gdx.input.isTouched()) {
                if (this.postava == null) {
                    this.instrukcia = new Texture("obrazovka/instrukcia_cervena.png");
                } else {
                    this.dispose();
                    this.game.setScreen(new GameScreen(this.game, this.postava));

                }
            }
        }
        if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2 - this.exitInactive.getWidth() / 2 && Gdx.graphics.getWidth() / 2 + this.exitInactive.getWidth() / 2 > Gdx.input.getX() &&
                Gdx.input.getY() > 3 * Gdx.graphics.getHeight() / 4 && 3 * Gdx.graphics.getHeight() / 4 + this.exitInactive.getHeight() > Gdx.input.getY()) {
            this.game.getBatch().draw(this.exitActive, Gdx.graphics.getWidth() / 2f - this.exitInactive.getWidth() / 2f, Gdx.graphics.getHeight() / 4f - this.playInactive.getHeight());
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * Metóda dispose() odstráni onbjekty z pamäte
     */
    @Override
    public void dispose() {
        this.nadpis.dispose();
        this.instrukcia.dispose();
        this.manButtonActive.dispose();
        this.manButtonInactive.dispose();
        this.womanButtonActive.dispose();
        this.womanButtonInactive.dispose();
        this.playInactive.dispose();
        this.playActive.dispose();
        this.exitInactive.dispose();
        this.exitActive.dispose();
    }
}
