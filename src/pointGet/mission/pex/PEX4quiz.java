package pointGet.mission.pex;

import java.util.Map;

import lombok.val;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class PEX4quiz extends PEXBase {
	final String url = "http://pex.jp/point_quiz";

	/**
	 * @param log
	 */
	public PEX4quiz(Logger log, Map<String, String> cProps) {
		super(log, cProps, "ポイントクイズ");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(this.url);
		int ran1 = Utille.getIntRand(4);
		selector = "ul.answer_select a";
		if (isExistEle(driver, selector)) {
			driver.findElements(By.cssSelector(selector)).get(ran1).click();
			Utille.sleep(3000);
			val alert = driver.switchTo().alert();
			alert.accept();
			Utille.sleep(3000);
		}
		else {
			logg.warn(mName + "]獲得済み");
		}
	}
}
