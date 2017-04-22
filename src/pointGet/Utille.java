package pointGet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author saitou utillity class
 */
public class Utille {

	public static final String APP_LOG_ERR = "Error";
	public static final Charset UTF_8 = StandardCharsets.UTF_8;
	public static int logLimitByte = 500;

	/**
	 * instance webdriver
	 *
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
	 * 0以上引数未満の数値をランダムで返す
	 *
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

	private static final Duration TIMEOUT = Duration.ofSeconds(30);
	private static final Duration INTERVAL = Duration.ofSeconds(3);

	/**
	 *
	 * @param wEle
	 * @param logg
	 * @return boolean
	 */
	public static boolean isExistEle(List<WebElement> wEle, Logger logg) {
		Eventually.eventually(() -> wEle, TIMEOUT, INTERVAL);
		return wEle.size() != 0;
	}

	/**
	 * 
	 * @param ele
	 * @param index
	 * @param logg
	 * @return
	 */
	public static boolean isExistEle(List<WebElement> ele, int index, Logger logg) {
		List<WebElement> eleL = new ArrayList<WebElement>();
		eleL.add(ele.get(index));
		boolean is = isExistEle(eleL, logg);
		logg.info(index + ":[" + is + "]");
		return is;
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
		return isExistEle(driver, selector, true, logg);
	}

	/**
	 * @param driver
	 * @param selector
	 * @param logg
	 * @return boolean
	 */
	public static boolean isExistEle(WebDriver driver, String selector, boolean showFlag, Logger logg) {
		By register = By.cssSelector(selector);
		boolean is = isExistEle(driver.findElements(register), logg);
		logg.info(selector + ":[" + is + "]");
		if (showFlag && is) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, 30);//待ち時間を指定
				wait.until(ExpectedConditions.visibilityOfElementLocated(register));
			} catch (Throwable e) {
				is = false;
				logg.info("---------------------------dont wanna wait!");
				logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 1000));
			}
		}
		return is;
	}

	/**
	 * 空白除去
	 *
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
	 *
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

	/**
	 * 文字列を指定バイト数で切り捨てる
	 *
	 * @param s
	 * @param maxBytes
	 * @return
	 */
	public static String truncateBytes(String s, int maxBytes) {
		Charset charset = UTF_8;
		ByteBuffer bb = ByteBuffer.allocate(maxBytes);
		CharBuffer cb = CharBuffer.wrap(s);
		CharsetEncoder encoder = charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE)
				.onUnmappableCharacter(CodingErrorAction.REPLACE).reset();
		CoderResult cr = encoder.encode(cb, bb, true);
		if (!cr.isOverflow()) {
			return s;
		}
		encoder.flush(bb);
		return cb.flip().toString();
	}

	/**
	 * 現在の曜日を返します。 ※曜日は省略します。
	 *
	 * @return 現在の曜日
	 */
	public static int getDayOfTheWeekShort() {
		Calendar cal = Calendar.getInstance();
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.MONDAY:
				return 1;
			case Calendar.TUESDAY:
				return 2;
			case Calendar.WEDNESDAY:
				return 3;
			case Calendar.THURSDAY:
				return 4;
			case Calendar.FRIDAY:
				return 5;
			case Calendar.SATURDAY:
				return 6;
			case Calendar.SUNDAY:
				return 7;
		}
		throw new IllegalStateException();
	}

	/**
	 * 今日から何日後を受け取って曜日を返す
	 *
	 * @param strAfterDayNum
	 * @return
	 */
	public static String getNanyoubi(String strAfterDayNum) {
		int iAfterDayNum = Integer.parseInt(strAfterDayNum);
		int amari = iAfterDayNum % 7;
		int thisYoubi = getDayOfTheWeekShort();
		int resYoubi = (thisYoubi + amari);
		if (resYoubi > 7) {
			resYoubi -= 7;
		}
		return String.valueOf(resYoubi);
	}

	/**
	 * 今日から何日後を受け取って曜日を返す
	 *
	 * @param strAfterDayNum
	 * @return
	 */
	public static String calcAnzan(Matcher m) {
		String strNum1 = m.group(1);
		String strNum2 = m.group(3);
		String strNum3 = m.group(5);
		int i = 0;
		String[] operator = new String[2];
		for (String code : new String[] { m.group(2), m.group(4) }) {
			String ope = "";
			switch (code) {
				case "+":
					ope = "+";
					break;
				case "-":
					ope = "-";
					break;
				case "×":
					ope = "*";
					break;
				case "÷":
					ope = "/";
					break;
			}
			operator[i++] = ope;
		}
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		Object res = null;
		try {
			res = engine.eval(strNum1 + operator[0] + strNum2 + operator[1] + strNum3);
			if (res instanceof Double) {
				double val = (Double) res;
				// 元データをBigDecimal型にする
				BigDecimal bd = new BigDecimal(val);
				// 四捨五入する
				res = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
		} catch (ScriptException ex) {
			ex.printStackTrace();
		}
		return res.toString();
	}

	/**
	 * 数値として等しいことを確認
	 * @param strA
	 * @param strB
	 * @return
	 */
	public static boolean numEqual(String strA, String strB) {
		Double dA = Double.parseDouble(strA);
		Double dB = Double.parseDouble(strB);
		return dA.compareTo(dB) == 0;
	}

	public static String getSiteCode(String mission) {
		String site = mission.substring(0, 3).toLowerCase();
		switch (site) {
			case Define.PSITE_CODE_R01:
				site = Define.PSITE_CODE_RIN;
				break;
		}
		return site;
	}

	/**
	 * 現在の日付を指定されたフォーマットの文字列にして返す
	 *
	 * @param formatStr 変換するフォーマット文字列
	 * @return 変換されたフォーマットの日付文字列
	 */
	public static String getNowTimeStr(String formatStr) {
		Date date = new Date();
		DateFormat updateTimeForm = new SimpleDateFormat(formatStr);
		return updateTimeForm.format(date);
	}

	/**
	 * 
	 * @param formatStr
	 * @return
	 */
	public static int getWaitTimeRan(String text) {
		int waitTime = getWaitTime(text);
		int ranDiff = Utille.getIntRand(300);
		int ranSign = Utille.getIntRand(2);
		if (ranSign == 0) {
			return waitTime + ranDiff;
		}
		else {
			return waitTime - ranDiff;
		}
	}

	/**
	 * 
	 * @param formatStr
	 * @return
	 */
	public static int getWaitTime(String text) {
		Double dA = Double.parseDouble(text) * 1000;
		return dA.intValue();
	}

	/**
	 *
	 * @param points
	 * @return
	 */
	public static String getNumber(String points) {
		String[] execlude = { ",", " pt", " pt", "Pt", "pt", "mile", "ポイント" };
		for (String s : execlude) {
			if (points.indexOf(s) > 0) {
				points = points.replaceAll(s, "");
				points = points.trim();
			}
		}
		return points;
	}

	/**
	 * 
	 * @param site
	 * @param points
	 * @param tot
	 * @return
	 */
	public static Double sumTotal(String site, String points, Double tot) {
		double current = Double.parseDouble(getNumber(points));
		switch (site) {
			case Define.PSITE_CODE_OSA:
			case Define.PSITE_CODE_MOP:
			case Define.PSITE_CODE_PMO:
			case Define.PSITE_CODE_HAP:
				tot += current;
				break;
			case Define.PSITE_CODE_CRI:
			case Define.PSITE_CODE_SUG:
				tot += current / 2;
				break;
			case Define.PSITE_CODE_GMY:
			case Define.PSITE_CODE_PEX:
			case Define.PSITE_CODE_ECN:
			case Define.PSITE_CODE_I2I:
			case Define.PSITE_CODE_GEN:
			case Define.PSITE_CODE_PIL:
			case Define.PSITE_CODE_PIC:
			case Define.PSITE_CODE_MOB:
			case Define.PSITE_CODE_WAR:
			case Define.PSITE_CODE_CIT:
			case Define.PSITE_CODE_PST:
			case "secondPoint":
				tot += current / 10;
				break;
			case Define.PSITE_CODE_PTO:
				tot += current / 20;
				break;
			case Define.PSITE_CODE_PNY:
				tot += current / 100;
				break;
			default:
				break;
		}
		return (double) Math.round(tot * 10) / 10;
	}
}
