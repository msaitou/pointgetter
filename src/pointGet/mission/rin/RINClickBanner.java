package pointGet.mission.rin;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class RINClickBanner extends RINBase {
	final String url = "https://www.rakuten-card.co.jp/e-navi/members/point/click-point/index.xhtml?l-id=enavi_all_submenu_clickpoint";

	/**
	 * @param log
	 */
	public RINClickBanner(Logger log, Map<String, String> cProps) {
		super(log, cProps, "クリックバナー(楽天)");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		Utille.sleep(4000);
		String[] selectorList = { "div.topArea.clearfix", "span#middleArea>ul>li" };
		for (int j = 0; j < selectorList.length; j++) {
			if (isExistEle(driver, selectorList[j])) {
				String selector2 = "[alt='Check']";
				int size = driver.findElements(By.cssSelector(selectorList[j])).size();
				for (int i = 0; i < size; i++) {
					// document.querySelectorAll('[alt="Check"]')[1].parentNode.parentNode.parentNode.querySelectorAll('div.bnrBox
					// img')[0];
					WebElement e = driver.findElements(By.cssSelector(selectorList[j])).get(i);
					if (isExistEle(e, selector2)) {
						e.findElement(By.cssSelector("a>img")).click();
						Utille.sleep(2000);
						closeOtherWindow(driver);
					}
				}
			}
		}
	}
}
