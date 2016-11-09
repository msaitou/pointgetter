package pointGet;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;

/**
 * @author saitou
 *
 */
public abstract class PointGet {

	private static String propPath = "pointGet.properties";
	private static String[] siteCodeList = null;
	
	// log
	protected static Logger logg = null;
	// PointGet common properties
	protected static Map<String, HashMap<String, String>> pGetProps = new HashMap<String, HashMap<String, String>>();
	protected static Map<String, String> commonProps = new HashMap<String, String>();

	protected static void init() {
		_loadProps();
	}

	/**
	 * ログクラスの設定
	 */
	protected static void _setLogger(String strPropFile, Class<?> clsObj) {
		PropertyConfigurator.configure(strPropFile);
		logg = Utille.setLogger(clsObj);
	}

	protected static boolean isExistEle(WebDriver driver, String selector) {
		return Utille.isExistEle(driver, selector, logg);
	}

	public static WebDriver getWebDriver() {
		return Utille.getWebDriver(commonProps.get("geckopath"), commonProps.get("ffprofile"));
	}
	
	/**
	 * 設定ファイルをローカル変数に展開する
	 */
	private static void _loadProps() {
		Properties loadProps = Utille.getProp(propPath);
		if (loadProps.isEmpty()) {
			return;
		}
		// 対象のサイトを取得
		siteCodeList = loadProps.getProperty("SiteCodeList").split(",");
		String[] attrList = loadProps.getProperty("AttrList").split(",");

		// PointGet config variable
		for (int i = 0; i < siteCodeList.length; i++) {
			String siteCode = siteCodeList[i];
			if (siteCode != null && !siteCode.equals("")) {
				HashMap<String, String> siteConf = new HashMap<String, String>();
				for (int j = 0; j < attrList.length; j++) {
					if (loadProps.containsKey(siteCode + "." + attrList[j])) {
						siteConf.put(attrList[j], loadProps.getProperty(siteCode + "." + attrList[j]));
					}
				}
				pGetProps.put(siteCode, siteConf);
			}
		}
		if (loadProps.containsKey("geckopath")) {
			commonProps.put("geckopath", loadProps.getProperty("geckopath"));
		}
		if (loadProps.containsKey("ffprofile")) {
			commonProps.put("ffprofile", loadProps.getProperty("ffprofile"));
		}
	}
}
