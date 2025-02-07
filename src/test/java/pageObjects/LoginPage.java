package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id="input-email")
	WebElement email;
	
	@FindBy(id="input-password")
	WebElement password;
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement loginbtn;
	
	public void setEmail(String m) {
		email.sendKeys(m);
	}
	
	public void setPass(String p) {
		password.sendKeys(p);
	}

	public void clickLoginBtn() {
		loginbtn.click();
	}
}
