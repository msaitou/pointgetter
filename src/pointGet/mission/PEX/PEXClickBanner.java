package pointGet.mission.PEX;

import java.util.List;

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
public class PEXClickBanner extends Mission {
	final String url = "http://pex.jp/point_actions#clickpoint";

	/**
	 * @param log
	 */
	public PEXClickBanner(Logger log) {
		super(log);
		this.mName = "■クリックポイント";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		selector = "div.clickpoint_innner li>a>p.title";
		driver.get(this.url);
		if (isExistEle(driver, selector)) {
			if (!isExistEle(driver, "p.get_massage")) { // 獲得済みか
				if (super.isExistEle(driver, selector)) {
					List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
					// clipoバナー
					int size = eleList.size();
					for (int i = 0; i < size; i++) {
						eleList.get(i).click();// ここでももう一度存在するかを確認した方がいいんじゃ。。。TODO
						Utille.sleep(2000);
						driver.get(this.url); // 元のページに戻る
					}
				}
			} else {
				logg.warn(mName + "]獲得済み");
			}
		}
	}
}
