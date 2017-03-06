package pointGet.mission.pto;

import java.util.HashMap;
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
public class PTODaily extends PTOBase {
	final String url = "http://www.pointtown.com/ptu/mypage/top.do";
	private static HashMap<String, HashMap<String, String>> clickMap = new HashMap<String, HashMap<String, String>>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("ポイント争奪戦", new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("url", "http://www.pointtown.com/ptu/competition/entry.do");
					put("sele", "a.btn-green");
				}
			});
			put("ぽいっとアンケート", new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("url", "http://www.pointtown.com/ptu/vote/entry.do");
					put("sele", "input[name='user_choice']");
				}
			});
			put("べじもん", new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("url", "http://www.pointtown.com/ptu/collection/index.do");
					put("sele", "div.bnArea dt>a>img");
				}
			});
			put("ポイントQ", new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("url", "http://www.pointtown.com/ptu/quiz/input.do");
					put("sele", "input#answer");
				}
			});
			put("ポイントチャンス", new HashMap<String, String>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("url", "http://www.pointtown.com/ptu/monitor/top.do#pointChance");
					put("sele", "li.pointchanceItem>a>img");
				}
			});

		}
	};

	/**
	 * @param log
	 */
	public PTODaily(Logger log, Map<String, String> cProps) {
		super(log, cProps, "Daily");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		int c = 0;
		for (Map.Entry<String, HashMap<String, String>> clMap : clickMap.entrySet()) {
			driver.get(clMap.getValue().get("url"));
			String sele = "";
			int size = 0;
			List<WebElement> eleList = null;
			switch (clMap.getKey()) {
				case "ポイント争奪戦":
					if (isExistEle(driver, clMap.getValue().get("sele"))) {
						logg.info(mName + " " + ++c + "." + clMap.getKey() + "!");
						clickSleepSelector(driver, clMap.getValue().get("sele"), 4000);
					}
					break;
				case "ぽいっとアンケート":
					sele = "div>input.over";
					eleList = driver.findElements(By.cssSelector(clMap.getValue().get("sele")));
					Utille.sleep(3000);
					if (eleList.size() > 0) {
						int ran = Utille.getIntRand(eleList.size());
						if (isExistEle(eleList, ran)) {
							logg.info(mName + " " + ++c + "." + clMap.getKey() + "!");
							clickSleepSelector(eleList, ran, 2000);
							if (isExistEle(driver, sele)) {
								clickSleepSelector(driver, sele, 2000);
								logg.info(mName + " " + c + "." + sele + "click!");
								//								clickSleepSelector(driver, sele, 2000);
							}
						}
					}
					break;
				case "ポイントチャンス":
					eleList = driver.findElements(By.cssSelector(clMap.getValue().get("sele")));
					size = eleList.size();
					++c;
					for (int i = 0; i < size; i++) {
						if (isExistEle(eleList, i)) {
							logg.info(mName + " " + c + "." + clMap.getKey() + i + "個目!");
							clickSleepSelector(eleList, i, 2000);
							closeOtherWindow(driver);
						}
					}
					break;
				case "べじもん":
					eleList = driver.findElements(By.cssSelector(clMap.getValue().get("sele")));
					size = eleList.size();
					++c;
					for (int i = 0; i < size; i++) {
						if (isExistEle(eleList, i)) {
							logg.info(mName + " " + c + "." + clMap.getKey() + i + "個目!");
							clickSleepSelector(eleList, i, 2000);
							closeOtherWindow(driver);
						}
					}
					driver.get("http://www.pointtown.com/ptu/collection/collection.do");
					sele = "li.listbox dt>a>img";
					List<WebElement> eleList2 = driver.findElements(By.cssSelector(sele));
					int size2 = eleList2.size();
					for (int i = 0; i < size2; i++) {
						if (isExistEle(eleList2, i)) {
							logg.info(mName + " " + c + "." + clMap.getKey() + "　隠れ " + i + "個目!");
							clickSleepSelector(eleList2, i, 2000);
							closeOtherWindow(driver);
						}
					}
					break;
				case "ポイントQ":
					// ランダムで1,2を選ぶ
					int ran1 = Utille.getIntRand(4) + 1;
					sele = clMap.getValue().get("sele") + "" + ran1;
					if (isExistEle(driver, sele)) {
						clickSleepSelector(driver, sele, 2000);
						String sele2 = "p.answer_btn>a";
						if (isExistEle(driver, sele2)) {
							logg.info(mName + " " + ++c + "." + clMap.getKey() + "!");
							clickSleepSelector(driver, sele2, 2000);
						}
					}
					break;
				default:
			}
		}
	}
}
