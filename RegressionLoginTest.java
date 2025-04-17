import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class RegressionLoginTest {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\path\\to\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            driver.manage().window().maximize();
            driver.get("file:///C:/path/to/your/index.html"); // apne HTML file ka path daalna

            // Test Case 1: Valid Login
            performLogin(driver, "test@example.com", "123456", true);

            // Test Case 2: Invalid Email
            performLogin(driver, "wrong@example.com", "123456", false);

            // Test Case 3: Invalid Password
            performLogin(driver, "test@example.com", "wrongpassword", false);

            // Test Case 4: Blank Email and Password
            performLogin(driver, "", "", false);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    public static void performLogin(WebDriver driver, String email, String password, boolean expectedSuccess) {
        try {
            // Fill the email
            WebElement emailField = driver.findElement(By.id("email"));
            emailField.clear();
            emailField.sendKeys(email);

            // Fill the password
            WebElement passwordField = driver.findElement(By.id("password"));
            passwordField.clear();
            passwordField.sendKeys(password);

            // Click Login
            WebElement loginButton = driver.findElement(By.tagName("button"));
            loginButton.click();

            // Wait for alert
            Thread.sleep(2000);

            // Capture alert text
            String alertText = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();

            // Check expected vs actual
            if (expectedSuccess && alertText.contains("Successful")) {
                System.out.println("✅ Test Passed: Valid Login");
            } else if (!expectedSuccess && alertText.contains("Invalid")) {
                System.out.println("✅ Test Passed: Invalid Login Handled");
            } else {
                System.out.println("❌ Test Failed: Unexpected behavior");
            }

        } catch (Exception e) {
            System.out.println("❌ Exception in test case: " + e.getMessage());
        }
    }
}
