package pointGet.mission.mop;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 * 4時更新
 */
public class MOPChyosatai extends Mission {
	final String url = "http://pc.moppy.jp/gamecontents/";

	/**
	 * @param log
	 */
	public MOPChyosatai(Logger log) {
		super(log);
		this.mName = "■トキメキ調査隊";
	}

	@Override
	public void roopMission(WebDriver driver) {

	}

	@Override
	public void privateMission(WebDriver driver) {
		for (int j = 0; j < 3; j++) {
			driver.get(url);
			selector = "div.game_btn>div.icon>img[alt='トキメキ調査隊']";
			if (super.isExistEle(driver, selector)) {
				driver.findElement(By.cssSelector(selector)).click(); // 遷移
				Utille.sleep(4000);

				super.changeWindow(driver);
				super.checkOverlay(driver, "div#popup a.modal_close");

				selector = "div.thumbnail span.icon-noentry";
				if (super.isExistEle(driver, selector)) {
					List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
					eleList.get(0).click(); // 遷移
					Utille.sleep(3000);

					selector = "div.thumb-start div.btn>a";
					if (super.isExistEle(driver, selector)) {
						driver.findElement(By.cssSelector(selector)).click(); // 遷移
						Utille.sleep(4000);
						for (int i = 0; i < 10; i++) {
							int ran = Utille.getIntRand(2);
							selector = "span.icon-arrow";
							if (super.isExistEle(driver, selector)) {
								super.checkOverlay(driver, "div#popup a.modal_close");
								driver.findElements(By.cssSelector(selector)).get(ran).click();
								Utille.sleep(3000);
							}
						}
					}
				}
				else {
					j = 3;
				}
			}
		}
	}
}
