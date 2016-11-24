package pointGet.mission.pex;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 * 午前7：00～翌日午前6：59
 */
public class PEXNews extends Mission {
	final String url = "http://pex.jp/point_news";

	/**
	 * @param log
	 */
	public PEXNews(Logger log) {
		super(log);
		this.mName = "■毎日ニュース";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		int pexNewsNum = 5;
		int pexNewsClick = 0;
		for (int i = 0; pexNewsClick < pexNewsNum; i++) {
			driver.get(this.url);
			// 未獲得印3つが確認できない場合ノルマ達成とみなしブレイク
			selector = "li.pt.ungained";
			if (!super.isExistEle(driver, selector)) {
				break;
			}
			selector = "ul#news-list>li>figure>a";
			if (!super.isExistEle(driver, selector)) {
				break;
			}
			List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
			String newsUrl = eleList.get(i).getAttribute("href");
			logg.info("newsUrl:" + newsUrl);
			driver.get(newsUrl);
			Utille.sleep(3000);
	
			int ran = Utille.getIntRand(4);
			String selectId = "table.you_say input";
			if (ran == 0) {
				selectId += "#submit-like";
			}
			else if (ran == 1) {
				selectId += "#submit-angry";
			}
			else if (ran == 2) {
				selectId += "#submit-sad";
			}
			else {
				selectId += "#submit-cool";
			}
			if (super.isExistEle(driver, selectId)) {
				driver.findElement(By.cssSelector(selectId)).click();
				Utille.sleep(3000);
				pexNewsClick++;
			}
			else if (super.isExistEle(driver, "p.got_maxpoints")) {
				break;
			}
		}
	}

}
