package pointGet.mission;

import static org.junit.Assert.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.val;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import pointGet.common.Eventually;
import pointGet.common.Utille;

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
        ((JavascriptExecutor) driver).executeScript("return document.readyState"), "complete"), Duration.ofSeconds(30),
        Duration.ofSeconds(3));
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
  * @param wEle
  * @param selector
  * @return
  */
  protected boolean isExistEle(WebElement wEle, String selector, boolean showFlag) {
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
  * @return
  */
  protected int getSelectorSize(WebElement ele, String selector) {
    return ele.findElements(By.cssSelector(selector)).size();
  }

  /**
   *
   * @param driver
   * @param selector
   */
  protected void clickSelector(WebDriver driver, String selector) {
    WebElement ele = driver.findElement(By.cssSelector(selector));
    clickSelector(driver, ele);
  }

  /**
   *
   * @param eleList
   * @param index
   */
  protected void clickSelector(WebDriver driver, List<WebElement> eleList, int index) {
    clickSelector(driver, eleList.get(index));
  }

  /**
   *
   * @param ele
   * @param selector
   */
  protected void clickSelector(WebDriver driver, WebElement ele, String selector) {
    clickSelector(driver, ele.findElement(By.cssSelector(selector)));
  }

//  /**
//   *
//   * @param ele
//   * @param selector
//   */
//  protected void clickSelector(WebElement ele) {
//    int i = 0;
//    while (i++ < 5) {
//      try {
//        ele.click();
//        return;
//      } catch (TimeoutException te) {
//    	  
//      } catch (WebDriverException e) {
//        logg.error("-clickSelector error-------------------");
//        logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 50));
//      }
//    }
//  }

  /**
  *
  * @param ele
  * @param selector
  */
 protected void clickSelector(WebDriver driver, WebElement ele) {
   Utille.scrolledPage(driver, ele);
   int i = 0;
   while (i++ < 5) {
     try {
       ele.click();
       return;
     } catch (TimeoutException te) {
    	 Utille.refresh(driver, logg);
     } catch (WebDriverException e) {
       logg.error("-clickSelector error-------------------");
       logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 50));
     }
   }
 }

 /**
   *
   * @param driver
   * @param selector
   * @param milliSeconds
   */
  protected void clickSleepSelector(WebDriver driver, String selector, int milliSeconds) {
    WebElement ele = driver.findElement(By.cssSelector(selector));
    clickSleepSelector(driver, ele, milliSeconds);
  }

  /**
   *
   * @param eleList
   * @param index
   * @param milliSeconds
   */
  protected void clickSleepSelector(WebDriver driver, List<WebElement> eleList, int index, int milliSeconds) {
    clickSleepSelector(driver, eleList.get(index), milliSeconds);
  }

  /**
   *
   * @param ele
   * @param selector
   * @param milliSeconds
   */
  protected void clickSleepSelector(WebDriver driver, WebElement ele, String selector, int milliSeconds) {
    clickSleepSelector(driver, ele.findElement(By.cssSelector(selector)), milliSeconds);
  }

  /**
   *
   * @param ele
   * @param selector
   * @param milliSeconds
   */
  protected void clickSleepSelector(WebDriver driver, WebElement ele, int milliSeconds) {
    clickSelector(driver, ele);
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
    } catch (NoSuchWindowException e) {
      logg.error("##NOT EXIST Alert2##################");
      Utille.sleep(2000);
    }
  }
}
