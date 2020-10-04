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
public class Sky extends BaseActor
{

    public Sky(float x, float y, Stage s)
      {
        super(x, y, s);
        loadTexture("assets/sky.png");
        setSpeed(25);
        setMotionAngle(180);
      }
    
    @Override
    public void act(float dt)
      {
        super.act(dt);
        applyPhysics(dt);
        
        //if moved completely past left edge of screen
        //shift right, past other instance
        if(getX() + getWidth() < 0)
        {
            moveBy(2 * getWidth(),0);
        }
      }
    
}
