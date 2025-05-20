package org.api.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.api.pojos.MobilePoiji;
import org.api.pojos.Product;
import org.api.pojos.ProductPoiji;
import org.api.restUtils.HttpMethods;
import org.api.utilities.JsonReader;
import org.api.utilities.PropertiesReader;
import org.testng.annotations.BeforeTest;

import io.restassured.response.Response;

public class BaseTest {

	@BeforeTest
	public void init() throws IOException {

		PropertiesReader.loadPropertyFile();
	}

	public Response postRequest() throws FileNotFoundException {
		String uri = PropertiesReader.getConfigData("uri_product");
		String requestPayload = JsonReader.readJson("product.json");
		return HttpMethods.performPost(uri, requestPayload, new HashMap<>());
	}
	
	public Response postRequest(String requestPayload, String uriKey) throws FileNotFoundException {
		String uri = PropertiesReader.getConfigData(uriKey);
		return HttpMethods.performPost(uri, requestPayload, new HashMap<>());
	}
	
	public Response postRequest(String requestPayload, String uriKey, String token) throws FileNotFoundException {
		String uri = PropertiesReader.getConfigData(uriKey);
		return HttpMethods.performPost(uri, requestPayload, new HashMap<>(), token);
	}
	
	
	public Response postRequestMultiPart(String requestPayload, String uriKey) throws FileNotFoundException {
		String uri = PropertiesReader.getConfigData(uriKey);
		return HttpMethods.performPostMultiPart(uri, requestPayload);
	}

	public Response postRequest(Product requestPayloadFromPojo) {
		String uri = PropertiesReader.getConfigData("uri_product");
		return HttpMethods.performPost(uri, requestPayloadFromPojo, new HashMap<>());
	}

	public Response postRequest(ProductPoiji productPayloadFromPoiji) {
		String uri = PropertiesReader.getConfigData("uri_product");
		return HttpMethods.performPost(uri, productPayloadFromPoiji, new HashMap<>());
	}

	public Response postRequest(MobilePoiji mobilePayloadFromPoiji) {
		String uri = PropertiesReader.getConfigData("uri_object");
		return HttpMethods.performPost(uri, mobilePayloadFromPoiji, new HashMap<>());
	}

}
