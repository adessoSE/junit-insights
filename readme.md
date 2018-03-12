[![GitHub issues](https://img.shields.io/github/issues/adessoAG/junit-insights.svg?style=flat-square)](https://github.com/adessoAG/junit-insights/issues)
[![GitHub forks](https://img.shields.io/github/forks/adessoAG/junit-insights.svg?style=flat-square)](https://github.com/adessoAG/junit-insights/network)
[![GitHub stars](https://img.shields.io/github/stars/adessoAG/junit-insights.svg?style=flat-square)](https://github.com/adessoAG/junit-insights/stargazers)
[![GitHub license](https://img.shields.io/github/license/adessoAG/junit-insights.svg?style=flat-square)](https://github.com/adessoAG/junit-insights)
![](https://img.shields.io/badge/Nice-100%25-brightgreen.svg)


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

Add the dependency to your project.

`//TODO`

Add @JUnitInsights to the test-classes you want to benchmark.
Be aware that ExtendsWith(SpringExtension::class) needs to be used as Runner-class.