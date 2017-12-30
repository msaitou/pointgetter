package pointGet.mission.pto;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerAdserver;
import pointGet.mission.parts.AnswerPointResearch;
import pointGet.mission.parts.AnswerSurveyEnk;

public class PTOPointResearch extends PTOBase {
  final String url = "http://www.pointtown.com/ptu/pointpark/enquete/top.do";
  WebDriver driver = null;
  /* アンケートクラス　ポイントサーチ */
  AnswerPointResearch PointResearch = null;
  AnswerSurveyEnk SurveyEnk = null;
  AnswerAdserver Adserver = null;

  /**
   * @param logg
   */
  public PTOPointResearch(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ポイントリサーチ");
    PointResearch = new AnswerPointResearch(logg);
    SurveyEnk = new AnswerSurveyEnk(logg);
    Adserver = new AnswerAdserver(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    selector = "td>span.promo_enq_bt";
    Utille.sleep(3000);
    int skip = 1;
    String sele3 = "div>button[type='submit']", // 回答する surveyenk用
        sele2 = "div.question_btn>input[type='submit']", //
        sele1 = "div.ui-control.type-fixed>a.ui-button"; // ポイントサーチ用

    while (true) {
      if (!isExistEle(driver, selector)) {
        break;
      }
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      int size = eleList.size(), targetIndex = skip - 1; // 順番はサイト毎に変更可能だが、変数を使う
      if (targetIndex > -1 && size > targetIndex
          && isExistEle(eleList, targetIndex)) {
        String wid = driver.getWindowHandle();
        clickSleepSelector(eleList, targetIndex, 10000); // アンケートスタートページ
        changeWindow(driver, wid);
        String cUrl = driver.getCurrentUrl();
        logg.info("url[" + cUrl + "]");
        if (isExistEle(driver, sele1)) {
          PointResearch.answer(driver, sele1, wid);
        }
        else if (isExistEle(driver, sele2)) {
          Adserver.answer(driver, sele2, wid);
        }
        else if (isExistEle(driver, sele3)) {
          //          closeOtherWindow(driver);
          //          _answerSurveyEnk(sele3, "");
          SurveyEnk.answer(driver, sele3, wid);
          skip++;
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
  }

  // TODO 消すな、処理が少し異なる
  /**
   *
   * @param sele3
   * @param wid
   */
  private void _answerSurveyEnk(String sele3, String wid) {
    // 回答開始
    clickSleepSelector(driver, sele3, 3000);
    String qTitleSele = "div.question-label",
        qTitle = "",
        radioSele = "label.item-radio",
        checkboxSele = "label.item-checkbox",
        choiceSele = "",
        seleSele = "select.mdl-textfield__input";
    for (int k = 1; k <= 15; k++) {
      int choiceNum = 0;
      qTitle = "";
      choiceSele = "";
      if (isExistEle(driver, qTitleSele)) {
        qTitle = driver.findElement(By.cssSelector(qTitleSele)).getText();
        logg.info("[" + qTitle + "]");
      }
      if (isExistEle(driver, radioSele)) { // ラジオ
        choiceSele = radioSele;
      }
      else if (isExistEle(driver, checkboxSele)) { // チェックボックス
        choiceSele = checkboxSele;
      }
      else if (isExistEle(driver, seleSele)) {// セレクトボックス
        choiceSele = seleSele;
      }
      if (radioSele.equals(choiceSele)
          || checkboxSele.equals(choiceSele)) {
        int choiceies = getSelectorSize(driver, choiceSele);
        switch (k) {
          case 13: // 性別
            //									case 3: // 結婚
            // 13問目は1：男
            // 3問目は3：未婚
            break;
          //									case 5: // 職業
          case 14: // 年齢
            // 2問目は3：30代
            if (choiceies > 3) {// 一応選択可能な範囲かをチェック
              choiceNum = 3;
            }
            break;
          default:
            choiceNum = Utille.getIntRand(choiceies);
        }
        List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
        if (isExistEle(eleList2, choiceNum)) {
          // 選択
          clickSleepSelector(eleList2.get(choiceNum), 2500);
        }
      }
      else if (seleSele.equals(choiceSele)) {
        Utille.sleep(2000);
        int size3 = getSelectorSize(driver, seleSele + ">option");
        String value = "";
        if (qTitle.indexOf("住まい") >= 0) {
          value = "14";
        }
        else {
          choiceNum = Utille.getIntRand(size3);
          value = driver.findElements(By.cssSelector(seleSele + ">option"))
              .get(choiceNum).getAttribute("value");
        }
        Select selectList = new Select(driver.findElement(By.cssSelector(seleSele)));
        selectList.selectByValue(value);
        Utille.sleep(3000);
      }
      if (isExistEle(driver, sele3)) {
        // 次へ
        clickSleepSelector(driver, sele3, 4000);
      }
    }
    Utille.sleep(3000);
    if (isExistEle(driver, sele3)) {
      // 次へ
      clickSleepSelector(driver, sele3, 4000);
    }
    // close
    String closeSele = "input.btn_close_en";
    if (isExistEle(driver, closeSele)) {
      clickSleepSelector(driver, closeSele, 4000);
    }
    //			driver.close();
    // 最後に格納したウインドウIDにスイッチ
    //		driver.switchTo().window(wid);
  }
}
