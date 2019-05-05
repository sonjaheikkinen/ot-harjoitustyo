# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on hyppelypeli, jossa hiirtä siirtelemällä yritetään 
saada hahmo hyppimään yhä vain korkeammalle satunnaisgeneroituja 
tasoja pitkin ja kerätä pisteitä.

## Käyttäjät

Pelissä on tällä hetkellä vain yksi käyttäjärooli, pelaaja. Jatkossa peliin 
on mahdollista lisätä myös admin-rooli.

## Käyttöliittymä

Sovellus koostuu useasta näkymästä: ensimmäisessä valitaan sisäänkirjautuminen
tai uuden tunnuksen luominen, jonka jälkeen avautuvat näitä toimintoja toteuttavat
näkymät. Kun käyttäjä on kirjatunut tai rekisteröitynyt, avautuu 
"aloitusnäkymä", josta valitaan uuden pelin aloitus, high score -listauksen tarkastelu 
tai ohjeiden lukeminen. Pelille ja ohjeille on omat näkymänsä. High Score -nappi 
vie näkymään, josta valitaan vielä halutaanko tarkastella henkilökohtaista vai yleistä 
listausta, jonka jälkeen näytetään valintaa noudattava listaus.

## Perusversion toiminnallisuus

### Pelitoiminnot 

- Hahmo siirty sivusuunnassa hiirtä liikuttamalla ja hyppää osuessaan hyppyalustaan.
- Pelaaja saa pisteitä osuessaan hyppyalustaan.
- Pelaaja saa lisää elämiä keräämällä boosteja, ja menettää niitä osumalla ansoihin.
- Jos elämät tippuvat nollaan, pelaaja kuolee seuraavasta osumasta.
- Pelin edetessä ansoja generoidaan yhä enemmän, jolloin peli vaikeutuu

### Muut toiminnot

- Pistetulokset tallennetaan tietokantaan, josta voidaan sitten hakea parhaat pisteet. 
- Pelaaja luo oman käyttäjätunnuksen ja salasanan, mikä mahdollistaa henkilökohtaisten tulosten tarkastelun.
- Pelaaja voi lisäksi tarkastella yleisiä high score -tietoja, ja lukea pelin ohjeet

## Jatkokehitysideoita

Perustoiminnallisuutta voidaan jatkokehityksessä täydentää esim. 
seuraavilla toiminnoilla: 

- Uusia tapoja vaikeuttaa peliä sen edetessä:
  - tasot generoituvat kauemmas toisistaan ja/tai tippuvat nopeammin
  - sivu- ja pystysuunnassa liikkuvia tasoja
- Erilaisia boosteja lisäelämien rinnalle
  - superhypyt
  - aseet joilla voi tuhota ansoja
  - pisteboostit, esim. tuplapisteet kymmenen sekunnin ajaksi
- peligrafiikka
- Admin-rooli ylläpitoa varten
- Jonkinlainen tietoturva salasanoihin, nykyinen toteutus on lähinnä kosmeettinen
- Tietokannan siivous säännöllisesti huonommista tuloksista, jottei se täyttyisi liikaa
- HighScore tietojen talletus internetiin, jolloin pelaajat voivat tarkastella myös globaaleja, eikä vain oman tietokoneensa sisäisiä tulostietoja
- Kirjautunut käyttäjä voi valita erilaisten teemojen väliltä, ja tieto valinnasta talletetaan tietokantaan, josta käyttöliittymä sitten hakee kulloinkin käytettävän css-tiedoston nimen.
