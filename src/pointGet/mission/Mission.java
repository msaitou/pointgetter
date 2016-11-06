package pointGet.mission;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;

/**
 * 
 * @author saitou
 *
 */
public abstract class Mission {

	// execute count
	protected int cnt = 0;
	// execute count limit
	protected int limit = 0;
	// last execution time
	protected long lastDoneTime = 0;
	// is mission complete
	protected boolean compFlag = false;
	// log
	protected Logger log = null;

	/**
	 * constracter
	 * @param log	log4j object
	 */
	public Mission(Logger log) {
		this.log = log;
	}

	/**
	 * roop mission execute
	 * @param driver 
	 */
	public abstract void roopMisstion(WebDriver driver);
	
	protected boolean isExistEle(WebDriver driver, String selector) {
		return Utille.isExistEle(driver, selector, this.log);
	}

	/**
	 * @return boolean(is mission complete)
	 */
	public boolean isCompFlag() {
		return compFlag;
	}
}
