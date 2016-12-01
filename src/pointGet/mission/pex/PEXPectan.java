package pointGet.mission.pex;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Define;
import pointGet.LoginSite;
import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 * 14時更新
 */
public class PEXPectan extends Mission {
	final String url = "http://pex.jp/pekutan/words/current";
	private boolean endFlag = false;

	/**
	 * @param logg
	 */
	public PEXPectan(Logger logg) {
		super(logg);
		this.mName = "■ぺく単";
		this.limit = 5;
	}

	@Override
	public void roopMission(WebDriver driver) {
		LoginSite.login(Define.PSITE_CODE_PEX, driver, logg);

		logg.info(this.mName + "]roop");
		for (; lastDoneTime == 0 || (lastDoneTime + 306000 <= System.currentTimeMillis());) {
			driver.get(url);
			// div.clear_box があったらクリア！
			selector = "div.clear_box";
			if (isExistEle(driver, selector)) {
				endFlag = true;
				break;
			}
			selector = "section.question ul";
			if (isExistEle(driver, selector)) {
				logg.info("atta");
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
					clickSelector(driver, selectVal);
					logg.info("clicked!!");
				}
			}
			this.cnt++;
			this.lastDoneTime = System.currentTimeMillis();
		}
		logg.info("this.cnt:" + this.cnt + " this.limit:" + this.limit);
		if (endFlag || this.cnt >= this.limit) {
			logg.info(this.mName + "]roop end");
			this.compFlag = true;
		}
	}

	@Override
	public void privateMission(WebDriver driver) {

	}
}
