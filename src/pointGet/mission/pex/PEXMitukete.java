package pointGet.mission.pex;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class PEXMitukete extends PEXBase {
	final String url = "http://pex.jp/seal";
	private boolean endFlag = false;
	// PEX登録ページリンクのマップ
	public final static Map<String, String> fieldMap = new HashMap<String, String>() {
		{
			put("無料会員登録", "/point_actions/list/free_register");
		}
		{
			put("キャンペーン応募", "/point_actions/list/campaign");
		}
		{
			put("カード発行", "/point_actions/list/card");
		}
		{
			put("口座開設", "/point_actions/list/open_account");
		}
		{
			put("資料請求", "/point_actions/list/document_request");
		}
		{
			put("査定・見積もり", "/point_actions/list/assessment");
		}
		{
			put("相談", "/point_actions/list/consultation");
		}
		{
			put("ゲーム起動", "/point_actions/list/start_up_game");
		}
		{
			put("会員登録後のアクション", "/point_actions/list/register_and_action");
		}
		{
			put("中古買取", "/point_actions/list/used_item_purchase");
		}
		{
			put("その他", "/point_actions/list/free_point_action_other");
		}
		{
			put("有料会員登録", "/point_actions/list/charge_register");
		}
		{
			put("旅行", "/point_actions/list/travel");
		}
		{
			put("エステ体験", "/point_actions/list/esthetic");
		}
		{
			put("来店", "/point_actions/list/visit_store");
		}
		{
			put("成約・借り入れ", "/point_actions/list/agree_contract");
		}
		{
			put("ウォーターサーバー設置完了", "/point_actions/list/water_server");
		}
		{
			put("その他", "/point_actions/list/charge_point_action_other");
		}
		{
			put("全ての案件", "/point_actions/list/point_action");
		}
	};

	/**
	 * @param logg
	 */
	public PEXMitukete(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "見つけて");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "section#mitsukete_seal>a.game_btn img";
		if (this.isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 6000);// move

			selector = "h3.h-lv3.fw_b>strong.h-lv2.fw_b.red";
			if (this.isExistEle(driver, selector)) {
				String text = driver.findElement(By.cssSelector(selector)).getText();
				// 解析
				HashMap<String, String> hint = this.getHint(text);
				if (hint.size() > 0) {
					driver.get("http://pex.jp/point_actions/list/free_register");

					// その他の時に、どうしようTODO
					selector = "section.vertical-menu.box-deco_orange a:contains('" + hint.get("condition") + "')";
					if (this.isExistEle(driver, selector)) {
						clickSelector(driver, selector);// move
					}

					//					selector = "section.vertical-menu.box-deco_orange a";
					//					if (this.isExistEle(driver, selector)) {
					//						int size =getSelectorSize(driver, selector);;
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
