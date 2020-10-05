/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 *
 * @author Harsh Pandey
 */
public class LevelScreen extends BaseScreen
{

    Plane plane;
    @Override
    public void initialize()
      {
        new Sky(0, 0, mainStage);
        new Sky(800, 0, mainStage);
        new Ground(0, 0, mainStage);
        new Ground(800, 0, mainStage);
        
        // plane onto mainStage
        plane = new Plane(100,500,mainStage);
        BaseActor.setWorldBounds(800,600);
      }

    @Override
    public void update(float dt)
      {
         
      }

    @Override
    public boolean keyDown(int keyCode)
      {
        if(keyCode == Keys.SPACE || keyCode == Keys.UP)
            plane.boost();
        return true;
      }
}
