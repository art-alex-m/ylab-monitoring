# 3. Monitoring-Service. Домашнее задание Servlet API и AOP

Необходимо обновить сервис, который вы разработали в первом задании согласно следующим требованиям и ограничениям

### Требования:

- Все взаимодействие должно теперь осуществляться через отправку HTTP запросов
- Сервлеты должны принимать JSON и отдавать также JSON
- Для сериализации и десереализации использовать jackson
- Использовать понятное именование эндпоинтов
- Возвращать разные статус-коды
- Добавить DTO (если ранее не было заложено в логике)
- Для маппинга сущностей в дто использовать MapStruct
- Реализовать валидацию входящих ДТО
- Аудит переделать на аспекты через аннотацию
- Также реализовать на аспектах логирование выполнения любого метода (с замером времени выполнения)
- Сервлеты должны быть покрыты тестами

Доп. материалы: https://drive.google.com/drive/folders/17NNSgfRkzvptE7-74nU20tI8pBP1VRdu?usp=sharing