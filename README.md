# FO: Forenklet deploy

[![Greenkeeper badge](https://badges.greenkeeper.io/navikt/fo-forenklet-deploy.svg)](https://greenkeeper.io/)

[![CircleCI](https://circleci.com/gh/navikt/fo-forenklet-deploy/tree/master.svg?style=svg)](https://circleci.com/gh/navikt/fo-forenklet-deploy/tree/master)

En applikasjon som viser oversikt over versjoner av et team sine applikasjoner, per miljø.
Applikasjonen henter ut data fra ulike baksystemet og presenterer endringene mellom ulike
miljøer ved å presentere git-commits mellom ulike versjoner.

Målet for applikasjonen er å gi en oversikt over hva som blir rullet ut til en hver tid,
spesielt når man har en stor applikasjonsportefølje (f.eks. mange microservices) og i
tillegg flere test-miljøer man bruker.

Applikasjonen blir til en hver tid deployet til et test-miljø i heroku og kan testes ut
ved å besøke https://forenklet-deploy-staging.herokuapp.com/

## Utvikling

Applikasjonen er satt opp for å kunne kjøre opp både med direkte tilkobling mot
eksterne tjenester, eller ved å bruke mockdata. 

Backenden er satt opp med 2 main metoder. I `src/main/kotlin` finner man en Main klasse under 
`no.nav.fo.forenkletdeploy.Main`. Denne starter opp applikasjonen med "prod-oppsett". Denne vil default
sette opp proxy-instillinger slik at ting vil fungere både i produksjon og ved utvikling lokalt. Den vil og
prøve å hente data fra reelle baksystemer. Proxy-instillinger kan disables ved å sette systemvariabelen
`webproxy.enabled` til `false`.

Om man ønsker å kjøre opp backenden med Mock-data kan man starte Main-metoden man finner under `src/test/kotlin`.
Denne starter opp appen uten proxy-instillinger og med alle baksystemer mocket ut. Om man bruker mock eller ikke
defineres av om spring-profilen `mock` er aktivert. Dette kan endres i `application.yml` som man finner under resources.

For å starte opp frontenden lokalt kjør `npm install && npm start` i `src/frontend`. Frontenden kommer også med egne
mockdata, slik at man kan utvikler på den uten å trenge å starte opp backeden. For å utvikle med mockdata kan man starte
opp frontenden med `npm run start-mock` i `src/frontend`. 

Det er lettest å starte backend og frontend helt uavhengig av hverandre når man skal teste lokalt. 

Mockdataen skal til en hver tid være representativ
for reelle data. Heroku starter opp en instans av alle pull-requestene, hvor den bruker mockdata.
Slik at man kan få verifisert frontend-endringer funksjonelt før man merger.

## Henvendelser

Spørsmål knyttet til koden eller prosjektet kan rettes til Team Pus.

For eksterne kontakt en av følgende:

* Steffen Hageland (steffen.hagelan@nav.no)
* Jan Berge Ommedal (jan.berge.ommedal@nav.no)

For NAV-ansatte kan henvendelser sendes via slack i kanalen #pus
