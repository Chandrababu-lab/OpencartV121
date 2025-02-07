package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage{

	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id="input-firstname")
	WebElement firstName;
	
	@FindBy(id="input-lastname")
	WebElement lastName;
	
	@FindBy(id="input-email")
	WebElement email;
	
	@FindBy(id="input-telephone")
	WebElement phoneNumber;
	
	@FindBy(id="input-password")
	WebElement password;
	
	@FindBy(id="input-confirm")
	WebElement confirm_password;
	
	@FindBy(xpath="//input[@name='agree']")
	WebElement agree;
	
	@FindBy(xpath="//input[@value='Continue']")
	WebElement register;
	
	@FindBy(xpath="//div/h1[text()='Your Account Has Been Created!']")
	WebElement msgconfirm;

	public void setFirstName(String fname) {
		firstName.sendKeys(fname);
	}
	
	public void setLastName(String lname) {
		lastName.sendKeys(lname);
	}
	
	public void setEmail(String mail) {
		email.sendKeys(mail);
	}
	
	public void setPhone(String phone) {
		phoneNumber.sendKeys(phone);
	}
	
	public void setPassword(String pass) {
		password.sendKeys(pass);
	}
	
	public void setConfirmPassword(String cpass) {
		confirm_password.sendKeys(cpass);
	}
	
	public void setPolicy() {
		agree.click();
	}
	
	public void clickContinue() {
		register.click();
	}
	
	public String getConfirmationMsg() {
		try {
			return (msgconfirm.getText());
		}catch(Exception e) {
			return (e.getMessage());
		}
	}
	
}
