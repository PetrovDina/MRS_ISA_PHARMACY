# MRS-ISA Tim 12 - Informacioni sistem apoteka ⚕

![Build Status](https://travis-ci.com/BoJaN77799/mrs-isa-back.svg?branch=master)
[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=BoJaN77799_MRS_ISA_PHARMACY)](https://sonarcloud.io/dashboard?id=BoJaN77799_MRS_ISA_PHARMACY)

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
- [Front end repozitorijum](https://github.com/PetrovDina/mrs-isa-front)
- [Back end repozitorijum](https://github.com/BoJaN77799/mrs-isa-back)

## [Heroku front](https://mrs-isa-front-v2.herokuapp.com/#/)

