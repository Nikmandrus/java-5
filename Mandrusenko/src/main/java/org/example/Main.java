package org.example;

// Использование
public class Main {
    public static void Main(String[] args) {
        try {
// Создаем объект TestClass
            TestClass testClass = new TestClass();
// Выполняем инъекцию
            testClass = Injector.inject(testClass);

// Проверяем работу инъекции, печатаем значение поля
            System.out.println(testClass.field.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
