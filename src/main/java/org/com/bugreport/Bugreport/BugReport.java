package org.com.bugreport.Bugreport;

import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BugReport {

	public static WebDriver driver;
	public final static String propFilePath = "C:\\Users\\immadan\\eclipse-workspace\\Bugreport\\src\\main\\java\\org\\com\\bugreport\\Bugreport\\Bugreportprop.properties";
	Properties prop;

	HtmlEmail message = new HtmlEmail();

	public void reportLogic() throws InterruptedException, EmailException {

		prop = readPropertyFile(propFilePath);
		String chromeDriver = prop.getProperty("driver");
		String driverPath = prop.getProperty("driverPath");
		System.setProperty(chromeDriver, driverPath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
		String url = prop.getProperty("URL");
		driver.get(url);
		driver.manage().window().maximize();
		driver.findElement(By.className("idp")).click();
		String userName = prop.getProperty("userName");
		String password = prop.getProperty("password");
		driver.findElement(By.xpath("//input[@id='user_name']")).sendKeys(userName);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@id='verify_btn']")).click();
		Thread.sleep(8000);

		String userId = prop.getProperty("userId"); // userid fetch from pro file

		int bugjira = 0;

		String[] userIdCount = userId.split(","); // split as array storing the value in useridCount

		int bugcount = userIdCount.length;

		ArrayList uv = new ArrayList();
		ArrayList bj = new ArrayList();
		ArrayList jl = new ArrayList();
		String uservalid = "";
		String jiraUrl = "";

		String[] userMailID = { "immadan@amazon.com", "kxrahu@amazon.com" };

		for (int i = 0; i < userIdCount.length; i++) {

			uservalid = userIdCount[i];

			System.out.println(uservalid);

			try {

				String bugs = "https://issues.labcollab.net/issues/?filter=-2&jql="
						+ "issuetype = Bug AND status in (Screen, \"In Progress\", Open, Review, Verify) AND assignee in ("
						+ uservalid + ")";

				driver.get(bugs);
				bugjira = driver.findElements(By.xpath("// table[@id='issuetable']//tr")).size() - 1;

			} catch (Exception e) {

				bugjira = 0;

			}

			System.out.println(bugjira);

			jiraUrl = driver.getCurrentUrl();
			String employeeName = prop.getProperty(uservalid);

			if (bugjira > 0) {

				uv.add(uservalid);
				bj.add(bugjira);
				jl.add(jiraUrl);

				System.out.println("Bugs:" + bugjira);

			}

			else {
				System.out.println("No Bugs found");
			}

		}

		driver.quit();

		message = new HtmlEmail();
		String htmlOutput = "<html><head style=font-family:Calibri;font-size:14px;><p>Hi All"
				+ ",</p><p>Please find the actionable bugs <b>" + "</b></p></head><body><style>\r\n" + "table {\r\n"
				+ "  font-family:Calibri(Body);\r\n" + "border:solid windowtext 1.0pt;" + "  border-style: solid;\r\n"
				+ " border-color: black;\r\n" + "  border-collapse: collapse;\r\n" + "  width:;\r\n" + "}\r\n" + "\r\n"
				+ "td, th {\r\n" + "  border:solid windowtext 1.0pt;\r\n" + "  text-align: centre;\r\n"
				+ "  padding: 2.5px;\r\n" + "}\r\n" + "\r\n" + "th {\r\n" + "  background-color: #D9E1F2;\r\n" + "}\r\n"
				+ "\r\n" + "tr:nth-child(even) {\r\n" + "\r\n" + "}\r\n"
				+ "</style><table style=font-family:Calibri;font-size:14px;><tr><th><center>  Assignee </center></th><th><center> Bugs </center> </th></tr>";

		for (int i = 0; i < uv.size(); i++) {

			htmlOutput += "<tr><td><center> " + uv.get(i) + "</center></td><td><center><a href=" + jl.get(i) + ">"
					+ bj.get(i) + "</a></center></td></tr>";

		}

		htmlOutput += "</table>";
		htmlOutput += "<p style = color:blue;><i>Don't reply to this automatic generated mail for any queries please reach out @aiswas.</i></p>";
		htmlOutput += "<p>Thanks</p>";
		htmlOutput += "<p>QS team</p>";
		htmlOutput += "</body></html>";
		message.setHtmlMsg(htmlOutput);
		message.setHostName("smtp.amazon.com");

		// message.addTo(uservalid + "@amazon.com");
		message.addTo(userMailID);
		message.addCc("kxrahu@amazon.com"); 

		// message.addCc("aiswas@amazon.com");

		message.setFrom("no-reply-report@amazon.com", "Bug validation");
		message.setSubject("Actionable Bugs");
		message.send();

		System.out.println("mail sent to the associates");

	}

	public Properties readPropertyFile(String filepath) {
		try {
			FileReader reader = new FileReader(filepath);
			Properties prop = new Properties();
			prop.load(reader);
			return prop;
		} catch (Exception e) {
			System.out.println("Cannot read property file... " + e);
		}
		return null;

	}

	public static void main(String[] args) throws InterruptedException, EmailException {

		BugReport bugReport = new BugReport();
		bugReport.reportLogic();

	}

}
