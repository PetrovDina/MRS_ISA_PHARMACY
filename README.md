# MRS-ISA Tim 12 - Informacioni sistem apoteka ⚕

[![Quality Gate Status][quality-gate-badge]][sonar-url]
![Build Status](https://travis-ci.com/BoJaN77799/mrs-isa-back.svg?branch=master)
[![Heroku frontend][frontend-badge]][frontend-url]
[![Heroku backend][backend-badge]][backend-url]


## Članovi tima
- Bojan Baškalo SW49/2018
- Dina Petrov SW52/2018
- Veljko Tošić SW55/2018
- Olivera Mirilović SW62/2018


## Pokretanje
### 1. Front 
 Kako bi se aplikacija mogla pokrenuti, neophodno je instalirati [Node.js](https://nodejs.org/en/), u okviru kog se dobija [npm](https://www.npmjs.com/).
 Pozicionirati se u folder _pharmacy_front_ i izvršiti komandu
```sh
npm install
```
Potom pokrenuti aplikaciju komandom
```sh
npm run dev
```


### 2. Back
Importovati projekat u workspace: Import -> Maven -> Existing Maven Project -> _pharmacy_
Instalirati sve dependency-je navedenih u pom.xml
Desni klik na projekat u project explorer -> Run as -> Java Application 


### Dodatne napomene:
Dodati su posebni odvojeni repozitorijumi radi uspešne Travis i SonarCloud konfiguracije:
- [Front end aplikacija](https://github.com/PetrovDina/mrs-isa-front)
- [Back end aplikacija](https://github.com/BoJaN77799/mrs-isa-back)



[quality-gate-badge]: https://sonarcloud.io/api/project_badges/measure?project=22434905a961c51b1d244289381f364488e90bcf&metric=alert_status
[sonar-url]: https://sonarcloud.io/dashboard?id=6aa1534bfc9e2e5495b99bfc6f7cbf1cec89d4ed
[backend-badge]: https://img.shields.io/badge/Heroku-backend-purple?logo=heroku
[backend-url]: https://mrs-isa-back.herokuapp.com/
[frontend-badge]: https://img.shields.io/badge/Heroku-frontend-purple?logo=heroku
[frontend-url]: https://mrs-isa-front-v2.herokuapp.com/
