package pointGet.mission;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import pointGet.common.Utille;

/**
 *
 * @author saitou
 *
 */
public abstract class Mission extends MissCommon {
  /* execute count */
  protected int cnt = 0;
  /* execute count limit */
  protected int limit = 0;
  /* last execution time */
  protected long lastDoneTime = 0;
  /* is mission complete */
  protected boolean compFlag = false;
  /* first selector */
  protected String selector = "";

  protected static Map<String, String> commonProps = new HashMap<String, String>();

  /**
   * constracter
   *
   * @param log4j object
   */
  public Mission(Logger log, Map<String, String> cProps) {
    logg = log;
    commonProps = cProps;
  }

  /**
   * @return boolean(is mission complete)
   */
  public boolean isCompFlag() {
    return compFlag;
  }

  /**
   * @param driver
   */
  public WebDriver exePrivateMission(WebDriver driver) {
    return exeMission(driver, 0);
  }

  /**
   *
   * @param driver
   */
  public WebDriver exeRoopMission(WebDriver driver) {
    return exeMission(driver, 1);
  }

  /**
   * roop mission execute
   *
   * @param driver
   */
  public abstract void roopMission(WebDriver driver);

  /**
   * @param driver
   */
  public abstract void privateMission(WebDriver driver);

  /**
   * 
   * @param driver
   * @param i
   * @return
   */
  private WebDriver exeMission(WebDriver driver, int i) {
    return exeMission(driver, i, 1);
  }

  /**
   * 
   * @param driver
   * @param i
   * @param retryCnt
   * @return
   */
  private WebDriver exeMission(WebDriver driver, int i, int retryCnt) {
    logg.info("[[[" + mName + "]]] START-");
    try {
      if (i == 0) {
        privateMission(driver);
      }
      else {
        roopMission(driver);
      }
    } catch (WebDriverException we) {
      logg.error("##wException##################");
      logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(we), 1000));
      logg.error("#############################");
      driver.quit();
      driver = Utille.getWebDriver(commonProps.get("geckopath"), commonProps.get("ffprofile"));
    } catch (Exception e) {
      logg.error("##Exception##################");
      logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 1000));
      logg.error("#############################");
      if (retryCnt > 0) {
        return exeMission(driver, i, --retryCnt);
      }
    }
    logg.info("[[[" + mName + "]]] END-");
    return driver;
  }
}
