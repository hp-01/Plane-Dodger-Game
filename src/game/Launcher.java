/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 *
 * @author Harsh Pandey
 */
public class Launcher
{
    public static void main(String[] args)
      {
        Game myGame = new MainGame();
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        config.title = "Plane Dodger";
        config.resizable = false;
        config.addIcon("assets/icon/plane_128.png",FileType.Internal);
        config.addIcon("assets/icon/plane_64.png",FileType.Internal);
        config.addIcon("assets/icon/plane_32.png",FileType.Internal);
        config.addIcon("assets/icon/plane_16.png",FileType.Internal);
        LwjglApplication launcher = new LwjglApplication( myGame, config );
      }
}
