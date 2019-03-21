package pointGet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Define;
import pointGet.common.Utille;
import pointGet.db.PointsCollection;

/**
 * write the current point
 * @author saito
 */
public class Points extends PointGet {

  private static double total = 0;
  private static String[] pointSitelist = null;

  protected static void init() {
    PointGet.init(Points.class.getSimpleName());
    pointSitelist = loadProps.getProperty("pointTargetList").split(",");
    _setLogger("log4jPoints.properties", Points.class);
  }

  /**
   * entry point
   * @param args
   */
  public static void main(String[] args) {
    init();
    StringBuffer sb = new StringBuffer();
    Map<String, Double> pMap = new HashMap<String, Double>();
    WebDriver driver = getWebDriver();
    try {
      PointsCollection PC = new PointsCollection(Dbase);
      List<HashMap<String, Object>> pList = PC.getPointList();
      HashMap<String, Object> pointMap = pList.get(0);
      System.out.println(pointMap);
      for (String siteCode : pointSitelist) {
        String selector = "", outPut = "", point = "", secondPoint = "";
        switch (siteCode) {
          case Define.PSITE_CODE_GMY:
            if (pointMap.containsKey(Define.PSITE_CODE_GMY)) {
              break;
            }
            selector = "li.user_point>a";
            Utille.url(driver, "http://dietnavi.com/pc", logg);
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_GMY + ":" + Utille.getNumber(point) + "]";
            }
            break;
          case Define.PSITE_CODE_GEN:
            if (pointMap.containsKey(Define.PSITE_CODE_GEN)) {
              break;
            }
            selector = "li#user_point01>a>span";
            Utille.url(driver, "http://www.gendama.jp/", logg);
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_GEN + ":" + Utille.getNumber(point) + "]";
            }
            break;
          case Define.PSITE_CODE_ECN:
            if (pointMap.containsKey(Define.PSITE_CODE_ECN)) {
              break;
            }
            selector = "p.user_point_txt>strong";
            Utille.url(driver, "https://ecnavi.jp/mypage/point_history/", logg);
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_ECN + ":" + Utille.getNumber(point) + "]";
            }
            break;
          case Define.PSITE_CODE_MOP:
            if (pointMap.containsKey(Define.PSITE_CODE_MOP)) {
              break;
            }
            Utille.url(driver, "http://pc.moppy.jp/", logg);
            selector = "div#preface>ul.pre__login__inner";
            if (!isExistEle(driver, selector)) {
              // login!!
              LoginSite.login(Define.PSITE_CODE_MOP, driver, logg);
            }
            selector = "div#point_blinking strong";
            Utille.url(driver, "http://pc.moppy.jp/bankbook/", logg);
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_MOP + ":" + Utille.getNumber(point);
            }
            selector = "div#point_blinking em";
            if (isExistEle(driver, selector)) {
              secondPoint = driver.findElement(By.cssSelector(selector)).getText();
              outPut += "." + Utille.getNumber(secondPoint) + "]";
            }
            break;
          case Define.PSITE_CODE_PEX:
            if (pointMap.containsKey(Define.PSITE_CODE_PEX)) {
              break;
            }
            selector = "dd.user_pt.fw_b>span.fw_b";
            Utille.url(driver, "https://pex.jp/user/point_passbook/all", logg);
            if (!isExistEle(driver, selector)) {
              // login!!
              LoginSite.login(Define.PSITE_CODE_PEX, driver, logg);
            }
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_PEX + ":" + Utille.getNumber(point) + "]";
            }
            break;
          case Define.PSITE_CODE_OSA:
            if (pointMap.containsKey(Define.PSITE_CODE_OSA)) {
              break;
            }
            selector = "ul.userinfo";
            Utille.url(driver, "http://osaifu.com/", logg);
            if (!isExistEle(driver, selector)) {
              // login!!
              LoginSite.login(Define.PSITE_CODE_OSA, driver, logg);
            }
            selector = "dl.bankbook-total>dd.current.coin>span";
            Utille.url(driver, "https://osaifu.com/contents/bankbook/top/", logg);
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_OSA + ":" + Utille.getNumber(point);
            }
            selector = "dl.bankbook-total>dd.gold.coin>span";
            if (isExistEle(driver, selector)) {
              secondPoint = driver.findElement(By.cssSelector(selector)).getText();
              outPut += "." + Utille.getNumber(secondPoint) + "]";
            }
            break;
          case Define.PSITE_CODE_PTO:
            if (pointMap.containsKey(Define.PSITE_CODE_PTO)) {
              break;
            }
            selector = "p.pt-user-nav__ttl--point";
            Utille.url(driver, "https://www.pointtown.com/ptu/top", logg);
            if (!isExistEle(driver, selector)) {
              // login!!
              LoginSite.login(Define.PSITE_CODE_PTO, driver, logg);
            }
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_PTO + ":" + Utille.getNumber(point) + "]";
            }
            else {
              // ログインができていない可能性がある
            }
            break;
          case Define.PSITE_CODE_I2I:
            if (pointMap.containsKey(Define.PSITE_CODE_I2I)) {
              break;
            }
            selector = "td.ad_point>span.limited";
            Utille.url(driver, "https://point.i2i.jp/account/", logg);
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_I2I + ":" + Utille.getNumber(point) + "]";
            }
            break;
          case Define.PSITE_CODE_PIL:
            if (pointMap.containsKey(Define.PSITE_CODE_PIL)) {
              break;
            }
            // login!!
            LoginSite.login(Define.PSITE_CODE_PIL, driver, logg);
            selector = "table.memberinfo tr>td>strong";
            //					Utille.url(driver, "http://www.point-island.com/", logg);
            if (isExistEle(driver, selector)) {
              List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
              if (isExistEle(eleList, 1)) {
                point = eleList.get(1).getText();
                outPut = "[" + Define.PSITE_CODE_PIL + ":" + Utille.getNumber(point) + "]";
              }
            }
            break;
          case Define.PSITE_CODE_PIC:
            if (pointMap.containsKey(Define.PSITE_CODE_PIC)) {
              break;
            }
            selector = "dl.basis_data_box span.red.bold";
            LoginSite.login(Define.PSITE_CODE_PIC, driver, logg);
            Utille.url(driver, "https://pointi.jp/my/my_page.php", logg); // http://pointi.jp/
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_PIC + ":" + Utille.getNumber(point) + "]";
            }
            break;
          case Define.PSITE_CODE_HAP:
            if (pointMap.containsKey(Define.PSITE_CODE_HAP)) {
              break;
            }
            // login!!
            //					LoginSite.login(Define.PSITE_CODE_PIC, driver, logg);
            selector = "a.usernavi-point";
            Utille.url(driver, "http://hapitas.jp/", logg);
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_HAP + ":" + Utille.getNumber(point) + "]";
            }
            break;
          case Define.PSITE_CODE_MOB:
            if (pointMap.containsKey(Define.PSITE_CODE_MOB)) {
              break;
            }
            // login!!
            //					LoginSite.login(Define.PSITE_CODE_PIC, driver, logg);
            selector = "div.bankbook_panel__point>em";
            Utille.url(driver, "http://pc.mtoku.jp/mypage/bankbook/", logg);
            if (isExistEle(driver, selector)) {
              List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
              if (isExistEle(eleList, 0)) {
                point = eleList.get(0).getText();
                outPut = "[" + Define.PSITE_CODE_MOB + ":" + Utille.getNumber(point) + "]";
              }
            }
            break;
          case Define.PSITE_CODE_CRI:
            if (pointMap.containsKey(Define.PSITE_CODE_CRI)) {
              break;
            }
            // login!!
            //					LoginSite.login(Define.PSITE_CODE_PIC, driver, logg);
            selector = "li.p_menu.point>a";
            Utille.url(driver, "http://www.chobirich.com/", logg);
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_CRI + ":" + Utille.getNumber(point) + "]";
            }
            break;
          case Define.PSITE_CODE_PNY:
            if (pointMap.containsKey(Define.PSITE_CODE_PNY)) {
              break;
            }
            // login!!
            //					LoginSite.login(Define.PSITE_CODE_PIC, driver, logg);
            selector = "p#user_get_point>em";
            Utille.url(driver, "https://www.poney.jp/", logg);
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_PNY + ":" + Utille.getNumber(point) + "]";
            }
            break;
          case Define.PSITE_CODE_SUG:
            if (pointMap.containsKey(Define.PSITE_CODE_SUG)) {
              break;
            }
            // login!!
            //					LoginSite.login(Define.PSITE_CODE_PIC, driver, logg);
            selector = "dd.js-user_point";
            Utille.url(driver, "https://www.sugutama.jp/mypage", logg);
            Utille.sleep(5000);
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_SUG + ":" + Utille.getNumber(point) + "]";
            }
            break;
          case Define.PSITE_CODE_WAR:
            if (pointMap.containsKey(Define.PSITE_CODE_WAR)) {
              break;
            }
            selector = "span.top-UserPoint_Point";
            Utille.url(driver, "http://www.warau.jp/", logg);
            if (!isExistEle(driver, selector)) {
              // login!!
              LoginSite.login(Define.PSITE_CODE_WAR, driver, logg);
            }
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_WAR + ":" + Utille.getNumber(point) + "]";
            }

            break;
          case Define.PSITE_CODE_CIT:
            if (pointMap.containsKey(Define.PSITE_CODE_CIT)) {
              break;
            }
            selector = "li.user_pt>a";
            Utille.url(driver, "https://www.chance.com/", logg);
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_CIT + ":" + Utille.getNumber(point) + "]";
            }
            break;
          case Define.PSITE_CODE_PMO:
            if (pointMap.containsKey(Define.PSITE_CODE_PMO)) {
              break;
            }
            selector = "div.point span.F5";
            Utille.url(driver, "http://poimon.jp/", logg);
            if (!isExistEle(driver, selector)) {
              // login!!
              LoginSite.login(Define.PSITE_CODE_PMO, driver, logg);
            }
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_PMO + ":" + Utille.getNumber(point) + "]";
            }
            break;
          // pointstadium
          case Define.PSITE_CODE_PST:
            if (pointMap.containsKey(Define.PSITE_CODE_PST)) {
              break;
            }
            selector = "div.login>p.point>strong";
            Utille.url(driver, "http://www.point-stadium.com/", logg);
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_PST + ":" + Utille.getNumber(point) + "]";
            }
            break;
          // cms
          case Define.PSITE_CODE_CMS:
            if (pointMap.containsKey(siteCode)) {
              break;
            }
            selector = "p.menbertxt>span";
            Utille.url(driver, "http://www.cmsite.co.jp/top/home/", logg);
            if (!Utille.isExistEle(driver, selector, logg)) {
              // login!!
              LoginSite.login(siteCode, driver, logg);
            }
            Utille.url(driver, "http://www.cmsite.co.jp/top/home/", logg);
            if (Utille.isExistEle(driver, selector, logg)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + siteCode + ":" + Utille.getNumber(point) + "]";
            }
            break;
          // LFM
          case Define.PSITE_CODE_LFM:
            if (pointMap.containsKey(siteCode)) {
              break;
            }
            selector = "em#mempoint";
            Utille.url(driver, "http://lifemedia.jp/", logg);
            LoginSite.login(siteCode, driver, logg);
            if (Utille.isExistEle(driver, selector, logg)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + siteCode + ":" + Utille.getNumber(point) + "]";
            }
            break;
          // GPO
          case Define.PSITE_CODE_GPO:
            if (pointMap.containsKey(siteCode)) {
              break;
            }
            selector = "span#point";
            String sel = "ul>li.status-point";
            Utille.url(driver, "https://www.gpoint.co.jp/scripts/direct/userinfo/MMMyPage.do", logg);
            if (!Utille.isExistEle(driver, sel, false, logg)) {
              LoginSite.login(siteCode, driver, logg);
              Utille.url(driver, "https://www.gpoint.co.jp/scripts/direct/userinfo/MMMyPage.do", logg);
            }
            if (Utille.isExistEle(driver, selector, logg)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + siteCode + ":" + Utille.getNumber(point) + "]";
            }
            break;
          default:
        }
        if (outPut.length() > 0) {
          total = Utille.sumTotal(siteCode, point, total);
          Double siteTotal = getSiteTotal(siteCode, point, 0.0);
          if (secondPoint.length() > 0) {
            total = Utille.sumTotal("secondPoint", secondPoint, total);
            siteTotal = getSiteTotal(siteCode, point, siteTotal);
          }
          sb.append(outPut);
          pMap.put(siteCode, siteTotal);
        }
        else {
          // 取得できなかった
          logg.warn("missed site:" + siteCode);
        }
      }
    } catch (Throwable e) {
      e.printStackTrace();
    } finally {
      //      driver.close();
      driver.quit();
    }
    PointsCollection PC = new PointsCollection(Dbase);
    if (!pMap.isEmpty()) {
      PC.putPointsData(pMap);
    }
    PC.putAchievementData();
    PC.sendMailAchievmentDayly(Dbase, pointSitelist);
    logg.warn(total + sb.toString());
  }

  private static Double getSiteTotal(String site, String points, Double siteTotal) {
    double current = Double.parseDouble(Utille.getNumber(points));
    switch (site) {
      case Define.PSITE_CODE_OSA:
      case Define.PSITE_CODE_MOP:
      case Define.PSITE_CODE_PMO:
      case Define.PSITE_CODE_HAP:
        siteTotal += current;
        break;
      case Define.PSITE_CODE_CRI:
      case Define.PSITE_CODE_SUG:
        siteTotal += current / 2;
        break;
      case Define.PSITE_CODE_GMY:
      case Define.PSITE_CODE_PEX:
      case Define.PSITE_CODE_ECN:
      case Define.PSITE_CODE_I2I:
      case Define.PSITE_CODE_GEN:
      case Define.PSITE_CODE_PIL:
      case Define.PSITE_CODE_PIC:
      case Define.PSITE_CODE_MOB:
      case Define.PSITE_CODE_WAR:
      case Define.PSITE_CODE_CIT:
      case Define.PSITE_CODE_PST:
      case "secondPoint":
        siteTotal += current / 10;
        break;
      case Define.PSITE_CODE_PTO:
        siteTotal += current / 20;
        break;
      case Define.PSITE_CODE_PNY:
        siteTotal += current / 100;
        break;
      default:
        break;
    }
    return (double) Math.round(siteTotal * 10) / 10;
  }
}
