/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 *
 * @author Harsh Pandey
 */
public abstract class BaseScreen implements Screen, InputProcessor
{

    protected Stage mainStage;
    protected Stage uiStage;
    protected Table uiTable;

    public BaseScreen()
      {
        mainStage = new Stage();
        uiStage = new Stage();
        
        uiTable = new Table();
        uiTable.setFillParent(true);
        uiStage.addActor(uiTable);
        initialize();
      }

    public abstract void initialize();

    public abstract void update(float dt);

    @Override
    public void render(float dt)
      {
        mainStage.act(dt);
        uiStage.act(dt);

        update(dt);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainStage.draw();
        uiStage.draw();
      }

    @Override
    public void show()
      {
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.addProcessor(this);
        im.addProcessor(mainStage);
        im.addProcessor(uiStage);
      }

    public boolean isTouchDownEvent(Event e)
      {
        return (e instanceof InputEvent) && ((InputEvent) e).getType().equals(Type.touchDown);
      }

    @Override
    public void resize(int width, int height)
      {

      }

    @Override
    public void pause()
      {

      }

    @Override
    public void resume()
      {

      }

    @Override
    public void hide()
      {
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.removeProcessor(this);
        im.removeProcessor(mainStage);
        im.removeProcessor(uiStage);
      }

    @Override
    public void dispose()
      {
        mainStage.dispose();
        uiStage.dispose();
      }

    @Override
    public boolean keyDown(int keycode)
      {
        return false;
      }

    @Override
    public boolean keyUp(int keycode)
      {
        return false;
      }

    @Override
    public boolean keyTyped(char character)
      {
        return false;
      }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
      {
        return false;
      }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
      {
        return false;
      }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
      {
        return false;
      }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
      {
        return false;
      }

    @Override
    public boolean scrolled(int amount)
      {
        return false;
      }

}
