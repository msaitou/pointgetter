package pointGet.mission.mop;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.Utille;

public class MOPPointResearch extends MOPBase {
	final String url = "http://pc.moppy.jp/point_research/index.php";

	/**
	 * @param logg
	 */
	public MOPPointResearch(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "ポイントリサーチ");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		int i = 1;
		while (true) {
			selector = "a.pointResearch__box__btn";
			List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
			int size = eleList.size(), index = size - i;
			if (index > -1 && isExistEle(eleList, index)) {
				String wid = driver.getWindowHandle();
				clickSleepSelector(eleList, index, 3000); // アンケートスタートページ
				changeWindow(driver, wid);
				selector = "div.ui-control.type-fixed>a.ui-button.quake";
				if (isExistEle(driver, selector)) {
					closeOtherWindow(driver);
					clickSleepSelector(driver, selector, 3000);
					// 回答開始
					selector = "form>input.ui-button.quake";
					if (isExistEle(driver, selector)) {
						clickSleepSelector(driver, selector, 3000);
						String choiceSele = "label.ui-label-radio", seleNext2 = "div.fx-control>input.ui-button.quake", seleSele = "select.ui-select", overLay = "div.overlay-popup a.button-close", noSele = "div.ui-item-no", titleSele = "h2.ui-item-title", checkSele = "label.ui-label-checkbox";
						// 12問
						for (int k = 1; k <= 13; k++) {
							if (!isExistEle(driver, "div.overlay-popup[style*='display: none;'] a.button-close")
									&& isExistEle(driver, overLay)) {
								checkOverlay(driver, overLay, false);
							}
							if (isExistEle(driver, noSele)) {
								String qNo = driver.findElement(By.cssSelector(noSele)).getText();
								String qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
								logg.info(qNo + " " + qTitle);
								int choiceNum = 0;
								if (isExistEle(driver, choiceSele)) {
									int choiceies = getSelectorSize(driver, choiceSele);
									switch (k) {
										case 1: // 性別
										case 3: // 結婚
											// 1問目は1：男
											// 3問目は3：未婚
											break;
										case 5: // 職業
										case 2: // 年齢
											// 2問目は3：30代
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
									}
								}
								else if (isExistEle(driver, checkSele)) {
									int size4 = getSelectorSize(driver, checkSele);
									choiceNum = Utille.getIntRand(size4);
									List<WebElement> eleList2 = driver.findElements(By.cssSelector(checkSele));
									if (isExistEle(eleList2, choiceNum)) {
										// 選択
										clickSleepSelector(eleList2.get(choiceNum), 2500);
									}
								}
								else if (isExistEle(driver, seleSele)) {
									Utille.sleep(2000);
									int size3 = getSelectorSize(driver, seleSele + ">option");
									String value = "";
									if (qTitle.indexOf("居住区") >= 0) {
										value = "4";
									}
									else {
										choiceNum = Utille.getIntRand(size3);
										value = driver.findElements(By.cssSelector(seleSele + ">option"))
												.get(choiceNum).getAttribute("value");
									}
									Select selectList = new Select(driver.findElement(By.cssSelector(seleSele)));
									selectList.selectByValue(value);
									Utille.sleep(3000);
								}
							}
							else {
								break;
							}
							if (isExistEle(driver, seleNext2)) {
								// 次へ
								clickSleepSelector(driver, seleNext2, 4000);
							}
						}
						Utille.sleep(2000);
						if (!isExistEle(driver, "div.overlay-popup[style*='display: none;'] a.button-close")
								&& isExistEle(driver, overLay)) {
							checkOverlay(driver, overLay, false);
						}
						if (isExistEle(driver, selector)) {
							// ポイント獲得
							clickSleepSelector(driver, selector, 3000);
						}
						if (isExistEle(driver, overLay)) {
							checkOverlay(driver, overLay, false);
						}
						String closeSele = "div.ui-control>a", moreSele = "div.pointResearch__more>a>span";
						if (isExistEle(driver, closeSele)) {
							// 閉じる
							clickSleepSelector(driver, closeSele, 3000);
						}
						if (isExistEle(driver, moreSele)) {
							// point一覧に戻る
							clickSleepSelector(driver, moreSele, 5000);
						}
					}
				}
				else {
					i++;
					driver.close();
					driver.switchTo().window(wid);
				}
			}
			else {
				break;
			}
		}
	}
}
