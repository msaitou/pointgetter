package pointGet.mission.gpo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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
public class GPOMotAnk extends GPOBase {
  final String url = "http://www.gpoint.co.jp/";
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
  public GPOMotAnk(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "もっと答えて");
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
    String sele2 = "a[href='https://kotaete.gpoint.co.jp/']>span.navi-icon", //
    selector2 = "td>a>img[src*=btn_answer01]";
    if (isExistEle(driver, sele2)) {
      clickSleepSelector(driver, sele2, 4000);

      // 回答可能なアンケート数から古い方から攻める
      String ableAnsNumSele = "h2.survey_title1_counts>span", //
      sele1 = "button#movenextbtn";
      while (true) {

        int ableAnsNum = 0;
        if (isExistEle(driver, ableAnsNumSele)) {
          String strAbleAnsNum = driver.findElement(By.cssSelector(ableAnsNumSele)).getText(), movePageSele = "li.page>a";
          ableAnsNum = Integer.parseInt(strAbleAnsNum);
          if (ableAnsNum == 0) {
            break;
          }
          int page = ableAnsNum / 10;
          page += (ableAnsNum % 10 > 0) ? 1 : 0; // 端数あれば加算
          List<WebElement> pageList = driver.findElements(By.cssSelector(movePageSele));
          if (isExistEle(pageList, page - 1)) {
            logg.info("first Page:" + page);
            clickSleepSelector(driver, pageList, page - 1, 5000); // 最後のページに移動

            int skip = 0, beforeSize = 0;
            if (isExistEle(driver, selector2)) {
              List<WebElement> eleList = driver.findElements(By.cssSelector(selector2));
              int size = eleList.size(), targetIndex = skip;
              if (beforeSize == size) {
                skip++;
              }
              String wid = driver.getWindowHandle();
              Utille.scrolledPage(driver, eleList.get(targetIndex));
              clickSleepSelector(driver, eleList, targetIndex, 3000); // アンケートスタートページ
              changeWindow(driver, wid);
              Utille.sleep(3000);
              String cUrl = driver.getCurrentUrl();
              logg.info("cUrl[" + cUrl + "]");
              if (
              //          (cUrl.indexOf("cosmelife.com/animal") >= 0) && 
              isExistEle(driver, sele1)) {
                answer(driver, sele1, wid);
              }
              else {

              }
              beforeSize = size;
              Utille.refresh(driver, logg);
              Utille.sleep(5000);
            }
          }
        }
        else {
          break;
        }
      }
    }
  }

  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    Utille.sleep(4000);
    String seleLabel = "label.answertext", //
    qContentsSele = "div.question-text", //
    choiceSele, //
    seleSelect = "p.question>select", finishSele = "button#movesubmitbtn",
    //
    a;
    int choiceNum = 0, choiceies = 0;
    List<WebElement> choiceList = null;
    clickSleepSelector(driver, startSele, 3000);
    for (int i = 0; i < 30; i++) {
      String qContents = "";
      if (isExistEle(driver, qContentsSele)) {
        qContents = driver.findElement(By.cssSelector(qContentsSele)).getText();
        logg.info(qContents);
      }
      // ラベル
      if (isExistEle(driver, seleLabel)) {
        choiceSele = seleLabel;
        choiceList = driver.findElements(By.cssSelector(choiceSele));
        choiceies = choiceList.size();
        if (i == 0 || i == 1 || i == 2 || i == 3 || i == 5) {
          // 最後まで答えるよね？
          choiceNum = 0;
        }
        else if (i == 4) {
          // 何歳
          choiceNum = 2;
        }
        else if (i == 6) {
          // 居住形態
          choiceNum = 5;
        }
        else {
          choiceNum = Utille.getIntRand(choiceies);
        }
        if (isExistEle(choiceList, choiceNum)) {
          clickSleepSelector(driver, choiceList, choiceNum, 5000); // 選択
        }
      }
      else if (isExistEle(driver, seleSelect)) {
        choiceSele = seleSelect;
        Utille.sleep(2000);
        int size3 = getSelectorSize(driver, choiceSele + ">option");
        String value = "";
        if (qContents.indexOf("お住まいの都道府県") >= 0) {
          value = "13";
        }
        else {
          choiceNum = Utille.getIntRand(size3);
          value = driver.findElements(By.cssSelector(choiceSele + ">option"))
              .get(choiceNum).getAttribute("value");
        }
        Select selectList = new Select(driver.findElement(By.cssSelector(choiceSele)));
        selectList.selectByValue(value);
        Utille.sleep(3000);
        selectList.selectByValue(value);
      }
      else {
        choiceSele = "";
      }

      if (isExistEle(driver, startSele)) {
        // 次へ
        clickSleepSelector(driver, startSele, 3000);
      }
      else if (isExistEle(driver, finishSele)) {
        // 次へ(終了)
        clickSleepSelector(driver, finishSele, 3000);
        String compSele = "button.nextBtn", //
        reAnswer = "center>a>button";
        if (isExistEle(driver, compSele)) {
          // 次へ
          clickSleepSelector(driver, compSele, 3000);
          if (isExistEle(driver, reAnswer)) {
            // 次へ
            //            clickSleepSelector(driver, reAnswer, 3000);
            List<WebElement> reList = driver.findElements(By.cssSelector(reAnswer));
            if (isExistEle(reList, 0)) {
              clickSleepSelector(driver, reList, 0, 5000); // 選択
              if (reList.size() > 1) {
                if (isExistEle(driver, startSele)) {
                  clickSleepSelector(driver, startSele, 3000);
                  i = 0;
                }
              }
              else {
                driver.close();
                driver.switchTo().window(wid);
                break;
              }
            }
          }
        }
      }
    }
  }
}
