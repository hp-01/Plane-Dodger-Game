/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

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
        uiTable.add(title).colspan(3).pad(50);

        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/Prelude-and-Action.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1.00f);
        backgroundMusic.play();

        TextButton shopButton = new TextButton("Shop", BaseGame.textButtonStyle);
        shopButton.addListener(
                (Event e) ->
                {
                    if (!isTouchDownEvent(e))
                    {
                        return false;
                    }
                    backgroundMusic.dispose();
                    MainGame.setActiveScreen(new ShopScreen());
                    return true;
                });

        TextButton playButton = new TextButton("Play", BaseGame.textButtonStyle);
        playButton.addListener((Event e) ->
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
        uiTable.add(shopButton).expandX();
        uiTable.add(playButton).expandX();
        uiTable.add(quitButton).expandX();

        /*String[] score = Gdx.files.internal("assets/main.dd").readString().split("\n");
         Label scoreLabel = new Label("High Score: " + score[0], BaseGame.labelStyle);

         uiTable.row();
         uiTable.add(scoreLabel).colspan(2).pad(20);*/
        FileHandle handle = null;
        try
        {
            handle = new FileHandle("main.dd");
            String[] score = handle.readString().split("\n");
            Label scoreLabel = new Label("High Score: " + score[0], BaseGame.labelStyle);
            uiTable.row();
            uiTable.add(scoreLabel).colspan(3).pad(20);
        } catch (com.badlogic.gdx.utils.GdxRuntimeException ex)
        {
            handle.writeString("0", false);
            String[] score = handle.readString().split("\n");
            Label scoreLabel = new Label("High Score: " + score[0], BaseGame.labelStyle);
            uiTable.row();
            uiTable.add(scoreLabel).colspan(3).pad(20);
        }

        //Label scoreLabel = new Label("High Score: "+score[0], BaseGame.labelStyle );
        BaseActor soundOffButton = new BaseActor(20, 20, uiStage);
        soundOffButton.loadTexture("assets/soundoff.png");
        soundOffButton.scaleBy((float) 0.8);
        soundOffButton.addListener((Event e) ->
        {
            if (!isTouchDownEvent(e))

            {
                return false;
            }
            backgroundMusic.pause();

            return true;
        });

        BaseActor soundOnButton = new BaseActor(740, 20, uiStage);
        soundOnButton.loadTexture("assets/soundon.png");
        soundOnButton.scaleBy((float) 0.8);
        soundOnButton.addListener((Event e) ->
        {
            if (!isTouchDownEvent(e))

            {
                return false;
            }
            backgroundMusic.play();

            return true;
        });
      }

    @Override
    public void update(float dt)
      {

      }
}
