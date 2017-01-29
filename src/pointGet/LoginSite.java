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
			case Define.PSITE_CODE_PIL:
				loginPil(driver, logg);
				break;
			case Define.PSITE_CODE_WAR:
				loginWar(driver, logg);
				break;
			case Define.PSITE_CODE_GMY:
			case Define.PSITE_CODE_GEN:
			case Define.PSITE_CODE_ECN:
			case Define.PSITE_CODE_MOP:
			case Define.PSITE_CODE_OSA:
			case Define.PSITE_CODE_I2I:
			case Define.PSITE_CODE_MOB:
			case Define.PSITE_CODE_CIT:
			case Define.PSITE_CODE_CRI:
			case Define.PSITE_CODE_HAP:
			case Define.PSITE_CODE_KOZ:
			case Define.PSITE_CODE_PIC:
			case Define.PSITE_CODE_NTM:
			case Define.PSITE_CODE_PMO:
			case Define.PSITE_CODE_PNY:
			case Define.PSITE_CODE_SUG:
			default:
				return;
		}
	}

	/**
	 * @param driver
	 * @param logg
	 */
	public static void loginWar(WebDriver driver, Logger logg) {
		driver.get("https://ssl.warau.jp/login?loopbackURL=http%3A%2F%2Fwww.warau.jp%2F");
		Utille.sleep(2000);
		WebElement ele = driver.findElement(By.cssSelector("input.mailForm"));
		ele.clear();
		ele.sendKeys(pGetProps.get(Define.PSITE_CODE_WAR).get("loginid"));
		ele = driver.findElement(By.cssSelector("input.passwordForm"));
		ele.clear();
		ele.sendKeys(pGetProps.get(Define.PSITE_CODE_WAR).get("loginpass"));
		driver.findElement(By.cssSelector("input.btn.btnlogin")).click();
		Utille.sleep(4000);
	}

	/**
	 * @param driver
	 * @param logg
	 */
	public static void loginPil(WebDriver driver, Logger logg) {
		driver.get("http://www.point-island.com/");
		WebElement ele = driver.findElement(By.cssSelector("input#mailadr"));
		ele.clear();
		ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PIL).get("loginid"));
		ele = driver.findElement(By.cssSelector("input#pwd"));
		ele.clear();
		ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PIL).get("loginpass"));
		driver.findElement(By.cssSelector("input[name='btnlogin']")).click();
		Utille.sleep(4000);
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
			if (!pGetProps.containsKey(Define.PSITE_CODE_RIN)
					|| !pGetProps.get(Define.PSITE_CODE_RIN).containsKey("loginid")
					|| !pGetProps.get(Define.PSITE_CODE_RIN).containsKey("loginpass")) {
				return;
			}
			WebElement ele = driver.findElement(By.cssSelector(selector));
			ele.clear();
			ele.sendKeys(pGetProps.get(Define.PSITE_CODE_RIN).get("loginid"));
			selector = "li#loginPw>input#p";
			ele = driver.findElement(By.cssSelector(selector));
			ele.clear();
			ele.sendKeys(pGetProps.get(Define.PSITE_CODE_RIN).get("loginpass"));
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
			if (!pGetProps.containsKey(Define.PSITE_CODE_PEX)
					|| !pGetProps.get(Define.PSITE_CODE_PEX).containsKey("loginid")
					|| !pGetProps.get(Define.PSITE_CODE_PEX).containsKey("loginpass")) {
				logg.warn("2");
			}
			// ログイン画面
			String selector2 = "input#pex_user_login_email";
			if (Utille.isExistEle(driver, selector2, logg)) {
				WebElement ele = driver.findElement(By.cssSelector(selector2));
				ele.clear();
				ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PEX).get("loginid"));
				ele = driver.findElement(By.cssSelector("input#pex_user_login_password"));
				ele.clear();
				ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PEX).get("loginpass"));
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
			if (!pGetProps.containsKey(Define.PSITE_CODE_PTO)
					|| !pGetProps.get(Define.PSITE_CODE_PTO).containsKey("loginid")
					|| !pGetProps.get(Define.PSITE_CODE_PTO).containsKey("loginpass")) {
				return;
			}
			// ログイン画面
			driver.get("https://www.pointtown.com/ptu/show_login.do?nextPath=%2Fptu%2Findex.do");
			String selector2 = "input.auth_input[name=uid]";
			if (isExistEle(driver, selector2)) {
				WebElement ele = driver.findElement(By.cssSelector(selector2));
				ele.clear();
				ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PTO).get("loginid"));
				ele = driver.findElement(By.cssSelector("input.auth_input[name=pass]"));
				ele.clear();
				ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PTO).get("loginpass"));
				driver.findElement(By.cssSelector("div.login-btn>input")).click();
				Utille.sleep(3000);
			}
		}
	}
}
