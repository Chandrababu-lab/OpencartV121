package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{

	@Test(groups={"Sanity","Master"})
	public void verify_login() {
		logger.info("******Started********");
		
		try {
			HomePage hp=new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			logger.info("Clicked Login Page");
			
			//Login page
			LoginPage lp=new LoginPage(driver);
			lp.setEmail(p.getProperty("email"));
			lp.setPass(p.getProperty("password"));
			lp.clickLoginBtn();
			
			//My Account Page
			MyAccountPage mp=new MyAccountPage(driver);
			boolean targetPage = mp.confirmHeading();
			
			//Assert.assertEquals(targetPage, true,"Login Failed");
			Assert.assertTrue(targetPage);
		}catch (Exception e) {
			// TODO: handle exception
			Assert.fail();
		}
		//HomePage
		
		logger.info("******Finished********");
	}
}
