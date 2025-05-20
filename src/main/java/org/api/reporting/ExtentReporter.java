package org.api.reporting;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.restassured.http.Header;

public class ExtentReporter {

	public static ExtentReports extentReports;
	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy\\HH_mm_ss");

	public static ExtentReports getReportObject() {
		String reportPath = "../OST-ApiAutomation/src/test/resources/reports/" + dtf.format(LocalDateTime.now());
		ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(reportPath);
		extentSparkReporter.config().setTheme(Theme.DARK);
		extentSparkReporter.config().setReportName("KOAST API Results");
		extentSparkReporter.config().setDocumentTitle("API Test Results");
		extentSparkReporter.config().setEncoding("utf-8");
		extentReports = new ExtentReports();
		extentReports.attachReporter(extentSparkReporter);
		extentReports.setSystemInfo("Organization", "KPIT");
		extentReports.setSystemInfo("Project", "KOAST");
		extentReports.setSystemInfo("QA", "Aakash Saxena");
		extentReports.setSystemInfo("Enviornment", "UAT");
		extentReports.setSystemInfo("Testing type", "API");
		return extentReports;
	}

	public static void logPassDetails(String log) {
		Listeners.extentTest.get().pass(MarkupHelper.createLabel(log, ExtentColor.GREEN));
	}

	public static void logFailureDetails(String log) {
		Listeners.extentTest.get().fail(MarkupHelper.createLabel(log, ExtentColor.RED));
	}

	public static void logExceptionDetails(String log) {
		Listeners.extentTest.get().fail(log);
	}

	public static void logInfoDetails(String log) {
		Listeners.extentTest.get().info(MarkupHelper.createLabel(log, ExtentColor.BLUE));
	}

	public static void logStartDetails(String log) {
		Listeners.extentTest.get().info(MarkupHelper.createLabel(log, ExtentColor.ORANGE));
	}

	public static void logWarningDetails(String log) {
		Listeners.extentTest.get().warning(MarkupHelper.createLabel(log, ExtentColor.YELLOW));
	}

	public static void logJson(String json) {
		Listeners.extentTest.get().info(MarkupHelper.createCodeBlock(json, CodeLanguage.JSON));
	}

	public static void logHeaders(List<Header> headersList) {

		String[][] arrayHeaders = headersList.stream()
				.map(header -> new String[] { header.getName(), header.getValue() }).toArray(String[][]::new);
		Listeners.extentTest.get().info(MarkupHelper.createTable(arrayHeaders));
	}
}
