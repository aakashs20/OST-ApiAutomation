package payloads;

import org.api.pojos.Product;
import org.api.utilities.RandomData;

public class ProductPayload {

	public static Product createProductPayloadFromPojo() {
		return Product
				.builder()
				.title(RandomData.getRandomAlphabets(7))
				.description(RandomData.getRandomString(18))
				.price(RandomData.getRandomNumber(5))
				.discountPercentage(RandomData.getRandomNumber(20, 60))
				.rating(RandomData.getRandomNumber(1, 5))
				.stock(RandomData.getRandomNumber(2))
				.brand(RandomData.getRandomAlphabets(5))
				.category(RandomData.getRandomAlphabets(8))
				.build();
	}
}
