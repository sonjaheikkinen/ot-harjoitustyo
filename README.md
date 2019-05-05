# Ohjelmistotekniikka, kevät 2019, harjoitustyö

Harjoitustyön aiheena on pelisovellus, jossa hiirtä liikuttelemalla hypätään alustalta 
toiselle ja yritetään olla tippumatta. Pelaaja saa pisteitä hypätessään ensimmäisen 
kerran hyppyalustalta, ja parhaat tulokset talletetaan käyttäjän tarkasteltavaksi. 
Peliä vaikeuttavat tappavat ansat, joilta voi suojautua lisäelämillä. 

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Käyttöohje](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/kayttoohje.md)

[Testausdokumentti](https://github.com/sonjaheikkinen/ot-harjotustyo/blob/master/testaus.md)

[Työaikakirjanpito](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/tyoaikakirjanpito.md)

## Releaset

[Viikko 6](https://github.com/sonjaheikkinen/ot-harjoitustyo/releases/tag/viikko6)

[Viikko 5](https://github.com/sonjaheikkinen/ot-harjoitustyo/releases/tag/viikko5)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testien suorittamiseksi tulee tiedoston test.txt sijaita projektin juurikansiossa.

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

### Suoritus

Ohjelma suoritetaan komentoriviltä komennolla

```
mvn compile exec:java -Dexec.mainClass=fi.sonjaheikkinen.gui.JumpingGameGui
```

Windows PowerShellillä tarvitaan lisäksi lainausmerkit

```
mvn compile exec:java -"Dexec.mainClass=fi.sonjaheikkinen.gui.JumpingGameGui"
```

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon *target* suoritettavan jar-tiedoston 
JumpingGame-1.0-SNAPSHOT.jar

Jar-tiedoston suorittamiseksi tulee tiedostojen highScores.txt ja 
stylesheet.css sijaita samassa kansiossa jar-tiedoston kanssa.

### Checkstyle

Tiedoston [checkstyle.xml](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/JumpingGame/checkstyle.xml) 
määrittelemät tarkistukset suoritetaan komennolla 

```
mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoiukset selviävät avaamalla selaimella tiedosto 
*target/site/checkstyle.html*

### Javadoc

Javadoc generoidaan komennolla 

```
mvn javadoc:javadoc
```



