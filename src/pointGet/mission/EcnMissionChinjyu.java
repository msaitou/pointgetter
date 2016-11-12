package pointGet.mission;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;

/**
 * @author saitou
 *
 */
public class EcnMissionChinjyu extends Mission {

	private boolean endFlag = false;
	/**
	 * @param log
	 */
	public EcnMissionChinjyu(Logger log) {
		super(log);
		this.limit = 5;
	}

	@Override
	public void roopMisstion(WebDriver driver) {
		String mName = "■珍獣先生";
		log.info(mName+"]roop");
		for (; lastDoneTime == 0 || (lastDoneTime + 306000 <= System.currentTimeMillis());) {
			driver.get("http://ecnavi.jp/research/chinju_lesson/");
			if (!isExistEle(driver, "div.button_area.clear_fix")) {
				endFlag = true;
				log.info("break");
				break;
			}
			// ランダムで1,2を選ぶ
			int ran = Utille.getIntRand(2);
			String selector1 = "p";
			if (ran == 0) {
				selector1 += ".button_yes a";
			}
			else {
				selector1 += ".button_no a";
			}
			if (isExistEle(driver, selector1)) {
				String AnsUrl = driver.findElement(By.cssSelector(selector1)).getAttribute("href");
				driver.get(AnsUrl);
				log.info("clicked!!");
			}
			this.cnt++;
			this.lastDoneTime = System.currentTimeMillis();
		}
		log.info("this.cnt:"+this.cnt+" this.limit:"+this.limit);
		if (endFlag || this.cnt >= this.limit) {
			log.info(mName+"]roop end");
			this.compFlag = true;
		}
	}
}
