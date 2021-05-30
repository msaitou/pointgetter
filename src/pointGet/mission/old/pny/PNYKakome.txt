package pointGet.mission.pny;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;

/**
 *
 * @author saitou
 */
public class PNYKakome extends PNYBase {
	final String url = "http://www.poney.jp/";

	/**
	 * @param logg
	 */
	public PNYKakome(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "囲めぶた");
	}

	@Override
	public void privateMission(WebDriver driver) {
		Utille.url(driver, url, logg);
		selector = "span#catch_the_pig";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 5000);// 囲めスタート前
			changeCloseWindow(driver);
			Utille.sleep(4000);
			checkOverlay(driver, "div#game_filter[style*='top: 3000px']>div#popup_bnr>a#closs_btm>img");
			// finish condition
			String finishSelector = "span.play";
			if (isExistEle(driver, finishSelector)) {
				finsishFlag = true;
				return;
			}
			selector = "form>a.ok_btn";
			for (int i = 0; i < 2; i++) {
				if (isExistEle(driver, selector)) {
					// 1:スタート
					// 2:障害物の位置確定スタート
					clickSelector(driver, selector);
				}
			}
			// ここから追い詰める

			// 直前の豚の位置
			String prePutaPosi = "";

			// 豚の位置を求める？
			String butaSele = "img#character";
			String butaPosi = getPosi(driver, butaSele);
			String putedSele = "a.active";
			String putedPosi = getPosi(driver, putedSele);

			if (isExistEle(driver, selector)) {
				clickSelector(driver, selector);
				for (int i = 0; i < 8; i++) {
					Utille.sleep(4000);
					selector = "ul.ui-item-body";
					if (isExistEle(driver, selector)) {
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
						if (isExistEle(driver, selectId)) {
							clickSleepSelector(driver, selectId, 4000); // 遷移
							int ranSleep = Utille.getIntRand(9);
							Utille.sleep(ranSleep * 1000);
							waitTilReady(driver);
							if (isExistEle(driver, selector2)) {
								waitTilReady(driver);
								clickSleepSelector(driver, selector2, 4000); // 遷移
								checkOverlay(driver, "div.overlay-popup a.button-close");
								if (isExistEle(driver, selector2)) {
									clickSleepSelector(driver, selector2, 3000); // 遷移
									checkOverlay(driver, "div.overlay-popup a.button-close");
								}
							}
						}
					}
				}
				logg.info(this.mName + "]kuria?");
				selector = "input[name='submit']";
				if (isExistEle(driver, selector)) {
					clickSleepSelector(driver, selector, 3000);
					waitTilReady(driver);
				}
			}
			else {
				logg.warn(this.mName + "]獲得済み");
			}
		}
	}

	public String getPosi(WebDriver driver, String sele) {
		WebElement myElement = driver.findElement(By.cssSelector(sele));
		WebElement parent = myElement.findElement(By.xpath(".."));
		return parent.getAttribute("class");
	}

}
