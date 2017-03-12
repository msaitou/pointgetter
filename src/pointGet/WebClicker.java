package pointGet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.mission.Mission;
import pointGet.mission.cit.CITClickBanner;
import pointGet.mission.cit.CITPriceChyosatai;
import pointGet.mission.cit.CITShindan;
import pointGet.mission.cit.CITToidas;
import pointGet.mission.cri.CRIAnk;
import pointGet.mission.cri.CRIManga;
import pointGet.mission.ecn.ECNChinjyu;
import pointGet.mission.ecn.ECNChirachi;
import pointGet.mission.ecn.ECNClickBokin;
import pointGet.mission.ecn.ECNDron;
import pointGet.mission.ecn.ECNGaragara;
import pointGet.mission.ecn.ECNNews;
import pointGet.mission.ecn.ECNSearchBokin;
import pointGet.mission.ecn.ECNTellmeWhich;
import pointGet.mission.ecn.ECNWebSearche;
import pointGet.mission.gen.GENChirachi;
import pointGet.mission.gen.GENClickBanner;
import pointGet.mission.gen.GENManga;
import pointGet.mission.gen.GENPointStar;
import pointGet.mission.gen.GENShindan;
import pointGet.mission.gen.GENUranai;
import pointGet.mission.gmy.GMYChirachi;
import pointGet.mission.gmy.GMYClickBanner;
import pointGet.mission.gmy.GMYPriceChyosatai;
import pointGet.mission.gmy.GMYShindan;
import pointGet.mission.gmy.GMYToidas;
import pointGet.mission.hap.HAPManga;
import pointGet.mission.i2i.I2IColum;
import pointGet.mission.i2i.I2ISeiza;
import pointGet.mission.mob.MOBAnzan;
import pointGet.mission.mob.MOBChirachi;
import pointGet.mission.mob.MOBClickBanner;
import pointGet.mission.mob.MOBCountTimer;
import pointGet.mission.mob.MOBEnglishTest;
import pointGet.mission.mob.MOBNanyoubi;
import pointGet.mission.mob.MOBQuiz;
import pointGet.mission.mop.MOPAnzan;
import pointGet.mission.mop.MOPChirachi;
import pointGet.mission.mop.MOPChyosatai;
import pointGet.mission.mop.MOPClickBanner;
import pointGet.mission.mop.MOPCountTimer;
import pointGet.mission.mop.MOPEnglishTest;
import pointGet.mission.mop.MOPManga;
import pointGet.mission.mop.MOPNanyoubi;
import pointGet.mission.mop.MOPQuiz;
import pointGet.mission.mop.MOPShindan;
import pointGet.mission.mop.MOPUranai;
import pointGet.mission.osa.OSAAnzan;
import pointGet.mission.osa.OSAChirachi;
import pointGet.mission.osa.OSAClickBanner;
import pointGet.mission.osa.OSACountTimer;
import pointGet.mission.osa.OSAEnglishTest;
import pointGet.mission.osa.OSAManga;
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
import pointGet.mission.pic.PICClickBanner;
import pointGet.mission.pic.PICPriceChyosatai;
import pointGet.mission.pic.PICShindan;
import pointGet.mission.pic.PICUranai;
import pointGet.mission.pil.PILClickBanner;
import pointGet.mission.pil.PILManga;
import pointGet.mission.pil.PILQuiz;
import pointGet.mission.pil.PILShindanAnk;
import pointGet.mission.pil.PILUranai;
import pointGet.mission.pmo.PMOChyosatai;
import pointGet.mission.pst.PSTManga;
import pointGet.mission.pst.PSTQuiz;
import pointGet.mission.pst.PSTShindanAnk;
import pointGet.mission.pst.PSTUranai;
import pointGet.mission.pto.PTOClickCorner;
import pointGet.mission.pto.PTODaily;
import pointGet.mission.pto.PTOKuji;
import pointGet.mission.pto.PTOManga;
import pointGet.mission.pto.PTOUranai;
import pointGet.mission.rin.RINClickBanner;
import pointGet.mission.sug.SUGColum;
import pointGet.mission.sug.SUGManga;
import pointGet.mission.sug.SUGQuiz;
import pointGet.mission.sug.SUGQuiz2;
import pointGet.mission.sug.SUGUranai;

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
	@SuppressWarnings("unused")
	private static boolean forthFlg = false;
	@SuppressWarnings("unused")
	private static boolean testFlag = false;
	private static String[] defoSiteList = new String[] {
			Define.PSITE_CODE_PIC, // PointInCome
			Define.PSITE_CODE_SUG, // SUGUTAMA
			Define.PSITE_CODE_CIT, // CHANCEIT
			Define.PSITE_CODE_PMO, // PointMonkey
			Define.PSITE_CODE_PIL, // PointIsland
			Define.PSITE_CODE_PST, // PointStudium
			Define.PSITE_CODE_RIN, // raktuten
			Define.PSITE_CODE_I2I, // i2i
			Define.PSITE_CODE_MOP, // moppi
			Define.PSITE_CODE_ECN, // ecnavi
			Define.PSITE_CODE_PEX, // pex
			Define.PSITE_CODE_GEN, // gendama
			Define.PSITE_CODE_GMY, // GetMoney
			Define.PSITE_CODE_OSA, // osaifu
			Define.PSITE_CODE_PTO, // pointtown
			Define.PSITE_CODE_MOB, // MOBATOKU
			Define.PSITE_CODE_CRI, // ちょびリッチ
			Define.PSITE_CODE_HAP, // ハピタス
	};
	private static String[] visitSites = null;
	private static String mName = "";
	private static ArrayList<Mission> missionList = new ArrayList<Mission>();
	public static long lastRoopTime = 0;
	public static boolean isDoingFlag = false;

	protected static void init(String[] args) {
		PointGet.init(WebClicker.class.getSimpleName());
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
				visitSites = new String[0];
			}
		} else {
			visitSites = defoSiteList;
		}
		// testFlag =true;
	}

	/**
	 * @param args
	 */
	public static void exeSite(String[] args, ArrayList<String> missionArr) {
		init(args);
		logg.info("======exeSiteStart======");
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
		logg.info("======exeSite End ======");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init(args);
		logg.info("======mainStart======");
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
		logg.info("======mainEND======");
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
				if (isDoingFlag) {
					logg.info("roopEnd!! isDoingFlag:true");
					break;
				} else {
					if (CompCnt >= missionList.size()) {
						logg.info("roopEnd!! isDoingFlag:false");
						break;
					}
					logg.info("CompCnt:" + CompCnt + "  missionList.size():" + missionList.size());
					Utille.sleep(306000);
				}
				logg.info("again!!");
			}
			driver.quit();
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
		logg.info("======subStart======");
		ArrayList<String> missionArr = new ArrayList<String>();
		missionArr.add(mission);
		goToClickSite(site, missionArr);
		roopMisssion(missionList);
		logg.info("======subEnd======");
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
				case Define.PSITE_CODE_PTO:
					goToClickPTO(driver, missionArr);
					break;
				case Define.PSITE_CODE_MOB:
					goToClickMOB(driver, missionArr);
					break;
				case Define.PSITE_CODE_PIC:
					goToClickPIC(driver, missionArr);
					break;
				case Define.PSITE_CODE_SUG:
					goToClickSUG(driver, missionArr);
					break;
				case Define.PSITE_CODE_PST:
					goToClickPST(driver, missionArr);
					break;
				case Define.PSITE_CODE_PIL:
					goToClickPIL(driver, missionArr);
					break;
				case Define.PSITE_CODE_CIT:
					goToClickCIT(driver, missionArr);
					break;
				case Define.PSITE_CODE_PMO:
					goToClickPMO(driver, missionArr);
					break;
				case Define.PSITE_CODE_CRI:
					goToClickCRI(driver, missionArr);
					break;
				case Define.PSITE_CODE_HAP:
					goToClickHAP(driver, missionArr);
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
				missions.add(Define.strI2IColum);
			}
		}
		for (String mission : missions) {
			switch (mission) {
				case Define.strI2ISeiza: // ■星座占い
					Mission I2ISeiza = new I2ISeiza(logg, commonProps);
					I2ISeiza.exePrivateMission(driver);
					break;
				case Define.strI2IColum: // ■コラム
					Mission I2IColum = new I2IColum(logg, commonProps);
					I2IColum.exePrivateMission(driver);
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
				missions.add(Define.strOSAManga);
			}
			if (secondFlg || thirdFlg) {
				missions.add(Define.strOSANanyoubi);
				missions.add(Define.strOSAAnzan);
				missions.add(Define.strOSAChirachi);
				missions.add(Define.strOSAEnglishTest);
				missions.add(Define.strOSACountTimer);
			}
		}
		String se = "ul.userinfo";
		driver.get("http://osaifu.com/");
		if (!isExistEle(driver, se)) {
			// login!!
			LoginSite.login(Define.PSITE_CODE_OSA, driver, logg);
		}
		for (String mission : missions) {
			switch (mission) {
				case Define.strOSAQuiz: // ■daily quiz
					Mission OSAQuiz = new OSAQuiz(logg, commonProps);
					OSAQuiz.exeRoopMission(driver);
					break;
				case Define.strOSAClickBanner: // ■クリックゴールド
					Mission OSAClickBanner = new OSAClickBanner(logg, commonProps);
					OSAClickBanner.exePrivateMission(driver);
					break;
				case Define.strOSAShindan: // ■毎日診断
					Mission OSAShindan = new OSAShindan(logg, commonProps);
					OSAShindan.exePrivateMission(driver);
					break;
				case Define.strOSAUranai: // ■OSA星座
					Mission OSAUranai = new OSAUranai(logg, commonProps);
					OSAUranai.exePrivateMission(driver);
					break;
				case Define.strOSANanyoubi: // ■OSA何曜日
					Mission OSANanyoubi = new OSANanyoubi(logg, commonProps);
					OSANanyoubi.exeRoopMission(driver);
					break;
				case Define.strOSAAnzan: // ■OSA暗算
					Mission OSAAnzan = new OSAAnzan(logg, commonProps);
					OSAAnzan.exeRoopMission(driver);
					break;
				case Define.strOSAChirachi: // ■チラシ
					Mission OSAChirachi = new OSAChirachi(logg, commonProps);
					OSAChirachi.exePrivateMission(driver);
					break;
				case Define.strOSAEnglishTest: // ■英単語TEST
					Mission OSAEnglishTest = new OSAEnglishTest(logg, commonProps);
					OSAEnglishTest.exeRoopMission(driver);
					break;
				case Define.strOSAManga: // ■漫画
					Mission OSAManga = new OSAManga(logg, commonProps);
					OSAManga.exePrivateMission(driver);
					break;
				case Define.strOSACountTimer: // ■CountTimer
					Mission OSACountTimer = new OSACountTimer(logg, commonProps);
					OSACountTimer.exeRoopMission(driver);
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
				missions.add(Define.strMOPManga);
			}
			if (secondFlg || thirdFlg) {
				missions.add(Define.strMOPChirachi);
				missions.add(Define.strMOPNanyoubi);
				missions.add(Define.strMOPAnzan);
				missions.add(Define.strMOPEnglishTest);
				missions.add(Define.strMOPCountTimer);
			}
		}
		driver.get("http://pc.moppy.jp/");
		String se = "div#preface>ul.pre__login__inner";
		if (!isExistEle(driver, se)) {
			// login!!
			LoginSite.login(Define.PSITE_CODE_MOP, driver, logg);
		}
		for (String mission : missions) {
			switch (mission) {
				case Define.strMOPQuiz: // ■モッピークイズ
					Mission MOPQuiz = new MOPQuiz(logg, commonProps);
					MOPQuiz.exeRoopMission(driver);
					break;
				case Define.strMOPClickBanner: // ■クリックで貯める
					Mission MOPClickBanner = new MOPClickBanner(logg, commonProps);
					MOPClickBanner.exePrivateMission(driver);
					break;
				case Define.strMOPShindan: // ■毎日診断
					Mission MOPShindan = new MOPShindan(logg, commonProps);
					MOPShindan.exePrivateMission(driver);
					break;
				case Define.strMOPChyosatai: // ■トキメキ調査隊
					Mission MOPChyosatai = new MOPChyosatai(logg, commonProps);
					MOPChyosatai.exePrivateMission(driver);
					break;
				case Define.strMOPUranai: // ■MOP星座
					Mission MOPUranai = new MOPUranai(logg, commonProps);
					MOPUranai.exePrivateMission(driver);
					break;
				case Define.strMOPChirachi: // ■MOPチラシ
					Mission MOPChirachi = new MOPChirachi(logg, commonProps);
					MOPChirachi.exePrivateMission(driver);
					break;
				case Define.strMOPNanyoubi: // ■MOP何曜日
					Mission MOPNanyoubi = new MOPNanyoubi(logg, commonProps);
					MOPNanyoubi.exeRoopMission(driver);
					break;
				case Define.strMOPAnzan: // ■MOP暗算
					Mission MOPAnzan = new MOPAnzan(logg, commonProps);
					MOPAnzan.exeRoopMission(driver);
					break;
				case Define.strMOPEnglishTest: // ■英単語TEST
					Mission MOPEnglishTest = new MOPEnglishTest(logg, commonProps);
					MOPEnglishTest.exeRoopMission(driver);
					break;
				case Define.strMOPCountTimer: // ■CountTimer
					Mission MOPCountTimer = new MOPCountTimer(logg, commonProps);
					MOPCountTimer.exeRoopMission(driver);
					break;
				case Define.strMOPManga: // ■漫画
					Mission MOPManga = new MOPManga(logg, commonProps);
					MOPManga.exePrivateMission(driver);
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
		if (missions.size() == 0) {
			missions.add(Define.strGMYShindan);
			if (!thirdFlg) { // 1日2回
				missions.add(Define.strPEX4quiz);
				missions.add(Define.strPEXChirachi);
				missions.add(Define.strPEXAnswer);
			}
			if (!secondFlg && !thirdFlg) {
				missions.add(Define.strPEXNews);
				missions.add(Define.strPEXClickBanner);
				missions.add(Define.strPEXMekutte);
				missions.add(Define.strPEXPectan);
				missions.add(Define.strPEXSearch);
			}
		}
		driver.get("http://pex.jp");
		String sel = "dd.user_pt.fw_b>span.fw_b";
		if (!isExistEle(driver, sel)) {
			// Login
			LoginSite.login(Define.PSITE_CODE_PEX, driver, logg);
		}
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
				missions.add(Define.strGMYPriceChyosatai);
				missions.add(Define.strGMYToidas);
			}
		}
		for (String mission : missions) {
			switch (mission) {
				case Define.strGMYShindan: // ■毎日診断
					Mission GMYShindan = new GMYShindan(logg, commonProps);
					GMYShindan.exePrivateMission(driver);
					break;
				case Define.strGMYClickBanner: // ■clipoバナー
					Mission GMYClickBanner = new GMYClickBanner(logg, commonProps);
					GMYClickBanner.exePrivateMission(driver);
					break;
				case Define.strGMYChirachi: // ■チラシ
					Mission GMYChirachi = new GMYChirachi(logg, commonProps);
					GMYChirachi.exePrivateMission(driver);
					break;
				case Define.strGMYPriceChyosatai: // ■プライス調査隊
					Mission GMYPriceChyosatai = new GMYPriceChyosatai(logg, commonProps);
					GMYPriceChyosatai.exePrivateMission(driver);
					break;
				case Define.strGMYToidas: // ■GMYトイダス
					Mission GMYToidas = new GMYToidas(logg, commonProps);
					GMYToidas.exePrivateMission(driver);

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
				missions.add(Define.strGENChirachi);
				missions.add(Define.strGENManga);
			}
		}
		for (String mission : missions) {
			switch (mission) {
				case Define.strGENPointStar: // ■ポイントの森(star)
					Mission GENPointStar = new GENPointStar(logg, commonProps);
					GENPointStar.exePrivateMission(driver);
					break;
				case Define.strGENClickBanner: // ■ポイントの森(クリック)
					Mission GENClickBanner = new GENClickBanner(logg, commonProps);
					GENClickBanner.exePrivateMission(driver);
					break;
				case Define.strGENShindan: // ■毎日診断
					Mission GENShindan = new GENShindan(logg, commonProps);
					GENShindan.exePrivateMission(driver);
					break;
				case Define.strGENUranai: // ■占い
					Mission GENUranai = new GENUranai(logg, commonProps);
					GENUranai.exePrivateMission(driver);
					break;
				case Define.strGENChirachi: // ■チラシ
					Mission GENChirachi = new GENChirachi(logg, commonProps);
					GENChirachi.exePrivateMission(driver);
					break;
				case Define.strGENManga: // ■漫画
					Mission GENManga = new GENManga(logg, commonProps);
					GENManga.exePrivateMission(driver);
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
	public static void goToClickPTO(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			if (!secondFlg && !thirdFlg) {
				missions.add(Define.strPTOClickCorner);
				missions.add(Define.strPTOUranai);
				missions.add(Define.strPTOKuji);
				missions.add(Define.strPTODaily);
				missions.add(Define.strPTOManga);
			}
		}
		String sel = "li.point>a>strong";
		driver.get("https://www.pointtown.com/ptu/index.do");
		if (!isExistEle(driver, sel)) {
			// login!!
			LoginSite.login(Define.PSITE_CODE_PTO, driver, logg);
		}
		for (String mission : missions) {
			switch (mission) {
				case Define.strPTOClickCorner: // ■クリックコーナー
					Mission PTOClickCorner = new PTOClickCorner(logg, commonProps);
					PTOClickCorner.exePrivateMission(driver);
					break;
				case Define.strPTOUranai: // ■占い
					Mission PTOUranai = new PTOUranai(logg, commonProps);
					PTOUranai.exePrivateMission(driver);
					break;
				case Define.strPTOKuji: // ■くじ
					Mission PTOKuji = new PTOKuji(logg, commonProps);
					PTOKuji.exePrivateMission(driver);
					break;
				case Define.strPTODaily: // ■デイリー
					Mission PTODaily = new PTODaily(logg, commonProps);
					PTODaily.exePrivateMission(driver);
					break;
				case Define.strPTOManga: // ■漫画
					Mission PTOManga = new PTOManga(logg, commonProps);
					PTOManga.exePrivateMission(driver);
					break;
				default:
			}
		}
	}

	/**
	 *
	 * @param driver
	 */
	private static void goToClickMOB(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			missions.add(Define.strMOBQuiz);
			if (!secondFlg && !thirdFlg) {// 1日1回
				missions.add(Define.strMOBClickBanner);
			}
			if (secondFlg || thirdFlg) {
				missions.add(Define.strMOBChirachi);
				missions.add(Define.strMOBNanyoubi);
				missions.add(Define.strMOBAnzan);
				missions.add(Define.strMOBEnglishTest);
				missions.add(Define.strMOBCountTimer);
			}
		}
		for (String mission : missions) {
			switch (mission) {
				case Define.strMOBQuiz: // ■モブクイズ
					Mission MOBQuiz = new MOBQuiz(logg, commonProps);
					MOBQuiz.exeRoopMission(driver);
					break;
				case Define.strMOBClickBanner: // ■クリックで貯める
					Mission MOBClickBanner = new MOBClickBanner(logg, commonProps);
					MOBClickBanner.exePrivateMission(driver);
					break;
				case Define.strMOBNanyoubi: // ■何曜日
					Mission MOBNanyoubi = new MOBNanyoubi(logg, commonProps);
					MOBNanyoubi.exeRoopMission(driver);
					break;
				case Define.strMOBAnzan: // ■暗算
					Mission MOBAnzan = new MOBAnzan(logg, commonProps);
					MOBAnzan.exeRoopMission(driver);
					break;
				case Define.strMOBChirachi: // ■チラシ
					Mission MOBChirachi = new MOBChirachi(logg, commonProps);
					MOBChirachi.exePrivateMission(driver);
					break;
				case Define.strMOBEnglishTest: // ■英単語TEST
					Mission MOBEnglishTest = new MOBEnglishTest(logg, commonProps);
					MOBEnglishTest.exeRoopMission(driver);
					break;
				case Define.strMOBCountTimer: // ■CountTimer
					Mission MOBCountTimer = new MOBCountTimer(logg, commonProps);
					MOBCountTimer.exeRoopMission(driver);
					break;
				default:
			}
		}
	}

	/**
	 *
	 * @param driver
	 */
	private static void goToClickPIC(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			//			missions.add(Define.strMOBQuiz);
			if (!secondFlg && !thirdFlg) {// 1日1回
				missions.add(Define.strPICUranai);
				missions.add(Define.strPICClickBanner);
				missions.add(Define.strPICPriceChyosatai);
				missions.add(Define.strPICShindan);
			}
			//			if (secondFlg || thirdFlg) {
			//				missions.add(Define.strMOBNanyoubi);
			//				missions.add(Define.strMOBAnzan);
			//			}
		}
		String sel = "ul.header_wrap.clearfix>li.right>span.name";
		driver.get("http://pointi.jp/"); // http://pointi.jp/
		if (!isExistEle(driver, sel)) {
			// login!!
			LoginSite.login(Define.PSITE_CODE_PIC, driver, logg);
		}
		for (String mission : missions) {
			switch (mission) {
				case Define.strPICClickBanner: // ■PICクリックバナー
					Mission PICClickBanner = new PICClickBanner(logg, commonProps);
					PICClickBanner.exePrivateMission(driver);
					break;
				case Define.strPICUranai: // ■占い
					Mission PICUranai = new PICUranai(logg, commonProps);
					PICUranai.exePrivateMission(driver);
					break;
				case Define.strPICPriceChyosatai: // ■PICPrice調査隊
					Mission PICPriceChyosatai = new PICPriceChyosatai(logg, commonProps);
					PICPriceChyosatai.exePrivateMission(driver);
					break;
				case Define.strPICShindan: // ■PIC毎日診断
					Mission PICShindan = new PICShindan(logg, commonProps);
					PICShindan.exePrivateMission(driver);
					break;
				default:
			}
		}
	}

	/**
	 *
	 * @param driver
	 */
	private static void goToClickSUG(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			missions.add(Define.strSUGQuiz);
			if (!secondFlg && !thirdFlg) {// 1日1回
				missions.add(Define.strSUGQuiz2);
				missions.add(Define.strSUGUranai);
				missions.add(Define.strSUGManga);
				missions.add(Define.strSUGColum);
			}
		}
		for (String mission : missions) {
			switch (mission) {
				case Define.strSUGQuiz: // ■SUGクイズ
					Mission SUGQuiz = new SUGQuiz(logg, commonProps);
					SUGQuiz.exeRoopMission(driver);
					break;
				case Define.strSUGQuiz2: // ■SUGクイズ2
					Mission SUGQuiz2 = new SUGQuiz2(logg, commonProps);
					SUGQuiz2.exeRoopMission(driver);
					break;
				case Define.strSUGUranai: // ■占い
					Mission SUGUranai = new SUGUranai(logg, commonProps);
					SUGUranai.exePrivateMission(driver);
					break;
				case Define.strSUGManga: // ■漫画
					Mission SUGManga = new SUGManga(logg, commonProps);
					SUGManga.exePrivateMission(driver);
					break;
				case Define.strSUGColum: // ■コラム
					Mission SUGColum = new SUGColum(logg, commonProps);
					SUGColum.exePrivateMission(driver);
					break;
				default:
			}
		}
	}

	/**
	 *
	 * @param driver
	 */
	private static void goToClickPST(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			missions.add(Define.strPSTQuiz);
			if (!secondFlg && !thirdFlg) {// 1日1回
				missions.add(Define.strPSTUranai);
				missions.add(Define.strPSTShindanAnk);
				missions.add(Define.strPSTManga);

			}
		}
		for (String mission : missions) {
			switch (mission) {
				case Define.strPSTQuiz: // ■PSTクイズ
					Mission PSTQuiz = new PSTQuiz(logg, commonProps);
					PSTQuiz.exeRoopMission(driver);
					break;
				case Define.strPSTUranai: // ■占い
					Mission PSTUranai = new PSTUranai(logg, commonProps);
					PSTUranai.exePrivateMission(driver);
					break;
				case Define.strPSTShindanAnk: // ■PST診断＆アンケート
					Mission PSTShindanAnk = new PSTShindanAnk(logg, commonProps);
					PSTShindanAnk.exeRoopMission(driver);
					break;
				case Define.strPSTManga: // ■PSTまんが　
					Mission PSTManga = new PSTManga(logg, commonProps);
					PSTManga.exePrivateMission(driver);
					break;

				default:
			}
		}
	}

	/**
	 *
	 * @param driver
	 */
	private static void goToClickPIL(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			missions.add(Define.strPILQuiz);
			if (!secondFlg && !thirdFlg) {// 1日1回
				missions.add(Define.strPILUranai);
				missions.add(Define.strPILClickBanner);
				missions.add(Define.strPILShindanAnk);
				missions.add(Define.strPILManga);
			}
		}
		// login!!
		LoginSite.login(Define.PSITE_CODE_PIL, driver, logg);
		for (String mission : missions) {
			switch (mission) {
				case Define.strPILClickBanner: // ■PILクリック
					Mission PILClickBanner = new PILClickBanner(logg, commonProps);
					PILClickBanner.exePrivateMission(driver);
					break;
				case Define.strPILQuiz: // ■PILクイズ
					Mission PILQuiz = new PILQuiz(logg, commonProps);
					PILQuiz.exeRoopMission(driver);
					break;
				case Define.strPILUranai: // ■PIL占い
					Mission PILUranai = new PILUranai(logg, commonProps);
					PILUranai.exePrivateMission(driver);
					break;
				case Define.strPILShindanAnk: // ■PIL診断＆アンケート
					Mission PILShindanAnk = new PILShindanAnk(logg, commonProps);
					PILShindanAnk.exeRoopMission(driver);
					break;
				case Define.strPILManga: // ■まんが
					Mission PILManga = new PILManga(logg, commonProps);
					PILManga.exePrivateMission(driver);
					break;
				default:
			}
		}
	}

	/**
	 *
	 * @param driver
	 */
	private static void goToClickCIT(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			if (!secondFlg && !thirdFlg) {// 1日1回
				missions.add(Define.strCITPriceChyosatai);
				missions.add(Define.strCITShindan);
				missions.add(Define.strCITToidas);
				missions.add(Define.strCITClickBanner);
			}
		}
		for (String mission : missions) {
			switch (mission) {
				case Define.strCITPriceChyosatai: // ■CITPrice調査隊
					Mission CITPriceChyosatai = new CITPriceChyosatai(logg, commonProps);
					CITPriceChyosatai.exePrivateMission(driver);
					break;
				case Define.strCITShindan: // ■CIT毎日診断
					Mission CITShindan = new CITShindan(logg, commonProps);
					CITShindan.exePrivateMission(driver);
					break;
				case Define.strCITToidas: // ■CITトイダス
					Mission CITToidas = new CITToidas(logg, commonProps);
					CITToidas.exePrivateMission(driver);
					break;
				case Define.strCITClickBanner: // ■CITクリックバナー
					Mission CITClickBanner = new CITClickBanner(logg, commonProps);
					CITClickBanner.exePrivateMission(driver);
					break;
				default:
			}
		}
	}

	/**
	 *
	 * @param driver
	 */
	private static void goToClickPMO(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			if (!secondFlg && !thirdFlg) {// 1日1回
				missions.add(Define.strPMOChyosatai);
			}
		}
		String sel = "span#ygps>span.pointEmphasis";
		driver.get("http://poimon.jp/");
		if (!isExistEle(driver, sel)) {
			// login!!
			LoginSite.login(Define.PSITE_CODE_PMO, driver, logg);
		}
		for (String mission : missions) {
			switch (mission) {
				case Define.strPMOChyosatai: // ■PMOPrice調査隊
					Mission PMOChyosatai = new PMOChyosatai(logg, commonProps);
					PMOChyosatai.exePrivateMission(driver);
					break;
				default:
			}
		}
	}

	/**
	 *
	 * @param driver
	 */
	private static void goToClickCRI(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			if (!secondFlg && !thirdFlg) {// 1日1回
				missions.add(Define.strCRIChirachi);
				missions.add(Define.strCRIManga);
			}
		}
		for (String mission : missions) {
			switch (mission) {
				case Define.strCRIChirachi: // ■アンケート
					Mission CRIAnk = new CRIAnk(logg, commonProps);
					CRIAnk.exePrivateMission(driver);
					break;
				case Define.strCRIManga: // ■漫画
					Mission CRIManga = new CRIManga(logg, commonProps);
					CRIManga.exePrivateMission(driver);
					break;
				default:
			}
		}
	}
	
	/**
	 *
	 * @param driver
	 */
	private static void goToClickHAP(WebDriver driver, ArrayList<String> missions) {
		if (missions.size() == 0) {
			if (!secondFlg && !thirdFlg) {// 1日1回
				missions.add(Define.strHAPManga);
			}
		}
		for (String mission : missions) {
			switch (mission) {
				case Define.strHAPManga: // ■漫画
					Mission HAPManga = new HAPManga(logg, commonProps);
					HAPManga.exePrivateMission(driver);
					break;
				default:
			}
		}
	}
}
