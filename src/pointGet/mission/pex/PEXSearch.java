/**
 *
 */
package pointGet.mission.pex;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Define;
import pointGet.LoginSite;
import pointGet.Utille;

/**
 *
 * @author saitou
 *
 */
public class PEXSearch extends PEXBase {
	final String url = "http://pex.jp/search/index";

	private String[] wordSearchList = null;

	/**
	 * constructer
	 * @param logg
	 * @param wordList
	 */
	public PEXSearch(Logger logg, Map<String, String> cProps, String[] wordList) {
		super(logg, cProps, "ポイント検索");
		this.limit = 5;
		this.wordSearchList = Utille.getWordSearchList(wordList, 7);
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get("http://pex.jp");
		String sel = "dd.user_pt.fw_b>span.fw_b";
		if (!isExistEle(driver, sel)) {
			// Login
			LoginSite.login(Define.PSITE_CODE_PEX, driver, logg);
		}
		logg.info(this.mName + "]roop");
		for (int i = this.cnt; lastDoneTime == 0 || (lastDoneTime + 306000 <= System.currentTimeMillis()); i++) {
			driver.get(url);
			String selector = "input#keyword";
			if (isExistEle(driver, selector)) {
				WebElement ele = driver.findElement(By.cssSelector(selector));
				ele.clear();
				logg.info("検索keyword[" + wordSearchList[i]);
				ele.sendKeys(wordSearchList[i]);
				clickSelector(driver, "input[name='commit']");
				logg.info("kimasita");
			}
			this.cnt++;
			this.lastDoneTime = System.currentTimeMillis();
		}
		logg.info("this.cnt:" + this.cnt + " this.limit:" + this.limit);
		if (this.cnt == this.limit) {
			logg.info(this.mName + "]roop end");
			this.compFlag = true;
		}
	}
}
