package sk.uniza.fri;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sk.uniza.fri.screens.MainMenuScreen;

/**
 * Trieda hra je potomkom triedy Game, ktorá pridáva metódy: create(konštruktor) a render(opakujúca sa činnosť).
 * Úlohou tejto triedy je vytvorenie obrazoviek: MainMenuScreen, GameScreen, WinGameScreen, GameOverScreen.
 * Tak isto vytvára spoločný SpriteBatch pre všetky triedy na vykreslenie textúr.
 *
 * @author Pavol Tomčík
 * @version 20.5.2022
 */

public class Hra extends Game {

    private SpriteBatch batch;

    /**
     * Metoda create je konštruktor v ktorom sa inicializuje atribut triedy SpriteBatch a vytvorí obrazovku MainMenuScreen
     */
    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
    }

    /**
     * Metoda render podedí od predka všetky vlastnosti
     */
    @Override
    public void render() {
        super.render();
    }

    /**
     * Getter slžiaci na získanie prístupu k atributu batch pre ostatné triedy, tak aky nebolo porušené zapúzdrenie
     *
     * @return SpriteBatch this.batch
     */
    public SpriteBatch getBatch() {
        return this.batch;
    }
}
