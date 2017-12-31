package pointGet.mission.pst;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerSouSenkyo;

public class PSTKumaVote extends PSTBase {
  final String url = "http://www.point-stadium.com/wcmpoint.asp";
  WebDriver driver = null;
  AnswerSouSenkyo SouSenkyo = null;

  /**
   * @param logg
   */
  public PSTKumaVote(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "くま投票");
    SouSenkyo = new AnswerSouSenkyo(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    selector = "form[name='ItemList']>p>input[name='entry']";
    String sele0 = "a.start__button" //
    , sele1 = "ul.select__list>li>a" // クラスを完全一致にするのは済の場合クラスが追加されるため
    , preSele = "a>img[alt='election']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 5000); // 遷移
      changeCloseWindow(driver);
      Utille.sleep(3000);
      if (isExistEle(driver, preSele)) {
        clickSleepSelector(driver, preSele, 5000); // 遷移 全体へ
        changeCloseWindow(driver);
        Utille.sleep(5000);
        if (isExistEle(driver, sele0)) {
          clickSleepSelector(driver, sele0, 5000); // 遷移 全体へ
          while (isExistEle(driver, sele1)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(sele1));
            int size1 = eleList.size(), zumiCnt = 0, targetIndex = 0;
            if (isExistEle(eleList, targetIndex)) {
              Utille.scrolledPage(driver, eleList.get(targetIndex));
              clickSleepSelector(driver, eleList, targetIndex, 4000); // 遷移
              SouSenkyo.answer(driver, "", null);
            }
            else {
              break;
            }
          }
        }
      }
    }
  }
}
