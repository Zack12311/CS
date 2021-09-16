# Credit Suisse

## how to use:

Execute:

```
mvn spring-boot:run
```

Application will be avalaible on

```
http://localhost:8080/
```
### usage:
- create user:

```
PUT: http://localhost:8080/users
```

message body

```
{
    "name": "Dolan",
    "dateOfBirth": "2015-03-01",
    "phoneNumber": "123-123-123"
}
```

- list users

```
GET: http://localhost:8080/users/
```

- delete user:

```
DELETE: http://localhost:8080/users/{id}
```

or

```
DELETE: http://localhost:8080/users/ 
```

message body:

```
{
    "id": 1,
    "name": "Dolan",
    "dateOfBirth": "2015-03-01",
    "phoneNumber": "123-123-123"
}
```

- create book:

```
PUT:  http://localhost:8080/books/
```

message body:

```
{
    "title": "Harry Potter",
    "cost": 10.5,
    "quantityAvailable": "1"
}
```

- list books:

```
GET: http://localhost:8080/books/
```
//TODO finish me!
