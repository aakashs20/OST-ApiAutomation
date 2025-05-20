package org.api.restUtils;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.Map;

import org.api.reporting.ExtentReporter;
import org.api.utilities.PropertiesReader;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

public class HttpMethods {

	private static final String TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJTbVdyUm5zc1NjLTgwcUxIa2xjU2VwQU11VWpaYUQtNk5KWXBOS2RqWEdVIn0.eyJleHAiOjE3Mzc0MDYxNDQsImlhdCI6MTczNzM2Mjk0NCwianRpIjoiMjY0ZTFjNjYtNTM4Mi00ZjJjLWE4YjktNzY2MDMyMmQ5YmNkIiwiaXNzIjoiaHR0cDovLzEwLjUyLjIxMy4yMzQ6OTE5OS9yZWFsbXMvS29hc3RfU2VjdXJpdHkiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiM2YxNjdmOTctMDlhYS00M2RmLWIyMWQtZGIxNzA1MTBiNzA5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiS29hc3RfU2VjdXJpdHlfQ2xpZW50Iiwic2lkIjoiZmRkOTEyNmQtMzkwYi00YTliLWI5MzUtNjhkYWY0ZTZiYzBiIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIvKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1rb2FzdF9zZWN1cml0eSIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IkVMSVZfVXNlcjEgRUxJVl9Vc2VyMSIsInByZWZlcnJlZF91c2VybmFtZSI6ImVsaXZfdXNlcjEiLCJnaXZlbl9uYW1lIjoiRUxJVl9Vc2VyMSIsImZhbWlseV9uYW1lIjoiRUxJVl9Vc2VyMSIsImVtYWlsIjoic3VyYWouc2luZ2hAa3BpdC5jb20ifQ.Ez1MAsXgJTEMOW_J-G9eVyNHI0PdajS7l2TOx_Qj7vsGm7zLxKpOfkkpSroxUUQ-cGKuoNYzqGwG6oys1gGBx2V_NJw-atmBJe3geBFaZpP--J5mqDMxWzGyaxnOgvtx-7TA7ZWuc7DUDPAvREYTEejzSp5swbGwh7H_kQ3w5H2EfuUGAymH8M3qqCnD5TwNQH7i_F_Y9l0x7SBJ9UFvzyzsMDfUIVu_NKV74hKmJB4kKfHYNttbfn5wcqF09XKAFvi1N_byaN0guzU7L1BN-pNCQO7K56N81gckPcrUnxmgTWJTsSRzv32h51MvwApjw5hM_l1b8At492y7urlaRQ";
	private static String importFile = "../OST-ApiAutomation/src/test/resources/testData/Nano_InitEvent_TimingEvent.arxml";

	private static RequestSpecification getRequestSpecification(String uri, Object requestPayload,
			Map<String, String> headers) {
		return RestAssured.given()
				.relaxedHTTPSValidation()
				.baseUri(uri)
				.headers(headers)
				.contentType(ContentType.JSON)
				.body(requestPayload);
	}
	
	private static RequestSpecification getRequestSpecification(String uri, Object requestPayload,
			Map<String, String> headers, String token) {
		return RestAssured.given()
				.relaxedHTTPSValidation()
				.baseUri(uri)
				.headers(headers)
				.header("Authorization", "Bearer " + token)
				.contentType(ContentType.JSON)
				.body(requestPayload);
	}

	private static RequestSpecification getRequestSpecificationMultiPart(String uri, Object requestPayload) {
		return RestAssured.given().relaxedHTTPSValidation()
				.baseUri(uri)
				.contentType(ContentType.MULTIPART)
				.header("Authorization", "Bearer " + TOKEN)
				.multiPart("request", requestPayload.toString(), "application/json")
				.multiPart("filelist", new File(importFile), "multipart/form-data");
	}

	private static void printRequestLogInReport(RequestSpecification requestSpecification) {
		QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
		ExtentReporter.logInfoDetails("URI is: " + queryableRequestSpecification.getBaseUri());
		ExtentReporter.logInfoDetails("Method is: " + queryableRequestSpecification.getMethod());
		ExtentReporter.logInfoDetails("Request Headers are: ");
		ExtentReporter.logHeaders(queryableRequestSpecification.getHeaders().asList());
		ExtentReporter.logInfoDetails("Request body is: ");
		ExtentReporter.logJson(queryableRequestSpecification.getBody());
	}

	private static void printResponseLogInReport(Response response) {
		ExtentReporter.logInfoDetails("Response status is: " + response.getStatusCode());
		ExtentReporter.logInfoDetails("Response Headers are: ");
		ExtentReporter.logHeaders(response.getHeaders().asList());
		ExtentReporter.logInfoDetails("Response body is: ");
		ExtentReporter.logJson(response.getBody().prettyPrint());
	}

	public static Response performPost(String uri, String requestPayload, Map<String, String> headers) {
		RequestSpecification requestSpecification = getRequestSpecification(uri, requestPayload, headers);
		Response response = requestSpecification.when().post();
		printRequestLogInReport(requestSpecification);
		printResponseLogInReport(response);
		return response;
	}
	
	public static Response performPost(String uri, String requestPayload, Map<String, String> headers, String token) {
		RequestSpecification requestSpecification = getRequestSpecification(uri, requestPayload, headers, token);
		Response response = requestSpecification.when().post();
		printRequestLogInReport(requestSpecification);
		printResponseLogInReport(response);
		return response;
	}
	
	public static Response performPostMultiPart(String uri, String requestPayload) {
		RequestSpecification requestSpecification = getRequestSpecificationMultiPart(uri, requestPayload);
		Response response = requestSpecification.when().post();
		printRequestLogInReport(requestSpecification);
		printResponseLogInReport(response);
		return response;
	}

	public static Response performPost(String uri, Object requestPayloadAsPojo, Map<String, String> headers) {
		RequestSpecification requestSpecification = getRequestSpecification(uri, requestPayloadAsPojo, headers);
		Response response = requestSpecification.when().post();
		printRequestLogInReport(requestSpecification);
		printResponseLogInReport(response);
		return response;
	}

	public static Response postRequest(String bodyData, String uriKey) {

		Response res = given().relaxedHTTPSValidation().body(bodyData).contentType(ContentType.JSON).when()
				.post(PropertiesReader.getConfigData(uriKey));

		System.out.println(res.statusCode());
		System.out.println(res.asString());

		return res;
	}

	public Response GetRequest(String qa_uri) {

		String uri = PropertiesReader.getConfigData(qa_uri);
		Response res = given().relaxedHTTPSValidation().contentType(ContentType.JSON).when().get(uri);
		return res;
	}

	public Response GetRequest(String qa_uri, String pathParameter) {

		String uri = PropertiesReader.getConfigData(qa_uri) + "/" + pathParameter;
		Response res = given().relaxedHTTPSValidation().contentType(ContentType.JSON).when().get(uri);
		return res;
	}


	
}
