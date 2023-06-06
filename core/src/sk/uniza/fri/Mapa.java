package sk.uniza.fri;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Trieda Mapa sa stará o vykreslenie mapy a jej objektov.
 *
 * @author Pavol Tomčík
 * @version 20.5.2022
 */
public class Mapa {
    private final TiledMap mapa;
    private final OrthogonalTiledMapRenderer renderer;
    private MapLayer stenaObject;
    private MapLayer portalObject;
    private MapLayer wargalSpawnObject;
    private MapLayer gateObject;
    private MapLayer blockedEnterObject;
    private MapLayer powerObject;
    private MapLayer healthObject;
    private MapLayer poisonObject;
    private final MapObjects stena;
    private final MapObjects portal;
    private final MapObjects wargalSpawn;
    private final MapObjects gate;
    private final MapObjects blockedEnter;
    private final MapObjects power;
    private final MapObjects health;
    private final MapObjects poison;

    /**
     * Konštruktor inicializuje atribúty a načítava mapu.
     */
    public Mapa() {
        TmxMapLoader loader = new TmxMapLoader();
        this.mapa = loader.load("maps/mapa.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(this.mapa);

        for (MapLayer layer : this.mapa.getLayers()) {
            if (layer.getName().equals("stena")) {
                this.stenaObject = layer;
            }
            if (layer.getName().equals("portal")) {
                this.portalObject = layer;
            }
            if (layer.getName().equals("wargalSpawn")) {
                this.wargalSpawnObject = layer;
            }
            if (layer.getName().equals("gate")) {
                this.gateObject = layer;
            }
            if (layer.getName().equals("blockedEnter")) {
                this.blockedEnterObject = layer;
            }
            if (layer.getName().equals("power")) {
                this.powerObject = layer;
            }
            if (layer.getName().equals("health")) {
                this.healthObject = layer;
            }
            if (layer.getName().equals("poison")) {
                this.poisonObject = layer;
            }

        }
        this.stena = this.stenaObject.getObjects();
        this.portal = this.portalObject.getObjects();
        this.wargalSpawn = this.wargalSpawnObject.getObjects();
        this.gate = this.gateObject.getObjects();
        this.blockedEnter = this.blockedEnterObject.getObjects();
        this.power = this.powerObject.getObjects();
        this.health = this.healthObject.getObjects();
        this.poison = this.poisonObject.getObjects();
    }

    /**
     * Metóda setView() slúži na nastavenie kamery na mapu.
     *
     * @param camera kamera z kniznice OrthographicCamera
     */
    public void setView(OrthographicCamera camera) {
        this.renderer.setView(camera);
    }

    /**
     * Metóda render() slúži na vykreslenie mapy.
     */
    public void render() {
        this.renderer.render();
    }

    /**
     * Getter getStena() pre zistenie kolízie.
     *
     * @return MapObjects this.stena
     */
    public MapObjects getStena() {
        return this.stena;
    }

    /**
     * Getter getPortal() pre zistenie kolízie.
     *
     * @return MapObjects this.portal
     */
    public MapObjects getPortal() {
        return this.portal;
    }

    /**
     * Getter getWargalSpawn() pre zistenie kolízie.
     *
     * @return MapObjects this.wargalSpawn
     */
    public MapObjects getWargalSpawn() {
        return this.wargalSpawn;
    }

    /**
     * Getter getGate() pre zistenie kolízie.
     *
     * @return MapObjects this.gate
     */
    public MapObjects getGate() {
        return this.gate;
    }

    /**
     * Getter getBlockedEnter() pre zistenie kolízie.
     *
     * @return MapObjects this.blockedEnter
     */
    public MapObjects getBlockedEnter() {
        return this.blockedEnter;
    }

    public MapObjects getPower() {
        return this.power;
    }

    public MapObjects getHealth() {
        return this.health;
    }

    public MapObjects getPoison() {
        return this.poison;
    }

    /**
     * Metóda dispose() odstráni onbjekty z pamäte
     */
    public void dispose() {
        this.mapa.dispose();
        this.renderer.dispose();

    }

}
