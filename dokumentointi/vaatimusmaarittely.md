# Vaatimusmäärittely

## Sovelluksen tarkoitus

Kyseessä on avaruusteemainen hyppelypeli, jossa hiirtä siirtelemällä yritetään 
saada hahmo (avaruuslimaolio) hyppimään yhä vain korkeammalle satunnaisgeneroituja 
tasoja (asteroideja) pitkin. Pisteitä saa korkeuden mukaan, ja peli vaikeutuu 
sen edetessä. Pisteitä voi lisätä ja peliä helpottaa myös erilaisilla boosteilla. 

## Käyttäjät

Alustavasti peliin on tulossa vain yksi käyttäjärooli, pelaaja.

## Käyttöliittymä

Sovellus koostuu useasta näkymästä: ensimmäisessä valitaan uusi peli, high score
tai ohjeiden lukeminen, toinen näkymä on pelin ohjeille, kolmannessa asetetaan 
nimimerkki high score -listaa varten ja mahdollisia muita asetuksia, neljäs
on itse peli, ja viides on high score -listaus. Lisäksi mahdollisesti oma näkymä 
esim. pelin introlle ja try again -tyyppinen näkymä kuollessa. Mikäli peliin tuotetaan 
kirjautumistoiminnallisuus tai erilliset pelitallennukset, tarvitaan näille 
mahdollisesti vielä yksi lisänäkymä.

## Perusversion toiminnallisuus

- Hahmo siirty sivusuunnassa hiirtä liikuttamalla ja hyppää osuessaan hyppyalustaan
- Hyppyalusta katoaa / rikkoutuu siihen osuttaessa
- Pisteitä saa osuessaan hyppyalustaan ja lisäksi korkeuden lisääntyessä
- (Parhaat pisteet tallennetaan high score -listaukseen)

## Jatkokehitysideoita

Perustoiminnallisuuden lisäksi peliä voidaan täydentää esim. seuraavilla 
toiminnoilla: 

- Peli vaikeutuu sen edetessä 
  - tasot generoituvat kauemmas toisistaan
  - sivu- ja pystysuunnassa liikkuvia tasoja
  - radioaktiivisia asteroideja / avaruuspetoja, joihin ei saa osua
- Peliä voi helpottaa erilaisilla boosteilla
  - superhypyt
  - aseet joilla voi tuhota asioita joihin ei saa osua
  - pisteboostit, esim. tuplapisteet kymmenen sekunnin ajaksi
  - lisäelämät
- Kirjautumistoiminnallisuus, jolloin henkilökohtaisten ennätysten seuraaminen toimii
- Kivaa pikku tilpehööriä:
  - mahdollisuus valita hahmon väri
  - muuttuvia taustakoristeita esim. planeettoja
- (High score, jos ei ehditä toteuttaa perusversioon)

