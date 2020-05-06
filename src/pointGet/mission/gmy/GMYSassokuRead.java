package pointGet.mission.gmy;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class GMYSassokuRead extends GMYBase {
  final String url = "https://dietnavi.com/pc/game/";
  private String overlaySelector = "div#popup[style*='display: block'] a.modal_close";
  private String footBnrSelector = "div.foot-bnr a.close>span";

  /**
   * @param logg
   */
  public GMYSassokuRead(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "GMY気持ちを読む");
    //    Kenkou = new AnswerKenkou(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    Utille.sleep(3000);
    String seleList[] = { 
//        "img[alt*='ねこのきもち']", 
        "img[alt*='いぬのきもち']", 
//        "img[alt*='サンキュ！ニュース']" 
        };
    String seleStampGet = "input.getStamp.btn", //
        seleRetrunList = "a.readMore.btn", //
        a, pageReturn = "div>a.pageBack.btn";
    int pagetarget = 6;
    for (String sele : seleList) {
      if (isExistEle(driver, sele)) {
        clickSleepSelector(driver, sele, 4000);
        checkAndAcceptAlert(driver);// アラートをけして
        String pageLinkSele = "ol>li>a", contentSele = "ul.articleList>li", readSele = "a>img.articleImg"//
        , zumiSele = "a>img.stamp", pageLinkSeleBack = "ol>li.back>a", pageLinkSeleNext = "ol>li.next>a"// 
        , geted20Sele = "div.stampArea>h2>img[alt='20ptGETしました']";
        // どのページから始める？
        if (isExistEle(driver, pageLinkSele)) {
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(pageLinkSele));
          if (isExistEle(eleList2, pagetarget)) {// 10番目　1番目リンクなしなので9番目
            clickSleepSelector(driver, eleList2, pagetarget, 3000);
          }
        }
        boolean isFinishedFlag = false;
        for (int i = 0; i < 10; i++) {
          List<WebElement> tmpEleList = driver.findElements(By.cssSelector(contentSele));
          int size = tmpEleList.size();
          for (int j = size - 1; j > -1; j--) { // 古い順にやる
            if (isExistEle(driver, contentSele)) {
              List<WebElement> eleList = driver.findElements(By.cssSelector(contentSele));
              size = eleList.size();
              logg.info("size:" + size + " target:" + j);
              if (isExistEle(eleList, j) && !isExistEle(eleList.get(j), zumiSele, false)
                  && isExistEle(eleList.get(j), readSele)) {
                eleList.get(j).findElement(By.cssSelector(readSele)).click();
                Utille.sleep(3000);
                if (isExistEle(driver, seleStampGet)) {
                  clickSleepSelector(driver, seleStampGet, 3000);
                  // 今日のノルマ10回終わった？
                  if (isExistEle(driver, geted20Sele)) {
                    Utille.url(driver, url, logg);
                    Utille.sleep(3000);
                    isFinishedFlag = true;
                    break;
                  }
                  else if (isExistEle(driver, seleRetrunList)) {
                    clickSleepSelector(driver, seleRetrunList, 3000);

//                    // どのページから始める？
//                    if (isExistEle(driver, pageLinkSele)) {
//                      List<WebElement> eleList2 = driver.findElements(By.cssSelector(pageLinkSele));
//                      if (isExistEle(eleList2, pagetarget)) {// 10番目　1番目リンクなしなので9番目
//                        clickSleepSelector(driver, eleList2, pagetarget, 3000);
//                      }
//                    }
                  }
                }
                else if (isExistEle(driver, pageReturn)) {
                  clickSleepSelector(driver, pageReturn, 3000);
//                  isFinishedFlag = true; // ここに来るのはすでに10スタンプ済み
//                  Utille.url(driver, url, logg);
//                  Utille.sleep(3000);
//                  break;
                }
              }
            }
          }
          if (isFinishedFlag) {
            break;
          }
          // 前へ
          if (isExistEle(driver, pageLinkSeleBack)) {
            clickSleepSelector(driver, pageLinkSeleBack, 3000);
          }
        }
      }
    }
  }
}
