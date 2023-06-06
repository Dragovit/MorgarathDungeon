package sk.uniza.fri.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import sk.uniza.fri.Hra;

/**
 * Trieda WinGameScreen slúži na zobrazenie výhry kde sa nachádzajú tlačidla Play(pre spustenie hry) a Exit(pre ukončenie hry).
 * Trieda implenemtuje interface Screen pre získanie metód: show(konštruktor), render(pre vkresľovanie objektov), resize, pause, resume, hide a dispose(pre odstránenie objektov z pamäte).
 *
 * @author Pavol Tomčík
 * @version 20.5.2022
 */

public class WinGameScreen implements Screen {
    private final Hra game;
    private final Texture nadpis;
    private final Texture playActive;
    private final Texture playInactive;
    private final Texture exitActive;
    private final Texture exitInactive;
    private final Texture texturaPostavy;


    /**
     * Konštruktor slúži na inicializáciu atributov textúr.
     *
     * @param game sa získa z triedy Hra a umožnuje prístup ku knižnici SpriteBatch pre vykreslenie textur.
     */
    public WinGameScreen(Hra game, String postava) {
        this.game = game;

        this.nadpis = new Texture("obrazovka/you_win.png");
        this.playInactive = new Texture("obrazovka/buttons/playButtonInactive.png");
        this.playActive = new Texture("obrazovka/buttons/playButtonActive.png");
        this.exitInactive = new Texture("obrazovka/buttons/exitButtonInactive.png");
        this.exitActive = new Texture("obrazovka/buttons/exitButtonActive.png");
        this.texturaPostavy = new Texture("postavy/" + postava + "/doprava/1.png");
    }

    @Override
    public void show() {

    }

    /**
     * Metóda render sluzi na vykonavanie činnosti v cykle.
     * Vyčistí obrazovku a vykreslí ju danou farbou. Potom sa zavolá getter triedy Hra (getBatch()) pre vykreslenie textúr.
     * Následne sa zavolajá privátna trieda tlacidlaAkcie()
     *
     * @param delta pre čas
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.2f, 0.2f, 1);
        this.game.getBatch().begin();
        this.game.getBatch().draw(this.nadpis, Gdx.graphics.getWidth() / 2f - this.nadpis.getWidth() / 2f, 3 * Gdx.graphics.getHeight() / 4f);
        this.game.getBatch().draw(this.texturaPostavy, Gdx.graphics.getWidth() / 2f - this.texturaPostavy.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - this.texturaPostavy.getHeight() / 2f);
        this.tlacidlaAkcie();
        this.game.getBatch().end();
    }

    /**
     * Privátna metóda tlacidlaAkcie() spravuje tlačidlá Play a Exit.
     * Táto metóda zvýraznuje tlačidla ale nenechá ich zvýraznené. Namiesto toho vykoná danú akciu, ktorá prislúcha danému tlačidlu.
     * Po kliknutí na tlačidlo Play sa obrazovka prepne na MainMenuScreen.
     * Po kliknutí na tlačidlo Exit sa aplikacia vypne.
     */
    private void tlacidlaAkcie() {
        this.game.getBatch().draw(this.playInactive, Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 4f - this.playInactive.getHeight());
        this.game.getBatch().draw(this.exitInactive, 3 * Gdx.graphics.getWidth() / 4f - this.exitInactive.getWidth(), Gdx.graphics.getHeight() / 4f - this.playInactive.getHeight());

        if (Gdx.input.getX() > Gdx.graphics.getWidth() / 4 && Gdx.graphics.getWidth() / 4 + this.playInactive.getWidth() > Gdx.input.getX() &&
                Gdx.input.getY() > 3 * Gdx.graphics.getHeight() / 4 && 3 * Gdx.graphics.getHeight() / 4 + this.playInactive.getHeight() > Gdx.input.getY()) {
            this.game.getBatch().draw(this.playActive, Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 4f - this.playInactive.getHeight());
            if (Gdx.input.isTouched()) {
                this.dispose();
                this.game.setScreen(new MainMenuScreen(this.game));
            }
        }
        if (Gdx.input.getX() > 3 * Gdx.graphics.getWidth() / 4 - this.exitInactive.getWidth() && 3 * Gdx.graphics.getWidth() / 4 > Gdx.input.getX() &&
                Gdx.input.getY() > 3 * Gdx.graphics.getHeight() / 4 && 3 * Gdx.graphics.getHeight() / 4 + this.exitInactive.getHeight() > Gdx.input.getY()) {
            this.game.getBatch().draw(this.exitActive, 3 * Gdx.graphics.getWidth() / 4f - this.exitInactive.getWidth(), Gdx.graphics.getHeight() / 4f - this.playInactive.getHeight());
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
        this.nadpis.dispose();
        this.playActive.dispose();
        this.playInactive.dispose();
        this.exitActive.dispose();
        this.exitInactive.dispose();
        this.texturaPostavy.dispose();
    }
}
