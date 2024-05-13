GPB мини-банк 
===

![logo](pictures/logo.png)

_Наш сервис помогает людям распоряжаться своими деньгами, производить различные банковские операции. Клиент взаимодействует с сервисом через бота, который посылает http-запросы на сервис, который, в свою очередь, будет производить операции и формировать http-ответ.
Приложение находится на стадии разработки, однако мы уверены, 
что с нашим подходом к работе, это будет нечто совершенное._

## Наши преимущества:

- [x] **надёжность** :lock:
- [x] **безопасность** :cactus:
- [x] **скорость** :horse_racing:


## Наш стек:

:one: [Java](https://www.java.com/ru/)  
:two: [Spring Boot](https://spring.io/projects/spring-boot)  
:three: [PostgreSQL](https://www.postgresql.org/)  
:four: [Gradle](https://gradle.org/)

## Как это работает?

**Для любителей серьёзных переговоров и пафоса**:
```plantuml
@startuml diagram

title GPB mini-bank

Actor Пользователь as u
participant frontend as fl
participant middle as ml
participant backend as bl

u -[#red]> fl: выбрал команду
activate fl
fl -[#red]> ml: http-запрос
activate ml
ml -[#red]> bl: http-запрос
activate bl
bl -[#blue]> ml: http-ответ
deactivate bl
ml -[#blue]> fl: http-ответ
deactivate ml
fl -[#blue]> u: получил результат команды
deactivate fl

@enduml
```
**Для любителей расслабиться и поюморить (а также чёрных фонов):**

![service.png](pictures/service.png)

## Контакты:
По поводу вопросов и предложений обращайтесь :arrow_down:  


:speech_balloon: Telegram: [@dmitkorsh](https://t.me/dmitkorsh)  
:mailbox: Email: <a href="mailto:dashima.1998@mail.ru">dashima.1998@mail.ru</a>
