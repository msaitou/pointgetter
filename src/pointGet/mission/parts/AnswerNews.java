package pointGet.mission.parts;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.mission.MissCommon;

public class AnswerNews extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerNews(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    clickSleepSelector(driver, startSele, 3000);

    String agreeSele = "div.terms_info label", //
        readEndSele = "div.news_footer>label",
    seleNextb2 = "form>input[alt='進む']", //
    seleEnd = "form>input[type='image']", //
    overLayNone = "div#inter-ad-content[style*='display: none;'] div#inter-ad-close", //
    overLay = "div#inter-ad-content div#inter-ad-close", //
    choiceSele = "ul#enqueteUl>li>label", // ラジオセレクター
    seleNext2 = "div>input.enquete_nextbt", //　次へセレクター
    seleNext3 = "div>input.enquete_nextbt_2", //　次へセレクター2
    seleSele = "form.menu>select", // プルダウンセレクター
    titleSele = "p#enqueteTitle", a = "";

    if (isExistEle(driver, agreeSele)) {
      clickSleepSelector(driver, agreeSele, 4000);
      if (isExistEle(driver, startSele)) {
        clickSleepSelector(driver, startSele, 3000);
//        if (isExistEle(driver, startSele)) {
//          clickSleepSelector(driver, startSele, 3000);
//        }
      }
    }

//    clickSleepSelector(driver, startSele, 4000); // 遷移　問開始
    for (int g = 0; g < 10; g++) {
      if (isExistEle(driver, readEndSele)) {
//        waitTilReady(driver);
        clickSleepSelector(driver, readEndSele, 3000); // 遷移　問開始するよ
        if (isExistEle(driver, startSele)) {
          clickSleepSelector(driver, startSele, 6000);
        }
      }
    }
    
    if (isExistEle(driver, startSele)) {
      clickSleepSelector(driver, startSele, 10000);
    }
    if (!isExistEle(driver, overLayNone, false)) {
      checkOverlay(driver, overLay);
      clickSleepSelector(driver, startSele, 6000);
    }
//    driver.close();
//    driver.switchTo().window(wid);
  }
}
