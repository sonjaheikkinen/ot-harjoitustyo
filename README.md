# Ohjelmistotekniikka, kevät 2019

## Dokumentaatio

[vaatimusmäärittely](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)

[työaikakirjanpito](https://github.com/sonjaheikkinen/ot-harjoitustyo/blob/master/dokumentointi/tyoaikakirjanpito.md)

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
mvn compile exec:java -Dexec.mainClass=ui.JumpingGameUi
```

Windows PowerShellillä tarvitaan lisäksi lainausmerkit

```
mvn compile exec:java -"Dexec.mainClass=ui.JumpingGameUi"
```



