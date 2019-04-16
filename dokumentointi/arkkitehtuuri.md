# Arkkitehtuurikuvaus

## Rakenne

## Käyttöliittymä

## Sovelluslogiikka

![alustavaPakkauskaavio.jpg](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/alustavaPakkauskaavio.jpg)

### Päätoiminnallisuudet

#### Pelihahmon hyppääminen

Peliruudun tapahtumista vastaava AnimationTimer-olio kontrolloi pelihahmon hyppäämistä 
alustalta seuraavasti:

![sekvenssikaavio.jpg](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/sekvenssikaavio.jgp)

Aluksi AnimationTimer kutsuu GameLogic-luokan metodia detectCollission, joka 
tarkastelee peliobjectien (luokka GameObject) välisiä törmäyksiä. GameLogic 
luo aluksi iteraattorin hyppyalustat sisältävälle listalle. Sitten listaa 
ruvetaan käymään läpi. Jokaisen hyppyalustan kohdalla kutsutaan pelihahmo-olion
intersects-metodia, joka hakee pelihahmoa (gameObject) ja alustaa (myös gameObject) 
vastaavat suorakulmiot, ja tarkastaa ovatko ne päällekkäin. Lisäksi tarkistetaan onko 
pelihahmo menossa ylöspäin (pelihahmo hyppää uudestaan vain pudotessaan alustalle, 
ei osuessaan siihen alhaaltapäin). Jos pelihahmo osuu alustaan ja on menossa alaspäin,
asetetaan pelihahmon Action-arvoksi true, ja suunnataan sen liike ylöspäin, jolloin 
hahmoa liikuttava metodi käsittelee sitä hyppäävänä hahmona. Tämän jälkeen tarkistetaan 
vielä alustan Action-arvo. Jos se on false, ei alustalta ole vielä aikaisemmin hypätty. 
Tällöin lisätään pisteitä ja merkataan alusta käydyksi (Action = true).

## Tietojen pysyväistallennus

### Tiedostot