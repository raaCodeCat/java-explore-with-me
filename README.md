# java-explore-with-me

### Описание

Свободное время — ценный ресурс. Ежедневно мы планируем, как его потратить — куда и с кем сходить. Сложнее всего в 
таком планировании поиск информации и переговоры. Нужно учесть много деталей: какие намечаются мероприятия, свободны ли в этот момент друзья, как всех пригласить и где собраться.

Реализован бэкэнд приложения — афиши.
В этой афише можно предложить какое-либо событие от выставки до похода в кино и собрать компанию для участия в нём.

Приложение состоит из двух микросервисов:

- Основной сервис - содержать всё необходимое для работы продукта;
- Сервис статистики - хранящий количество просмотров и позволит делать различные выборки для анализа работы приложения.

## ТЗ

- [Техническое задание для API основного сервиса](./ewm-main-service-spec.json)
- [Техническое задание для API сервиса статистики](./ewm-stats-service-spec.json)

Помимо основного функционала по ТЗ был добавлен функционал "Комментарии".

### Описание функционала "Комментарии".
- Комментарии создаются только к опубликованным событиям.
- Комментарий ограничен 300 символами.
- Один пользователь может создать только один комментарий на событие.
- Комментарии можно лайка/дизлайкать. 
- Один пользователь может поставить только одну реакцию (лайк или дизлайк), при изменении реакции, она заменяет собой предыдущую.
- Реакцию можно отменить (удалить).
- Комментарии можно просматривать следующими способами:
  - По идентификатору комментария (один комментарий);
  - По идентификатору события, которому они были оставлены (список комментариев);
  - По идентификатору пользователя, который создавал комментарии (список комментариев).


[Тесты API рейтинга (дополнительная функциональность)](./postman/feature.json)