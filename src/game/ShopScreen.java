/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

/**
 *
 * @author Harsh Pandey
 */
public class ShopScreen extends BaseScreen
{

    FileHandle handle = null;
    String[] s;
    boolean[] previousPurchased;
    int len = 0;
    Label scoreLabel;
    Label messageLabel;
    String totalScore;

    @Override
    public void initialize()
      {
        new Sky(0, 0, mainStage);
        new Sky(800, 0, mainStage);
        new Ground(0, 0, mainStage);
        new Ground(800, 0, mainStage);

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

        String[] planeColor =
        {
            "Green", "Brown", "Blue", "Grey", "Pink", "Yellow", "Special"
        };

        String[] price =
        {
            "0", "2000", "3000", "4000", "5000", "6000", "10000"
        };

        boolean[] defaultPurchased =
        {
            true, false, false, false, false, false, false
        };

        try
        {
            handle = new FileHandle("src/assets/purchase.txt");
            s = handle.readString().split("\n");
            len = s.length;
            previousPurchased = new boolean[s.length];
            for (int i = 0; i < s.length; i++)
            {
                previousPurchased[i] = Boolean.parseBoolean(s[i]);
            }
        } catch (com.badlogic.gdx.utils.GdxRuntimeException ex)
        {
            for (int i = 0; i < defaultPurchased.length; i++)
            {
                handle.writeString((String.valueOf(defaultPurchased[i]) + "\n"), true);
            }
            len = handle.readString().split("\n").length;
        }

        Table t2 = new Table();
        t2.pad(50);
        for (int row = 0; row < len; row++)
        {
            t2.add(shopItem(planes[row], price[row], row, planeColor[row])).expandX().pad(20);
            t2.row();
        }
        ScrollPane s2Pane = new ScrollPane(t2);
        s2Pane.setForceScroll(false, true);
        s2Pane.setBounds(0, 0, 800, 600);
        uiTable.add(s2Pane).expand();

        try
        {
            handle = new FileHandle("src/assets/sc.txt");
            totalScore = handle.readString().split("\n")[0];
        } catch (com.badlogic.gdx.utils.GdxRuntimeException e)
        {
            handle.writeString("0", false);
            totalScore = "0";
        }

        scoreLabel = new Label(totalScore, BaseGame.labelStyle);
        messageLabel = new Label("Hello",BaseGame.labelStyle);
        Table t = new Table();
        t.setBounds(0, 0, 800, 600);
        t.add(messageLabel).align(Align.left).align(Align.topLeft).pad(50).expandX();
        t.add(scoreLabel).align(Align.right).align(Align.topRight).pad(50).expandX();
        t.row();
        t.add().colspan(2).expandY();
        mainStage.addActor(t);
      }

    @Override
    public void update(float dt)
      {

      }

    public Table shopItem(String planeName, String price, int row, String planeColor)
      {
        BaseActor item = new BaseActor(0, 0, uiStage);
        item.loadTexture("assets/" + planeName);
        item.addListener((Event e) ->
        {
            if (!isTouchDownEvent(e))
            {
                return false;
            }
            int p = Integer.parseInt(price);
            int s = Integer.parseInt(totalScore);
            if (previousPurchased[row] == false && Integer.parseInt(totalScore) >= Integer.parseInt(price))
            {
                int score = s - p;
                handle = new FileHandle("src/assets/sc.txt");
                handle.writeString(String.valueOf(score), false);
                handlePlaneFile(planeColor);
                totalScore = String.valueOf(score);
                previousPurchased[row] = true;
                handle = new FileHandle("src/assets/purchase.txt");
                handle.writeString("", false);
                for (int i = 0; i < previousPurchased.length; i++)
                {
                    handle.writeString((String.valueOf(previousPurchased[i]) + "\n"), true);
                }
                len = handle.readString().split("\n").length;
                scoreLabel.setText(totalScore);
                messageLabel.setText("Selected\n"+planeColor+"\nPlane");
                return true;
            }
            if (previousPurchased[row] == true)
            {
                handlePlaneFile(planeColor);
                messageLabel.setText("Selected\n"+planeColor+"\nPlane");
                return true;
            }
            messageLabel.setText("Not\nEnough\nPoints");
            return true;
        });
        Label label = new Label(price, BaseGame.labelStyle);
        Table table = new Table();
        table.add(item);
        table.row();
        table.add(label);
        return table;
      }

    public void handlePlaneFile(String planeColor)
      {
        FileHandle handle = null;
        try
        {
            handle = new FileHandle("src/assets/plane.txt");
            handle.writeString("", false);
            for (int i = 0; i <= 2; i++)
            {
                handle.writeString("assets/plane" + planeColor + i + ".png\n", true);
            }
            handle.writeString("assets/plane" + planeColor + "0.png\n", true);

        } catch (com.badlogic.gdx.utils.GdxRuntimeException ex)
        {
            System.out.println(ex);
        }
      }
}
