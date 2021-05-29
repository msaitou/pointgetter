package pointGet.mission.ecn;

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
public class ECNWebSearche extends ECNBase {
	final String url = "https://ecnavi.jp/contents/dictionary_search/";

	private String[] wordSearchList = null;

	/**
	 * @param log
	 * @param wordlist
	 */
	public ECNWebSearche(Logger log, Map<String, String> cProps, String[] wordlist) {
		super(log, cProps, "web検索");
		this.wordSearchList = Utille.getWordSearchList(wordlist, 5);
	}

	@Override
	public void privateMission(WebDriver driver) {
		// propertiesファイルから単語リストを抽出して、ランダムで5つ（数は指定可能）
		Utille.url(driver, this.url, logg);
		int ecnSearchNum = 4;
		for (int i = 0; i < ecnSearchNum; i++) {
			String selector = "input[name='Keywords']";
			if (isExistEle(driver, selector)) {
				WebElement ele = driver.findElement(By.cssSelector(selector));
				ele.clear();
				logg.info("検索keyword[" + wordSearchList[i]);
				ele.sendKeys(wordSearchList[i]);
				clickSleepSelector(driver, "form#dictionary-search-form button[type='submit']", 3000);
			}
		}
	}
}
