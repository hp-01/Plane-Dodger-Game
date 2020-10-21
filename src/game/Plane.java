/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Harsh Pandey
 */
public class Plane extends BaseActor
{

    public Plane(float x, float y, Stage s, String[] planes)
      {
        super(x, y, s);

        loadAnimationFromFiles(planes, 0.1f, true);
        setMaxSpeed(800);
        setBoundaryPolygon(8);
      }

    @Override
    public void act(float dt)
      {
        super.act(dt);

        // simulate force of gravity
        setAcceleration(800);
        accelerateAtAngle(270);
        applyPhysics(dt);

        // stop plane from passing through the ground
        for (BaseActor g : BaseActor.getList(this.getStage(), "Ground"))
        {
            if (this.overlaps(g))
            {
                setSpeed(0);
                preventOverlap(g);
            }
        }

        // stop plane from moving past top of screen
        if (getY() + getHeight() > getWorldBounds().height)
        {
            setSpeed(0);
            boundToWorld();
        }

      }

    public void boost()
      {
        setSpeed(300);
        setMotionAngle(90);
      }

}
