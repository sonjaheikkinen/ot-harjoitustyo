package fi.sonjaheikkinen.gui;

import fi.sonjaheikkinen.logic.ProgramLogic;
import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Luokka luo kaikki ohjelman toimintaan tarvittavat Scene-oliot
 */
public class SceneConstructor {

    private Scene LoginOrRegister;
    private Scene loginScene;
    private Scene registerScene;
    private Scene startScene;
    private Scene game;
    private Scene gameOver;
    private Scene chooseHighScore;
    private Scene highScores;
    private Scene instructionScene;
    private final StageHandler handler;
    private final ProgramLogic pLogic;
    private final File f;

    public SceneConstructor(StageHandler handler, ProgramLogic pInfo) {
        this.handler = handler;
        this.pLogic = pInfo;
        this.f = new File("stylesheet.css");
    }

    /**
     * Metodi luo koko ajan samanlaisina pysyvät Scene-oliot
     */
    public void createScenes() {
        createLoginOrRegister();
        createStartScene();
        createInstructionScene();
    }

    /**
     * Metodi luo scene-olion, joka sisältää napit login ja register.
     */
    public void createLoginOrRegister() {

        BorderPane layout = new BorderPane();

        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);

        Button login = new Button("Login");
        Button register = new Button("Create new User");

        buttons.getChildren().addAll(login, register);

        login.setOnAction(event -> {
            createLoginScene();
            this.handler.setScene(this.loginScene);
        });

        register.setOnAction(event -> {
            createRegisterScene();
            this.handler.setScene(this.registerScene);
        });

        layout.setCenter(buttons);

        Scene scene = new Scene(layout);
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + this.f.getAbsolutePath().replace("\\", "/"));
        this.LoginOrRegister = scene;

    }

    /**
     * Metodi luo scene-olion, johon käyttäjä voi syöttää kirjautumistiedot.
     */
    public void createLoginScene() {

        BorderPane layout = new BorderPane();

        Text name = new Text("Username: ");
        TextField nameField = new TextField();

        Text password = new Text("Password: ");
        PasswordField passwordField = new PasswordField();

        GridPane nameAndPassword = new GridPane();
        nameAndPassword.setAlignment(Pos.CENTER);
        nameAndPassword.addRow(0, name, nameField);
        nameAndPassword.addRow(1, password, passwordField);

        Text infoText = new Text("");

        Button login = new Button("login");
        login.setOnAction(event -> {
            String infoTextString = this.pLogic.checkUserInfo(nameField.getText(), passwordField.getText());
            if (infoTextString.equals("true")) {
                infoText.setText("");
                this.pLogic.setCurrentPlayer(nameField.getText());
                this.handler.setScene(this.startScene);
            } else {
                infoText.setText(infoTextString);
            }
        });

        VBox fieldsAndButton = new VBox();
        fieldsAndButton.getChildren().addAll(nameAndPassword, infoText, login);
        fieldsAndButton.setAlignment(Pos.CENTER);

        Button back = new Button("Back");
        back.setOnAction((event) -> {
            this.handler.setScene(this.LoginOrRegister);
        });

        VBox bottom = new VBox();
        bottom.getChildren().add(back);

        layout.setBottom(bottom);
        layout.setCenter(fieldsAndButton);

        Scene scene = new Scene(layout);
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + this.f.getAbsolutePath().replace("\\", "/"));
        this.loginScene = scene;

    }

    /**
     * Metodi luo scene-olion, jossa käyttäjä voi luoda uuden käyttäjätunnuksen.
     * Ennen tunnuksen luomista tarkistetaan, ettei annettu nimimerkki ole jo
     * käytössä, sekä muutamia tyyliseikkoja.
     */
    public void createRegisterScene() {

        BorderPane layout = new BorderPane();

        Text name = new Text("Username: ");
        TextField nameField = new TextField();

        Text password = new Text("Password: ");
        PasswordField passwordField = new PasswordField();

        GridPane nameAndPassword = new GridPane();
        nameAndPassword.setAlignment(Pos.CENTER);
        nameAndPassword.addRow(0, name, nameField);
        nameAndPassword.addRow(1, password, passwordField);

        Text infoText = new Text("");

        Button register = new Button("Create new User");
        register.setOnAction(event -> {
            String infoTextString = this.pLogic.checkRegisterInfo(nameField.getText(), passwordField.getText());
            if (infoTextString.equals("true")) {
                infoText.setText("");
                this.pLogic.createNewUser(nameField.getText(), passwordField.getText());
                this.handler.setScene(this.startScene);
            } else {
                infoText.setText(infoTextString);
            }
        });

        VBox fieldsAndButton = new VBox();
        fieldsAndButton.getChildren().addAll(nameAndPassword, infoText, register);
        fieldsAndButton.setAlignment(Pos.CENTER);

        Button back = new Button("Back");
        back.setOnAction((event) -> {
            this.handler.setScene(this.LoginOrRegister);
        });

        VBox bottom = new VBox();
        bottom.getChildren().add(back);

        layout.setBottom(bottom);
        layout.setCenter(fieldsAndButton);

        Scene scene = new Scene(layout);
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + this.f.getAbsolutePath().replace("\\", "/"));
        this.registerScene = scene;
    }

    /**
     * Metodi luo aloitusnäkymän, jossa voi valita haluaako mennä pelaamaan,
     * lukea peliohjeet vai tarkastella high score -tietoja.
     */
    public void createStartScene() {

        BorderPane layout = new BorderPane();

        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);

        Label heading = new Label("Jumping Game");
        heading.setId("mainHeading");
        layout.setTop(heading);
        layout.setCenter(buttons);

        Button startGame = new Button("New Game");
        Button howToPlay = new Button("How To Play");
        Button highScore = new Button("High Score");
        Button switchUser = new Button("Switch User");

        startGame.setOnAction((event) -> {
            this.pLogic.setPoints(0);
            createGameScene();
            this.handler.setScene(this.game);
        });
        howToPlay.setOnAction((event) -> {
            this.handler.setScene(this.instructionScene);
        });
        highScore.setOnAction((event) -> {
            createChooseHighScore();
            this.handler.setScene(this.chooseHighScore);
        });
        switchUser.setOnAction((event) -> {
            this.handler.setScene(this.LoginOrRegister);
        });

        buttons.getChildren().addAll(startGame, howToPlay, highScore, switchUser);

        Scene start = new Scene(layout);

        start.getStylesheets().clear();
        start.getStylesheets().add("file:///" + this.f.getAbsolutePath().replace("\\", "/"));

        this.startScene = start;

    }

    /**
     * Metodi luo pelinäkymän, sekä olion gameScreenHandler, joka muokkaa
     * pelinäkymää yhdessä pelilogiikan kanssa.
     */
    public void createGameScene() {

        BorderPane layout = new BorderPane();
        Scene gameScene = new Scene(layout);

        Canvas canvas = new Canvas(400, 500);
        layout.getChildren().add(canvas);

        Text infoText = new Text("Points: \n0 \nLives: \n3");

        layout.setRight(infoText);

        GameScreenHandler gch = new GameScreenHandler(canvas, gameScene, infoText, this.handler, this.pLogic);

        gch.updateGame();

        gameScene.getStylesheets().clear();
        gameScene.getStylesheets().add("file:///" + this.f.getAbsolutePath().replace("\\", "/"));

        this.game = gameScene;

    }

    /**
     * Metodi luo game over -näkymän, joka näyttää saavutetut pisteet ja antaa
     * mahdollisuuden kokeilla peliä uudelleen.
     */
    public void createGameOverScene() {

        BorderPane layout = new BorderPane();

        layout.setTop(new Label("Game Over"));

        VBox buttonsEtc = new VBox();
        buttonsEtc.setAlignment(Pos.CENTER);

        Text player = new Text("Player: " + this.pLogic.getCurrentPlayer());
        Text points = new Text("Points: " + this.pLogic.getPoints());

        Button tryAgain = new Button("Try Again");
        tryAgain.setOnAction((event) -> {
            createGameScene();
            this.pLogic.setPoints(0);
            this.handler.setScene(this.game);
        });

        Button quit = new Button("Quit");
        quit.setOnAction((event) -> {
            this.handler.setScene(this.startScene);
        });

        buttonsEtc.getChildren().addAll(player, points, tryAgain, quit);
        layout.setCenter(buttonsEtc);

        Scene gameOverScene = new Scene(layout);

        gameOverScene.getStylesheets().clear();
        gameOverScene.getStylesheets().add("file:///" + this.f.getAbsolutePath().replace("\\", "/"));

        this.gameOver = gameOverScene;

    }

    /**
     * Metodi luo näkymän, joka näyttää peliohjeet.
     */
    public void createInstructionScene() {

        BorderPane layout = new BorderPane();

        layout.setTop(new Label("How To Play"));

        Text instruction = new Text();
        instruction.setText("Move sideways by moving your mouse. \n"
                + "Character jumps automatically on platform. \n"
                + "Do not fall off screen. \n"
                + "Collect triangles to get more lives. \n"
                + "Do not collide with circles, or you lose them. \n"
                + "Once your lives drop to zero, you die from next impact.");

        Button back = new Button("Back");
        back.setOnAction((event) -> {
            this.handler.setScene(this.startScene);
        });
        Button newGame = new Button("New Game");
        newGame.setOnAction((event) -> {
            this.pLogic.setPoints(0);
            createGameScene();
            this.handler.setScene(this.game);
        });

        HBox buttons = new HBox();

        buttons.getChildren().addAll(back, newGame);

        layout.setCenter(instruction);
        layout.setBottom(buttons);

        Scene howToPlay = new Scene(layout);

        howToPlay.getStylesheets().clear();
        howToPlay.getStylesheets().add("file:///" + this.f.getAbsolutePath().replace("\\", "/"));

        this.instructionScene = howToPlay;

    }

    /**
     * Metodi luo näkymän, jossa käyttäjä voi valita, haluaako tarkastella
     * henkilökohtaista vai yleistä high score -listausta
     */
    public void createChooseHighScore() {

        BorderPane layout = new BorderPane();

        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);

        Button personal = new Button("Personal High Score");
        Button all = new Button("High Score");

        buttons.getChildren().addAll(personal, all);

        personal.setOnAction(event -> {
            createHighScoreScene("personal");
            this.handler.setScene(this.highScores);
        });

        all.setOnAction(event -> {
            createHighScoreScene("all");
            this.handler.setScene(this.highScores);
        });

        layout.setCenter(buttons);

        Scene scene = new Scene(layout);
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + this.f.getAbsolutePath().replace("\\", "/"));
        this.chooseHighScore = scene;

    }

    /**
     * Metodi luo näkymän, joka tulostaa ruudulle gameLogicin annetun parametrin
     * perusteella haetun high score -listauksen
     *
     * @param highScoreType String muuttuja, joka annetaan eteenpäin
     * gameLogicille, joka sitten palauttaa oikeantyyppisen (all tai personal)
     * high score -listauksen
     */
    public void createHighScoreScene(String highScoreType) {

        BorderPane layout = new BorderPane();

        layout.setTop(new Label("High Score"));

        Text highScore = new Text();
        pLogic.updateHighScore(highScoreType);
        highScore.setText(this.pLogic.getHighScoreString());
        layout.setCenter(highScore);

        HBox buttons = new HBox();
        Button back = new Button("Back");
        back.setOnAction((event) -> {
            this.handler.setScene(this.startScene);
        });
        buttons.getChildren().add(back);
        layout.setBottom(buttons);

        Scene highScoreScene = new Scene(layout);

        highScoreScene.getStylesheets().clear();
        highScoreScene.getStylesheets().add("file:///" + this.f.getAbsolutePath().replace("\\", "/"));

        this.highScores = highScoreScene;

    }

    public Scene getLoginOrRegister() {
        return this.LoginOrRegister;
    }

    public Scene getGameOverScene() {
        return this.gameOver;
    }

}
