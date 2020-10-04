/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 *
 * @author Harsh Pandey
 */
public abstract class BaseGame extends Game
{

    private static BaseGame game;
    public static LabelStyle labelStyle;

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
        
        labelStyle = new LabelStyle();
        
        //setting font generator
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("assets/OpenSans.ttf"));
        
        // setting font 
        FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
        fontParameters.size = 48;
        fontParameters.color = Color.WHITE;
        fontParameters.borderWidth = 2;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = TextureFilter.Linear;
        fontParameters.magFilter = TextureFilter.Linear;
        
        BitmapFont customFont  = fontGenerator.generateFont(fontParameters);
        labelStyle.font = customFont;
      }
}
