package pointGet.mission.mop;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;

/**
 *
 * @author saitou 0時、12時開催
 */
public class MOPEnglishTest extends MOPBase {
	final String url = "http://pc.moppy.jp/gamecontents/";

	/**
	 * @param logg
	 */
	public MOPEnglishTest(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "英単語Test");
	}

	@Override
	public void privateMission(WebDriver driver) {
		Utille.url(driver, url, logg);
    selector = "img[alt='英単語TEST']";
		if (isExistEle(driver, selector)) {
			clickSleepSelectorNoRefre(driver, selector, 2000); // 遷移
			changeCloseWindow(driver);
			checkOverlay(driver, "div.overlay-popup a.button-close");
      String exchangeSele = "a.stamp__btn", //
          exchangeListSele = "select.exchange__selection",
          doExchangeSele = "input.exchange__btn",
          returnTopSele = "a.stamp__btn.stamp__btn-return";
      Utille.sleep(2000);
      // スタンプ変換
      if (isExistEle(driver, exchangeSele)) {
        List<WebElement> elems = driver.findElements(By.cssSelector(exchangeSele));
        for (int ii = 0; ii < elems.size(); ii++) {
          if (isExistEle(elems, ii)) {
            Utille.scrolledPage(driver, elems.get(ii));
            if ("スタンプ交換".equals(elems.get(ii).getText())) {
              clickSleepSelectorNoRefre(driver, elems, ii, 3000); // 遷移

              if (isExistEle(driver, exchangeListSele)) {
                int size = getSelectorSize(driver, exchangeListSele + ">option");
                String value = driver.findElements(By.cssSelector(exchangeListSele + ">option"))
                    .get(size - 1).getAttribute("value");
                Select selectList = new Select(driver.findElement(By.cssSelector(exchangeListSele)));
                selectList.selectByValue(value); // 交換ポイントを選択
                Utille.sleep(3000);
                if (isExistEle(driver, doExchangeSele)) {
                  clickSleepSelectorNoRefre(driver, doExchangeSele, 4000); // i=1 交換する　i=2 本当に
                }
              }
              // Topへ戻る
              if (isExistEle(driver, returnTopSele)) {
                clickSleepSelectorNoRefre(driver, returnTopSele, 4000);
              }
              break;
            }
          }
        }
      }
			// finish condition
			String finishSelector = "p.ui-timer";
			if (isExistEle(driver, finishSelector)) {
				finsishFlag = true;
				return;
			}
			selector = "div.ui-control>form>input[name='submit']";
			String noSele = "div.ui-item-no", titleSele = "h2.ui-item-title";
			Utille.sleep(4000);
			if (isExistEle(driver, selector)) {
				clickSelectorNoRefre(driver, selector);
				for (int i = 0; i < 8; i++) {
					Utille.sleep(4000);
					String selectYoubi = "";
					if (isExistEle(driver, noSele)) {
						String qNo = driver.findElement(By.cssSelector(noSele)).getText();
						String qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
						logg.info(qNo + " " + qTitle);
					}
					//					if (isExistEle(driver, selectorDay)) {
					//						String text = driver.findElement(By.cssSelector(selectorDay)).getText();
					//						logg.info("testです　" + text);
					//						String str = text;
					//						String regex = "今日の(\\d+)日後は何曜日？";
					//						Pattern p = Pattern.compile(regex);
					//						Matcher m = p.matcher(str);
					//						System.out.println("hajimari");
					//						if (m.find()) {
					//							String strAfterDayNum = m.group(1);
					//							selectYoubi = Utille.getNanyoubi(strAfterDayNum);
					//							System.out.println("なんにちです　[" + selectYoubi + "]");
					//						}
					//						System.out.println("owari");
					//					} else {
					//						logg.info("not get after days");
					//						return;
					//					}
					selectYoubi = String.valueOf(Utille.getIntRand(4) + 1);
					selector = "ul.ui-item-body"; // 曜日要素全体
					if (isExistEle(driver, selector)) {
						// label.ui-label-radio.ui-circle-button[for='radio-1']
						String selectId = "label.ui-label-radio[for='radio-" + selectYoubi + "']";
						String selector2 = "input[name='submit'].ui-button.ui-button-b.ui-button-answer.quake";
						if (isExistEle(driver, selectId)) {
							clickSleepSelectorNoRefre(driver, selectId, 4000); // 遷移
							int ranSleep = Utille.getIntRand(9);
							Utille.sleep(ranSleep * 1000);
//							waitTilReady(driver);
							if (isExistEle(driver, selector2)) {
//								waitTilReady(driver);
								clickSleepSelectorNoRefre(driver, selector2, 4000); // 遷移
								checkOverlay(driver, "div.overlay-popup a.button-close");
								String selector3 = "input[name='submit'].ui-button.ui-button-b.ui-button-result.quake";
								String selector4 = "input[name='submit'].ui-button.ui-button-b.ui-button-end.quake";
								if (isExistEle(driver, selector3)) {
									clickSleepSelectorNoRefre(driver, selector3, 5000); // 遷移
									checkOverlay(driver, "div.overlay-popup a.button-close");
									if (isExistEle(driver, selector4)) {
										clickSleepSelectorNoRefre(driver, selector4, 5000); // 遷移
										checkOverlay(driver, "div.overlay-popup a.button-close");
									}
								}
							}
						}
					}
				}
				logg.info(this.mName + "]kuria?");
				selector = "div.fx-control>a.ui-button.ui-button-a.ui-button-close.quake";
				if (isExistEle(driver, selector)) {
					clickSleepSelectorNoRefre(driver, selector, 3000);
					waitTilReady(driver);
				}
			}
			else {
				logg.warn(this.mName + "]獲得済み");
			}
			finsishFlag = true;
		}
	}
}
