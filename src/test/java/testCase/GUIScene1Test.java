package testCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import application.GUI;
import application.LibraryController;
import javafx.scene.Scene;
import net.sourceforge.marathon.javadriver.JavaDriver;
import net.sourceforge.marathon.javadriver.JavaProfile;
import net.sourceforge.marathon.javadriver.JavaProfile.LaunchMode;
import net.sourceforge.marathon.javadriver.JavaProfile.LaunchType;

public class GUIScene1Test extends JavaFXTest {
	private WebDriver driver;
	private GUI gui = new GUI();
	private LibraryController controller = new LibraryController();

	@Before
	public void setup() {
		JavaProfile profile = new JavaProfile(LaunchMode.EMBEDDED);
		profile.setLaunchType(LaunchType.FX_APPLICATION);
		driver = new JavaDriver(profile);
	}

	@After
	public void teardown() {
		driver.quit();
	}	
	
	// Test if user exists in database 
	@Test
	public void testIfUserExist() throws InterruptedException {
		String ssn = "750628-0000";
		assertTrue(controller.checkIfBorrowerExist(ssn));		
	}
	
	// Test if login succeed with "login" button, correct personal number and correct password
	@Test
	public void testLoginSuccessful() throws InterruptedException {
		String ssn = "750628-0000";
		String pass ="GOgo2020%";
		WebElement user = driver.findElement(By.id("userSSN"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.cssSelector("button[text='Login']"));
		user.sendKeys(ssn);
		password.sendKeys(pass);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(loginButton));
		loginButton.click();
		assertTrue(gui.loginSuccessful);	
	}
	
	// Test if login not succeed with "login" button, correct password but incorrect serial number
	@Test
	public void loginFailWrongSSN() {
		String ssn = "7506287777";
		String pass ="GOgo2020%";
		WebElement user = driver.findElement(By.id("userSSN"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.cssSelector("button[text='Login']"));
		WebElement messageLabel = driver.findElement(By.id("messageLabel"));
		user.sendKeys(ssn);
		password.sendKeys(pass);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(loginButton));
		loginButton.click();
		assertEquals("Please enter a valid SSN", messageLabel.getText());
	}
	
	// Test if login not succeed with "login" button, correct serial number but incorrect password
	@Test
	public void loginFailWrongPassword() {
		String ssn = "750628-0000";
		String pass ="gogo2020%";
		WebElement user = driver.findElement(By.id("userSSN"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.cssSelector("button[text='Login']"));
		WebElement messageLabel = driver.findElement(By.id("messageLabel"));
		user.sendKeys(ssn);
		password.sendKeys(pass);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(loginButton));
		loginButton.click();
		assertEquals("Please enter a valid password", messageLabel.getText());
	}
	

	@Override
	protected Scene getScene() {
		return gui.createScene();
	}
	
}
