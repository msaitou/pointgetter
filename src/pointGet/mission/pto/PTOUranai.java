package pointGet.mission.pto;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Define;
import pointGet.LoginSite;
import pointGet.Utille;
import pointGet.mission.Mission;

public class PTOUranai extends Mission {
	final String url = "http://www.pointtown.com/";

	/**
	 * @param logg
	 */
	public PTOUranai(Logger logg, Map<String, String> cProps) {
		super(logg, cProps);
		this.mName = "■PTO星座";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		LoginSite.login(Define.PSITE_CODE_PTO, driver, logg);
		selector = "a.panel__bnr-wrap>img.bnr";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 6000); // 遷移
			driver.switchTo().frame(0);
			Utille.sleep(3000);
			String uranaiSelector = "a>img[alt='uranai']";
			if (isExistEle(driver, uranaiSelector)) {
				clickSleepSelector(driver, uranaiSelector, 3000); // 遷移 全体へ
				changeWindow(driver);
				Utille.sleep(4000);
				selector = "div#parts-slide-button__action a>img"; // 占い始める
				String selector1 = "section>div>form>input[type=image]";
				String selectList[] = { selector, selector1 };
				for (int g = 0; g < selectList.length; g++) {
					if (isExistEle(driver, selectList[g])) {
						clickSleepSelector(driver, selectList[g], 3000); // 遷移
						String nextSelector = "div#next-button a>img";
						String symbleSelector = "div#symbols-next-button a>img";
						boolean endFlag = false;
						for (int j = 0; j < 20; j++) { // 20に特に意味なし
														// エンドレスループを避けるため
							// overlayを消して
							if (!isExistEle(driver, "div#inter-ad[style*='display: none'] div#inter-ad-close")) {
								Utille.sleep(3000);
								if (isExistEle(driver, "div#inter-ad div#inter-ad-close")) {
									checkOverlay(driver, "div#inter-ad div#inter-ad-close", false);
									endFlag = true;
								}
							}

							if (isExistEle(driver, nextSelector)) {
								clickSleepSelector(driver, nextSelector, 3000); // 遷移
							} else if (isExistEle(driver, selector)) {
								clickSleepSelector(driver, selector, 3000); // 遷移
							} else if (isExistEle(driver, symbleSelector)) {
								clickSleepSelector(driver, symbleSelector, 3000); // 遷移
							} else if (isExistEle(driver, selector1)) {
								clickSleepSelector(driver, selector1, 3000); // 遷移
							}
							Utille.sleep(3000);
							if (endFlag) {
								break;
							}
						}
					}
				}
			}
		}
	}
}