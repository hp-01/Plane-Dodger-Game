/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 *
 * @author Harsh Pandey
 */
public class MenuScreen extends BaseScreen
{

    public String score = "0";

    @Override
    public void initialize()
      {
        new Sky(0, 0, mainStage);
        new Sky(800, 0, mainStage);
        new Ground(0, 0, mainStage);
        new Ground(800, 0, mainStage);

        Label title = new Label("Plane Dodger", BaseGame.labelStyle);
        title.setFontScale((float) 1.5);
        title.setColor(Color.ORANGE);
        uiTable.add(title).colspan(2).pad(50);

        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/Prelude-and-Action.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1.00f);
        backgroundMusic.play();

        TextButton startButton = new TextButton("Start", BaseGame.textButtonStyle);
        startButton.addListener((Event e) ->
        {
            if (!isTouchDownEvent(e))
            {
                return false;
            }
            backgroundMusic.stop();
            backgroundMusic.dispose();
            MainGame.setActiveScreen(new LevelScreen());
            return true;
        });

        TextButton quitButton = new TextButton("Quit", BaseGame.textButtonStyle);
        quitButton.addListener(
                (Event e) ->
                {
                    if (!isTouchDownEvent(e))
                    {
                        return false;
                    }
                    Gdx.app.exit();
                    return true;
                });
        uiTable.row();
        uiTable.add(startButton).expandX();
        uiTable.add(quitButton).expandX();

        String[] score = Gdx.files.internal("assets/main.dd").readString().split("\n");
        Label scoreLabel = new Label("High Score: " + score[0], BaseGame.labelStyle);

        uiTable.row();
        uiTable.add(scoreLabel).colspan(2).pad(20);

      }

    @Override
    public void update(float dt)
      {

      }
}
