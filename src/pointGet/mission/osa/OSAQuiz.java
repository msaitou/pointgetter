package pointGet.mission.osa;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 * 0時、8時、16時開催
 */
public class OSAQuiz extends Mission {
	final String url = "http://osaifu.com/contents/coinland/";

	/**
	 * @param logg
	 */
	public OSAQuiz(Logger logg) {
		super(logg);
		this.mName = "■daily quiz";
	}

	@Override
	public void roopMission(WebDriver driver) {

	}

	@Override
	public void privateMission(WebDriver driver) {
		selector = "li.long img[alt='デイリークイズ']";
		driver.get(url);
		Utille.sleep(2000);
		if (super.isExistEle(driver, selector)) {
			logg.info(this.mName + "]" + selector + " is EXIST");
			driver.findElement(By.cssSelector(selector)).click(); // 遷移
			Utille.sleep(2000);
			String overlaySelector = "div.overlay.overlay-timer>div.overlay-item[style*='display: block'] a.button-close";
			super.checkOverlay(driver, overlaySelector);
			selector = "input[name='submit']";
			Utille.sleep(4000);
			if (super.isExistEle(driver, selector)) {
				logg.info(this.mName + "]" + selector + " is EXIST");
				driver.findElement(By.cssSelector(selector)).click();
				for (int i = 0; i < 8; i++) {
					Utille.sleep(4000);
					selector = "ul.ui-item-body";
					if (super.isExistEle(driver, selector)) {
						logg.info(this.mName + "]" + selector + " is EXIST");
						int ran = Utille.getIntRand(4);
						String selectId = "label[for='radio-";
						if (ran == 0) {
							selectId += "1']";
						}
						else if (ran == 1) {
							selectId += "2']";
						}
						else if (ran == 2) {
							selectId += "3']";
						}
						else {
							selectId += "4']";
						}
						// 8kai roop
						String selector2 = "input[name='submit']";
						if (super.isExistEle(driver, selectId)) {
							logg.info(this.mName + "]" + selectId + " is EXIST");
							driver.findElement(By.cssSelector(selectId)).click(); // 選択
							Utille.sleep(2000);
							driver.findElement(By.cssSelector(selector2)).click(); // 遷移
							Utille.sleep(4000);
							super.checkOverlay(driver, overlaySelector);
							if (super.isExistEle(driver, selector2)) {
								logg.info(this.mName + "]" + selector2 + " is EXIST");
								driver.findElement(By.cssSelector(selector2)).click(); // 遷移
								Utille.sleep(3000);
								super.checkOverlay(driver, overlaySelector);
							}
						}
					}
				}
				logg.info(this.mName + "]kuria?");
				selector = "input[name='submit']";
				if (super.isExistEle(driver, selector)) {
					driver.findElement(By.cssSelector(selector)).click();
					Utille.sleep(3000);
				}
			}
		}
		else {
			logg.warn(this.mName + "]獲得済み");
		}
	}
}
