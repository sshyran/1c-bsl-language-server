# Incorrect expression line break (IncorrectLineBreak)

<!-- Блоки выше заполняются автоматически, не трогать -->
## Description
<!-- Описание диагностики заполняется вручную. Необходимо понятным языком описать смысл и схему работу -->

Long arithmetic expressions are carried as follows: one entry can contain more than one operand; when wrapping, operation characters are written at the beginning of the line (and not at the end of the previous line); operands on a new line are preceded by standard indentation, or they are aligned to the beginning of the first operand, regardless of the operation signs.

If necessary, parameters of procedures, functions and methods should be transferred as follows:

* parameters are either aligned to the beginning of the first parameter, or preceded by standard indentation;
* closing parenthesis and operator separator ";" are written on the same line as the last parameter;
* the formatting method that offers the auto-formatting function in the configurator is also acceptable

Complex logical conditions in If ... ElseIf ... EndIf should be carried as follows:

* The basis for the newline if the line length is limited to 120 characters;
* logical operators AND, OR are placed at the beginning of a line, and not at the end of the previous line;
* all conditions are preceded by the standard first indent, or they are aligned at the start of work without taking into account the logical operator (it is recommended to use spaces to align expressions relative to the first line).

## Examples
<!-- В данном разделе приводятся примеры, на которые диагностика срабатывает, а также можно привести пример, как можно исправить ситуацию -->

Incorrect:

```bsl
AmountDocument = AmountWithoutDiscount +
                 AmountManualDiscounts +
                 AmountAutomaticDiscount;
```

Correct:

```bsl
AmountDocument = AmountWithoutDiscount 
    + AmountManualDiscounts 
    + AmountAutomaticDiscount;
```

or

```bsl
AmountDocument = AmountWithoutDiscount 
                 + AmountManualDiscounts 
                 + AmountAutomaticDiscount;
```

## Sources
<!-- Необходимо указывать ссылки на все источники, из которых почерпнута информация для создания диагностики -->

* Standard: [Wrap expressions (RU)](https://its.1c.ru/db/v8std#content:444:hdoc)
