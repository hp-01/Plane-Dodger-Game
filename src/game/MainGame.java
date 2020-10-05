/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Harsh Pandey
 */
public class MainGame extends BaseGame
{
    @Override
    public void create()
      {
        super.create();
        this.setScreen(new LevelScreen());
      }

}
