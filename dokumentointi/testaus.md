# Testausdokumentti

Ohjelmaa on testattu sekä automaattisesti JUnitilla, että manuaalisesti. 

## JUnit-testaus

Automatisoitujen testien pakkaus- ja luokkarakenne noudattelee varsinaisen ohjelman 
rakennetta, mutta nimien perään on lisätty merkintä Test erottelemaan ne tavallisista 
luokista. Jokainen testiluokka testaa niitä metodeita, jotka esiintyvät sitä 
vastaavassa varsinaisessa luokassa.

Testeihin kuuluu sekalaisesti yksikkö- ja integraatiotestejä. Integraatiotestejä 
on niissä testiluokissa, joita vastaavat oikeat luokat käyttävät hyödykseen muita 
luokkia. 

### Tiedon pysyväistallennus testeissä

Testit käyttävät varsinaisen ohjelman käyttämän highScores-tietokannan kopiota 
test, jonka taulut alustetaan tyhjiksi testauksen yhteydessä. Kaikki testeissä 
käytettävä tieto siis lisätään ja luetaan testauksen aikana. 

### Testauskattavuus

Testien rivikattavuus on noin. 87 % ja haaraumakattavuus noin 81 %.
Käyttöliittymän muodostavia luokkia ei ole otettu mukaan testauskattavuusraporttiin.

![testakattavuus.jpg](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/testikattavuus.jpg)

Testaamatta ovat jääneet osa gettereistä ja settereistä, sekä muutama metodi 
GameLogicista, joissa käytetään satunnaislukuja tai jotka vain kutsuvat kasaa muita 
metodeita. Lisäksi sieltä täältä on jäänyt testaamatta joitain vähemmän oleellisia 
rivejä ja haaraumia.

## Manuaalinen testaus

Koska kyseessä on peli, on iso osa testauksesta tehty manuaalisesti pelaamalla peliä, 
ja tulostelemalla joitain arvoja pelin edetessä. Tällä tavalla on testattu kattavasti 
erilaisia skenaarioita ja todettu peli toimivaksi (bugeja ei ole löytynyt). Peli on 
testattu netbeansilla windows-alustalla, sekä käyttöohjeen mukaisesti jar-tiedostona 
windowsilla ja mcOS mojavella. Myös kaikki vaatimusmäärittelyn mukaiset toiminnot 
on testattu sekä oikeilla, että virheellisillä syötteillä.

## Sovellukseen jääneet laatuongelmat

Tyylimäärittelyt näyttävät erilaisilta eri järjestelmillä, minkä vuoksi osa 
käyttäjistä saattaa joutua venyttämään ruutua, että kaikki tekstit näkyvät 
loppuun asti. Lisäksi joillain alustoilla on todettu ongelmia pelin 
hyppytoiminallisuudessa. Näiden lähdettä ei ole kuitenkaan saatu vielä paikannettua. 