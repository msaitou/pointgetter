/**
 *
 */
package pointGet.mission.pst;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Define;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class PSTBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_PST;
	boolean finsishFlag = false;

	/**
	 * @param log
	 * @param cProps
	 */
	public PSTBase(Logger log, Map<String, String> cProps, String name) {
		super(log, cProps);
		this.mName = "■" + sCode + name;
	}

	@Override
	public void roopMission(WebDriver driver) {
		for (int i = 0; i < 5 && !finsishFlag; i++) {
			privateMission(driver);
		}
	}

	@Override
	public void privateMission(WebDriver driver) {
	}
}
