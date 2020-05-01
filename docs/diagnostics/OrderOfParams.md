# Порядок параметров метода (OrderOfParams)

| Тип | Поддерживаются<br/>языки | Важность | Включена<br/>по умолчанию | Время на<br/>исправление (мин) | Тэги |
| :-: | :-: | :-: | :-: | :-: | :-: |
| `Дефект кода` | `BSL`<br/>`OS` | `Важный` | `Да` | `30` | `standard`<br/>`design` |

<!-- Блоки выше заполняются автоматически, не трогать -->
## Описание диагностики

Необязательные параметры (параметры со значениями по умолчанию) должны располагаться после обязательных параметров (без значений по умолчанию).  

## Примеры

```bsl
Функция КурсВалютыНаДату(Валюта, Дата = Неопределено) Экспорт
```

## Источники

* [Стандарт: Параметры процедур и функций](https://its.1c.ru/db/v8std#content:640:hdoc)

## Сниппеты

<!-- Блоки ниже заполняются автоматически, не трогать -->
### Экранирование кода

```bsl
// BSLLS:OrderOfParams-off
// BSLLS:OrderOfParams-on
```

### Параметр конфигурационного файла

```json
"OrderOfParams": false
```