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
public class PEX4quiz extends Mission {
	final String url = "http://pex.jp/point_quiz";

	/**
	 * @param log
	 */
	public PEX4quiz(Logger log) {
		super(log);
		this.mName = "■ポイントクイズ";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(this.url);
		int ran1 = Utille.getIntRand(4);
		selector = "ul.answer_select a";
		if (super.isExistEle(driver, selector)) {
			driver.findElements(By.cssSelector(selector)).get(ran1).click();
			val alert2 = driver.switchTo().alert();
			alert2.accept();
		}
		else {
			logg.warn(mName + "]獲得済み");
		}
	}
}
