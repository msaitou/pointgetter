package pointGet;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * write the current point
 * @author saito
 */
public class Points extends PointGet {

	private static double total = 0;
	private static String[] pointSitelist = null;

	protected static void init() {
		PointGet.init(Points.class.getSimpleName());
		pointSitelist = loadProps.getProperty("pointTargetList").split(",");
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
			String point = "";
			String secondPoint = "";
			switch (siteCode) {
				case Define.PSITE_CODE_GMY:
					selector = "span.user_point";
					driver.get("http://dietnavi.com/pc");
					if (isExistEle(driver, selector)) {
						point = driver.findElement(By.cssSelector(selector)).getText();
						outPut = "[" + Define.PSITE_CODE_GMY + ":" + getNumber(point) + "]";
					}
					break;
				case Define.PSITE_CODE_GEN:
					selector = "li#user_point01>a>span";
					driver.get("http://www.gendama.jp/");
					if (isExistEle(driver, selector)) {
						point = driver.findElement(By.cssSelector(selector)).getText();
						outPut = "[" + Define.PSITE_CODE_GEN + ":" + getNumber(point) + "]";
					}
					break;
				case Define.PSITE_CODE_ECN:
					selector = "p.user_point_txt>strong";
					driver.get("https://ecnavi.jp/mypage/point_history/");
					if (isExistEle(driver, selector)) {
						point = driver.findElement(By.cssSelector(selector)).getText();
						outPut = "[" + Define.PSITE_CODE_ECN + ":" + getNumber(point) + "]";
					}
					break;
				case Define.PSITE_CODE_MOP:
					driver.get("http://pc.moppy.jp/bankbook/");
					selector = "div#point_blinking strong";
					if (isExistEle(driver, selector)) {
						point = driver.findElement(By.cssSelector(selector)).getText();
						outPut = "[" + Define.PSITE_CODE_MOP + ":" + getNumber(point);
					}
					selector = "div#point_blinking em";
					if (isExistEle(driver, selector)) {
						secondPoint = driver.findElement(By.cssSelector(selector)).getText();
						outPut += "." + getNumber(secondPoint) + "]";
					}
					break;
				case Define.PSITE_CODE_PEX:
					selector = "dd.user_pt.fw_b>span.fw_b";
					driver.get("https://pex.jp/user/point_passbook/all");
					// login!!
					LoginSite.login(Define.PSITE_CODE_PEX, driver, logg);
					if (isExistEle(driver, selector)) {
						point = driver.findElement(By.cssSelector(selector)).getText();
						outPut = "[" + Define.PSITE_CODE_PEX + ":" + getNumber(point) + "]";
					}
					break;
				case Define.PSITE_CODE_OSA:
					selector = "dl.bankbook-total>dd.current.coin>span";
					driver.get("https://osaifu.com/contents/bankbook/top/");
					if (isExistEle(driver, selector)) {
						point = driver.findElement(By.cssSelector(selector)).getText();
						outPut = "[" + Define.PSITE_CODE_OSA + ":" + getNumber(point);
					}
					selector = "dl.bankbook-total>dd.gold.coin>span";
					if (isExistEle(driver, selector)) {
						secondPoint = driver.findElement(By.cssSelector(selector)).getText();
						outPut += "." + getNumber(secondPoint) + "]";
					}
					break;
				case Define.PSITE_CODE_PTO:
					selector = "li.point>a>strong";
					driver.get("https://www.pointtown.com/ptu/index.do");
					// login!!
					LoginSite.login(Define.PSITE_CODE_PTO, driver, logg);
					if (isExistEle(driver, selector)) {
						point = driver.findElement(By.cssSelector(selector)).getText();
						outPut = "[" + Define.PSITE_CODE_PTO + ":" + getNumber(point) + "]";
					} else {
						// ログインができていない可能性がある
					}
					break;
				case Define.PSITE_CODE_I2I:
					selector = "td.ad_point>span.limited";
					driver.get("https://point.i2i.jp/account/");
					if (isExistEle(driver, selector)) {
						point = driver.findElement(By.cssSelector(selector)).getText();
						outPut = "[" + Define.PSITE_CODE_I2I + ":" + getNumber(point) + "]";
					}
					break;
				case Define.PSITE_CODE_PIL:
					// login!!
					LoginSite.login(Define.PSITE_CODE_PIL, driver, logg);
					selector = "table.memberinfo tr>td>strong";
					//					driver.get("http://www.point-island.com/");
					if (isExistEle(driver, selector)) {
						List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
						if (isExistEle(eleList, 1)) {
							point = eleList.get(1).getText();
							outPut = "[" + Define.PSITE_CODE_PIL + ":" + getNumber(point) + "]";
						}
					}
					break;
				default:
			}
			if (outPut.length() > 0) {
				sumTotal(siteCode, point);
				if (secondPoint.length() > 0) {
					sumTotal("secondPoint", secondPoint);
				}
				sb.append(outPut);
			} else {
				// 取得できなかった
				logg.warn("missed site:" + siteCode);
			}
		}
		logg.warn(total + sb.toString());
		driver.quit();
	}

	private static void sumTotal(String site, String points) {
		double current = Double.parseDouble(getNumber(points));
		switch (site) {
			case Define.PSITE_CODE_OSA:
			case Define.PSITE_CODE_MOP:
				total += current;
				break;
			case Define.PSITE_CODE_GMY:
			case Define.PSITE_CODE_PEX:
			case Define.PSITE_CODE_ECN:
			case Define.PSITE_CODE_I2I:
			case Define.PSITE_CODE_GEN:
			case Define.PSITE_CODE_PIL:
			case "secondPoint":
				total += current / 10;
				break;
			case Define.PSITE_CODE_PTO:
				total += current / 20;
				break;
			default:
				break;
		}
		total = (double) Math.round(total * 10) / 10;
		//		logg.warn("total["+total+"]円");
		//		logg.warn("current["+current+"]円");
		//		logg.warn("points["+points+"]円");
	}

	/**
	 *
	 * @param points
	 * @return
	 */
	private static String getNumber(String points) {
		String[] execlude = { ",", "Pt", " pt", "pt" };
		for (String s : execlude) {
			if (points.indexOf(s) > 0) {
				points = points.replaceAll(s, "");
				points = points.trim();
			}
		}
		return points;
	}
}
