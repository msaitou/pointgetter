package pointGet.mission.pto;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerAdEnq;
import pointGet.mission.parts.AnswerShindan;

public class PTOChyousadan extends PTOBase {
  final String url = "http://www.pointtown.com/";
  AnswerAdEnq AdEnq = null;
  AnswerShindan Shindan = null;

  /**
   * @param logg
   */
  public PTOChyousadan(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "調査団");
    AdEnq = new AnswerAdEnq(logg);
    Shindan = new AnswerShindan(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    selector = "section.pt-section a[onclick*='CMくじ']>div>img[alt='CMくじ']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 6000); // 遷移
      changeCloseWindow(driver);
      Utille.sleep(3000);
      // ポイントに変換
      String bankSele = "li.icon_h_bank>a", coinSele = "p.h_coin>em", changeSele = "#modal-open.btn",
          changeBtnSele = "#modal-content2[style*='display: block;']>a.btn";
      if (isExistEle(driver, coinSele)) {
        String coins = driver.findElement(By.cssSelector(coinSele)).getText();
        int icoins = Integer.parseInt(Utille.getNumber(coins));
        if (icoins >= 100) {
          if (isExistEle(driver, bankSele)) {
            clickSleepSelector(driver, bankSele, 5000); // 遷移
            if (isExistEle(driver, changeSele)) {
              clickSleepSelector(driver, changeSele, 5000);
              if (isExistEle(driver, changeBtnSele)) {
                clickSleepSelector(driver, changeBtnSele, 5000);
              }
            }
          }
        }
      }
      Utille.url(driver, "https://pointtown.cmnw.jp/game/", logg);
      String seleFirst = "img[src*='kumakumachosa']";
      if (isExistEle(driver, seleFirst)) {
        clickSleepSelector(driver, seleFirst, 3000); // 調査一覧へ
        changeCloseWindow(driver);
        Utille.sleep(8000);

        int skip = 0;
        String sele1_ = "iframe.question_frame", //
            sele1 = "form>input[type='submit']", //
            sele4 = "a.submit-btn",
            b = "";
        selector = "div.enquete_box>a dd.title";
        int cn = 0;
        while (isExistEle(driver, selector)) {
          List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
          int size = eleList.size(), targetIndex = skip;
          if (size > targetIndex && isExistEle(eleList, targetIndex)) {
            String wid = driver.getWindowHandle();
            Utille.scrolledPage(driver, eleList.get(targetIndex));
            //            clickSleepSelector(eleList, targetIndex, 3000); // アンケートスタートページ
            clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)).get(targetIndex), 6000); // アンケートスタートページ
            changeWindow(driver, wid);
            String cUrl = driver.getCurrentUrl();
            logg.info("url[" + cUrl + "]");
            if (cUrl.indexOf("ad/enq/") >= 0
                && isExistEle(driver, sele1_)) {
              // $('iframe').contents().find("div>input[type='submit']")
              AdEnq.answer(driver, sele1, wid);
            }
            else if ((cUrl.indexOf("syouhisya-kinyu.com/agw3") >= 0)
                && isExistEle(driver, sele4)) {
              Shindan.answer(driver, sele4, wid);
              skip++;
            }
            else {
              skip++;
              driver.close();
              driver.switchTo().window(wid);
            }
            Utille.refresh(driver, logg);
            Utille.sleep(5000);
            //            // 回数を制限する
            //            if (cn++ > 2) {
            //              break;
            //            }
          }
          else {
            break;
          }
        }
      }
    }
  }
}
