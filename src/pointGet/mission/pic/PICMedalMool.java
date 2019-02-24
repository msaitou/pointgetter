package pointGet.mission.pic;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerAbEnk;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerComicNews;
import pointGet.mission.parts.AnswerCooking;
import pointGet.mission.parts.AnswerHirameki;
import pointGet.mission.parts.AnswerHyakkey;
import pointGet.mission.parts.AnswerIjin;
import pointGet.mission.parts.AnswerKansatu;
import pointGet.mission.parts.AnswerKenkou;
import pointGet.mission.parts.AnswerManga;
import pointGet.mission.parts.AnswerNews;
import pointGet.mission.parts.AnswerPhotoEnk;
import pointGet.mission.parts.AnswerQuiz;
import pointGet.mission.parts.AnswerUranai;

public class PICMedalMool extends PICBase {
  final String url = "https://pointi.jp/game/";
  WebDriver driver = null;

  AnswerManga Manga = null;
  AnswerPhotoEnk PhotoEnk = null;
  AnswerColum Colum = null;
  AnswerUranai Uranai = null;
  AnswerKenkou Kenkou = null;
  AnswerKansatu Kansatu = null;
  AnswerCooking Cooking = null;
  AnswerHyakkey Hyakkey = null;

  AnswerNews News = null;
  AnswerComicNews cNews = null;
  AnswerHirameki Hirameki = null;
  AnswerIjin Ijin = null;
  AnswerQuiz Quiz = null;
  AnswerAbEnk AbEnk = null;

  /**
   * @param logg
   */
  public PICMedalMool(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "メダルモール");
    Colum = new AnswerColum(logg);
    PhotoEnk = new AnswerPhotoEnk(logg);
    Manga = new AnswerManga(logg);
    News = new AnswerNews(logg);
    Uranai = new AnswerUranai(logg);
    Kenkou = new AnswerKenkou(logg);
    Cooking = new AnswerCooking(logg);
    Hyakkey = new AnswerHyakkey(logg);
    Kansatu = new AnswerKansatu(logg);
    cNews = new AnswerComicNews(logg);
    Hirameki = new AnswerHirameki(logg);
    Ijin = new AnswerIjin(logg);
    Quiz = new AnswerQuiz(logg);
    AbEnk = new AnswerAbEnk(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    selector = "img[alt='メダルモール']";
    int skip = 0;

    String sele3 = "div.enq-submit>button[type='submit']", //
    sele8 = "div#buttonArea>input[name='next']", //
    sele4 = "form>input[alt='進む']", sele2 = "form>input[type='image']", // 回答する 漫画用
    sele1 = "a>img[alt='次へ']",
    sele6 = "form>input.next_bt",

    sele5 = "img[alt='進む']";

    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 3000); // 遷移
      String wid0 = driver.getWindowHandle();
      changeCloseWindow(driver);
      // 占い
      // 写真
      // コラム
      // 漫画

      String[] preSeleList = {
          "img[src*='main_ab']", // 2卓アンケート
          "img[src*='main_quiz05']", // 食べ物クイズ
          "img[src*='main_quiz04']", // 動物クイズ
          "img[src*='main_quiz03']", // 書籍クイズ
          "img[src*='main_quiz02']", // 海外クイズ
          "img[src*='main_quiz01']", // 漢字クイズ
           "a[href*='ijin']",
          "img[src='common/images/contents/main_hirameki.png']",
          "a[href*='/comicnews/']",
          "a[href*='/comedynews/']",
          "a[href*='sarasara']",
          "a[href*='natural-cuisine.com/pointi']", // 料理
          "a[href*='eyemake-beauty.com/pointi']", // 観察
          "a[href*='beautyhair-fashion.com/pointi']", // 百景
          "a[href*='/news/'] p.thum>img",
          "a[href*='cosmeticsstyle.com/pointi/list']",
          "img[src='common/images/contents/main_column.png']",
          "a[href*='comicEnquete']",
          "a[href*='pc/uranai']",
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
        // ひらめき
        else if (preSele.equals("img[src='common/images/contents/main_hirameki.png']")) {
          clickSleepSelector(driver, preSele, 3000); // 遷移
          String wid = driver.getWindowHandle();
          changeWindow(driver, wid);
          selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
          Utille.sleep(5000);
          String sele = "form>input.next_bt";
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            int size2 = eleList.size(), targetIndex = size2 - 1;
            logg.info("size2:" + size2 + " target:" + targetIndex);
            if (size2 > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
              Utille.scrolledPage(driver, eleList.get(targetIndex));
              clickSleepSelector(driver, eleList, targetIndex, 5000); // アンケートスタートページ
              if (isExistEle(driver, sele)) {
                Hirameki.answer(driver, sele, wid);
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
        // 料理
        else if (preSele.equals("a[href*='natural-cuisine.com/pointi']")) {
          clickSleepSelector(driver, preSele, 3000); // 遷移
          String wid = driver.getWindowHandle();
          changeWindow(driver, wid);
          selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
          Utille.sleep(5000);
          String sele = "form>input.next_bt";
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            int size2 = eleList.size(), targetIndex = size2 - 1;
            logg.info("size2:" + size2 + " target:" + targetIndex);
            if (size2 > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
              Utille.scrolledPage(driver, eleList.get(targetIndex));
              clickSleepSelector(driver, eleList, targetIndex, 5000); // アンケートスタートページ
              if (isExistEle(driver, sele)) {
                Cooking.answer(driver, sele, wid);
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
        // 観察
        else if (preSele.equals("a[href*='eyemake-beauty.com/pointi']")) {
          clickSleepSelector(driver, preSele, 3000); // 遷移
          String wid = driver.getWindowHandle();
          changeWindow(driver, wid);
          selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
          Utille.sleep(5000);
          String sele = "form>input.next_bt";
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            int size2 = eleList.size(), targetIndex = size2 - 1;
            logg.info("size2:" + size2 + " target:" + targetIndex);
            if (size2 > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
              Utille.scrolledPage(driver, eleList.get(targetIndex));
              clickSleepSelector(driver, eleList, targetIndex, 5000); // アンケートスタートページ
              if (isExistEle(driver, sele)) {
                Kansatu.answer(driver, sele, wid);
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
        // 百景
        else if (preSele.equals("a[href*='beautyhair-fashion.com/pointi']")) {
          clickSleepSelector(driver, preSele, 3000); // 遷移
          String wid = driver.getWindowHandle();
          changeWindow(driver, wid);
          selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
          Utille.sleep(5000);
          String sele = "form>input.next_bt";
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            int size2 = eleList.size(), targetIndex = size2 - 1;
            logg.info("size2:" + size2 + " target:" + targetIndex);
            if (size2 > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
              Utille.scrolledPage(driver, eleList.get(targetIndex));
              clickSleepSelector(driver, eleList, targetIndex, 5000); // アンケートスタートページ
              if (isExistEle(driver, sele)) {
                Hyakkey.answer(driver, sele, wid);
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
        else if (preSele.equals("a[href*='cosmeticsstyle.com/pointi/list']")) {
          clickSleepSelector(driver, preSele, 3000); // 遷移
          String wid = driver.getWindowHandle();
          changeWindow(driver, wid);
          selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
          Utille.sleep(5000);
          String sele = "form>input.next_bt";
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            int size2 = eleList.size(), targetIndex = size2 - 1;
            logg.info("size2:" + size2 + " target:" + targetIndex);
            if (size2 > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
              Utille.scrolledPage(driver, eleList.get(targetIndex));
              clickSleepSelector(driver, eleList, targetIndex, 5000); // アンケートスタートページ
              if (isExistEle(driver, sele)) {
                PhotoEnk.answer(driver, sele, wid);
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
        else if (preSele.equals("img[src='common/images/contents/main_column.png']")
            || preSele.equals("a[href*='style-cutehair.com']")) {
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
          }
          else {
            driver.close();
            driver.switchTo().window(wid);
            k++;
            Utille.sleep(3000);
          }
        }
        else if (preSele.equals("a[href*='comicEnquete']")) {
          clickSleepSelector(driver, preSele, 3000); // 遷移
          String wid = driver.getWindowHandle();
          changeWindow(driver, wid);
          selector = "a.ui-btn.ui-btn-a"; // 回答する
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            if (isExistEle(eleList, 0)) {
              clickSleepSelector(driver, eleList, 0, 5000); // 遷移
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
            driver.close();
            driver.switchTo().window(wid);
            k++;
          }
          //          if (++cnt > 1) {
          //            k++;
          //            cnt = 0;
          //          }
        }
        // ニュース
        else if (preSele.equals("a[href*='/news/'] p.thum>img")) {
          clickSleepSelector(driver, preSele, 16000); // 遷移
          String wid = driver.getWindowHandle();
          changeWindow(driver, wid);
          selector = "img[src='./images/top/read_button.png']"; // 回答する
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            if (isExistEle(eleList, 0)) {
              clickSleepSelector(driver, eleList, 0, 5000); // 遷移
              if (isExistEle(driver, sele1)) {
                News.answer(driver, sele1, wid);
                driver.close();
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
        // コミックニュース
        else if (preSele.equals("a[href*='/comicnews/']")) {
          clickSleepSelector(driver, preSele, 16000); // 遷移
          String wid = driver.getWindowHandle();
          changeWindow(driver, wid);
          selector = "div.top-list__news td >a"; // 回答する
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            if (isExistEle(eleList, 0)) {
              clickSleepSelector(driver, eleList, 0, 5000); // 遷移
              if (isExistEle(driver, sele5)) {
                cNews.answer(driver, sele5, wid);
              }
              driver.close();
              driver.switchTo().window(wid);
            }
          }
          else {
            driver.close();
            driver.switchTo().window(wid);
            k++;
          }
        }
        // お笑いニュース
        else if (preSele.equals("a[href*='/comedynews/']")) {
          clickSleepSelector(driver, preSele, 16000); // 遷移
          String wid = driver.getWindowHandle();
          changeWindow(driver, wid);
          selector = "div.top-list__news td >a"; // 回答する
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            if (isExistEle(eleList, 0)) {
              clickSleepSelector(driver, eleList, 0, 5000); // 遷移
              if (isExistEle(driver, sele5)) {
                cNews.answer(driver, sele5, wid);
                driver.close();
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
        // 健康さらさら
        else if (preSele.equals("a[href*='sarasara']")) {
          clickSleepSelector(driver, preSele, 16000); // 遷移
          String wid = driver.getWindowHandle();
          changeWindow(driver, wid);
          selector = "a.ui-btn.ui-btn-a"; // 回答する
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            if (isExistEle(eleList, 0)) {
              clickSleepSelector(driver, eleList, 0, 5000); // 遷移
              if (isExistEle(driver, sele4)) {
                Kenkou.answer(driver, sele4, wid);
                driver.close();
                driver.switchTo().window(wid);
              }
              else {
              }
            }
          }
          else {
            driver.close();
            driver.switchTo().window(wid);
            k++;
          }
        }
        // 偉人
        else if (preSele.equals("a[href*='ijin']")) {
          clickSleepSelector(driver, preSele, 16000); // 遷移
          String wid = driver.getWindowHandle();
          changeWindow(driver, wid);
          selector = "a.ui-btn.ui-btn-a"; // 回答する
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            if (isExistEle(eleList, 0)) {
              clickSleepSelector(driver, eleList, 0, 5000); // 遷移
              if (isExistEle(driver, sele6)) {
                Ijin.answer(driver, sele6, wid);
//                driver.close();
//                driver.switchTo().window(wid);
              }
              else {
              }
            }
          }
          else {
            driver.close();
            driver.switchTo().window(wid);
            k++;
          }
        }
        else if (preSele.equals("img[src*='main_quiz05']") // 食べ物クイズ
            || preSele.equals("img[src*='main_quiz04']") // 動物クイズ
            || preSele.equals("img[src*='main_quiz03']") // 書籍クイズ
            || preSele.equals("img[src*='main_quiz02']") // 海外クイズ
            || preSele.equals("img[src*='main_quiz01']") // 漢字クイズ
        ) {
          clickSleepSelector(driver, preSele, 3000); // 遷移

//          String wid = wid0;
          String wid = driver.getWindowHandle();
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
        // ABアンケート
        else if (preSele.equals("img[src*='main_ab']")) {
          clickSleepSelector(driver, preSele, 3000); // 遷移
          String wid = driver.getWindowHandle();
          changeWindow(driver, wid);
          selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
          Utille.sleep(3000);
          String sele = "form>input.next_bt";
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
                AbEnk.answer(driver, sele, wid);
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
        //        k++;
      }
    }
  }
}
