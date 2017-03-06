package pointGet.mission.mop;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.Utille;

public class MOPManga extends MOPBase {
	final String url = "http://pc.moppy.jp/gamecontents/";

	/**
	 * @param logg
	 */
	public MOPManga(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "漫画");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "div.game_btn>div.icon>img[alt='CMくじ']";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 6000); // 遷移
			changeCloseWindow(driver);
			Utille.sleep(3000);
			String uranaiSelector = "a>img[alt='up_current']";
			if (isExistEle(driver, uranaiSelector)) {
				clickSleepSelector(driver, uranaiSelector, 3000); // 遷移 全体へ
				changeCloseWindow(driver);
				// // アラートをけして
				// val alert = driver.switchTo().alert();
				// alert.accept();
				Utille.sleep(4000);
				// changeCloseWindow(driver);

				selector = "a.ui-btn.ui-btn-a"; // 回答する
				while (true) {
					if (isExistEle(driver, selector)) {
						List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
						if (isExistEle(eleList, 0)) {
							clickSleepSelector(eleList, 0, 3000); // 遷移
							String overLay = "div#interstitial[style*='display: block']>div>div#inter-close";
							String seleNext = "form>input[type='image']";
							for (int g = 0; g < 9; g++) {
								if (isExistEle(driver, seleNext)) {
									if (isExistEle(driver, overLay)) {
										checkOverlay(driver, overLay);
									}
									clickSleepSelector(driver, seleNext, 3000); // 遷移
								}
							}
							Utille.sleep(3000);
							String choiceSele = "input[type='radio']";
							String seleNext2 = "div>input.enquete_nextbt";
							String seleNext3 = "div>input.enquete_nextbt_2";
							String seleSele = "form.menu>select";
							// 8問
							for (int k = 1; k <= 8; k++) {
								if (isExistEle(driver, overLay)) {
									checkOverlay(driver, overLay);
								}
								int choiceNum = 0;
								if (isExistEle(driver, choiceSele)) {
									int choiceies = getSelectorSize(driver, choiceSele);
									switch (k) {
									case 1:
										// 1問目は1：男
										break;
									case 2:
									case 3:
										// 2問目は3：30代
										// 3問目は3：会社員
										if (choiceies > 2) {// 一応選択可能な範囲かをチェック
											choiceNum = 2;
										}
										break;
									default:
										choiceNum = Utille.getIntRand(choiceies);
									}
									List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
									if (isExistEle(eleList2, choiceNum)) {
										// 選択
										clickSleepSelector(eleList2.get(choiceNum), 2500);
										if (isExistEle(driver, seleNext2)) {
											// 次へ
											clickSleepSelector(driver, seleNext2, 4000);
										}
									}
								}
								else if (isExistEle(driver, seleSele)) {
									int size3 = getSelectorSize(driver, seleSele + ">option");
									choiceNum = Utille.getIntRand(size3);
									String value = driver.findElements(By.cssSelector(seleSele + ">option"))
											.get(choiceNum).getAttribute("value");
									Select selectList = new Select(driver.findElement(By.cssSelector(seleSele)));
									//										selectList.deselectByIndex(choiceNum);
									selectList.selectByValue(value);
									if (isExistEle(driver, seleNext3)) {
										// 次へ
										clickSleepSelector(driver, seleNext3, 4000);
									}
								}
								else {
									break;
								}
							}
							if (isExistEle(driver, overLay)) {
								checkOverlay(driver, overLay);
							}
							if (isExistEle(driver, seleNext)) {
								clickSleepSelector(driver, seleNext, 3000); // 遷移
							}
							String finishSele = "div#again_bt>a>img";
							if (isExistEle(driver, overLay)) {
								checkOverlay(driver, overLay);
							}
							if (isExistEle(driver, finishSele)) {
								clickSleepSelector(driver, finishSele, 3000); // 遷移
								// 一覧に戻るはず
							}
						}
					}
					else {
						break;
					}
				}
			}
		}
	}
}
