package pointGet.mission;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.val;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Eventually;
import pointGet.Utille;

public abstract class MissCommon {
  /* log */
  protected Logger logg = null;
  /* mission name */
  protected String mName = "";

  /**
   * @return
   */
  public static WebDriver getWebDriver(Map<String, String> cProps) {
    return Utille.getWebDriver(cProps.get("geckopath"), cProps.get("ffprofile"));
  }

  /**
   * @param driver
   */
  protected void waitTilReady(WebDriver driver) {
    Eventually.eventually(() -> assertEquals(
        ((JavascriptExecutor) driver).executeScript("return document.readyState"), "complete"));
  }

  /**
   *
   * @param driver
   * @param selector
   * @return
   */
  protected boolean isExistEle(WebDriver driver, String selector) {
    return Utille.isExistEle(driver, selector, true, logg);
  }

  /**
   *
   * @param driver
   * @param selector
   * @return
   */
  protected boolean isExistEle(WebDriver driver, String selector, boolean showFlag) {
    return Utille.isExistEle(driver, selector, showFlag, logg);
  }

  /**
   *
   * @param ele
   * @return
   */
  protected boolean isExistEle(List<WebElement> ele) {
    return Utille.isExistEle(ele, logg);
  }

  /**
   *
   * @param ele
   * @param index
   * @return
   */
  protected boolean isExistEle(List<WebElement> ele, int index) {
    boolean is = isExistEle(ele.get(index));
    logg.info(index + ":[" + is + "]");
    return is;
  }

  /**
   *
   * @param ele
   * @return
   */
  protected boolean isExistEle(WebElement ele) {
    List<WebElement> eleL = new ArrayList<WebElement>();
    eleL.add(ele);
    return isExistEle(eleL);
  }

  /**
   *
   * @param wEle
   * @param selector
   * @return
   */
  protected boolean isExistEle(WebElement wEle, String selector) {
    return Utille.isExistEle(wEle, selector, logg);
  }

  /**
   *
   * @param driver
   * @param selector
   * @return
   */
  protected int getSelectorSize(WebDriver driver, String selector) {
    return driver.findElements(By.cssSelector(selector)).size();
  }

  /**
   *
   * @param driver
   * @param selector
   */
  protected void clickSelector(WebDriver driver, String selector) {
    WebElement ele = driver.findElement(By.cssSelector(selector));
    Utille.scrolledPage(driver, ele);
    clickSelector(ele);
  }

  /**
   *
   * @param eleList
   * @param index
   */
  protected void clickSelector(List<WebElement> eleList, int index) {
    clickSelector(eleList.get(index));
  }

  /**
   *
   * @param ele
   * @param selector
   */
  protected void clickSelector(WebElement ele, String selector) {
    clickSelector(ele.findElement(By.cssSelector(selector)));
  }

  /**
   *
   * @param ele
   * @param selector
   */
  protected void clickSelector(WebElement ele) {
    ele.click();
  }

  /**
   *
   * @param driver
   * @param selector
   * @param milliSeconds
   */
  protected void clickSleepSelector(WebDriver driver, String selector, int milliSeconds) {
    WebElement ele = driver.findElement(By.cssSelector(selector));
    Utille.scrolledPage(driver, ele);
    clickSleepSelector(ele, milliSeconds);
  }

  /**
   *
   * @param eleList
   * @param index
   * @param milliSeconds
   */
  protected void clickSleepSelector(List<WebElement> eleList, int index, int milliSeconds) {
    clickSleepSelector(eleList.get(index), milliSeconds);
  }

  /**
   *
   * @param ele
   * @param selector
   * @param milliSeconds
   */
  protected void clickSleepSelector(WebElement ele, String selector, int milliSeconds) {
    clickSleepSelector(ele.findElement(By.cssSelector(selector)), milliSeconds);
  }

  /**
   *
   * @param ele
   * @param selector
   * @param milliSeconds
   */
  protected void clickSleepSelector(WebElement ele, int milliSeconds) {
    clickSelector(ele);
    Utille.sleep(milliSeconds);
  }

  /**
  *
  * @param driver
  * @param selector
  * @param waitFlag
  */
  protected void checkOverlay(WebDriver driver, String selector, boolean isWait) {
    int i = 0;
    while (i < 10) {
      try {
        if (isWait) {
          waitTilReady(driver);
        }
        if (isExistEle(driver, selector)) {
          clickSleepSelector(driver, selector, 2000); // 広告消す
          logg.info("広告消してやったぜ");
        }
        break;
      } catch (Throwable e) {
        logg.error("広告消えない[" + ++i + "回目]");
        e.printStackTrace();
        Utille.sleep(2000);
        continue;
      }
      // logg.info("check roop[" + i + "]");
    }
  }

  /**
   *
   * @param driver
   * @param selector
   */
  protected void checkOverlay(WebDriver driver, String selector) {
    checkOverlay(driver, selector, true);
  }

  /**
   * to active the new windows
   *
   * @param driver
   */
  protected void changeCloseWindow(WebDriver driver) {
    String wid = driver.getWindowHandle();
    java.util.Set<String> widSet = driver.getWindowHandles();
    if (widSet.size() > 1) {
      String new_window_id = null;
      for (String id : widSet) {
        if (!id.equals(wid)) {
          // 現在のウインドウIDと違っていたら格納
          // 最後に格納されたIDが一番新しく開かれたウインドウと判定
          new_window_id = id;
        }
      }
      driver.close();
      // 最後に格納したウインドウIDにスイッチ
      driver.switchTo().window(new_window_id);
    }
  }

  /**
   * to active the new windows
   *
   * @param driver
   * @param wid
   */
  protected void changeWindow(WebDriver driver, String wid) {
    java.util.Set<String> widSet = driver.getWindowHandles();
    for (String id : widSet) {
      if (!id.equals(wid)) {
        driver.switchTo().window(id);
      }
    }
  }

  /**
   *
   * @param driver
   */
  protected void closeOtherWindow(WebDriver driver) {
    String wid = driver.getWindowHandle();
    java.util.Set<String> widSet = driver.getWindowHandles();
    for (String id : widSet) {
      if (!id.equals(wid)) {
        // 最後に格納したウインドウIDにスイッチして閉じる
        driver.switchTo().window(id);
        driver.close();
      }
    }
    // 元のウインドウIDにスイッチ
    driver.switchTo().window(wid);
  }

  /**
   * 
   * @param driver
   */
  public void checkAndAcceptAlert(WebDriver driver) {
    try {
      // アラートをけして
      val alert = driver.switchTo().alert();
      alert.accept();
      Utille.sleep(3000);
    } catch (NoAlertPresentException ae) {
      logg.error("##NOT EXIST Alert##################");
      logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(ae), 1000));
      logg.error("#############################");
    } catch (NoSuchWindowException e) {
      logg.error("##NOT EXIST Alert2##################");
      logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 1000));
      logg.error("#############################");
      Utille.sleep(2000);
    }
  }
}
