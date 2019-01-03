package pointGet.mission.gpo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerQuiz;
import pointGet.mission.parts.AnswerUranai;

public class GPOMedalMool extends GPOBase {
  final String url = "https://www.gpoint.co.jp/gpark/";
  WebDriver driver = null;

  AnswerUranai Uranai = null;
  AnswerQuiz Quiz = null;

  /**
   * @param logg
   */
  public GPOMedalMool(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "メダルモール");
    Uranai = new AnswerUranai(logg);
    Quiz = new AnswerQuiz(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);

    String enkLinkSele = "div.bnr>[alt='メダルモール']", //
    a = "";

    if (isExistEle(driver, enkLinkSele)) {
      clickSleepSelector(driver, enkLinkSele, 4000); // 遷移
      changeCloseWindow(driver);
      String wid0 = driver.getWindowHandle();
      changeWindow(driver, wid0);

      String[] preSeleList = {
          "img[src*='main_quiz05']", // 食べ物クイズ
          "img[src*='main_quiz04']", // 動物クイズ
          "img[src*='main_quiz03']", // 書籍クイズ
          "img[src*='main_quiz02']", // 海外クイズ
          "img[src*='main_quiz01']", // 漢字クイズ
//          "img[src*='main_ab_new']", // 2卓アンケート
          //          "a[href*='pc/uranai']",
      };
      int cnt = 0;
      for (int k = 0; k < preSeleList.length;) {
        String preSele = preSeleList[k];
        if (preSele.equals("a[href*='pc/uranai']")) {
          clickSleepSelector(driver, preSele, 7000); // 遷移
          String wid = driver.getWindowHandle();
          changeWindow(driver, wid);
          Uranai.answer(driver, "", wid);
          k++;
        }
        else if (preSele.equals("img[src*='main_quiz05']") // 食べ物クイズ
            || preSele.equals("img[src*='main_quiz04']") // 動物クイズ
            || preSele.equals("img[src*='main_quiz03']") // 書籍クイズ
            || preSele.equals("img[src*='main_quiz02']") // 海外クイズ
            || preSele.equals("img[src*='main_quiz01']") // 漢字クイズ
        ) {
          clickSleepSelector(driver, preSele, 3000); // 遷移

          String wid = wid0;
          changeWindow(driver, wid);
          selector = "section#mainContent ul>li>a"; // アンケート一覧の回答するボタン
          Utille.sleep(5000);
          String sele = "a>img.next_bt";
          String overLay = "div#interstitial[style*='display: block']>div>div#inter-close";
          checkOverlay(driver, overLay, false);

          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            int size2 = eleList.size(), targetIndex = size2 - 1;

            logg.info("size2:" + size2 + " target:" + targetIndex);
            if (size2 > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
              Utille.scrolledPage(driver, eleList.get(targetIndex));
              clickSleepSelector(driver, eleList, targetIndex, 5000); // アンケートスタートページ

              if (isExistEle(driver, sele)) {
                Quiz.answer(driver, sele, wid);
                driver.switchTo().window(wid);
              }
              else {
                driver.close();
                driver.switchTo().window(wid);
              }
            }
          }
          else {
            driver.close();
            driver.switchTo().window(wid);
            k++;
          }
        }
        Utille.sleep(5000);
      }
    }
  }
}
