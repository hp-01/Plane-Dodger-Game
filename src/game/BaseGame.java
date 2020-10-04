/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

/**
 *
 * @author Harsh Pandey
 */
public abstract class BaseGame extends Game
{

    private static BaseGame game;

    public BaseGame()
      {
        game = this;
      }

    public static void setActiveScreen(BaseScreen s)
      {
        game.setScreen(s);
      }

    @Override
    public void create()
      {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);
      }
}
