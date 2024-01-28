<!-- TOC -->
* [Monitoring-Service](#monitoring-service)
* [Требования к программному обеспечению](#требования-к-программному-обеспечению)
    * [Зависимости Сборки из исходных кодов](#зависимости-сборки-из-исходных-кодов)
* [Сборка из исходных кодов и запуск](#сборка-из-исходных-кодов-и-запуск)
* [Архитектура приложения](#архитектура-приложения)
  * [Заметки к релизу](#заметки-к-релизу)
    * [2024-01-28](#2024-01-28)
<!-- TOC -->

# Monitoring-Service

Bеб-сервис для подачи показаний счетчиков отопления, горячей и холодной воды

# Требования к программному обеспечению

* Java 17+ Runtime

### Зависимости Сборки из исходных кодов

* Java 17+ SDK
* Apache Maven 3.9.5
* GNU Make 4.3+ (опционально)
* Доступ в интернет

# Сборка из исходных кодов и запуск

* Нужно клонировать репозиторий и перейти в корень проекта
* Запустить `mvn package`
* Если не возникло ошибок, то в подпапке `app-console/target` будет создан файл `monitoring-app-console-1.0.0.jar`
* Для запуска приложения следует выполнить команду `java -jar ./app-console/target/monitoring-app-console-1.0.0.jar`
* Будет запущено консольное приложение
* Консольное приложение сконфигурировано с пользователем-администратором `admin`, пароль `admin`

В проекте также используется maven wrapper и утилита make (с командами clean, build, run). Для использования данных
утилит требуется создать файл `.env` по примеру [.env.example](.env.example)

# Архитектура приложения

Все задачи приложения автором были разделены на 3 части: аудит (audit), авторизация (auth), работа с показаниями
счетчиков (core).
Самая стабильная часть задач приложения - работа с показаниями счетчиков. Другие две части задач могут быть подвержены
бОльшему влиянию в зависимости от клиентского интерфейса: консольное приложение или веб-приложение, или разные фреймверки.
Поэтому автор с учетом прогноза жизни проекта (дальнейших заданий курса) принял решение разделить архитектуру на 11 
модулей:

1. [app-console](app-console) финальные реализации интерфейсов домена, специфичные для консольного приложения
2. [audit](audit) частичные реализации интерфейсов поддомена аудита
3. [auth](auth) частичные реализации интерфейсов поддомена авторизации. В случае использования веб-фреймверка будут
   заменены на логику авторизации фреймверка
4. [core](core) реализации сценариев поддомена работы с показаниями счетчиков
5. [core-model](core-model) реализации доменных моделей: пользователь, показания счетчика
6. [db-memo](db-memo) реализации интерфейсов хранилища данных для хранения в памяти, основаны на java collection
7. [domain-audit](domain-audit) интерфейсы поддомена аудита
8. [domain-auth](domain-auth) интерфейсы поддомена авторизации
9. [domain-core](domain-core) интерфейсы поддомена работы с показаниями счетчиков
10. [domain-core-model](domain-core-model) базовые интерфейсы предметной области используемые во всех пакетах
11. [domain-core-repo](domain-core-repo) отделенные интерфейсы репозиториев из пакета domain-core для обеспечения только
    максимально целевых интерфейсов в пакетах, реализующих логику хранения данных

Базовые зависимости бизнес сценариев, моделей ядра предметной области представлены в графическом виде в файлах
папки [doc](doc)

## Заметки к релизу

### 2024-01-28

* Применение автором принципов Clean Architecture вызвало создание большого количества программных сущностей. Что по
  мнению и надежде автора даст бОльшее преимущество в простоте и скорости модификаций приложения при решении будущих
  заданий. Поэтому на данный момент автор решил пожертвовать объемом юнит-тестов, чтобы уложиться в срок дедлайна. Автор
  планирует постепенно дописывать тесты ядра, чтобы обеспечить лучшую надежность приложения.