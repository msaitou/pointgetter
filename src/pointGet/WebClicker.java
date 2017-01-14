package pointGet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.mission.Mission;
import pointGet.mission.ecn.ECNChinjyu;
import pointGet.mission.ecn.ECNChirachi;
import pointGet.mission.ecn.ECNClickBokin;
import pointGet.mission.ecn.ECNDron;
import pointGet.mission.ecn.ECNGaragara;
import pointGet.mission.ecn.ECNNews;
import pointGet.mission.ecn.ECNSearchBokin;
import pointGet.mission.ecn.ECNTellmeWhich;
import pointGet.mission.ecn.ECNWebSearche;
import pointGet.mission.gen.GENClickBanner;
import pointGet.mission.gen.GENPointStar;
import pointGet.mission.gen.GENShindan;
import pointGet.mission.gen.GENUranai;
import pointGet.mission.gmy.GMYChirachi;
import pointGet.mission.gmy.GMYClickBanner;
import pointGet.mission.gmy.GMYShindan;
import pointGet.mission.i2i.I2ISeiza;
import pointGet.mission.mop.MOPAnzan;
import pointGet.mission.mop.MOPChyosatai;
import pointGet.mission.mop.MOPClickBanner;
import pointGet.mission.mop.MOPNanyoubi;
import pointGet.mission.mop.MOPQuiz;
import pointGet.mission.mop.MOPShindan;
import pointGet.mission.mop.MOPUranai;
import pointGet.mission.osa.OSAAnzan;
import pointGet.mission.osa.OSAClickBanner;
import pointGet.mission.osa.OSANanyoubi;
import pointGet.mission.osa.OSAQuiz;
import pointGet.mission.osa.OSAShindan;
import pointGet.mission.osa.OSAUranai;
import pointGet.mission.pex.PEX4quiz;
import pointGet.mission.pex.PEXAnswer;
import pointGet.mission.pex.PEXChirachi;
import pointGet.mission.pex.PEXClickBanner;
import pointGet.mission.pex.PEXMekutte;
import pointGet.mission.pex.PEXNews;
import pointGet.mission.pex.PEXPectan;
import pointGet.mission.pex.PEXSearch;
import pointGet.mission.rin.RINClickBanner;

/**
 * get point from the point site
 *
 * @author 雅人
 *
 */
public class WebClicker extends PointGet {

	private static final String loadFilePath = "pGetWeb.properties";

	private static String[] wordList = null;

	private static boolean secondFlg = false;

	private static boolean thirdFlg = false;
	private static boolean forthFlg = false;
	private static boolean testFlag = false;
	private static String[] defoSiteList = new String[] { Define.PSITE_CODE_ECN, // ecnavi
			Define.PSITE_CODE_PEX, // pex
			Define.PSITE_CODE_GEN, // gendama
			Define.PSITE_CODE_GMY, // GetMoney
			Define.PSITE_CODE_RIN, // raktuten
			Define.PSITE_CODE_I2I, // i2i
			Define.PSITE_CODE_MOP, // moppi
			Define.PSITE_CODE_OSA, // osaifu
			Define.PSITE_CODE_PTO, // pointtown
	};
	private static String[] visitSites = null;
	private static String mName = "";
	private static ArrayList<Mission> missionList = new ArrayList<Mission>();

	protected static void init(String[] args) {
		PointGet.init();
		_setLogger("log4jweb.properties", WebClicker.class);
		// 設定ファイルをローカル変数に展開する
		Properties loadProps = Utille.getProp(loadFilePath);
		if (loadProps.isEmpty()) {
			return;
		}
		wordList = loadProps.getProperty("wordList").split(",");
		if (args.length > 0) {
			if (args[0].equals("1")) {
				secondFlg = true;
			} else if (args[0].equals("2")) {
				thirdFlg = true;
			} else if (args[0].equals("3")) {
				forthFlg = true;
			}
		}
		if (args.length > 1) {
			if (Arrays.asList(defoSiteList).contains(args[1])) {
				visitSites = new String[] { args[1] };
			} else {
				visitSites = defoSiteList;
			}
		} else {
			visitSites = defoSiteList;
		}
		// testFlag =true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init(args);
		logg.info("Start!!");
		ArrayList<String> missionArr = new ArrayList<String>();
		for (String site : visitSites) {
			if (thirdFlg && Arrays.asList(new String[] { Define.PSITE_CODE_ECN, // ecnavi
					Define.PSITE_CODE_PEX, // pex
					// Define.PSITE_CODE_GEN,// gendama
			}).contains(site)) {
				continue;
			} else if ((secondFlg || thirdFlg) && Arrays.asList(new String[] { Define.PSITE_CODE_GMY, // GetMoney
					Define.PSITE_CODE_RIN, // raktuten
					Define.PSITE_CODE_I2I,// i2i
			}).contains(site)) {
				continue;
			}
			goToClickSite(site, missionArr);
			missionArr.clear();
		}
		roopMisssion(missionList);
		logg.info("can not read properties!!");
	}

	private static void roopMisssion(ArrayList<Mission> missionList) {
		// roop
		if (missionList.size() > 0) {
			logg.info("roopStart!!");
			WebDriver driver = getWebDriver();
			int CompCnt = 0;
			while (true) {
				for (Mission mission : missionList) {
					if (mission.isCompFlag()) {
						logg.info("Complete!!");
						CompCnt++;
						continue;
					}
					mission.roopMission(driver);
				}
				if (CompCnt == missionList.size()) {
					break;
				}
				logg.info("again!!");
				Utille.sleep(306000);
			}
			driver.quit();
			logg.info("roopEnd!!");
		}
	}

	/**
	 *
	 * @param site
	 * @param strFlag
	 * @param mission
	 */
	public static void sub(String site, String strFlag, String mission) {
		init(new String[] { strFlag });
		logg.info("Start!!");
		ArrayList<String> missionArr = new ArrayList<String>();
		missionArr.add(mission);
		goToClickSite(site, missionArr);
		roopMisssion(missionList);
		logg.info("End!!");
	}

	/**
	 *
	 * @param siteType
	 */
	private static void goToClickSite(String siteType, ArrayList<String> missionArr) {
		WebDriver driver = getWebDriver();
		logg.info("■■■■■■missionSite[" + siteType + "]START■■■■■■");
		try {
			switch (siteType) {
			case Define.PSITE_CODE_GMY:
				goToClickGMY(driver, missionArr);
				break;
			case Define.PSITE_CODE_GEN:
				goToClickGEN(driver, missionArr);
				break;
			case Define.PSITE_CODE_ECN:
				goToClickECN(driver, missionArr);
				break;
			case Define.PSITE_CODE_MOP:
				goToClickMOP(driver, missionArr);
				break;
			case Define.PSITE_CODE_PEX:
				goToClickPEX(driver, missionArr);
				break;
			case Define.PSITE_CODE_OSA:
				goToClickOSA(driver, missionArr);
				break;
			case Define.PSITE_CODE_RIN:
				goToClickRIN(driver, missionArr);
				break;
			case Define.PSITE_CODE_I2I:
				goToClickI2I(driver, missionArr);
				break;
			default:
			}
		} catch (Throwable e) {
			e.printStackTrace();
			logg.error("-Throwable-------------------");
			logg.error(Utille.truncateBytes(e.getLocalizedMessage(), 500));
			logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 500));
			logg.error("-Throwable-------------------");
		}
		logg.info("■■■■■■missionSite[" + siteType + "]END■■■■■■");
		driver.quit();
	}

	/**
	 * rakuten
	 *
	 * @param driver
	 */
	private static void goToClickRIN(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			if (!secondFlg && !thirdFlg) {// 1日1回
				missions.add(Define.strRINClickBanner);
			}
		}
		for (String mission : missions) {
			switch (mission) {
			case Define.strRINClickBanner:
				// ■クリックバナー(楽天)
				Mission RINClickBanner = new RINClickBanner(logg, commonProps);
				RINClickBanner.exePrivateMission(driver);
				break;
			default:
			}
		}
	}

	/**
	 *
	 * @param driver
	 */
	private static void goToClickI2I(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			if (!secondFlg && !thirdFlg) {// 1日1回
				missions.add(Define.strI2ISeiza);
			}
		}
		for (String mission : missions) {
			switch (mission) {
			case Define.strI2ISeiza:
				// ■星座占い
				Mission I2ISeiza = new I2ISeiza(logg, commonProps);
				I2ISeiza.exePrivateMission(driver);
				break;
			default:
			}
		}
	}

	/**
	 *
	 * @param driver
	 */
	private static void goToClickOSA(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			missions.add(Define.strOSAQuiz);
			if (!secondFlg && !thirdFlg) {// 1日1回
				missions.add(Define.strOSAClickBanner);
				missions.add(Define.strOSAShindan);
				missions.add(Define.strOSAUranai);
			}
			if (!thirdFlg) {
				missions.add(Define.strOSANanyoubi);
				missions.add(Define.strOSAAnzan);
			}
		}
		for (String mission : missions) {
			switch (mission) {
			case Define.strOSAQuiz:
				// ■daily quiz
				Mission OSAQuiz = new OSAQuiz(logg, commonProps);
				OSAQuiz.exeRoopMission(driver);
				break;
			case Define.strOSAClickBanner:
				// ■クリックゴールド
				Mission OSAClickBanner = new OSAClickBanner(logg, commonProps);
				OSAClickBanner.exePrivateMission(driver);
				break;
			case Define.strOSAShindan:
				// ■毎日診断
				Mission OSAShindan = new OSAShindan(logg, commonProps);
				OSAShindan.exePrivateMission(driver);
				break;
			case Define.strOSAUranai:
				// ■OSA星座
				Mission OSAUranai = new OSAUranai(logg, commonProps);
				OSAUranai.exePrivateMission(driver);
				break;
			case Define.strOSANanyoubi:
				// ■OSA何曜日
				Mission OSANanyoubi = new OSANanyoubi(logg, commonProps);
				OSANanyoubi.exePrivateMission(driver);
				break;
			case Define.strOSAAnzan:
				// ■OSA暗算
				Mission OSAAnzan = new OSAAnzan(logg, commonProps);
				OSAAnzan.exePrivateMission(driver);
				break;
			default:
			}
		}
	}

	/**
	 *
	 * @param driver
	 */
	private static void goToClickMOP(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			missions.add(Define.strMOPQuiz);
			if (!secondFlg && !thirdFlg) {// 1日1回
				missions.add(Define.strMOPClickBanner);
				missions.add(Define.strMOPShindan);
				missions.add(Define.strMOPChyosatai);
				missions.add(Define.strMOPUranai);
			}
			if (!thirdFlg) {
				missions.add(Define.strMOPNanyoubi);
				missions.add(Define.strMOPAnzan);
			}
		}
		for (String mission : missions) {
			switch (mission) {
			case Define.strMOPQuiz:
				// ■モッピークイズ
				Mission MOPQuiz = new MOPQuiz(logg, commonProps);
				MOPQuiz.exeRoopMission(driver);
				break;
			case Define.strMOPClickBanner:
				// ■クリックで貯める
				Mission MOPClickBanner = new MOPClickBanner(logg, commonProps);
				MOPClickBanner.exePrivateMission(driver);
				break;
			case Define.strMOPShindan:
				// ■毎日診断
				Mission MOPShindan = new MOPShindan(logg, commonProps);
				MOPShindan.exePrivateMission(driver);
				break;
			case Define.strMOPChyosatai:
				// ■トキメキ調査隊
				Mission MOPChyosatai = new MOPChyosatai(logg, commonProps);
				MOPChyosatai.exePrivateMission(driver);
				break;
			case Define.strMOPUranai:
				// ■MOP星座
				Mission MOPUranai = new MOPUranai(logg, commonProps);
				MOPUranai.exePrivateMission(driver);
				break;
			case Define.strMOPNanyoubi:
				// ■MOP何曜日
				Mission MOPNanyoubi = new MOPNanyoubi(logg, commonProps);
				MOPNanyoubi.exePrivateMission(driver);
				break;
			case Define.strMOPAnzan:
				// ■MOP暗算
				Mission MOPAnzan = new MOPAnzan(logg, commonProps);
				MOPAnzan.exePrivateMission(driver);
				break;
			default:
			}
		}
	}

	/**
	 *
	 * @param driver
	 */
	private static void goToClickPEX(WebDriver driver, ArrayList<String> missions) {

		LoginSite.login(Define.PSITE_CODE_PEX, driver, logg);
		// 以下1日回
		if (!secondFlg && !thirdFlg) {
			mName = "■rakutenバナー";
			driver.get("http://pex.jp");
			String selector = "div>div>div>a>img[src*='image.infoseek.rakuten.co.jp']";
			if (isExistEle(driver, selector)) {
				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
				eleList.get(0).click();
				Utille.sleep(3000);
			} else {
				logg.warn(mName + "]なかったよ...");
			}
		}
		if (missions.size() == 0) {
			logg.info("kitano");
			missions.add(Define.strGMYShindan);
			if (!thirdFlg) { // 1日2回
				missions.add(Define.strPEX4quiz);
				missions.add(Define.strPEXChirachi);
				missions.add(Define.strPEXAnswer);
			}
			if (!secondFlg && !thirdFlg) {
				missions.add(Define.strPEXMekutte);
				missions.add(Define.strPEXClickBanner);
				missions.add(Define.strPEXNews);
				missions.add(Define.strPEXPectan);
				missions.add(Define.strPEXSearch);
			}
		}
		for (String mission : missions) {
			switch (mission) {
			case Define.strPEX4quiz: // ■ポイントクイズ
				Mission PEX4quiz = new PEX4quiz(logg, commonProps);
				PEX4quiz.exePrivateMission(driver);
				break;
			case Define.strPEXChirachi: // ■オリチラ
				Mission PEXChirachi = new PEXChirachi(logg, commonProps);
				PEXChirachi.exePrivateMission(driver);
				break;
			case Define.strPEXAnswer: // ■みんなのアンサー
				Mission PEXAnswer = new PEXAnswer(logg, commonProps);
				PEXAnswer.exePrivateMission(driver);
				break;
			case Define.strPEXMekutte: // ■めくってシール
				Mission PEXMekutte = new PEXMekutte(logg, commonProps);
				PEXMekutte.exePrivateMission(driver);
				break;
			case Define.strPEXClickBanner:// ■クリックポイント
				Mission PEXClickBanner = new PEXClickBanner(logg, commonProps);
				PEXClickBanner.exePrivateMission(driver);
				break;
			case Define.strPEXNews: // ■毎日ニュース
				Mission PEXNews = new PEXNews(logg, commonProps);
				PEXNews.exePrivateMission(driver);
				break;
			case Define.strPEXPectan: // ■ぺく単
				Mission PexMissionPectan = new PEXPectan(logg, commonProps);
				PexMissionPectan.exeRoopMission(driver);
				missionList.add(PexMissionPectan);
				break;
			case Define.strPEXSearch: // ■ポイント検索
				Mission PexMissionSearch = new PEXSearch(logg, commonProps, wordList);
				PexMissionSearch.exeRoopMission(driver);
				missionList.add(PexMissionSearch);
				break;
			default:
			}
		}
		// // テスト中
		// PEXMitukete PEXMitukete = new PEXMitukete(logg, commonProps);
		// PEXMitukete.exePrivateMission(driver);
	}

	/**
	 *
	 * @param driver
	 */
	private static void goToClickECN(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			// 1日2回
			if (!thirdFlg) {
				missions.add(Define.strECNChirachi);
			}
			if (!secondFlg && !thirdFlg) {// 1日1回
				missions.add(Define.strECNGaragara);
				missions.add(Define.strECNNews);
				missions.add(Define.strECNTellmeWhich);
				missions.add(Define.strECNDron);
				missions.add(Define.strECNClickBokin);
				missions.add(Define.strECNSearchBokin);
				missions.add(Define.strECNWebSearche);
				missions.add(Define.strECNChinjyu);
			}
		}
		for (String mission : missions) {
			switch (mission) {
			case Define.strECNGaragara: // ■ガラガラ
				Mission ECNGaragara = new ECNGaragara(logg, commonProps);
				ECNGaragara.exePrivateMission(driver);
				break;
			case Define.strECNNews: // ■毎日ニュース
				Mission ECNNews = new ECNNews(logg, commonProps);
				ECNNews.exePrivateMission(driver);
				break;
			case Define.strECNTellmeWhich: // ■教えてどっち
				Mission ECNTellmeWhich = new ECNTellmeWhich(logg, commonProps);
				ECNTellmeWhich.exePrivateMission(driver);
				break;
			case Define.strECNDron: // ■ドロンバナークリック2種
				Mission ECNDron = new ECNDron(logg, commonProps);
				ECNDron.exePrivateMission(driver);
				break;
			case Define.strECNClickBokin: // ■クリック募金
				Mission ECNClickBokin = new ECNClickBokin(logg, commonProps);
				ECNClickBokin.exePrivateMission(driver);
				break;
			case Define.strECNSearchBokin: // ■検索募金
				Mission ECNSearchBokin = new ECNSearchBokin(logg, commonProps, wordList);
				ECNSearchBokin.exePrivateMission(driver);
				break;
			case Define.strECNWebSearche: // ■検索募金
				Mission ECNWebSearche = new ECNWebSearche(logg, commonProps, wordList);
				ECNWebSearche.exePrivateMission(driver);
				break;
			case Define.strECNChinjyu: // ■珍獣先生
				Mission EcnMissionChinjyu = new ECNChinjyu(logg, commonProps);
				EcnMissionChinjyu.roopMission(driver);
				missionList.add(EcnMissionChinjyu);
				break;
			case Define.strECNChirachi: // ■チラシ
				Mission ECNChirachi = new ECNChirachi(logg, commonProps);
				ECNChirachi.exePrivateMission(driver);
				break;
			default:
			}
		}
	}

	/**
	 *
	 * @param driver
	 * @param targetMission
	 */
	public static void goToClickGMY(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			missions.add(Define.strGMYShindan);
			if (!secondFlg && !thirdFlg) {
				missions.add(Define.strGMYClickBanner);
				missions.add(Define.strGMYChirachi);
			}
		}
		for (String mission : missions) {
			switch (mission) {
			case Define.strGMYShindan:
				// ■毎日診断
				Mission GMYShindan = new GMYShindan(logg, commonProps);
				GMYShindan.exePrivateMission(driver);
				break;
			case Define.strGMYClickBanner:
				// ■clipoバナー
				Mission GMYClickBanner = new GMYClickBanner(logg, commonProps);
				GMYClickBanner.exePrivateMission(driver);
				break;
			case Define.strGMYChirachi:
				// ■チラシ
				Mission GMYChirachi = new GMYChirachi(logg, commonProps);
				GMYChirachi.exePrivateMission(driver);
				break;
			default:
			}
		}
	}

	/**
	 *
	 * @param driver
	 */
	private static void goToClickGEN(WebDriver driver, ArrayList<String> missions) {
		// // ■もりもり囲め
		// Mission GENMorimoriKakome = new GENMorimoriKakome(logg, commonProps);
		// GENMorimoriKakome.exePrivateMission(driver);
		// if (testFlag) {
		// return;
		// }
		if (missions.size() == 0) {
			missions.add(Define.strGENPointStar);
			if (!secondFlg && !thirdFlg) {
				missions.add(Define.strGENClickBanner);
				missions.add(Define.strGENShindan);
				missions.add(Define.strGENUranai);
			}
		}
		for (String mission : missions) {
			switch (mission) {
			case Define.strGENPointStar:
				// ■ポイントの森(star)
				Mission GENPointStar = new GENPointStar(logg, commonProps);
				GENPointStar.exePrivateMission(driver);
				break;
			case Define.strGENClickBanner:
				// ■ポイントの森(クリック)
				Mission GENClickBanner = new GENClickBanner(logg, commonProps);
				GENClickBanner.exePrivateMission(driver);
				break;
			case Define.strGENShindan:
				// ■毎日診断
				Mission GENShindan = new GENShindan(logg, commonProps);
				GENShindan.exePrivateMission(driver);
				break;
			case Define.strGENUranai:
				// ■毎日診断
				Mission GENUranai = new GENUranai(logg, commonProps);
				GENUranai.exePrivateMission(driver);
				break;
			default:
			}
		}
	}
}
