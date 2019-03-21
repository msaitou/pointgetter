package pointGet;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Define;
import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class LoginSite extends PointGet {

  private static String selector = "";

  /**
   * @param siteCode
   * @param driver
   * @param logg
   */
  public static void login(String siteCode, WebDriver driver, Logger logg) {
    switch (siteCode) {
      case Define.PSITE_CODE_RIN:
        loginRin(driver, logg);
        break;
      case Define.PSITE_CODE_PEX:
        loginPex(driver, logg);
        break;
      case Define.PSITE_CODE_PTO:
        loginPto(driver, logg);
        break;
      case Define.PSITE_CODE_PIL:
        loginPil(driver, logg);
        break;
      case Define.PSITE_CODE_WAR:
        loginWar(driver, logg);
        break;
      case Define.PSITE_CODE_PMO:
        loginPmo(driver, logg);
        break;
      case Define.PSITE_CODE_PIC:
        loginPic(driver, logg);
        break;
      case Define.PSITE_CODE_MOP:
        loginMop(driver, logg);
        break;
      case Define.PSITE_CODE_OSA:
        loginOsa(driver, logg);
        break;
      case Define.PSITE_CODE_GEN:
        loginGen(driver, logg);
        break;
      case Define.PSITE_CODE_DMY:
        loginDmy(driver, logg);
        break;
      case Define.PSITE_CODE_GPO:
        loginGpo(driver, logg);
        break;
      case Define.PSITE_CODE_GMY:
        loginGmy(driver, logg);
        break;
      case Define.PSITE_CODE_CIT:
        loginCit(driver, logg);
        break;
      case Define.PSITE_CODE_HAP:
        loginHap(driver, logg);
        break;
      case Define.PSITE_CODE_LFM:
        loginLfm(driver, logg);
        break;
      case Define.PSITE_CODE_CMS:
        loginCms(driver, logg);
        break;
      case Define.PSITE_CODE_ECN:
      case Define.PSITE_CODE_I2I:
      case Define.PSITE_CODE_MOB:
      case Define.PSITE_CODE_CRI:
      case Define.PSITE_CODE_KOZ:
      case Define.PSITE_CODE_NTM:
      case Define.PSITE_CODE_PNY:
      case Define.PSITE_CODE_SUG:
      default:
        return;
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginCms(WebDriver driver, Logger logg) {
    String seleLogin = "ul#GuestMenu>li>a.thickbox";
    if (Utille.isExistEle(driver, seleLogin, logg)) {
      driver.findElement(By.cssSelector(seleLogin)).click();
      Utille.sleep(4000);
      if (Utille.isExistEle(driver, "input#usermei", logg)) {
        WebElement ele = driver.findElement(By.cssSelector("input#usermei"));
        ele.clear();
        ele.sendKeys(pGetProps.get(Define.PSITE_CODE_CMS).get("loginid"));
        ele = driver.findElement(By.cssSelector("input[type='password']"));
        ele.clear();
        ele.sendKeys(pGetProps.get(Define.PSITE_CODE_CMS).get("loginpass"));
        //        Utille.clickRecaptha(driver, logg);
        driver.findElement(By.cssSelector("input#btn_send")).click();
        Utille.sleep(5000);
      }
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginLfm(WebDriver driver, Logger logg) {
    if (!isLongin(Define.PSITE_CODE_LFM, driver, logg)) {
      String seleLogin = "li.log>a>span";
      if (Utille.isExistEle(driver, seleLogin, logg)) {
        driver.findElement(By.cssSelector(seleLogin)).click();
        Utille.sleep(4000);
        if (Utille.isExistEle(driver, "input[name='login[cfid]']", logg)) {
          WebElement ele = driver.findElement(By.cssSelector("input[name='login[cfid]']"));
          ele.clear();
          ele.sendKeys(pGetProps.get(Define.PSITE_CODE_LFM).get("loginid"));
          ele = driver.findElement(By.cssSelector("input[name='login[passwd]']"));
          ele.clear();
          ele.sendKeys(pGetProps.get(Define.PSITE_CODE_LFM).get("loginpass"));
          //        Utille.clickRecaptha(driver, logg);
          driver.findElement(By.cssSelector("div>input.next")).click();
          Utille.sleep(5000);
        }
      }
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginHap(WebDriver driver, Logger logg) {
    String seleLogin = "a.usernavi-link-signin";
    if (Utille.isExistEle(driver, seleLogin, logg)) {
      driver.findElement(By.cssSelector(seleLogin)).click();
      Utille.sleep(4000);
      if (Utille.isExistEle(driver, "li>input[name='mail']", logg)) {
        WebElement ele = driver.findElement(By.cssSelector("li>input[name='mail']"));
        ele.clear();
        ele.sendKeys(pGetProps.get(Define.PSITE_CODE_HAP).get("loginid"));
        ele = driver.findElement(By.cssSelector("li>input[name='password']"));
        ele.clear();
        ele.sendKeys(pGetProps.get(Define.PSITE_CODE_HAP).get("loginpass"));
        // 自動ログインチェック（一応）
        if (Utille.isExistEle(driver, "p>input[name='login_keep']", logg)) {
          driver.findElement(By.cssSelector("p>input[name='login_keep']")).click();
        }
        Utille.clickRecaptha(driver, logg);
        driver.findElement(By.cssSelector("p>input[type='submit']")).click();
        Utille.sleep(5000);
      }
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginCit(WebDriver driver, Logger logg) {
    String seleLogin = "li.login>a>span";
    if (Utille.isExistEle(driver, seleLogin, logg)) {
      driver.findElement(By.cssSelector(seleLogin)).click();
      Utille.sleep(4000);
      if (Utille.isExistEle(driver, "dd>input[name='id']", logg)) {
        WebElement ele = driver.findElement(By.cssSelector("dd>input[name='id']"));
        ele.clear();
        ele.sendKeys(pGetProps.get(Define.PSITE_CODE_CIT).get("loginid"));
        ele = driver.findElement(By.cssSelector("dd>input[name='password']"));
        ele.clear();
        ele.sendKeys(pGetProps.get(Define.PSITE_CODE_CIT).get("loginpass"));
        Utille.clickRecaptha(driver, logg);
        driver.findElement(By.cssSelector("p.btn_login>input.bt")).click();
        Utille.sleep(5000);
      }
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginGmy(WebDriver driver, Logger logg) {
    String seleLogin = "li.btn_login>a";
    if (Utille.isExistEle(driver, seleLogin, logg)) {
      driver.findElement(By.cssSelector(seleLogin)).click();
      Utille.sleep(4000);
      if (Utille.isExistEle(driver, "dd>input[name='mail']", logg)) {
        WebElement ele = driver.findElement(By.cssSelector("dd>input[name='mail']"));
        ele.clear();
        ele.sendKeys(pGetProps.get(Define.PSITE_CODE_GMY).get("loginid"));
        ele = driver.findElement(By.cssSelector("dd>input[name='pass']"));
        ele.clear();
        ele.sendKeys(pGetProps.get(Define.PSITE_CODE_GMY).get("loginpass"));
        Utille.clickRecaptha(driver, logg);
        driver.findElement(By.cssSelector("p>input.login_btn")).click();
        Utille.sleep(5000);
      }
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginGpo(WebDriver driver, Logger logg) {
    Utille.url(driver, "https://www.gpoint.co.jp/scripts/auth/LoginEntry.do", logg);
    Utille.sleep(2000);
    if (Utille.isExistEle(driver, "input#userid", logg)) {
      WebElement ele = driver.findElement(By.cssSelector("input#userid"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_GPO).get("loginid"));
      ele = driver.findElement(By.cssSelector("input#passwd"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_GPO).get("loginpass"));
      driver.findElement(By.cssSelector("input.btnlogin")).click();
      Utille.sleep(5000);
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginDmy(WebDriver driver, Logger logg) {
    //    Utille.url(driver, "https://dauth.user.ameba.jp/login/ameba", logg);
    String loginBtnSele = "a.c-btn-login";
    String loginBtnSele2 = "input.btn_primary";
    if (Utille.isExistEle(driver, loginBtnSele, logg)) {
      driver.findElement(By.cssSelector(loginBtnSele)).click();
      Utille.sleep(2000);
      if (Utille.isExistEle(driver, loginBtnSele2, logg)) {
        driver.findElement(By.cssSelector(loginBtnSele2)).click();
        Utille.sleep(2000);
        if (Utille.isExistEle(driver, "input[name='accountId']", logg)) {
          WebElement ele = driver.findElement(By.cssSelector("input[name='accountId']"));
          ele.clear();
          ele.sendKeys(pGetProps.get(Define.PSITE_CODE_DMY).get("loginid"));
          ele = driver.findElement(By.cssSelector("input[name='password']"));
          ele.clear();
          ele.sendKeys(pGetProps.get(Define.PSITE_CODE_DMY).get("loginpass"));
          driver.findElement(By.cssSelector("button[type='submit']")).click();
          Utille.sleep(5000);
        }
      }
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginGen(WebDriver driver, Logger logg) {
    driver
        .get(
            "https://ssl.realworld.jp/auth/?site=gendama_jp&rid=&af=&frid=&token=&goto=http%3A%2F%2Fwww.gendama.jp%2F?p=start");
    Utille.sleep(2000);
    if (Utille.isExistEle(driver, "input[name='rwsid']", logg)) {
      WebElement ele = driver.findElement(By.cssSelector("input[name='rwsid']"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_GEN).get("loginid"));
      ele = driver.findElement(By.cssSelector("input[name='pass']"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_GEN).get("loginpass"));
      Utille.clickRecaptha(driver, logg);
      driver.findElement(By.cssSelector("input[name='login_page']")).click();
      Utille.sleep(5000);
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginOsa(WebDriver driver, Logger logg) {
    Utille.url(driver, "https://osaifu.com/login/", logg);
    Utille.sleep(2000);
    if (Utille.isExistEle(driver, "input[name='_username']", logg)) {
      WebElement ele = driver.findElement(By.cssSelector("input[name='_username']"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_OSA).get("loginid"));
      ele = driver.findElement(By.cssSelector("input[name='_password']"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_OSA).get("loginpass"));
      driver.findElement(By.cssSelector("button[type='submit']")).click();
      Utille.sleep(5000);
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginMop(WebDriver driver, Logger logg) {
    Utille.url(driver, "https://ssl.pc.moppy.jp/login/", logg);
    Utille.sleep(2000);
    if (Utille.isExistEle(driver, "input[name='mail']", logg)) {
      WebElement ele = driver.findElement(By.cssSelector("input[name='mail']"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_MOP).get("loginid"));
      ele = driver.findElement(By.cssSelector("input[name='pass']"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_MOP).get("loginpass"));
      driver.findElement(By.cssSelector("div>button.a-btn__login")).click();
      Utille.sleep(5000);
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginPic(WebDriver driver, Logger logg) {
    if (!isLongin(Define.PSITE_CODE_PIC, driver, logg)) {
      Utille.url(driver, "https://pointi.jp/entrance.php", logg);
      Utille.sleep(2000);
      if (Utille.isExistEle(driver, "input[name='email_address']", logg)) {
        WebElement ele = driver.findElement(By.cssSelector("input[name='email_address']"));
        ele.clear();
        ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PIC).get("loginid"));
        ele = driver.findElement(By.cssSelector("input[name='password']"));
        ele.clear();
        ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PIC).get("loginpass"));
        driver.findElement(By.cssSelector("div.entrance_left>input[name='Submit']")).click();
        Utille.sleep(5000);
      }
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginPmo(WebDriver driver, Logger logg) {
    Utille.url(driver, "https://ssl.poimon.jp/member/loginform/", logg);
    Utille.sleep(2000);
    if (Utille.isExistEle(driver, "input.mailForm", logg)) {
      WebElement ele = driver.findElement(By.cssSelector("input.mailForm"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PMO).get("loginid"));
      ele = driver.findElement(By.cssSelector("input.passwordForm"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PMO).get("loginpass"));
      driver.findElement(By.cssSelector("input.btnSubmit")).click();
      Utille.sleep(4000);
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginWar(WebDriver driver, Logger logg) {
    Utille.url(driver, "https://ssl.warau.jp/login?loopbackURL=http%3A%2F%2Fwww.warau.jp%2F", logg);
    Utille.sleep(2000);
    if (Utille.isExistEle(driver, "input.mailForm", logg)) {
      WebElement ele = driver.findElement(By.cssSelector("input.mailForm"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_WAR).get("loginid"));
      ele = driver.findElement(By.cssSelector("input.passwordForm"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_WAR).get("loginpass"));
      Utille.clickRecaptha(driver, logg);

      driver.findElement(By.cssSelector("input.login-Btn_Login")).click();
      Utille.sleep(4000);
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginPil(WebDriver driver, Logger logg) {
    Utille.url(driver, "http://www.point-island.com/", logg);
    if (Utille.isExistEle(driver, "input#mailadr", logg)) {
      WebElement ele = driver.findElement(By.cssSelector("input#mailadr"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PIL).get("loginid"));
      ele = driver.findElement(By.cssSelector("input#pwd"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PIL).get("loginpass"));
      driver.findElement(By.cssSelector("input[name='btnlogin']")).click();
      Utille.sleep(8000);
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginRin(WebDriver driver, Logger logg) {
    Utille.url(driver, "https://www.rakuten-card.co.jp/e-navi/index.xhtml", logg);
    selector = "li#loginId>input#u";
    // ログイン画面であれば
    if (Utille.isExistEle(driver, selector, logg)) {
      if (!pGetProps.containsKey(Define.PSITE_CODE_RIN)
          || !pGetProps.get(Define.PSITE_CODE_RIN).containsKey("loginid")
          || !pGetProps.get(Define.PSITE_CODE_RIN).containsKey("loginpass")) {
        return;
      }
      WebElement ele = driver.findElement(By.cssSelector(selector));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_RIN).get("loginid"));
      selector = "li#loginPw>input#p";
      ele = driver.findElement(By.cssSelector(selector));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_RIN).get("loginpass"));
      driver.findElement(By.cssSelector("input#loginButton")).click();
      Utille.sleep(5000);
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginPex(WebDriver driver, Logger logg) {
    Utille.url(driver, "https://pex.jp/user/point_passbook/all", logg);
    // ログイン画面
    String selector2 = "input#pex_user_login_email";
    if (Utille.isExistEle(driver, selector2, logg)) {
      WebElement ele = driver.findElement(By.cssSelector(selector2));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PEX).get("loginid"));
      ele = driver.findElement(By.cssSelector("input#pex_user_login_password"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PEX).get("loginpass"));
      Utille.clickRecaptha(driver, logg);
      String seleKeep = "label[for='pex_user_login_remember']";
      if (Utille.isExistEle(driver, seleKeep, logg)) {
        driver.findElement(By.cssSelector(seleKeep)).click();
      }
      driver.findElement(By.cssSelector("input.form-submit")).click();
      Utille.sleep(3000);
    }
  }

  /**
   * @param driver
   * @param logg
   */
  private static void loginPto(WebDriver driver, Logger logg) {
    // ログイン画面
    Utille.url(driver, "https://www.pointtown.com/ptu/show_login.do?nextPath=%2Fptu%2Findex.do", logg);
    String selector2 = "input.auth_input[name=uid]";
    if (isExistEle(driver, selector2)) {
      WebElement ele = driver.findElement(By.cssSelector(selector2));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PTO).get("loginid"));
      ele = driver.findElement(By.cssSelector("input.auth_input[name=pass]"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PTO).get("loginpass"));
      Utille.clickRecaptha(driver, logg);
      driver.findElement(By.cssSelector("div.login-btn>input")).click();
      Utille.sleep(3000);
    }
  }

  /**
   * @param siteCode
   * @param driver
   * @param logg
   */
  public static boolean isLongin(String siteCode, WebDriver driver, Logger logg) {
    String sel = "";
    boolean res = false;
    switch (siteCode) {
      case Define.PSITE_CODE_RIN:
      case Define.PSITE_CODE_PEX:
      case Define.PSITE_CODE_PTO:
      case Define.PSITE_CODE_PIL:
      case Define.PSITE_CODE_WAR:
      case Define.PSITE_CODE_PMO:
      case Define.PSITE_CODE_PIC:
        sel = "ul a[href*='/exchange/pts_exchange_top.php']";
        res = isExistEle(driver, sel, false);
        break;
      case Define.PSITE_CODE_MOP:
      case Define.PSITE_CODE_OSA:
      case Define.PSITE_CODE_GEN:
      case Define.PSITE_CODE_DMY:
      case Define.PSITE_CODE_GPO:
      case Define.PSITE_CODE_GMY:
      case Define.PSITE_CODE_CIT:
      case Define.PSITE_CODE_HAP:
      case Define.PSITE_CODE_LFM:
        sel = "em#mempoint";
        res = isExistEle(driver, sel, false);
        break;
      case Define.PSITE_CODE_CMS:
      case Define.PSITE_CODE_ECN:
      case Define.PSITE_CODE_I2I:
      case Define.PSITE_CODE_MOB:
      case Define.PSITE_CODE_CRI:
      case Define.PSITE_CODE_KOZ:
      case Define.PSITE_CODE_NTM:
      case Define.PSITE_CODE_PNY:
      case Define.PSITE_CODE_SUG:
      default:
        logg.info("isLongin 知らんのが来た[" + siteCode + "]");
    }
    return res;
  }

}
