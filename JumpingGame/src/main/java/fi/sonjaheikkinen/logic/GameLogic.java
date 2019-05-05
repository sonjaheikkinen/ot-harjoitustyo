
package fi.sonjaheikkinen.logic;

import fi.sonjaheikkinen.domain.GameObject;
import fi.sonjaheikkinen.other.LongValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * Luokka ohjaa pelin toimintaa. Se pitää kirjaa peliobjectien sijainneista ja
 * statuksista, sekä muistaa kaikki olennaiset lukumäärät, kuten pisteet ja
 * jäljellä olevat elämät.
 */
public class GameLogic {

    private int points;
    private final int gameScreenWidth;
    private final int gameScreenHeight;
    private int pointIncrease;
    private int level;
    private final int fallingSpeed;
    private int nextGap;
    private int lives;
    private final Random random;
    private double elapsedTimeInSeconds;

    private GameObject gameCharacter;
    private ArrayList<GameObject> platforms;
    private ArrayList<GameObject> traps;
    private GameObject boost;

    public GameLogic() {
        this.points = 0;
        this.gameScreenWidth = 400;
        this.gameScreenHeight = 500;
        this.pointIncrease = 1;
        this.level = 0;
        this.fallingSpeed = 100;
        this.nextGap = 20;
        this.lives = 3;
        this.random = new Random();
    }

    /**
     * Metodi kutsuu muita metodeita, joiden avulla luodaan kaikki pelin
     * tarvitsemat GameObject-oliot ja talletetaan ne luokan oliomuuttujiksi
     * myöhempää käyttöä varten.
     */
    public void createGameObjects() {
        createGameCharacter();
        createPlatforms();
        createTraps();
        createBoost();
    }

    /**
     * Metodi luo uuden GameObject-olion, pelihahmon, pelaajan ohjattavaksi
     */
    public void createGameCharacter() {
        this.gameCharacter = new GameObject(360, 0, 15, 15, 0, 0);
    }

    /**
     * Metodi luo viisi hyppyalustan muotoista GameObject-oliota. Yksi alusta on
     * aluksi koko pelialueen levyinen, jotta pelaaja ei tippuisi heti
     * aloittaessaan pelin.
     */
    public void createPlatforms() {
        this.platforms = new ArrayList<>();
        GameObject groundPlatform = new GameObject(0, 200, 400, 2, 0, 0);
        this.platforms.add(groundPlatform);
        for (int i = 0; i < 4; i++) {
            int platformY = i * 100;
            if (platformY >= 200) {
                platformY = i * 100 + 100;
            }
            GameObject platform = new GameObject((3 * i * 10), platformY, 70, 2, 0, 0);
            this.platforms.add(platform);
        }
    }

    /**
     * Metodi luo kymmenen ansaa GameObject-oliona
     */
    public void createTraps() {
        this.traps = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GameObject trap = new GameObject(0, 0, 0, 0, 0, 0);
            trap.setAction(true);
            traps.add(trap);
        }
    }

    /**
     * Metodi luo GameObject-olion boost
     */
    public void createBoost() {
        this.boost = new GameObject(this.random.nextInt(this.gameScreenWidth) - 10, -500, 10, 10, 0, this.fallingSpeed);
    }

    /**
     * Metodi päivittää kaikki pelitiedot suhteessa kuluneeseen aikaan. Tämä tapahtuu laskemalla kulunut aika 
     * sekunneissa, ja kutsumalla sitten muita metodeja, jotka hoitavat pelitietojen päivityksen.
     * 
     * @param currentNanoTime nykyinen aika nanosekunneissa
     * @param lastNanoTime edellisen päivityksen ajankohta nanosekunteina
     */
    public void updateGame(long currentNanoTime, LongValue lastNanoTime) {
        this.elapsedTimeInSeconds = calculateElapsedTime(lastNanoTime, currentNanoTime);
        moveGameObjects();
        detectCollission();
        handleLevel();
    }
    
    
    /**
     * Metodi liikuttaa ruudulla olevia GameObject-olioita kutsumalla useampaa metodia, joista kukakin vastaa tietystä 
     * olioryhmästä, jotka kaikki liikkuvat eri tavalla.
     */
    public void moveGameObjects() {
        moveCharacter();
        movePlatforms();
        moveTraps();
        moveBoost();
    }

    /**
     * Metodi käsittelee ruudulla liikkuvien olioden väliset törmäykset kutsumalla muita törmäyksiä käsitteleviä
     * metodeita.
     */
    public void detectCollission() {
        detectCollissionWithPlatforms();
        detectDeathOnTrap();
        detectCollissionWithBoost();
    }

    
     /**
     * Metodi laskee edellisestä ruudunpäivityksestä kuluneen ajan miinustamalla tämänhetkisestä ajankohdasta 
     * edellisen päivityksen ajankohta. Aika muutetaan nanosekunneista sekunneiksi jakamalla tulos miljardilla.
     * 
     * @param lastNanoTime edellisen ruudunpäivityksen ajankohta nanosekunteina
     * @param currentNanoTime tämänhetkinen aika nanosekunteina
     * 
     * @return palautetaan edellisestä päivityksestä kulunut aika muutettuna sekunneiksi
     */
    public double calculateElapsedTime(LongValue lastNanoTime, long currentNanoTime) {
        double elapsedTime = (currentNanoTime - lastNanoTime.getValue()) / 1000000000.0;
        lastNanoTime.setValue(currentNanoTime);
        return elapsedTime;
    }

    /**
     * Metodi liikuttaa pelihahmoa. Jos pelihahmon Action-arvo on true,
     * tulkitaan pelihahmo hyppääväksi. Tällöin pelihahmon ylöspäinsuuntautuvaa
     * liikettä vähennetään kahdellakymmenellä joka kutsukerralla, kunnes hahmo
     * on taas saavuttanut oman putoamisnopeutensa (500). Jos pelihahmo ei
     * hyppää, eli Action-arvo on false, asetetaan sen nopeudeksi hahmon oma
     * putoamisnopeus. Tämän jälkeen kutsutaan pelihahmon update-metodia, joka
     * päivittää pelihahmon sijainnin parametrin elapseTimeInSeconds
     * perusteella.
     */
    public void moveCharacter() {
        if (this.gameCharacter.getAction()) {
            this.gameCharacter.changeVelocity(0, 20);
            if (this.gameCharacter.getVelocityY() >= 500) {
                this.gameCharacter.setAction(false);
                this.gameCharacter.setVelocityY(500);
            }
        } else {
            this.gameCharacter.setVelocityY(500);
        }
        this.gameCharacter.update(this.elapsedTimeInSeconds);
    }

    /**
     * Metodi liikuttaa hyppyalustoiksi määriteltyjä GameObject-olioita.
     * Jokaisella kutsukerralla jokaisen hyppyalustan putoamisnopeudeksi
     * annetaan yleinen putoamisnopeus (100). Jos hyppyalusta tippuu ruudun
     * ulkopuolelle, se siirretään takaisin ylös ja nollataan action-arvo arvoon
     * false. Lisäksi sijainti x-akselin suhteen arvotaan satunnaiseksi ja
     * leveys määritetään arvoon 70. Lopuksi hyppyalustan sijainti päivitetään
     * parametrin elapsedTimeInSeconds perusteella.
     */
    public void movePlatforms() {
        for (GameObject platform : this.platforms) {
            platform.setVelocityY(this.fallingSpeed);
            if (platform.getPositionY() >= this.gameScreenHeight) {
                platform.setWidth(70);
                platform.setPositionY(-10);
                platform.setPositionX(this.random.nextInt(330));
                platform.setAction(false);
            }
            platform.update(this.elapsedTimeInSeconds);
        }
    }

    /**
     * Metodi liikuttaa ansoiksi määritettyjä GameObject-olioita. Jos
     * taso-muuttujan arvo on 0, ei ansoja liikuteta, jolloin ne seisovat
     * käyttämättöminä pelialueen ulkopuolella. Muuten liikutetaan tasoa
     * vastaavaa määrää ansoja, kuitenkin maksimissaan kymmentä ansaa.
     *
     * Jos ansan Action-arvo on true, eli se on juuri luotu, tai ansa kulkeutuu
     * pelialueen ulkopuolelle, tai ansa ei liiku mihinkään suuntaan, se
     * rebootataan, eli ansan koko ja liikerata määritetään uudellen. Lisäksi
     * jokaisen liikutettavan ansan sijainti päivitetään parametrin
     */
    public void moveTraps() {
        for (int i = 0; i < Math.min(this.traps.size(), this.level); i++) {
            if (this.level != 0) {
                GameObject trap = this.traps.get(i);
                if (trapGoesOffScreen(trap) || trap.getAction() || (trap.getVelocityY() == 0 && trap.getVelocityX() == 0)) {
                    rebootTrap(trap);
                }
                trap.update(this.elapsedTimeInSeconds);
            }
        }
    }

    /**
     * Metodi tarkistaa, onko sille parametrina annettu GameObject-olio
     * kulkeutunut pelialueen reunojen ulkopuolelle. Ansa tulkitaan
     * kulkeutuneeksi reunojen yli, jos se toteuttaa vähintään yhden näistä
     * ehdoista: - Ansa kulkee oikealle ja on oikean reunan oikealla puolella -
     * Ansa kulkee vasemmalle ja on vasemman reunan vasemmalla puolella - Ansa
     * kulkee ylös ja on yläreunan yläpuolella - Ansa kulkee alas ja on
     * alareunan alapuolella
     *
     * @param trap ansaa kuvaava GameObject-olio
     *
     * @return palautetaan true, jos ansa on kulkeutunut reunojen ulkopuolelle,
     * muuten false
     */
    public boolean trapGoesOffScreen(GameObject trap) {
        if (trap.getPositionX() < 0 - trap.getWidth() && trap.getVelocityX() < 0
                || trap.getPositionX() > this.gameScreenWidth && trap.getVelocityX() > 0
                || trap.getPositionY() < 0 - trap.getHeight() && trap.getVelocityY() < 0
                || trap.getPositionY() > this.gameScreenHeight && trap.getVelocityY() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Metodi määrittelee uuden koon, sijainnin ja liikkeen suunnan sille
     * parametrina annetulle ansaksi määritellylle GameObject-oliolle. Ansan
     * action-arvoksi merkitään false, jolloin ansaa ei enää käsitellä
     * vastaluotuna. Tämän jälkeen ansa sijoitetaan satunnaisesti peliruudun
     * ylä- tai alapuolelle satunnaiseen kohtaan x-akselia. Ansan leveys
     * arvotaan välille 5-45 ja korkeudeksi asetetaan sama kuin leveys. Vaaka-
     * ja pystysuuntainen liike arvotaan satunnaisesti välille ]-200, 200[
     *
     * @param trap ansaksi määritelty GameObject-olio
     */
    public void rebootTrap(GameObject trap) {
        trap.setAction(false);
        int numberY = this.random.nextInt(2);
        if (numberY == 0) {
            trap.setPositionY(-100);
        } else {
            trap.setPositionY(600);
        }
        trap.setWidth(this.random.nextInt(40) + 5);
        trap.setHeight(trap.getWidth());
        trap.setPositionX(this.random.nextInt(1200) - 400);
        trap.setVelocityX(this.random.nextInt(200));
        if (trap.getVelocityX() % 2 == 0) {
            trap.setVelocityX(trap.getVelocityX() * -1);
        }
        trap.setVelocityY(this.random.nextInt(200));
        if (trap.getVelocityY() % 2 == 0) {
            trap.setVelocityY(trap.getVelocityY() * -1);
        }
    }

    /**
     * Metodi liikuttaa boostiksi määriteltyä GameObject-oliota. Jos boostin
     * Action-arvo on true, on pelaaja osunut boostiin. Tällöin olio siirretään
     * pelialueen ulkopuolelle odottamaan mahdollista reboottausta, eli
     * uudelleenkäyttöönottoa, jonka toteutusta säätelee kutsuttava
     * tryReboot-metodi. Jos boostiin ei ole osuttu, sen sijainti päivitetään
     * parametrin elapsedTimeInSeconds perusteella. Tämän jälkeen sijainti
     * tarkistetaan, ja rebootataan, jos olio on kulkeutunut liian kauas
     * pelialueelta.
     */
    public void moveBoost() {
        if (this.boost.getAction()) {
            //player has hit boost
            this.boost.setVelocityY(0);
            this.boost.setPositionY(600);
            tryReboot();
        } else {
            this.boost.update(this.elapsedTimeInSeconds);
            if (this.boost.getPositionY() > 600 && !this.boost.getAction()) {
                tryReboot();
            }
        }
    }

    /**
     * Metodi kutsuu boostin reboottaavaa metodia 10 % todennäköisyydellä. Eli
     * keskimäärin kerran kymmennessä kutsukerrassa.
     */
    public void tryReboot() {
        int randomNumber = this.random.nextInt(10);
        if (randomNumber == 5) {
            rebootBoost();
        }
    }

    /**
     * Metodi siirtää boostin pelialueen yläreunaan ja valitsee sen sijainnin
     * x-akselilla satunnaisesti. Nopeudeksi asetetaan yleinen putoamisnopeus.
     */
    public void rebootBoost() {
        this.boost.setAction(false);
        this.boost.setPositionY(-500);
        this.boost.setPositionX(this.random.nextInt(this.gameScreenWidth - (int) this.boost.getWidth()));
        this.boost.setVelocityY(this.fallingSpeed);
    }

    /**
     * Metosi tarkastaa osuuko pelihahmo johonkin hyppyalustoista, ja
     * tarvittaessa laittaa pelihahmon hyppäämään. Hyppy toteutetaan asettamalla
     * pelihahmon y-akselin suuntainen liike arvoon 500 ja action-arvoksi true.
     * Lisäksi, jos alustan action-arvo on false, eli pelihahmo ei vielä alustan
     * tällä putoamiskerralla ole siihen osunut, lisätään pisteitä ja merkataan
     * alusta osutuksi, eli asetetaan action-muuttuja arvoon true.
     */
    public void detectCollissionWithPlatforms() {
        Iterator<GameObject> platformIterator = this.platforms.iterator();
        while (platformIterator.hasNext()) {
            GameObject platform = platformIterator.next();
            if (this.gameCharacter.intersects(platform) && this.gameCharacter.getVelocityY() >= 0) {
                this.gameCharacter.setVelocityY(-500);
                this.gameCharacter.setAction(true);
                if (!platform.getAction()) {
                    //character has not yet been given points from this platform
                    platform.setAction(true);
                    this.points += this.pointIncrease;
                }
            }
        }
    }

    /**
     * Metodi tarkistaa, osuuko pelaaja johonkin ansoista, ja tarvittaessa joko
     * vähentää elämiä tai tappaa pelaajan. Kaikki ansat käydään läpi. Jos
     * pelaaja osuu ansaan, ja elämiä on vielä jäljellä, rebootataan ansa ja
     * vähennetään elämien määrää. Jos elämät ovat nollissa, tiputetaan
     * pelihahmo peliruudun ulkopuolelle, jolloin pelaaja tulkitaan kuolleeksi.
     */
    public void detectDeathOnTrap() {
        Iterator<GameObject> trapIterator = traps.iterator();
        while (trapIterator.hasNext()) {
            GameObject trap = trapIterator.next();
            if (this.gameCharacter.intersects(trap)) {
                if (this.lives == 0) {
                    this.gameCharacter.setPositionY(this.gameScreenHeight + 1);
                } else {
                    this.lives--;
                    rebootTrap(trap);
                }
            }
        }
    }

    /**
     * Metodi tarkistaa, onko pelaaja osunut boostiin, ja näin tapahtuessa lisää
     * elämien määrää yhdellä, sekä merkitsee boostin osutuksi asettamalla sen
     * Action-arvoksi true.
     */
    public void detectCollissionWithBoost() {
        if (this.gameCharacter.intersects(this.boost)) {
            this.lives++;
            this.boost.setAction(true);
        }
    }

    /**
     * Metodi päivittää pelaajalle näkymätöntä vaikeustasoa, joka vaikuttaa
     * pisteiden kerääntymisen nopeuteen ja liikutettavien ansojen määrään.
     * Pelitaso nousee aina viidenkymmenenhypyn välein, ja samalla nostetaan
     * jokaisesta hyppyalustaosumasta kertyvien pisteiden määrää.
     */
    public void handleLevel() {
        if (this.points >= this.nextGap) {
            this.level++;
            this.pointIncrease = this.pointIncrease + this.level * 5;
            this.nextGap = this.nextGap + this.pointIncrease * 50;
        }
    }

    public int getPoints() {
        return this.points;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }

    public int getLives() {
        return this.lives;
    }
    
    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public GameObject getGameCharacter() {
        return gameCharacter;
    }

    public ArrayList<GameObject> getPlatforms() {
        return platforms;
    }

    public ArrayList<GameObject> getTraps() {
        return traps;
    }

    public GameObject getBoost() {
        return boost;
    }
    
    public void setElapsedTimeInSeconds(double time) {
        this.elapsedTimeInSeconds = time;
    }

    public int getLevel() {
        return this.level;
    }
    
    public int getPointIncrease() {
        return this.pointIncrease;
    }

}
