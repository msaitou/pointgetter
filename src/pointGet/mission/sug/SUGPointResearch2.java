package pointGet.mission.sug;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerKotsuta;
import pointGet.mission.parts.AnswerManga;
import pointGet.mission.parts.AnswerPointResearch;

public class SUGPointResearch2 extends SUGBase {
  final String url = "http://www.sugutama.jp/survey";
  WebDriver driver = null;
  /* アンケートクラス　ポイントサーチ */
  AnswerPointResearch PointResearch = null;

  AnswerColum Colum = null;
  AnswerManga Manga = null;
  AnswerKotsuta Kotsuta = null;

  /**
   * @param logg
   */
  public SUGPointResearch2(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ポイントリサーチ2");
    PointResearch = new AnswerPointResearch(logg);
    Colum = new AnswerColum(logg);
    Manga = new AnswerManga(logg);
    Kotsuta = new AnswerKotsuta(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    selector = "div.tab_content2 dd>span>a";
    int skip = 1;
    String
    //    sele1 = "div.ui-control.type-fixed>a.ui-button",
    sele6 = "form>input.next_bt", // コラム用
        sele2 = "form>input[type='image']", // 回答する 漫画用
        sele1 = "div.page-content-button>input.button.btn-next", // 回答する 漫画用
        sele1_ = "input[type='button']", a = "";
//    Utille.sleep(10000);
    while (true) {
      waitTilReady(driver);
      if (!isExistEle(driver, selector, false)) {
        // 対象がなくなったら終了
        break;
      }
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      int size = eleList.size(), targetIndex = skip - 1; // 順番はサイト毎に変更可能だが、変数を使う
      if (size > targetIndex
          && isExistEle(eleList, targetIndex)) {
        clickSleepSelector(eleList, targetIndex, 6000); // アンケートスタートページ
        String wid = driver.getWindowHandle();
        changeWindow(driver, wid);
        String cUrl = driver.getCurrentUrl();

        //          if (isExistEle(driver, sele1)) {
        //            PointResearch.answer(driver, sele1, wid);
        //          }
        //
        if (cUrl.indexOf("www.netmile.co.jp/ctrl/user") >= 0
            && isExistEle(driver, sele1_)) {
          clickSleepSelector(driver, sele1_, 4000); // なぜだかませぺーじ
          if (isExistEle(driver, sele2)) {
            Kotsuta.answer(driver, sele1, wid);
          }
          else {
            skip++;
            driver.close();
            driver.switchTo().window(wid);
          }
        }
        else if (cUrl.indexOf("column-enquete") >= 0
            && isExistEle(driver, sele6)) {
          Colum.answer(driver, sele6, wid);
        }
        // 漫画
        else if (isExistEle(driver, sele2)) {
          Manga.answer(driver, sele2, wid);
          logg.info("manngayたんと動く？");
        }
        else {
          skip++;
          driver.close();
          driver.switchTo().window(wid);
        }
        driver.navigate().refresh();
        Utille.sleep(5000);
      }
      else {
        break;
      }
    }
  }
}
