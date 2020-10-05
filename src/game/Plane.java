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

    public Plane(float x, float y, Stage s)
      {
        super(x, y, s);
        String[] fileNames = {
            "assets/planeGreen0.png",
            "assets/planeGreen1.png",
            "assets/planeGreen2.png",
            "assets/planeGreen0.png"
        };
        
        this.loadAimationFromFiles(fileNames, 0.1f, true);
        
        setMaxSpeed(800);
        setBoundaryPolygon(8);
      }
    
    @Override
    public void act(float dt)
      {
        super.act(dt);
        
        //simulate force of gravity
        setAcceleration(800);
        accelerateAtAngle(270);
        applyPhysics(dt);
        
        //stop plane from passing through the ground
        for(BaseActor g : BaseActor.getList(this.getStage(), "Ground"))
        {
            if(overlaps(g))
            {
                setSpeed(0);
                preventOverlap(g);
            }
        }
        
        //stop plane from moving past top of screen
        if(getY() + this.getHeight() > this.getWorldBounds().height)
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
