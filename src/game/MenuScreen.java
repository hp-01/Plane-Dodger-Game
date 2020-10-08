/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.badlogic.gdx.Gdx;
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
            uiTable.add(title).colspan(2).pad(50);

            TextButton startButton = new TextButton("Start", BaseGame.textButtonStyle);
            startButton.addListener((Event e) ->
            {
                if (!isTouchDownEvent(e))
                {
                    return false;
                }
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
            
      }

    @Override
    public void update(float dt)
      {

      }
}
