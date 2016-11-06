package pointGet.mission;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;

/**
 * @author saitou
 *
 */
public class PexMissionPectan extends Mission {

	/**
	 * @param log
	 */
	public PexMissionPectan(Logger log) {
		super(log);
		this.limit = 5;
	}

	@Override
	public void roopMisstion(WebDriver driver) {
		log.info("kimasu");
		for (; lastDoneTime == 0 || (lastDoneTime + 306000 <= System.currentTimeMillis());) {
			log.info("forrrrrr");
			driver.get("http://pex.jp/pekutan/words/current");
			String selector = "section.question ul";
//			section.question があったらクリア！　TODO
			if (isExistEle(driver, selector)) {
				log.info("atta");
				// ランダムで1,2を選ぶ
				int ran = Utille.getIntRand(2);
				String selectVal = "input[name='commit']";
				if (ran == 0) {
					selectVal += "[value='はい']";
				}
				else {
					selectVal += "[value='いいえ']";
				}
				if (isExistEle(driver, selectVal)) {
					driver.findElement(By.cssSelector(selectVal)).click();
					log.info("kimasita");
				}
			}
			this.cnt++;
			this.lastDoneTime = System.currentTimeMillis();
		}
		log.info("this.cnt:" + this.cnt + " this.limit:" + this.limit);
		if (this.cnt >= this.limit) {
			log.info("end");
			this.compFlag = true;
		}
	}
}
