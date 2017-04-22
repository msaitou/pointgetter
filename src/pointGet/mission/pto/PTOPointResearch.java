package pointGet.mission.pto;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.Utille;

public class PTOPointResearch extends PTOBase {
	final String url = "http://www.pointtown.com/ptu/pointpark/enquete/top.do";
	WebDriver driver = null;

	/**
	 * @param logg
	 */
	public PTOPointResearch(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "ポイントリサーチ");
	}

	@Override
	public void privateMission(WebDriver driverAtom) {
		driver = driverAtom;
		driver.get(url);
		int skip = 1;
		Utille.sleep(3000);
		while (true) {
			selector = "td>span.promo_enq_bt";
			List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
			int size = eleList.size(), targetIndex = skip - 1; // 順番はサイト毎に変更可能だが、変数を使う
			if (size > targetIndex
					&& isExistEle(eleList, targetIndex)) {
				String wid = driver.getWindowHandle();
				clickSleepSelector(eleList, targetIndex, 10000); // アンケートスタートページ
				changeWindow(driver, wid);
				selector = "div.ui-control.type-fixed>a.ui-button";
				String sele3 = "div>button[type='submit']", // 回答する surveyenk用
				sele1 = "div.question_btn>input[type='submit']";
				if (isExistEle(driver, selector)) {
					_answerPointResearch(selector, wid);
				}
				else if (isExistEle(driver, sele1)) {
					_answerAdserver(sele1, wid);
					Utille.sleep(3000);
				}
				else if (isExistEle(driver, sele3)) {
					closeOtherWindow(driver);
					_answerSurveyEnk(sele3, "");
				}
				else {
					skip++;
					driver.close();
					driver.switchTo().window(wid);
				}
			}
			else {
				break;
			}
		}
	}

	/** 
	 * 
	 * @param sele3
	 * @param wid
	 */
	private void _answerPointResearch(String sele3, String wid) {
		closeOtherWindow(driver);
		clickSleepSelector(driver, selector, 4000);
		// 回答開始
		selector = "form>input.ui-button";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 4000);
			String choiceSele = "label.ui-label-radio", seleNext2 = "div.fx-control>input.ui-button", seleSele = "select.ui-select", overLay = "div.overlay-popup a.button-close", noSele = "div.ui-item-no", titleSele = "h2.ui-item-title", checkSele = "label.ui-label-checkbox";
			// 12問
			for (int k = 1; k <= 13; k++) {
				if (!isExistEle(driver, "div.overlay-popup[style*='display: none;'] a.button-close", false)
						&& isExistEle(driver, overLay)) {
					checkOverlay(driver, overLay, false);
				}
				if (isExistEle(driver, noSele)) {
					String qNo = driver.findElement(By.cssSelector(noSele)).getText();
					String qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
					logg.info(qNo + " " + qTitle);
					int choiceNum = 0;
					if (isExistEle(driver, choiceSele)) {
						int choiceies = getSelectorSize(driver, choiceSele);
						switch (k) {
							case 1: // 性別
							case 3: // 結婚
								// 1問目は1：男
								// 3問目は3：未婚
								break;
							case 5: // 職業
							case 2: // 年齢
								// 2問目は3：30代
								if (choiceies > 2) {// 一応選択可能な範囲かをチェック
									choiceNum = 2;
								}
								break;
							default:
								choiceNum = Utille.getIntRand(choiceies);
						}
						List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
						if (isExistEle(eleList2, choiceNum)) {
							// 選択
							clickSleepSelector(eleList2.get(choiceNum), 3000);
						}
					}
					else if (isExistEle(driver, checkSele)) {
						int size4 = getSelectorSize(driver, checkSele);
						choiceNum = Utille.getIntRand(size4);
						List<WebElement> eleList2 = driver.findElements(By.cssSelector(checkSele));
						if (isExistEle(eleList2, choiceNum)) {
							// 選択
							clickSleepSelector(eleList2.get(choiceNum), 3000);
						}
					}
					else if (isExistEle(driver, seleSele)) {
						Utille.sleep(2000);
						int size3 = getSelectorSize(driver, seleSele + ">option");
						String value = "";
						if (qTitle.indexOf("居住区") >= 0) {
							value = "4";
						}
						else {
							choiceNum = Utille.getIntRand(size3);
							value = driver.findElements(By.cssSelector(seleSele + ">option"))
									.get(choiceNum).getAttribute("value");
						}
						Select selectList = new Select(driver.findElement(By.cssSelector(seleSele)));
						selectList.selectByValue(value);
						Utille.sleep(3000);
					}
				}
				else {
					break;
				}
				if (isExistEle(driver, seleNext2)) {
					// 次へ
					clickSleepSelector(driver, seleNext2, 4500);
				}
			}
			Utille.sleep(2000);
			if (!isExistEle(driver, "div.overlay-popup[style*='display: none;'] a.button-close", false)
					&& isExistEle(driver, overLay)) {
				checkOverlay(driver, overLay, false);
			}
			if (isExistEle(driver, selector)) {
				// ポイント獲得
				clickSleepSelector(driver, selector, 3000);
			}
			if (isExistEle(driver, overLay)) {
				checkOverlay(driver, overLay, false);
			}
			String closeSele = "div.ui-control>a";
			if (isExistEle(driver, closeSele)) {
				// 閉じる
				clickSleepSelector(driver, closeSele, 3000);
			}
			// point一覧に戻る
		}
	}
	
	/**
	 *
	 * @param sele3
	 * @param wid
	 */
	private void _answerSurveyEnk(String sele3, String wid) {
		// 回答開始
		clickSleepSelector(driver, sele3, 3000);
		String qTitleSele = "div.question-label", qTitle = "", radioSele = "label.item-radio", checkboxSele = "label.item-checkbox", choiceSele = "", seleSele = "select.mdl-textfield__input";
		for (int k = 1; k <= 15; k++) {
			int choiceNum = 0;
			qTitle = "";
			choiceSele = "";
			if (isExistEle(driver, qTitleSele)) {
				qTitle = driver.findElement(By.cssSelector(qTitleSele)).getText();
				logg.info("[" + qTitle + "]");
			}
			if (isExistEle(driver, radioSele)) { // ラジオ
				choiceSele = radioSele;
			}
			else if (isExistEle(driver, checkboxSele)) { // チェックボックス
				choiceSele = checkboxSele;
			}
			else if (isExistEle(driver, seleSele)) {// セレクトボックス
				choiceSele = seleSele;
			}
			if (radioSele.equals(choiceSele)
					|| checkboxSele.equals(choiceSele)) {
				int choiceies = getSelectorSize(driver, choiceSele);
				switch (k) {
					case 13: // 性別
						//									case 3: // 結婚
						// 13問目は1：男
						// 3問目は3：未婚
						break;
					//									case 5: // 職業
					case 14: // 年齢
						// 2問目は3：30代
						if (choiceies > 3) {// 一応選択可能な範囲かをチェック
							choiceNum = 3;
						}
						break;
					default:
						choiceNum = Utille.getIntRand(choiceies);
				}
				List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
				if (isExistEle(eleList2, choiceNum)) {
					// 選択
					clickSleepSelector(eleList2.get(choiceNum), 2500);
				}
			}
			else if (seleSele.equals(choiceSele)) {
				Utille.sleep(2000);
				int size3 = getSelectorSize(driver, seleSele + ">option");
				String value = "";
				if (qTitle.indexOf("住まい") >= 0) {
					value = "14";
				}
				else {
					choiceNum = Utille.getIntRand(size3);
					value = driver.findElements(By.cssSelector(seleSele + ">option"))
							.get(choiceNum).getAttribute("value");
				}
				Select selectList = new Select(driver.findElement(By.cssSelector(seleSele)));
				selectList.selectByValue(value);
				Utille.sleep(3000);
			}
			if (isExistEle(driver, sele3)) {
				// 次へ
				clickSleepSelector(driver, sele3, 4000);
			}
		}
		Utille.sleep(3000);
		if (isExistEle(driver, sele3)) {
			// 次へ
			clickSleepSelector(driver, sele3, 4000);
		}
		// close
		String closeSele = "input.btn_close_en";
		if (isExistEle(driver, closeSele)) {
			clickSleepSelector(driver, closeSele, 4000);
		}
		//			driver.close();
		// 最後に格納したウインドウIDにスイッチ
		//		driver.switchTo().window(wid);
	}
	
	private void _answerAdserver(String sele, String wid) {
		String choiceSele, 
				radioSele = "label.radiolabel",
				overLay = "div#center-frame>img", 
				noSele = "h2>span.q_number",
				titleSele = "h2>span.q_text",
				qTitle
				;
		clickSleepSelector(driver, sele, 4000);
		Utille.sleep(4000);
		for (int k = 1; k <= 20; k++) {
			int choiceNum = 0;
			qTitle = "";
			choiceSele = "";
			if (isExistEle(driver, noSele)) {
				String qNo = driver.findElement(By.cssSelector(noSele)).getText();
				qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
				logg.info(qNo + " " + qTitle);
				if (isExistEle(driver, radioSele)) { // ラジオ
					choiceSele = radioSele;
				}
				// 回答選択
				if (radioSele.equals(choiceSele)
//						|| checkboxSele.equals(choiceSele)
							) {
					int choiceies = getSelectorSize(driver, choiceSele);
					if (qTitle.indexOf("あなたの性別を教えてください") >= 0) {
						choiceNum = 0; // 1：男
					}
					else if (qTitle.indexOf("あなたの年齢を教えてください") >= 0) {
						choiceNum = 3; // 4：30代
					}
					else if (qTitle.indexOf("あなたの職業を教えてください") >= 0) {
						choiceNum = 3; // 3：会社員
					}
					else if (qTitle.indexOf("あなたのお住まいの都道府県を教えてください") >= 0) {
						choiceNum = 3; // 3：関東
					}
					else {
						choiceNum = Utille.getIntRand(choiceies);
					}
					List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
					if (isExistEle(eleList2, choiceNum)) {
						// 選択
						clickSleepSelector(eleList2.get(choiceNum), 3000);
						if (isExistEle(driver, sele)) {
							clickSleepSelector(driver, sele, 6000);
						}
					}
				}
			}
			else {
				break;
			}
		}
		Utille.sleep(5000);
		// [style*='visibility: visible']
		checkOverlay(driver, overLay, false);
		if (!isExistEle(driver, "div#fade-layer[style*='display: none']", false)) {
			checkOverlay(driver, overLay, false);
		}
		// close
		String closeSele = "div.question_btn>input[name='submit']";
		if (isExistEle(driver, closeSele)) {
			clickSleepSelector(driver, closeSele, 4000);
			driver.switchTo().window(wid);
			driver.navigate().refresh();
		}
	}
}
