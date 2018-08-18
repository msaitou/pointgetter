package pointGet.mission.cri;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;

public class CRIClickBananer extends CRIBase {
	final String url = "http://www.chobirich.com/game/";
	private static HashMap<String, HashMap<String, String>> clickMap = new HashMap<String, HashMap<String, String>>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("topページ", new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("url", "http://www.chobirich.com");
					put("sele", "div.point_1get_bnr>a>img");
				}
			});
			put("スタンプ", new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("url", "http://www.chobirich.com/earn#clickstamp_box");
					put("sele", "div.clickstamp_list li>a>img");
				}
			});
		}
	};

	/**
	 * @param logg
	 */
	public CRIClickBananer(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "クリックバナー");
	}

	@Override
	public void privateMission(WebDriver driver) {
		//		Utille.url(driver, url, logg);
		int c = 0;
		for (Map.Entry<String, HashMap<String, String>> clMap : clickMap.entrySet()) {
			Utille.url(driver, clMap.getValue().get("url"), logg);
			switch (clMap.getKey()) {
				case "topページ":
					if (isExistEle(driver, clMap.getValue().get("sele"))) {
						logg.info(mName + " " + clMap.getKey() + "!");
						clickSleepSelector(driver, clMap.getValue().get("sele"), 2000);
						closeOtherWindow(driver);
					}
					break;
				case "スタンプ":
					if (isExistEle(driver, clMap.getValue().get("sele"))) {
						List<WebElement> eleList = driver.findElements(By.cssSelector(clMap.getValue().get("sele")));
						int stampNum = eleList.size();
						for (int i = 0; i < stampNum; i++) {
							if (isExistEle(eleList, i)) {
								logg.info(mName + " " + i + "." + clMap.getKey() + "!");
								clickSleepSelector(driver, eleList, i, 9000);
								closeOtherWindow(driver);
							}
						}
					}
					break;
				default:
			}
		}
	}
}
