
# CV01

Pro zjednodušení vývoje byl místo tří serverů vytvořen jeden, který při spuštění bere parametr,
který určí jaký servlet se má použít.

## Example configuration
```
java -jar multi-server-1.0.0.jar sequencer 8080 http://localhost:8081
java -jar multi-server-1.0.0.jar shuffler 8081 http://localhost:8082 http://localhost:8083
java -jar multi-server-1.0.0.jar server 8082
java -jar multi-server-1.0.0.jar server 8083

java -jar client-1.0.0.jar http://localhost:8080 50
```

## Spuštění
1) build.cmd
2) vagrant up
3) vagrant ssh sequencer
4) cd /vagrant
5) java -jar client-1.0.0.jar http://10.0.2.10:8080 20

logy lze najít v tomto adresáři