import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {


        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100), //возраст до 100
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)]));
        }
        // 1. Найти количество несовершеннолетних (т.е. людей младше 18 лет)
        // Для поиска несовершеннолетних используйте промежуточный метод filter() и терминальный метод count().
        long underageCount = persons.stream()
                .filter(x -> x.getAge() < 18)
                .count();
        System.out.printf("Количество несовершеннолетних: " + underageCount);

        // 2. Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
        // Для получения списка призывников потребуется применить несколько промежуточных методов filter(),
        // а также для преобразования данных из Person в String (так как нужны только фамилии) используйте метод map().
        // Так как требуется получить список
        // List<String> терминальным методом будет collect(Collectors.toList()).
        List<String> conscripts = persons.stream()
                .filter(x -> "MAN".equals(x.getSex().toString()))
                .filter(x -> x.getAge() >= 18 & x.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Фамилии призывников: " + conscripts);
        System.out.println();

        // 3. Получить отсортированный по фамилии список потенциально работоспособных людей с высшим образованием в выборке
        // (т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин).
        // Для получения отсортированного по фамилии списка потенциально работоспособных людей с высшим образованием
        // необходимо применить ряд промежуточных методов filter()
        // метод sorted() в который нужно будет положить компаратор по фамилиям Comparator.comparing()
        // Завершить стрим необходимо методом collect().
        List<Person> higherEducatedPersons = persons.stream()
                .filter(x -> ("HIGHER".equals(x.getEducation().toString()) && x.getAge() >= 18
                        && (("MAN".equals(x.getSex().toString()) && x.getAge() <= 65)
                        || ("WOMAN".equals(x.getSex().toString()) && x.getAge() <= 60))))
                //с помощью терминальной операции сортируем по фамилии(компаратор)
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());

        System.out.println("Cписок потенциально работоспособных людей с высшим образованием:");
        System.out.println(higherEducatedPersons);

    }


}

