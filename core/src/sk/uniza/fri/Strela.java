package sk.uniza.fri;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Trieda Strela slúži na vytvorenie strely, smer pohybu a jej hitbox.
 *
 * @author Pavol Tomčík
 * @version 20.5.2022
 */
public class Strela {
    private Texture naboj;
    private final Rectangle hitbox;
    private float x;
    private float y;
    private final float uhol;
    private float casLetu;
    private boolean remove;
    private int damage = 25;

    /**
     * Konštruktor pre inicializáciu atribútov a matematika pre výpočet súradníc a smeru pohybu strely.
     *
     * @param x súradnica X
     * @param y súradnica Y
     * @param camera kamera
     * @param mysX súradnica myši X
     * @param mysY súradnica myši Y
     */
    public Strela(float x, float y, OrthographicCamera camera, float mysX, float mysY) {
        this.x = x;
        this.y = y;
        this.remove = false;

        this.casLetu = 0;

        Vector3 poziciaMys = camera.unproject(new Vector3(mysX, mysY, 0));
        Vector3 poziciaStrely = new Vector3(this.x, this.y, 0);

        this.uhol = (float)Math.atan2(poziciaMys.y - poziciaStrely.y, poziciaMys.x - poziciaStrely.x);

        this.naboj = new Texture("bullet.png");
        this.hitbox = new Rectangle(x, y, this.naboj.getWidth(), this.naboj.getHeight());
    }

    /**
     * Metóda update() slúži na obnovenie súradníc čo vytvára efekt pohybu.
     * Ak prejde strela daný čas tak sa vymaže.
     *
     * @param deltaTime pre čas
     */
    public void update(float deltaTime) {

        float speed = 600.0f;
        this.x += speed * deltaTime * (float)Math.cos(this.uhol);
        this.y += speed * deltaTime * (float)Math.sin(this.uhol);
        this.hitbox.setPosition(this.x, this.y);

        this.casLetu += deltaTime;

        if (this.casLetu > 0.9f) {
            this.remove = true;
            this.casLetu = 0;
        }
    }

    /**
     * Metóda render() slúži na vykreslenie strely.
     *
     * @return Texture this.naboj
     */
    public Texture render() {
        return this.naboj;
    }

    /**
     * Getter getX() pre vrátenie súradnice X.
     *
     * @return float this.x
     */
    public float getX() {
        return this.x;
    }

    /**
     * Getter getY() pre vrátenie súradnice Y.
     *
     * @return float this.y
     */
    public float getY() {
        return this.y;
    }

    /**
     * Metóda isRemove() vrati hodnotu či je strela odstránená.
     *
     * @return boolean this.remove
     */
    public boolean isRemove() {
        return this.remove;
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
     * Metóda dispose() odstráni onbjekty z pamäte
     */
    public void dispose() {
        this.naboj.dispose();
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setNaboj() {
        this.naboj = new Texture("bullet2.png");
    }
}
