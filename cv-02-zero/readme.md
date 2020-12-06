
# CV02

[zadani-sem-prace-2.pdf](zadani-sem-prace-2.pdf)

## Technologie
* Java
* Maven
* Vagrant

## Příklad konfigurace (localhost)
TODO

## Spuštění ukázek – localhost
1) `mvn package`
2) `./run-local_2.sh`
3) `./run-local_4.sh`

Nastavte delší prodlevy pokud se snapshot nestačí provést.

## Spuštění ukázky – více strojů (vagrant)
1) `mvn package`
2) `wsl`
3) `vagrant up`
4) `vagrant ssh bank1`
    (pass: vagrant)
5) `cd /vagrant`
6) TODO
7) `exit`
8) `vagrant destroy -f`

Logy lze pak najít v tomto adresáři.
