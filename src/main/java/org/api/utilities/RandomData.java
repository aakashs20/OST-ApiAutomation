package org.api.utilities;

import org.apache.commons.lang3.RandomStringUtils;

import net.datafaker.Faker;

public class RandomData {
	
	public static Faker faker = new Faker();

	public static String getRandomNumber() {

		return faker.number().digit();
	}
	
	public static String getRandomNumber(int count) {
        return faker.number().digits(count);
    }

    public static int getRandomNumber(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

    public static String getRandomAlphabets(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }
	
	public static String getRandomString(int count) {
		return RandomStringUtils.randomAlphanumeric(count);
	}
	
}
