/**
 * 
 */
package pointGet.mission.pex;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Define;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class PEXBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_PEX;
	boolean finsishFlag = false;

	/**
	 * @param log
	 * @param cProps
	 */
	public PEXBase(Logger log, Map<String, String> cProps, String name) {
		super(log, cProps);
		this.mName = "■" + sCode + name;
	}

	@Override
	public void roopMission(WebDriver driver) {
		for (int i = 0; i < 1 && !finsishFlag; i++) {
			privateMission(driver);
		}
	}

	@Override
	public void privateMission(WebDriver driver) {
	}
}
