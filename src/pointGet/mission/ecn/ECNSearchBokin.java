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
public class ECNSearchBokin extends ECNBase {
	final String url = "http://ecnavi.jp/smile_project/search_fund/";

	private String[] wordSearchList = null;

	/**
	 * @param log
	 * @param wordList
	 */
	public ECNSearchBokin(Logger log, Map<String, String> cProps, String[] wordList) {
		super(log, cProps, "検索募金");
		this.wordSearchList = Utille.getWordSearchList(wordList, 2);
	}

	@Override
	public void privateMission(WebDriver driver) {
		int ecnSearchBokinNum = 2;
		for (int i = 0; i < ecnSearchBokinNum; i++) {
			Utille.url(driver, this.url, logg);
			selector = "input[name='Keywords']";
			if (isExistEle(driver, selector)) {
				WebElement ele = driver.findElement(By.cssSelector("input[name='Keywords']"));
				ele.clear();
				logg.info("検索keyword[" + wordSearchList[i]);
				ele.sendKeys(this.wordSearchList[i]);
				selector = "button[type='submit']";
				clickSleepSelector(driver, selector, 3000);
				closeOtherWindow(driver);
			}
		}
	}
}
