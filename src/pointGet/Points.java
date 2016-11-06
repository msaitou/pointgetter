package pointGet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * write the current point 
 * @author 雅人
 *
 */
public class Points {
	// log
	private static Logger log = null;

	private static String[] pointSitelist = {
			Define.PSITE_CODE_GMY,
			Define.PSITE_CODE_OSA,
			Define.PSITE_CODE_PTO,
			Define.PSITE_CODE_PEX,
			Define.PSITE_CODE_ECN,
			Define.PSITE_CODE_I2I,
			Define.PSITE_CODE_GEN,
			Define.PSITE_CODE_MOP
	};

	/**
	 * entry point
	 * @param args
	 */
	public static void main(String[] args) {
		_setLogger();
		StringBuffer sb = new StringBuffer();
		WebDriver driver = Utille.getWebDriver();
		for (String siteCode : pointSitelist) {
			String selector = "";
			String outPut = "";
			switch (siteCode) {
			case Define.PSITE_CODE_GMY:
				selector = "span.user_point";
				driver.get("http://dietnavi.com/pc");
				if (isExistEle(driver, selector)) {
					String point = driver.findElement(By.cssSelector(selector)).getText();
					outPut = Define.PSITE_CODE_GMY + " [point:" + point + "] ";
				}
				break;
			case Define.PSITE_CODE_GEN:
				selector = "li#user_point01>a>span";
				driver.get("http://www.gendama.jp/");
				if (isExistEle(driver, selector)) {
					String point = driver.findElement(By.cssSelector(selector)).getText();
					outPut = Define.PSITE_CODE_GEN + " [point:" + point + "] ";
				}
				break;
			case Define.PSITE_CODE_ECN:
				selector = "p.user_point_txt>strong";
				driver.get("https://ecnavi.jp/mypage/point_history/");
				if (isExistEle(driver, selector)) {
					String point = driver.findElement(By.cssSelector(selector)).getText();
					outPut = Define.PSITE_CODE_ECN + " [point:" + point + "] ";
				}
				break;
			case Define.PSITE_CODE_MOP:
				driver.get("http://pc.moppy.jp/bankbook/");
				selector = "div#point_blinking strong";
				if (isExistEle(driver, selector)) {
					String point = driver.findElement(By.cssSelector(selector)).getText();
					outPut = Define.PSITE_CODE_MOP + " [point:" + point + " ";
				}
				selector = "div#point_blinking em";
				if (isExistEle(driver, selector)) {
					String coin = driver.findElement(By.cssSelector(selector)).getText();
					outPut += " coin:" + coin + "] ";
				}
				break;
			case Define.PSITE_CODE_PEX:
				selector = "dd.user_pt.fw_b>span.fw_b";
				driver.get("https://pex.jp/user/point_passbook/all");
				if (isExistEle(driver, selector)) {
					String point = driver.findElement(By.cssSelector(selector)).getText();
					outPut = Define.PSITE_CODE_PEX + " [point:" + point + "] ";
				}
				break;
			case Define.PSITE_CODE_OSA:
				selector = "dl.bankbook-total>dd.current.coin>span";
				driver.get("https://osaifu.com/contents/bankbook/top/");
				if (isExistEle(driver, selector)) {
					String point = driver.findElement(By.cssSelector(selector)).getText();
					outPut = Define.PSITE_CODE_OSA + " [point:" + point + " ";
				}
				selector = "dl.bankbook-total>dd.gold.coin>span";
				if (isExistEle(driver, selector)) {
					String gold = driver.findElement(By.cssSelector(selector)).getText();
					outPut += " gold:" + gold + "] ";
				}
				break;
			case Define.PSITE_CODE_PTO:
				selector = "li.point>a>strong";
				driver.get("https://www.pointtown.com/ptu/index.do");
				if (!isExistEle(driver, selector)) {
					// ログイン画面
					driver.get("https://www.pointtown.com/ptu/show_login.do?nextPath=%2Fptu%2Findex.do");
					String selector2 = "input.auth_input[name=uid]";
					if (isExistEle(driver, selector2)) {
						WebElement ele = driver.findElement(By.cssSelector(selector2));
						ele.clear();
						ele.sendKeys("clonecopyfake@gmail.com");
						ele = driver.findElement(By.cssSelector("input.auth_input[name=pass]"));
						ele.clear();
						ele.sendKeys("clonecopy5");
						driver.findElement(By.cssSelector("div.login-btn>input")).click();
						Utille.sleep(3000);
					}
				}
				if (isExistEle(driver, selector)) {
					String point = driver.findElement(By.cssSelector(selector)).getText();
					outPut = Define.PSITE_CODE_PTO + " [point:" + point + "] ";
				}
				else {
					// ログインができていない可能性がある
				}
				break;
			case Define.PSITE_CODE_I2I:
				selector = "td.ad_point>span.limited";
				driver.get("https://point.i2i.jp/account/");
				if (isExistEle(driver, selector)) {
					String point = driver.findElement(By.cssSelector(selector)).getText();
					outPut = Define.PSITE_CODE_I2I + " [point:" + point + "] ";
				}
				break;
			}
			if (outPut.length() > 0) {
				sb.append(outPut);
			}
			else {
				// 取得できなかった
				log.warn("missed site:" + siteCode);
			}
		}
		log.info(sb.toString());
		driver.quit();
	}

	/**
	 * ログクラスの設定
	 */
	private static void _setLogger() {
		PropertyConfigurator.configure("log4jPoints.properties");
		log = Utille.setLogger(Points.class);
	}

	private static boolean isExistEle(WebDriver driver, String selector) {
		return Utille.isExistEle(driver, selector, log);
	}
}
