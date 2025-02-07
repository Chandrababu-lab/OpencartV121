package utilities;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;


// Executed but not expected output 
public class ExtentReportUtility implements ITestListener {

	public ExtentSparkReporter spark;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	public void onStart(ITestContext testContext) {

		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report" + timestamp + ".html";
		spark = new ExtentSparkReporter(".\\reports\\" + repName);
		
		//spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/"+repName);
		
		spark.config().setDocumentTitle("Opencart Automation Report");
		spark.config().setReportName("Opencart Functional Testing");
		spark.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(spark);
		extent.setSystemInfo("Appication", "Opencart");
		extent.setSystemInfo("Username", System.getProperty("user.name"));

		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);

		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser Name", browser);

		List<String> includegrps = testContext.getCurrentXmlTest().getIncludedGroups();
		if (!includegrps.isEmpty()) {
			extent.setSystemInfo("Groups", includegrps.toString());
		}

	}

	public void onTestSuccess(ITestResult result) {

		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, result.getName() + " got successfully executed ");

	}

	public void onTestFailure(ITestResult result) {

		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());

		test.log(Status.FAIL, result.getName() + " got Failed ");
		test.log(Status.INFO, result.getThrowable().getMessage());

		try {
			String imgPath = new BaseClass().catpureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	public void onTestSkip(ITestResult result) {

		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + " got successfully executed ");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestResult result) {
		
		extent.flush();
		
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	

}
