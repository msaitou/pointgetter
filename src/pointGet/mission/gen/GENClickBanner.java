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
		String selecter[] = { "div#forestBox a img", "div#downBox a.all p.bnrImg", "section#reach a.clearfix dt"
				//					, "div#sponsor a[href^='/cl/?id='] img"  // ここにバッチ起動時のみバグってる
		};
		for (int j = 0; j < selecter.length; j++) {
			String selector = selecter[j];
			if (super.isExistEle(driver, selector)) {
				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
				int size = eleList.size();
				for (int i = 0; i < size; i++) {
					if (super.isExistEle(eleList)) {
						eleList.get(i).click();
						Utille.sleep(2000);
					}
				}
			}
		}
		logg.info("selector: end");
	}
}
