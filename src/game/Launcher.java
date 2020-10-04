/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 *
 * @author Harsh Pandey
 */
public class Launcher
{
    public static void main(String[] args)
      {
        Game myGame = new MainGame();
        LwjglApplication launcher = new LwjglApplication(myGame,"Plane Dodger",800,600);
      }
}
