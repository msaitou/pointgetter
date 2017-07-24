package pointGet.mission.pic;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerManga;
import pointGet.mission.parts.AnswerPhotoEnk;
import pointGet.mission.parts.AnswerUranai;

public class PICMedalMool extends PICBase {
  final String url = "https://pointi.jp/";
  WebDriver driver = null;

  AnswerManga Manga = null;
  AnswerPhotoEnk PhotoEnk = null;
  AnswerColum Colum = null;
  AnswerUranai Uranai = null;

  /**
   * @param logg
   */
  public PICMedalMool(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "メダルモール");
    Colum = new AnswerColum(logg);
    PhotoEnk = new AnswerPhotoEnk(logg);
    Manga = new AnswerManga(logg);
    Uranai = new AnswerUranai(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    selector = "a[href='//pointi.jp/api/medal_mall.php']>span";
    int skip = 0;

    String sele3 = "div.enq-submit>button[type='submit']", //
    sele8 = "div#buttonArea>input[name='next']", //
    sele2 = "form>input[type='image']"// 回答する 漫画用
    ;

    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 3000); // 遷移
      // 占い
      // 写真
      // コラム
      // 漫画

      String[] preSeleList = {
//          "a[href*='pc/uranai']",
//          "a[href*='cosmeticsstyle.com/pointi/list']",
//          "a[href*='fashion-cosmelife.com/pointi']",
          "a[href*='comicEnquete']" };
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
        else if (preSele.equals("a[href*='cosmeticsstyle.com/pointi/list']")) {
          clickSleepSelector(driver, preSele, 3000); // 遷移
          String wid = driver.getWindowHandle();
          changeWindow(driver, wid);
          selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
          Utille.sleep(5000);
          String sele1 = "form>input.next_bt";
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            int size2 = eleList.size(), targetIndex = size2 - 1;
            logg.info("size2:" + size2 + " target:" + targetIndex);
            if (size2 > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
              Utille.scrolledPage(driver, eleList.get(targetIndex));
              clickSleepSelector(eleList, targetIndex, 5000); // アンケートスタートページ
              if (isExistEle(driver, sele1)) {
                PhotoEnk.answer(driver, sele1, wid);
              }
              else {
                driver.close();
                driver.switchTo().window(wid);
              }
            }
          }
          else {
            k++;
          }
        }
        else if (preSele.equals("a[href*='fashion-cosmelife.com/pointi']")) {
          clickSleepSelector(driver, preSele, 3000); // 遷移
          String wid = driver.getWindowHandle();
          changeWindow(driver, wid);
          selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
          String seleNextb2 = "form>input[type='image']";
          if (isExistEle(driver, selector)) {
            clickSleepSelector(driver, selector, 4000); // 遷移　問開始
            if (isExistEle(driver, seleNextb2)) {
              Colum.answer(driver, seleNextb2, wid);
            }
            else {
              driver.close();
              driver.switchTo().window(wid);
            }

            //            driver.navigate().refresh();
            //            Utille.sleep(5000);
          }
          else {
            k++;
          }
//          if (++cnt > 1) {
//            k++;
//            cnt = 0;
//          }
        }
        else if (preSele.equals("a[href*='comicEnquete']")) {
          clickSleepSelector(driver, preSele, 3000); // 遷移
          String wid = driver.getWindowHandle();
          selector = "a.ui-btn.ui-btn-a"; // 回答する
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            if (isExistEle(eleList, 0)) {
              clickSleepSelector(eleList, 0, 5000); // 遷移
              if (isExistEle(driver, sele2)) {
                Manga.answer(driver, sele2, wid);
              }
              else {
                driver.close();
                driver.switchTo().window(wid);
              }
            }
          }
          else {
            k++;
          }
//          if (++cnt > 1) {
//            k++;
//            cnt = 0;
//          }
        }
        Utille.sleep(5000);
      }
    }
  }
}
