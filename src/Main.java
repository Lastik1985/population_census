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

        long underageCount = persons.stream()
                .filter(x -> x.getAge() < 18)
                .count();
        System.out.printf("Количество несовершеннолетних: " + underageCount);


        List<String> conscripts = persons.stream()
                .filter(x -> "MAN".equals(x.getSex().toString()))
                .filter(x -> x.getAge() >= 18 & x.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println("Фамилии призывников: " + conscripts);
        System.out.println();


        List<Person> higherEducatedPersons = persons.stream()
                .filter(x -> ("HIGHER".equals(x.getEducation().toString()) && x.getAge() >= 18
                        && (("MAN".equals(x.getSex().toString()) && x.getAge() <= 65)
                        || ("WOMAN".equals(x.getSex().toString()) && x.getAge() <= 60))))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());

        System.out.println("Cписок потенциально работоспособных людей с высшим образованием:");
        System.out.println(higherEducatedPersons);

    }


}

