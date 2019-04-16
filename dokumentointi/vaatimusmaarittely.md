# Vaatimusmäärittely

## Sovelluksen tarkoitus

Kyseessä on hyppelypeli, jossa hiirtä siirtelemällä yritetään 
saada hahmo hyppimään yhä vain korkeammalle satunnaisgeneroituja 
tasoja pitkin. Pisteitä saa osuessaan uudelle alustalle ensimmäisen kerran.
Pisteitä voi lisätä ja peliä helpottaa myös erilaisilla boosteilla. 

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

![kayttoliittymaluonnos.jpg](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/kayttoliittymaluonnos.jpg)

## Perusversion toiminnallisuus

- Hahmo siirty sivusuunnassa hiirtä liikuttamalla ja hyppää osuessaan hyppyalustaan
- Pisteitä saa osuessaan hyppyalustaan
- Parhaat pisteet tallennetaan high score -listaukseen

## Jatkokehitysideoita

Perustoiminnallisuuden lisäksi peliä voidaan ajan salliessa täydentää esim. 
seuraavilla toiminnoilla: 

- Peli vaikeutuu sen edetessä 
  - tasot generoituvat kauemmas toisistaan ja/tai tippuvat nopeammin
  - sivu- ja pystysuunnassa liikkuvia tasoja
  - tappavia peliobjekteja, joihin ei saa osua
- Peliä voi helpottaa erilaisilla boosteilla
  - superhypyt
  - aseet joilla voi tuhota asioita joihin ei saa osua
  - pisteboostit, esim. tuplapisteet kymmenen sekunnin ajaksi
  - lisäelämät
- Kivaa pikku tilpehööriä:
  - peligrafiikka
  - mahdollisuus valita hahmon väri
  - taustakoristeet

