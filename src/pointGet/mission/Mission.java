package pointGet.mission;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Eventually;
import pointGet.Utille;

/**
 * 
 * @author saitou
 *
 */
public abstract class Mission {

	/* execute count */
	protected int cnt = 0;
	/* execute count limit */
	protected int limit = 0;
	/* last execution time */
	protected long lastDoneTime = 0;
	/* is mission complete */
	protected boolean compFlag = false;
	/* log */
	protected Logger logg = null;
	/* first selector */
	protected String selector = "";
	/* mission name */
	protected String mName = "";

	/**
	 * constracter
	 * @param logg	log4j object
	 */
	public Mission(Logger logg) {
		this.logg = logg;
	}

	/**
	 * @return boolean(is mission complete)
	 */
	public boolean isCompFlag() {
		return compFlag;
	}

	/**
	 * @param driver
	 */
	public void exePrivateMission(WebDriver driver) {
		exeMission(driver, 0);
	}

	/**
	 * 
	 * @param driver
	 */
	public void exeRoopMission(WebDriver driver) {
		exeMission(driver, 1);
	}

	/**
	 * roop mission execute
	 * @param driver 
	 */
	public abstract void roopMission(WebDriver driver);

	/**
	 * @param driver
	 */
	public abstract void privateMission(WebDriver driver);

	/**
	 * @param driver
	 */
	protected void waitTilReady(WebDriver driver) {
		Eventually.eventually(() -> assertEquals(
				((JavascriptExecutor) driver).executeScript("return document.readyState"), "complete"));
	}

	/**
	 * 
	 * @param driver
	 * @param selector
	 * @return
	 */
	protected boolean isExistEle(WebDriver driver, String selector) {
		return Utille.isExistEle(driver, selector, this.logg);
	}

	protected boolean isExistEle(List<WebElement> ele) {
		return Utille.isExistEle(ele, this.logg);
	}

	/**
	 * 
	 * @param ele
	 * @return
	 */
	protected boolean isExistEle(WebElement ele) {
		List<WebElement> eleL = new ArrayList<WebElement>();
		eleL.add(ele);
		return this.isExistEle(eleL);
	}

	/**
	 * 
	 * @param wEle
	 * @param selector
	 * @return
	 */
	protected boolean isExistEle(WebElement wEle, String selector) {
		return Utille.isExistEle(wEle, selector, this.logg);
	}

	/**
	 * 
	 * @param driver
	 * @param selector
	 * @return
	 */
	protected int getSelectorSize(WebDriver driver, String selector) {
		return driver.findElements(By.cssSelector(selector)).size();
	}

	/**
	 * 
	 * @param driver
	 * @param selector
	 */
	protected void clickSelector(WebDriver driver, String selector) {
		driver.findElement(By.cssSelector(selector)).click();
	}

	/**
	 * 
	 * @param driver
	 * @param selector
	 * @param milliSeconds
	 */
	protected void clickSleepSelector(WebDriver driver, String selector, int milliSeconds) {
		this.clickSelector(driver, selector);
		Utille.sleep(milliSeconds);
	}

	/**
	 * 
	 * @param driver
	 * @param selector
	 * @param waitFlag
	 */
	protected void checkOverlay(WebDriver driver, String selector, boolean isWait) {
		int i = 0;
		while (i < 10) {
			try {
				if (isWait) {
					this.waitTilReady(driver);
				}
				if (this.isExistEle(driver, selector)) {
					this.clickSleepSelector(driver, selector, 2000); // 広告消す
					logg.info("広告消してやったぜ");
				}
				break;
			} catch (Throwable e) {
				this.logg.error("広告消えない[" + ++i + "回目]");
				e.printStackTrace();
				Utille.sleep(2000);
				continue;
			}
			//			logg.info("check roop[" + i + "]");
		}
	}

	/**
	 * 
	 * @param driver
	 * @param selector
	 */
	protected void checkOverlay(WebDriver driver, String selector) {
		this.checkOverlay(driver, selector, true);
	}

	/**
	 * to active the new windows 
	 * @param driver
	 */
	protected void changeWindow(WebDriver driver) {
		String wid = driver.getWindowHandle();
		java.util.Set<String> widSet = driver.getWindowHandles();
		String new_window_id = null;
		for (String id : widSet) {
			if (!id.equals(wid)) {
				//現在のウインドウIDと違っていたら格納  
				//最後に格納されたIDが一番新しく開かれたウインドウと判定  
				new_window_id = id;
			}
		}
		driver.close();
		//最後に格納したウインドウIDにスイッチ  
		driver.switchTo().window(new_window_id);
	}

	/**
	 * 
	 * @param driver
	 * @param i
	 */
	private void exeMission(WebDriver driver, int i) {
		logg.info("[[[" + mName + "]]] START-");
		try {
			if (i == 0) {
				this.privateMission(driver);

			}
			else {
				this.roopMission(driver);
			}
		} catch (Exception e) {
			e.printStackTrace();
			driver.quit();
			logg.error("##Exception##################");
			logg.error(e.getLocalizedMessage());
			logg.error(Utille.parseStringFromStackTrace(e));
			logg.error("##Exception##################");
		}
		logg.info("[[[" + mName + "]]] END-");
	}
}
