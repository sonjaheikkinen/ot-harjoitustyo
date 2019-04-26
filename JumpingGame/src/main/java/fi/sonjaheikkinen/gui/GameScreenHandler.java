
package fi.sonjaheikkinen.gui;

import fi.sonjaheikkinen.domain.GameObject;
import fi.sonjaheikkinen.logic.GameLogic;
import fi.sonjaheikkinen.logic.ProgramLogic;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import fi.sonjaheikkinen.other.LongValue;
import javafx.scene.text.Text;

/**
 * Luokka ohjaa ruudulla näkyviä elementtejä pelin ollessa käynnissä.
 */
public class GameScreenHandler {

    private Scene game;
    private Canvas canvas;
    private GameLogic gLogic;
    private StageHandler stageHandler;
    private Text gameInfo;
    private ProgramLogic pLogic;
    private GraphicsContext gc;

    public GameScreenHandler(Canvas canvas, Scene scene, Text gameInfo, StageHandler handler, ProgramLogic pLogic) {
        this.game = scene;
        this.canvas = canvas;
        this.gLogic = new GameLogic();
        this.stageHandler = handler;
        this.gameInfo = gameInfo;
        this.pLogic = pLogic;
        this.gc = canvas.getGraphicsContext2D();
        this.gLogic.createGameObjects();
    }

    /**
     * Metodi kutsuu metodeita handleMouseMovement, joka hoitaa hiiren liikkeiden kuuntelun, ja handleAnimation, joka 
     * hoitaa pelinäkymän päivittämisen ruudulle.
     */
    public void updateGame() {
        handleMouseMovement();
        handleAnimation();
    }

    /**
     * Metodi kuuntelee käyttäjän hiiren liikkeitä ja siirtelee ruudulla
     * liikkuvaa pelihahmoa niiden mukaisesti sivusuunnassa.
     */
    public void handleMouseMovement() {
        this.game.setOnMouseMoved((event) -> {
            double mouseX = event.getSceneX();
            this.gLogic.getGameCharacter().setPositionX(mouseX);
        });
    }

    /**
     * Metodi luo AnimationTimer-olion ja kutsuu sen metodia handle, joka vastaa
     * GameObject-olioden sijainnista suhteessa kuluneeseen aikaan.
     * AnimationTimer pysähtyy ja kutsuu StopGame-metodia pelaajan tipahtaessa
     * peliruudun alapuolelle. Muuna aikana se kutsuu säännöllisin väliajoin
     * metodia updateGameScreen, joka hoitaa varsinaisen peliruudun
     * päivittämisen.
     */
    public void handleAnimation() {
        LongValue lastNanoTime = new LongValue(System.nanoTime());
        GameObject character = this.gLogic.getGameCharacter();
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (character.getPositionY() > 500) {
                    stopGame();
                    this.stop();
                } else {
                    updateGameScreen(currentNanoTime, lastNanoTime);
                }
            }
        }.start();
    }

    /**
     * Metodi hoitaa pelitietojen päivittämisen pelin päättyessä. Se kutsuu
     * ProgramLogicin metodeita päivittämään pisteet ja high score -listauksen,
     * sekä StageHandlerin metodia GameOver, joka valitsee ruudulle
     * näytettäväksi seuraavan skenaarion pelin päättyessä.
     */
    public void stopGame() {
        this.pLogic.updatePoints(this.gLogic);
        this.pLogic.updateHighScore();
        this.stageHandler.gameOver();
    }

    /**
     * Metodi päivittää peliruudun näkymää. Aluksi päivitetään pelitiedot kutsumalla gameLogicin metodia updateGame. 
     * Sen jälkeen ruudulle päivitetään pistetiedot ja GameObjectit piirretään ruudulle kutsumalla metodia render.
     *
     * @param currentNanoTime tämänhetkinen aika nanosekunneissa
     * @param lastNanoTime edellisen päivityksen aika nanosekunneissa
     */
    public void updateGameScreen(long currentNanoTime, LongValue lastNanoTime) {
        this.gLogic.updateGame(currentNanoTime, lastNanoTime);
        this.gameInfo.setText("Points: \n" + this.gLogic.getPoints() + "\nLives: \n" + this.gLogic.getLives());
        render();
    }

    /**
     * Metodi määrittää piirtovärin valkoiseksi, tyhjentää ruudun, ja kutsuu
     * sitten muita metodeja, jotka maalaavat pelissä mukana olevat
     * GameObject-oliot ikkunaan.
     */
    public void render() {
        this.gc.setFill(javafx.scene.paint.Color.WHITE);
        this.gc.clearRect(0, 0, 400, 500);
        renderCharacter();
        renderPlatforms();
        renderTraps();
        renderBoost();
    }

    /**
     * Metodi maalaa liikutettavan pelihahmon kohdalle neliön, jonka vasen
     * yläkulma on olion kulloisenkin sijainnin mukainen, ja koko vastaa oliolle
     * määriteltyä leveyttä ja korkeutta. Jos pelihahmon sijainti on ruudun
     * sivureunojen ulkopuolella, maalataan hahmo lähimpään reunaan.
     */
    public void renderCharacter() {
        GameObject character = this.gLogic.getGameCharacter();
        double characterX = character.getPositionX();
        if (characterX < 0) {
            characterX = 0;
        } else if (characterX > 360) {
            characterX = 360;
        }
        gc.fillRect(characterX, character.getPositionY(), character.getWidth(), character.getHeight());
    }

    /**
     * Metodi käy läpi listan hyppyalustoja, ja maalaa niiden sijaintia ja kokoa
     * vastaavat nelikulmiot ruudulle.
     */
    public void renderPlatforms() {
        for (GameObject platform : this.gLogic.getPlatforms()) {
            this.gc.fillRect(platform.getPositionX(), platform.getPositionY(), platform.getWidth(), platform.getHeight());
        }
    }

    /**
     * Metodi käy läpi ansalistan, ja maalaa jokaisen ansan kohdalle ympyrän,
     * jonka koko ja sijainti vastaavat ansan kokoa ja sijaintia.
     */
    public void renderTraps() {
        for (GameObject trap : this.gLogic.getTraps()) {
            this.gc.fillOval(trap.getPositionX(), trap.getPositionY(), trap.getWidth(), trap.getHeight());
        }
    }

    /**
     * Metodi maalaa boostin kohdalle boostin sijaintia ja kokoa vastaavan
     * ylöspäinosoittavan kolmion.
     */
    public void renderBoost() {
        GameObject boost = this.gLogic.getBoost();
        double posX = boost.getPositionX();
        double posY = boost.getPositionY();
        double w = boost.getWidth();
        double h = boost.getHeight();
        this.gc.fillPolygon(new double[]{posX, posX + w, posX + (w / 2)}, new double[]{posY, posY, posY - h}, 3);
    }

}
