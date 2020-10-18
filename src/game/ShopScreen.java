/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 *
 * @author Harsh Pandey
 */
public class ShopScreen extends BaseScreen
{

    @Override
    public void initialize()
      {
        new Sky(0, 0, mainStage);
        new Sky(800, 0, mainStage);
        new Ground(0, 0, mainStage);
        new Ground(800, 0, mainStage);

        /*   Label title = new Label("SHOP", BaseGame.labelStyle);
         title.setFontScale((float) 1.5);
         title.setColor(Color.ORANGE);
         uiTable.add(title).colspan(3).pad(50);*/
        BaseActor soundOffButton = new BaseActor(20, 20, uiStage);
        soundOffButton.loadTexture("assets/home.png");
        soundOffButton.setColor(Color.BLACK);
        soundOffButton.scaleBy((float) 0.8);
        soundOffButton.addListener((Event e) ->
        {
            if (!isTouchDownEvent(e))

            {
                return false;
            }

            MainGame.setActiveScreen(new MenuScreen());
            return true;
        });

        String[] planes =
        {
            "planeGreen0.png", "planeBrown0.png", "planeBlue0.png", "planeGrey0.png", "planePink0.png", "planeYellow0.png", "planeSpecial0.png"
        };

        String[] price =
        {
            "1000", "2000", "3000", "4000", "5000", "6000", "10000"
        };
        Table t2 = new Table();
        t2.pad(50);
        for (int row = 0; row < 7; row++)
        {
            t2.add(shopItem(planes[row], price[row])).expandX();
            t2.row();
        }
        ScrollPane s2Pane = new ScrollPane(t2);
        s2Pane.setForceScroll(false, true);
        s2Pane.setBounds(0, 0, 800, 600);
        uiTable.add(s2Pane).expand();
        //this.uiStage.addActor(s2Pane);
      }

    @Override
    public void update(float dt)
      {

      }

    public Table shopItem(String planeName, String price)
      {
        BaseActor item = new BaseActor(0, 0, uiStage);
        item.loadTexture("assets/"+planeName);
        item.addListener((Event e) ->
        {
            if(!isTouchDownEvent(e))
                return false;
            System.out.println(planeName + price);
            return true;
        });
        Label label = new Label(price,BaseGame.labelStyle);
        label.addListener((Event e) ->
        {
            if(!isTouchDownEvent(e))
                return false;
            System.out.println(planeName + price);
            return true;
        });
        Table table = new Table();
        table.add(item);
        table.row();
        table.add(label);
        return table;
      }
}
