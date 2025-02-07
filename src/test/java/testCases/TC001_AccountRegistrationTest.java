package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{

	
	@Test(groups={"Regression","Master"})
	public void verify_account_registration() {
		logger.info("***********Started**************");
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			logger.info("Clicked on My Link..");
			hp.clickRegister();
			logger.info("Clicked on Registration..");
			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
			regpage.setFirstName("john");
			regpage.setLastName("David");
			regpage.setEmail(randomString() + "@gmail.com");
			regpage.setPhone(randomNumber());

			String p = randomAlphaNumeric();
			regpage.setPassword(p);
			regpage.setConfirmPassword(p);
			logger.info("Entered valid credentials..");
			regpage.setPolicy();
			regpage.clickContinue();
			logger.info("Validating Expected Message..");
			
			String confmsg = regpage.getConfirmationMsg();
			
			if(confmsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
				
			}else {
				logger.error("Test Failed..");
				logger.debug("Debug logs..");
				Assert.assertTrue(false);
			}
			//Assert.assertEquals(confmsg, "Your Account Has Been Created!");
			
		}catch(Exception e) {
			
			Assert.fail();
		}
		
		logger.info("***********Finished**************");
		
	}

	
}
