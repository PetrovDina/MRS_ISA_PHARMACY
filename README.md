# MRS-ISA Tim 12 - Informacioni sistem apoteka ⚕

https://travis-ci.com/BoJaN77799/mrs-isa-back.svg?branch=master

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

### HEROKU
## [Heroku front](https://mrs-isa-front-v2.herokuapp.com/#/)

