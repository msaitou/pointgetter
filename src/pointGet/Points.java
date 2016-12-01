package pointGet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * write the current point 
 * @author 雅人
 *
 */
public class Points extends PointGet {

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

	protected static void init() {
		PointGet.init();
		_setLogger("log4jPoints.properties", Points.class);
	}

	/**
	 * entry point
	 * @param args
	 */
	public static void main(String[] args) {
		init();
		StringBuffer sb = new StringBuffer();
		WebDriver driver = getWebDriver();
		for (String siteCode : pointSitelist) {
			String selector = "";
			String outPut = "";
			switch (siteCode) {
			case Define.PSITE_CODE_GMY:
				selector = "span.user_point";
				driver.get("http://dietnavi.com/pc");
				if (isExistEle(driver, selector)) {
					String point = driver.findElement(By.cssSelector(selector)).getText();
					outPut = "[" + Define.PSITE_CODE_GMY + ":" + point + "] ";
				}
				break;
			case Define.PSITE_CODE_GEN:
				selector = "li#user_point01>a>span";
				driver.get("http://www.gendama.jp/");
				if (isExistEle(driver, selector)) {
					String point = driver.findElement(By.cssSelector(selector)).getText();
					outPut = "[" + Define.PSITE_CODE_GEN + ":" + point + "] ";
				}
				break;
			case Define.PSITE_CODE_ECN:
				selector = "p.user_point_txt>strong";
				driver.get("https://ecnavi.jp/mypage/point_history/");
				if (isExistEle(driver, selector)) {
					String point = driver.findElement(By.cssSelector(selector)).getText();
					outPut = "[" + Define.PSITE_CODE_ECN + ":" + point + "] ";
				}
				break;
			case Define.PSITE_CODE_MOP:
				driver.get("http://pc.moppy.jp/bankbook/");
				selector = "div#point_blinking strong";
				if (isExistEle(driver, selector)) {
					String point = driver.findElement(By.cssSelector(selector)).getText();
					outPut = "[" + Define.PSITE_CODE_MOP + ":" + point + " ";
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
				// login!!
				LoginSite.login(Define.PSITE_CODE_PEX, driver, logg);
				if (isExistEle(driver, selector)) {
					String point = driver.findElement(By.cssSelector(selector)).getText();
					outPut = "[" + Define.PSITE_CODE_PEX + ":" + point + "] ";
				}
				break;
			case Define.PSITE_CODE_OSA:
				selector = "dl.bankbook-total>dd.current.coin>span";
				driver.get("https://osaifu.com/contents/bankbook/top/");
				if (isExistEle(driver, selector)) {
					String point = driver.findElement(By.cssSelector(selector)).getText();
					outPut = "[" + Define.PSITE_CODE_OSA + ":" + point + " ";
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
				// login!!
				LoginSite.login(Define.PSITE_CODE_PTO, driver, logg);
				if (isExistEle(driver, selector)) {
					String point = driver.findElement(By.cssSelector(selector)).getText();
					outPut = "[" + Define.PSITE_CODE_PTO + ":" + point + "] ";
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
					outPut = "[" + Define.PSITE_CODE_I2I + ":" + point + "] ";
				}
				break;
			default:
			}
			if (outPut.length() > 0) {
				sb.append(outPut);
			}
			else {
				// 取得できなかった
				logg.warn("missed site:" + siteCode);
			}
		}
		logg.warn(sb.toString());
		driver.quit();
	}
}
