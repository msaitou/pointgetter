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
		String mName = "■ぺく単";
		log.info(mName+"]roop");
		for (; lastDoneTime == 0 || (lastDoneTime + 306000 <= System.currentTimeMillis());) {
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
					log.info("clicked!!");
				}
			}
			this.cnt++;
			this.lastDoneTime = System.currentTimeMillis();
		}
		log.info("this.cnt:" + this.cnt + " this.limit:" + this.limit);
		if (this.cnt >= this.limit) {
			log.info(mName+"]roop end");
			this.compFlag = true;
		}
	}
}
