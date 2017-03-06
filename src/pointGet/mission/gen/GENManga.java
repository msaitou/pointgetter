package pointGet.mission.gen;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.Utille;

public class GENManga extends GENBase {
	final String url = "http://www.gendama.jp/";

	/**
	 * @param logg
	 */
	public GENManga(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "漫画");
	}

	@Override
	public void privateMission(WebDriver driver) {
		// div#dropmenu01
		driver.get(url);
		selector = "div#dropmenu01";
		if (isExistEle(driver, selector)) {
			int size = getSelectorSize(driver, selector);
			for (int i = 0; i < size; i++) {
				WebElement e = driver.findElements(By.cssSelector(selector)).get(i);
				String selector2 = "a[onclick*='CMくじ']";
				if (isExistEle(e, selector2)) {
					if (!isExistEle(e, selector2)) {
						break;
					}
					String cmPageUrl = e.findElement(By.cssSelector(selector2)).getAttribute("href");
					driver.get(cmPageUrl); // CMpage
					Utille.sleep(3000);
					String uranaiSelector = "a>img[alt='up_current']";
					if (!isExistEle(driver, uranaiSelector)) {
						break;
					}
					clickSleepSelector(driver, uranaiSelector, 3000); // 遷移 全体へ
					changeCloseWindow(driver);
					// // アラートをけして
					// val alert = driver.switchTo().alert();
					// alert.accept();
					Utille.sleep(4000);
					// changeCloseWindow(driver);

					selector = "a.ui-btn.ui-btn-a"; // 回答する
					while (true) {
						if (isExistEle(driver, selector)) {
							List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
							if (isExistEle(eleList, 0)) {
								clickSleepSelector(eleList, 0, 3000); // 遷移
								String overLay = "div#interstitial[style*='display: block']>div>div#inter-close";
								String seleNext = "form>input[type='image']";
								for (int g = 0; g < 9; g++) {
									if (isExistEle(driver, seleNext)) {
										if (isExistEle(driver, overLay)) {
											checkOverlay(driver, overLay);
										}
										clickSleepSelector(driver, seleNext, 3000); // 遷移
									}
								}
								Utille.sleep(3000);
								String choiceSele = "input[type='radio']";
								String seleNext2 = "div>input.enquete_nextbt";
								String seleNext3 = "div>input.enquete_nextbt_2";
								String seleSele = "form.menu>select";
								// 8問
								for (int k = 1; k <= 8; k++) {
									if (isExistEle(driver, overLay)) {
										checkOverlay(driver, overLay);
									}
									int choiceNum = 0;
									if (isExistEle(driver, choiceSele)) {
										int choiceies = getSelectorSize(driver, choiceSele);
										switch (k) {
										case 1:
											// 1問目は1：男
											break;
										case 2:
										case 3:
											// 2問目は3：30代
											// 3問目は3：会社員
											if (choiceies > 2) {// 一応選択可能な範囲かをチェック
												choiceNum = 2;
											}
											break;
										default:
											choiceNum = Utille.getIntRand(choiceies);
										}
										List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
										if (isExistEle(eleList2, choiceNum)) {
											// 選択
											clickSleepSelector(eleList2.get(choiceNum), 2500);
											if (isExistEle(driver, seleNext2)) {
												// 次へ
												clickSleepSelector(driver, seleNext2, 4000);
											}
										}
									}
									else if (isExistEle(driver, seleSele)) {
										int size3 = getSelectorSize(driver, seleSele + ">option");
										choiceNum = Utille.getIntRand(size3);
										String value = driver.findElements(By.cssSelector(seleSele + ">option")).get(choiceNum).getAttribute("value");
										Select selectList = new Select(driver.findElement(By.cssSelector(seleSele)));
//										selectList.deselectByIndex(choiceNum);
										selectList.selectByValue(value);
										if (isExistEle(driver, seleNext3)) {
											// 次へ
											clickSleepSelector(driver, seleNext3, 4000);
										}
									}
									else {
										break;
									}
								}
								if (isExistEle(driver, overLay)) {
									checkOverlay(driver, overLay);
								}
								if (isExistEle(driver, seleNext)) {
									clickSleepSelector(driver, seleNext, 3000); // 遷移
								}
//								<div id="interstitial" style="background-color: rgba(0, 0, 0, 0.6); position: absolute; z-index: 1000; width: 728px; height: 450px; display: block;">
//								<div id="inter-contents" style="width: 300px;margin: 0 auto;position:relative;margin-top: 54px;">
//								<div id="inter-close" style="position:absolute;z-index:1000;top:-19px;left: 0;background-color: #fff;padding: -1px -1px;font-size: 18px;cursor:pointer">×</div>
//									<script type="text/javascript" src="http://js.gsspcln.jp/t/057/940/a1057940.js"></script><div><script type="text/javascript">(function(){var d='http://aladdin.genieesspv.jp/yie/ld/jsk?zoneid=1057940&amp;cb=41255468577&amp;charset=UTF-8&amp;loc=http%3A%2F%2Fmanga-enquete.com%2Fpublic_html%2Fdreevee%2Ffinish.php&amp;referer=http%3A%2F%2Fmanga-enquete.com%2Fpublic_html%2Fdreevee%2Fenquete.php';if(typeof(window.__geniee_usd)!=='undefined'&&typeof(window.__geniee_usd.kvc)!=='undefined'&&window.__geniee_usd.kvc!='')d+=('&amp;etp='+window.__geniee_usd.kvc);if(typeof(window.__geniee_rtus)!=='undefined'&&window.__geniee_rtus!=null&&window.__geniee_rtus!='')d+=('&amp;rtus='+encodeURIComponent(window.__geniee_rtus));if(typeof(window.__geniee_rta)!=='undefined'&&window.__geniee_rta!=null&&window.__geniee_rta!='')d+=('&amp;custom_rta='+encodeURIComponent(window.__geniee_rta));d+=('&amp;topframe='+((window.parent==window.self)?1:0));document.open();document.write("<script type='text/javascript' src='"+d+"'><\/"+"script>");document.close();})();</script><script type="text/javascript" src="http://aladdin.genieesspv.jp/yie/ld/jsk?zoneid=1057940&amp;cb=41255468577&amp;charset=UTF-8&amp;loc=http%3A%2F%2Fmanga-enquete.com%2Fpublic_html%2Fdreevee%2Ffinish.php&amp;referer=http%3A%2F%2Fmanga-enquete.com%2Fpublic_html%2Fdreevee%2Fenquete.php&amp;topframe=1"></script><script type="text/javascript">window.__genieeCommon = {getOverlayDocument : function() {return (document.getElementById('geniee_overlay') ? document : null);},getOverlayWindow : function() {return (document.getElementById('geniee_overlay') ? window : null);}};var SSPPassback2 = function (){var _bannerlist;var _passbackcnt = 0;var _prepend_code = 0;var _create_iframe = 0;var _append_code = 0;this.setBannerList = function(banner_list) {_bannerlist = banner_list;if(_bannerlist['prepend_code'] != undefined) {_prepend_code = _bannerlist['prepend_code'];delete _bannerlist['prepend_code'];}if(_bannerlist['create_iframe'] != undefined) {_create_iframe = _bannerlist['create_iframe'];delete _bannerlist['create_iframe'];}if(_bannerlist['append_code'] != undefined) {_append_code = _bannerlist['append_code'];delete _bannerlist['append_code'];}};this.getBanner = function (){return _bannerlist[_passbackcnt] ? _bannerlist[_passbackcnt] : '';};this.getPassbackCnt = function (){return _passbackcnt;};this.incrementPassbackCnt = function (){_passbackcnt += 1;};this.getPrependCode = function(){return _prepend_code;};this.getCreateIframe = function(){return _create_iframe;};this.getAppendCode = function(){return _append_code;};};SSPPassback2.prototype = {init:function(banner_list) {this.setBannerList(banner_list)},passBack : function (){var do_banner=this.getBanner();if(do_banner!=''){this.incrementPassbackCnt();if(this.getCreateIframe() == 0){document.write(do_banner);}else{var iframeContent = "<html><head><\/" + "head><body marginwidth='0' marginheight='0' style='margin:0px; padding:0px'>";iframeContent += "<script type='text/javascript'>";iframeContent += "window.__genieeCommon = {";iframeContent += "getOverlayDocument : function() {";iframeContent += "return window.parent.__genieeCommon.getOverlayDocument();";iframeContent += "},";iframeContent += "getOverlayWindow : function() {";iframeContent += "return window.parent.__genieeCommon.getOverlayWindow();";iframeContent += "}";iframeContent += "};";iframeContent += "var SSPPassback2_iFrame = function() {";iframeContent += "this.callParentPassBack = function() {";iframeContent += "window.parent.gpb_1057940.passBack();";iframeContent += "};";iframeContent += "};";iframeContent += "SSPPassback2_iFrame.prototype = {";iframeContent += "passBack : function() {";iframeContent += "this.callParentPassBack();";iframeContent += "}";iframeContent += "};";iframeContent += "var gpb_1057940 = new SSPPassback2_iFrame();";iframeContent += "<\/" + "script>";iframeContent += do_banner;iframeContent += "<\/" + "body><\/" + "html>";var do_iframeDocument=document.getElementById('geniee_delivery_1057940_c0da22f5').contentWindow.document;do_iframeDocument.open();do_iframeDocument.write(iframeContent);do_iframeDocument.close();}}},writePrependCode:function(){var do_prependcode = this.getPrependCode();if(do_prependcode != 0) {document.write(do_prependcode );}},writeAppendCode:function(){var do_appendcode = this.getAppendCode();if(do_appendcode != 0) {document.write(do_appendcode);}},run:function(){if(this.getCreateIframe() == 0){gpb_1057940.writePrependCode();gpb_1057940.passBack();gpb_1057940.writeAppendCode();}else{gpb_1057940.writePrependCode();gpb_1057940.writeAppendCode();document.write("<script type='text/javascript'>gpb_1057940.passBack();<\/" + "script>");}}};var gpb_1057940 = new SSPPassback2();var json_banner_data = {"prepend_code":"","create_iframe":0,"0":"<DIV STYLE=\"position: absolute; left: 0px; top: 0px; visibility: hidden;\"><IMG SRC=\"https:\/\/pagead2.googlesyndication.com\/pagead\/gen_204?id=xbid&dbm_b=AKAmf-CLt6E-x9enOY1KGHhqnmdwmICHhlZ5E_cycnXtYuucbroGY1xNQCRsHVTQxrTDaTQshPQp4YEH9YLGCV9p7xdsv_J50CorOBOfK8xDCHMUi8LyCuc\" BORDER=0 WIDTH=1 HEIGHT=1 ALT=\"\" STYLE=\"display:none\"><\/DIV><div><div style=\"position:relative; display:inline-block;\"><script language='javascript' src=\"https:\/\/googleads.g.doubleclick.net\/dbm\/ad?dbm_c=AKAmf-B1ZwZ0kCC8tfQ8fC8MV5AHxrRKh3FCpEinxaWrI4UnV8lmgyb7ivsbhUawBWmrF0hgfPWg&dbm_d=AKAmf-DZb7bswfAeOEKT3ZVsreAPok1HTg3ULAnWgW03XeNsSUD9hCmWevYwugI-finj3GvEZufiqIOHa7N1UhvxwSgNThLw7nfEZW9BaVhuokzowILxb9wOP_R6qSORH6xcGc8aHefh6E-NTBaxVQa-fATrF0XUYKvH4MZ3a64wHuqZXYsebnrWrGBkdqr2GPq9l1ivSU_2W5uAoOaAVE8QwDegddp7tcRXvs8L2cVlhoi6VPDytzQC0EopJdOxYbf_cIkXpDyeiykUhLS13v0xdbUPTV-h3grI9ogvT62fjK9h4rqpSylwoRnrKLxg_X1IG9kSLESCKii1v4rJURqwEl3vvsmdq8qzdEDh9oqaDz_B0moDlkd7eoCTn7OUUOFaJXpCflbAj29f3D09AJy8vLEKOUglp_Tgd8xSUGFzO1k1kyRUXQPjAfoZcl_5MGrCNtH0dMRzg5fmt6Ko486ocxJXbG4d8lJ5dV6K75Q1fOrf6MC722OzYDOTPXVUt61j8M62gw5UB5PRvK_5PkJMjS2euK8si_XU03tW3RFpP91SolP_-ioeqivf_iXMXwbqbHxrrslGXxzcA1gPsnzU9QiRxg6M6_BejMYS5ftiZhqtD0PbenLMXQd8t4Z1PbKc4A8vw7DGaQHtjo11F9J1edKcrXHtTUddPx3fCGUsxYWFdJhplFTIdUiEqHuvWhlbBxAQxvWuR4LgT5vSk_FeAcXAd9swdQmxMm9iQf3AXMr3b0Jw1JHnRRdLZ1Elh3U4EIOd4oE8WvHPtFVYl22Hh89p6EKDYtVCVFA83id8h_4Z7GCA8KBqMbXoKIyCEvyj0mIQcOkQ1XjYMqN72wXw5vuq7xYC6tsvir90fdfvgfcAzOLuBDUklG2fmwedLFDfdlEp7o_-NMoSoNWCBQ_Gc9jK-D5VWsz6bYcBsPYwgHH7W5Q38OXrRkNl7xtk_uWfQH1ipzjxf0RX87IYRc6qLOL6RfbXpbtwbqdW5-n9dR_SFH702aJycODYbaxKn6VWp8QHvjoNFe6G6-9TyCWaEohEGgf1iEzGTxMdYQesY3_CFb3F3CwskFolKrJHgR9T7HVzgDLoyCsN8gsGo1SI11tJPBo-iOrdp-BWJWGWzYeHil9kHZjaMr72iN_CRLjOEWJJxCXnMwRpQIHEVNi56nnxhGW0CrSJyLsSYwY0frRofTVbqZ_x58_oKtz7qL9q7l6ZfqlGA9DqbuTAtDznNoo_aYMs2irRHecB1tmErxdeW4i5XeATCWGIjJwbB8QgRwL5e1bPoKyhZiLoUsnxVUG4rHTExqP_P4CCJGwtbS4CISt7Xt26gj44gphAY1SC8oAktczcWIRRDNhRV7OKIR4c_doug0BKj-82TXiR_vEQTp2tSmeHO4PCfywMBsswhJ4WZtexRClTWTM6sSNeYMG2MGwuSdFpjd_N6Zv_guuaO2CUNwXbORPTID9LZY5Cej1CBIKuG6HmJU1rxdYKVY0SVzzLqZTar2AZND_9uVnOqebyznwZB5PFyC8NERlgkLKMrqkE5Um8Kb15nQ1m2OiSPbS3SLRqg5xICO4JbU6braZuzuwTKPQd1BJOqKLFkHPWLEZImMl1YZvfsKuW8-WQfCpyegldP4x-FJEIPbJvaSZYc_3Y_hXAiIhJMOsOANz4Xva2&pr=43:kk%2Fy24c7&xfc=http%3A%2F%2Fcs.gssprt.jp%2Fyie%2Fld%2Fck%3Fzid%3D1057940%26asid%3D1469839%26idx%3D0%26gid%3D653aa81b6a7f54d435dfd419d05354a0%26l%3DidJilqqEFz0Fp98TwG1k1E60LNi2YG6fwvDvusIZWqLE8g6HClm3ivqBvZWPXNI6UIUv3ERCJnT_3qsV3AsxQ76xKPgRmiiOStda54XSMhZgb6B7eFuIAUN4X_l__WcV_XIzvKcd7NuhC3fLthdrMyCotYE6ho4uvw3NzogjP5Kj_Kf1USs7VmWixWTQ6nGwT4M0iKPMji1_33ZIOdsi9rwX4-mquzkdHWmfslrTb8lCwnLcwlLt8jKmTrOHNy8r2QCCr6GXSmRhEXaAWDOqNFe8qux2yZuwcip_A1JMavkfW59eTS32Wnj841ZXCgT6QMNdFqog7nIy8vXN0TWj2QLHZWUkIW1PFSINqnHGRaZLaWfSzAoqQo6fXewTMTBP%26dest%3D\"><\/script><IFRAME src=\"https:\/\/googleads.g.doubleclick.net\/xbbe\/pixel?d=CMqzMBDlqUwYtsicEzAB&v=APEucNUmHs7B8PlOBtkO2YPDRjn0ZOgwkp5N5BdNxfNImYDFMe70QM69wiqxwIeCCJP4GE1mwZiqR0IOvWDOrFpIeC8y12er6A\" style=\"display:none\"><\/IFRAME><\/div><\/div><div style=\"position: absolute; left: 0px; top: 0px; visibility: hidden;\"><img src=\"http:\/\/aladdin.genieesspv.jp\/yie\/ld\/gl2?zid=1057940&asid=1469839&idx=0&gid=653aa81b6a7f54d435dfd419d05354a0&cb=f3deaa573f&l=2vdhQGgjyyk1ALeBDerSjZChb5UMfSe97uQjlauXvDi2TkdPS9fh3IGVhjcSi217qhfllUZqXl0GzdMRLYIckglbxvbKIhofZHz1Fc4wFD8Aq5Ki-899ED8l_n2u9Xx9I7_fkfQTYmWrnxLPJavp4Xe4y69dDjlOjsD4j6gbGbtre5wWEtjnL0YoruqQ6_UjVvC371NZsXtczkjHz8C1TKwDSMBGPH28pjdI81d7hxZYyfOD6KBpZwOLqO25ARU1pkBV96OXsxRLhs0PqI-CZSiPm21-JpnP_VKdWRodMoObrEReD5JieZieCkL4jVAeqaZEzMarxhuI4O2CRT5P-YohnGElUNONvGDEpmURIj672ezOaVdyBuFNZ3ueQFsH9eu5pX6X_1Uc-DdoUdz3Iv0Qe3sXK_1MRsvCywbK4XUmED8hgZevF4yvVGZt5Qa5TabLUHvUWVj70wpj-d2Wr3iww2UGKsAJ9SYU7nUnDFLoHk4A3UkauKErLsYy76KjYgb5xjCTF8gys82Ptv634pRLFm6Wdi8tpir4LWkgPoJk8LzNVeTDIA8e1YZyphaJnfGxkLqzUamvOtL6YU11sl_wJikdubLfk5oRpv9MSO-DiWWkLR3FM_TO80v9TSXY3Wb2R9dPwHnc6-OrEZGdBrkmddzHk8cI5ofnInYtnrsH7u8DnJJjYRza1IespXHXYbY0wKjoe-RlaFp1rbG82_DxMwn4YeRXzyvC_F0BfuFyfFQPATWMGO-jImSiBbXiUpDhZ9Vcm_6exd22hWX4Lt9KF4KJ0JO_1Iabe4R2M6ZlvQWT-bA4ApzOzAkTRQVJOvIJFp8_r7RNM4MVnOuJDc-dnDGML_PgDUmUwSe2srRhHmupPnj4K6XpuJOldpn6jpERVa-dnJk\" width=\"0\" height=\"0\" alt=\"\" style=\"width: 0px; height: 0px;\"><\/div>","append_code":""};gpb_1057940.init(json_banner_data);gpb_1057940.run();</script><div style="position: absolute; left: 0px; top: 0px; visibility: hidden;"><img src="https://pagead2.googlesyndication.com/pagead/gen_204?id=xbid&amp;dbm_b=AKAmf-CLt6E-x9enOY1KGHhqnmdwmICHhlZ5E_cycnXtYuucbroGY1xNQCRsHVTQxrTDaTQshPQp4YEH9YLGCV9p7xdsv_J50CorOBOfK8xDCHMUi8LyCuc" alt="" style="display:none" width="1" border="0" height="1"></div><div><div style="position:relative; display:inline-block;"><script language="javascript" src="https://googleads.g.doubleclick.net/dbm/ad?dbm_c=AKAmf-B1ZwZ0kCC8tfQ8fC8MV5AHxrRKh3FCpEinxaWrI4UnV8lmgyb7ivsbhUawBWmrF0hgfPWg&amp;dbm_d=AKAmf-DZb7bswfAeOEKT3ZVsreAPok1HTg3ULAnWgW03XeNsSUD9hCmWevYwugI-finj3GvEZufiqIOHa7N1UhvxwSgNThLw7nfEZW9BaVhuokzowILxb9wOP_R6qSORH6xcGc8aHefh6E-NTBaxVQa-fATrF0XUYKvH4MZ3a64wHuqZXYsebnrWrGBkdqr2GPq9l1ivSU_2W5uAoOaAVE8QwDegddp7tcRXvs8L2cVlhoi6VPDytzQC0EopJdOxYbf_cIkXpDyeiykUhLS13v0xdbUPTV-h3grI9ogvT62fjK9h4rqpSylwoRnrKLxg_X1IG9kSLESCKii1v4rJURqwEl3vvsmdq8qzdEDh9oqaDz_B0moDlkd7eoCTn7OUUOFaJXpCflbAj29f3D09AJy8vLEKOUglp_Tgd8xSUGFzO1k1kyRUXQPjAfoZcl_5MGrCNtH0dMRzg5fmt6Ko486ocxJXbG4d8lJ5dV6K75Q1fOrf6MC722OzYDOTPXVUt61j8M62gw5UB5PRvK_5PkJMjS2euK8si_XU03tW3RFpP91SolP_-ioeqivf_iXMXwbqbHxrrslGXxzcA1gPsnzU9QiRxg6M6_BejMYS5ftiZhqtD0PbenLMXQd8t4Z1PbKc4A8vw7DGaQHtjo11F9J1edKcrXHtTUddPx3fCGUsxYWFdJhplFTIdUiEqHuvWhlbBxAQxvWuR4LgT5vSk_FeAcXAd9swdQmxMm9iQf3AXMr3b0Jw1JHnRRdLZ1Elh3U4EIOd4oE8WvHPtFVYl22Hh89p6EKDYtVCVFA83id8h_4Z7GCA8KBqMbXoKIyCEvyj0mIQcOkQ1XjYMqN72wXw5vuq7xYC6tsvir90fdfvgfcAzOLuBDUklG2fmwedLFDfdlEp7o_-NMoSoNWCBQ_Gc9jK-D5VWsz6bYcBsPYwgHH7W5Q38OXrRkNl7xtk_uWfQH1ipzjxf0RX87IYRc6qLOL6RfbXpbtwbqdW5-n9dR_SFH702aJycODYbaxKn6VWp8QHvjoNFe6G6-9TyCWaEohEGgf1iEzGTxMdYQesY3_CFb3F3CwskFolKrJHgR9T7HVzgDLoyCsN8gsGo1SI11tJPBo-iOrdp-BWJWGWzYeHil9kHZjaMr72iN_CRLjOEWJJxCXnMwRpQIHEVNi56nnxhGW0CrSJyLsSYwY0frRofTVbqZ_x58_oKtz7qL9q7l6ZfqlGA9DqbuTAtDznNoo_aYMs2irRHecB1tmErxdeW4i5XeATCWGIjJwbB8QgRwL5e1bPoKyhZiLoUsnxVUG4rHTExqP_P4CCJGwtbS4CISt7Xt26gj44gphAY1SC8oAktczcWIRRDNhRV7OKIR4c_doug0BKj-82TXiR_vEQTp2tSmeHO4PCfywMBsswhJ4WZtexRClTWTM6sSNeYMG2MGwuSdFpjd_N6Zv_guuaO2CUNwXbORPTID9LZY5Cej1CBIKuG6HmJU1rxdYKVY0SVzzLqZTar2AZND_9uVnOqebyznwZB5PFyC8NERlgkLKMrqkE5Um8Kb15nQ1m2OiSPbS3SLRqg5xICO4JbU6braZuzuwTKPQd1BJOqKLFkHPWLEZImMl1YZvfsKuW8-WQfCpyegldP4x-FJEIPbJvaSZYc_3Y_hXAiIhJMOsOANz4Xva2&amp;pr=43:kk%2Fy24c7&amp;xfc=http%3A%2F%2Fcs.gssprt.jp%2Fyie%2Fld%2Fck%3Fzid%3D1057940%26asid%3D1469839%26idx%3D0%26gid%3D653aa81b6a7f54d435dfd419d05354a0%26l%3DidJilqqEFz0Fp98TwG1k1E60LNi2YG6fwvDvusIZWqLE8g6HClm3ivqBvZWPXNI6UIUv3ERCJnT_3qsV3AsxQ76xKPgRmiiOStda54XSMhZgb6B7eFuIAUN4X_l__WcV_XIzvKcd7NuhC3fLthdrMyCotYE6ho4uvw3NzogjP5Kj_Kf1USs7VmWixWTQ6nGwT4M0iKPMji1_33ZIOdsi9rwX4-mquzkdHWmfslrTb8lCwnLcwlLt8jKmTrOHNy8r2QCCr6GXSmRhEXaAWDOqNFe8qux2yZuwcip_A1JMavkfW59eTS32Wnj841ZXCgT6QMNdFqog7nIy8vXN0TWj2QLHZWUkIW1PFSINqnHGRaZLaWfSzAoqQo6fXewTMTBP%26dest%3D"></script><div id="ad_unit"><div class="GoogleActiveViewClass" id="DfaVisibilityIdentifier_1203491235"><a target="_blank" href="https://adclick.g.doubleclick.net/pcs/click?xai=AKAOjsvZt3yIgJgsCE-kfOntFTpf9wBIc2b77A83ZoBtgiJCTk0a7Ti9hy9HrpYBW6sjb6IFc3U4YuE5hcd_2cfFVsC0e1WeqRegiAkxMdJ42idQQMpMPYbQKXRDPCnsXPDWXT5H0sZ2oZD7foyI-IjiD9_CFzaQi-0B0wbOiapPFB1zCtnuxSBOV4DO1zICDiw4aKJaO0tdk5oOnGD3J2MRwIN7vtdX1j4QFkCR9me1iIvgKX3YA458ioQfTanMYtMKZnzhFZZvxXSBbimHLotKT2ZWypxgpn5a-cZH1iwSn1SP4-6W2rSnZJsBJ-ehPtBnoq689JzXh6Kc7_wJcsIawTCV27Uu4admwyGl3YCMMvZYR17cScIq3v-K8yhEC_HSnyhQFvrAhQvr9fsIZWX0zrifPTwTVAzfOK_D-UxRUCkrKpHgKhIUgckaHZyOLUiYay_3TewqL8N-AFc96OOH82SR1Bi2c4oCu8__LREIAB4SSWP0hSYs-e64ERK7aDujmgyE9RCxS0LvI5eChIcLngSAkjuR_Av-8Gzod8zG3oz6okwuH7mtw1xStu6MxmA6vum_lGpCkfZxiiY3NawkZkkP4fkM8fr-zuW6aIjiCp_Wg0lCsEDOS2GUsbNjU7aQdx-y8YsjeK-r9Zmyo_a2DrxgXA11uYxohCKIsy4CbUsSR2ISqhFxp7u6spSWHFKXjwpWR4bDhcThHxzsApsqD4P1_f88Oh3wSb5ZyQhUg4jN-m1cXuLjwtIxG91xHhAWHt31xYk_A-TYLlqGI_scRnaZ9PpdgNXdQAizrMlF5_YEA9IN54EUbqVwEig9gDZR-f93262HTDntEkumIXGzgks&amp;sig=Cg0ArKJSzOya8f3s3EZf&amp;urlfix=1&amp;adurl=http://cs.gssprt.jp/yie/ld/ck%3Fzid%3D1057940%26asid%3D1469839%26idx%3D0%26gid%3D653aa81b6a7f54d435dfd419d05354a0%26l%3DidJilqqEFz0Fp98TwG1k1E60LNi2YG6fwvDvusIZWqLE8g6HClm3ivqBvZWPXNI6UIUv3ERCJnT_3qsV3AsxQ76xKPgRmiiOStda54XSMhZgb6B7eFuIAUN4X_l__WcV_XIzvKcd7NuhC3fLthdrMyCotYE6ho4uvw3NzogjP5Kj_Kf1USs7VmWixWTQ6nGwT4M0iKPMji1_33ZIOdsi9rwX4-mquzkdHWmfslrTb8lCwnLcwlLt8jKmTrOHNy8r2QCCr6GXSmRhEXaAWDOqNFe8qux2yZuwcip_A1JMavkfW59eTS32Wnj841ZXCgT6QMNdFqog7nIy8vXN0TWj2QLHZWUkIW1PFSINqnHGRaZLaWfSzAoqQo6fXewTMTBP%26dest%3Dhttp://www.epson.jp/products/ecotank/concept/%3Fxadid%3Dhkd_cis20170215_00005"><img src="https://s0.2mdn.net/6015505/ecotank_view_computer_printink_PC_A.jpg" alt="Advertisement" width="300" border="0" height="250"></a><style>div,ul,li{margin:0;padding:0;}.abgc{display:block;height:15px;position:absolute;right:0px;top:0px;text-rendering:geometricPrecision;width:15px;z-index:9020;}.abgb{display:block;height:15px;width:15px;}.abgc img{display:block;}.abgc svg{display:block;}.abgs{display:none;height:100%;}.abgl{text-decoration:none;}.abgi{fill-opacity:1.0;fill:#00aecd;stroke:none;}.abgbg{fill-opacity:1.0;fill:#cdcccc;stroke:none;}.abgtxt{fill:black;font-family:'Arial';font-size:100px;overflow:visible;stroke:none;}</style><div id="abgc" class="abgc" dir="ltr"><div id="abgb" class="abgb"><svg width="100%" height="100%"><rect class="abgbg" width="100%" height="100%"></rect><svg class="abgi" x="0px"><path d="M7.5,1.5a6,6,0,1,0,0,12a6,6,0,1,0,0,-12m0,1a5,5,0,1,1,0,10a5,5,0,1,1,0,-10ZM6.625,11l1.75,0l0,-4.5l-1.75,0ZM7.5,3.75a1,1,0,1,0,0,2a1,1,0,1,0,0,-2Z"></path></svg></svg></div><div id="abgs" class="abgs"><a id="abgl" class="abgl" href="https://www.google.com/url?ct=abg&amp;q=https://www.google.com/adsense/support/bin/request.py%3Fcontact%3Dabg_afc%26url%3Dhttp://manga-enquete.com/public_html/dreevee/finish.php%26gl%3DJP%26hl%3Dja%26ai0%3D&amp;usg=AFQjCNHSB_ZkEr3XXxTS6TV83cPPIHiKAg" target="_blank"><svg width="100%" height="100%"><path class="abgbg" d="M0,0L96,0L96,15L4,15s-4,0,-4,-4z"></path><svg class="abgtxt" x="5px" y="11px" width="34px"><text>Ads by</text></svg><svg class="abgtxt" x="41px" y="11px" width="38px"><text>Google</text></svg><svg class="abgi" x="81px"><path d="M7.5,1.5a6,6,0,1,0,0,12a6,6,0,1,0,0,-12m0,1a5,5,0,1,1,0,10a5,5,0,1,1,0,-10ZM6.625,11l1.75,0l0,-4.5l-1.75,0ZM7.5,3.75a1,1,0,1,0,0,2a1,1,0,1,0,0,-2Z"></path></svg></svg></a></div></div><script>document.write('\n\x3cscript\x3evar abgp={elp:document.getElementById(\'abgcp\'),el:document.getElementById(\'abgc\'),ael:document.getElementById(\'abgs\'),iel:document.getElementById(\'abgb\'),hw:15,sw:96,hh:15,sh:15,himg:\'https://pagead2.googlesyndication.com\'+\'/pagead/images/abg/icon.png\',simg:\'https://pagead2.googlesyndication.com/pagead/images/abg/en.png\',alt:\'Ads by Google\',t:\'Ads by\',tw:34,t2:\'Google\',t2w:38,tbo:0,att:\'adsbygoogle\',ff:\'\',halign:\'right\',fe:false,iba:false,lttp:false,uic:false,uit:false,ict:document.getElementById(\'cbb\'),ci:\'\',icd:undefined,uaal:true,opi: false};\x3c/script\x3e');</script>
//		<script>var abgp={elp:document.getElementById('abgcp'),el:document.getElementById('abgc'),ael:document.getElementById('abgs'),iel:document.getElementById('abgb'),hw:15,sw:96,hh:15,sh:15,himg:'https://pagead2.googlesyndication.com'+'/pagead/images/abg/icon.png',simg:'https://pagead2.googlesyndication.com/pagead/images/abg/en.png',alt:'Ads by Google',t:'Ads by',tw:34,t2:'Google',t2w:38,tbo:0,att:'adsbygoogle',ff:'',halign:'right',fe:false,iba:false,lttp:false,uic:false,uit:false,ict:document.getElementById('cbb'),ci:'',icd:undefined,uaal:true,opi: false};</script><script src="https://pagead2.googlesyndication.com/pagead/js/r20170227/r20110914/abg.js"></script></div></div><iframe src="https://googleads.g.doubleclick.net/xbbe/pixel?d=CMqzMBDlqUwYtsicEzAB&amp;v=APEucNUmHs7B8PlOBtkO2YPDRjn0ZOgwkp5N5BdNxfNImYDFMe70QM69wiqxwIeCCJP4GE1mwZiqR0IOvWDOrFpIeC8y12er6A" style="display:none"></iframe></div></div><div style="position: absolute; left: 0px; top: 0px; visibility: hidden;"><img src="http://aladdin.genieesspv.jp/yie/ld/gl2?zid=1057940&amp;asid=1469839&amp;idx=0&amp;gid=653aa81b6a7f54d435dfd419d05354a0&amp;cb=f3deaa573f&amp;l=2vdhQGgjyyk1ALeBDerSjZChb5UMfSe97uQjlauXvDi2TkdPS9fh3IGVhjcSi217qhfllUZqXl0GzdMRLYIckglbxvbKIhofZHz1Fc4wFD8Aq5Ki-899ED8l_n2u9Xx9I7_fkfQTYmWrnxLPJavp4Xe4y69dDjlOjsD4j6gbGbtre5wWEtjnL0YoruqQ6_UjVvC371NZsXtczkjHz8C1TKwDSMBGPH28pjdI81d7hxZYyfOD6KBpZwOLqO25ARU1pkBV96OXsxRLhs0PqI-CZSiPm21-JpnP_VKdWRodMoObrEReD5JieZieCkL4jVAeqaZEzMarxhuI4O2CRT5P-YohnGElUNONvGDEpmURIj672ezOaVdyBuFNZ3ueQFsH9eu5pX6X_1Uc-DdoUdz3Iv0Qe3sXK_1MRsvCywbK4XUmED8hgZevF4yvVGZt5Qa5TabLUHvUWVj70wpj-d2Wr3iww2UGKsAJ9SYU7nUnDFLoHk4A3UkauKErLsYy76KjYgb5xjCTF8gys82Ptv634pRLFm6Wdi8tpir4LWkgPoJk8LzNVeTDIA8e1YZyphaJnfGxkLqzUamvOtL6YU11sl_wJikdubLfk5oRpv9MSO-DiWWkLR3FM_TO80v9TSXY3Wb2R9dPwHnc6-OrEZGdBrkmddzHk8cI5ofnInYtnrsH7u8DnJJjYRza1IespXHXYbY0wKjoe-RlaFp1rbG82_DxMwn4YeRXzyvC_F0BfuFyfFQPATWMGO-jImSiBbXiUpDhZ9Vcm_6exd22hWX4Lt9KF4KJ0JO_1Iabe4R2M6ZlvQWT-bA4ApzOzAkTRQVJOvIJFp8_r7RNM4MVnOuJDc-dnDGML_PgDUmUwSe2srRhHmupPnj4K6XpuJOldpn6jpERVa-dnJk" alt="" style="width: 0px; height: 0px;" width="0" height="0"></div><script type="text/javascript">(function(){function op(p){for(var e=p.firstChild;e!=null;e=e.nextSibling){if(e.nodeType==1){if(e.nodeName=='A'){var ah=e.href;if(ah!=null&&ah!=''){var hp='';var hq='';var hh='';var qb=ah.indexOf('?');if(qb>=0){hp=ah.substring(0,qb);var hb=ah.indexOf('#',qb+1);if(hb>=0){hq=ah.substring(qb+1,hb);hh=ah.substring(hb+1);}else{hq=ah.substring(qb+1);}}else{hp=ah;}if(hq!=''){var ob=hq.toLowerCase().indexOf('opensafari=');if(ob==0||(ob>0&&hq.charAt(ob-1)=='&')){continue;}else{hq+='&opensafari=1';}}else{hq='opensafari=1';}ah=hp;if(hq!=''){ah+='?'+hq;if(hh!=''){ah+='#'+hh;}}e.href=ah;}}if(e.nodeName!='SCRIPT'){op(e);}}}}function os(){if(typeof(window.__geniee_open_safari)!=='undefined'&&window.__geniee_open_safari!=null&&window.__geniee_open_safari==1){window.__geniee_open_safari=0;var ss=document.getElementsByTagName('script');var se=ss[ss.length-1];var sp=se.parentNode;var pe=null;if(window.parent==window.self){while(sp!=null&&sp!=document.documentElement&&sp!=document.body){if(sp.nodeType==1&&sp.nodeName=='DIV'){pe=sp;break;}sp=sp.parentNode;}}else{while(sp!=null&&sp!=document.documentElement){if(sp.nodeType==1&&sp.nodeName=='BODY'){pe=sp;break;}sp=sp.parentNode;}}if(pe!=null)op(pe);}}os();})();</script></div>						</div>
//							</div>
								String finishSele = "div#again_bt>a>img";
								if (isExistEle(driver, overLay)) {
									checkOverlay(driver, overLay);
								}
								if (isExistEle(driver, finishSele)) {
									clickSleepSelector(driver, finishSele, 3000); // 遷移
									// 一覧に戻るはず
								}
							}
						}
						else {
							break;
						}
					}
					break; // 星座はこれで終了
				}
			}
		}
	}
}
