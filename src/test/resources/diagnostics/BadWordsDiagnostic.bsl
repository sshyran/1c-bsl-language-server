// при наличии в списке запрещенных слов "лотус/шмотус" // тут должно сработать дважды

Запрос = Новый Запрос;
Запрос.Текст = "ВЫБРАТЬ ПЕРВЫЕ 1
|   Лотус.Ссылка КАК Узел                               //тут должно сработать
|ИЗ
|   ПланОбмена.ДиспетчерЛотус КАК Лотус";               //тут должно сработать дважды

УзелШмотуса = Запрос.Выполнить().Выгрузить()[0].Ссылка;  //тут должно сработать