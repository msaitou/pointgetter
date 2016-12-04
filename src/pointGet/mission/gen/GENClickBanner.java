package pointGet.mission.gen;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou 途中TODO
 */
public class GENClickBanner extends Mission {
	final String url = "http://www.gendama.jp/forest/";

	/**
	 * @param log
	 */
	public GENClickBanner(Logger log) {
		super(log);
		this.mName = "■ポイントの森(クリック)";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		Utille.sleep(2000);
		//		String selecter[] = { "div#forestBox a img", "div#downBox a.all p.bnrImg", "section#reach a.clearfix dt"
		//									, "div#sponsor a[href*='/cl/?id=']"  // ここにバッチ起動時のみバグってる
		//		};
		//		for (int j = 0; j < selecter.length; j++) {
		//			logg.info("selector: start");
		//			String selector = selecter[j];
		//			if (isExistEle(driver, selector)) {
		//				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
		//				int size = eleList.size();
		//				for (int i = 0; i < size; i++) {
		//					if (isExistEle(eleList, i)) {
		//						clickSleepSelector(eleList, i, 2000);
		//					}
		//				}
		//			}
		//			logg.info("selector: end");
		//		}

		String selecter[] = { "a.itx-listitem-link div",
				"img[src='http://img.gendama.jp/img/forest/bt_day1.gif']",
				"img[src='//img.gendama.jp/img/neo/index/click_pt.png']",
				"img[src='http://img.gendama.jp/img/forest/forest_bt1.gif']",
				"img[src='//img.gendama.jp/img/renew/common/btn_detail.png']",
				"img[src*='//img.gendama.jp/img/renew/common/btn_detail_daily.gif']" };
		for (int j = 0; j < selecter.length; j++) {
			logg.info("selector: start");
			String selector = selecter[j];
			if (isExistEle(driver, selector)) {
				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
				int size = eleList.size();
				for (int i = 0; i < size; i++) {
					if (isExistEle(eleList, i)) {
						clickSleepSelector(eleList, i, 2000);
					}
				}
			}
			String wid = driver.getWindowHandle();
			java.util.Set<String> widSet = driver.getWindowHandles();
			for (String id : widSet) {
				if (!id.equals(wid)) {
					//最後に格納したウインドウIDにスイッチ  
					driver.switchTo().window(id);
					driver.close();
				}
			}
			//最後に格納したウインドウIDにスイッチ  
			driver.switchTo().window(wid);

			logg.info("selector: end");
		}
	}
}
