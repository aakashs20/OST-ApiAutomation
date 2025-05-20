package org.api.testCases;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.api.base.BaseTest;
import org.api.pojos.MobilePoiji;
import org.api.pojos.Product;
import org.api.pojos.ProductPoiji;
import org.api.reporting.ExtentReporter;
import org.api.responseValidation.ValidateResponse;
import org.api.restUtils.HttpMethods;
import org.api.utilities.ExcelReader;
import org.api.utilities.JsonParsingUsingJsonPath;
import org.api.utilities.JsonReader;
import org.api.utilities.JsonReplacement;
import org.api.utilities.RandomData;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poiji.bind.Poiji;

import io.restassured.response.Response;
import payloads.ProductPayload;

public class TC1_PostRequest extends BaseTest {
	
	public static String responseValue;

	@Test
	public void testCase_01() throws IOException {
		
//		HttpMethods http = new HttpMethods();
		
		String i = RandomData.getRandomNumber();
		
		String bodyData = JsonReader.readJson("mobile.json");
		bodyData = JsonReplacement.jsonReplace(bodyData, "price", i.toString());
		Response response = HttpMethods.postRequest(bodyData, "uri_object");
//		responseValue = JsonParsingUsingOrgJson.parseComplexJson(response.asString(), "address.postalCode");
		responseValue = JsonParsingUsingJsonPath.jsonParse(response, "address.postalCode");
		System.out.println("postalCode: " + responseValue);

		ValidateResponse.statusCodeValidate(response, 200);
		ValidateResponse.responseDataValidate(response, "name", "Apple MacBook Pro 16");
	}
	
	@Test
	public void testCase_02() throws IOException {
		
//		HttpMethods http = new HttpMethods();
		
		String userData = JsonReader.readJson("user.json");
		Response response = postRequest(userData, "uri_user");
		
//		String responsePostal = JsonParsingUsingOrgJson.parseComplexJson(response.asString(), "address.postalCode");
		String responsePostal = JsonParsingUsingJsonPath.jsonParse(response, "address.postalCode");
		ExtentReporter.logInfoDetails(responsePostal);
		String responseId = JsonParsingUsingJsonPath.jsonParse(response, "id");
		ExtentReporter.logInfoDetails(responseId);
		String city = JsonParsingUsingJsonPath.jsonParse(response, "address.city");
		ExtentReporter.logInfoDetails(city);
		String type = JsonParsingUsingJsonPath.jsonParse(response, "phoneNumbers[0].type");
		ExtentReporter.logInfoDetails(type);
		
		ValidateResponse.statusCodeValidate(response, 201);
		ValidateResponse.responseDataValidate(response, "firstName", "John");
	}

	@Test
	public void testCase_03() throws IOException {

		Response response = postRequest();

		ExtentReporter
				.logInfoDetails("Get category from the response: " + response.jsonPath().getString("category"));

		ValidateResponse.statusCodeValidate(response, 201);
		ValidateResponse.responseDataValidate(response, "category", "Smartphones");
	}

	@Test
	public void testCase_04() throws JsonMappingException, JsonProcessingException {

		Product request = ProductPayload.createProductPayloadFromPojo();
		Response response = postRequest(request);

		ExtentReporter
				.logInfoDetails("Get category from the response: " + response.jsonPath().getString("category"));

		Assert.assertEquals(response.jsonPath().getString("category"), request.getCategory());
		
		ValidateResponse.statusCodeValidate(response, 201);
		ValidateResponse.responseDataValidate(response, "category", request.getCategory());

		ObjectMapper objectMapper = new ObjectMapper();
		Product productsResponse = objectMapper.readValue(response.getBody().asString(), Product.class);
		Assert.assertEquals(productsResponse, request);
	}

	@Test(dataProvider = "productData")
	public void testCase_05(Product product) {

		Response response = postRequest(product);

		ExtentReporter
				.logInfoDetails("Get category from the response: " + response.jsonPath().getString("category"));

		ValidateResponse.statusCodeValidate(response, 201);
		ValidateResponse.responseDataValidate(response, "category", product.getCategory());
	}

	@Test(dataProvider = "productDataPoiji")
	public void testCase_06(ProductPoiji productPoiji) {

		Response response = postRequest(productPoiji);

		ExtentReporter
				.logInfoDetails("Get id from the response: " + response.jsonPath().getString("id"));

		Assert.assertEquals(response.jsonPath().getString("id"), productPoiji.getId());
		
		ValidateResponse.statusCodeValidate(response, 200);
		ValidateResponse.responseDataValidate(response, "category", productPoiji.getCategory());
	}

	@Test(dataProvider = "mobileDataPoiji")
	public void testCase_07(MobilePoiji mobilePoiji) {

		Response response = postRequest(mobilePoiji);

		ExtentReporter.logInfoDetails("Get CPU model from the response: " + response.jsonPath().getString("name"));

		ValidateResponse.statusCodeValidate(response, 200);
		ValidateResponse.responseDataValidate(response, "data.year", mobilePoiji.getData().getYear());
	}

	///////////////////////// *****************************************************///////////////////////////////////////

	@DataProvider(name = "productData")
	public Iterator<Product> createProductData() throws IOException {

		List<LinkedHashMap<String, String>> excelData = ExcelReader.getExcelDataAsListOfMap("CreateProductData",
				"Sheet1");
		List<Product> productList = new ArrayList<>();
		for (LinkedHashMap<String, String> data : excelData) {
			Product product = Product
					.builder()
					.title(data.get("title"))
					.description(data.get("description"))
					.price(data.get("price"))
					.discountPercentage(Integer.parseInt(data.get("discountPercentage")))
					.rating(Integer.parseInt(data.get("rating")))
					.stock(data.get("stock"))
					.brand(data.get("brand"))
					.category(data.get("category"))
					.build();
			productList.add(product);
		}
		return productList.iterator();
	}

	@DataProvider(name = "productDataPoiji")
	public Iterator<ProductPoiji> createProductDataPoiji() throws IOException {
		
		List<ProductPoiji> productData = Poiji
				.fromExcel(new File("src/test/resources/testData/CreateProductDataPoiji.xlsx"), ProductPoiji.class);
		return productData.iterator();
	}
	

	@DataProvider(name = "mobileDataPoiji")
	public Iterator<MobilePoiji> createMobileDataPoiji() throws IOException {

		List<MobilePoiji> mobileData = Poiji
				.fromExcel(new File("src/test/resources/testData/CreateMobileDataPoiji.xlsx"), MobilePoiji.class);
		return mobileData.iterator();
	}

}
