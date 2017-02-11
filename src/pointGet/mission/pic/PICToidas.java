package pointGet.mission.pic;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class PICToidas extends Mission {
	final String url = "http://dietnavi.com/pc/";

	/**
	 * @param logg
	 */
	public PICToidas(Logger logg, Map<String, String> cProps) {
		super(logg, cProps);
		this.mName = "■GMYトイダス";
	}

	@Override
	public void roopMission(WebDriver driver) {

	}

	@Override
	public void privateMission(WebDriver driver) {
		selector = "div.everyday_list1 ul>li>a[href='http://dietnavi.com/pc/ad_jump.php?id=42431']";
		driver.get(url);
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 5500); // 遷移
			changeWindow(driver);
			while (true) {
//				selector = "li.col-md-4.col-sm-6.entry-item.category-trivia.checked";	// が取得済み
				selector = "li[class='col-md-4 col-sm-6 entry-item category-trivia ']";
				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
				int size1 = eleList.size();
				WebElement wEle = null;
				for (int i = 0; i < size1; i++) {
					if (isExistEle(eleList, i)) {
						wEle = eleList.get(i);
						break;
					}
				}
				if (wEle == null) {
					break;
				}
				selector = "div.entry-text-wrap a";
				if (isExistEle(wEle, selector)) {
					clickSleepSelector(wEle, selector, 4500); // 遷移
					selector = "div#pager";// 始める
					if (isExistEle(driver, selector)) {
						clickSleepSelector(driver, selector, 5000); // 遷移

						String sele2 = "div.toidas-question-answer.fade-transition[style='opacity: 1;'] div.toidas-question-answer-checkbox-img";
						if (isExistEle(driver, sele2)) {
							for (int i = 0; i < 10; i++) {
								selector = sele2;
								if (isExistEle(driver, selector)) {
									int size = getSelectorSize(driver, selector); // 選択肢の数を数える
									int ran1 = Utille.getIntRand(size);
									if (isExistEle(driver.findElements(By.cssSelector(selector)).get(ran1))) {
										driver.findElements(By.cssSelector(selector)).get(ran1).click(); // 選択
										Utille.sleep(2000);

										// end-btn が出たら終了
										String none = "[style*='display: none']";
										String nextSelector = "div#pager";
										String endSelector = "div.actionBar>a.end-btn";
										if (isExistEle(driver, nextSelector)
//												&& isExistEle(driver, endSelector + none)
												) {
											clickSleepSelector(driver, nextSelector, 2000); // 遷移
											if (isExistEle(driver, nextSelector)) {
												clickSleepSelector(driver, nextSelector, 2000); // 遷移
											}
										}
									}
								}
							}
							String nextSelector = "div#pager";
							clickSleepSelector(driver, nextSelector, 2000); // 一覧に戻る
						}
					}
				}
				else {
					logg.warn(mName + "]all済み");
					break;
				}
			}
		}
	}
}
