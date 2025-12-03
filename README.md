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

We don't make the whole system so we don't manage the coffee machine itself, there's no real point in adding a microservice for that.

1. Recording of cup quantities.
2. Presence analysis.
3. LEDs management.
4. Machine network status (Orchestrator): analysis of cup presence and level and sending to LEDs
    - BDD: name, building, condition, date last visit
5. (*Other notifications (software): deposit of a ticket to the admin, campus applications, screens at the entrance of the departments*)
6. test
