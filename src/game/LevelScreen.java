/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LevelScreen extends BaseScreen
{

    Plane plane;

    float startTimer;
    float starSpawnInterval;
    int score;
    Label scoreLabel;

    float enemyTimer;
    float enemySpawnInterval;
    float enemySpeed;
    boolean gameOver;
    BaseActor gameOverMessage;

    Music backgroundMusic;
    Sound sparkleSound;
    Sound explosionSound;

    @Override
    public void initialize()
      {
        new Sky(0, 0, mainStage);
        new Sky(800, 0, mainStage);
        new Ground(0, 0, mainStage);
        new Ground(800, 0, mainStage);

        String[] mainPlanes =
        {
            "assets/planeGreen0.png", "assets/planeGreen1.png", "assets/planeGreen2.png", "assets/planeGreen0.png"
        };

        String[] planes = null;
        FileHandle handle = null;
        try
        {
            handle = new FileHandle("src/assets/plane.txt");
            planes = handle.readString().split("\n");
        } catch (com.badlogic.gdx.utils.GdxRuntimeException ex)
        {
            for (int i = 0; i <= 3; i++)
            {
                handle.writeString(mainPlanes[i] + "\n", true);
            }
            planes = mainPlanes;
        }
        // plane onto mainStage
        plane = new Plane(100, 500, mainStage, planes);
        BaseActor.setWorldBounds(800, 600);

        startTimer = 0;
        starSpawnInterval = 4;
        score = 0;
        scoreLabel = new Label(Integer.toString(score), BaseGame.labelStyle);
        uiTable.pad(10);
        uiTable.add(scoreLabel).expandX();
        BaseActor.setWorldBounds(800, 600);

        enemyTimer = 0;
        enemySpeed = 100;
        enemySpawnInterval = 3;

        BaseActor homeButton = new BaseActor(0, 0, uiStage);
        homeButton.loadTexture("assets/home.png");
        homeButton.setColor(Color.BLACK);

        homeButton.addListener(
                (Event e) ->
                {
                    if (!isTouchDownEvent(e))
                    {
                        return false;
                    }

                    backgroundMusic.dispose();
                    sparkleSound.dispose();
                    explosionSound.dispose();

                    MainGame.setActiveScreen(new MenuScreen());
                    return true;
                }
        );

        uiTable.pad(10);
        uiTable.add(homeButton).expandX();

        BaseActor retryButton = new BaseActor(0, 0, uiStage);
        retryButton.loadTexture("assets/retry.png");
        retryButton.setColor(Color.BLACK);

        retryButton.addListener(
                (Event e) ->
                {
                    if (!isTouchDownEvent(e))
                    {
                        return false;
                    }

                    backgroundMusic.dispose();
                    sparkleSound.dispose();
                    explosionSound.dispose();

                    MainGame.setActiveScreen(new LevelScreen());
                    return true;
                }
        );

        uiTable.pad(10);
        uiTable.add(retryButton).expandX();

        gameOver = false;
        gameOverMessage = new BaseActor(0, 0, uiStage);
        gameOverMessage.loadTexture("assets/game-over.png");
        gameOverMessage.setVisible(false);
        uiTable.row();
        uiTable.add(gameOverMessage).expandY().colspan(3);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/Prelude-and-Action.mp3"));
        sparkleSound = Gdx.audio.newSound(Gdx.files.internal("assets/sparkle.mp3"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("assets/explosion.wav"));

        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1.00f);
        backgroundMusic.play();
      }

    @Override
    public void update(float dt)
      {
        if (gameOver)
        {
            return;
        }

        enemyTimer += dt;

        // to spawn new enemy as well as gradually decreasing enemy spawn interval time
        if (enemyTimer > enemySpawnInterval)
        {
            Enemy enemy = new Enemy(800, MathUtils.random(100, 500), mainStage);
            enemy.setSpeed(enemySpeed);

            enemyTimer = 0;
            enemySpawnInterval -= 0.10f;
            enemySpeed += 10;

            if (enemySpawnInterval < 0.8f)
            {
                enemySpawnInterval = 0.8f;
            }

            if (enemySpeed > 400)
            {
                enemySpeed = 400;
            }
        }

        // to detect overlap with enemy
        for (BaseActor enemy : BaseActor.getList(mainStage, "Enemy"))
        {
            if (plane.overlaps(enemy))
            {
                plane.remove();

                Explosion ex = new Explosion(0, 0, mainStage);
                ex.centerAtActor(plane);
                ex.setScale(3);
                explosionSound.play();
                backgroundMusic.stop();

                FileHandle handle = null;
                try
                {
                    handle = new FileHandle("main.dd");
                    String[] previousScore = handle.readString().split("\n");
                    if (score > Integer.parseInt(previousScore[0]))
                    {
                        handle.writeString(Integer.toString(score), false);
                    }
                } catch (com.badlogic.gdx.utils.GdxRuntimeException e)
                {
                    handle.writeString("0", false);
                }

                try
                {
                    handle = new FileHandle("src/assets/sc.txt");
                    int i = Integer.parseInt(handle.readString().split("\n")[0]);
                    handle.writeString(String.valueOf(i + score) + "\n", false);
                } catch (com.badlogic.gdx.utils.GdxRuntimeException e)
                {
                    handle.writeString(String.valueOf(score) + "\n", false);
                    handle = new FileHandle("main.dd");
                    handle.writeString(String.valueOf(score), false);
                }

                gameOver = true;
                gameOverMessage.setVisible(true);
            }

            if (enemy.getX() + enemy.getWidth() < 0)
            {
                score++;
                scoreLabel.setText(Integer.toString(score));
                enemy.remove();
            }
        }

        startTimer += dt;

        // to spawn new star at around 4 second interval
        if (startTimer > starSpawnInterval)
        {
            new Star(800, MathUtils.random(100, 500), mainStage);
            startTimer = 0;
        }

        // to detect collision with star
        for (BaseActor star : BaseActor.getList(mainStage, "Star"))
        {
            if (plane.overlaps(star))
            {
                star.remove();

                Sparkle sp = new Sparkle(0, 0, mainStage);
                sp.centerAtActor(star);
                sparkleSound.play();

                score++;
                scoreLabel.setText(Integer.toString(score));
            }
        }

      }

    @Override
    public boolean keyDown(int keyCode)
      {
        if (keyCode == Keys.SPACE || keyCode == Keys.UP)
        {
            if (keyCode == Keys.SPACE || keyCode == Keys.UP)
            {
                plane.boost();
            }
        }
        return true;
      }

    @Override
    public void dispose()
      {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

    public void pause()
      {

      }
}
