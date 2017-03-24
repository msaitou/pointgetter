/**
 * 
 */
package pointGet.mission.i2i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Define;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class I2IBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_I2I;

	/**
	 * @param log
	 * @param cProps
	 */
	public I2IBase(Logger log, Map<String, String> cProps, String name) {
		super(log, cProps);
		this.mName = "■" + sCode + name;
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
	}

	/**
	 * 
	 * @param loggg
	 * @param cProps
	 * @param missions
	 */
	public static void goToClick(Logger loggg, Map<String, String> cProps, ArrayList<String> missions) {
		WebDriver driver = getWebDriver(cProps);
		for (String mission : missions) {
			Mission MisIns = null;
			switch (mission) {
				case Define.strI2ISeiza: // ■星座占い
					MisIns = new I2ISeiza(loggg, cProps);
					break;
				case Define.strI2IColum: // ■コラム
					MisIns = new I2IColum(loggg, cProps);
					break;
				case Define.strI2IMangaVer2: // ■漫画
					MisIns = new I2IMangaVer2(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strI2ISeiza,
					Define.strI2IColum,
					Define.strI2IMangaVer2,
					}).contains(mission)) {
				driver = MisIns.exePrivateMission(driver);
			}
		}
		driver.quit();
	}

}
