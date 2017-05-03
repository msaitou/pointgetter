package pointGet.mission.pto;

import java.util.List;
import java.util.Map;

import lombok.val;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.Utille;
import pointGet.mission.parts.AnswerSurveyEnk;

public class PTOPointResearch2 extends PTOBase {
  final String url = "http://www.pointtown.com/ptu/pointpark/enquete/top.do";
  WebDriver driver = null;
  AnswerSurveyEnk SurveyEnk = null;

  /**
   * @param logg
   */
  public PTOPointResearch2(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ポイントリサーチ2!!");
    SurveyEnk = new AnswerSurveyEnk(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    int skip = 0;
    String sele3 = "div.enq-submit>button[type='submit']", // 回答する surveyenk用
    sele1 = "div>input.btn_submit",// 
    sele4 = "div>input[type='submit']",// 
    sele4_ = "#iframe", //
    sele5 = "div#shindan", //
    sele7 = "div.btn>button[type='submit']", //
    sele6 = "form>input.next_bt", //
    sele2 = "div.page-content-button>input.button.btn-next";
    while (true) {
      Utille.sleep(5000);
      selector = "div#pt-enq1 td.frame04>a.promo_enq_bt";
      if (isExistEle(driver, selector)) {
        List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
        int size = eleList.size();
        int targetIndex = size - 1 - skip; // 順番はサイト毎に変更可能だが、変数を使う
        logg.info("size:" + size + " targetIndex:" + targetIndex + " skip:" + skip);
        if (size > targetIndex && targetIndex > 0
            && isExistEle(eleList, targetIndex)) {
          String wid = driver.getWindowHandle();
          clickSleepSelector(eleList, targetIndex, 10000); // アンケートスタートページ
          changeWindow(driver, wid);
          //					selector = "div.ui-control.type-fixed>a.ui-button";
          if (isExistEle(driver, sele3)) {
            _answerSurveyEnk(sele3, wid);
            SurveyEnk.answer(driver, sele3, wid);
          }
          else if (isExistEle(driver, sele2)) {
            _answerKotsuta(sele2, wid);
          }
          else if (isExistEle(driver, sele1)) {
            _answerInfopanel(sele1, wid);
          }
          else if (isExistEle(driver, sele5)) {
            _answerShindan(sele5, wid);
          }
          else if (isExistEle(driver, sele6)) {
            _answerColum(sele6, wid);
          }
          else if (isExistEle(driver, sele7)) {
            _answerHiroba(sele7, wid);
          }
          else if (isExistEle(driver, sele4_)) {
            driver.switchTo().frame(0);
            _answerShopping(sele4, wid);
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

  /**
   * 
   * @param sele6
   * @param wid
   */
  private void _answerHiroba(String sele7, String wid) {
    String choiceSele, radioSele = "div.random>label", titleSele = "div.question", // 質問NOも含む
    qTitle = "div.question";
    String seleNext = "button.next-btn";
    if (isExistEle(driver, sele7)) {
      clickSleepSelector(driver, sele7, 2000); // 遷移　問開始
      for (int g = 0; g < 10 + 2; g++) {
        int choiceNum = 0;
        qTitle = "";
        choiceSele = "";
        if (isExistEle(driver, titleSele)) {
          qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
          logg.info(qTitle);
          if (isExistEle(driver, radioSele)) { // ラジオ
            choiceSele = radioSele;
          }
          // 回答選択
          if (radioSele.equals(choiceSele)) {
            int choiceies = getSelectorSize(driver, choiceSele);
            choiceNum = Utille.getIntRand(choiceies);
            List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
            if (isExistEle(eleList2, choiceNum)) {
              // 選択
              clickSleepSelector(eleList2.get(choiceNum), 3000);
              if (isExistEle(driver, seleNext)) {
                clickSleepSelector(driver, seleNext, 3000);
                if (isExistEle(driver, seleNext)) {
                  clickSleepSelector(driver, seleNext, 3000);
                }
              }
            }
          }
        }
        else {
          break;
        }
      }
      if (isExistEle(driver, seleNext)) {
        clickSleepSelector(driver, seleNext, 3000);
      }
      String finishSele = "button.next-btn[type='submit']";
      if (isExistEle(driver, finishSele)) {
        clickSleepSelector(driver, finishSele, 3000);
        String closeSele = "input.btn_close_en";
        if (isExistEle(driver, closeSele)) {
          clickSleepSelector(driver, closeSele, 3000);
          driver.switchTo().window(wid);
          driver.navigate().refresh();
        }
      }
    }
  }

  /**
   * 
   * @param sele6
   * @param wid
   */
  private void _answerColum(String sele6, String wid) {
    String seleNextb2 = "form>input[type='image']";
    String seleNextb3 = "form>input[alt='next']";
    String overLay = "div#interstitial[style*='display: block']>div>div#inter-close";
    if (isExistEle(driver, sele6)) {
      clickSleepSelector(driver, sele6, 4000); // 遷移　問開始
      for (int g = 0; g < 2; g++) {
        if (isExistEle(driver, seleNextb2)) {
          clickSleepSelector(driver, seleNextb2, 4000); // 遷移　問開始するよ
        }
      }
      Utille.sleep(7000);
      // 6page
      for (int g = 0; g < 6; g++) {
        if (isExistEle(driver, seleNextb3)) {
          clickSleepSelector(driver, seleNextb3, 13000); // 遷移　問開始するよ
        }
      }
      checkOverlay(driver, overLay, false);
      if (isExistEle(driver, seleNextb2)) {
        clickSleepSelector(driver, seleNextb2, 5000); // 遷移　問開始するよ
      }
      String choiceSele = "input[type='radio']";
      String seleNext2 = "div>input.enquete_nextbt";
      String seleNext3 = "div>input.enquete_nextbt_2";
      String seleSele = "form.menu>select";
      // 6問
      for (int k = 1; k <= 6; k++) {
        checkOverlay(driver, overLay, false);
        int choiceNum = 0;
        if (isExistEle(driver, choiceSele)) {
          int choiceies = getSelectorSize(driver, choiceSele);
          switch (k) {
            case 1:
              // 1問目は1：男
              break;
            case 2:
            case 3:
              // 2問目は3：30代
              // 3問目は3：会社員
              if (choiceies > 2) {// 一応選択可能な範囲かをチェック
                choiceNum = 2;
              }
              break;
            default:
              choiceNum = Utille.getIntRand(choiceies);
          }
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
          if (isExistEle(eleList2, choiceNum)) {
            // 選択
            clickSleepSelector(eleList2.get(choiceNum), 3500);
            if (isExistEle(driver, seleNext2)) {
              // 次へ
              clickSleepSelector(driver, seleNext2, 4000);
            }
          }
        }
        else if (isExistEle(driver, seleSele)) {
          int size3 = getSelectorSize(driver, seleSele + ">option");
          choiceNum = Utille.getIntRand(size3);
          String value = driver.findElements(By.cssSelector(seleSele + ">option"))
              .get(choiceNum).getAttribute("value");
          Select selectList = new Select(driver.findElement(By.cssSelector(seleSele)));
          //										selectList.deselectByIndex(choiceNum);
          selectList.selectByValue(value);
          if (isExistEle(driver, seleNext3)) {
            // 次へ
            clickSleepSelector(driver, seleNext3, 4000);
          }
        }
        else {
          break;
        }
      }
      for (int ii = 0; ii < 2; ii++) {
        checkOverlay(driver, overLay, false);
        if (isExistEle(driver, seleNextb2)) {
          clickSleepSelector(driver, seleNextb2, 4000); // 遷移　
        }
      }
      String finishSele = "div#again_bt>a>img";
      checkOverlay(driver, overLay, false);
      if (isExistEle(driver, finishSele)) {
        clickSleepSelector(driver, finishSele, 5000); // 遷移
        // アラートをけして
        val alert = driver.switchTo().alert();
        alert.accept();
        Utille.sleep(5000);
      }
      String closeSele = "input.btn_close_en";
      if (isExistEle(driver, closeSele)) {
        clickSleepSelector(driver, closeSele, 4000);
        driver.switchTo().window(wid);
      }
    }
  }

  /**
   * 
   * @param sele5
   * @param wid
   */
  private void _answerShindan(String sele5, String wid) {
    // 表示される結果によって処理を分ける
    selector = "a.submit-btn";// 次へ
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 2000); // 遷移
      if (isExistEle(driver, selector)) {
        clickSleepSelector(driver, selector, 2000); // 遷移
        if (isExistEle(driver, "div[data-qid]")) {
          int qSize = getSelectorSize(driver, "div[data-qid]"); // 選択肢の数を数える
          for (int i = 0; i < qSize + 5; i++) {
            selector = "div[data-qid][class=''] label";
            if (isExistEle(driver, selector)) {
              int size = getSelectorSize(driver, selector); // 選択肢の数を数える
              int ran1 = Utille.getIntRand(size);
              if (isExistEle(driver.findElements(By.cssSelector(selector)).get(ran1))) {
                driver.findElements(By.cssSelector(selector)).get(ran1).click(); // 選択
                Utille.sleep(4000);

                // end-btn が出たら終了
                String none = "[style*='display: none']";
                String nextSelector = "div.actionBar>a.next-btn";
                String endSelector = "div.actionBar>a.end-btn";
                if (isExistEle(driver, nextSelector)
                    && isExistEle(driver, endSelector + none, false)) {
                  clickSleepSelector(driver, nextSelector, 2000); // 遷移
                }
                else if (isExistEle(driver, endSelector)
                    && isExistEle(driver, nextSelector + none, false)) {
                  clickSleepSelector(driver, endSelector, 4000); // 遷移
                  logg.info("neukata?");
                  selector = "div.col-xs-12>a.btn-danger";
                  if (isExistEle(driver, selector)) {
                    clickSleepSelector(driver, selector, 2000); // 遷移
                  }
                  String closeSele = "input.btn_close_en";
                  if (isExistEle(driver, closeSele)) {
                    clickSleepSelector(driver, closeSele, 2000);
                  }
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

  /**
   *
   * @param sele3
   * @param wid
   */
  private void _answerShopping(String sele, String wid) {
    // 回答開始
    clickSleepSelector(driver, sele, 3000);
    String choiceSele, radioSele = "div.answer>input[type='radio']", checkSele = "div.answer>input[type='checkbox']",
    //		noSele = "span.query-num",
    titleSele = "h2.question", // 質問NOも含む
    qTitle, seleSub = "div.btn_next>input[type='submit']";
    Utille.sleep(2000);
    for (int k = 1; k <= 15; k++) {
      int choiceNum = 0;
      qTitle = "";
      choiceSele = "";
      if (isExistEle(driver, titleSele)) {
        qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
        logg.info(qTitle);
        if (isExistEle(driver, radioSele)) { // ラジオ
          choiceSele = radioSele;
        }
        else if (isExistEle(driver, checkSele)) { // チェックぼっくす
          choiceSele = checkSele;
        }

        // 回答選択
        if (radioSele.equals(choiceSele)
            || checkSele.equals(choiceSele)) {
          int choiceies = getSelectorSize(driver, choiceSele);
          if (qTitle.indexOf("あなたの性別") >= 0) {
            choiceNum = 0; // 1：男
          }
          else if (qTitle.indexOf("あなたの年齢を") >= 0) {
            choiceNum = 2; // 2：30代
          }
          else if (qTitle.indexOf("あなたのご職業") >= 0) {
            choiceNum = 2; // 2：会社員
          }
          else if (qTitle.indexOf("あなたのお住まいを") >= 0) {
            choiceNum = 2; // 2：関東
          }
          else {
            choiceNum = Utille.getIntRand(choiceies);
          }
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
          if (isExistEle(eleList2, choiceNum)) {
            // 選択
            clickSleepSelector(eleList2.get(choiceNum), 3000);
            String seleNe = "div.btn_next>input[type='submit']";
            if (isExistEle(driver, seleNe)) {
              clickSleepSelector(driver, seleNe, 6000);
            }
          }
        }
      }
      else {
        break;
      }
    }
    String finishSele = "div.btn_2next>input[type='button']";
    if (isExistEle(driver, finishSele)) {
      String wid2 = driver.getWindowHandle();
      clickSleepSelector(driver, finishSele, 4000);
      driver.close();
      changeWindow(driver, wid2);
      String closeSele = "input.btn_close_en";
      if (isExistEle(driver, closeSele)) {
        clickSleepSelector(driver, closeSele, 4000);
        driver.switchTo().window(wid);
        driver.navigate().refresh();
      }
    }
  }

  private void _answerInfopanel(String sele1, String wid) {
    String choiceSele, radioSele = "div.answer>input[type='radio']", checkSele = "div.answer>input[type='checkbox']",
    //				noSele = "span.query-num",
    titleSele = "h2.question", // 質問NOも含む
    qTitle, seleSub = "div.btn_next>input[type='submit']";
    clickSleepSelector(driver, sele1, 5000);
    driver.switchTo().frame(0);
    Utille.sleep(2000);
    if (isExistEle(driver, seleSub)) {
      clickSleepSelector(driver, seleSub, 5000);
      for (int k = 1; k <= 18; k++) {
        int choiceNum = 0;
        qTitle = "";
        choiceSele = "";
        if (isExistEle(driver, titleSele)) {
          qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
          logg.info(qTitle);
          if (isExistEle(driver, radioSele)) { // ラジオ
            choiceSele = radioSele;
          }
          else if (isExistEle(driver, checkSele)) { // チェックぼっくす
            choiceSele = checkSele;
          }

          // 回答選択
          if (radioSele.equals(choiceSele)
              || checkSele.equals(choiceSele)) {
            int choiceies = getSelectorSize(driver, choiceSele);
            if (qTitle.indexOf("あなたの性別") >= 0) {
              choiceNum = 0; // 1：男
            }
            else if (qTitle.indexOf("あなたの年齢を") >= 0) {
              choiceNum = 2; // 2：30代
            }
            else if (qTitle.indexOf("あなたのご職業") >= 0) {
              choiceNum = 2; // 2：会社員
            }
            else if (qTitle.indexOf("あなたのお住まい") >= 0) {
              choiceNum = 2; // 2：関東
            }
            else {
              choiceNum = Utille.getIntRand(choiceies);
            }
            List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
            if (isExistEle(eleList2, choiceNum)) {
              // 選択
              clickSleepSelector(eleList2.get(choiceNum), 3000);
              String seleNe = "div.btn_next>input[type='submit']";
              if (isExistEle(driver, seleNe)) {
                clickSleepSelector(driver, seleNe, 6000);
              }
            }
          }
        }
        else {
          break;
        }
      }
    }
    String finishSele = "div.btn_2next>input[type='button']";
    if (isExistEle(driver, finishSele)) {
      String wid2 = driver.getWindowHandle();
      clickSleepSelector(driver, finishSele, 4000);
      driver.close();
      changeWindow(driver, wid2);
      String closeSele = "input.btn_close_en";
      if (isExistEle(driver, closeSele)) {
        clickSleepSelector(driver, closeSele, 4000);
        driver.switchTo().window(wid);
        driver.navigate().refresh();
      }
    }
  }

  /**
   * 
   * @param sele2
   * @param wid
   */
  private void _answerKotsuta(String sele2, String wid) {
    String choiceSele, radioSele = "td.choice-table-input>input.radio", seleSele = "select", noSele = "span.query-num", titleSele = "span.query-text", qTitle;
    clickSleepSelector(driver, sele2, 4000);
    for (int k = 1; k <= 18; k++) {
      int choiceNum = 0;
      qTitle = "";
      choiceSele = "";
      if (isExistEle(driver, noSele)) {
        String qNo = driver.findElement(By.cssSelector(noSele)).getText();
        qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
        logg.info(qNo + " " + qTitle);
        if (isExistEle(driver, radioSele)) { // ラジオ
          choiceSele = radioSele;
        }
        else if (isExistEle(driver, seleSele)) { // セレクト
          choiceSele = seleSele;
        }
        // 回答選択
        if (radioSele.equals(choiceSele)
        //				|| checkboxSele.equals(choiceSele)
        ) {
          int choiceies = getSelectorSize(driver, choiceSele);
          if (qTitle.indexOf("あなたの性別") >= 0) {
            choiceNum = 0; // 1：男
          }
          else if (qTitle.indexOf("あなたの年齢をお知らせください") >= 0) {
            choiceNum = 3; // 4：30代
          }
          else if (qTitle.indexOf("あなたの職業") >= 0) {
            choiceNum = 3; // 3：会社員
          }
          else if (qTitle.indexOf("あなたのお住まいの都道府県を教えてください") >= 0) {
            choiceNum = 3; // 3：関東
          }
          else {
            choiceNum = Utille.getIntRand(choiceies);
          }
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
          if (isExistEle(eleList2, choiceNum)) {
            // 選択
            clickSleepSelector(eleList2.get(choiceNum), 3000);
            if (isExistEle(driver, sele2)) {
              clickSleepSelector(driver, sele2, 6000);
            }
          }
        }
        else if (seleSele.equals(choiceSele)) {
          Utille.sleep(2000);
          int size3 = getSelectorSize(driver, choiceSele + ">option");
          String value = "";
          if (qTitle.indexOf("あなたのお住まいを") >= 0) {
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
          if (isExistEle(driver, sele2)) {
            clickSleepSelector(driver, sele2, 6000);
          }
        }
      }
      else {
        break;
      }
    }
    while (true) {
      if (isExistEle(driver, sele2)) {
        clickSleepSelector(driver, sele2, 5000);
      }
      else {
        break;
      }
    }
    Utille.sleep(5000);
    // close
    String closeSele = "input.btn_close_en";
    if (isExistEle(driver, closeSele)) {
      clickSleepSelector(driver, closeSele, 4000);
      driver.switchTo().window(wid);
      driver.navigate().refresh();
    }
  }

  /** 
   * 
   * @param sele3
   * @param wid
   */
  private void _answerPointResearch(String sele3, String wid) {
    closeOtherWindow(driver);
    clickSleepSelector(driver, selector, 4000);
    // 回答開始
    selector = "form>input.ui-button";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 4000);
      String choiceSele = "label.ui-label-radio", seleNext2 = "div.fx-control>input.ui-button", seleSele = "select.ui-select", overLay = "div.overlay-popup a.button-close", noSele = "div.ui-item-no", titleSele = "h2.ui-item-title", checkSele = "label.ui-label-checkbox";
      // 12問
      for (int k = 1; k <= 13; k++) {
        if (!isExistEle(driver, "div.overlay-popup[style*='display: none;'] a.button-close", false)
            && isExistEle(driver, overLay)) {
          checkOverlay(driver, overLay, false);
        }
        if (isExistEle(driver, noSele)) {
          String qNo = driver.findElement(By.cssSelector(noSele)).getText();
          String qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
          logg.info(qNo + " " + qTitle);
          int choiceNum = 0;
          if (isExistEle(driver, choiceSele)) {
            int choiceies = getSelectorSize(driver, choiceSele);
            switch (k) {
              case 1: // 性別
              case 3: // 結婚
                // 1問目は1：男
                // 3問目は3：未婚
                break;
              case 5: // 職業
              case 2: // 年齢
                // 2問目は3：30代
                if (choiceies > 2) {// 一応選択可能な範囲かをチェック
                  choiceNum = 2;
                }
                break;
              default:
                choiceNum = Utille.getIntRand(choiceies);
            }
            List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
            if (isExistEle(eleList2, choiceNum)) {
              // 選択
              clickSleepSelector(eleList2.get(choiceNum), 3000);
            }
          }
          else if (isExistEle(driver, checkSele)) {
            int size4 = getSelectorSize(driver, checkSele);
            choiceNum = Utille.getIntRand(size4);
            List<WebElement> eleList2 = driver.findElements(By.cssSelector(checkSele));
            if (isExistEle(eleList2, choiceNum)) {
              // 選択
              clickSleepSelector(eleList2.get(choiceNum), 3000);
            }
          }
          else if (isExistEle(driver, seleSele)) {
            Utille.sleep(2000);
            int size3 = getSelectorSize(driver, seleSele + ">option");
            String value = "";
            if (qTitle.indexOf("居住区") >= 0) {
              value = "4";
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
        }
        else {
          break;
        }
        if (isExistEle(driver, seleNext2)) {
          // 次へ
          clickSleepSelector(driver, seleNext2, 4500);
        }
      }
      Utille.sleep(2000);
      if (!isExistEle(driver, "div.overlay-popup[style*='display: none;'] a.button-close", false)
          && isExistEle(driver, overLay)) {
        checkOverlay(driver, overLay, false);
      }
      if (isExistEle(driver, selector)) {
        // ポイント獲得
        clickSleepSelector(driver, selector, 3000);
      }
      if (isExistEle(driver, overLay)) {
        checkOverlay(driver, overLay, false);
      }
      String closeSele = "div.ui-control>a";
      if (isExistEle(driver, closeSele)) {
        // 閉じる
        clickSleepSelector(driver, closeSele, 3000);
      }
      // point一覧に戻る
    }
  }

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
    for (int k = 1; k <= 18; k++) {
      int choiceNum = 0;
      qTitle = "";
      choiceSele = "";
      if (isExistEle(driver, qTitleSele)) {
        qTitle = driver.findElement(By.cssSelector(qTitleSele)).getText();
        logg.info("[" + qTitle + "]");
      }
      else {
        break;
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
        if (qTitle.indexOf("あなたの性別を") >= 0) {
          // 1：男
        }
        else if (qTitle.indexOf("あなたの年齢を") >= 0) {
          if (choiceies > 3) {// 一応選択可能な範囲かをチェック
            choiceNum = 3;
          }
        }
        else {
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
      driver.switchTo().window(wid);
      driver.navigate().refresh();
    }
    //			driver.close();
    // 最後に格納したウインドウIDにスイッチ
    //		driver.switchTo().window(wid);
  }

  private void _answerAdserver(String sele, String wid) {
    String choiceSele, radioSele = "label.radiolabel", overLay = "div#center-frame>img", noSele = "h2>span.q_number", titleSele = "h2>span.q_text", qTitle;
    clickSleepSelector(driver, sele, 4000);
    Utille.sleep(4000);
    for (int k = 1; k <= 20; k++) {
      int choiceNum = 0;
      qTitle = "";
      choiceSele = "";
      if (isExistEle(driver, noSele)) {
        String qNo = driver.findElement(By.cssSelector(noSele)).getText();
        qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
        logg.info(qNo + " " + qTitle);
        if (isExistEle(driver, radioSele)) { // ラジオ
          choiceSele = radioSele;
        }
        // 回答選択
        if (radioSele.equals(choiceSele)
        //						|| checkboxSele.equals(choiceSele)
        ) {
          int choiceies = getSelectorSize(driver, choiceSele);
          if (qTitle.indexOf("あなたの性別を教えてください") >= 0) {
            choiceNum = 0; // 1：男
          }
          else if (qTitle.indexOf("あなたの年齢を教えてください") >= 0) {
            choiceNum = 3; // 4：30代
          }
          else if (qTitle.indexOf("あなたの職業を教えてください") >= 0) {
            choiceNum = 3; // 3：会社員
          }
          else if (qTitle.indexOf("あなたのお住まいの都道府県を教えてください") >= 0) {
            choiceNum = 3; // 3：関東
          }
          else {
            choiceNum = Utille.getIntRand(choiceies);
          }
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
          if (isExistEle(eleList2, choiceNum)) {
            // 選択
            clickSleepSelector(eleList2.get(choiceNum), 3000);
            if (isExistEle(driver, sele)) {
              clickSleepSelector(driver, sele, 6000);
            }
          }
        }
      }
      else {
        break;
      }
    }
    Utille.sleep(5000);
    // [style*='visibility: visible']
    checkOverlay(driver, overLay, false);
    if (!isExistEle(driver, "div#fade-layer[style*='display: none']", false)) {
      checkOverlay(driver, overLay, false);
    }
    // close
    String closeSele = "div.question_btn>input[name='submit']";
    if (isExistEle(driver, closeSele)) {
      clickSleepSelector(driver, closeSele, 4000);
      driver.switchTo().window(wid);
      driver.navigate().refresh();
    }
  }
}
