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
public class Enemy extends BaseActor
{

    public Enemy(float x, float y, Stage s)
      {
        super(x, y, s);
        String[] filenames =
        {
            "assets/planeRed0.png", "assets/planeRed1.png", "assets/planeRed2.png", "assets/planeRed1.png"
        };
        loadAnimationFromFiles(filenames,0.1f,true);
        
        setSpeed(100);
        setMotionAngle(180);
        setBoundaryPolygon(8);
      }
    
    public void act(float dt)
      {
        super.act(dt);
        applyPhysics(dt);
      }

}
