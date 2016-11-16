package pointGet;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import lombok.val;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.mission.EcnMissionChinjyu;
import pointGet.mission.Mission;
import pointGet.mission.PexMissionPectan;
import pointGet.mission.PexMissionSearch;

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
		if (!thirdFlg) {
			// ecサイト
			goToClickSite(Define.PSITE_CODE_ECN);
			// pex
			goToClickSite(Define.PSITE_CODE_PEX);
			// げん玉
			goToClickSite(Define.PSITE_CODE_GEN);
		}
		if (!secondFlg && !thirdFlg) {
			// GetMoney
			goToClickSite(Define.PSITE_CODE_GMY);
			// RIN
			goToClickSite(Define.PSITE_CODE_RIN);
		}

		// OSA
		goToClickSite(Define.PSITE_CODE_OSA);
		// mop
		goToClickSite(Define.PSITE_CODE_MOP);

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
					mission.roopMisstion(driver);
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

	private static void checkOverlay(WebDriver driver, String selector) {
		if (isExistEle(driver, selector)) {
			driver.findElement(By.cssSelector(selector)).click(); // 広告消す
			Utille.sleep(2000);
		}
	}

	private static void goToClickOSA(WebDriver driver) {
		String mName = "■daily quiz"; //  0時、8時、16時開催
		String selector = "li.long img[alt='デイリークイズ']";
		driver.get("http://osaifu.com/contents/coinland/");
		Utille.sleep(2000);
		if (isExistEle(driver, selector)) {
			logg.warn(mName + "]" + selector + " is EXIST");
			driver.findElement(By.cssSelector(selector)).click(); // 遷移
			Utille.sleep(2000);
			checkOverlay(driver, "div.overlay.overlay-timer a.button-close");
			selector = "input[name='submit']";
			Utille.sleep(4000);
			if (isExistEle(driver, selector)) {
				logg.warn(mName + "]" + selector + " is EXIST");
				driver.findElement(By.cssSelector(selector)).click();
				for (int i = 0; i < 8; i++) {
					Utille.sleep(4000);
					selector = "ul.ui-item-body";
					if (isExistEle(driver, selector)) {
						logg.warn(mName + "]" + selector + " is EXIST");
						int ran = Utille.getIntRand(4);
						String selectId = "label[for='radio-";
						if (ran == 0) {
							selectId += "1']";
						}
						else if (ran == 1) {
							selectId += "2']";
						}
						else if (ran == 2) {
							selectId += "3']";
						}
						else {
							selectId += "4']";
						}
						// 8kai roop
						String selector2 = "input[name='submit']";
						if (isExistEle(driver, selectId)) {
							logg.warn(mName + "]" + selectId + " is EXIST");
							driver.findElement(By.cssSelector(selectId)).click(); // 選択
							Utille.sleep(2000);
							driver.findElement(By.cssSelector(selector2)).click(); // 遷移
							Utille.sleep(4000);
							checkOverlay(driver, "div.overlay.overlay-timer a.button-close");
							if (isExistEle(driver, selector2)) {
								logg.warn(mName + "]" + selector2 + " is EXIST");
								driver.findElement(By.cssSelector(selector2)).click(); // 遷移
								Utille.sleep(3000);
								checkOverlay(driver, "div.overlay.overlay-timer a.button-close");
							}
						}
					}
				}
				logg.warn(mName + "]kuria?");
				selector = "input[name='submit']";
				if (isExistEle(driver, selector)) {
					driver.findElement(By.cssSelector(selector)).click();
					Utille.sleep(3000);
				}
			}
			//			checkOverlay(driver, "div.overlay.overlay-timer a.button-close");
			//			selector = "a.ui-button.ui-button-close";
			//			if (isExistEle(driver, selector)) {
			//				String url = driver.findElement(By.cssSelector(selector)).getAttribute("href");
			//				driver.get(url);
			//				Utille.sleep(3000);
			//			}
			//			// 閉じるボタン（既にゲット済み）
			//			if (isExistEle(driver, "a.ui-button.ui-button-close")) {
			//				driver.findElement(By.cssSelector("a.ui-button.ui-button-close")).click();
			//				Utille.sleep(2000);
			//			}

			// String selector2 = "[alt='Check']";
			// int size = driver.findElements(By.cssSelector(selector)).size();
			// for (int i = 0; i < size; i++) {
			// // document.querySelectorAll('[alt="Check"]
			// ')[1].parentNode.parentNode.parentNode.querySelectorAll('div.bnrBox
			// img')[0];
			// WebElement e =
			// driver.findElements(By.cssSelector(selector)).get(i);
			// if (isExistEle(e, selector2)) {
			// Utille.sleep(2000);
			// }
			// }
			// selector = "";
		}
	}

	/**
	 * 
	 * @param driver
	 */
	private static void goToClickMOP(WebDriver driver) {
		if (!secondFlg && !thirdFlg) { // 1日1回
			// クリックで貯める
			driver.get("http://pc.moppy.jp/cc/");
			String selector = "p.click>a.cc-btn";
			if (isExistEle(driver, selector)) {
				int size = driver.findElements(By.cssSelector(selector)).size();
				for (int i = 0; i < size; i++) {
					driver.findElements(By.cssSelector(selector)).get(i).click();
					Utille.sleep(2000);
				}
			}
			String mName = "モッピークイズ";
			mName = "トキメキ調査隊"; //  4時更新
			for (int j = 0; j < 3; j++) {
				driver.get("http://pc.moppy.jp/gamecontents/");
				selector = "div.game_btn>div.icon>img[alt='トキメキ調査隊']";
				if (isExistEle(driver, selector)) {
					driver.findElement(By.cssSelector(selector)).click(); // 遷移
					Utille.sleep(4000);
					String wid = driver.getWindowHandle();
					java.util.Set<String> widSet = driver.getWindowHandles();
					String new_window_id = null;
					for (String id : widSet) {
						if (!id.equals(wid)) {
							//現在のウインドウIDと違っていたら格納  
							//最後に格納されたIDが一番新しく開かれたウインドウと判定  
							new_window_id = id;
						}
					}
					driver.close();
					//最後に格納したウインドウIDにスイッチ  
					driver.switchTo().window(new_window_id);
					checkOverlay(driver, "div#popup a.modal_close");
					selector = "div.thumbnail span.icon-noentry";
					if (isExistEle(driver, selector)) {
						List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
						eleList.get(0).click(); // 遷移
						Utille.sleep(3000);
						selector = "div.thumb-start div.btn>a";
						if (isExistEle(driver, selector)) {
							driver.findElement(By.cssSelector(selector)).click(); // 遷移
							Utille.sleep(4000);
							for (int i = 0; i < 10; i++) {
								int ran = Utille.getIntRand(2);
								selector = "span.icon-arrow";
								if (isExistEle(driver, selector)) {
									checkOverlay(driver, "div#popup a.modal_close");
									driver.findElements(By.cssSelector(selector)).get(ran).click();
									Utille.sleep(3000);
								}
							}
						}
					}
				}
			}
		}

		if (true) {
			//		if (false) {
			String mName = "モッピークイズ"; //  0時、8時、16時開催
			driver.get("http://pc.moppy.jp/gamecontents/");
			String selector = "div.game_btn>div.icon>img[alt='モッピークイズ']";
			if (isExistEle(driver, selector)) {
				logg.warn(mName + "]" + selector + " is EXIST");
				driver.findElement(By.cssSelector(selector)).click(); // 遷移
				Utille.sleep(2000);
				String wid = driver.getWindowHandle();
				java.util.Set<String> widSet = driver.getWindowHandles();
				widSet.remove(wid);
				driver.switchTo().window(widSet.iterator().next());
				checkOverlay(driver, "div.overlay-popup a.button-close");
				selector = "form>input[name='submit']";
				if (isExistEle(driver, selector)) {
					logg.warn(mName + "]" + selector + " is EXIST");
					driver.findElement(By.cssSelector(selector)).click();
					for (int i = 0; i < 8; i++) {
						Utille.sleep(4000);
						selector = "ul.ui-item-body";
						if (isExistEle(driver, selector)) {
							logg.warn(mName + "]" + selector + " is EXIST");
							int ran = Utille.getIntRand(4);
							String selectId = "label[for='radio-";
							if (ran == 0) {
								selectId += "1']";
							}
							else if (ran == 1) {
								selectId += "2']";
							}
							else if (ran == 2) {
								selectId += "3']";
							}
							else {
								selectId += "4']";
							}
							// 8kai roop
							String selector2 = "input[name='submit']";
							if (isExistEle(driver, selectId)) {
								logg.warn(mName + "]" + selectId + " is EXIST");
								driver.findElement(By.cssSelector(selectId)).click(); // 選択
								Utille.sleep(2000);
								driver.findElement(By.cssSelector(selector2)).click(); // 遷移
								Utille.sleep(4000);
								checkOverlay(driver, "div.overlay-popup a.button-close");
								if (isExistEle(driver, selector2)) {
									logg.warn(mName + "]" + selector2 + " is EXIST");
									driver.findElement(By.cssSelector(selector2)).click(); // 遷移
									Utille.sleep(3000);
									checkOverlay(driver, "div.overlay-popup a.button-close");
								}
							}
						}
					}
					logg.warn(mName + "]kuria?");
					selector = "input[name='submit']";
					if (isExistEle(driver, selector)) {
						driver.findElement(By.cssSelector(selector)).click();
						Utille.sleep(3000);
					}
				}
				//				checkOverlay(driver, "div.overlay-popup a.button-close");
				//				selector = "a.ui-button.ui-button-close";
				//				logg.warn(mName + "]来たよ" + " is EXIST");
				////				if (isExistEle(driver, selector)) {
				////					String url = driver.findElement(By.cssSelector(selector)).getAttribute("href");
				////					driver.get(url);
				////					Utille.sleep(3000);
				////				}
				//				// 閉じるボタン（既にゲット済み）
				//				if (isExistEle(driver, "a.ui-button.ui-button-close")) {
				//					driver.findElement(By.cssSelector("a.ui-button.ui-button-close")).click();
				//					Utille.sleep(2000);
				//				}
			}
		}
	}

	private static void goToClickPEX(WebDriver driver) {
		// 1日2回
		if (!thirdFlg) {
			String mName = "■みんなのアンサー";
			driver.get("http://pex.jp/minna_no_answer/questions/current");
			// ランダムで1,2を選ぶ
			int ran = Utille.getIntRand(2);
			String selector = "section.question_area input[type='submit']";
			if (isExistEle(driver, selector)) {
				driver.findElements(By.cssSelector(selector)).get(ran).click();
				val alert = driver.switchTo().alert();
				alert.accept();
			}
			else {
				logg.warn(mName + "]獲得済み");
			}

			mName = "■ポイントクイズ";
			driver.get("http://pex.jp/point_quiz");
			int ran1 = Utille.getIntRand(4);
			selector = "ul.answer_select a";
			if (isExistEle(driver, selector)) {
				driver.findElements(By.cssSelector(selector)).get(ran1).click();
				val alert2 = driver.switchTo().alert();
				alert2.accept();
			}
			else {
				logg.warn(mName + "]獲得済み");
			}
			mName = "■オリチラ";
			driver.get("http://pex.jp/chirashi");
			selector = "section.list li figure>a";
			if (isExistEle(driver, selector)) {
				driver.findElement(By.cssSelector(selector)).click();
				Utille.sleep(3000);
			}
			else {
				logg.warn(mName + "]獲得済み");
			}
		}
		// 以下1日回
		if (!secondFlg && !thirdFlg) {
			// ■ポイント検索
			Mission PexMissionSearch = new PexMissionSearch(logg, wordList);
			PexMissionSearch.roopMisstion(driver);
			missionList.add(PexMissionSearch);
			// ■ぺく単
			Mission PexMissionPectan = new PexMissionPectan(logg);
			PexMissionPectan.roopMisstion(driver);
			missionList.add(PexMissionPectan);

			String mName = "■毎日ニュース　午前7：00～翌日午前6：59";
			int pexNewsNum = 5;
			int pexNewsClick = 0;
			for (int i = 0; pexNewsClick < pexNewsNum; i++) {
				driver.get("http://pex.jp/point_news");
				String selector = "ul#news-list>li>figure>a";
				if (!isExistEle(driver, selector)) {
					break;
				}
				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
				String newsUrl = eleList.get(i).getAttribute("href");
				logg.info("newsUrl:" + newsUrl);
				driver.get(newsUrl);
				Utille.sleep(3000);

				int ran = Utille.getIntRand(4);
				String selectId = "table.you_say input";
				if (ran == 0) {
					selectId += "#submit-like";
				}
				else if (ran == 1) {
					selectId += "#submit-angry";
				}
				else if (ran == 2) {
					selectId += "#submit-sad";
				}
				else {
					selectId += "#submit-cool";
				}
				if (isExistEle(driver, selectId)) {
					driver.findElement(By.cssSelector(selectId)).click();
					Utille.sleep(3000);
					pexNewsClick++;
				}
				else if (isExistEle(driver, "p.got_maxpoints")) {
					break;
				}
			}

			mName = "■クリックポイント";
			driver.get("http://pex.jp/point_actions#clickpoint");
			String selector = "div.clickpoint_innner li>a>p.title";
			if (isExistEle(driver, selector)) {
				if (!isExistEle(driver, "p.get_massage")) { // 獲得済みか
					int size = driver.findElements(By.cssSelector(selector)).size();
					for (int i = 0; i < size; i++) {
						if (isExistEle(driver, selector)) {
							driver.findElements(By.cssSelector(selector)).get(i).click();
							Utille.sleep(2000);
							driver.get("http://pex.jp/point_actions#clickpoint");
						}
					}
				}
				else {
					logg.warn(mName + "]獲得済み");
				}
			}
		}
	}

	private static boolean isExistEle(WebElement wEle, String selector) {
		return Utille.isExistEle(wEle, selector, logg);
	}

	private static void goToClickECN(WebDriver driver) {
		// 1日2回
		if (!thirdFlg) {
			String mName = "■チラシ";
			String selector = "a.chirashi_link";
			driver.get("http://ecnavi.jp/contents/chirashi/");
			if (isExistEle(driver, selector)) {
				driver.findElement(By.cssSelector(selector)).click();
			}
			else {
				logg.warn(mName + "]獲得済み");
			}
		}
		// 以下1日回
		if (!secondFlg && !thirdFlg) {
			String mName = "■web検索";
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
				String selector = "div#doron a.item";
				if (isExistEle(driver, selector)) {
					driver.findElement(By.cssSelector(selector)).click();
					//					val alert = driver.switchTo().alert();
					//					alert.accept();
					// alert.accept();
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

			// ■珍獣先生
			Mission EcnMissionChinjyu = new EcnMissionChinjyu(logg);
			EcnMissionChinjyu.roopMisstion(driver);
			missionList.add(EcnMissionChinjyu);
		}
	}

	private static void goToClickGMY(WebDriver driver) {
		if (!secondFlg && !thirdFlg) {
			String mName = "■clipoバナーtop";
			driver.get("http://dietnavi.com/pc/");
			String selector = "a span.clickpt";
			if (isExistEle(driver, selector)) {
				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
				// clipoバナー
				int size = eleList.size();
				for (int i = 0; i < size; i++) {
					eleList.get(i).click();
					Utille.sleep(2000);
				}
			}
			mName = "■clipoバナーページ";
			driver.get("http://dietnavi.com/pc/daily_click.php");
			if (isExistEle(driver, selector)) {
				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
				int size = eleList.size();
				for (int i = 0; i < size; i++) {
					eleList.get(i).click();
					Utille.sleep(2000);
				}
			}
			mName = "■チラシ";
			driver.get("http://dietnavi.com/pc/");
			selector = "#chirashi_check";
			if (isExistEle(driver, selector)) {
				driver.findElement(By.cssSelector(selector)).click();
				Utille.sleep(2000);
			}
		}
	}

	private static void goToClickGEN(WebDriver driver) {

		if (!thirdFlg) {
			String mName = "■ポイントの森";
			driver.get("http://www.gendama.jp/forest/");
			String selector = "a img[src$='star.gif']";
			if (isExistEle(driver, selector)) {
				driver.findElement(By.cssSelector(selector)).click();
			}
			else {
				logg.warn(mName + "]獲得済み");
			}
		}

		if (!secondFlg && !thirdFlg) {
			String mName = "■ポイントの森";
			driver.get("http://www.gendama.jp/forest/");
			String currentWindowId = driver.getWindowHandle();
			logg.info("currentWindowId: " + currentWindowId);
			String selecter[] = { "#forestBox a[href^='/cl/?id=']", "#downBox a[href^='/cl/?id=']",
					"#sponsor a[href^='/cl/?id=']" };
			for (int j = 0; j < selecter.length; j++) {
				String selector = selecter[j];
				logg.info("selector: " + selector);
				if (isExistEle(driver, selector)) {
					List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
					int size = eleList.size();
					for (int i = 0; i < size; i++) {
						logg.info("text: " + driver.findElements(By.cssSelector(selector)).get(i).getText());
						driver.findElements(By.cssSelector(selector)).get(i).click();
					}
				}
			}
			// ポイントの森
			driver.get("http://www.gendama.jp/forest/");
			String selector = "#reach a[href^='/cl/?id=']";
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
