package pointGet.mission.i2i;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.val;
import pointGet.Utille;

/**
 * @author saitou
 *
 */
public class I2ISeiza extends I2IBase {
	final String url = "https://point.i2i.jp/special/freepoint/";

	/**
	 * @param logg
	 */
	public I2ISeiza(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "星座");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "li.pointfreeList_item";
		if (isExistEle(driver, selector)) {
			int size = getSelectorSize(driver, selector);
			for (int i = 0; i < size; i++) {
				WebElement e = driver.findElements(By.cssSelector(selector)).get(i);
				String selector2 = "img[src='/img/special/freepoint/horosco_340_120.png']";
				if (isExistEle(e, selector2)) {
					if (!isExistEle(e, selector2)) {
						break;
					}
					clickSleepSelector(e, selector2, 5000);
//					changeCloseWindow(driver);
					// アラートをけして
					val alert = driver.switchTo().alert();
					alert.accept();
					Utille.sleep(10000);
					changeCloseWindow(driver);

					selector = "div#parts-slide-button__action a>img"; // 占い始める　全体へ
					String selector1 = "section>div>form>input[type=image]";
					String selectList[] = {selector, selector1};
					for (int g = 0; g< selectList.length;g++) {
						if (isExistEle(driver, selectList[g])) {
							clickSleepSelector(driver, selectList[g], 3000); // 遷移　全体へ

							String nextSelector = "div#next-button a>img";
							String symbleSelector = "div#symbols-next-button a>img";
							boolean endFlag = false;
							for (int j = 0; j < 20; j++) { // 20に特に意味なし　エンドレスループを避けるため
								// overlayを消して
								if (!isExistEle(driver, "div#inter-ad[style*='display: none'] div#inter-ad-close")) {
									if (isExistEle(driver, "div#inter-ad div#inter-ad-close")) {
										checkOverlay(driver, "div#inter-ad div#inter-ad-close", false);
										endFlag = true;
									}
								}

								if (isExistEle(driver, nextSelector)) {
									clickSleepSelector(driver, nextSelector, 3000); // 遷移
								}
								else if (isExistEle(driver, selector)) {
									clickSleepSelector(driver, selector, 3000); // 遷移
								}
								else if (isExistEle(driver, symbleSelector)) {
									clickSleepSelector(driver, symbleSelector, 3000); // 遷移
								}
								else if (isExistEle(driver, selector1)) {
									clickSleepSelector(driver, selector1, 3000); // 遷移
								}
								if (endFlag) {
									break;
								}
							}
						}
					}
					break; // 星座はこれで終了
				}
			}
		}
	}
}
