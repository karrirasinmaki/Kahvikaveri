Kahvikaveri
===========

Ohjelmoinnin harjoitustyö 2014 (aka. Javalabra). Kahvireseptivihko ja työkalu hyvän kahvin valmistukseen.

Ajettava android applikaatio löytyy juuresta "coffeebuddy.apk".

Testien rivikattavuusraportit löytyy: "Dokumentointi/raportit"
- En onnistunut vielä saamaan staattista html sivustoa kattavuusraportille
- codecoverage.png (Logiikkaluokan yleiskatsaus)
- koodilaaturaportti.pdf (PDF raportti. Siellä on paljon tyhjiä kenttiä, SonarQube ei osaa oikeen tulkita projektin komponentteja ja niiden kytköksiä)

Maven-eclipse-android konffausohjeet:
https://code.google.com/p/maven-android-plugin/

Käytössä olevat kirjastot ja pluginit:
- SonarQube (Koodin analysointi)
- JaCoCo (Testien rivikattavuus)
- android m2e (Maven integraatio Eclipselle)
  - http://rgladwell.github.io/m2e-android/
  
TODO:
=====
- Code coverage toimimaan automaattisemmin
- Mutaatiotestejä
- Tagien tallennus/lataus
- Kahvivelho viisaammaksi
- UI päivityksiä
