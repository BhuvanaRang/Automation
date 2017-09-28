package sfdcAssignment;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import practiceprograms.ExcelFile;

public class AutomationScripts extends ReUsableMethods{
	public static WebDriver driver;
	public static void SFDCLogin() throws Exception {


		/* Launch a Browser*/
		System.setProperty("webdriver.gecko.driver", "C:/Users/Abhis_lw0caw1/Google Drive/July 10 2017/July 10 2017 Read Only/Framework/Lib/geckodriver.exe");
		driver = new FirefoxDriver(); 

		/*Launch URL*/
		driver.get("https://login.salesforce.com/");

		/*Enter UserName CMMI-5 level coding std*/
		WebElement userName = driver.findElement(By.xpath("//input[@id='username']"));
		enterText(userName, "User@gmail.com", "UserName");

		/*Enter Password*/
		WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
		enterText(password, "Password123", "Password");
		WebElement loginButton = driver.findElement(By.xpath("//input[@id='Login']"));
		clickButton(loginButton, "Login");
	}



	public static void validateErrorMessage(){

	}

	/* Test case to check forgot password where we pass the login but password is empty*/

	public static void forgotPassword() throws InterruptedException, IOException {

		startReport("Forgot password testcase","C:\\Users\\Bhuvana\\Desktop\\Report");

		System.setProperty("webdriver.gecko.driver","C:\\Users\\Bhuvana\\Downloads\\geckodriver-v0.18.0-win32\\geckodriver.exe");
		Iterator<Row> itr=ExcelFile.readExcel("C:/Users/Bhuvana/Desktop/sfdc_ts/SFDCLoginClearPassword.xls");
		itr.next();

		FirefoxDriver driver = new 	FirefoxDriver();

		while(itr.hasNext()){

			/*WebElement userName = driver.findElement(By.xpath("//input[@id='username']"));
			enterText(userName, "User@gmail.com", "UserName");*/
			/*clickButton(WebElement obj,  String objName)*/

			Row row=itr.next();
			String url=row.getCell(1).getStringCellValue();
			String userName=row.getCell(2).getStringCellValue();
			String errormessage=row.getCell(3).getStringCellValue();

			driver.get(url);
			WebElement userNameElem=driver.findElement(By.id("username"));
			enterText(userNameElem, "r.bhu84@gmail.com", "UserName");
			WebElement password=driver.findElement(By.id("password"));
			enterText(password, "", "password");
			WebElement login=driver.findElement(By.id("Login"));
			clickButton(login,"Login successful");
			Thread.sleep(1000);
			String errMssg=driver.findElement(By.xpath("//*[@id='error']")).getText();

			if(errMssg.equals(errormessage)){
				Update_Report("Pass: " ,"Error message should be displayed", "Please enter your password message should be displayed");
			}else{
				Update_Report("Fail: " ,"Error message should be displayed", "Please enter your password message is not displayed");
			}
			bw.close();
		}
	}

	/********************************************************************************************************* */
	/* Test case to check whether rememberMe check box is getting clicked*/

	public static void rememberUserNameCheck() throws InterruptedException, IOException{

		startReport("Remember username testcase","C:\\Users\\Bhuvana\\Desktop\\Report");

		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Bhuvana\\Downloads\\geckodriver-v0.18.0-win32\\geckodriver.exe");
		Iterator<Row> itr=ExcelFile.readExcel("C:/Users/Bhuvana/Desktop/sfdc_ts/remusername.xls");
		itr.next();

		WebDriver driver=new FirefoxDriver();

		while(itr.hasNext()){

			Row row=itr.next();
			String url=row.getCell(1).getStringCellValue();
			String userName=row.getCell(2).getStringCellValue();
			String pwd=row.getCell(3).getStringCellValue();

			driver.get(url);
			WebElement userNameElem=driver.findElement(By.id("username"));
			enterText(userNameElem, userName, "userName");
			WebElement password=driver.findElement(By.id("password"));
			enterText(password, pwd, "password");
			driver.findElement(By.xpath(".//*[@id='rememberUn']")).click();
			WebElement login = driver.findElement(By.id("Login"));
			clickButton(login,"Login is successful");

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement rememberUserNameCheckBox=driver.findElement(By.id("userNavLabel"));
			clickButton(rememberUserNameCheckBox,"rememberUserNameCheckBox is clicked");
			List<WebElement> elements = driver.findElements(By.xpath(".//*[@id='userNav-menuItems']"));

			for(WebElement elem : elements) {
				WebElement logoutElem = elem.findElement(By.linkText("Logout"));
				if (logoutElem != null) {
					clickButton(logoutElem,"Logout button is click");
					break;
				}
			}
			Thread.sleep(5000);
			String uname=driver.findElement(By.xpath("//*[@id='idcard']//span")).getText();
			if(uname.equals(userName)){
				Update_Report("Pass: " ,"Check for username field", "Username is displayed");
			}else{
				Update_Report("Fail: " ,"Check for username field", "Username is not displayed");
			}
			bw.close();

		}
	}


	/********************************************************************************************************************/

	/* Forgot password link should be clicked, Password reset message is displayed and an email is sent to associated <username> */

	public static void forgotPasswordLink() throws Exception{
		/*Launch a Browser*/

		startReport("forgot password Link","C:\\Users\\Bhuvana\\Desktop\\Report");
		System.setProperty("webdriver.gecko.driver","C:\\Users\\Bhuvana\\Downloads\\geckodriver-v0.18.0-win32\\geckodriver.exe");

		Iterator<Row> itr=ExcelFile.readExcel("C:/Users/Bhuvana/Desktop/sfdc_ts/ForgotPW4B.xls");
		itr.next();
		FirefoxDriver driver = new FirefoxDriver();

		while(itr.hasNext()){

			Row row=itr.next();
			String url=row.getCell(1).getStringCellValue();
			String userName=row.getCell(2).getStringCellValue();
			String messagePage=row.getCell(3).getStringCellValue();

			/* Launch URL */
			driver.get(url);

			WebElement forgotPasswordLink=driver.findElement(By.xpath(".//*[@id='forgot_password_link']"));
			clickButton(forgotPasswordLink,"forgotPasswordLink link is clicked");

			WebElement userNameElem=driver.findElement(By.xpath(".//*[@id='un']"));
			enterText(userNameElem, userName, "userName is entered in the username field of forgot password page");

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement continueElem=driver.findElement(By.xpath(".//*[@id='continue']"));
			clickButton(continueElem,"Continue is clicked");

			Thread.sleep(3000);

			String checkYourEmail=driver.findElement(By.xpath("//*[@id='header")).getText();
			if(checkYourEmail.equals(messagePage)){
				Update_Report("Pass: " ,"Test Forgot Password", "An email associated with the <username> account is sent");
			}else{
				Update_Report("Fail: " ,"Test Forgot Password", "An email associated with the <username> account is sent");
			}
			bw.close();

		}
	}

	/**
	 * @throws InterruptedException *************************************************************************************************************************/

	public static void forgotPassword4B() throws IOException, InterruptedException {

		startReport("forgot password4B testcase","C:\\Users\\Bhuvana\\Desktop\\Report");
		System.setProperty("webdriver.gecko.driver","C:\\Users\\Bhuvana\\Downloads\\geckodriver-v0.18.0-win32\\geckodriver.exe");
		Iterator<Row> itr=ExcelFile.readExcel("C:/Users/Bhuvana/Desktop/sfdc_ts/ForgotPW4thB.xls");
		itr.next();

		FirefoxDriver driver = new 	FirefoxDriver();

		while(itr.hasNext()){

			Row row=itr.next();
			String url=row.getCell(1).getStringCellValue();
			String userName=row.getCell(2).getStringCellValue();
			String pwd=row.getCell(3).getStringCellValue();
			String errormessage=row.getCell(4).getStringCellValue();

			driver.get(url);
			WebElement userNameElem=driver.findElement(By.id("username"));
			enterText(userNameElem, userName, "userName");

			WebElement password=driver.findElement(By.id("password"));
			enterText(password, pwd, "password");

			WebElement login = driver.findElement(By.id("Login"));
			clickButton(login,"Login button is clicked");
			Thread.sleep(1000);
			String errMssg=driver.findElement(By.xpath("//*[@id='error']")).getText();

			Assert.assertEquals(errMssg,errormessage);

			if(errMssg.equals(errormessage)){
				Update_Report("Pass: " ,"After the login button is clicked", "Please check your username and password.If you still can't log in, contact your Salesforce administrator");
			}else{
				Update_Report("Fail: " ,"After the login button is clicked", "Error message is not displayed");
			}
			bw.close();
		}
	}


/********************************************************************************************************************************************/

/*click My profile */

	public static void userMenu() throws InterruptedException, IOException{
		startReport("userMenu","C:\\Users\\Bhuvana\\Desktop\\Report");
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Bhuvana\\Downloads\\geckodriver-v0.18.0-win32\\geckodriver.exe");
		Iterator<Row> itr=ExcelFile.readExcel("C:/Users/Bhuvana/Desktop/sfdc_ts/UserMenu.xls");
		itr.next();
	
		FirefoxDriver driver=new FirefoxDriver();
		while(itr.hasNext()){
	
			Row row=itr.next();
			String url=row.getCell(1).getStringCellValue();
			String userName=row.getCell(2).getStringCellValue();
			String pwd=row.getCell(3).getStringCellValue();
	
			driver.get(url);
			WebElement userNameElem=driver.findElement(By.id("username"));
			enterText(userNameElem, userName, "userName");
			//username.sendKeys(userName);
			WebElement passwordElem=driver.findElement(By.id("password"));
			enterText(passwordElem, pwd, "password");
			//password.sendKeys(pwd);
			WebElement loginElem=driver.findElement(By.id("Login"));
			clickButton(loginElem,"Login button is clicked");
	
			Thread.sleep(6000);
			
			WebElement usernamedisplayed=driver.findElement(By.id("userNavLabel"));
			clickButton(usernamedisplayed,"click the username at the right top");
			List<WebElement> elements = driver.findElements(By.xpath(".//*[@id='userNav-menuItems']"));
	
			for(WebElement elem : elements) {
				WebElement myProfile = elem.findElement(By.linkText("My Profile"));
				if (myProfile != null) {
					clickButton(myProfile, "my profile is clicked");
					break;
				}
			}
			
			
		}
	}
	
	public static void myProfile() throws InterruptedException, IOException{
		startReport("myProfile","C:\\Users\\Bhuvana\\Desktop\\Report");
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Bhuvana\\Downloads\\geckodriver-v0.18.0-win32\\geckodriver.exe");
		Iterator<Row> itr=ExcelFile.readExcel("C:/Users/Bhuvana/Desktop/sfdc_ts/UserMenu.xls");
		itr.next();
	
		FirefoxDriver driver=new FirefoxDriver();
		while(itr.hasNext()){
	
			Row row=itr.next();
			String url=row.getCell(1).getStringCellValue();
			String userName=row.getCell(2).getStringCellValue();
			String pwd=row.getCell(3).getStringCellValue();
	
			driver.get(url);
			WebElement userNameElem=driver.findElement(By.id("username"));
			enterText(userNameElem, userName, "userName");
			//username.sendKeys(userName);
			WebElement passwordElem=driver.findElement(By.id("password"));
			enterText(passwordElem, pwd, "password");
			//password.sendKeys(pwd);
			WebElement loginElem=driver.findElement(By.id("Login"));
			clickButton(loginElem,"Login button is clicked");
	
			Thread.sleep(6000);
			
			WebElement usernamedisplayed=driver.findElement(By.id("userNavLabel"));
			clickButton(usernamedisplayed,"click the username at the right top");
			List<WebElement> elements = driver.findElements(By.xpath(".//*[@id='userNav-menuItems']"));
	
			for(WebElement elem : elements) {
				WebElement myProfile = elem.findElement(By.linkText("My Profile"));
				if (myProfile != null) {
					clickButton(myProfile, "my profile is clicked");
					break;
				}
			}
			
			WebElement elem = driver.findElement(By.xpath("//img[@ title='Edit Profile']"));
			clickButton(elem, "Edit Profile");;
			driver.switchTo().frame("contactInfoContentId");
			driver.findElement(By.id("aboutTab")).click();
			WebElement lastName=driver.findElement(By.id("lastName"));
			lastName.clear();
			enterText(lastName, "Rang", "Password");
			
			WebElement saveAll = driver.findElement(By.xpath("//input[@ value='Save All']"));
			clickButton(saveAll, "Save All");
			driver.navigate().refresh();		
			
			WebElement publishContent = driver.findElement(By.id("publisherAttachContentPost"));
			publishContent.click();
			//driver.findElement(By.xpath( ".//*[text()='File' and @class='publisherattachtext']")).click();
			//driver.findElement(By.xpath("//ul/li[2]/a/span[1]")).click();
			//driver.findElement(By.xpath(".//*[@id='publisherAttachContentPost']/span[1]")).click();
			//driver.findElement(By.className("publisherattachtext")).click();
			//Thread.sleep(10000);
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
			WebElement chatterUpload = driver.findElement(By.xpath("//a[@id='chatterUploadFileAction']"));
			chatterUpload.click();
			bw.close();
		}
	}
	
	public static void mySettings() throws InterruptedException, IOException{
		startReport("mySettings","C:\\Users\\Bhuvana\\Desktop\\Report");
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Bhuvana\\Downloads\\geckodriver-v0.18.0-win32\\geckodriver.exe");
		Iterator<Row> itr=ExcelFile.readExcel("C:/Users/Bhuvana/Desktop/sfdc_ts/UserMenu.xls");
		itr.next();
	
		FirefoxDriver driver=new FirefoxDriver();
		while(itr.hasNext()){
	
			Row row=itr.next();
			String url=row.getCell(1).getStringCellValue();
			String userName=row.getCell(2).getStringCellValue();
			String pwd=row.getCell(3).getStringCellValue();
	
			driver.get(url);
			WebElement userNameElem=driver.findElement(By.id("username"));
			enterText(userNameElem, userName, "userName");
			//username.sendKeys(userName);
			WebElement passwordElem=driver.findElement(By.id("password"));
			enterText(passwordElem, pwd, "password");
			//password.sendKeys(pwd);
			WebElement loginElem=driver.findElement(By.id("Login"));
			clickButton(loginElem,"Login button is clicked");
	
			Thread.sleep(6000);
			
			WebElement usernamedisplayed=driver.findElement(By.id("userNavLabel"));
			clickButton(usernamedisplayed,"click the username at the right top");
			List<WebElement> elements = driver.findElements(By.xpath(".//*[@id='userNav-menuItems']"));
	
			for(WebElement elem : elements) {
				WebElement mySettings = elem.findElement(By.linkText("My Settings"));
				if (mySettings != null) {
					clickButton(mySettings, "my settings is clicked");
					break;
				}
			}
			
			WebElement personal = driver.findElement(By.xpath(".//a[contains(@class,'header') and contains(@class,'setupFolder')]"));
			personal.click();

			Thread.sleep(3000);
			
			WebElement loginHistory = driver.findElement(By.xpath(".//span[@id='LoginHistory_font']"));
			loginHistory.click();

			
			
			
			
			
			bw.close();
		}
	}
}



