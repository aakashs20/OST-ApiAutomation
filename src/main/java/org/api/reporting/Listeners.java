package org.api.reporting;

import java.util.Arrays;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class Listeners implements ITestListener {

	public static ExtentReports extentReports;
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	public void onStart(ITestContext context) {
		extentReports = ExtentReporter.getReportObject();
	}

	public void onTestStart(ITestResult result) {
		ExtentTest test = extentReports.createTest("Test Name: " + result.getMethod().getMethodName(),
				result.getMethod().getDescription());
		extentTest.set(test);
		ExtentReporter.logStartDetails("<b>" + result.getMethod().getMethodName() + " " + "is Started." + "</b>");
	}

	public void onTestSuccess(ITestResult result) {
		ExtentReporter.logPassDetails("<b>" + result.getMethod().getMethodName() + " " + "is Successfull.!" + "</b>");
	}

	public void onTestFailure(ITestResult result) {
		ExtentReporter.logFailureDetails(result.getThrowable().getMessage());
		String stackTrace = Arrays.toString(result.getThrowable().getStackTrace());
		stackTrace = stackTrace.replaceAll(",", "<br>");
		String formmatedTrace = "<details>\n" + "    <summary>Click Here To See Exception Logs</summary>\n" + "    "
				+ stackTrace + "\n" + "</details>\n";
		ExtentReporter.logExceptionDetails(formmatedTrace);
		ExtentReporter.logFailureDetails("<b>" + result.getMethod().getMethodName() + " " + "is Failed.!!" + "</b>");
	}

	public void onFinish(ITestContext context) {
		if (extentReports != null)
			extentReports.flush();
	}

}
