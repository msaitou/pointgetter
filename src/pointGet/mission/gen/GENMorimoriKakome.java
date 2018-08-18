package pointGet.mission.gen;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 */
public class GENMorimoriKakome extends Mission {
	final String url = "http://www.gendama.jp/";

	/**
	 * @param log
	 */
	public GENMorimoriKakome(Logger log, Map<String, String> cProps) {
		super(log, cProps);
		this.mName = "■もりもり囲め";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		Utille.url(driver, url, logg);
		Utille.sleep(2000);
		selector = "img[src='//img.gendama.jp/img/renew/top/ban_kakome.png']";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 3000);
			String popSelectorNone = "div#game_filter[style*='top: 3000px']";
			String popSelector = "div#game_filter";
			if (isExistEle(driver, popSelector)) {
				if (!isExistEle(driver, popSelectorNone)) {
					checkOverlay(driver, "a#closs_btm");
				}
			}
			selector = "a.ok_btn";
			// スタート
			if (isExistEle(driver, selector)) {
				clickSleepSelector(driver, selector, 3000);
				if (isExistEle(driver, popSelector)) {
					if (!isExistEle(driver, popSelectorNone)) {
						checkOverlay(driver, "a#closs_btm");
					}
				}
				// プレイ開始（or 変更する）
				if (isExistEle(driver, selector)) {
					clickSleepSelector(driver, selector, 3000);
				}
				// ここからおいていく
			}
		}
		else {
			logg.warn(mName + "]獲得済み");
		}
	}
	private static int topMaxLimit = 9;
	private static int leftMaxLimit = 9;
	private static int topMinLimit = 1;
	private static int leftMinLimit = 1;
	private void getNextMasuMain() {
		// マスの位置変数
		HashMap<String, Integer> masuMap = new HashMap<String, Integer>();
		masuMap.put("tPosi", 5);
		masuMap.put("lPosi", 5);
//		HashMap<String, Integer> nextMasu = getNextMasu(masuMap);
	}

	private void getNextMasu(HashMap<String, Integer> masuMap) {

		int cTop = masuMap.get("tPosi");
		int cLeft = masuMap.get("lPosi");
		// its over
		if (topMaxLimit == cTop || topMinLimit == cTop
			|| leftMaxLimit == cLeft || leftMinLimit == cLeft) {
			return ;
		}
		else {
			// 左上：1、右上：2、右：3、右下：4.左下:5、左：6
			int rinsetuTop1 = 0;
			int rinsetuTop2 = 0;
			int rinsetuTop3 = 0;
			int rinsetuTop4 = 0;
			int rinsetuTop5 = 0;
			int rinsetuTop6 = 0;
			int rinsetuLeft1 = 0;
			int rinsetuLeft2 = 0;
			int rinsetuLeft3 = 0;
			int rinsetuLeft4 = 0;
			int rinsetuLeft5 = 0;
			int rinsetuLeft6 = 0;
			rinsetuTop1 = cTop + 1;
			rinsetuTop2 = cTop + 1;
			rinsetuTop3 = cTop;
			rinsetuTop4 = cTop - 1;
			rinsetuTop5 = cTop - 1;
			rinsetuTop6 = cTop;
				// 偶数
			if (cTop%2 == 0) {
		}

		}
	}
}


//<form method="post" action="/ssp/catch_the_pig/setblock">
//<input id="set_block" type="hidden" name="setting" value=""/>
//<ul id="cell_box">
//<li class="cell_1_1">
//<a href="#"></a>
//</li>
//<li class="cell_1_2">
//<a href="#"></a>
//</li>
//<li class="cell_1_3">
//<a href="#"></a>
//</li>
//<li class="cell_1_4">
//<a href="#"></a>
//</li>
//<li class="cell_1_5">
//<a href="#"></a>
//</li>
//<li class="cell_1_6">
//<a href="#"></a>
//</li>
//<li class="cell_1_7">
//<a href="#"></a>
//</li>
//<li class="cell_1_8">
//<a href="#"></a>
//</li>
//<li class="cell_1_9">
//<a href="#"></a>
//</li>
//<li class="cell_2_1">
//<a href="#"></a>
//</li>
//<li class="cell_2_2">
//<a href="#"></a>
//</li>
//<li class="cell_2_3">
//<a href="#"></a>
//</li>
//<li class="cell_2_4">
//<a href="#"></a>
//</li>
//<li class="cell_2_5">
//<a href="#"></a>
//</li>
//<li class="cell_2_6">
//<a href="#"></a>
//</li>
//<li class="cell_2_7">
//<a href="#"></a>
//</li>
//
//<li class="cell_2_8">
//<a class="active" href="#"></a>
//</li>
//<li class="cell_2_9">
//<a href="#"></a>
//</li>
//<li class="cell_3_1">
//<a href="#"></a>
//</li>
//<li class="cell_3_2">
//<a href="#"></a>
//</li>
//<li class="cell_3_3">
//<a href="#"></a>
//</li>
//<li class="cell_3_4">
//<a href="#"></a>
//</li>
//<li class="cell_3_5">
//<a href="#"></a>
//</li>
//<li class="cell_3_6">
//<a href="#"></a>
//</li>
//<li class="cell_3_7">
//<a href="#"></a>
//</li>
//<li class="cell_3_8">
//<a href="#"></a>
//</li>
//<li class="cell_3_9">
//<a href="#"></a>
//</li>
//
//<li class="cell_4_1">
//<a class="active" href="#"></a>
//</li>
//<li class="cell_4_2">
//<a href="#"></a>
//</li>
//<li class="cell_4_3">
//<a href="#"></a>
//</li>
//<li class="cell_4_4">
//<a href="#"></a>
//</li>
//<li class="cell_4_5">
//<a href="#"></a>
//</li>
//<li class="cell_4_6">
//<a href="#"></a>
//</li>
//<li class="cell_4_7">
//<a href="#"></a>
//</li>
//<li class="cell_4_8">
//<a href="#"></a>
//</li>
//<li class="cell_4_9">
//<a href="#"></a>
//</li>
//<li class="cell_5_1">
//<a href="#"></a>
//</li>
//<li class="cell_5_2">
//<a href="#"></a>
//</li>
//
//<li class="cell_5_3">
//<a class="active" href="#"></a>
//</li>
//<li class="cell_5_4">
//<a href="#"></a>
//</li>
//<li class="cell_5_5">
//<img src="http://ssp-cdn.neogame.jp/img/gendama/ctpg/character01.gif" id="character"/>
//</li>
//<li class="cell_5_6">
//<a href="#"></a>
//</li>
//<li class="cell_5_7">
//<a href="#"></a>
//</li>
//<li class="cell_5_8">
//<a href="#"></a>
//</li>
//<li class="cell_5_9">
//<a href="#"></a>
//</li>
//<li class="cell_6_1">
//<a href="#"></a>
//</li>
//<li class="cell_6_2">
//<a href="#"></a>
//</li>
//<li class="cell_6_3">
//<a href="#"></a>
//</li>
//<li class="cell_6_4">
//<a href="#"></a>
//</li>
//<li class="cell_6_5">
//<a href="#"></a>
//</li>
//<li class="cell_6_6">
//<a href="#"></a>
//</li>
//<li class="cell_6_7">
//<a href="#"></a>
//</li>
//<li class="cell_6_8">
//<a href="#"></a>
//</li>
//<li class="cell_6_9">
//<a href="#"></a>
//</li>
//<li class="cell_7_1">
//<a href="#"></a>
//</li>
//<li class="cell_7_2">
//<a href="#"></a>
//</li>
//<li class="cell_7_3">
//<a href="#"></a>
//</li>
//<li class="cell_7_4">
//<a href="#"></a>
//</li>
//<li class="cell_7_5">
//<a href="#"></a>
//</li>
//<li class="cell_7_6">
//<a href="#"></a>
//</li>
//<li class="cell_7_7">
//<a href="#"></a>
//</li>
//<li class="cell_7_8">
//<a href="#"></a>
//</li>
//<li class="cell_7_9">
//<a href="#"></a>
//</li>
//<li class="cell_8_1">
//<a href="#"></a>
//</li>
//<li class="cell_8_2">
//<a href="#"></a>
//</li>
//<li class="cell_8_3">
//<a href="#"></a>
//</li>
//<li class="cell_8_4">
//<a href="#"></a>
//</li>
//<li class="cell_8_5">
//<a href="#"></a>
//</li>
//<li class="cell_8_6">
//<a href="#"></a>
//</li>
//<li class="cell_8_7">
//<a href="#"></a>
//</li>
//
//<li class="cell_8_8">
//<a class="active" href="#"></a>
//</li>
//<li class="cell_8_9">
//<a href="#"></a>
//</li>
//<li class="cell_9_1">
//<a href="#"></a>
//</li>
//<li class="cell_9_2">
//<a href="#"></a>
//</li>
//<li class="cell_9_3">
//<a href="#"></a>
//</li>
//<li class="cell_9_4">
//<a href="#"></a>
//</li>
//<li class="cell_9_5">
//<a href="#"></a>
//</li>
//<li class="cell_9_6">
//<a href="#"></a>
//</li>
//<li class="cell_9_7">
//<a href="#"></a>
//</li>
//<li class="cell_9_8">
//<a href="#"></a>
//</li>
//<li class="cell_9_9">
//<a href="#"></a>
//</li>
//</ul>
//</form>
