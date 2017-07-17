package pointGet.mission.parts;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerUranai extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerUranai(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String nextSelector = "div#next-button a>img", //
    symbleSelector = "div#symbols-next-button a>img", //
    startSelector = "div#parts-slide-button__action a>img", // 占い始める
    a = "";
    String selector1 = "section>div>form>input[type=image]";
    String selectList[] = { startSelector, selector1 };

    // 全体へ
    for (int g = 0; g < selectList.length; g++) {
      if (isExistEle(driver, selectList[g])) {
        clickSleepSelector(driver, selectList[g], 3000); // 遷移
        // 全体へ

        boolean endFlag = false;
        for (int j = 0; j < 20; j++) { // 20に特に意味なし
          // エンドレスループを避けるため
          // overlayを消して
          if (!isExistEle(driver, "div#inter-ad[style*='display: none'] div#inter-ad-close", false)) {
            Utille.sleep(3000);
            if (isExistEle(driver, "div#inter-ad div#inter-ad-close")) {
              checkOverlay(driver, "div#inter-ad div#inter-ad-close", false);
              endFlag = true;
            }
          }

          if (isExistEle(driver, nextSelector)) {
            clickSleepSelector(driver, nextSelector, 3000); // 遷移
          }
          else if (isExistEle(driver, startSelector)) {
            clickSleepSelector(driver, startSelector, 3000); // 遷移
          }
          else if (isExistEle(driver, symbleSelector)) {
            clickSleepSelector(driver, symbleSelector, 3000); // 遷移
          }
          else if (isExistEle(driver, selector1)) {
            clickSleepSelector(driver, selector1, 3000); // 遷移
          }
          Utille.sleep(3000);
          if (endFlag) {
            break;
          }
        }
      }
    }
    driver.close();
    driver.switchTo().window(wid);
  }
}
