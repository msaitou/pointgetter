package pointGet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * @param geckoPath 
	 * @param ffProfile 
	 * @return webdriver object
	 */
	public static WebDriver getWebDriver(String geckoPath, String ffProfile) {
		// firefox
		System.setProperty("webdriver.gecko.driver", geckoPath);
		System.setProperty("webdriver.firefox.profile", ffProfile);

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
	 * @param logg
	 * @return boolean
	 */
	public static boolean isExistEle(List<WebElement> wEle, Logger logg) {
		Eventually.eventually(() -> wEle);
		return wEle.size() != 0;
	}

	/**
	 * 
	 * @param wEle
	 * @param selector
	 * @param logg
	 * @return boolean
	 */
	public static boolean isExistEle(WebElement wEle, String selector, Logger logg) {
		boolean is = isExistEle(wEle.findElements(By.cssSelector(selector)), logg);
		logg.info(selector + ":[" + is + "]");
		return is;
	}

	/**
	 * @param driver
	 * @param selector
	 * @param logg
	 * @return boolean
	 */
	public static boolean isExistEle(WebDriver driver, String selector, Logger logg) {
		boolean is = isExistEle(driver.findElements(By.cssSelector(selector)), logg);
		logg.info(selector + ":[" + is + "]");
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

	/**
	 * 
	 * @param regex
	 * @param word
	 * @param index 
	 * @return 
	 */
	public static String getPatternWord(String regex, String word, int index) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(word);
		if (m.find()) {
			String matchstr = m.group();
			System.out.println(matchstr + "の部分にマッチしました");
			for (int i = 0; i <= m.groupCount(); i++) {
				System.out.println("group" + i + ":" + m.group(i));
				if (index == i) {
					return m.group(i);
				}
			}
		}
		return "";
	}
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	public static String parseStringFromStackTrace(Throwable e) {
		// エラーのスタックトレースを表示
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}
}
