package pointGet.mission.pex;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.val;
import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class PEXAnswer extends Mission {
	final String url = "http://pex.jp/minna_no_answer/questions/current";

	/**
	 * @param log
	 */
	public PEXAnswer(Logger log) {
		super(log);
		this.mName = "■みんなのアンサー";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(this.url);
		// ランダムで1,2を選ぶ
		int ran = Utille.getIntRand(2);
		selector = "section.question_area input[type='submit']";
		if (super.isExistEle(driver, selector)) {
			driver.findElements(By.cssSelector(selector)).get(ran).click();
			val alert = driver.switchTo().alert();
			alert.accept();
		}
		else {
			logg.warn(mName + "]獲得済み");
		}
	}
}
