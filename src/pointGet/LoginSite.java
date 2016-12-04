package pointGet;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author saitou
 *
 */
public class LoginSite extends PointGet {

	private static String selector = "";

	/**
	 * @param siteCode
	 * @param driver
	 * @param logg
	 */
	public static void login(String siteCode, WebDriver driver, Logger logg) {
		switch (siteCode) {
		case Define.PSITE_CODE_RIN:
			loginRin(driver, logg);
			break;
		case Define.PSITE_CODE_PEX:
			loginPex(driver, logg);
			break;
		case Define.PSITE_CODE_PTO:
			loginPto(driver, logg);
			break;
		case Define.PSITE_CODE_GMY:
		case Define.PSITE_CODE_GEN:
		case Define.PSITE_CODE_ECN:
		case Define.PSITE_CODE_MOP:
		case Define.PSITE_CODE_OSA:
		case Define.PSITE_CODE_I2I:
		default:
			return;
		}
	}

	/**
	 * @param driver 
	 * @param logg 
	 */
	public static void loginRin(WebDriver driver, Logger logg) {
		driver.get("https://www.rakuten-card.co.jp/e-navi/index.xhtml");
		selector = "li#loginId>input#u";
		// ログイン画面であれば
		if (Utille.isExistEle(driver, selector, logg)) {
			if (!pGetProps.containsKey("rin") || !pGetProps.get("rin").containsKey("loginid")
					|| !pGetProps.get("rin").containsKey("loginpass")) {
				return;
			}
			WebElement ele = driver.findElement(By.cssSelector(selector));
			ele.clear();
			ele.sendKeys(pGetProps.get("rin").get("loginid"));
			selector = "li#loginPw>input#p";
			ele = driver.findElement(By.cssSelector(selector));
			ele.clear();
			ele.sendKeys(pGetProps.get("rin").get("loginpass"));
			driver.findElement(By.cssSelector("input#loginButton")).click();
			Utille.sleep(5000);
		}
	}

	/**
	 * @param driver 
	 * @param logg 
	 */
	public static void loginPex(WebDriver driver, Logger logg) {
		String selector = "dd.user_pt.fw_b>span.fw_b";
		driver.get("https://pex.jp/user/point_passbook/all");
		if (!Utille.isExistEle(driver, selector, logg)) {
			if (!pGetProps.containsKey("pex") || !pGetProps.get("pex").containsKey("loginid")
					|| !pGetProps.get("pex").containsKey("loginpass")) {
				logg.warn("2");
			}
			// ログイン画面
			String selector2 = "input#pex_user_login_email";
			if (Utille.isExistEle(driver, selector2, logg)) {
				WebElement ele = driver.findElement(By.cssSelector(selector2));
				ele.clear();
				ele.sendKeys(pGetProps.get("pex").get("loginid"));
				ele = driver.findElement(By.cssSelector("input#pex_user_login_password"));
				ele.clear();
				ele.sendKeys(pGetProps.get("pex").get("loginpass"));
				driver.findElement(By.cssSelector("input.form-submit")).click();
				Utille.sleep(3000);
			}
		}
	}

	/**
	 * @param driver 
	 * @param logg 
	 */
	public static void loginPto(WebDriver driver, Logger logg) {
		selector = "li.point>a>strong";
		driver.get("https://www.pointtown.com/ptu/index.do");
		if (!isExistEle(driver, selector)) {
			if (!pGetProps.containsKey("pto") || !pGetProps.get("pto").containsKey("loginid")
					|| !pGetProps.get("pto").containsKey("loginpass")) {
				return;
			}
			// ログイン画面
			driver.get("https://www.pointtown.com/ptu/show_login.do?nextPath=%2Fptu%2Findex.do");
			String selector2 = "input.auth_input[name=uid]";
			if (isExistEle(driver, selector2)) {
				WebElement ele = driver.findElement(By.cssSelector(selector2));
				ele.clear();
				ele.sendKeys(pGetProps.get("pto").get("loginid"));
				ele = driver.findElement(By.cssSelector("input.auth_input[name=pass]"));
				ele.clear();
				ele.sendKeys(pGetProps.get("pto").get("loginpass"));
				driver.findElement(By.cssSelector("div.login-btn>input")).click();
				Utille.sleep(3000);
			}
		}
	}
}
