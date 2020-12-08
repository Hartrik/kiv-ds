
# CV02

[zadani-sem-prace-2.pdf](zadani-sem-prace-2.pdf)

Síť bank, ve které autonomně a náhodně probíhají bankovní operace.
Každý uzel jednou za čas provede operaci se svým sousedem.
A my do toho můžeme zvenčí spustit snapshot, ze kterého je vidět aktuální stav a že v tom systému nevznikají ani nemizí žádné peníze.

## Technologie
* Java (Jetty, ZeroMQ)
* Maven
* Vagrant

## Ovládání
1) Suštění serveru
    ```
    java -jar target/server-1.0.0-SNAPSHOT.jar
            <port REST serveru (pro spouštění snapshotů, vypínání...)>
            <seznam localhost:PORT oddělené čárkou k vytvoření (bind)>
            <seznam ADRESA:PORT oddělené čárkou k připojení (connect)>
    ```
    Adresy v seznamech tvoří páry.
    Tj. n-tý z prvního seznamu a n-tný z druhého seznamu tvoří obousměrné spojení mezi dvěma bankami.
    Spojení mezi bankami musí být vždy obousměrné.

2) Spuštění snapshotu na daném uzlu
    
    Je možné s náhodným id (vrátí se v odpovědi) nebo se zvoleným:
    ```
    curl -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://<ADRESA>:<PORT>/snapshot
    curl -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://<ADRESA>:<PORT>/snapshot/<ID>
    ```

3) Získání stavu snapshotu
    
    Musí být použito id snapshotu:
    ```
    curl -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://<ADRESA>:<PORT>/snapshot/<ID>/status
    ```

4) Ukončení serveru
    ```
    curl -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://<ADRESA>:<PORT>/shutdown
    ```

Komplexní příklady viz ukázky.

## Spuštění ukázek – localhost
1) `mvn package`
2) `./run-local_2.sh` – jednoduchý příklad jen se dvěma uzly.
3) `./run-local_4.sh` – složitější příklad se čtyřmi uzly.

Logy lze pak najít v tomto adresáři.
Nastavte delší prodlevy pokud se snapshot nestačí provést.

## Spuštění ukázky – více strojů (vagrant)
Stejná konfigurace jako `run-local_4.sh`.

1) `mvn package`
2) `wsl`
3) `vagrant up`
4) `vagrant ssh bank1`
    (pass: vagrant)
5) `cd /vagrant`
6) TODO
7) `exit`
8) `vagrant destroy -f`
