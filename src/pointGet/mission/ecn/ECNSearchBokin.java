package pointGet.mission.ecn;

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
public class ECNSearchBokin extends Mission {
	final String url = "http://ecnavi.jp/smile_project/search_fund/";

	private String[] wordSearchList = null;

	/**
	 * @param log
	 */
	public ECNSearchBokin(Logger log, String[] wordList) {
		super(log);
		this.mName = "■検索募金";
		this.wordSearchList = Utille.getWordSearchList(wordList, 2);
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		int ecnSearchBokinNum = 2;
		for (int i = 0; i < ecnSearchBokinNum; i++) {
			driver.get(this.url);
			selector = "input[name='Keywords']";
			if (isExistEle(driver, selector)) {
				WebElement ele = driver.findElement(By.cssSelector("input[name='Keywords']"));
				ele.clear();
				logg.info("検索keyword[" + wordSearchList[i]);
				ele.sendKeys(this.wordSearchList[i]);
				selector = "button[type='submit']";
				clickSleepSelector(driver, selector, 3000);
			}
		}
	}
}
