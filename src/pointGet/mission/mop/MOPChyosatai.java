package pointGet.mission.mop;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
	public MOPChyosatai(Logger log, Map<String, String> cProps) {
		super(log, cProps);
		this.mName = "■トキメキ調査隊";
	}

	@Override
	public void roopMission(WebDriver driver) {

	}

	@Override
	public void privateMission(WebDriver driver) {
		String overlaySelector = "div#popup[style*='display: block'] a.modal_close";
		String footBnrSelector = "div.foot-bnr a.close>span";
		for (int j = 0; j < 6; j++) {
			driver.get(url);
			selector = "div.game_btn>div.icon>img[alt='トキメキ調査隊']";
			if (isExistEle(driver, selector)) {
				clickSleepSelector(driver, selector, 4000); // 遷移

				changeWindow(driver);
				checkOverlay(driver, overlaySelector);
				if (isExistEle(driver, footBnrSelector)
						&& !isExistEle(driver, "div.foot-bnr[style*='display :none'] a.close>span")) {
					checkOverlay(driver, footBnrSelector);
				}

				String noEntrySele = "div.thumbnail span.icon-noentry";
				String entrySele = "div.thumbnail span.icon-entry";
				if (isExistEle(driver, noEntrySele)) {
					clickSleepSelector(driver, noEntrySele, 3000); // 遷移
					checkOverlay(driver, overlaySelector);
					if (isExistEle(driver, footBnrSelector)
							&& !isExistEle(driver, "div.foot-bnr[style*='display :none'] a.close>span")) {
						checkOverlay(driver, footBnrSelector);
					}

					selector = "div.thumb-start div.btn>a";
					if (isExistEle(driver, selector)) {
						clickSleepSelector(driver, selector, 4000); // 遷移
						checkOverlay(driver, overlaySelector);
						checkOverlay(driver, footBnrSelector);
						String finshSele = "div.finish-area";
						// otukare!
						if (isExistEle(driver.findElements(By.cssSelector(finshSele)))) {
							break;
						}
						selector = "span.icon-arrow";
						for (int i = 0; i < 10; i++) {
							int ran = Utille.getIntRand(2);
							if (isExistEle(driver.findElements(By.cssSelector(selector)), ran)) {
								clickSleepSelector(driver.findElements(By.cssSelector(selector)), ran, 3000);
								checkOverlay(driver, overlaySelector);
								if (isExistEle(driver, footBnrSelector)
										&& !isExistEle(driver, "div.foot-bnr[style*='display :none'] a.close>span")) {
									checkOverlay(driver, footBnrSelector);
								}
							}
						}
					}
				}
				else if (isExistEle(driver, entrySele)) {
					clickSleepSelector(driver, entrySele, 3000); // 遷移
					checkOverlay(driver, overlaySelector);
					if (isExistEle(driver, footBnrSelector)
							&& !isExistEle(driver, "div.foot-bnr[style*='display :none'] a.close>span")) {
						checkOverlay(driver, footBnrSelector);
					}
//					selector = "div.btn>a";
					selector = "span.icon-arrow";
					for (int i = 0; i < 10; i++) {
						String finshSele = "div.finish-area";
						// otukare!
						if (isExistEle(driver.findElements(By.cssSelector(finshSele)))) {
							break;
						}
						int ran = Utille.getIntRand(2);
						Utille.sleep(2000);
						if (isExistEle(driver.findElements(By.cssSelector(selector)))
								&& isExistEle(driver.findElements(By.cssSelector(selector)), ran)) {
							clickSleepSelector(driver.findElements(By.cssSelector(selector)), ran, 3000);
							checkOverlay(driver, overlaySelector);
							if (isExistEle(driver, footBnrSelector)
									&& !isExistEle(driver, "div.foot-bnr[style*='display :none'] a.close>span")) {
								checkOverlay(driver, footBnrSelector);
							}
						}
					}
				}
				else {
					j = 6;
				}
			}
		}
	}
}
