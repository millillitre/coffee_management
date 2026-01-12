# Automation of the INSA's coffee management

We want to develop a Web application (Proof-of-Concept) for managing  INSA's coffee machines. Through software services (microservices), the application retrieves data of sensors (temperature, presence, ...), and according to the values of the retrieved data, actions on actuators can be triggered. This application must allow:

| Sensors | Actuators | Specificities | (Add'on) |
| ------- | --------- | ------------- | --------- |
| Cup quantity | A LED in front of each departments | green : ok, blue: degraded service (no more cup) | (Infos can also be communicated throw campus application or screens presents in the hall of the departments) |
| Presence |  |  orange : too much people | // |

## Asked work

- [ ] Design an application based on microservices
- [ ] Implement the different services and services calls
- [ ] (optionnal: Implement a web interface for viewing the history of actions)
- [ ] Language to use : Java.
- [ ] Framework to use : SpringBoot

## Microservice architecture

![Alt text](docs/Architecture.png "Project Architecture")

We don't make the whole system so we don't manage the coffee machine itself, there's no real point in adding a microservice for that. If we wanted to do something more proper, we would had a microservice for machine instead of using the orchestrator as a way to obtain teh machines from the database.

1. Recording of cup quantities.

    ``` bash
    curl -X POST "http://localhost:8081/api/cup-Ms?machineId=1&value=10" #pour ajouter uen valeur de nombre de cup à la machine d'id 1 (GEI)
    curl http://localhost:8081/api/cup-Ms/1
    ```

2. Presence analysis.

    ``` bash
    curl -X POST "http://localhost:8082/api/presence-Ms?machineId=1&value=3" # Enregistrer une valeur
    curl http://localhost:8082/api/presence-Ms/history/1 # Récupérer l'historique
    ```

3. LEDs management.

    ``` bash
    curl -X POST "http://localhost:8083/api/led-Ms/update?machineId=1&cupQuantity=5&presenceValue=15"
    curl http://localhost:8083/api/led-Ms/1"
    ```

4. Machine network status (Orchestrator): analysis of cup presence and level and sending to LEDs
    - BDD: name, building, condition, date last visit

## Database architecture

- A table `machine`: storage of the static infos of the coffee machines:
  - machine_id
  - building
  - condition :'operational', 'degraded', 'out_of_order'
  - last_visit

``` SQL
-- Table `machine`
CREATE TABLE IF NOT EXISTS machine (
    machine_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    building VARCHAR(50) NOT NULL,
    status ENUM('operational', 'degraded', 'out_of_order') NOT NULL,
    last_visit DATETIME
);
```

- A table `cup_sensor`: data storage for the cup sensor:
  - sensor_id
  - machine_id (foreign key)
  - value
  - timestamp

``` SQL
-- Table `cup_sensor`
CREATE TABLE IF NOT EXISTS cup_sensor (
    sensor_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    machine_id BIGINT NOT NULL,
    value INT NOT NULL,
    timestamp DATETIME NOT NULL,
    FOREIGN KEY (machine_id) REFERENCES machine(machine_id)
);
```

- A table `presence_sensor`: data storage for the presence sensor:
  - sensor_id
  - machine_id (foreign key)
  - value
  - timestamp

``` SQL
  CREATE TABLE IF NOT EXISTS presence_sensor (
    sensor_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    machine_id BIGINT NOT NULL,
    value INT NOT NULL,  -- Nombre de personnes détectées
    timestamp DATETIME NOT NULL,
    FOREIGN KEY (machine_id) REFERENCES machine(machine_id)
);
```

- A table `actions_history`: storage of the actions that have been made before:
  - action_id
  - machine_id (foreign key)
  - status :'green', 'blue', 'red', 'orange', 'off',
  - timestamp

``` SQL
  CREATE TABLE IF NOT EXISTS actions_history (
      action_id BIGINT PRIMARY KEY AUTO_INCREMENT,
      machine_id BIGINT NOT NULL,
      status ENUM('green', 'blue', 'red', 'orange', 'off') NOT NULL,
      timestamp DATETIME NOT NULL,
      FOREIGN KEY (machine_id) REFERENCES machine(machine_id)
  );
```

## Discovery and configuration

### Discovery service (Eureka)

The discovery service centralises and provides external configurations (application.yml, application.properties files, etc.) for all microservices and the discovery service. It must start first, as other services (including the discovery service) depend on it to retrieve their configuration.

### Configuration service

It allows microservices to register and discover each other. It starts after the Config Server, as it needs to retrieve its own configuration (ports and cluster settings).

## Usage

1. Launch [Discovery Application](/DiscoveryMS/src/main/java/fr/insa/coffee/DiscoveryMS/DiscoveryMsApplication.java). The discovery service is accessible here: [http://localhost:8761/](http://localhost:8761/)

2. Launch [Config Server Application](/config-server/src/main/java/fr/insa/coffee/config_server/ConfigServerApplication.java)

3. Launch Microservices:
   - [Cup application](/CupMS/src/main/java/fr/insa/coffee/CupMS/CupMsApplication.java)
   - [Presence Application](/PresenceMS/src/main/java/fr/insa/coffee/PresenceMS/PresenceMsApplication.java)
   - [LED Application](/LEDMS/src/main/java/fr/insa/coffee/LEDMS/LEDMsApplication.java)