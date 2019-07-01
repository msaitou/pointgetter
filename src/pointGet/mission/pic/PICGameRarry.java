package pointGet.mission.pic;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import pointGet.common.Utille;
import pointGet.mission.parts.GameBears;
import pointGet.mission.parts.GameDinosaur;
import pointGet.mission.parts.GameFish;
import pointGet.mission.parts.GameJewel;
import pointGet.mission.parts.GameOtenba;
import pointGet.mission.parts.GameQuiz;
import pointGet.mission.parts.GameRace;

/**
 *
 * @author saitou
 */
public class PICGameRarry extends PICBase {
  final String url = "https://pointi.jp/game/";
  GameJewel Jewel = null;
  GameOtenba Otenba = null;
  GameBears Bears = null;
  GameFish Fish = null;
  GameQuiz Quiz = null;
  GameDinosaur Dinosaur = null;
  GameRace Race = null;

  /**
   * @param logg
   */
  public PICGameRarry(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ゲームラリー");
    Jewel = new GameJewel(logg);
    Otenba = new GameOtenba(logg);
    Bears = new GameBears(logg);
    Fish = new GameFish(logg);
    Quiz = new GameQuiz(logg);
    Dinosaur = new GameDinosaur(logg);
    Race = new GameRace(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    selector = "img[alt='ゲームボックス']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 8000); // 遷移
      changeCloseWindow(driver);
    }

    String[] gameList = {
        "a>img[alt='リーグオブジュエル']", // jewel
        "a>img[alt='戦国!姫のお宝さがし']", // otenba
        "a>img[alt='']", // bears
        "a>img[alt='釣神']", // fish
        "a>img[alt='スクールオブクイズ']", // quiz
        "a>img[alt='']", // dinosaur
        "a>img[alt='ベジモンレース']",// race
    };

    String[] gameList2 = {
        "",
        "a>img[src*='otenba']", // otenba
        "a>img[src*='bears']", // bears
        "a>img[src*='fish']", // fish
        "a>img[src*='quiz']", // quiz
        "a>img[src*='dinosaur']", // dinosaur
        "a>img[src*='race']",// race
    };

    for (int i = 0; i < gameList.length; i++) {
      if (i == 2 
//          || i == 3 
          || i == 5
          )
        continue;
      if (isExistEle(driver, gameList[i])) {

        // jewel
        if (gameList[i].equals(gameList[0])) {
          String wid = driver.getWindowHandle();
          Utille.scrolledPage(driver, driver.findElement(By.cssSelector(gameList[i])));
          Utille.sleep(1000);
          Actions actions = new Actions(driver);
          actions.keyDown(Keys.CONTROL);
          actions.click(driver.findElement(By.cssSelector(gameList[i])));
          actions.perform();
          Utille.sleep(5000);
          //          changeWindow(driver, wid);
          changeCloseWindow(driver);
          //          Utille.sleep(3000);
          //          clickSleepSelector(driver, gameList[i], 2000); // 遷移
          Utille.sleep(4000);
          Jewel.answer(driver, null, wid);
          gameList = gameList2; // 入れ替える
        }
        // otenba
        else if (gameList[i].equals(gameList[1])) {
          String wid = driver.getWindowHandle();
          clickSleepSelector(driver, gameList[i], 2000); // 遷移
          changeCloseWindow(driver);
          Utille.sleep(4000);
          Otenba.answer(driver, null, wid);
          gameList = gameList2; // 入れ替える
        }
        // bears
        else if (gameList[i].equals(gameList[2])) {
          String wid = driver.getWindowHandle();
          clickSleepSelector(driver, gameList[i], 2000); // 遷移
          changeCloseWindow(driver);
          Utille.sleep(4000);
          Bears.answer(driver, null, wid);
          gameList = gameList2; // 入れ替える
        }
        // fish
        else if (gameList[i].equals(gameList[3])) {
          String wid = driver.getWindowHandle();
          clickSleepSelector(driver, gameList[i], 2000); // 遷移
          changeCloseWindow(driver);
          Utille.sleep(4000);
          Fish.answer(driver, null, wid);
          gameList = gameList2; // 入れ替える
        }
        // quiz
        else if (gameList[i].equals(gameList[4])) {
          String wid = driver.getWindowHandle();
          clickSleepSelector(driver, gameList[i], 2000); // 遷移
          changeCloseWindow(driver);
          Utille.sleep(4000);
          Quiz.answer(driver, null, wid);
          gameList = gameList2; // 入れ替える
        }
        // dinosaur
        else if (gameList[i].equals(gameList[5])) {
          String wid = driver.getWindowHandle();
          clickSleepSelector(driver, gameList[i], 2000); // 遷移
          changeCloseWindow(driver);
          Utille.sleep(4000);
          Dinosaur.answer(driver, null, wid);
          gameList = gameList2; // 入れ替える
        }
        // Race
        else if (gameList[i].equals(gameList[6])) {
          String wid = driver.getWindowHandle();
          clickSleepSelector(driver, gameList[i], 2000); // 遷移
          changeCloseWindow(driver);
          Utille.sleep(4000);
          Race.answer(driver, null, wid);
          gameList = gameList2; // 入れ替える
        }
      }
    }

    String stampRes = "#stamp_recieve_btn";
    for (int i = 0; i < 2; i++) {
      if (isExistEle(driver, stampRes, false)) {
        clickSleepSelector(driver, stampRes, 2000); // GET
      }
      Utille.refresh(driver, logg);
    }
  }
}
