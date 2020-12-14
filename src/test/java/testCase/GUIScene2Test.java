package testCase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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

public class GUIScene2Test extends JavaFXTest {
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
	
	// Test "search" button with search text "city"
	@Test
	public void testSearch() throws InterruptedException {
		WebElement search = driver.findElement(By.cssSelector("text-field"));
		WebElement searchButton = driver.findElement(By.cssSelector("button[text='Search']"));
		search.sendKeys("city");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		searchButton.click();
		assertNotNull(gui.theTextArea.getItems());
	}
	
	// Test "borrowed" button
	@Test
	public void testSearchBorrowed() throws InterruptedException {
		WebElement borrowedButton = driver.findElement(By.cssSelector("button[text='Borrowed']"));
		borrowedButton.click();
		assertNotNull(gui.theTextArea.getItems());
	} 
	
	// Test user input with search text "mat" and "search" button
	@Test
	public void testCheckUserInput( ) {	
		WebElement search = driver.findElement(By.cssSelector("text-field"));
		WebElement searchButton = driver.findElement(By.cssSelector("button[text='Search']"));
		search.sendKeys("mat");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		searchButton.click();
		assertTrue(controller.checkUserInput("mat"));		
		}
	
	// Test user input with search text in digits "366665" and "search" button
	@Test
	public void testCheckInputOnlyDigits( ) {
		WebElement search = driver.findElement(By.cssSelector("text-field"));
		WebElement searchButton = driver.findElement(By.cssSelector("button[text='Search']"));
		search.sendKeys("366665");
		searchButton.click();
		assertTrue(controller.checkInputOnlyDigits("366665"));	
		}
	
	// Test if logout succeed with "logout" button
	@Test
	public void testLogoutSuccesful( ) {
		WebElement logoutButton = driver.findElement(By.cssSelector("button[text='Logout']"));
		logoutButton.click();
		assertFalse(gui.loginSuccessful);
		}

	@Override
	protected Scene getScene() {
		return gui.createScene2();
	}
}
