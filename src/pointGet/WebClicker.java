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
import pointGet.mission.ecn.ECNSearchBokin;
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init(args);
		logg.info("Start!!");
		for (String site : new String[] {
				Define.PSITE_CODE_ECN,// ecnavi
				Define.PSITE_CODE_PEX,// pex
				Define.PSITE_CODE_GEN,// gendama
				Define.PSITE_CODE_GMY,// GetMoney
				Define.PSITE_CODE_RIN,// raktuten
				Define.PSITE_CODE_I2I,// i2i
				Define.PSITE_CODE_MOP,// moppi
				Define.PSITE_CODE_OSA,// osaifu
				Define.PSITE_CODE_PTO,// pointtown
		}) {
			if (thirdFlg && Arrays.asList(new String[] {
					Define.PSITE_CODE_ECN,// ecnavi
					Define.PSITE_CODE_PEX,// pex
					Define.PSITE_CODE_GEN,// gendama
			}).contains(site)) {
				continue;
			}
			else if ((secondFlg || thirdFlg) && Arrays.asList(new String[] {
					Define.PSITE_CODE_GMY,// GetMoney
					Define.PSITE_CODE_RIN,// raktuten
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
		logg.info("■■■■\\r\\n■■missionSite[" + siteType + "]START■■\\r\\n■■■■");
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
		logg.info("■■■■\\r\\n■■missionSite[" + siteType + "]END■■\\r\\n■■■■");
		driver.quit();
	}

	/**
	 * rakuten
	 * 
	 * @param driver
	 */
	private static void goToClickRIN(WebDriver driver) {
		if (!secondFlg && !thirdFlg) { // 1日1回
			driver.get("https://www.rakuten-card.co.jp/e-navi/index.xhtml");
			String selector = "li#loginId>input#u";
			// ログイン画面であれば
			if (isExistEle(driver, selector)) {
				if (!pGetProps.containsKey("rin") || !pGetProps.get("rin").containsKey("loginid")
						|| !pGetProps.get("rin").containsKey("loginpass")) {
					return;
				}
				WebElement ele = driver.findElement(By.cssSelector(selector));
				ele.clear();
				ele.sendKeys(pGetProps.get("rin").get("loginid"));
				selector = "li#loginPw>input#p";
				ele = driver.findElement(By.cssSelector(selector));
				ele.clear();
				ele.sendKeys(pGetProps.get("rin").get("loginpass"));
				driver.findElement(By.cssSelector("input#loginButton")).click();
				Utille.sleep(5000);
			}
			driver.get(
					"https://www.rakuten-card.co.jp/e-navi/members/point/click-point/index.xhtml?l-id=enavi_all_submenu_clickpoint");
			Utille.sleep(5000);
			selector = "div.topArea.clearfix";
			if (isExistEle(driver, selector)) {
				String selector2 = "[alt='Check']";
				int size = driver.findElements(By.cssSelector(selector)).size();
				for (int i = 0; i < size; i++) {
					// document.querySelectorAll('[alt="Check"]')[1].parentNode.parentNode.parentNode.querySelectorAll('div.bnrBox img')[0];
					WebElement e = driver.findElements(By.cssSelector(selector)).get(i);
					if (isExistEle(e, selector2)) {
						e.findElement(By.cssSelector("a>img")).click();
						Utille.sleep(2000);
					}
				}
			}
			selector = "span#middleArea>ul>li";
			if (isExistEle(driver, selector)) {
				String selector2 = "[alt='Check']";
				int size = driver.findElements(By.cssSelector(selector)).size();
				for (int i = 0; i < size; i++) {
					WebElement e = driver.findElements(By.cssSelector(selector)).get(i);
					if (isExistEle(e, selector2)) {
						e.findElement(By.cssSelector("a>img")).click();
						Utille.sleep(2000);
					}
				}
			}
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
//			// ■毎日診断
//			Mission MOPShindan = new MOPShindan(logg);
//			MOPShindan.exePrivateMission(driver);
		}
		if (true) {
			// ■モッピークイズ
			Mission MOPQuiz = new MOPQuiz(logg);
			MOPQuiz.exePrivateMission(driver);
		}
	}

	private static void goToClickPEX(WebDriver driver) {
		String selector = "dd.user_pt.fw_b>span.fw_b";
		driver.get("https://pex.jp/user/point_passbook/all");
		if (!isExistEle(driver, selector)) {
			if (!pGetProps.containsKey("pex") || !pGetProps.get("pex").containsKey("loginid")
					|| !pGetProps.get("pex").containsKey("loginpass")) {
				logg.warn("2");
			}
			// ログイン画面
			String selector2 = "input#pex_user_login_email";
			if (isExistEle(driver, selector2)) {
				logg.warn("3");
				WebElement ele = driver.findElement(By.cssSelector(selector2));
				ele.clear();
				ele.sendKeys(pGetProps.get("pex").get("loginid"));
				ele = driver.findElement(By.cssSelector("input#pex_user_login_password"));
				ele.clear();
				ele.sendKeys(pGetProps.get("pex").get("loginpass"));
				driver.findElement(By.cssSelector("input.form-submit")).click();
				Utille.sleep(3000);
			}
		}
		// 以下1日回
		if (!secondFlg && !thirdFlg) {
			mName = "■rakutenバナー";
			driver.get("http://pex.jp");
			selector = "div>div>div>a>img[src*='image.infoseek.rakuten.co.jp']";
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

	private static boolean isExistEle(WebElement wEle, String selector) {
		return Utille.isExistEle(wEle, selector, logg);
	}

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

			mName = "■教えてどっち";
			driver.get("http://ecnavi.jp/vote/choice/");
			// ランダムで1,2を選ぶ
			int ran1 = Utille.getIntRand(2);
			String selector = "button";
			if (ran1 == 1) {
				selector += "#btnA";
			}
			else {
				selector += "#btnB";
			}
			if (isExistEle(driver, selector)) {
				driver.findElement(By.cssSelector(selector)).click();
			}
			else {
				logg.warn(mName + "]獲得済み");
			}

			mName = "■毎日ニュース";
			int ecnNewsClickNorma = 5;
			int ecnNewsClick = 0;
			for (int i = 0; ecnNewsClick < ecnNewsClickNorma; i++) {
				driver.get("http://ecnavi.jp/mainichi_news/");
				if (!isExistEle(driver, "div.article_list li>a")) {
					break;
				}
				List<WebElement> eleList = driver.findElements(By.cssSelector("div.article_list li>a"));
				eleList.get(i).click();
				Utille.sleep(3000);
				int ran = Utille.getIntRand(4);
				String selector1 = "div.feeling_buttons button";
				if (ran == 0) {
					selector1 += ".btn_feeling_good";
				}
				else if (ran == 1) {
					selector1 += ".btn_feeling_bad";
				}
				else if (ran == 2) {
					selector1 += ".btn_feeling_sad";
				}
				else if (ran == 3) {
					selector1 += ".btn_feeling_glad";
				}
				if (isExistEle(driver, selector1)) {
					driver.findElement(By.cssSelector(selector1)).click();
					Utille.sleep(3000);
					ecnNewsClick++;
				}
				else if (isExistEle(driver, "p.got_maxpoints")) {
					logg.warn(mName + "]獲得済み");
					break;
				}
			}
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
