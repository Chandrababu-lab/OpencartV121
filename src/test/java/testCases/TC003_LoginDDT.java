package testCases;


import org.testng.Assert;
import org.testng.annotations.Test;


import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;



public class TC003_LoginDDT extends BaseClass{
	
	
	
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class,groups="DataDriven")
	public void verify_LoginDDT(String email,String pwd,String exp) {
		
		logger.info("******Starting TC003_LoginDDT****");
		
		try {
		//Home Page
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		
		//Login page
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(email);
		lp.setPass(pwd);
		lp.clickLoginBtn();
		
		//My Account Page
		MyAccountPage mp=new MyAccountPage(driver);
		boolean targetPage = mp.confirmHeading();
		
		/*
		 1.Data is valid - login success - test pass - logout
		 					login failed - test fail
		 
		 3.Data is invalid - login success - test fail - logout
		  					login failed - test pass 
		 */
		if(exp.equalsIgnoreCase("Valid")) {
			
			if(targetPage == true) {
				mp.clickLogout();
				Assert.assertTrue(true);
			}
			else {
				Assert.assertTrue(false);
			}
		}
		if(exp.equalsIgnoreCase("Invalid")) {
			
			if(targetPage == true) {
				mp.clickLogout();
				Assert.assertTrue(false);
			}else {
				Assert.assertTrue(true);
			}
		}
		}catch (Exception e) {
			// TODO: handle exception
			Assert.fail();
		}
		logger.info("******Finished TC003_LoginDDT****");
	}

}
