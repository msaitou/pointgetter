/**
 * 
 */
package pointGet.mission.ecn;

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
public abstract class ECNBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_ECN;
	boolean finsishFlag = false;

	/**
	 * @param log
	 * @param cProps
	 */
	public ECNBase(Logger log, Map<String, String> cProps, String name) {
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
	/**
	 * 
	 * @param loggg
	 * @param cProps
	 * @param missions
	 */
	public static void goToClick(Logger loggg, Map<String, String> cProps, ArrayList<String> missions,
			ArrayList<Mission> missionList, String[] wordList) {
		WebDriver driver = getWebDriver(cProps);
		for (String mission : missions) {
			Mission MisIns = null;
			switch (mission) {
				case Define.strECNGaragara: // ■ガラガラ
					MisIns = new ECNGaragara(loggg, cProps);
					break;
				case Define.strECNNews: // ■毎日ニュース
					MisIns = new ECNNews(loggg, cProps);
					break;
				case Define.strECNTellmeWhich: // ■教えてどっち
					MisIns = new ECNTellmeWhich(loggg, cProps);
					break;
				case Define.strECNDron: // ■ドロンバナークリック2種
					MisIns = new ECNDron(loggg, cProps);
					break;
				case Define.strECNClickBokin: // ■クリック募金
					MisIns = new ECNClickBokin(loggg, cProps);
					break;
				case Define.strECNSearchBokin: // ■検索募金
					MisIns = new ECNSearchBokin(loggg, cProps, wordList);
					break;
				case Define.strECNWebSearche: // ■検索募金
					MisIns = new ECNWebSearche(loggg, cProps, wordList);
					break;
				case Define.strECNChinjyu: // ■珍獣先生
					MisIns = new ECNChinjyu(loggg, cProps);
					missionList.add(MisIns);
					break;
				case Define.strECNChirachi: // ■チラシ
					MisIns = new ECNChirachi(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strECNGaragara,
					Define.strECNNews,
					Define.strECNTellmeWhich,
					Define.strECNDron,
					Define.strECNClickBokin,
					Define.strECNSearchBokin,
					Define.strECNWebSearche,
					Define.strECNChinjyu,
					Define.strECNChirachi,
			}).contains(mission)) {
				driver = MisIns.exeRoopMission(driver);
			}
			else {
				driver = MisIns.exePrivateMission(driver);
			}
		}
		driver.quit();
	}
}
