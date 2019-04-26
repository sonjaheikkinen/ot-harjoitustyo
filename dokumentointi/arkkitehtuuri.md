# Arkkitehtuurikuvaus

## Rakenne

Ohjelman pakkausrakenne on yksinkertainen. Hierarkian ylimpänä on graafisen 
käyttöliittymän muodostava pakkaus gui. Seuraavalla tasolla ovat laskennan suorittava 
pakkaus logic, ja sekalaista sälää sisältävä pakkaus other. Kolmannella tasolla ovat 
vielä logic-pakkauksen käyttämät pakkaukset filehandling, joka nimensä mukaisesti 
käsittelee tiedonsiirtoa tiedostojen ja ohjelman välillä, sekä domain, joka sisältää 
pelin "käsitteet". Pakkausrakenne on kuvattu alla olevassa kaaviossa. Kaikki pakkaukset 
ovat pakkauksen fi.sonjaheikkinen sisällä, vaikkei sitä ole kaavion piirretty.

![pakkausrakenne.jpg](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/pakkausrakenne.jpg)

Luokkien väliset suhteet muodostavat verkkomaisen kaavion:

![pakkauskaavio.jpg](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/pakkauskaavio.jpg)

Kaavioon on merkitty pakkaukset, luokkien väliset pysyvät suhteet sekä oleellisin 
riippuvuussuhde. 

### gui

Pakkaus gui rakentaa graafisen käyttöliittymän. Se sisältää neljä luokkaa. Ohjelman 
käynnistää luokka JumpingGameGui, joka alustaa ohjelmalogiikan, ja ryhtyy sitten 
rakentamaan sen pohjalta käyttöliittymää. Se luo oliot luokista SceneConstructor ja 
StageHandler. SceneConstructor vastaa yksittäisten näkymien, scenejen, luomisesta, kun 
taas stageHandler hallitsee scenejen asettamiseen näkyväksi ikkunaan, stageen. Jos 
ikkunassa on pelinäkymä, vastaa luokka GameScreenHandler kommunikoinnista pelilogiikan 
kanssa ja pelitapahtumien piirtämisestä ruudulle. Luokat JumpingGameGui, 
SceneConstructor ja GameScreenHandler käyttävät hyödykseen myös ProgramLogic-oliota, 
jolta ne saavat erilaisia tietoja, kuten tämänhetkisen high score -listauksen 
piirrettäväksi ruudulle. 

### other

Pakkaus other sisältää vain yhden luokan, LongValue, joka kapsuloi long-tyyppisen 
muuttujan, jotta sitä voidaan käyttää luokan animationTimer sisällä. 

### logic

Pakkaus logic sisältää kaksi luokkaa: ohjelmalogiikasta vastaavan luokan ProgramLogic 
ja pelilogiikasta vastaavan luokan gameLogic. ProgramLogic kommunikoi käyttöliittymän 
ja filehandlerin kanssa ja sen päätehtävänä on pitää kirjaa kulloisenkin peli-instassin 
tiedoista ja päivittää high score -listausta. GameLogic kommunikoi luokan 
GameScreenHandler ja pakkauksen domain kanssa. Se ohjaa yksittäisen peli-instassin 
toimintaa, ja luodaan aina uudestaan uuden pelin käynnistyessä. 

### filehandling

Pakkauksen filehandling luokka highScoreHandler vastaa pistetietojen kirjaamisesta 
tiedostoon ja niiden noutamisesta sieltä.

###  domain

Pakkaus domain sisältää luokan GameObject. Jokainen luokan instanssi vastaa yksittäistä 
pelioliota, "spritea", joiden liikkeitä ja muutoksia kontrolloi luokka GameLogic.

## Käyttöliittymä

Käyttöliittymä sisältää kuusi näkymää.

- aloitusnäkymä
- uuden pelin aloitus
- pelinäkymä
- pelin loppumisnäkymä
- peliohjeet
- high score

Jokainen näkymä on toteutettu omana scene-olionaan, ja vain yksi näkymä on kerrallaan 
sijoitettuna stageen. Pelinäkymä, pelin loppumisnäkymä ja high score generoidaan 
uudestaan joka kutsumiskerralla vastaamaan ohjelma- ja pelilogiikassa kutsumishetkellä 
olevia tietoja. 

## Sovelluslogiikka

Sovelluslogiikan pohjan muodostavat luokat ProgamLogic, GameLogic ja GameObject, 
joiden suhteet muihin luokkiin on kuvattu yllä kohdassa rakenne. 

### Päätoiminnallisuudet

#### Pelin aloitus 

Pelaajan aloittaessa uuden pelin, luodaan uusi instassi luokasta GameScreenHandler. 
GameScreenHandler luo uuden instassin luokasta GameLogic, ja kutsuu GameLogicin 
metodia, joka luo peliruudulla liikkuvat GameObjectit. Tämän jälkeen kutsutaan 
metodia updateGame, joka huolehtii pelin tapahtumien päivityksestä GameScreenHandlerin 
hallinnoimalle canvas-oliolle. 

#### Pelin päivitys

Metodi updateGame kutsuu metodeja handleMouseMovement ja handleAnimation. 

HandleMouseMovement kuuntelee hiirten liikkeitä, ja kutsuu GameLogicia, joka 
siirtää pelattavan hahmon x-koordinaatin vastaamaan hiiren x-koordinaattia. 

HandleAnimation luo instassin luokasta AnimationTimer, ja sen metodia handle 
säännöllisin väliajoin. Handle puolestaan kutsuu GameScreenHandlerin metodia 
updateGameScreen.

UpdateGameScreen kutsuu GameLogicin metodia updateGame, joka päivittää kaikki
pelitiedot, kuten pelihahmojen sijainnit ja pisteet, sekä luokan sisäistä metodia 
render, joka sitten piirtää kaikki pelihahmot GameLogicin sisältämien tietojen avulla 
ruudulle. 

#### Peliobjektien liike

Jokaisella GameObject-oliolla on määritelty vaaka- ja pystysuuntainen liike, sekä 
x- ja y-koordinaatti. Liike tapahtuu kutsumalla metodia update, joka saa parametrinaan 
edellisestä päivityksestä kuluneet sekunnit, ja joka sitten päivittää olion sijainnin 
lisäämällä sen x-koordinaattiin x-akselin suuntaisen liikkeen kerrottuna sekunneilla, 
ja vastaavasti päivitetään myös y-koordinaatti. 

GameObject-olioiden liikettä ohjataan GameLogic-luokan metodeilla, ja liikkeen ohjaus 
riippuu siitä, mihin GameLogicin luokkamuuttujaan kyseinen olio on tallennettu. 
Esimerkiksi hyppyalustoja tiputetaan tasaista vauhtia alaspäin, kunnes ne saavuttavat 
ruudun alareunan, jolloin niiden y-koordinaattia muokataan niin, että ne siirtyvät 
takaisin ylös, ja x-koordinaatti valitaan satunnaisesti. Ansoille taas arvotaan sekä 
lähtöpaikka, että erilliset liikenopeudet x- ja y-akselin suuntaisille liikkeille. 

#### Peliobjektien väliset törmäykset

GameObject luokalla on metodi getBoundary, joka palauttaa olion sijaintia ja kokoa 
vastaavan neliskulmion. Luokan metodi intersects taas vertaa kahden eri 
GameObject-olion getBoundary-metodien palautusarvoja, ja palauttaa true, jos 
neliskulmiot ovat edes osittain päällekkäiset. 

GameLogic-luokalla on useita metodeita, jotka hyödyntävät intersects-metodia, ja 
törmäyksen sattuessa tekevät erilaisia asioita, kuten lisäävät pisteitä tai muuttavat 
hahmojen sijaintia ja liikettä.

##### Esimerkki: pelihahmon hyppääminen

Peliruudun tapahtumista vastaava AnimationTimer-olio kontrolloi pelihahmon hyppäämistä 
alustalta seuraavasti:

![sekvenssikaavio.jpg](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/sekvenssikaavio.jpg)

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

#### Piilotettu vaikeustaso

Pelissä on piilotettu vaikeustaso. GameLogicilla on luokkamuuttuja level, joka 
kasvaa tasaisesti pisteiden kerääntyessä. Mitä isompi vaikeustaso, sitä enemmän ansoja 
pelialueelle generoidaan. Tämä tapahtuu kasvattamalla metodin moveTraps liikuttamien 
ansojen määrää (paikallaan olevat ansat ovat pelialueen ulkopuolella).


## Tietojen pysyväistallennus

### Tiedostot

Sovellus tallettaa high score -listan tiedostoon aina sovelluksen sulkeutuessa, ja 
hakee sen sieltä sovelluksen taas avautuessa. Tiedostojen käsittelystä vastaa luokka 
highScoreHandler. Ohjelma tarvitsee tietojen säilytyksen ainoastaan yhden 
tekstitiedoston, jossa jokainen tallennettu pistetieto on omalla rivillään. 

Tietojen formaatti: 

```
pelaajannimimerkki:000
```

Tiedoissa on siis kaksi kenttää. Ensimmäinen kenttä sisältää pelaajan nimimerkin, 
joka saa sisältää numeroita tai kirjaimia. Toinen kenttä sisältää tallennetut 
pisteet numerointa. Kentät on erotettu kaksoispisteellä. 