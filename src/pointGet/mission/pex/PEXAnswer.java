package pointGet.mission.pex;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;

/**
 * @author saitou
 *
 */
public class PEXAnswer extends PEXBase {
	final String url = "http://pex.jp/minna_no_answer/questions/current";

	/**
	 * @param log
	 */
	public PEXAnswer(Logger log, Map<String, String> cProps) {
		super(log, cProps, "みんなのアンサー");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(this.url);
		// ランダムで1,2を選ぶ
		int ran = Utille.getIntRand(2);
		selector = "section.question_area input[type='submit']";
		if (isExistEle(driver, selector)) {
			driver.findElements(By.cssSelector(selector)).get(ran).click();
			Utille.sleep(2000);
//			AlertOverride alertOverride = new AlertOverride(true);
//			alertOverride.replaceAlertMethod(driver);
//			if(alertOverride.isAlertPresent(driver)){                                     //②
//			    String alertMsg = alertOverride.getNextAlert(driver);               //③
//			}
//			if(alertOverride.isConfirmationPresent(driver)){                          //④
//			val alert = driver.switchTo().alert();
//			alert.accept();
//			}
		}
		else {
			logg.warn(mName + "]獲得済み");
		}
	}
}
