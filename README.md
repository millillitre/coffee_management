# Automation of the INSA's coffee management

We want to develop a Web application (Proof-of-Concept) for managing  INSA's coffee machines. Through software services (microservices), the application retrieves data of sensors (temperature, presence, ...), and according to the values of the retrieved data, actions on actuators can be triggered. This application must allow:

| Sensors | Actuators | Specificities | (Add'on) |
| ------- | --------- | ------------- | --------- |
| Cup quantity | A LED in front of each departments | green : ok, blue: degraded service (no more cup), red: out of order | (Infos can also be communicated throw campus application or screens presents in the hall of the departments) |
| Presence | // |  orange : too much people | // |

## Asked work

- [ ] Design an application based on microservices
- [ ] Implement the different services and services calls
- [ ] Implement a web interface for viewing the history of actions
- [ ] Language to use : Java.
- [ ] Framework to use : SpringBoot

## Microservice architecture

![Alt text](docs/Architecture.png "Project Architecture")

We don't make the whole system so we don't manage the coffee machine itself, there's no real point in adding a microservice for that.

1. Recording of cup quantities.
2. Presence analysis.
3. LEDs management.
4. Machine network status (Orchestrator): analysis of cup presence and level and sending to LEDs
    - BDD: name, building, condition, date last visit
5. (*Other notifications (software): deposit of a ticket to the admin, campus applications, screens at the entrance of the departments*)

## Database architecture

- A table `machine`: storage of the static infos of the coffee machines
- A table `sensors`: data storage for the sensors (Do wee need to make tables or not?)
- A table `actions_history`: storage of the actionned that have been made before
- A table `actuators`?
- (A table `notifications`: storage of the notifications that have been sent (admin, screens))

## Reporting

- To return on 21/01
- Add:
  - [ ] Architecture diagram (microservices)
  - [ ] Class Diagram
  - [ ] Use case diagram
