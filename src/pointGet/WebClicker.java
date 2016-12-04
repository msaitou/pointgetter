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
import pointGet.mission.gmy.GMYChirachi;
import pointGet.mission.gmy.GMYClickBanner;
import pointGet.mission.i2i.I2ISeiza;
import pointGet.mission.mop.MOPChyosatai;
import pointGet.mission.mop.MOPClickBanner;
import pointGet.mission.mop.MOPQuiz;
import pointGet.mission.osa.OSAClickBanner;
import pointGet.mission.osa.OSAQuiz;
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
			}
			else if (args[0].equals("2")) {
				thirdFlg = true;
			}
		}
	}

	// garagarahhクリックするものをログに出したほうが良い
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init(args);
		logg.info("Start!!");
		for (String site : new String[] {
				Define.PSITE_CODE_ECN, // ecnavi
				Define.PSITE_CODE_PEX, // pex
				Define.PSITE_CODE_GEN, // gendama
				Define.PSITE_CODE_GMY, // GetMoney
				Define.PSITE_CODE_RIN, // raktuten
				Define.PSITE_CODE_I2I, // i2i
				Define.PSITE_CODE_MOP, // moppi
				Define.PSITE_CODE_OSA, // osaifu
				Define.PSITE_CODE_PTO, // pointtown
		}) {
			if (thirdFlg && Arrays.asList(new String[] { Define.PSITE_CODE_ECN, // ecnavi
					Define.PSITE_CODE_PEX, // pex
					Define.PSITE_CODE_GEN,// gendama
			}).contains(site)) {
				continue;
			}
			else if ((secondFlg || thirdFlg) && Arrays.asList(new String[] { Define.PSITE_CODE_GMY, // GetMoney
					Define.PSITE_CODE_RIN, // raktuten
					Define.PSITE_CODE_I2I,// i2i
			}).contains(site)) {
				continue;
			}
			goToClickSite(site);
		}

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
		logg.info("can not read properties!!");
	}

	/**
	 * 
	 * @param siteType
	 */
	private static void goToClickSite(String siteType) {
		WebDriver driver = getWebDriver();
		logg.info("■■■■\r\n■■missionSite[" + siteType + "]START■■\r\n■■■■");
		try {
			switch (siteType) {
			case Define.PSITE_CODE_GMY:
				goToClickGMY(driver);
				break;
			case Define.PSITE_CODE_GEN:
				goToClickGEN(driver);
				break;
			case Define.PSITE_CODE_ECN:
				goToClickECN(driver);
				break;
			case Define.PSITE_CODE_MOP:
				goToClickMOP(driver);
				break;
			case Define.PSITE_CODE_PEX:
				goToClickPEX(driver);
				break;
			case Define.PSITE_CODE_OSA:
				goToClickOSA(driver);
				break;
			case Define.PSITE_CODE_RIN:
				goToClickRIN(driver);
				break;
			case Define.PSITE_CODE_I2I:
				goToClickI2I(driver);
				break;
			default:
			}
		} catch (Throwable e) {
			e.printStackTrace();
			logg.error("-Throwable-------------------");
			logg.error(e.getLocalizedMessage());
			logg.error(Utille.parseStringFromStackTrace(e));
			logg.error("-Throwable-------------------");
		}
		logg.info("■■■■\r\n■■missionSite[" + siteType + "]END■■\r\n■■■■");
		driver.quit();
	}

	/**
	 * rakuten
	 * 
	 * @param driver
	 */
	private static void goToClickRIN(WebDriver driver) {
		if (!secondFlg && !thirdFlg) { // 1日1回
			// ■クリックバナー(楽天)
			Mission RINClickBanner = new RINClickBanner(logg);
			RINClickBanner.exePrivateMission(driver);
		}
	}

	/**
	 * 
	 * @param driver
	 */
	private static void goToClickI2I(WebDriver driver) {
		if (!secondFlg && !thirdFlg) { // 1日1回
			// ■星座占い
			Mission I2ISeiza = new I2ISeiza(logg);
			I2ISeiza.exePrivateMission(driver);
		}
	}

	/**
	 * 
	 * @param driver
	 */
	private static void goToClickOSA(WebDriver driver) {
		if (!secondFlg && !thirdFlg) { // 1日1回
			// ■クリックゴールド
			Mission OSAClickBanner = new OSAClickBanner(logg);
			OSAClickBanner.exePrivateMission(driver);
		}
		if (true) {
			// ■daily quiz
			Mission OSAQuiz = new OSAQuiz(logg);
			OSAQuiz.exePrivateMission(driver);
		}
	}

	/**
	 * 
	 * @param driver
	 */
	private static void goToClickMOP(WebDriver driver) {
		if (!secondFlg && !thirdFlg) { // 1日1回
			// ■クリックで貯める
			Mission MOPClickBanner = new MOPClickBanner(logg);
			MOPClickBanner.exePrivateMission(driver);
			// ■トキメキ調査隊
			Mission MOPChyosatai = new MOPChyosatai(logg);
			MOPChyosatai.exePrivateMission(driver);
			// // ■毎日診断
			// Mission MOPShindan = new MOPShindan(logg);
			// MOPShindan.exePrivateMission(driver);
		}
		if (true) {
			// ■モッピークイズ
			Mission MOPQuiz = new MOPQuiz(logg);
			MOPQuiz.exePrivateMission(driver);
		}
		//		 // ■毎日診断
		//		 Mission MOPShindan = new MOPShindan(logg);
		//		 MOPShindan.exePrivateMission(driver);
	}

	/**
	 * 
	 * @param driver
	 */
	private static void goToClickPEX(WebDriver driver) {

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
			}
			else {
				logg.warn(mName + "]なかったよ...");
			}
			// ■ポイント検索
			Mission PexMissionSearch = new PEXSearch(logg, wordList);
			PexMissionSearch.exeRoopMission(driver);
			missionList.add(PexMissionSearch);
			// ■ぺく単
			Mission PexMissionPectan = new PEXPectan(logg);
			PexMissionPectan.exeRoopMission(driver);
			missionList.add(PexMissionPectan);
			// ■毎日ニュース
			Mission PEXNews = new PEXNews(logg);
			PEXNews.exePrivateMission(driver);
			// ■クリックポイント
			Mission PEXClickBanner = new PEXClickBanner(logg);
			PEXClickBanner.exePrivateMission(driver);
			// ■めくってシール
			Mission PEXMekutte = new PEXMekutte(logg);
			PEXMekutte.exePrivateMission(driver);
		}
		// 1日2回
		if (!thirdFlg) {
			// ■みんなのアンサー
			Mission PEXAnswer = new PEXAnswer(logg);
			PEXAnswer.exePrivateMission(driver);
			// ■ポイントクイズ
			Mission PEX4quiz = new PEX4quiz(logg);
			PEX4quiz.exePrivateMission(driver);
			// ■オリチラ
			Mission PEXChirachi = new PEXChirachi(logg);
			PEXChirachi.exePrivateMission(driver);
		}
		// // テスト中
		// PEXMitukete PEXMitukete = new PEXMitukete(logg);
		// PEXMitukete.exePrivateMission(driver);

	}

	/**
	 * 
	 * @param driver
	 */
	private static void goToClickECN(WebDriver driver) {
		// 1日2回
		if (!thirdFlg) {
			// ■チラシ
			Mission ECNChirachi = new ECNChirachi(logg);
			ECNChirachi.exePrivateMission(driver);
		}
		// 以下1日回
		if (!secondFlg && !thirdFlg) {
			// ■珍獣先生
			Mission EcnMissionChinjyu = new ECNChinjyu(logg);
			EcnMissionChinjyu.roopMission(driver);
			missionList.add(EcnMissionChinjyu);
			// ■検索募金
			Mission ECNWebSearche = new ECNWebSearche(logg, wordList);
			ECNWebSearche.exePrivateMission(driver);
			// ■検索募金
			Mission ECNSearchBokin = new ECNSearchBokin(logg, wordList);
			ECNSearchBokin.exePrivateMission(driver);
			// ■クリック募金
			Mission ECNClickBokin = new ECNClickBokin(logg);
			ECNClickBokin.exePrivateMission(driver);
			// ■ドロンバナークリック2種
			Mission ECNDron = new ECNDron(logg);
			ECNDron.exePrivateMission(driver);
			// ■教えてどっち
			Mission ECNTellmeWhich = new ECNTellmeWhich(logg);
			ECNTellmeWhich.exePrivateMission(driver);
			// ■毎日ニュース
			Mission ECNNews = new ECNNews(logg);
			ECNNews.exePrivateMission(driver);
			// ■ガラガラ
			Mission ECNGaragara = new ECNGaragara(logg);
			ECNGaragara.exePrivateMission(driver);
		}
	}

	/**
	 * 
	 * @param driver
	 */
	private static void goToClickGMY(WebDriver driver) {
		if (!secondFlg && !thirdFlg) {
			// ■clipoバナー
			Mission GMYClickBanner = new GMYClickBanner(logg);
			GMYClickBanner.exePrivateMission(driver);
			// ■チラシ
			Mission GMYChirachi = new GMYChirachi(logg);
			GMYChirachi.exePrivateMission(driver);
		}
	}

	/**
	 * 
	 * @param driver
	 */
	private static void goToClickGEN(WebDriver driver) {
		//		// ■もりもり囲め
		//		Mission GENMorimoriKakome = new GENMorimoriKakome(logg);
		//		GENMorimoriKakome.exePrivateMission(driver);

		if (!thirdFlg) {
			// ■ポイントの森(star)
			Mission GENPointStar = new GENPointStar(logg);
			GENPointStar.exePrivateMission(driver);
		}
		if (!secondFlg && !thirdFlg) {
			// ■ポイントの森(クリック)
			Mission GENClickBanner = new GENClickBanner(logg);
			GENClickBanner.exePrivateMission(driver);
		}
	}
}
