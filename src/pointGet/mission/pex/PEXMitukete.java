package pointGet.mission.pex;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class PEXMitukete extends Mission {
	final String url = "http://pex.jp/seal";
	private boolean endFlag = false;

	/**
	 * @param logg
	 */
	public PEXMitukete(Logger logg) {
		super(logg);
		this.mName = "■見つけて";
	}

	@Override
	public void roopMission(WebDriver driver) {

	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "section#mitsukete_seal>a.game_btn img";
		if (this.isExistEle(driver, selector)) {
			driver.findElement(By.cssSelector(selector)).click(); // move
			Utille.sleep(6000);

			selector = "h3.h-lv3.fw_b>strong.h-lv2.fw_b.red";
			if (this.isExistEle(driver, selector)) {
				String text = driver.findElement(By.cssSelector(selector)).getText();
				// 解析
				HashMap<String, String> hint = this.getHint(text);
				if (hint.size() > 0) {
					driver.get("http://pex.jp/point_actions/list/free_register");

					// その他の時に、どうしようTODO
					selector = "section.vertical-menu.box-deco_orange a:contains('"+hint.get("condition")+"')";
					if (this.isExistEle(driver, selector)) {
						driver.findElement(By.cssSelector(selector)).click(); // move
						
					}
					
					
//					selector = "section.vertical-menu.box-deco_orange a";
//					if (this.isExistEle(driver, selector)) {
//						int size =driver.findElements(By.cssSelector(selector)).size();
//						for (int i = 0;i<size;i++) {
//							driver.findElement(By.cssSelector(selector)).getText();
//						}
//					}
				}
			}
		}
		//		img.bounce
		//		
		//			PEXMekutte PEXMekutte = new PEXMekutte(logg);
		//			PEXMekutte.mekutteSeal(driver);

		// 終了サイン 以下が存在しないこと
		// div.mitsukete_game_bg.geme_cnt.mitsukete_seal.display-none
		
	}

	/**
	 * 
	 * @param nativeWord
	 * @return
	 */
	private HashMap<String, String> getHint(String nativeWord) {
		HashMap<String, String> hint = new HashMap<String, String>();
		logg.info("hint:" + nativeWord);
		String regex = "(【獲得条件 : )(.*)( 】)";
		String a = Utille.getPatternWord(regex, nativeWord, 2);
		if (a.length() > 0) {
			hint.put("condition", a);
		}
		regex = "(\\d+)(P.*)";
		String b = Utille.getPatternWord(regex, nativeWord, 1);
		if (b.length() > 0) {
			hint.put("point", b);
		}
		return hint;
	}
}
