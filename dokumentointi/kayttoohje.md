# Käyttöohje

Lataa tiedostot JumpingGame.jar, highScores.h2.db, highScores.trace.db ja 
stylesheet.css uusimmasta 
[releasesta](https://github.com/sonjaheikkinen/ot-harjoitustyo/releases). 
Sijoita ne samaan kansioon. 

## Ohjelman käynnistäminen 

Ohjelma käynnistetään komennolla 

```
java -jar JumpingGame.jar
```

## Käytön aloitus

Peli käynnistyy aloitusnäkymään. Valitse joko kirjautuminen tai uuden tunnuksen 
luominen, jos sinulla ei vielä ole tunnusta. Syötä avautuvaan ikkunaan nimimerkkisi 
ja salasanasi. **Sovelluksessa ei vielä ole tietoturvaa, joten älä käytä oikeita 
salasanojasi!**  

Kirjauduttasi peli siirtyy toiseen aloitusnäkymään. Jos haluat lukea peliohjeet, 
paina nappia How To Play. High score -listauksen löydät High Scores -napin takaa.
Tällöin sinun tulee vielä valita, haluako lukea yleistä, vai henkilökohtaista 
high scorea. Molemmista näkymistä pääset takaisin aloitusnäkymään painamalla nappia 
Back. Sekä aloitusnäkymästä, että ohjenäkymästä voit käynnistää uuden pelin painamalla 
nappia New Game. Käyttäjän vaihtaminen onnistuu napista Switch User.

## Pelaaminen

Pelaaminen tapahtuu liikuttamalla hiirtä sivusuunnassa, jolloin pelihahmo (neliö)
liikkuu myös sivusuunnassa. Pelihahmo hyppää osuessaan hyppyalustalle (leveä 
neliskulmio). Tarkoituksena on hyppiä hyppyalustoja pitkin ylöspäin. Jos hahmo 
tippuu peliruudun alapuolelle, peli päättyy. 

Oikeasta yläkulmasta näkyvät kertyneet pisteet, ja jäljellä olevat elämät. Osuminen 
ansaan (ympyrä) vähentää elämien lukumäärää yhdellä, osuminen boostiin (kolmio) taas
lisää elämien määrää yhdellä. Jos elämien määrä putoaa nollaan, pelihahmo kuolee 
seuraavaan osumaan.

## Pelin päättyminen

Pelihahmon kuoltua avautuu Game Over -ikkuna, josta näkyy nimimerkki ja kerätyt 
pisteet. Try Again -napista voi yrittää peliä uudestaan. 
Quit-napilla pääsee takaisin aloitusnäkymään. 

## Ohjelman sulkeminen

Ohjelma suljetaan painamalla ruksia yläkulmasta. 