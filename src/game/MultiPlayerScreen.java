package game;

import org.json.simple.parser.ParseException;
import org.java_websocket.handshake.ServerHandshake;
import java.util.Map;
import org.java_websocket.drafts.Draft;
import com.badlogic.gdx.files.FileHandle;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.java_websocket.client.WebSocketClient;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.net.URI;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import java.util.ArrayList;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import org.json.simple.JSONObject;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class MultiPlayerScreen extends BaseScreen {

    Plane myPlane;
    Plane enemyPlane;
    float startTimer;
    float starSpawnInterval;
    int score;
    Label scoreLabel;
    float enemyTimer;
    float enemySpawnInterval;
    float enemySpeed;
    BaseActor gameOverMessage;
    Music backgroundMusic;
    Sound sparkleSound;
    Sound explosionSound;
    boolean gameOver;
    boolean messageScreen;
    private String myID;
    private String otherID;
    private boolean start;
    private float ping;
    private ExampleClient c;
    private JSONObject obj;
    Label myIDLabel;
    Label messageLabel;
    Table table;
    ArrayList<String> planeList;
    ArrayList<String> starList;
    private int enemyPlaneNo;
    private int starNo;
    private int COUNT;
    private float waitTime;

    @Override
    public void initialize() {
        new Sky(0.0f, 0.0f, mainStage);
        new Sky(800.0f, 0.0f, mainStage);
        new Ground(0.0f, 0.0f, mainStage);
        new Ground(800.0f, 0.0f, mainStage);
        final String[] myPlanes = {"assets/planeGreen0.png", "assets/planeGreen1.png", "assets/planeGreen2.png", "assets/planeGreen0.png"};
        final String[] enemyPlanes = {"assets/planeBlue0.png", "assets/planeBlue1.png", "assets/planeBlue2.png", "assets/planeBlue0.png"};
        enemyPlane = new Plane(90.0f, 500.0f, mainStage, enemyPlanes);
        myPlane = new Plane(100.0f, 500.0f, mainStage, myPlanes);
        BaseActor.setWorldBounds(800.0f, 600.0f);
        startTimer = 0.0f;
        starSpawnInterval = 4.0f;
        score = 0;
        enemyTimer = 0.0f;
        enemySpeed = 100.0f;
        enemySpawnInterval = 3.0f;
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/Prelude-and-Action.mp3"));
        sparkleSound = Gdx.audio.newSound(Gdx.files.internal("assets/sparkle.mp3"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("assets/explosion.wav"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1.0f);
        backgroundMusic.play();
        scoreLabel = new Label(Integer.toString(score), BaseGame.labelStyle);
        final BaseActor homeButton = new BaseActor(0.0f, 0.0f, uiStage);
        homeButton.loadTexture("assets/home.png");
        homeButton.setColor(Color.BLACK);
        homeButton.addListener(e -> {
            if (!isTouchDownEvent(e)) {
                return false;
            } else {
                backgroundMusic.dispose();
                sparkleSound.dispose();
                explosionSound.dispose();
                BaseGame.setActiveScreen(new MenuScreen());
                c.close();
                return true;
            }
        });
        final BaseActor retryButton = new BaseActor(0.0f, 0.0f, uiStage);
        retryButton.loadTexture("assets/retry.png");
        retryButton.setColor(Color.BLACK);
        retryButton.addListener(e -> {
            if (!isTouchDownEvent(e)) {
                return false;
            } else {
                backgroundMusic.dispose();
                sparkleSound.dispose();
                explosionSound.dispose();
                BaseGame.setActiveScreen(new MultiPlayerScreen());
                c.close();
                return true;
            }
        });
        uiTable.add(scoreLabel).expandX();
        uiTable.add(homeButton).expandX();
        uiTable.add(retryButton).expandX();
        planeList = new ArrayList<String>();
        starList = new ArrayList<String>();
        ping = 0.0f;
        (obj = new JSONObject()).put("messageType", "");
        myID = "";
        otherID = "";
        start = false;
        enemyPlaneNo = 0;
        starNo = 0;
        COUNT = 0;
        try {
            (c = new ExampleClient(new URI("wss://ws-connection-heroku.herokuapp.com/"))).connect();
        } catch (Exception e2) {
            System.out.println(e2);
        }
        table = new Table();
        myIDLabel = new Label("Your ID: " + myID, BaseGame.labelStyle);
        table.add(myIDLabel).expandX().align(1).colspan(2);
        messageLabel = new Label("", BaseGame.labelStyle);
        final Label otherLabel = new Label("Other ID: ", BaseGame.labelStyle);
        final Skin skin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));
        final TextField field = new TextField("", skin);
        field.setText("");
        field.addListener(new InputListener() {
            @Override
            public boolean keyUp(final InputEvent event, final int keycode) {
                otherID = field.getText();
                if (keycode == 66) {
                    obj.put("messageType", "REQUEST");
                    obj.put("myID", myID.trim());
                    obj.put("otherID", otherID.trim());
                    c.send(obj.toJSONString());
                    obj.clear();
                    messageLabel.setText("REUEST SENT TO " + otherID);
                    return true;
                }
                return false;
            }
        });
        table.row();
        table.add(otherLabel).expandX().align(1);
        table.add(field).expandX().align(1);
        table.row();
        table.add(messageLabel).expandX().align(1).colspan(2);
        final TextButton acceptButton = new TextButton("Accept", BaseGame.textButtonStyle);
        acceptButton.addListener(e -> {
            if (!isTouchDownEvent(e)) {
                return false;
            } else if (!otherID.equals("")) {
                obj.put("messageType", "ACCEPT");
                obj.put("myID", myID.trim());
                obj.put("otherID", otherID.trim());
                c.send(obj.toJSONString());
                obj.clear();
                messageLabel.setText("REQUEST ACCEPETED OF" + otherID);
                return true;
            } else {
                return false;
            }
        });
        final TextButton rejectButton = new TextButton("Reject", BaseGame.textButtonStyle);
        rejectButton.addListener(e -> {
            if (!isTouchDownEvent(e)) {
                return false;
            } else if (!otherID.equals("")) {
                obj.put("messageType", "REJECT");
                obj.put("myID", myID.trim());
                obj.put("otherID", otherID.trim());
                c.send(obj.toJSONString());
                obj.clear();
                messageLabel.setText("REQUEST REJECTED " + otherID);
                return true;
            } else {
                return false;
            }
        });
        table.row();
        table.add(acceptButton).expandX().align(1);
        table.add(rejectButton).expandX().align(1);
        uiTable.row();
        uiTable.add(table).colspan(3).pad(20.0f).expand();
    }

    @Override
    public void update(final float dt) {
        ping += dt;
        if (ping >= 4.0f) {
            try {
                c.send(obj.toJSONString());
            } catch (Exception ex2) {
                messageLabel.setText("Not connected to server");
            }
            obj.clear();
            ping = 0.0f;
        }
        if (!start) {
            return;
        }
        myPlane.setVisible(true);
        enemyPlane.setVisible(true);
        enemyTimer += dt;
        if (enemyTimer > enemySpawnInterval && !planeList.isEmpty()) {
            final Enemy enemy = new Enemy(800.0f, Float.parseFloat(planeList.get(enemyPlaneNo)), mainStage);
            enemy.setSpeed(enemySpeed);
            ++enemyPlaneNo;
            enemyTimer = 0.0f;
            enemySpawnInterval -= 0.1f;
            enemySpeed += 10.0f;
            if (enemySpawnInterval < 0.7f) {
                enemySpawnInterval = 0.7f;
            }
            if (enemySpeed > 400.0f) {
                enemySpeed = 400.0f;
            }
        }
        for (final BaseActor enemy2 : BaseActor.getList(mainStage, "Enemy")) {
            if (myPlane.overlaps(enemy2)) {
                final Explosion ex = new Explosion(0.0f, 0.0f, mainStage);
                ex.centerAtActor(myPlane);
                ex.setScale(3.0f);
                explosionSound.play();
                backgroundMusic.stop();
                start = false;
                startTimer = 0.0f;
                starSpawnInterval = 4.0f;
                enemyTimer = 0.0f;
                enemySpeed = 100.0f;
                enemySpawnInterval = 3.0f;
                obj.clear();
                obj.put("messageType", "LOST");
                obj.put("myID", myID.trim());
                obj.put("otherID", otherID.trim());
                c.send(obj.toJSONString());
                messageLabel.setText("YOU LOST");
                table.setVisible(true);
            }
            if (enemy2.getX() + enemy2.getWidth() < 0.0f) {
                ++score;
                scoreLabel.setText(Integer.toString(score));
                enemy2.remove();
            }
        }
        startTimer += dt;
        if (startTimer > starSpawnInterval && !starList.isEmpty()) {
            new Star(800.0f, Float.parseFloat(starList.get(starNo)), mainStage);
            startTimer = 0.0f;
            ++starNo;
        }
        for (final BaseActor star : BaseActor.getList(mainStage, "Star")) {
            if (myPlane.overlaps(star)) {
                star.remove();
                final Sparkle sp = new Sparkle(0.0f, 0.0f, mainStage);
                sp.centerAtActor(star);
                sparkleSound.play();
                ++score;
                scoreLabel.setText(Integer.toString(score));
            }
        }
    }

    @Override
    public boolean keyDown(final int keyCode) {
        if ((keyCode == 62 || keyCode == 19) && (keyCode == 62 || keyCode == 19)) {
            myPlane.boost();
            obj.clear();
            obj.put("messageType", "KEYSTROKE");
            obj.put("myID", myID.trim());
            obj.put("otherID", otherID.trim());
            try {
                c.send(obj.toJSONString());
            } catch (Exception ex) {
                System.out.println("KeyStroke not send");
            }
        }
        return true;
    }

    public class ExampleClient extends WebSocketClient {

        JSONParser parser;
        JSONObject obj;
        JSONArray jArray;
        FileHandle handle;

        public ExampleClient(final URI serverUri, final Draft draft) {
            super(serverUri, draft);
            handle = null;
        }

        public ExampleClient(final URI serverURI) {
            super(serverURI);
            handle = null;
        }

        public ExampleClient(final URI serverUri, final Map<String, String> httpHeaders) {
            super(serverUri, httpHeaders);
            handle = null;
        }

        @Override
        public void onOpen(final ServerHandshake handshakedata) {
            System.out.println("opened connection");
        }

        @Override
        public void onMessage(final String message) {
            parser = new JSONParser();
            obj = null;
            try {
                obj = (JSONObject) parser.parse(message);
            } catch (NullPointerException ex) {
                System.out.println(ex);
            } catch (ParseException ex2) {
                System.out.println("Parsing error");
            }
            if (obj.get("myID") != null) {
                myID = obj.get("myID").toString();
                myIDLabel.setText("Your ID: " + myID);
            } else if (obj.get("messageType").toString().equals("REQUESTED")) {
                if (obj.get("otherID") != null) {
                    otherID = obj.get("otherID").toString();
                }
                messageLabel.setText("REQUEST FROM " + otherID);
            } else if (obj.get("messageType").toString().equals("ACCEPTED")) {
                if (obj.get("otherID") != null) {
                    otherID = obj.get("otherID").toString();
                }
                jArray = (JSONArray) obj.get("plane");
                if (jArray != null) {
                    for (int i = 0; i < jArray.size(); ++i) {
                        planeList.add(jArray.get(i).toString());
                    }
                }
                jArray.clear();
                messageLabel.setText("ACCEPTED BY " + otherID);
            } else if (obj.get("messageType").toString().equals("REJECTED")) {
                if (obj.get("otherID") != null) {
                    otherID = obj.get("otherID").toString();
                }
                messageLabel.setText("REJECTED BY " + otherID);
            } else if (obj.get("messageType").toString().equals("PLANE")) {
                jArray = (JSONArray) obj.get("plane");
                if (jArray != null) {
                    for (int i = 0; i < jArray.size(); ++i) {
                        planeList.add(jArray.get(i).toString());
                    }
                }
                jArray.clear();
                jArray = (JSONArray) obj.get("star");
                if (jArray != null) {
                    for (int i = 0; i < jArray.size(); ++i) {
                        starList.add(jArray.get(i).toString());
                    }
                }
                jArray.clear();
            } else if (obj.get("messageType").toString().equals("WIN")) {
                obj.clear();
                obj.put("messageType", "WON");
                obj.put("myID", myID.trim());
                obj.put("otherID", otherID.trim());
                c.send(obj.toJSONString());
                System.out.println("REQUEST SENT OF WON PROPERLY");
                start = false;
                startTimer = 0.0f;
                starSpawnInterval = 4.0f;
                enemyTimer = 0.0f;
                enemySpeed = 100.0f;
                enemySpawnInterval = 3.0f;
                messageLabel.setText("YOU WON");
                table.setVisible(true);
            } else if (obj.get("messageType").toString().equals("KEYSTROKE")) {
                enemyPlane.boost();
            } else if (obj.get("messageType").toString().equals("START") && !start) {
                table.setVisible(false);
                start = true;
            }
            obj.clear();
        }

        @Override
        public void onClose(final int code, final String reason, final boolean remote) {
            System.out.println("Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: " + reason);
        }

        @Override
        public void onError(final Exception ex) {
            ex.printStackTrace();
        }
    }
}
