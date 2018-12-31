package pointGet.mission.gpo;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerWhichCollect;

/**
 * @author saitou
 *
 */
public class GPOWhichCollectUsedCar extends GPOBase {
  final String url = "https://www.gpoint.co.jp/gpark/";
  /* アンケートクラス　図鑑 */
  AnswerWhichCollect WhichCollect = null;

  /**
   * @param logg
   */
  public GPOWhichCollectUsedCar(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "どっちが正解？中古車");
    WhichCollect = new AnswerWhichCollect(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    String sele1 = "img[alt*='中古車編']";
    WhichCollect.answer(driver, sele1, null);
  }
}
