package pointGet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author saitou
 * utillity class
 */
public class Utille {

	/**
	 * instance webdriver
	 * @return webdriver object
	 */
	public static WebDriver getWebDriver() {
		// firefox
		System.setProperty("webdriver.gecko.driver",
				"C:/pleiades/eclipse/jre/lib/myjar/geckodriver-v0.11.1-win64/geckodriver.exe");
		System.setProperty("webdriver.firefox.profile", "webdrive");

		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);
		WebDriver driver = new FirefoxDriver(capabilities);
		return driver;
	}

	/**
	 * @param obj 
	 * @return 
	 */
	public static Logger setLogger(Class<?> obj) {
		return Logger.getLogger(obj);
	}

	/**
	 * @param microtime
	 */
	public static void sleep(int microtime) {
		try {
			Thread.sleep(microtime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param microtime
	 */
	public static void sleep2(int microtime) {
		int repeat = microtime / 2000;
		for (int i = 0; i < repeat; i++) {
			Utille.sleep(2000);
		}
	}

	/**
	 * 0以上引数未満の数値をランダムで返す
	 * @param scopLimit
	 * @return 
	 */
	public static int getIntRand(int scopLimit) {
		Random rnd = new Random();
		return rnd.nextInt(scopLimit);
	}

	/**
	 * @param wordList
	 * @param num
	 * @return
	 */
	public static String[] getWordSearchList(String[] wordList, int num) {

		int wordNum = wordList.length;
		if (wordNum >= num) {
			ArrayList<Integer> listIndex = new ArrayList<Integer>();
			for (int i = 0; i < num; i++) {
				int ran = getIntRand(wordNum);
				if (!listIndex.contains(ran)) {
					listIndex.add(ran);
				}
				else {
					i--;
				}
			}
			String[] returnList = new String[num];
			for (int i = 0; i < num; i++) {
				returnList[i] = wordList[listIndex.get(i)];
			}
			return returnList;
		}
		return null;
	}

	/**
	 * 
	 * @param loadFilePath
	 * @return
	 */
	public static Properties getProp(String loadFilePath) {
		Properties loadProps = null;
		// 設定ファイル読み込み
		try {
			loadProps = new Properties();
			// プロパティファイルをバイト入力ストリームとして読み込む。
			InputStream inputStream = new FileInputStream(loadFilePath);
			// 文字コード：UTF-8として、文字入力ストリームに変換する。
			InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
			// バッファリングして、効率的に読み込む。
			BufferedReader br = new BufferedReader(isr);
			loadProps.load(br);
			inputStream.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return loadProps;
	}

	/**
	 * 
	 * @param wEle
	 * @param log
	 * @return boolean
	 */
	public static boolean isExistEle(List<WebElement> wEle, Logger log) {
		Eventually.eventually(() -> wEle);
		return wEle.size() != 0;
	}

	/**
	 * 
	 * @param wEle
	 * @param selector
	 * @param log
	 * @return boolean
	 */
	public static boolean isExistEle(WebElement wEle, String selector, Logger log) {
		boolean is = isExistEle(wEle.findElements(By.cssSelector(selector)), log);
		log.debug(selector + ":[" + is + "]");
		return is;
	}

	/**
	 * @param driver
	 * @param selector
	 * @param log
	 * @return boolean
	 */
	public static boolean isExistEle(WebDriver driver, String selector, Logger log) {
		boolean is = isExistEle(driver.findElements(By.cssSelector(selector)), log);
		log.debug(selector + ":[" + is + "]");
		return is;
	}

	/**
	 * 空白除去
	 * @param str
	 * @return
	 */
	public static String trimWhitespace(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		int st = 0;
		int len = str.length();
		char[] val = str.toCharArray();
		while ((st < len) && ((val[st] <= '\u0020') || (val[st] == '\u00A0') || (val[st] == '\u3000'))) {
			st++;
		}
		while ((st < len) && ((val[len - 1] <= '\u0020') || (val[len - 1] == '\u00A0') || (val[len - 1] == '\u3000'))) {
			len--;
		}
		return ((st > 0) || (len < str.length())) ? str.substring(st, len) : str;
	}

	/**
	 * msのフォーマット
	 * @param period
	 * @return
	 */
	public static String prettyFormat(long period) {
		if (period == 0) {
			return "0ms";
		}
		StringBuffer sb = new StringBuffer();

		sb.append(makePrettyString(period, 1000, "ms"));
		period /= 1000;
		sb.insert(0, makePrettyString(period, 60, "s"));
		period /= 60;
		sb.insert(0, makePrettyString(period, 60, "m"));
		period /= 60;
		sb.insert(0, makePrettyString(period, 24, "h"));
		period /= 24;
		sb.insert(0, makePrettyString(period, 0, "d"));
		return sb.toString();
	}

	private static String makePrettyString(long src, long unit, String unitStr) {
		if (src == 0) {
			return "";
		}
		if (unit == 0) {
			return src + unitStr;
		}
		long unitTime = src % unit;
		if (unitTime == 0) {
			return "";
		}
		return unitTime + unitStr;
	}
}
