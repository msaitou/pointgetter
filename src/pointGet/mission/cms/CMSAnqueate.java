package pointGet.mission.cms;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerAdEnq;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerCooking;
import pointGet.mission.parts.AnswerHyakkey;
import pointGet.mission.parts.AnswerKansatu;
import pointGet.mission.parts.AnswerManga;
import pointGet.mission.parts.AnswerPhotoEnk;
import pointGet.mission.parts.AnswerZukan;

/**
 * @author saitou
 *
 */
public class CMSAnqueate extends CMSBase {
  final String url = "http://www.cmsite.co.jp/top/enq/";
  AnswerAdEnq AdEnq = null;
  AnswerPhotoEnk PhotoEnk = null;
  AnswerKansatu Kansatu = null;
  AnswerCooking Cooking = null;
  AnswerHyakkey Hyakkey = null;
  AnswerZukan Zukan = null;
  AnswerColum Colum = null;
  AnswerManga Manga = null;

  /**
   * @param logg
   */
  public CMSAnqueate(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "アンケート");
    AdEnq = new AnswerAdEnq(logg);
    Cooking = new AnswerCooking(logg);
    PhotoEnk = new AnswerPhotoEnk(logg);
    Hyakkey = new AnswerHyakkey(logg);
    Kansatu = new AnswerKansatu(logg);
    Zukan = new AnswerZukan(logg);
    Colum = new AnswerColum(logg);
    Manga = new AnswerManga(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    Utille.sleep(3000);
    String sele2 = "img[src='img/q_02.png']", selector2 = "img[src='img/q_02.png']";
    if (isExistEle(driver, selector2)) {
      //        String wid = driver.getWindowHandle();
      clickSleepSelector(driver, selector2, 5000);
      // アラートをけして
      checkAndAcceptAlert(driver);
      Utille.sleep(5000);
      changeCloseWindow(driver);
      int skip = 0, beforeSize = 0;
      selector = "div.enquete_box>a"; // アンケート一覧の回答するボタン
      String seleNextb2 = "form>input[type='image']";
      String sele1_ = "iframe.question_frame", //
      sele6 = "form>input.next_bt", // コラム用
      sele1 = "form>input[type='submit']", sele10 = "form>input[type='image']", // 回答する 漫画用
      b;

      while (isExistEle(driver, selector)) {
        if (isExistEle(driver, selector)) {
          List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
          int size = eleList.size(), targetIndex = skip;
          if (beforeSize == size) {
            skip++;
          }
          logg.info("size:" + size + " target:" + targetIndex);
          if (size > targetIndex &&
              targetIndex >= 0 && isExistEle(eleList, targetIndex)) {
            Utille.scrolledPage(driver, driver.findElements(By.cssSelector(selector)).get(targetIndex));
            Actions actions = new Actions(driver);
            actions.keyDown(Keys.CONTROL);
            actions.click(driver.findElements(By.cssSelector(selector)).get(targetIndex));
            actions.perform();
            Utille.sleep(10000);

            String wid = driver.getWindowHandle();
            changeWindow(driver, wid);
            Utille.sleep(1000);
            String cUrl = driver.getCurrentUrl();
            logg.info("url[" + cUrl + "]");
            if ((cUrl.indexOf("ad/enq/") >= 0
                || cUrl.indexOf("credit-card.link") >= 0)
                && isExistEle(driver, sele1_)) {
              // $('iframe').contents().find("div>input[type='submit']")
              boolean isSuccess = true;
              do {
                try {
                  if (!AdEnq.answer(driver, sele1, wid)) {
                    //                        skip++;
                    driver.close();
                    driver.switchTo().window(wid);
                    //                    break;
                  }
                } catch (StaleElementReferenceException e) {
                  logg.warn("StaleElementReferenceException-----------------");
                  isSuccess = false;
                }
              } while (!isSuccess);
            }
            else if ((cUrl.indexOf("cosmelife.com/animal") >= 0
                || cUrl.indexOf("/animal/") >= 0
                //            || cUrl.indexOf("eyelashes-fashion.com") >= 0
                )
                && isExistEle(driver, sele6)) {
              Zukan.answer(driver, sele6, wid);
            }
            else if ((cUrl.indexOf("cosmelife.com/observation") >= 0
                || cUrl.indexOf("/observation/") >= 0
                //            || cUrl.indexOf("eyelashes-fashion.com") >= 0
                )
                && isExistEle(driver, sele6)) {
              Kansatu.answer(driver, sele6, wid);
            }
            else if ((cUrl.indexOf("cosmelife.com/map") >= 0
                || cUrl.indexOf("/map/") >= 0)
                && isExistEle(driver, sele6)) {
              Hyakkey.answer(driver, sele6, wid);
            }
            else if ((cUrl.indexOf("cosmelife.com/cooking") >= 0
                || cUrl.indexOf("/cooking/") >= 0)
                && isExistEle(driver, sele6)) {
              Cooking.answer(driver, sele6, wid);
            }
            else if ((cUrl.indexOf("photo-enquete") >= 0
                || cUrl.indexOf("/photo/") >= 0
                || cUrl.indexOf("cosmelife.com/photo") >= 0
                || cUrl.indexOf("eyelashes-fashion.com") >= 0
                || cUrl.indexOf("natural-vegetables.com") >= 0
                || cUrl.indexOf("cosmeticsstyle.com") >= 0)
                && isExistEle(driver, sele6)) {
              PhotoEnk.answer(driver, sele6, wid);
            }
            else if ((cUrl.indexOf("column-enquete") >= 0
                || cUrl.indexOf("/column/") >= 0
                || cUrl.indexOf("beautynail-design.com") >= 0
                || cUrl.indexOf("style-cutehair.com") >= 0
                || cUrl.indexOf("eyelashes-fashion.com") >= 0
                || cUrl.indexOf("fashion-cosmelife.com") >= 0)
                && isExistEle(driver, sele6)) {
              Colum.answer(driver, sele6, wid);
            }
            // 漫画
            else if (isExistEle(driver, sele10)) {
              Manga.answer(driver, sele10, wid);
            }
            else {
              break;
            }

            //                beforeSize = size;
            Utille.refresh(driver, logg);
            Utille.sleep(5000);
          }
        }
        else {
          break;
        }
      }
    }
  }
}
