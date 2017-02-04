package pointGet.mission.pto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Define;
import pointGet.LoginSite;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class PTOKuji extends Mission {
	final String url = "";
	private static HashMap<String, HashMap<String, String>> clickMap = new HashMap<String, HashMap<String, String>>() {
		{
			put("くじ赤", new HashMap<String, String>() {
				{
					put("url", "http://www.pointtown.com/ptu/shopping/top.do	");
					put("sele", "a#red_kuji>img");
				}
			});
			put("くじ黄", new HashMap<String, String>() {
				{
					put("url", "http://www.pointtown.com/ptu/shopping/top.do");
					put("sele", "a#yellow_kuji");
					// div.ptpc-search-box__hotword>a ０番目
				}
			});
			put("くじ紫", new HashMap<String, String>() {
				{
					put("url", "http://www.pointtown.com/ptu/pointpark/comparison/creditcardsearch.do");
					put("sele", "a>img[alt='三角くじ紫']");
				}
			});
			put("くじ桃", new HashMap<String, String>() {
				{
					put("url", "http://www.pointtown.com/ptu/coupon/top.do");
					put("sele", "a>img[alt='三角くじ桃']");
				}
			});
			put("くじ青", new HashMap<String, String>() {
				{
					put("url", "http://www.pointtown.com/ptu/poitto/top.do");
					put("sele", "a#blue_kuji>img");
				}
			});
			put("うさくじ", new HashMap<String, String>() {
				{
					put("url", "http://www.pointtown.com/ptu/odekake/top.do");
					put("sele", "a#red_kuji>img");
				}
			});
		}
	};

	/**
	 * @param log
	 */
	public PTOKuji(Logger log, Map<String, String> cProps) {
		super(log, cProps);
		this.mName = "■PTOくじ";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		LoginSite.login(Define.PSITE_CODE_PTO, driver, logg);
		// 1．ログイン直後のTOP画面で
		int c = 0;
		for (Map.Entry<String, HashMap<String, String>> clMap : clickMap.entrySet()) {
			driver.get(clMap.getValue().get("url"));
			boolean existFlag = false;
			String sele = "";
			switch (clMap.getKey()) {
				case "うさくじ":
				case "くじ紫":
				case "くじ赤":
					if (isExistEle(driver, clMap.getValue().get("sele"))) {
						logg.info(mName + " " + ++c + "." + clMap.getKey() + "!");
						clickSleepSelector(driver, clMap.getValue().get("sele"), 4000);
						existFlag = true;
					}
					break;
				case "くじ黄":
				case "くじ桃":
					if ("くじ桃".equals(clMap.getKey())) {
						sele = "li.img>img";
					} else if ("くじ黄".equals(clMap.getKey())) {
						sele = "div.ptpc-search-box__hotword>a";
					}
					if (isExistEle(driver, sele)) {
						List<WebElement> eleList = driver.findElements(By.cssSelector(sele));
						for (int i = 0; i < 2; i++) {
							if (isExistEle(eleList, i)) { // 最初のリンクをクリック
								logg.info(mName + " " + ++c + "." + clMap.getKey() + "pre1!");
								clickSleepSelector(eleList, i, 4000);
								closeOtherWindow(driver);
								if (isExistEle(driver, clMap.getValue().get("sele"))) {
									logg.info(mName + " " + c + "." + clMap.getKey() + "!");
									clickSleepSelector(driver, clMap.getValue().get("sele"), 4000);
									existFlag = true;
									if ("くじ桃".equals(clMap.getKey())) {
										break;
									}
								}
							}
						}
					}
					break;
				case "くじ青":
					sele = "ul.list li.tit_topic strong>a";
					if (isExistEle(driver, sele)) {
						List<WebElement> eleList = driver.findElements(By.cssSelector(sele));
						if (isExistEle(eleList, 0)) { // 最初のリンクをクリック
							logg.info(mName + " " + ++c + "." + clMap.getKey() + "pre1!");
							clickSleepSelector(eleList, 0, 4000);
							if (isExistEle(driver, clMap.getValue().get("sele"))) {
								logg.info(mName + " " + c + "." + clMap.getKey() + "!");
								clickSleepSelector(driver, clMap.getValue().get("sele"), 5000);
								existFlag = true;
							}
						}
					}
					break;
				default:
			}
			if (existFlag) {
				String sele2 = "a#clickDailyBanner>img"; //2つ目
				if (isExistEle(driver, sele2)) {
					logg.info(mName + " " + c + " click!!");
					clickSleepSelector(driver, sele2, 3000);
					closeOtherWindow(driver);
					String wKuji = "div#wchance img";
					if (isExistEle(driver, wKuji)) {
						clickSleepSelector(driver, wKuji, 3000);
						closeOtherWindow(driver);
					}
				}
			}
		}
	}
}
