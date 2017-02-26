/**
 * 
 */
package pointGet.mission.cri;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Define;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class CRIBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_CRI;

	/**
	 * @param log
	 * @param cProps
	 */
	public CRIBase(Logger log, Map<String, String> cProps, String name) {
		super(log, cProps);
		this.mName = "■" + sCode + name;
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
	}
}
