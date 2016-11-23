package pointGet;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.mission.Mission;
import pointGet.mission.ecn.ECNChinjyu;
import pointGet.mission.ecn.ECNChirachi;
import pointGet.mission.gmy.GMYChirachi;
import pointGet.mission.gmy.GMYClickBanner;
import pointGet.mission.mop.MOPChyosatai;
import pointGet.mission.mop.MOPClickBanner;
import pointGet.mission.mop.MOPQuiz;
import pointGet.mission.osa.OSAClickBanner;
import pointGet.mission.osa.OSAQuiz;
import pointGet.mission.pex.PEXMekutte;

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
			String strFlg = args[0];
			if (strFlg.equals("1")) {
				secondFlg = true;
			}
			else if (strFlg.equals("2")) {
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
		//		if (!thirdFlg) {
//		// ecサイト
//		goToClickSite(Define.PSITE_CODE_ECN);
					// pex
					goToClickSite(Define.PSITE_CODE_PEX);
		//			// げん玉
		//			goToClickSite(Define.PSITE_CODE_GEN);
		//		}
		//		if (!secondFlg && !thirdFlg) {
		//			// GetMoney
		//			goToClickSite(Define.PSITE_CODE_GMY);
		//			// RIN
		//			goToClickSite(Define.PSITE_CODE_RIN);
		//		}
		//		// mop
		//		goToClickSite(Define.PSITE_CODE_MOP);
		//		// OSA
		//		goToClickSite(Define.PSITE_CODE_OSA);

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
		logg.info("■■■■");
		logg.info("■■missionSite[" + siteType + "]START■■");
		logg.info("■■■■");
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
			}
		} catch (Exception e) {
			e.printStackTrace();
			logg.error("-Exception-------------------");
			logg.error(e.getMessage());
			logg.error(e.getLocalizedMessage());
			logg.error(e.getStackTrace().toString());
			logg.error("-Exception-------------------");
		} catch (Throwable e) {
			e.printStackTrace();
			logg.error("-Throwable-------------------");
			logg.error(e.getMessage());
			logg.error(e.getStackTrace());
			logg.error("-Throwable-------------------");
		}
		logg.info("■■■■");
		logg.info("■■missionSite[" + siteType + "]END■■");
		logg.info("■■■■");
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
					// document.querySelectorAll('[alt="Check"]
					// ')[1].parentNode.parentNode.parentNode.querySelectorAll('div.bnrBox
					// img')[0];
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
					// document.querySelectorAll('[alt="Check"]
					// ')[1].parentNode.parentNode.parentNode.querySelectorAll('div.bnrBox
					// img')[0];
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
		}
		if (true) {
			// ■モッピークイズ
			Mission MOPQuiz = new MOPQuiz(logg);
			MOPQuiz.exePrivateMission(driver);
		}
	}

	private static void goToClickPEX(WebDriver driver) {
		String selector = "";
//		// 1日2回
//		if (!thirdFlg) {
//			String mName = "■みんなのアンサー";
//			driver.get("http://pex.jp/minna_no_answer/questions/current");
//			// ランダムで1,2を選ぶ
//			int ran = Utille.getIntRand(2);
//			selector = "section.question_area input[type='submit']";
//			if (isExistEle(driver, selector)) {
//				driver.findElements(By.cssSelector(selector)).get(ran).click();
//				val alert = driver.switchTo().alert();
//				alert.accept();
//			}
//			else {
//				logg.warn(mName + "]獲得済み");
//			}
//
//			mName = "■ポイントクイズ";
//			driver.get("http://pex.jp/point_quiz");
//			int ran1 = Utille.getIntRand(4);
//			selector = "ul.answer_select a";
//			if (isExistEle(driver, selector)) {
//				driver.findElements(By.cssSelector(selector)).get(ran1).click();
//				val alert2 = driver.switchTo().alert();
//				alert2.accept();
//			}
//			else {
//				logg.warn(mName + "]獲得済み");
//			}
//			// ■オリチラ
//			Mission PEXChirachi = new PEXChirachi(logg);
//			PEXChirachi.exePrivateMission(driver);
//		}
//		// 以下1日回
//		if (!secondFlg && !thirdFlg) {
//			mName = "■rakutenバナー";
//			driver.get("http://pex.jp");
//			selector = "div>div>div>a>img[src*='image.infoseek.rakuten.co.jp']";
//			if (isExistEle(driver, selector)) {
//				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
//				eleList.get(0).click();
//				Utille.sleep(3000);
//			}
//			else {
//				logg.warn(mName + "]なかったよ...");
//			}
//			// ■ポイント検索
//			Mission PexMissionSearch = new PEXSearch(logg, wordList);
//			PexMissionSearch.exeRoopMission(driver);
//			missionList.add(PexMissionSearch);
//			// ■ぺく単
//			Mission PexMissionPectan = new PEXPectan(logg);
//			PexMissionPectan.exeRoopMission(driver);
//			missionList.add(PexMissionPectan);
//
//			mName = "■毎日ニュース　午前7：00～翌日午前6：59";
//			int pexNewsNum = 5;
//			int pexNewsClick = 0;
//			for (int i = 0; pexNewsClick < pexNewsNum; i++) {
//				driver.get("http://pex.jp/point_news");
//				// 未獲得印3つが確認できない場合ノルマ達成とみなしブレイク
//				selector = "li.pt.ungained";
//				if (!isExistEle(driver, selector)) {
//					break;
//				}
//				selector = "ul#news-list>li>figure>a";
//				if (!isExistEle(driver, selector)) {
//					break;
//				}
//				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
//				String newsUrl = eleList.get(i).getAttribute("href");
//				logg.info("newsUrl:" + newsUrl);
//				driver.get(newsUrl);
//				Utille.sleep(3000);
//
//				int ran = Utille.getIntRand(4);
//				String selectId = "table.you_say input";
//				if (ran == 0) {
//					selectId += "#submit-like";
//				}
//				else if (ran == 1) {
//					selectId += "#submit-angry";
//				}
//				else if (ran == 2) {
//					selectId += "#submit-sad";
//				}
//				else {
//					selectId += "#submit-cool";
//				}
//				if (isExistEle(driver, selectId)) {
//					driver.findElement(By.cssSelector(selectId)).click();
//					Utille.sleep(3000);
//					pexNewsClick++;
//				}
//				else if (isExistEle(driver, "p.got_maxpoints")) {
//					break;
//				}
//			}
//
//			// ■クリックポイント
//			Mission PEXClickBanner = new PEXClickBanner(logg);
//			PEXClickBanner.exePrivateMission(driver);

			// ■めくってシール
			Mission PEXMekutte = new PEXMekutte(logg);
			PEXMekutte.exePrivateMission(driver);
//		}
		//		// テスト中
		//				PEXMitukete PEXMitukete = new PEXMitukete(logg);
		//				PEXMitukete.exePrivateMission(driver);

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

			mName = "■web検索";
			// propertiesファイルから単語リストを抽出して、ランダムで5つ（数は指定可能）
			String[] wordSearchList = Utille.getWordSearchList(wordList, 5);
			driver.get("http://ecnavi.jp/search/web/?Keywords=");
			int ecnSearchNum = 4;
			for (int i = 0; i < ecnSearchNum; i++) {
				String selector = "input[name='Keywords']";
				if (isExistEle(driver, selector)) {
					WebElement ele = driver.findElement(By.cssSelector(selector));
					ele.clear();
					logg.info("検索keyword[" + wordSearchList[i]);
					ele.sendKeys(wordSearchList[i]);
					driver.findElement(By.cssSelector("button[type='submit']")).click();
					Utille.sleep(3000);
				}
			}

			mName = "■検索募金";
			wordSearchList = Utille.getWordSearchList(wordList, 2);
			int ecnSearchBokinNum = 2;
			for (int i = 0; i < ecnSearchBokinNum; i++) {
				driver.get("http://ecnavi.jp/smile_project/search_fund/");
				String selector = "input[name='Keywords']";
				if (isExistEle(driver, selector)) {
					WebElement ele = driver.findElement(By.cssSelector("input[name='Keywords']"));
					ele.clear();
					logg.info("検索keyword[" + wordSearchList[i]);
					ele.sendKeys(wordSearchList[i]);
					driver.findElement(By.cssSelector("button[type='submit']")).click();
					Utille.sleep(3000);
				}
			}

			mName = "■クリック募金";
			driver.get("http://point.ecnavi.jp/fund/bc/");
			if (isExistEle(driver, "div.bnr_box")) {
				List<WebElement> eleList1 = driver.findElements(By.cssSelector("div.bnr_box"));
				for (WebElement ele : eleList1) {
					WebElement eleTarget = ele.findElement(By.cssSelector("a"));
					eleTarget.click();
					Utille.sleep(3000);
				}
			}

			mName = "■ドロンバナークリック2種";
			String[] dronUrlList = { "http://ecnavi.jp/", "http://ecnavi.jp/pointboard/#doron" };
			for (int i = 0; i < dronUrlList.length; i++) {
				driver.get(dronUrlList[i]);
				Utille.sleep(1000);
				String selector = "div#doron a.item";
				if (isExistEle(driver, "div.js_anime.got")) {
					logg.warn(mName + i + "]獲得済み");
					continue;
				}
				else if (isExistEle(driver, selector)) {
					driver.findElement(By.cssSelector(selector)).click();
				}
			}

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

	private static void goToClickGEN(WebDriver driver) {

		if (!thirdFlg) {
// 			Mission GENPointStar = new GENPointStar(logg);
// 			GENPointStar.exePrivateMission(driver);

			String mName = "■ポイントの森";
			driver.get("http://www.gendama.jp/forest/");
			Utille.sleep(2000);
			String selector = "a img[src$='star.gif']";
			if (isExistEle(driver, selector)) {
				driver.findElement(By.cssSelector(selector)).click();
			}
			else {
				logg.warn(mName + "]獲得済み");
			}
		}

		if (!secondFlg && !thirdFlg) {

// 			Mission GENClickBanner = new GENClickBanner(logg);
// 			GENClickBanner.exePrivateMission(driver);

			mName = "■ポイントの森";
			driver.get("http://www.gendama.jp/forest/");
			Utille.sleep(2000);
			String selecter[] = { "div#forestBox a img", "div#downBox a.all p.bnrImg"
					//					, "div#sponsor a[href^='/cl/?id='] img"  // ここにバッチ起動時のみバグってる
			};
			for (int j = 0; j < selecter.length; j++) {
				String selector = selecter[j];
				logg.info("selector: " + selector);
				if (isExistEle(driver, selector)) {
					List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
					int size = eleList.size();
					for (int i = 0; i < size; i++) {
						//						logg.info("text: " + eleList.get(i).getText());
						eleList.get(i).click();
						Utille.sleep(2000);
					}
				}
			}
			// ポイントの森
			//			driver.get("http://www.gendama.jp/forest/");
			String selector = "section#reach a.clearfix dt";
			if (isExistEle(driver, selector)) {
				int size = driver.findElements(By.cssSelector(selector)).size();
				for (int i = 0; i < size; i++) {
					driver.findElements(By.cssSelector(selector)).get(i).click();
					Utille.sleep(2000);
				}
			}
			logg.info("selector: end");
		}
	}
}
