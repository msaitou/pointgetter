package pointGet.mission.osa;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerEnkShopQP;
import pointGet.mission.parts.AnswerEnqNstk2;
import pointGet.mission.parts.AnswerEnqY2at;
import pointGet.mission.parts.AnswerManga;
import pointGet.mission.parts.AnswerPointResearch;
import pointGet.mission.parts.AnswerSurveyEnk;

public class OSAPointResearch2 extends OSABase {
  final String url = "https://osaifu.com/enquete/";
  WebDriver driver = null;
  /* アンケートクラス　ポイントサーチ */
  AnswerPointResearch PointResearch = null;
  AnswerEnkShopQP EnkShopQP = null;
  AnswerSurveyEnk SurveyEnk = null;
  AnswerEnqY2at EnqY2at = null;
  AnswerManga Manga = null;
  AnswerEnqNstk2 EnqNstk2 = null;

  /**
   * @param logg
   */
  public OSAPointResearch2(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "アンケート");
    PointResearch = new AnswerPointResearch(logg);
    EnkShopQP = new AnswerEnkShopQP(logg);
    SurveyEnk = new AnswerSurveyEnk(logg);
    EnqY2at = new AnswerEnqY2at(logg);
    Manga = new AnswerManga(logg);
    EnqNstk2 = new AnswerEnqNstk2(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    selector = "div.point-research__btn>a.a-btn-cvn";

    if (!isExistEle(driver, selector)) {
      return;
    }
    else {
      clickSleepSelector(driver, selector, 6000); // アンケートスタートページ
    }

    String exchangeSele = "a.stamp__btn", //
    exchangeListSele = "select.exchange__selection", doExchangeSele = "input.exchange__btn", returnTopSele = "a.stamp__btn.stamp__btn-return";
    Utille.sleep(2000);
    // スタンプ変換
    if (isExistEle(driver, exchangeSele)) {
      List<WebElement> elems = driver.findElements(By.cssSelector(exchangeSele));
      for (int ii = 0; ii < elems.size(); ii++) {
        if (isExistEle(elems, ii)) {
          Utille.scrolledPage(driver, elems.get(ii));
          if ("スタンプ交換".equals(elems.get(ii).getText())) {
            clickSleepSelector(driver, elems, ii, 3000); // 遷移

            if (isExistEle(driver, exchangeListSele)) {
              int size = getSelectorSize(driver, exchangeListSele + ">option");
              String value = driver.findElements(By.cssSelector(exchangeListSele + ">option"))
                  .get(size - 1).getAttribute("value");
              Select selectList = new Select(driver.findElement(By.cssSelector(exchangeListSele)));
              selectList.selectByValue(value); // 交換ポイントを選択
              Utille.sleep(3000);
              if (isExistEle(driver, doExchangeSele)) {
                clickSleepSelector(driver, doExchangeSele, 4000); // i=1 交換する　i=2 本当に
              }
            }
            // Topへ戻る
            if (isExistEle(driver, returnTopSele)) {
              clickSleepSelector(driver, returnTopSele, 4000);
            }
            break;
          }
        }
      }
    }
    selector = "table a.ui-button";
    int skip = 1;
    String sele1 = "div.ui-control.type-fixed>a.ui-button", //
    sele2 = "div.question_btn>input[type='submit']"//
    ;// pointResearch用
    while (true) {
      if (!isExistEle(driver, selector)) {
        break;
      }
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      int size = eleList.size(), targetIndex = size - skip;
      if (targetIndex > -1 && targetIndex > -1
          && isExistEle(eleList, targetIndex)) {
        String wid = driver.getWindowHandle();
        clickSleepSelector(driver, eleList, targetIndex, 4000); // アンケートスタートページ
        changeWindow(driver, wid);
        String cUrl = driver.getCurrentUrl();
        logg.info("url[" + cUrl + "]");
        if (isExistEle(driver, sele1)) {
          PointResearch.answer(driver, sele1, wid);
        }
        else {
          skip++;
          driver.close();
          driver.switchTo().window(wid);
        }
        Utille.refresh(driver, logg);
        Utille.sleep(5000);
      }
      else {
        break;
      }
    }
    //    }

  }
}
