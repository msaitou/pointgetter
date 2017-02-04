package pointGet.mission.gmy;

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
public class GMYPriceChyosatai extends Mission {
	final String url = "http://dietnavi.com/pc/";

	/**
	 * @param log
	 */
	public GMYPriceChyosatai(Logger log, Map<String, String> cProps) {
		super(log, cProps);
		this.mName = "■GMYPrice調査隊";
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
			selector = "ul a[href='http://dietnavi.com/pc/game/price/play.php']";
			if (isExistEle(driver, selector)) {
				driver.get("http://dietnavi.com/pc/game/price/play.php");
				Utille.sleep(2000);
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
						for (int i = 0; i < 5; i++) {
							int ran = Utille.getIntRand(2);
							if (isExistEle(driver.findElements(By.cssSelector(selector)), ran)) {
								clickSleepSelector(driver.findElements(By.cssSelector(selector)), ran, 3500);
								String sele2 ="div#popup div.btn.mrg-t5.mrg-b5";
								if (isExistEle(driver, sele2)) {
									clickSleepSelector(driver, sele2, 3000);
									checkOverlay(driver, overlaySelector);
									if (isExistEle(driver, footBnrSelector)
											&& !isExistEle(driver, "div.foot-bnr[style*='display :none'] a.close>span")) {
										checkOverlay(driver, footBnrSelector);
									}
									if (isExistEle(driver, selector)) {
										clickSleepSelector(driver, selector, 3000);
									}
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
					for (int i = 0; i < 5; i++) {
						String finshSele = "div.finish-area";
						// otukare!
						if (isExistEle(driver.findElements(By.cssSelector(finshSele)))) {
							break;
						}
						int ran = Utille.getIntRand(2);
						if (isExistEle(driver.findElements(By.cssSelector(selector)), ran)) {
							clickSleepSelector(driver.findElements(By.cssSelector(selector)), ran, 3500);
							String sele2 ="div#popup div.btn.mrg-t5.mrg-b5";
							if (isExistEle(driver, sele2)) {
								clickSleepSelector(driver, sele2, 3000);
								checkOverlay(driver, overlaySelector);
								if (isExistEle(driver, footBnrSelector)
										&& !isExistEle(driver, "div.foot-bnr[style*='display :none'] a.close>span")) {
									checkOverlay(driver, footBnrSelector);
								}
								if (isExistEle(driver, selector)) {
									clickSleepSelector(driver, selector, 3000);
								}
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