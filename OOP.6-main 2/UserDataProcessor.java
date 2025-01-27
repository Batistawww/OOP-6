import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class UserDataProcessor {

    public static void main(String[] args) {
        processUserData();
    }

    public static void processUserData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в формате: Фамилия Имя Отчество ДатаРождения НомерТелефона Пол (m/f)");

        while (true) {
            String input = scanner.nextLine();
            String[] data = input.split(" ");

            if (data.length != 6) {
                System.out.println("Неверное количество данных. Пожалуйста, введите данные в нужном формате.");
                continue;
            }

            String lastName = data[0];
            String firstName = data[1];
            String middleName = data[2];
            String birthDate = data[3];
            String phoneNumberStr = data[4];
            String genderStr = data[5];

            // Проверка формата номера телефона
            long phoneNumber;
            try {
                phoneNumber = Long.parseLong(phoneNumberStr);
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат данных для номера телефона.");
                continue;
            }

            // Проверка формата даты рождения
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(birthDate);
            } catch (ParseException e) {
                System.out.println("Неверный формат даты рождения. Используйте формат dd.MM.yyyy.");
                continue;
            }

            // Проверка пола
            char gender = genderStr.charAt(0);
            if (gender != 'm' && gender != 'f') {
                System.out
                        .println("Неверный формат данных для пола. Используйте 'm' для мужского или 'f' для женского.");
                continue;
            }

            // Запись данных в файл
            String fileName = lastName + ".txt";
            String userData = String.format("%s %s %s; %s; %d; %c", lastName, firstName, middleName, birthDate,
                    phoneNumber, gender);

            try (FileWriter writer = new FileWriter(fileName, true)) {
                writer.write(userData + "\n");
                System.out.println("Данные успешно записаны в файл " + fileName);
                break; // Выход из цикла после успешной записи
            } catch (IOException ex) {
                System.err.println("Ошибка при записи в файл: " + ex.getMessage());
                continue;
            }
        }

        scanner.close();
    }
}