
# CV01

## Example configuration
```
java -jar multi-server-1.0.0.jar sequencer 8080 http://localhost:8081
java -jar multi-server-1.0.0.jar shuffler 8081 http://localhost:8082 http://localhost:8083
java -jar multi-server-1.0.0.jar server 8082
java -jar multi-server-1.0.0.jar server 8083

java -jar client-1.0.0.jar http://localhost:8080 50
```
