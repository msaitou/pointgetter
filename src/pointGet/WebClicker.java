package pointGet;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import lombok.val;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.mission.EcnMissionChinjyu;
import pointGet.mission.Mission;
import pointGet.mission.PexMissionPectan;
import pointGet.mission.PexMissionSearch;

/**
 * get point from the point site 
 * @author 雅人
 *
 */
public class WebClicker {

	private static final String loadFilePath = "pGetWeb.properties";
	// log class
	private static Logger log = null;

	private static String[] wordList = null;

	private static ArrayList<Mission> missionList = new ArrayList<Mission>();

	/**
	 * 設定ファイルをローカル変数に展開する
	 */
	private static void _getPointGetWebConf() {

		Properties loadProps = Utille.getProp(loadFilePath);
		if (loadProps.isEmpty()) {
			return;
		}
		wordList = loadProps.getProperty("wordList").split(",");
	}

	/**
	 * ログクラスの設定
	 */
	private static void _setLogger() {
		PropertyConfigurator.configure("log4jweb.properties");
		log = Utille.setLogger(WebClicker.class);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		_setLogger();
		log.info("Start!!");
		_getPointGetWebConf();
		// GetMoney 
		goToClickSite(Define.PSITE_CODE_GMY);
		// mop
		goToClickSite(Define.PSITE_CODE_MOP);
		// げん玉
		goToClickSite(Define.PSITE_CODE_GEN);
		// ecサイト
		goToClickSite(Define.PSITE_CODE_ECN);
		// pex
		goToClickSite(Define.PSITE_CODE_PEX);
		//		// OSA
		//		goToClickSite(Define.PSITE_CODE_OSA);
		// RIN
		goToClickSite(Define.PSITE_CODE_RIN);

		if (missionList.size() > 0) {
			log.info("roopStart!!");
			WebDriver driver = Utille.getWebDriver();
			int CompCnt = 0;
			while (true) {
				for (Mission mission : missionList) {
					if (mission.isCompFlag()) {
						log.info("Complete!!");
						CompCnt++;
						continue;
					}
					mission.roopMisstion(driver);
				}
				if (CompCnt == missionList.size()) {
					break;
				}
				log.info("again!!");
				Utille.sleep(306000);
			}
			driver.quit();
			log.info("roopEnd!!");
		}

		log.info("can not read properties!!");
	}

	/**
	 * 
	 * @param siteType
	 */
	private static void goToClickSite(String siteType) {

		WebDriver driver = Utille.getWebDriver();
		//		Wait<WebDriver> wait = new WebDriverWait(driver, 30);
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
		driver.quit();
	}

	/**
	 * rakuten 
	 * @param driver
	 */
	private static void goToClickRIN(WebDriver driver) {

		driver.get("https://www.rakuten-card.co.jp/e-navi/index.xhtml");
		String selector = "li#loginId>input#u";
		// ログイン画面であれば
		if (isExistEle(driver, selector)) {
			WebElement ele = driver.findElement(By.cssSelector(selector));
			ele.clear();
			ele.sendKeys("peterpansymdromer@gmail.com");
			selector = "li#loginPw>input#p";
			ele = driver.findElement(By.cssSelector(selector));
			ele.clear();
			ele.sendKeys("koiseyoseiko");
			driver.findElement(By.cssSelector("input#loginButton")).click();
			Utille.sleep(5000);
		}
		driver.get("https://www.rakuten-card.co.jp/e-navi/members/point/click-point/index.xhtml?l-id=enavi_all_submenu_clickpoint");
		Utille.sleep(5000);
		selector = "div.topArea.clearfix";
		if (isExistEle(driver, selector)) {
			String selector2 = "[alt='Check']";
			int size = driver.findElements(By.cssSelector(selector)).size();
			for (int i = 0; i < size; i++) {
				// document.querySelectorAll('[alt="Check"] ')[1].parentNode.parentNode.parentNode.querySelectorAll('div.bnrBox img')[0];
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
				// document.querySelectorAll('[alt="Check"] ')[1].parentNode.parentNode.parentNode.querySelectorAll('div.bnrBox img')[0];
				WebElement e = driver.findElements(By.cssSelector(selector)).get(i);
				if (isExistEle(e, selector2)) {
					e.findElement(By.cssSelector("a>img")).click();
					Utille.sleep(2000);
				}
			}
		}
	}

	// div.game_btn>div.icon>img[alt='モッピークイズ']

	private static void goToClickOSA(WebDriver driver) {
		//		ui-button ui-button-start
		// daily quiz
		String selector = "li.long img[alt='デイリークイズ']";
		driver.get("http://osaifu.com/contents/coinland/");
		Utille.sleep(2000);
		if (isExistEle(driver, selector)) {
			driver.findElement(By.cssSelector(selector)).click();
			selector = "input[name='submit']";
			Utille.sleep(2000);
			if (isExistEle(driver, selector)) {
				driver.findElement(By.cssSelector(selector)).click();
				for (int i = 0; i < 8; i++) {
					selector = "ul.ui-item-body";
					if (isExistEle(driver, selector)) {
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
						String selector2 = "input.[name='submit']";
						if (isExistEle(driver, selectId)) {
							driver.findElement(By.cssSelector(selectId)).click();
							Utille.sleep(3000);
							driver.findElement(By.cssSelector(selector2)).click();
							Utille.sleep(3000);
						}
					}
				}
				selector = "input.[name='submit']";
				if (isExistEle(driver, selector)) {
					driver.findElement(By.cssSelector(selector)).click();
					Utille.sleep(3000);
					selector = "$('a.ui-button').attr('href')";
					if (isExistEle(driver, selector)) {
						driver.findElement(By.cssSelector(selector)).click();
						String url = driver.findElement(By.cssSelector(selector)).getAttribute("href");
						driver.get(url);
						Utille.sleep(3000);
					}
				}
			}

			String selector2 = "[alt='Check']";
			int size = driver.findElements(By.cssSelector(selector)).size();
			for (int i = 0; i < size; i++) {
				// document.querySelectorAll('[alt="Check"] ')[1].parentNode.parentNode.parentNode.querySelectorAll('div.bnrBox img')[0];
				WebElement e = driver.findElements(By.cssSelector(selector)).get(i);
				if (isExistEle(e, selector2)) {
					Utille.sleep(2000);
				}
			}
			selector = "";
		}
	}

	/**
	 * 
	 * @param driver
	 */
	private static void goToClickMOP(WebDriver driver) {
		if (true) {
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
		}
	}

	private static void goToClickPEX(WebDriver driver) {
		// 1日2回
		if (true) {
			// ■みんなのアンサー
			driver.get("http://pex.jp/minna_no_answer/questions/current");
			// ランダムで1,2を選ぶ
			int ran = Utille.getIntRand(2);
			String selector = "section.question_area input[type='submit']";
			if (isExistEle(driver, selector)) {
				driver.findElements(By.cssSelector(selector)).get(ran).click();
				val alert = driver.switchTo().alert();
				alert.accept();
			}

			driver.get("http://pex.jp/point_quiz");
			// ■ポイントクイズ
			int ran1 = Utille.getIntRand(4);
			selector = "ul.answer_select a";
			if (isExistEle(driver, selector)) {
				driver.findElements(By.cssSelector(selector)).get(ran1).click();
				val alert2 = driver.switchTo().alert();
				alert2.accept();
			}

			// ■オリチラ
			driver.get("http://pex.jp/chirashi");
			selector = "section.list li figure>a";
			if (isExistEle(driver, selector)) {
				driver.findElement(By.cssSelector(selector)).click();
				Utille.sleep(3000);
			}
		}
		// 以下1日回
		if (true) {

			// ■ポイント検索
			Mission PexMissionSearch = new PexMissionSearch(log, wordList);
			PexMissionSearch.roopMisstion(driver);
			missionList.add(PexMissionSearch);
			// ■ぺく単
			Mission PexMissionPectan = new PexMissionPectan(log);
			PexMissionPectan.roopMisstion(driver);
			missionList.add(PexMissionPectan);

			// ■毎日ニュース　7:00～
			int pexNewsNum = 5;
			for (int i = 0; i < pexNewsNum; i++) {
				driver.get("http://pex.jp/point_news");
				String selector = "ul#news-list>li>figure>a";
				if (!isExistEle(driver, selector)) {
					break;
				}
				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
				String newsUrl = eleList.get(i).getAttribute("href");
				log.info("newsUrl:" + newsUrl);
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
				}
				else if (isExistEle(driver, "p.got_maxpoints")) {
					break;
				}
			}

			// ■クリックポイント
			driver.get("http://pex.jp/point_actions#clickpoint");
			String selector = "div.clickpoint_innner li>a>p.title";
			if (isExistEle(driver, selector)) {
				int size = driver.findElements(By.cssSelector(selector)).size();
				for (int i = 0; i < size; i++) {
					if (isExistEle(driver, selector)) {
						//						String AnsUrl = driver.findElement(By.cssSelector(selector)).getAttribute("href");
						//						driver.get(AnsUrl);
						driver.findElements(By.cssSelector(selector)).get(i).click();
						Utille.sleep(2000);
						driver.get("http://pex.jp/point_actions#clickpoint");
					}
				}
			}
		}
	}

	private static boolean isExistEle(WebElement wEle, String selector) {
		return Utille.isExistEle(wEle, selector, log);
	}

	private static boolean isExistEle(WebDriver driver, String selector) {
		return Utille.isExistEle(driver.findElements(By.cssSelector(selector)), log);
	}

	private static void goToClickECN(WebDriver driver) {

		// 1日2回
		if (true) {
			// ■チラシ
			driver.get("http://ecnavi.jp/contents/chirashi/");
			String selector = "a.chirashi_link";
			if (isExistEle(driver, selector)) {
				driver.findElement(By.cssSelector(selector)).click();
			}
		}
		// 以下1日回
		if (true) {
			// ■web検索
			// propertiesファイルから単語リストを抽出して、ランダムで5つ（数は指定可能）
			String[] wordSearchList = Utille.getWordSearchList(wordList, 5);
			driver.get("http://ecnavi.jp/search/web/?Keywords=");
			int ecnSearchNum = 4;
			for (int i = 0; i < ecnSearchNum; i++) {
				String selector = "input[name='Keywords']";
				if (isExistEle(driver, selector)) {
					WebElement ele = driver.findElement(By.cssSelector(selector));
					ele.clear();
					log.info("検索keyword[" + wordSearchList[i]);
					ele.sendKeys(wordSearchList[i]);
					driver.findElement(By.cssSelector("button[type='submit']")).click();
					Utille.sleep(3000);
				}
			}

			// ■検索募金
			wordSearchList = Utille.getWordSearchList(wordList, 2);
			int ecnSearchBokinNum = 2;
			for (int i = 0; i < ecnSearchBokinNum; i++) {
				driver.get("http://ecnavi.jp/smile_project/search_fund/");
				String selector = "input[name='Keywords']";
				if (isExistEle(driver, selector)) {
					WebElement ele = driver.findElement(By.cssSelector("input[name='Keywords']"));
					ele.clear();
					log.info("検索keyword[" + wordSearchList[i]);
					ele.sendKeys(wordSearchList[i]);
					driver.findElement(By.cssSelector("button[type='submit']")).click();
					Utille.sleep(3000);
				}
			}

			// ■クリック募金
			driver.get("http://point.ecnavi.jp/fund/bc/");
			if (isExistEle(driver, "div.bnr_box")) {
				List<WebElement> eleList1 = driver.findElements(By.cssSelector("div.bnr_box"));
				for (WebElement ele : eleList1) {
					WebElement eleTarget = ele.findElement(By.cssSelector("a"));
					eleTarget.click();
					Utille.sleep(3000);
				}
			}

			// ■ドロンバナークリック2種
			String[] dronUrlList = { "http://ecnavi.jp/", "http://ecnavi.jp/pointboard/#doron" };
			for (int i = 0; i < dronUrlList.length; i++) {
				driver.get(dronUrlList[i]);
				String selector = "div#doron a.item";
				if (isExistEle(driver, selector)) {
					driver.findElement(By.cssSelector(selector)).click();
				}
			}

			// ■教えてどっち
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

			// ■毎日ニュース
			int ecnNewsClickCnt = 0;
			int ecnNewsClickNorma = 5;
			for (int i = 0; ecnNewsClickCnt != ecnNewsClickNorma; i++) {
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
					ecnNewsClickCnt++;
					Utille.sleep(3000);
				}
				else if (isExistEle(driver, "p.got_maxpoints")) {
					break;
				}
			}

			// ■珍獣先生
			Mission EcnMissionChinjyu = new EcnMissionChinjyu(log);
			EcnMissionChinjyu.roopMisstion(driver);
			missionList.add(EcnMissionChinjyu);
		}
	}

	private static void goToClickGMY(WebDriver driver) {

		if (true) {
			// top
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
			// clipoバナー
			driver.get("http://dietnavi.com/pc/daily_click.php");
			if (isExistEle(driver, selector)) {
				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
				int size = eleList.size();
				for (int i = 0; i < size; i++) {
					eleList.get(i).click();
					Utille.sleep(2000);
				}
			}
			// チラシ
			driver.get("http://dietnavi.com/pc/");
			selector = "#chirashi_check";
			if (isExistEle(driver, selector)) {
				driver.findElement(By.cssSelector(selector)).click();
				Utille.sleep(2000);
			}
		}
	}

	private static void goToClickGEN(WebDriver driver) {

		if (true) {
			String selector = "a img[src$='star.gif']";
			if (isExistEle(driver, selector)) {
				driver.findElement(By.cssSelector(selector)).click();
			}
		}

		if (true) {
			// ポイントの森
			driver.get("http://www.gendama.jp/forest/");
			String currentWindowId = driver.getWindowHandle();
			log.info("currentWindowId: " + currentWindowId);
			String selecter[] = { "#forestBox a[href^='/cl/?id=']", "#downBox a[href^='/cl/?id=']",
					"#sponsor a[href^='/cl/?id=']" };
			for (int j = 0; j < selecter.length; j++) {
				String selector = selecter[j];
				log.info("selector: " + selector);
				if (isExistEle(driver, selector)) {
					List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
					int size = eleList.size();
					for (int i = 0; i < size; i++) {
						log.info("text: " + driver.findElements(By.cssSelector(selector)).get(i).getText());
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
			log.info("selector: end");
		}
	}
}
