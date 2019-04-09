# Ohjelmistotekniikka, kevät 2019, harjoitustyö

Harjoitustyön aiheena on pelisovellus, jossa hiirtä liikuttelemalla hypätään alustalta 
toiselle ja yritetään olla tippumatta. Jatkossa tarkoituksena on kehittää peliin 
myös pisteiden keräämistoiminto, huippupisteiden tallennus ja lisäominaisuuksia, esim. 
pisteboosteja, liikkuvia alustoja ja tappavia esineitä.

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/sonjaheikkinen/ot-harjoitystyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Työaikakirjanpito](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/tyoaikakirjanpito.md)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

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

###Checkstyle

Tiedoston [checkstyle.xml](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/JumpingGame/checkstyle.xml) 
määrittelemät tarkistukset suoritetaan komennolla 

```
mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoiukset selviävät avaamalla selaimella tiedosto 
*target/site/checkstyle.html*




