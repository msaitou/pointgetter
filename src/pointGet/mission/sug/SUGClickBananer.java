package pointGet.mission.sug;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;

public class SUGClickBananer extends SUGBase {
	final String url = "http://www.sugutama.jp/#daily";
	private static HashMap<String, HashMap<String, String>> clickMap = new HashMap<String, HashMap<String, String>>() {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		{
			put("topページ", new HashMap<String, String>() {
				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				{
					put("url", "https://www.sugutama.jp/#daily");
					put("sele", "ul.listadItem>li>span.daily>div");
				}
			});
      put("shoppingページ", new HashMap<String, String>() {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        {
          put("url", "https://www.sugutama.jp/shop/#daily");
          put("sele", "ul.box_4card.listadItem>li>span.daily>div");
        }
      });
      put("savingページ", new HashMap<String, String>() {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        {
          put("url", "https://www.sugutama.jp/savings/#daily");
          put("sele", "span.daily dd.ad-name");
        }
      });
		}
	};

	/**
	 * @param logg
	 */
	public SUGClickBananer(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "クリックバナー");
	}

	@Override
	public void privateMission(WebDriver driver) {
		//		Utille.url(driver, url, logg);
		int c = 0;
		for (Map.Entry<String, HashMap<String, String>> clMap : clickMap.entrySet()) {
			Utille.url(driver, clMap.getValue().get("url"), logg);
			Utille.sleep(5000);
			switch (clMap.getKey()) {
//				case "topページ":
//					if (isExistEle(driver, clMap.getValue().get("sele"))) {
//						logg.info(mName + " " + clMap.getKey() + "!");
//						clickSleepSelector(driver, clMap.getValue().get("sele"), 2000);
//						closeOtherWindow(driver);
//					}
//					break;
        case "topページ":
          if (isExistEle(driver, clMap.getValue().get("sele"))) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(clMap.getValue().get("sele")));
            int stampNum = eleList.size();
            for (int i = 0; i < stampNum; i++) {
              if (isExistEle(eleList, i)) {
                logg.info(mName + " " + i + "." + clMap.getKey() + "!");
//                Actions action = new Actions(driver);
//                //ガイドをマウスオーバー
//                WebElement mouseOver = eleList.get(i);
//                action.moveToElement(mouseOver);
////                action.click();
//                //performで実際に動作する
//                action.perform();

                clickSleepSelector(driver, eleList, i, 9000);
                closeOtherWindow(driver);
              }
            }
          }
          break;
        case "savingページ":
          if (isExistEle(driver, clMap.getValue().get("sele"))) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(clMap.getValue().get("sele")));
            int stampNum = eleList.size();
            for (int i = 0; i < stampNum; i++) {
              if (isExistEle(eleList, i)) {
                logg.info(mName + " " + i + "." + clMap.getKey() + "!");
                clickSleepSelector(driver, eleList, i, 9000);
                closeOtherWindow(driver);
              }
            }
          }
          // shoppingページ
          break;
				case "shoppingページ":
					if (isExistEle(driver, clMap.getValue().get("sele"))) {
						List<WebElement> eleList = driver.findElements(By.cssSelector(clMap.getValue().get("sele")));
						int stampNum = eleList.size();
						for (int i = 0; i < stampNum; i++) {
							if (isExistEle(eleList, i)) {
								logg.info(mName + " " + i + "." + clMap.getKey() + "!");
//								Actions action = new Actions(driver);
//  							//ガイドをマウスオーバー
//  							WebElement mouseOver = eleList.get(i);
//  							action.moveToElement(mouseOver);
////  							action.click();
//  							//performで実際に動作する
//  							action.perform();
//							
								clickSleepSelector(driver, eleList, i, 7000);
								closeOtherWindow(driver);
//								Utille.refresh(driver, logg);
							}
						}
					}
					// shoppingページ
					break;
				default:
			}
		}
	}
}
