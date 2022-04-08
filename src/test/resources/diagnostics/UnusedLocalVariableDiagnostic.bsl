&НаКлиенте
Перем ПеременнаяМодуляНеИспользуемая; // Тут ошибка

&НаСервере
Перем ПеременнаяМодуляНеИспользуемая; // Тут без ошибок

Перем ПеременнаяМодуляНеИспользуемаяЭкспортная Экспорт; // Тут думаю ошибка не нужно, возможно ради поддержания интефейса
Перем ПеременнаяМодуляИспользуемая; // Тут без ошибок
Перем ПеременнаяМодуляИспользуемаяЭкспортная Экспорт; // Тут без ошибок

Функция Первая()

    ПеременнаяМодуляИспользуемая = ДействиеСРезультатомЧисло();
    ДействиеСПараметром(ПеременнаяМодуляИспользуемая);
    ДействиеСПараметром2(ПеременнаяМодуляИспользуемаяЭкспортная);

КонецФункции

Функция Вторая()
    Перем ЛокальнаяБезИспользования, ТолькоСПрисвоениемЗначения, ЛокальнаяСИспользованием;

    ЛокальнаяСИспользованием = 40;
    ТолькоСПрисвоениемЗначения = ВыполнитьДействие(ЛокальнаяСИспользованием);
    ВПроцедуреИспользуемая = Проверка();
    ВПроцедуреНеИспользуемая = Проверка();

    Если ВПроцедуреИспользуемая = Истина Тогда

       ТолькоСПрисвоениемЗначения = 39;

    КонецЕсли;

    ПеременнаяОбъектСИспользованием = Обработки.Проверка.Создать();
    ПеременнаяОбъектСИспользованием.Выполнить();

    ВПроцедуреИспользуемая2 = Новый Файл(ОбъединитьПути(".", "test_versions.mxl"));
    Ожидаем.Что(ВПроцедуреИспользуемая2.Существует(), "Файл отчета не был создан").ЭтоИстина();

КонецФункции

Функция Третья(ЭтоПараметр)

    ЭтоПараметр = Новый Массив();

    НоваяСтрока                = ГруппаДоступа.ВидыДоступа.Добавить();
    НоваяСтрока.ВидДоступа     = СтрокаВидаДоступа.ВидДоступа;
    НоваяСтрока.ДоступРазрешен = СтрокаВидаДоступа.ДоступРазрешен;

КонецФункции

Процедура ЗаполнитьСвойстваОбъектаОбъектнойМоделиCOMАдминистратораПоОписанию(Объект, Знач Описание, Знач Словарь)

	Для Каждого ФрагментСловаря Из Словарь Цикл

		ИмяСвойства = ФрагментСловаря.Значение;

		ЗначениеСвойства = Описание[ФрагментСловаря.Ключ];

		Объект[ИмяСвойства] = ЗначениеСвойства;

	КонецЦикла;

КонецПроцедуры

Процедура ВывестиШапкуПоВерсии(ТЧОтчета, Знач Текст, Знач НомерСтроки, Знач НомерКолонки)

	Если Не ПустаяСтрока(Текст) Тогда

		ТЧОтчета.Область("C"+Строка(НомерКолонки)).ШиринаКолонки = 50;

		Регион = "R" + Формат(НомерСтроки, "ЧГ=0") + "C" + Формат(НомерКолонки, "ЧГ=0");
		ТЧОтчета.Область(Регион).Текст = Текст;
		ТЧОтчета.Область(Регион).ЦветФона = ЦветаСтиля.ТекстЗапрещеннойЯчейкиЦвет;
		ТЧОтчета.Область(Регион).Шрифт = Новый Шрифт(, 8, Истина, , , );
		ТЧОтчета.Область(Регион).ГраницаСверху = Новый Линия(ТипЛинииЯчейкиТабличногоДокумента.Сплошная);
		ТЧОтчета.Область(Регион).ГраницаСнизу  = Новый Линия(ТипЛинииЯчейкиТабличногоДокумента.Сплошная);
		ТЧОтчета.Область(Регион).ГраницаСлева  = Новый Линия(ТипЛинииЯчейкиТабличногоДокумента.Сплошная);
		ТЧОтчета.Область(Регион).ГраницаСправа = Новый Линия(ТипЛинииЯчейкиТабличногоДокумента.Сплошная);

	КонецЕсли;

КонецПроцедуры

ВнеПроцедурНеИспользуемая = 30;
ВнеПроцедурИспользуемая = 40;
ДействиеСПараметром(ВнеПроцедурИспользуемая);
