
package fi.sonjaheikkinen.domain;

import javafx.geometry.Rectangle2D;

/**
 * Luokka toimii pohjana erilaisille pelin olioille, kuten pelihahmolle, ansoille ja hyppyalustoille. Kaikkia näitä 
 * varten riittää yksi luokka, koska niillä kaikilla on samat perusominaisuudet: koko, sijainti, liike, ja jokin toiminto.
 */
public class GameObject {

    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    /**
     * Muuttujan action rooli vaihtelee sen mukaan, mitä roolia kyseessä oleva olio palvelee pelissä. Pelihahmolla 
     * action-muuttuja on true, jos pelihahmo on hyppäämässä, ja muuten false. Hyppyalustalla action on true siitä hetkestä 
     * lähtien, kun pelihahmo on osunut hyppyalustaan, ja muutetaan takaisin falseksi vain kun alusta nollataan sen tippuessa
     * ruudun alapuolelle. Ansalla action on true kun ansa on juuri luotu, jotta pelilogiikka tietä määrittää sille koon, 
     * sijainnin ja liikkeen. Boostilla action on true jos pelaaja on osunut boostiin, ja se asetetaan takaisin falseksi 
     * kun boost nollataan ja sille arvotaan uusi paikka. 
     */
    private boolean action;

    public GameObject(double x, double y, double width, double height, double velocityX, double velocityY) {
        this.positionX = x;
        this.positionY = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.width = width;
        this.height = height;
        this.action = false;
    }

    /**
     * Metodi päivittää objektin sijaintia suhteessa sen liikkeeseen ja edellisestä päivityskerrasta kuluneeseen aikaan.
     * Se lisää objektin x-koordinaattiin x-akselin suhtaisen liikkeen kerrottuna kuluneilla sekunneilla, ja 
     * y-koordinaattiin vastaavasti y-akselin suhtaisen liikkeen kerrottuna kuluneilla sekunneilla. 
     * 
     * @param time edellisestä päivityksestä kuluneet sekunnit 
     */
    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

    /**
     * Metodi palauttaa neliskulmion, jonka sijainti, leveys ja korkeus vastaavat pelioliota. 
     * 
     * @return Rectangle2D-olio
     */
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    /**
     * Metodi vertaa keskenään kahta GameObject-oliota, ja tarkistaa, törmäävätkö ne keskenään. Tämän se tekee 
     * hakemalla molempia olioita vastaavat Rectangle2D-oliot metodin getBoundary avulla, ja hyödyntämällä sitten
     * rectangle2D:n metodia intersects, joka palauttaa true, jos nelikulmiot osuvat toisiinsa. 
     * 
     * @param other GameObject-olio, jonka rajoihin nykyisen olion rajoja verrataan
     * 
     * @return true, jos oliot törmäävät, muuten false
     */
    public boolean intersects(GameObject other) {
        return other.getBoundary().intersects(this.getBoundary());
    }

    /**
     * Muuttaa olion liikettä lisäämällä tai vähentämällä sen liike-muuttujia anneituilla parametreilla
     * 
     * @param x x-akselin suuntainen liikkeenmuutos
     * @param y y-akselin suuntainen liikkeenmuutos
     */
    public void changeVelocity(double x, double y) {
        velocityX += x;
        velocityY += y;
    }

    public double getPositionX() {
        return this.positionX;
    }

    public void setPositionX(double x) {
        positionX = x;
    }

    public double getPositionY() {
        return this.positionY;
    }

    public void setPositionY(double y) {
        positionY = y;
    }

    public double getVelocityX() {
        return this.velocityX;
    }

    public void setVelocityX(double x) {
        this.velocityX = x;
    }

    public double getVelocityY() {
        return this.velocityY;
    }

    public void setVelocityY(double y) {
        this.velocityY = y;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean getAction() {
        return this.action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

}
