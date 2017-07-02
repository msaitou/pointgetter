package pointGet.mission.ecn;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class ECNChinjyu extends ECNBase {
	final String url = "http://ecnavi.jp/research/chinju_lesson/";
	private boolean endFlag = false;

	/**
	 * @param logg
	 */
	public ECNChinjyu(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "珍獣先生");
		this.limit = 5;
	}

	@Override
	public void privateMission(WebDriver driver) {
		logg.info(this.mName+"]roop");
		for (; lastDoneTime == 0 || (lastDoneTime + 306000 <= System.currentTimeMillis());) {
			driver.get(url);
			if (!isExistEle(driver, "div.button_area.clear_fix")) {
				endFlag = true;
				logg.info("break");
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
				logg.info("clicked!!");
			}
			this.cnt++;
			this.lastDoneTime = System.currentTimeMillis();
		}
		logg.info("this.cnt:"+this.cnt+" this.limit:"+this.limit);
		if (endFlag || this.cnt >= this.limit) {
			logg.info(this.mName+"]roop end");
			this.compFlag = true;
		}
	}
}
