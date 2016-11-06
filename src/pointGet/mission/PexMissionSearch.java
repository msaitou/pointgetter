/**
 * 
 */
package pointGet.mission;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;

/**
 * 
 * @author saitou
 *
 */
public class PexMissionSearch extends Mission {

	private String[] wordSearchList = null;

	/**
	 * constructer
	 * @param log 
	 * @param wordList 
	 */
	public PexMissionSearch(Logger log, String[] wordList) {
		super(log);
		this.limit = 5;
		this.wordSearchList = Utille.getWordSearchList(wordList, 7);
	}

	@Override
	public void roopMisstion(WebDriver driver) {
		log.info("kimasu");
		for (int i = this.cnt; lastDoneTime == 0 || (lastDoneTime + 306000 <= System.currentTimeMillis()); i++) {
			driver.get("http://pex.jp/search/index");
			String selector = "input#keyword";
			if (isExistEle(driver, selector)) {
				WebElement ele = driver.findElement(By.cssSelector(selector));
				ele.clear();
				log.info("検索keyword[" + wordSearchList[i]);
				ele.sendKeys(wordSearchList[i]);
				driver.findElement(By.cssSelector("input[name='commit']")).click();
				log.info("kimasita");
			}
			this.cnt++;
			this.lastDoneTime = System.currentTimeMillis();
		}
		log.info("this.cnt:"+this.cnt+" this.limit:"+this.limit);
		if (this.cnt == this.limit) {
			log.info("end");
			this.compFlag = true;
		}
	}
}
