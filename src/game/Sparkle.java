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
public class Sparkle extends BaseActor
{

    public Sparkle(float x, float y, Stage s)
      {
        super(x, y, s);
        loadAnimationFromSheet("assets/sparkle.png",8,8,0.02f,false);
      }
    
    public void act(float dt)
      {
        super.act(dt);
        
        if(isAnimationFinished())
            remove();
      }
    
}
