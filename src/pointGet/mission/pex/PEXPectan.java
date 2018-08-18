package pointGet.mission.pex;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.LoginSite;
import pointGet.common.Define;
import pointGet.common.Utille;

/**
 * @author saitou
 * 14時更新
 */
public class PEXPectan extends PEXBase {
	final String url = "http://pex.jp/pekutan/words/current";
	private boolean endFlag = false;

	/**
	 * @param logg
	 */
	public PEXPectan(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "ぺく単");
		this.limit = 5;
	}

	@Override
	public void privateMission(WebDriver driver) {
		Utille.url(driver, "http://pex.jp", logg);
		String sel = "nav span.g-icon_point";
		if (!isExistEle(driver, sel)) {
			// Login
			LoginSite.login(Define.PSITE_CODE_PEX, driver, logg);
		}
		logg.info(this.mName + "]roop");
		for (; lastDoneTime == 0 || (lastDoneTime + 306000 <= System.currentTimeMillis());) {
			Utille.url(driver, url, logg);
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
}
