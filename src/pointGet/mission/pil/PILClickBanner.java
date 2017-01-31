package pointGet.mission.pil;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Define;
import pointGet.LoginSite;
import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 */
public class PILClickBanner extends Mission {
	final String url = "http://www.point-island.com/mincp.asp";

	/**
	 * @param log
	 */
	public PILClickBanner(Logger log, Map<String, String> cProps) {
		super(log, cProps);
		this.mName = "■PILクリックポイント";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		LoginSite.login(Define.PSITE_CODE_PIL, driver, logg);
		driver.get(url);
		Utille.sleep(2000);
		String selecter[] = { "table.msg001 table table td>a>img" };
		for (int j = 0; j < selecter.length; j++) {
			logg.info("selector: start");
			String selector = selecter[j];
			if (isExistEle(driver, selector)) {
				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
				int size = eleList.size();
				try {
					for (int i = 0; i < size; i++) {
						if (isExistEle(eleList, i)) {
							clickSleepSelector(eleList, i, 2500);
							closeOtherWindow(driver);
						}
					}
				} catch (Throwable e) {
					driver.quit();
					logg.error("##PILException##################");
					logg.error(Utille.truncateBytes(e.getLocalizedMessage(), 500));
					logg.error("####################");
					logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 500));
					logg.error("##PILException##################");
					driver = Utille.getWebDriver(commonProps.get("geckopath"), commonProps.get("ffprofile"));
				}
			}
			logg.info("selector: end");
		}
	}
}
