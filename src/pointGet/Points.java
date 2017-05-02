package pointGet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.db.PointsCollection;

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
		Map<String, Double> pMap = new HashMap<String, Double>();
		WebDriver driver = getWebDriver();
		try {
			for (String siteCode : pointSitelist) {
				String selector = "", outPut = "", point = "", secondPoint = "";
				switch (siteCode) {
					case Define.PSITE_CODE_GMY:
						selector = "span.user_point";
						driver.get("http://dietnavi.com/pc");
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_GMY + ":" + Utille.getNumber(point) + "]";
						}
						break;
					case Define.PSITE_CODE_GEN:
						selector = "li#user_point01>a>span";
						driver.get("http://www.gendama.jp/");
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_GEN + ":" + Utille.getNumber(point) + "]";
						}
						break;
					case Define.PSITE_CODE_ECN:
						selector = "p.user_point_txt>strong";
						driver.get("https://ecnavi.jp/mypage/point_history/");
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_ECN + ":" + Utille.getNumber(point) + "]";
						}
						break;
					case Define.PSITE_CODE_MOP:
						driver.get("http://pc.moppy.jp/");
						selector = "div#preface>ul.pre__login__inner";
						if (!isExistEle(driver, selector)) {
							// login!!
							LoginSite.login(Define.PSITE_CODE_MOP, driver, logg);
						}
						selector = "div#point_blinking strong";
						driver.get("http://pc.moppy.jp/bankbook/");
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_MOP + ":" + Utille.getNumber(point);
						}
						selector = "div#point_blinking em";
						if (isExistEle(driver, selector)) {
							secondPoint = driver.findElement(By.cssSelector(selector)).getText();
							outPut += "." + Utille.getNumber(secondPoint) + "]";
						}
						break;
					case Define.PSITE_CODE_PEX:
						selector = "dd.user_pt.fw_b>span.fw_b";
						driver.get("https://pex.jp/user/point_passbook/all");
						if (!isExistEle(driver, selector)) {
							// login!!
							LoginSite.login(Define.PSITE_CODE_PEX, driver, logg);
						}
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_PEX + ":" + Utille.getNumber(point) + "]";
						}
						break;
					case Define.PSITE_CODE_OSA:
						selector = "ul.userinfo";
						driver.get("http://osaifu.com/");
						if (!isExistEle(driver, selector)) {
							// login!!
							LoginSite.login(Define.PSITE_CODE_OSA, driver, logg);
						}
						selector = "dl.bankbook-total>dd.current.coin>span";
						driver.get("https://osaifu.com/contents/bankbook/top/");
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_OSA + ":" + Utille.getNumber(point);
						}
						selector = "dl.bankbook-total>dd.gold.coin>span";
						if (isExistEle(driver, selector)) {
							secondPoint = driver.findElement(By.cssSelector(selector)).getText();
							outPut += "." + Utille.getNumber(secondPoint) + "]";
						}
						break;
					case Define.PSITE_CODE_PTO:
						selector = "li.point>a>strong";
						driver.get("https://www.pointtown.com/ptu/index.do");
						if (!isExistEle(driver, selector)) {
							// login!!
							LoginSite.login(Define.PSITE_CODE_PTO, driver, logg);
						}
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_PTO + ":" + Utille.getNumber(point) + "]";
						} else {
							// ログインができていない可能性がある
						}
						break;
					case Define.PSITE_CODE_I2I:
						selector = "td.ad_point>span.limited";
						driver.get("https://point.i2i.jp/account/");
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_I2I + ":" + Utille.getNumber(point) + "]";
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
								outPut = "[" + Define.PSITE_CODE_PIL + ":" + Utille.getNumber(point) + "]";
							}
						}
						break;
					case Define.PSITE_CODE_PIC:
						selector = "p.text.point";
						driver.get("http://pointi.jp/my/my_page.php"); // http://pointi.jp/
						if (!isExistEle(driver, selector)) {
							// login!!
							LoginSite.login(Define.PSITE_CODE_PIC, driver, logg);
							driver.get("http://pointi.jp/my/my_page.php"); // http://pointi.jp/
						}
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_PIC + ":" + Utille.getNumber(point) + "]";
						}
						break;
					case Define.PSITE_CODE_HAP:
						// login!!
						//					LoginSite.login(Define.PSITE_CODE_PIC, driver, logg);
						selector = "a.usernavi-point";
						driver.get("http://hapitas.jp/");
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_HAP + ":" + Utille.getNumber(point) + "]";
						}
						break;
					case Define.PSITE_CODE_MOB:
						// login!!
						//					LoginSite.login(Define.PSITE_CODE_PIC, driver, logg);
						selector = "div.bankbook_panel__point>em";
						driver.get("http://pc.mtoku.jp/mypage/bankbook/");
						if (isExistEle(driver, selector)) {
							List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
							if (isExistEle(eleList, 0)) {
								point = eleList.get(0).getText();
								outPut = "[" + Define.PSITE_CODE_MOB + ":" + Utille.getNumber(point) + "]";
							}
						}
						break;
					case Define.PSITE_CODE_CRI:
						// login!!
						//					LoginSite.login(Define.PSITE_CODE_PIC, driver, logg);
						selector = "li.p_menu.point>a";
						driver.get("http://www.chobirich.com/");
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_CRI + ":" + Utille.getNumber(point) + "]";
						}
						break;
					case Define.PSITE_CODE_PNY:
						// login!!
						//					LoginSite.login(Define.PSITE_CODE_PIC, driver, logg);
						selector = "p#user_get_point>em";
						driver.get("https://www.poney.jp/");
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_PNY + ":" + Utille.getNumber(point) + "]";
						}
						break;
					case Define.PSITE_CODE_SUG:
						// login!!
						//					LoginSite.login(Define.PSITE_CODE_PIC, driver, logg);
						selector = "span#user-mile-status-earn";
						driver.get("http://www.sugutama.jp/passbook");
						Utille.sleep(5000);
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_SUG + ":" + Utille.getNumber(point) + "]";
						}
						break;
					case Define.PSITE_CODE_WAR:
						selector = "span.item>span.PT3.marginRight5";
						driver.get("http://www.warau.jp/");
						if (!isExistEle(driver, selector)) {
							// login!!
							LoginSite.login(Define.PSITE_CODE_WAR, driver, logg);
						}
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_WAR + ":" + Utille.getNumber(point) + "]";
						}

						break;
					case Define.PSITE_CODE_CIT:
						selector = "li.user>a>span.user_pt";
						driver.get("http://www.chance.com/");
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_CIT + ":" + Utille.getNumber(point) + "]";
						}
						break;
					case Define.PSITE_CODE_PMO:
						selector = "span#ygps>span.pointEmphasis";
						driver.get("http://poimon.jp/");
						if (!isExistEle(driver, selector)) {
							// login!!
							LoginSite.login(Define.PSITE_CODE_PMO, driver, logg);
						}
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_PMO + ":" + Utille.getNumber(point) + "]";
						}
						break;
					// pointstadium
					case Define.PSITE_CODE_PST:
						selector = "div.login>p.point>strong";
						driver.get("http://www.point-stadium.com/");
						if (isExistEle(driver, selector)) {
							point = driver.findElement(By.cssSelector(selector)).getText();
							outPut = "[" + Define.PSITE_CODE_PST + ":" + Utille.getNumber(point) + "]";
						}
						break;
					default:
				}
				if (outPut.length() > 0) {
					total = Utille.sumTotal(siteCode, point, total);
					Double siteTotal = getSiteTotal(siteCode, point, 0.0);
					if (secondPoint.length() > 0) {
						total = Utille.sumTotal("secondPoint", secondPoint, total);
						siteTotal = getSiteTotal(siteCode, point, siteTotal);
					}
					sb.append(outPut);
					pMap.put(siteCode, siteTotal);
				} else {
					// 取得できなかった
					logg.warn("missed site:" + siteCode);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
		//		pMap.put("mop", 582.9);
		//		pMap.put("osa", 183.0);
		PointsCollection PC = new PointsCollection(Dbase);
		if (!pMap.isEmpty()) {
			PC.putPointsData(pMap);
		}
		PC.putAchievementData();
		logg.warn(total + sb.toString());
	}

	private static Double getSiteTotal(String site, String points, Double siteTotal) {
		double current = Double.parseDouble(Utille.getNumber(points));
		switch (site) {
			case Define.PSITE_CODE_OSA:
			case Define.PSITE_CODE_MOP:
			case Define.PSITE_CODE_PMO:
			case Define.PSITE_CODE_HAP:
				siteTotal += current;
				break;
			case Define.PSITE_CODE_CRI:
			case Define.PSITE_CODE_SUG:
				siteTotal += current / 2;
				break;
			case Define.PSITE_CODE_GMY:
			case Define.PSITE_CODE_PEX:
			case Define.PSITE_CODE_ECN:
			case Define.PSITE_CODE_I2I:
			case Define.PSITE_CODE_GEN:
			case Define.PSITE_CODE_PIL:
			case Define.PSITE_CODE_PIC:
			case Define.PSITE_CODE_MOB:
			case Define.PSITE_CODE_WAR:
			case Define.PSITE_CODE_CIT:
			case Define.PSITE_CODE_PST:
			case "secondPoint":
				siteTotal += current / 10;
				break;
			case Define.PSITE_CODE_PTO:
				siteTotal += current / 20;
				break;
			case Define.PSITE_CODE_PNY:
				siteTotal += current / 100;
				break;
			default:
				break;
		}
		return (double) Math.round(siteTotal * 10) / 10;
	}
}
