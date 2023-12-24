package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

// Создаем аннотацию @AutoInjectable
@interface AutoInjectable {}

// Создаем класс Injector
public class Injector {

    // Параметризированный метод inject
    public static <T> T inject(T object) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
// Считываем настройки из файла properties
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("config.properties");
        properties.load(fileInputStream);
        fileInputStream.close();

// Проходим по полям объекта
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
// Проверяем, помечено ли поле аннотацией @AutoInjectable
            if (field.isAnnotationPresent(AutoInjectable.class)) {
// Получаем тип поля
                Class<?> fieldType = field.getType();
// Получаем имя класса для инъекции из настроек
                String className = properties.getProperty(fieldType.getName());
// Загружаем класс для инъекции
                Class<?> injectorClass = Class.forName(className);
// Создаем экземпляр класса для инъекции
                Object injection = injectorClass.newInstance();

// Устанавливаем значение поля в объекте
                field.setAccessible(true);
                field.set(object, injection);
            }
        }

        return object;
    }
}

// Интерфейс FieldInterface
interface FieldInterface {
    void setValue(int value);
    int getValue();
}

// Класс FieldClass, реализующий интерфейс FieldInterface
class FieldClass implements FieldInterface {
    private int value;

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

// Класс TestClass, содержащий поле с аннотацией @AutoInjectable
class TestClass {
    @AutoInjectable
    public FieldInterface field;
}