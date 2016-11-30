package pointGet.mission.ecn;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class ECNNews extends Mission {
	final String url = "http://ecnavi.jp/mainichi_news/";

	/**
	 * @param log
	 */
	public ECNNews(Logger log) {
		super(log);
		this.mName = "■毎日ニュース";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		int ecnNewsClickNorma = 5;
		int ecnNewsClick = 0;
		for (int i = 0; ecnNewsClick < ecnNewsClickNorma; i++) {
			driver.get(this.url);
			selector = "div.article_list li>a";
			if (!isExistEle(driver, selector)) {
				break;
			}
			List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
			eleList.get(i).click();
			Utille.sleep(3000);
			int ran = Utille.getIntRand(4);
			String selector1 = "div.feeling_buttons button";
			if (ran == 0) {
				selector1 += ".btn_feeling_good";
			} else if (ran == 1) {
				selector1 += ".btn_feeling_bad";
			} else if (ran == 2) {
				selector1 += ".btn_feeling_sad";
			} else if (ran == 3) {
				selector1 += ".btn_feeling_glad";
			}
			if (isExistEle(driver, selector1)) {
				clickSleepSelector(driver, selector1, 3000);
				ecnNewsClick++;
			} else if (isExistEle(driver, "p.got_maxpoints")) {
				logg.warn(mName + "]獲得済み");
				break;
			}
		}
	}
}
