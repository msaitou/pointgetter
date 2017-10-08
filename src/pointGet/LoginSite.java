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
      case Define.PSITE_CODE_ECN:
      case Define.PSITE_CODE_I2I:
      case Define.PSITE_CODE_MOB:
      case Define.PSITE_CODE_CIT:
      case Define.PSITE_CODE_CRI:
      case Define.PSITE_CODE_HAP:
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
  public static void loginGpo(WebDriver driver, Logger logg) {
    driver.get("https://www.gpoint.co.jp/scripts/auth/LoginEntry.do");
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
  public static void loginDmy(WebDriver driver, Logger logg) {
    //    driver.get("https://dauth.user.ameba.jp/login/ameba");
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
  public static void loginGen(WebDriver driver, Logger logg) {
    driver
        .get("https://ssl.realworld.jp/auth/?site=gendama_jp&rid=&af=&frid=&token=&goto=http%3A%2F%2Fwww.gendama.jp%2F?p=start");
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
  public static void loginOsa(WebDriver driver, Logger logg) {
    driver.get("https://osaifu.com/contents/login/");
    Utille.sleep(2000);
    if (Utille.isExistEle(driver, "input[name='tel_or_email']", logg)) {
      WebElement ele = driver.findElement(By.cssSelector("input[name='tel_or_email']"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_OSA).get("loginid"));
      ele = driver.findElement(By.cssSelector("input[name='passwd']"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_OSA).get("loginpass"));
      driver.findElement(By.cssSelector("input[name='submit']")).click();
      Utille.sleep(5000);
    }
  }

  /**
   * @param driver
   * @param logg
   */
  public static void loginMop(WebDriver driver, Logger logg) {
    driver.get("https://ssl.pc.moppy.jp/login/");
    Utille.sleep(2000);
    if (Utille.isExistEle(driver, "input[name='mail']", logg)) {
      WebElement ele = driver.findElement(By.cssSelector("input[name='mail']"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_MOP).get("loginid"));
      ele = driver.findElement(By.cssSelector("input[name='pass']"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_MOP).get("loginpass"));
      driver.findElement(By.cssSelector("div.login-btn>button")).click();
      Utille.sleep(5000);
    }
  }

  /**
   * @param driver
   * @param logg
   */
  public static void loginPic(WebDriver driver, Logger logg) {
    driver.get("https://pointi.jp/entrance.php");
    Utille.sleep(2000);
    if (Utille.isExistEle(driver, "input.entrance_input[name='email_address']", logg)) {
      WebElement ele = driver.findElement(By.cssSelector("input.entrance_input[name='email_address']"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PIC).get("loginid"));
      ele = driver.findElement(By.cssSelector("input.entrance_input[name='password']"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PIC).get("loginpass"));
      driver.findElement(By.cssSelector("div.btn_submit>input[name='Submit']")).click();
      Utille.sleep(5000);
    }
  }

  /**
   * @param driver
   * @param logg
   */
  public static void loginPmo(WebDriver driver, Logger logg) {
    driver.get("https://ssl.poimon.jp/member/loginform/");
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
  public static void loginWar(WebDriver driver, Logger logg) {
    driver.get("https://ssl.warau.jp/login?loopbackURL=http%3A%2F%2Fwww.warau.jp%2F");
    Utille.sleep(2000);
    if (Utille.isExistEle(driver, "input.mailForm", logg)) {
      WebElement ele = driver.findElement(By.cssSelector("input.mailForm"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_WAR).get("loginid"));
      ele = driver.findElement(By.cssSelector("input.passwordForm"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_WAR).get("loginpass"));
      driver.findElement(By.cssSelector("input.btn.btnlogin")).click();
      Utille.sleep(4000);
    }
  }

  /**
   * @param driver
   * @param logg
   */
  public static void loginPil(WebDriver driver, Logger logg) {
    driver.get("http://www.point-island.com/");
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
  public static void loginRin(WebDriver driver, Logger logg) {
    driver.get("https://www.rakuten-card.co.jp/e-navi/index.xhtml");
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
  public static void loginPex(WebDriver driver, Logger logg) {
    driver.get("https://pex.jp/user/point_passbook/all");
    // ログイン画面
    String selector2 = "input#pex_user_login_email";
    if (Utille.isExistEle(driver, selector2, logg)) {
      WebElement ele = driver.findElement(By.cssSelector(selector2));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PEX).get("loginid"));
      ele = driver.findElement(By.cssSelector("input#pex_user_login_password"));
      ele.clear();
      ele.sendKeys(pGetProps.get(Define.PSITE_CODE_PEX).get("loginpass"));
      driver.findElement(By.cssSelector("input.form-submit")).click();
      Utille.sleep(3000);
    }
  }

  /**
   * @param driver
   * @param logg
   */
  public static void loginPto(WebDriver driver, Logger logg) {
    // ログイン画面
    driver.get("https://www.pointtown.com/ptu/show_login.do?nextPath=%2Fptu%2Findex.do");
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
}
