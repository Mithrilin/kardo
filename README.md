# Kardo
Данное приложение позволяет пользователям подавать заявки на участие в различных соревнованиях или стать зрителем в них.
Выкладывать видео-ролики, а так же комментировать и лайкать их.

_**Используемый стэк**_: Spring Boot, Spring Data JPA, Spring Security, SQL, PostgreSQL, Hibernate, Lombok, Mapstruct, 
Swagger, Flyway.  

Приложение является групповым. Соразработчик [Дмитрий Деминцев](https://github.com/DmitriyDemintsev)

## Функциональность
Приложение представляет собой, своего рода, афишу, на которой размещены множество разнообразных спортивных соревнований,
как в оффлайн формате, так и в онлайн. Крупные же соревнования состоят из нескольких этапов отборов и заканчиваются 
многодневным Гранд-финалом, состоящим из множества различных развлекательных и спортивных мероприятий. 
Любой зарегистрированный пользователь может подать заявку на участие в них или заявку зрителя. 

API приложения разделено на две части:
- доступна только авторизованным пользователям;
- административная — для администраторов сервиса.  

Ниже приведён обобщённый список с функциями, разделённый по ролям:  
Администраторы могут:
- добавлять все соревнования, отборы и мероприятия к ним. А так же редактировать или удалять их
- менять статусы заявок пользователей, как на участие в отборах на соревнования, так и заявки зрителей на просмотр 
отборов и мероприятий
- просматривать информацию о пользователях
- просматривать и в случае необходимости удалять видео-ролики, нарушающие правила приложения
- комментировать видео-ролики, просматривать чужие комментарии и при необходимости удалять видео-ролики, 
нарушающие правила приложения  


  Пользователи могут:
- регистрироваться в приложении, обновлять или удалять данные своего профиля, в т.ч. и аватарки
- оставлять заявки на участие в отборах или зрительские заявки, а так же редактировать или удалять их
- получать списки различных соревнований или мероприятий по параметрам
- выкладывать видео-ролики, а так же просматривать их, комментировать и лайкать

### Swagger (OpenAPI) Documentation
В приложение интегрирован Swagger - инструмент для написания OpenAPI документации.   
[kardo-swagger.json](kardo-swagger.json) - файл для https://editor-next.swagger.io в json формате  
http://51.250.32.245:8080/swagger-ui/index.html - ссылка на временный сервер для _**swagger-ui**_.

### Инструкция по сборке и запуску
Приложение готово к сборке. Осуществить её можно с помощью команды _**mvn install**_.  
Далее запускаем собранный jar файл.

## Схема базы данных
![Схема](kardodb.png)
