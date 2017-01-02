package pointGet.mission.rin;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Define;
import pointGet.LoginSite;
import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class RINClickBanner extends Mission {
	final String url = "https://www.rakuten-card.co.jp/e-navi/members/point/click-point/index.xhtml?l-id=enavi_all_submenu_clickpoint";

	/**
	 * @param log
	 */
	public RINClickBanner(Logger log, Map<String, String> cProps) {
		super(log, cProps);
		this.mName = "■クリックバナー(楽天)";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		// login!!
		LoginSite.login(Define.PSITE_CODE_RIN, driver, logg);
		driver.get(url);
		Utille.sleep(4000);
		String[] selectorList = { "div.topArea.clearfix", "span#middleArea>ul>li" };
		String wid = driver.getWindowHandle();
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
						java.util.Set<String> widSet = driver.getWindowHandles();
						for (String id : widSet) {
							if (!id.equals(wid)) {
								//最後に格納したウインドウIDにスイッチ
								driver.switchTo().window(id);
								driver.close();
							}
						}
						// 元のウインドウIDにスイッチ
						driver.switchTo().window(wid);
					}
				}
			}
		}
	}
}
