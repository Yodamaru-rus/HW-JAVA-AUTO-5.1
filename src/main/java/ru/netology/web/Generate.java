package ru.netology.web;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Generate {
    private Generate() {
    }

    public static String generateDate(int days) {
        String date = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String generateCity() {
        // TODO: добавить логику для объявления переменной city и задания её значения, генерацию можно выполнить
        List<String> list = new ArrayList<String>();
        list.add("Москва");
        list.add("Симферополь");
        list.add("Смоленск");
        list.add("Тамбов");
        String city = list.get(new Random().nextInt(list.size()));

        return city;
    }

    public static String generateName(Faker faker) {
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(Faker faker) {
        // TODO: добавить логику для объявления переменной phone и задания её значения, для генерации можно
        String phone = String.valueOf(faker.phoneNumber().phoneNumber());
        return phone;
    }

    public static class Registration {
        private static Faker faker;

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            faker = new Faker(new Locale(locale));
            return new UserInfo(
                    generateCity(),
                    generateName(faker),
                    generatePhone(faker)
            );

        }
    }
    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }

}

