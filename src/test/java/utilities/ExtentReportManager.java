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

import testBase.BaseClass;



public class ExtentReportManager implements ITestListener{

	public ExtentReports extent;
	public ExtentSparkReporter spark;
	public ExtentTest test;
	
	String repName;
	
	public void onStart(ITestContext context) {
		
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report" + timestamp + ".html";
		spark = new ExtentSparkReporter(".\\reports\\" + repName);
		
		
		//spark = new ExtentSparkReporter(System.getProperty("user.dir")+"/reports/sample.html");
		//spark = new ExtentSparkReporter(System.getProperty("user.dir")+"\\reports\\myreports1.html");
		extent = new ExtentReports();
		extent.attachReporter(spark);
		extent.setSystemInfo("Appication", "Opencart");
		extent.setSystemInfo("Username", System.getProperty("user.name"));

		String os = context.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);

		String browser = context.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser Name", browser);
		

		List<String> includegrps = context.getCurrentXmlTest().getIncludedGroups();
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
	
	public void onFinish(ITestContext context) {
		
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
