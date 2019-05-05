# Arkkitehtuurikuvaus

## Rakenne

Ohjelman pakkausrakenne on yksinkertainen. Hierarkian ylimpänä on graafisen 
käyttöliittymän muodostava pakkaus gui. Seuraavalla tasolla ovat laskennan suorittava 
pakkaus logic, ja long-muuttujan kapseloiva pakkaus other. Kolmannella tasolla ovat 
logic-pakkauksen käyttämät pakkaukset dbhandling, joka  
käsittelee tiedonsiirtoa tietokannan ja ohjelman välillä, sekä domain, joka sisältää 
pelin "käsitteet". Pakkausrakenne on kuvattu alla olevassa kaaviossa. Kaikki pakkaukset 
ovat pakkauksen fi.sonjaheikkinen sisällä, vaikkei sitä ole kaavion piirretty.

![pakkausrakenne.jpg](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/pakkausrakenne.jpg)

Luokkien väliset suhteet muodostavat verkkomaisen kaavion:

![pakkauskaavio.jpg](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/pakkauskaavio.jpg)

Kaavioon on merkitty pakkaukset, luokkien väliset pysyvät suhteet sekä oleellisin 
riippuvuussuhde.

## Käyttöliittymä

Käyttöliittymä sisältää yhdeksän näkymää.

- aloitusnäkymä1, josta valitaan kirjautuminen tai rekisteröityminen
- kirjautumisnäkymä
- rekisteröitymisnäkymä
- aloitusnäkymä2 (kirjautumisen/rekisteröitymisen jälkeen), valinnat uusi peli, ohjeet, high score
- pelinäkymä
- pelin loppumisnäkymä
- peliohjeet
- high score -valintanäkymä (henkilökohtainen vai kaikki)
- high score -listaus

Jokainen näkymä on toteutettu omana scene-olionaan, ja vain yksi näkymä on kerrallaan 
sijoitettuna stageen. Pelinäkymä, pelin loppumisnäkymä ja high score generoidaan 
uudestaan joka kutsumiskerralla vastaamaan ohjelma- ja pelilogiikassa kutsumishetkellä 
olevia tietoja. Käyttöliittymän muodostamisesta vastaa pakkaus gui.

### pakkaus: gui

Pakkaus gui rakentaa graafisen käyttöliittymän. Se sisältää neljä luokkaa. Ohjelman 
käynnistää luokka JumpingGameGui, joka alustaa ohjelmalogiikan, ja ryhtyy sitten 
rakentamaan sen pohjalta käyttöliittymää. Se luo oliot luokista SceneConstructor ja 
StageHandler. SceneConstructor vastaa yksittäisten näkymien, scenejen, luomisesta, kun 
taas stageHandler hallitsee scenejen asettamiseen näkyväksi ikkunaan, stageen. Luokat 
vaihtavat näkymää käyttäjän tekemien toimintojen (esim. napinpainallus) perusteella, 
mutta hyödyntävät myös ProgramLogic-oliota, jolta saadaan monia ruudulle kirjoitettavia 
tietoja, kuten pelaajan nimi ja tuloslistaus. Mikäli stagessa on pelinäkymä, luodaan 
hallitsee käyttöliittymän toimintaa luokka GameScreenHandler, joka kutsuu GameLogicin 
metodia updateGame, ja piirtää sitten pelinäkymää ruudulle GameLogicista saatujen 
tietojen perusteella.

## Sovelluslogiikka

Sovelluslogiikan pohjan muodostavat luokat ProgamLogic, GameLogic ja GameObject, 
joiden suhteet muihin luokkiin on kuvattu yllä kohdassa rakenne. 

### pakkaus: logic

Pakkaus logic sisältää kaksi luokkaa: ohjelmalogiikasta vastaavan luokan ProgramLogic 
ja pelilogiikasta vastaavan luokan gameLogic. ProgramLogic kommunikoi käyttöliittymän 
ja DatabaseHandlerin kanssa ja sen päätehtävänä on pitää kirjaa kulloisenkin peli-instassin 
tiedoista sekä ohjata dbHandlerin tiedonhakuja ja tiedonlisäystä. GameLogic kommunikoi luokan 
GameScreenHandler ja pakkauksen domain kanssa. Se ohjaa yksittäisen peli-instassin 
toimintaa, ja luodaan aina uudestaan uuden pelin käynnistyessä. 

### pakkaus: domain

Pakkaus domain sisältää luokan GameObject. Jokainen luokan instanssi vastaa yksittäistä 
pelioliota, "spritea", joiden liikkeitä ja muutoksia kontrolloi luokka GameLogic.

### pakkaus: other

Pakkaus other sisältää vain yhden luokan, LongValue, joka kapsuloi long-tyyppisen 
muuttujan, jotta sitä voidaan käyttää luokan animationTimer sisällä. 


### Päätoiminnallisuudet

#### Kirjautuminen ja rekisteröityminen

Käyttäjän painaessa kirjautumisnappia avautuu kirjautumisnäkymä. Käyttöliittymä 
välittää käyttäjän syöttämät tiedot ProgramLogicille, joka sitten kysyy 
databaseHandlerilta, löytyykö tietoihin sopivaa käyttäjää tietokannasta. Jos löytyy, 
asetetaan käyttäjän nimimerkki pelaajaksi ProgramLogikiin, muuten tulostetaan käyttäjälle 
tieto väärästä nimestä tai salasanasta.

Uuden käyttäjän luominen toimii vastaavasti, mutta tällä kertaa tarkistetaan sekä 
nimen, että salasanan formaatti, ja lisäksi varmistetaan, ettei rekisteröitävää 
nimimerkkiä ole jo tietokannassa.

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

#### Piilotettu vaikeustaso

Pelissä on piilotettu vaikeustaso. GameLogicilla on luokkamuuttuja level, joka 
kasvaa tasaisesti pisteiden kerääntyessä. Mitä isompi vaikeustaso, sitä enemmän ansoja 
pelialueelle generoidaan. Tämä tapahtuu kasvattamalla metodin moveTraps liikuttamien 
ansojen määrää (paikallaan olevat ansat ovat pelialueen ulkopuolella).

#### Pelin toiminnallisuus yleisesti

GameScreenHandler kutsuu säännöllisin väliajoin metodia updateGameScreen.
UpdateGameScreen kutsuu GameLogicin metodia updateGame parametrilla 
elapsedTimeInSeconds (edellisestä päivityksestä kulunut aika sekunteina). Tämän 
perusteella GameLogic sitten laskee jokaiselle GameObject-oliolle uuden sijainnin. 
Sen jälkeen GameLogic tarkastaa törmäävätkö jotkin oliot ja tarvittaessa päivittää 
sijaintia, nopeutta tai muita tietoja. Lisäksi tarkistetaan piilotettu vaikeustaso 
ja pistemäärä. UpdateGameScreen päivittää seuraavaksi infotekstin, ja tämän jälkeen 
kutsuu metodia render, joka hakee jokaisen peliobjektin tämänhetkiset sijainti- ja 
kokotiedot, ja piirtää niiden mukaisia kuvioita ruudulle. 

## Tietojen pysyväistallennus

### Tietokanta

Sovelluksen käyttämässä tietokannassa on kaksi taulua, Scores ja Users. Ensimmäiseen 
tauluun talletetaan pistetuloksia. Siinä on sarakkeet name ja points, joihin talletetaan 
pelaajan nimi ja saavutetut pisteet. Toiseen tauluun talletetaan käyttäjätunnuksia. 
Siinä on sarakkeet name ja password, joihin talletaan käyttäjätunnukset ja salasana.

Tietojen talletusta hoitaa luokka databaseHandler, jonka metodeita kutsuu 
ohjelmalogiikkaa hoitava ProgramLogic.

#### Rekisteröinti ja kirjautuminen

Pelaajan luodessa uutta käyttäjätunnusta tarkistetaan, ettei samoilla tiedoilla ole 
jo käyttäjää taulussa users, ja jos ei, lisätään tauluun uusi käyttäjä. Kirjautuessa 
taas tarkistetaan löytyykö annetuilla tiedoilla käyttäjää taulusta users, ja jos 
löytyy, asetetaan käyttäjänimi ProgramLogiciin tämänhetkiseksi pelaajaksi. 

#### Pisteiden tallennus 

Pelin päättyessä ProgramLogic käskee databaseHandleria kirjaamaan uusimmat pisteet 
kirjautuneen käyttäjän nimellä varustettuna tauluun Scores.

#### High Score -listaus

High Score -listauksen tuottaa ProgramLogicin metodi updateHighScore, joka kutsuu 
databaseHandleria hakemaan käyttäjän tekemän valinnan mukaan joko kymmenen parasta 
käyttäjän henkilökohtaista tulosta, tai kymmenen yleisesti parasta tulosta taulusta 
Scores. 

## Ohjelman rakenteeseen jääneet heikkoudet

### Käyttöliittymä

Käyttöliittymästä vastaa useampi luokka. Luokkien väliset suhteet ovat kuitenkin melko 
verkkomaiset ja sekavat, eikä tehtäväjaottelukaan ole ihan yksiselitteinen. Etenkin 
SceneConstructoria ja StageHandleria pitäisi refaktoroida, sillä tällä hetkellä iso 
osa scenejen vaihdon hallinnasta tapahtuu SceneConstructorissa, kun taas 
StageHandlerissa sijaitsevat lähinnä varsinaiset getterit ja setterit, mutta se ei 
oikeastaan tee mitään. Luokka SceneConstructor kaipaisi muutenkin refaktorointia, 
sillä kaikki sen metodit ovat todella pitkiä ja sisältävät toisteista koodia.

### Suuret luokat

Ohjelman luokat ovat melko suuria, ja niiden tehtäviä voisikin luultavasti jaotella 
vielä alaluokiksi. Esimerkiksi GameLogicin voisi jakaa niin, että yksi luokka pitäisi 
kirjaa objektien liikkeestä ja sijainneista, kun taas toinen käsittelisi niiden 
väliset törmäykset, ja kolmas muistaisi piilotetun tason ja pistemäärän. Samanlaista 
refaktorointia kaipaisivat varmaankin myös ProgramLogic ja GameScreenHandler.
