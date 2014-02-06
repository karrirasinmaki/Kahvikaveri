Kahvikaveri
===========

Ohjelmoinnin harjoitustyö 2014 (aka. Javalabra). Kahvireseptivihko ja työkalu hyvän kahvin valmistukseen.

Ajettava android applikaatio löytyy juuresta "Android.apk".

Maven-eclipse-android konffausohjeet:
https://code.google.com/p/maven-android-plugin/

Käytössä olevat kirjastot ja pluginit:
- EclEmma
  - http://www.eclemma.org/
- android m2e (Maven integraatio Eclipselle)
  - http://rgladwell.github.io/m2e-android/
- Spoon (Visualisoi testien suoriutumista eri puhelimilla)
  - https://github.com/square/spoon
  
TODO:
=====
- EMMA (code coverage) toimimaan
  - Antaa ulos jo EMMA:n .ec ja .em filut, mutta ei osaa kääntää niistä HTML tiedostoa.
- Mutaatiotestejä
  - Ensin EMMA kuntoon
- Tagit toimimaan resepteissä
  - Tallennus/lataus
- Kahvivelho viisaammaksi
- UI päivityksiä
