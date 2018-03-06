# JUnit-Insigths for Spring

JUnit5-Insights ist eine Erweiterung für Spring, die
1. für jede Testmethode / jede Testklasse mitzählt, wie viel Zeit für setup / execution / teardown benötigt wurde
2. daraus dann einen schönen Report erstellt (erstmal plain HTML, später vielleicht Angular). 

Hintergrund: wenn man Integrationstests mit Spring baut (z.B. mit @SpringBootTest), wird für Tests manchmal ein
eigener Spring ApplicationContext hochgefahren und manchmal nicht. Von außen betrachtet sieht es so aus, 
als würde der Test ewig dauern, obwohl die eigentliche Testausführung schnell geht. Um das transparent zu machen, 
soll der Report generiert werden. Wenn man es hinbekommt, kann man die Funktionalität vielleicht noch ergänzen, 
so dass man im Report sehen kann, an welchen Stellen ein Spring-Context hochgefahren wurde.

# How to use
