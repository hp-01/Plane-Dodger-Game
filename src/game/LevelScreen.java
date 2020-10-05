/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LevelScreen extends BaseScreen
{

    Plane plane;

    float startTimer;
    float starSpawnInterval;
    int score;
    Label scoreLabel;

    @Override
    public void initialize()
      {
        new Sky(0, 0, mainStage);
        new Sky(800, 0, mainStage);
        new Ground(0, 0, mainStage);
        new Ground(800, 0, mainStage);

        // plane onto mainStage
        plane = new Plane(100, 500, mainStage);
        BaseActor.setWorldBounds(800, 600);

        startTimer = 0;
        starSpawnInterval = 4;
        score = 0;
        scoreLabel = new Label(Integer.toString(score), BaseGame.labelStyle);
        uiTable.pad(10);
        uiTable.add(scoreLabel);
        uiTable.row();
        uiTable.add().expandY();
        BaseActor.setWorldBounds(800, 600);
      }

    @Override
    public void update(float dt)
      {
        startTimer += dt;

        // to spawn new star at around 4 second interval
        if (startTimer > starSpawnInterval)
        {
            new Star(800, MathUtils.random(100, 500), mainStage);
            startTimer = 0;
        }

        // to detect collision with star
        for (BaseActor star : BaseActor.getList(mainStage, "Star"))
        {
            if (plane.overlaps(star))
            {
                star.remove();
                score++;
                scoreLabel.setText(Integer.toString(score));
            }
        }

      }

    @Override
    public boolean keyDown(int keyCode)
      {
        if (keyCode == Keys.SPACE || keyCode == Keys.UP)
        {
            if (keyCode == Keys.SPACE || keyCode == Keys.UP)
            {
                plane.boost();
            }
        }
        return true;
      }

    @Override
    public void dispose()
      {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

    public void pause()
      {
        
      }
}
