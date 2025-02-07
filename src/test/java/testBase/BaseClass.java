package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.beust.jcommander.Parameter;

import freemarker.template.SimpleDate;

public class BaseClass {
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	
	@Parameters({"os","browser"})
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	
	public void setUp(String os,String br) throws IOException {
		
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		logger=LogManager.getLogger(this.getClass()); //Log4j2
		
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote")) 
		{
			
			DesiredCapabilities cp = new DesiredCapabilities();
			
			//os
			if(os.equalsIgnoreCase("windows")) 
			{
				cp.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("mac")) 
			{
				cp.setPlatform(Platform.MAC);
			}
			else 
			{
				System.out.println("No Matching os");
				return;
			}
			
			//browser
			switch(br.toLowerCase()) {
			case "chrome":cp.setBrowserName("chrome"); break;
			case "edge":cp.setBrowserName("MicrosoftEdge"); break;
			default:System.out.println("No Matching Browser");return;
			
			}
			
			driver = new RemoteWebDriver(new URL("http://10.68.46.11:4444"),cp);
			
		}
		if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch(br.toLowerCase()) {
			case "chrome":driver = new ChromeDriver(); break;
			case "edge":driver=new EdgeDriver(); break;
			case "firefox":driver = new FirefoxDriver(); break;
			default : System.out.println("Invalid browser name"); return;
			}
		}
		
		
		
		//driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//driver.get("https://tutorialsninja.com/demo/index.php");
		driver.get(p.getProperty("url"));
	}

	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tearDown() {
		driver.quit();
	}

	public String randomString() {
		String generatedstring = RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
	}

	public String randomNumber() {
		String generatedstring = RandomStringUtils.randomNumeric(10);
		return generatedstring;
	}

	public String randomAlphaNumeric() {
		String generatedstring = RandomStringUtils.randomAlphabetic(5);
		String generatednumber = RandomStringUtils.randomAlphabetic(5);
		return (generatedstring + "@" + generatednumber);
	}
	
	public String catpureScreen(String tname) {
		
		String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		File src = screenshot.getScreenshotAs(OutputType.FILE);
		
		String destFilePath = System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timestamp+".png";
		File dest = new File(destFilePath);
		
		src.renameTo(dest);
		
		return destFilePath;
		
	}
}
