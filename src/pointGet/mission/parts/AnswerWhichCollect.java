package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerWhichCollect extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerWhichCollect(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String sele2 = "img[alt*='ゲームで遊ぶ']",
        sele3 = "button#missionDialogBtnStart>img",
            sele3None = "div#missionDialog[style*='none'] button#missionDialogBtnStart",
        sele4 = "button#missionDialogBtnStart2>img",
        sele5 = "div.priceBox>button",
        sele6 = "button#resultDialogBtn>img",
        sele6None = "button#resultDialogBtn[style*='none']>img",
        sele7 = "a.play>img",
        sele8 = "a#btnEnd",
        goodSele = "img#titleGood",
        badSele = "img#titleBad",
        noneStyle = "[style*='display: none']",
        a = "";
    if (isExistEle(driver, startSele)) {
      clickSleepSelector(driver, startSele, 3000);
      changeCloseWindow(driver);
      Utille.sleep(4000);

      for (int m = 0; m < 6; m++) {

        if (isExistEle(driver, sele2)) {

          clickSleepSelector(driver, sele2, 3000);
          for (int j = 0; j < 10;) {
            if (!isExistEle(driver, sele3None, false)
                && isExistEle(driver, sele3)) {
              clickSleepSelector(driver, sele3, 3000);
            }
            if (isExistEle(driver, sele4)) {
              clickSleepSelector(driver, sele4, 3000);

              if (isExistEle(driver, sele5)) {
                List<WebElement> eleList = driver.findElements(By.cssSelector(sele5));
                int selectSize = eleList.size();
                int ran = Utille.getIntRand(selectSize);

                //                Integer array[] = new Integer[selectSize];
                //                for (int k = 0; k < selectSize; k++) {
                //                  array[k] = k; // 選択肢の分の数字の配列を作ってランダムに
                //                }
                //                // 配列からListへ変換します。
                //                List<Integer> list = Arrays.asList(array);
                //                // リストの並びをシャッフルします。
                //                Collections.shuffle(list);
                //                // listから配列へ戻します。
                //                Integer[] array2 = (Integer[]) list.toArray(new Integer[list.size()]);
                //
                //                for (int k = 0; k < array2.length; k++) {
                //                  if (isExistEle(eleList, array2[k])) {
                //                    clickSleepSelector(driver, eleList.get(array2[k]), 3000);
                //                    if (isExistEle(driver, sele6)) {
                //                      clickSleepSelector(driver, sele6, 3000);
                //                    }
                //                  }
                //                }

                if (isExistEle(eleList, ran)) {
                  clickSleepSelector(driver, eleList.get(ran), 3000);
                  if (!isExistEle(driver, badSele + noneStyle, false)
                      && isExistEle(driver, badSele)) {
                    if (isExistEle(driver, sele6)) {
                      clickSleepSelector(driver, sele6, 3000);
                    }
                  }
                  else if (!isExistEle(driver, goodSele + noneStyle, false)
                      && isExistEle(driver, goodSele)) {
                    j++; // カウントアップ
                    if (!isExistEle(driver, sele6None, false)
                        && isExistEle(driver, sele6)) {
                      clickSleepSelector(driver, sele6, 4000);
                    }
                    else if (isExistEle(driver, sele8)) {
                      clickSleepSelector(driver, sele8, 4000);
                    }
                  }
                  else {
                    if (isExistEle(driver, sele6)) {
                      clickSleepSelector(driver, sele6, 3000);
                      break;
                    }
                  }
                }
              }
            }
            else {
              break;
            }
          }
          if (isExistEle(driver, sele7)) {
            clickSleepSelector(driver, sele7, 3000);
          }
        }
      }
    }
  }
}
