GPB мини-банк 
===

![logo](images/logo.png)

_Мы - один из крупнейших универсальных банков России, приступаем к разработке нового проекта - GPB мини-банк. Наш сервис помогает людям распоряжаться своими деньгами так, как им хочется._

Для получения более точной информации воспользуйтесь навигацией:
- [Наши преимущества](#наши-преимущества)
- [Наш стек](#наш-стек)
- [Как это работает?](#как-это-работает?)
- [Quick Start](#quick-start)
- [Контакты](#контакты)

## Наши преимущества: <a name="наши-преимущества"></a>

- [x] **надёжность** :lock:
- [x] **безопасность** :cactus:
- [x] **скорость** :horse_racing:


## Наш стек: <a name="наш-стек"></a>

:one: [Java](https://www.java.com/ru/)  
:two: [Spring Boot](https://spring.io/projects/spring-boot)  
:three: [PostgreSQL](https://www.postgresql.org/)  
:four: [Gradle](https://gradle.org/)

## Как это работает? <a name="как-это-работает?"></a>

Клиент взаимодействует с сервисом через бота, который посылает http-запросы на сервис, который, в свою очередь, будет производить операции и формировать http-ответ. Более точный принцип работы смотри ниже :arrow_down:

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

![service.png](images/service.png)

## Quick Start: <a name="quick-start"></a>
_Для Linux:_
```bash
# клонировать приложение из удалённого репозитория
# перейти в директорию с файлом 'application.yml'
cd $HOME/{директория с приложением}/korshunov-telegram-bot/src/main/resources

# открыть файл 'application.yml' в текстовом редакторе
# поменять значения полей 'bot.name' и 'bot.token'
bot.name: #имя созданного вами бота
bot.token: #токен созданного вами бота

# перейти в директорию приложения
cd $HOME/{директория с приложением}/korshunov-telegram-bot

# запустить приложение командой
./gradlew bootRun

# пользоваться =)
```

## Контакты: <a name="контакты"></a>
По поводу вопросов и предложений обращайтесь :arrow_down:  


:speech_balloon: Telegram: [@dmitkorsh](https://t.me/dmitkorsh)  
:mailbox: Email: <a href="mailto:dashima.1998@mail.ru">dashima.1998@mail.ru</a>
