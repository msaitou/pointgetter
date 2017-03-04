package pointGet.mission.gmy;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;

/**
 * @author saitou
 *
 */
public class GMYToidas extends GMYBase {
	final String url = "http://dietnavi.com/pc/";

	/**
	 * @param logg
	 */
	public GMYToidas(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "トイダス");
	}

	@Override
	public void privateMission(WebDriver driver) {
		selector = "div.everyday_list1 ul>li>a[href='http://dietnavi.com/pc/ad_jump.php?id=42431']";
		driver.get(url);
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 7500); // 遷移
			changeCloseWindow(driver);
			while (true) {
				driver.switchTo().defaultContent();
				//				selector = "li.col-md-4.col-sm-6.entry-item.category-trivia.checked";	// が取得済み
				selector = "li[class='col-md-4 col-sm-6 entry-item category-trivia ']";
				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
				int size1 = eleList.size();
				WebElement wEle = null;
				for (int i = 0; i < size1; i++) {
					if (isExistEle(eleList, i)) {
						if (isExistEle(eleList.get(i), "div.entry-date")) {
							String entryDate = eleList.get(i).findElement(By.cssSelector("div.entry-date")).getText();
							String nowDate = Utille.getNowTimeStr("yyyy/MM/dd");
							logg.warn("entryDate:" + entryDate + " nowDate:" + nowDate);

							if (entryDate.equals(nowDate)) {
								wEle = eleList.get(i);
								break;
							}
							break;
						}
					}
				}
				if (wEle == null) {
					break;
				}
				selector = "div.entry-text-wrap a";
				if (isExistEle(wEle, selector)) {
					clickSleepSelector(wEle, selector, 7000); // 遷移
					this.waitTilReady(driver);
					driver.switchTo().frame(0);
					selector = "div#pager";// 始める
					if (isExistEle(driver, selector)) {
						clickSleepSelector(driver, selector, 10000); // 遷移
						//						this.waitTilReady(driver);
						String sele2 = "div.toidas-question-answer.fade-transition[style='opacity: 1;'] div.toidas-question-answer-checkbox-img";
						if (isExistEle(driver, sele2)) {
							for (int i = 0; i < 10; i++) {
								if (isExistEle(driver, sele2)) {
									List<WebElement> eleList2 = driver.findElements(By.cssSelector(sele2));
									int size = eleList2.size();
									int ran1 = Utille.getIntRand(size);
									if (isExistEle(eleList2, ran1)) {
										clickSleepSelector(eleList2, ran1, 2000);// 選択
										String nextSelector = selector;
										if (isExistEle(driver, nextSelector)) {
											clickSleepSelector(driver, nextSelector, 2000); // 遷移
											if (isExistEle(driver, nextSelector)) {
												clickSleepSelector(driver, nextSelector, 2000); // 遷移
											}
										}
									}
								}
							}
							Utille.sleep(3000);
							clickSleepSelector(driver, selector, 5000); // pointgett
							logg.warn(mName + "]1つクリア！！");
							clickSleepSelector(driver, selector, 5000); // 一覧に戻る
						} else {
							logg.warn(mName + "]sele2なし");
						}
					} else {
						logg.warn(mName + "]始められない？");
					}
				} else {
					logg.warn(mName + "]all済み");
					break;
				}
				logg.warn(mName + "]roopします");
			}
		}
	}
}
