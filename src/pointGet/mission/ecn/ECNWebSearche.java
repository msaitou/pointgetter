package pointGet.mission.ecn;

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
public class ECNWebSearche extends Mission {
	final String url = "http://ecnavi.jp/search/web/?Keywords=";

	private String[] wordList = null;
	/**
	 * @param log
	 */
	public ECNWebSearche(Logger log, String[] wordlist) {
		super(log);
		this.mName = "■web検索";
		this.wordList = wordlist;
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		// propertiesファイルから単語リストを抽出して、ランダムで5つ（数は指定可能）
		String[] wordSearchList = Utille.getWordSearchList(this.wordList, 5);
		driver.get(this.url);
		int ecnSearchNum = 4;
		for (int i = 0; i < ecnSearchNum; i++) {
			String selector = "input[name='Keywords']";
			if (isExistEle(driver, selector)) {
				WebElement ele = driver.findElement(By.cssSelector(selector));
				ele.clear();
				logg.info("検索keyword[" + wordSearchList[i]);
				ele.sendKeys(wordSearchList[i]);
				clickSleepSelector(driver, "button[type='submit']", 3000);
			}
		}
	}
}
