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
            selector = "span.user_point";
            driver.get("http://dietnavi.com/pc");
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
            driver.get("http://www.gendama.jp/");
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
            driver.get("https://ecnavi.jp/mypage/point_history/");
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_ECN + ":" + Utille.getNumber(point) + "]";
            }
            break;
          case Define.PSITE_CODE_MOP:
            if (pointMap.containsKey(Define.PSITE_CODE_MOP)) {
              break;
            }
            driver.get("http://pc.moppy.jp/");
            selector = "div#preface>ul.pre__login__inner";
            if (!isExistEle(driver, selector)) {
              // login!!
              LoginSite.login(Define.PSITE_CODE_MOP, driver, logg);
            }
            selector = "div#point_blinking strong";
            driver.get("http://pc.moppy.jp/bankbook/");
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
            driver.get("https://pex.jp/user/point_passbook/all");
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
            driver.get("http://osaifu.com/");
            if (!isExistEle(driver, selector)) {
              // login!!
              LoginSite.login(Define.PSITE_CODE_OSA, driver, logg);
            }
            selector = "dl.bankbook-total>dd.current.coin>span";
            driver.get("https://osaifu.com/contents/bankbook/top/");
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
            driver.get("https://www.pointtown.com/ptu/top");
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
            driver.get("https://point.i2i.jp/account/");
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
            //					driver.get("http://www.point-island.com/");
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
            selector = "p.text.point";
            driver.get("http://pointi.jp/my/my_page.php"); // http://pointi.jp/
            if (!isExistEle(driver, selector)) {
              // login!!
              LoginSite.login(Define.PSITE_CODE_PIC, driver, logg);
              driver.get("http://pointi.jp/my/my_page.php"); // http://pointi.jp/
            }
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
            driver.get("http://hapitas.jp/");
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
            driver.get("http://pc.mtoku.jp/mypage/bankbook/");
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
            driver.get("http://www.chobirich.com/");
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
            driver.get("https://www.poney.jp/");
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
            selector = "span#user-mile-status-earn";
            driver.get("http://www.sugutama.jp/passbook");
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
            driver.get("http://www.warau.jp/");
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
            selector = "li.user>span.user_pt";
            driver.get("https://www.chance.com/");
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
            driver.get("http://poimon.jp/");
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
            driver.get("http://www.point-stadium.com/");
            if (isExistEle(driver, selector)) {
              point = driver.findElement(By.cssSelector(selector)).getText();
              outPut = "[" + Define.PSITE_CODE_PST + ":" + Utille.getNumber(point) + "]";
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
    //		pMap.put("mop", 582.9);
    //		pMap.put("osa", 183.0);
    PointsCollection PC = new PointsCollection(Dbase);
    if (!pMap.isEmpty()) {
      PC.putPointsData(pMap);
    }
    PC.putAchievementData();
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
