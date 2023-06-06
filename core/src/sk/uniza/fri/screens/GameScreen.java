package sk.uniza.fri.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.utils.ScreenUtils;
import sk.uniza.fri.Strela;
import sk.uniza.fri.postavy.Hrac;
import sk.uniza.fri.Hra;
import sk.uniza.fri.Mapa;
import sk.uniza.fri.postavy.Morgarath;
import sk.uniza.fri.postavy.Nepriatel;
import sk.uniza.fri.postavy.Wargal;
import java.util.ArrayList;
import java.util.Random;

/**
 * GameScreen je hlavná trieda kde sa vykonáva celá hra.
 * Trieda implenemtuje interface Screen pre získanie metód: show(konštruktor), render(pre vkresľovanie objektov), resize, pause, resume, hide a dispose(pre odstránenie objektov z pamäte).
 *
 * @author Pavol Tomčík
 * @version 20.5.2022
 */

public class GameScreen implements Screen {
    private Mapa mapa;
    private OrthographicCamera camera;

    private Hrac hrac;
    private final int pocetZivotov;
    private ArrayList<Nepriatel> nepriatelia;
    private ArrayList<Strela> strely;
    private float casovacStrelbyHrac;
    private float casovacUtokuNepriatel;
    private final String postava;
    private final Hra game;
    private int level;
    private int pocetNepriatelov;
    private int pocetMrtvychNepriatelov;
    private int cas;
    private boolean lastState;

    /**
     * V konštruktore sa inicializujú konšranty zo zadaných parametrov a niektoré atribúty.
     *
     * @param game sa získa z triedy Hra a umožnuje prístup ku knižnici SpriteBatch pre vykreslenie textur.
     * @param postava sa ziska z triedy MainMenuScreen ako zvolena hráčska postava.
     */
    public GameScreen(Hra game, String postava) {
        this.game = game;
        this.postava = postava;
        this.lastState = false;
        this.level = 2;
        this.pocetNepriatelov = 3;
        this.pocetZivotov = 100;
    }

    /**
     * V metóde show() sa inicializuju atributy.
     */
    @Override
    public void show() {
        this.strely = new ArrayList<>();
        this.nepriatelia = new ArrayList<>();
        this.casovacStrelbyHrac = 0;

        this.hrac = new Hrac(this.postava, this.pocetZivotov);

        this.camera = new OrthographicCamera(this.hrac.getPoziciaX(), this.hrac.getPoziciaY());

        this.mapa = new Mapa();
        this.camera.setToOrtho(false);
        this.camera.zoom = 1.12f;

    }

    /**
     * Metóda render() je hlavna metóda, kde sa vykonáva celá logika hry.
     * Nastavenie obrazovky, kamery a vykreslenie mapy, a textúr.
     * @param delta pre čas
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.2f, 0.2f, 1);
        this.mapa.setView(this.camera);
        this.mapa.render();

        this.camera.position.set(this.hrac.getPoziciaX(), this.hrac.getPoziciaY(), 0);
        this.camera.update();
        this.game.getBatch().begin();

        this.hrac.vykresliZivoty(this.game.getBatch());
        this.brana();
        this.spawnNepriatelov();
        this.posunNepriatela();
        this.posunHraca();
        this.utokHrac(delta);
        this.aktivneDlazdice();
        this.utokNepriatelia(delta);
        this.novyLevel();

        this.game.getBatch().setProjectionMatrix(this.camera.combined);
        this.game.getBatch().end();


        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        /**
         * Cheaty v hre.
         * P - vymaze vsetkych nepriatelov ktorí sa nachádzaju na mape
         * H - doplni hracovy zivoty na maximum
         * T - teleportuje hrača na dalšiu úroveň
         */
        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            this.pocetMrtvychNepriatelov = 1000000000;
            this.nepriatelia.removeAll(this.nepriatelia);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            this.hrac.pridajZivot(this.pocetZivotov);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.T)) {
            this.hrac.setPoziciaX(12900);
            this.hrac.setPoziciaY(4700);
            this.pocetNepriatelov = 30;
            this.level = 7;
        }

    }

    private void aktivneDlazdice() {
        for (RectangleMapObject power : this.mapa.getPower().getByType(RectangleMapObject.class)) {
            if (power.getRectangle().overlaps(this.hrac.getHitbox())) {
                for (Strela strela : this.strely) {
                    strela.setNaboj();
                    strela.setDamage(50);
                }
            }
        }
        for (RectangleMapObject health : this.mapa.getHealth().getByType(RectangleMapObject.class)) {
            if (health.getRectangle().overlaps(this.hrac.getHitbox())) {
                this.hrac.pridajZivot(this.pocetZivotov);
            }
        }
        for (RectangleMapObject poison : this.mapa.getPoison().getByType(RectangleMapObject.class)) {
            if (poison.getRectangle().overlaps(this.hrac.getHitbox())) {
                this.hrac.pridajZivot(this.pocetZivotov / 4);
            }
        }

    }

    /**
     * Metóda brana() slúži na zistenie či háač prešiel bránou medzi miestnostami.
     * Ak ano tak nastavy atribút this.pocetMrtvychNepriatelov a čas na prechod na 0.
     */
    private void brana() {
        for (RectangleMapObject gate : this.mapa.getGate().getByType(RectangleMapObject.class)) {
            boolean currentState = gate.getRectangle().overlaps(this.hrac.getHitbox());
            if (this.lastState != currentState) {
                this.cas++;
            }
            if (this.cas == 50) {
                this.lastState = currentState;
                this.pocetMrtvychNepriatelov = 0;
                this.cas = 0;
            }

        }
    }

    /**
     * Metóda posunHráča() vykresluje hráča na novej pozicí, pričom volá metódu animácia z triedy Hrac.
     * V tejto metóde sa rieši aj kolízia hráča so stenou. Keď nastane kolízia tak zavolá metódu kolizia() z triedy Hrac.
     */
    private void posunHraca() {
        if (this.hrac.pohniSa()) {
            this.game.getBatch().draw(this.hrac.animacia(), this.hrac.getPoziciaX(), this.hrac.getPoziciaY());
        } else {
            this.game.getBatch().draw(this.hrac.stoj(), this.hrac.getPoziciaX(), this.hrac.getPoziciaY());
        }
        for (RectangleMapObject stena : this.mapa.getStena().getByType(RectangleMapObject.class)) {
            if (stena.getRectangle().overlaps(this.hrac.getHitbox())) {
                this.hrac.kolizia();
                break;
            }
        }

    }

    /**
     * Metóda novyLevel() má za úlohu presúvať hráča do nových levelov keď hráč sa dostane do portalu a klikne tlačitlo E
     */
    private void novyLevel() {
        for (RectangleMapObject portal : this.mapa.getPortal().getByType(RectangleMapObject.class)) {
            if (portal.getRectangle().overlaps(this.hrac.getHitbox()) && this.nepriatelia.isEmpty() && Gdx.input.isKeyPressed(Input.Keys.E)) {
                switch (this.level) {
                    case 2:
                        this.hrac.setPoziciaX(5000);
                        this.hrac.setPoziciaY(9300);
                        this.pocetNepriatelov = 5;
                        this.level = 3;
                        break;
                    case 3:
                        this.hrac.setPoziciaX(16000);
                        this.hrac.setPoziciaY(7000);
                        this.pocetNepriatelov = 10;
                        this.level = 4;
                        break;
                    case 4:
                        this.hrac.setPoziciaX(16000);
                        this.hrac.setPoziciaY(9300);
                        this.pocetNepriatelov = 15;
                        this.level = 5;
                        break;
                    case 5:
                        this.hrac.setPoziciaX(600);
                        this.hrac.setPoziciaY(4700);
                        this.pocetNepriatelov = 20;
                        this.level = 6;
                        break;
                    case 6:
                        this.hrac.setPoziciaX(12900);
                        this.hrac.setPoziciaY(4700);
                        this.pocetNepriatelov = 30;
                        this.level = 7;
                        break;
                    default:
                        this.hrac.setPoziciaX(600);
                        this.hrac.setPoziciaY(9300);
                        this.pocetNepriatelov = 3;
                        break;
                }
                this.hrac.pridajZivot(this.pocetZivotov);
            }
        }
    }

    /**
     * Metóda ytokNepriatela() rieši utok každého vygenerovanéeho nepriateľa. Nepriatelia utočia tým že sa dostanú blýzko ku hráčovi.
     * Keď hráč zomrie tak sa zavolá trieda GameOverScreen pre zobrazenie kocna hry.
     *
     * @param delta pre čas
     */
    private void utokNepriatelia(float delta) {
        this.casovacUtokuNepriatel += delta;
        for (Nepriatel nepriatel : this.nepriatelia) {
            if (nepriatel.getHitbox().overlaps(this.hrac.getHitbox())) {
                if (this.casovacUtokuNepriatel > 2) {
                    this.hrac.uberZivot(25);
                    this.casovacUtokuNepriatel = 0;
                    if (this.hrac.jeMrtvy()) {
                        System.out.println("mrtvy");
                        this.camera.zoom = 1;
                        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                        this.dispose();
                        this.game.setScreen(new GameOverScreen(this.game));
                    }
                }
            }
        }
    }

    /**
     * Metóda utokHrač() sa stará o útok hráča na nepriateľov. Hráč striela pomocou medzerníku a smer strely je určený pozíciou myši.
     *
     * @param delta pre čas
     */
    private void utokHrac(float delta) {
        this.casovacStrelbyHrac += delta;
        float oneskorenieStrelby = 0.3f;
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) && this.casovacStrelbyHrac >= oneskorenieStrelby) || (Gdx.input.isTouched() && this.casovacStrelbyHrac >= oneskorenieStrelby)) {
            this.casovacStrelbyHrac = 0;
            this.strely.add(new Strela(this.hrac.getPoziciaX(), this.hrac.getPoziciaY(), this.camera, Gdx.input.getX(), Gdx.input.getY()));
        }



        for (int i = 0; i < this.strely.size(); i++) {
            boolean existujeNaboj = true;
            this.strely.get(i).update(delta);
            if (this.strely.get(i).isRemove()) {
                this.strely.get(i).dispose();
                this.strely.remove(i);
                break;

            } else if (this.nepriatelia.size() == 0 && this.level == 8) {
                this.camera.zoom = 1;
                this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                this.dispose();
                this.game.setScreen(new WinGameScreen(this.game, this.postava));


            } else if (!this.nepriatelia.isEmpty()) {
                for (int j = 0; j < this.nepriatelia.size(); j++) {
                    if (this.nepriatelia.get(j).getHitbox().overlaps(this.strely.get(i).getHitbox())) {
                        this.nepriatelia.get(j).uberZivot(this.strely.get(i).getDamage());
                        if (this.nepriatelia.get(j).jeMrtvy()) {
                            this.nepriatelia.get(j).dispose();
                            this.nepriatelia.remove(j);
                            this.pocetMrtvychNepriatelov++;

                        }
                        this.strely.remove(i);
                        existujeNaboj = false;
                        break;
                    }

                }
            }
            if (existujeNaboj) {
                for (RectangleMapObject stena : this.mapa.getStena().getByType(RectangleMapObject.class)) {
                    if (stena.getRectangle().overlaps(this.strely.get(i).getHitbox())) {
                        this.strely.remove(i);
                        break;
                    }
                }
            }
        }
        // vykreslenie
        for (Strela strela : this.strely) {
            this.game.getBatch().draw(strela.render(), strela.getX(), strela.getY());
        }
    }

    /**
     * Metóda posunNepriatela() slúži na nepriateľov pohyb. Pre každého nepriatela sa zavolá metóda posun z abstraktnej triedy Nepriatel.
     * Nepriatel sa môže pohybovať len v oblasti jeho spawnu, takže stena a brána budú tvoriť kolíziu.
     */
    private void posunNepriatela() {
        for (Nepriatel nepriatel : this.nepriatelia) {
            nepriatel.posun();
            this.game.getBatch().draw(nepriatel.animacia(), nepriatel.getPoziciaX(), nepriatel.getPoziciaY());
            for (RectangleMapObject stena : this.mapa.getStena().getByType(RectangleMapObject.class)) {
                if (stena.getRectangle().overlaps(nepriatel.getHitbox())) {
                    nepriatel.kolizia();
                    break;
                }
            }
            for (RectangleMapObject blokovanyVstup : this.mapa.getBlockedEnter().getByType(RectangleMapObject.class)) {
                if (blokovanyVstup.getRectangle().overlaps(nepriatel.getHitbox())) {
                    nepriatel.kolizia();
                    break;
                }
            }
        }
    }

    /**
     * Metóda spawnNepriatelov() má za úlohu vytvoriť daný počet nepriatelov na danom mieste ak sa tam už nenachádzajú.
     * Generovanie nepriatelov je vo vlnách od 1 po 3 (vrátane). V poslednom leveli sa vytvorí jeden nepriateľ a to hlavy boss Morgarath.
     */
    private void spawnNepriatelov() {
        for (RectangleMapObject wargalsSpawn : this.mapa.getWargalSpawn().getByType(RectangleMapObject.class)) {
            if (wargalsSpawn.getRectangle().overlaps(this.hrac.getHitbox())) {
                int pocetVln = new Random().nextInt(2) + 1;


                if (this.nepriatelia.isEmpty() && this.pocetMrtvychNepriatelov < this.pocetNepriatelov * pocetVln) {
                    for (int i = 0; i < this.pocetNepriatelov; i++) {
                        Wargal wargal = new Wargal(wargalsSpawn.getRectangle().getX() + 500, wargalsSpawn.getRectangle().getY() + 500);
                        this.nepriatelia.add(wargal);
                    }

                }
                if (this.level == 7 && this.nepriatelia.isEmpty()) {
                    Morgarath morgarath = new Morgarath(wargalsSpawn.getRectangle().getX() + 500, wargalsSpawn.getRectangle().getY() + 500);
                    this.nepriatelia.add(morgarath);
                    this.level = 8;

                }

                break;
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
        this.hrac.dispose();
        this.mapa.dispose();
        this.game.dispose();
    }
}
