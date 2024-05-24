GPB мини-банк (frontend-level)
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

1. Клонировать приложение из удалённого репозитория.
2. Перейти в директорию с файлом проекта 'application.yml'.
```bash
cd $HOME/{директория с приложением}/korshunov-telegram-bot/src/main/resources
```
3. Открыть файл 'application.yml' в текстовом редакторе.
4. Добавить поля 'bot.name' и 'bot.token', а также значения к ним.
```yaml
bot.name: #имя вашего бота
bot.token: #токен вашего бота
```
5. Перейти в директорию приложения.
```bash
cd $HOME/{директория с приложением}/korshunov-telegram-bot
```
6. Запустить приложение.
```bash
./gradlew bootRun
```

## Контакты: <a name="контакты"></a>
По поводу вопросов и предложений обращайтесь :arrow_down:  


:speech_balloon: Telegram: [@dmitkorsh](https://t.me/dmitkorsh)  
:mailbox: Email: <a href="mailto:dashima.1998@mail.ru">dashima.1998@mail.ru</a>
