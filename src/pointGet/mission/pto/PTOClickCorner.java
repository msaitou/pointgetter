package pointGet.mission.pto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author saitou
 *
 */
public class PTOClickCorner extends PTOBase {
	final String url = "";
	private static HashMap<String, HashMap<String, String>> clickMap = new HashMap<String, HashMap<String, String>>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("topページ上click", new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("url", "https://www.pointtown.com/ptu/index.do");
					put("sele", "a.ptpc-panel--click-corner__main-link>img");
				}
			});
			put("マイページ", new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("url", "http://www.pointtown.com/ptu/mypage/top.do");
					put("sele", "div.click-bnr img");
				}
			});
			put("メールボックス", new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("url", "http://www.pointtown.com/ptu/mail_box/list.do");
					put("sele", "div#clickBx3 img");
				}
			});
		}
	};

	/**
	 * @param log
	 */
	public PTOClickCorner(Logger log, Map<String, String> cProps) {
		super(log, cProps, "クリックコーナー");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get("https://www.pointtown.com/ptu/index.do");
		// 1．ログイン直後のTOP画面で
		int c = 0;
		for (Map.Entry<String, HashMap<String, String>> clMap : clickMap.entrySet()) {
			driver.get(clMap.getValue().get("url"));
			if (isExistEle(driver, clMap.getValue().get("sele"))) {
				logg.info(mName + " " + ++c + "." + clMap.getKey() + "!");
				clickSleepSelector(driver, clMap.getValue().get("sele"), 2000);
				closeOtherWindow(driver);
			}
		}
		++c;
		selector = "section#footer-click li.panel__list__item.has-horizontal-child span.click-txt";
		// サービスページ下
		driver.get("http://www.pointtown.com/ptu/pointpark/top.do");
		if (isExistEle(driver, selector)) {
			List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
			int size = eleList.size();
			String wid = driver.getWindowHandle();
			for (int i = 0; i < size; i++) {
				if (isExistEle(eleList, i)) {
					logg.info(mName + " " + c + ".サービスページ下! " + i);
					clickSleepSelector(eleList, i, 5000);
					changeWindow(driver, wid);
					//					java.util.Set<String> widSet = driver.getWindowHandles();
					//					for (String id : widSet) {
					//						if (!id.equals(wid)) {
					//							driver.switchTo().window(id);
					//						}
					//					}
					String sele2 = "div.service-btn a";
					if (isExistEle(driver, sele2)) {
						logg.info(mName + " " + c + ".サービスページ下! " + i + " click!!");
						clickSleepSelector(driver, sele2, 4000);
					}
					driver.close();
					// 元のウインドウIDにスイッチ
					driver.switchTo().window(wid);
				}
			}
		}
	}
}
