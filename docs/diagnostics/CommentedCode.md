# Закомментированный фрагмент кода (CommentedCode)

 Тип | Поддерживаются<br>языки | Важность | Включена<br>по умолчанию | Время на<br>исправление (мин) | Тэги 
 :-: | :-: | :-: | :-: | :-: | :-: 
 `Дефект кода` | `BSL`<br>`OS` | `Незначительный` | `Да` | `1` | `standard`<br>`badpractice` 

## Параметры 

 Имя | Тип | Описание | Значение по умолчанию 
 :-: | :-: | :-- | :-: 
 `threshold` | `Число с плавающей точкой` | ```Порог чуствительности``` | ```0.9``` 

<!-- Блоки выше заполняются автоматически, не трогать -->
## Описание диагностики

Программные модули не должны иметь закомментированных фрагментов кода, а также фрагментов,
которые каким-либо образом связаны с процессом разработки (отладочный код, служебные отметки, например, !!!_, MRG и т.п.)
и с конкретными разработчиками этого кода.

Например, недопустимо оставлять подобные фрагменты в коде после завершения отладки или рефакторинга:

```bsl
Процедура ПередУдалением(Отказ)
//    Если Истина Тогда
//        Сообщение("Для отладки");
//    КонецЕсли;
КонецПроцедуры
```
также неправильно:
```bsl
Процедура ПередУдалением(Отказ)
    Если Истина Тогда
        // Иванов: доделать 
    КонецЕсли;
КонецПроцедуры
```

Правильно: после завершения отладки или рефакторинга удалить обработчик ПередУдалением из кода.

**ВНИМАНИЕ**:  
Блок комментарием считается кодом, если хотя бы одна строка внутри блока определяется как код. 

## Источники

* [Источник](https://its.1c.ru/db/v8std/content/456/hdoc)

## Сниппеты

<!-- Блоки ниже заполняются автоматически, не трогать -->
### Экранирование кода

```bsl
// BSLLS:CommentedCode-off
// BSLLS:CommentedCode-on
```

### Параметр конфигурационного файла

```json
"CommentedCode": {
    "threshold": 0.9
}
```