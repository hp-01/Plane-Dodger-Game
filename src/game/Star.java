/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Harsh Pandey
 */
public class Star extends BaseActor
{

    public Star(float x, float y, Stage s)
      {
        super(x, y, s);
        loadTexture("assets/star.png");

        // pulsing animation to star
        Action pulse = Actions.sequence(
                Actions.scaleTo(1.2f, 1.2f, 0.5f),
                Actions.scaleTo(1.0f, 1.0f, 0.5f)
        );

        addAction(Actions.forever(pulse));

        setSpeed(100);
        setMotionAngle(180);
      }

    @Override
    public void act(float dt)
      {
        super.act(dt);
        applyPhysics(dt);

        // remove after moving past left edge of screen
        if (getX() + getWidth() < 0)
        {
            this.remove();
        }
      }
}
