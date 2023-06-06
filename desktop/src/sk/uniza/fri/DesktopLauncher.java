package sk.uniza.fri;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * DesktopLauncher je hlavná trieda ktorá spúšťa Hru, nastavuje počet snímkov sa sekundu(fps) a rozlíšenie
 *
 * @author Pavol Tomčík
 * @version 20.5.2022
 */

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		//config.setWindowedMode(1920, 1020);
		config.setResizable(true);
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		new Lwjgl3Application(new Hra(), config);
	}
}
