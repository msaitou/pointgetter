package pointGet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;

import org.openqa.selenium.WebDriver;

/**
 * get point from the point mail
 * @author 雅人
 *
 */
public class MailClicker extends PointGet {

	// config file for PointGetMail path
	private static final String loadFilePath = "pGetMail.properties";
	// PointGetMail config variable
	private static String[] siteCodes = null;
	// PointGetMail config variable
	private static Map<String, HashMap<String, String>> targetSites = new HashMap<String, HashMap<String, String>>();
	// mail config variable
	private static Map<String, String> mailConf = new HashMap<String, String>();

	protected static void init() {
		PointGet.init();
		_setLogger("log4jmail.properties", MailClicker.class);
		// properties をローカル変数に展開
		Properties loadProps = Utille.getProp(loadFilePath);
		if (loadProps.isEmpty()) {
			return;
		}
		// メールのクリックポイントを獲得する対象のサイトを取得
		siteCodes = loadProps.getProperty("targetSiteCode").split(",");
		// PointGetMail config variable
		for (int i = 0; i < siteCodes.length; i++) {
			String key = siteCodes[i];
			if (key != null && !key.equals("")) {
				HashMap<String, String> siteConf = new HashMap<String, String>();
				siteConf.put("dir", loadProps.getProperty(key + ".dir"));
				siteConf.put("subjectKey", loadProps.getProperty(key + ".subjectKey"));
				if (loadProps.containsKey(key + ".subjectKey2")) {
					siteConf.put("subjectKey2", loadProps.getProperty(key + ".subjectKey2"));
				}
				targetSites.put(key, siteConf);
			}
		}
		// mail's conf
		mailConf.put("host", loadProps.getProperty("mail.host"));
		mailConf.put("user", loadProps.getProperty("mail.user"));
		mailConf.put("password", loadProps.getProperty("mail.password"));
		mailConf.put("port", loadProps.getProperty("mail.port"));
		mailConf.put("before", loadProps.getProperty("mail.beforeDate"));
	}

	/**
	 * Entry point!!!
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		init();
		logg.info("■■■Starting my application...");
		logg.info("■■-Starting get Mail...");
		// 設定ファイルを読み込む
		if (mailConf.isEmpty() || siteCodes.length < 1 || targetSites.isEmpty()) {
			logg.info("can not read properties!!");
			logg.info("mailConf: " + mailConf.toString());
			logg.info("siteCodes: " + siteCodes.toString());
			logg.info("targetSites: " + targetSites.toString());
			return;
		}
		Properties props = new Properties();
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		props.put("mail.imap.starttls.enable", "true");
		props.put("mail.imap.auth", "true");
		props.put("mail.imap.socketFactory.port", mailConf.get("port"));
		props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.imap.socketFactory.fallback", "false");

		Store store = null;
		Map<String, ArrayList<String>> urlMap = new HashMap<String, ArrayList<String>>();
		try {
			Session session = Session.getDefaultInstance(props, null);
			store = session.getStore("imap");
			store.connect(mailConf.get("host"), mailConf.get("user"), mailConf.get("password"));

			int iBeforeDate = Integer.parseInt(mailConf.get("before"));
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -iBeforeDate); // 実行時の前日の日付以降のメールを取得する
			//			cal.add(Calendar.DATE, -10); // 実行時の前日の日付以降のメールを取得する
			int getMailYear = cal.get(Calendar.YEAR);
			int getMailMonth = cal.get(Calendar.MONTH); // 1月が0で12月は11らしい
			int getMailDate = cal.get(Calendar.DATE);

			logg.info(getMailYear + "/" + String.valueOf(getMailMonth + 1) + "/" + getMailDate + " 以降のmailを取得");
			// loop for folder size
			for (Map.Entry<String, HashMap<String, String>> e : targetSites.entrySet()) {
				String folderName = e.getValue().get("dir");
				logg.info("■getMail site[" + folderName + "] START");

				// IMAPの場合はラベル名を指定すればそのラベルのメールが取得出来る
				// (POP3の場合はエラーが発生します)
				Folder folder = store.getFolder(folderName);
				folder.open(Folder.READ_ONLY);
				Message[] messages = folder.search(new ReceivedDateTerm(ComparisonTerm.GE, new GregorianCalendar(
						getMailYear, getMailMonth, getMailDate).getTime()));
				// メッセージ件数分
				for (Message message : messages) {
					// 件名
					//					logg.debug("Subject: " + message.getSubject());
					String keyWord = e.getValue().get("subjectKey");
					String keyWord2 = e.getValue().get("subjectKey2");
					if (!keyWord.isEmpty()) {
						if (message.getSubject().indexOf(keyWord) >= 0) {
							logg.info("Subject: " + message.getSubject());
						}
						else {
							if (keyWord2 != null && !keyWord2.isEmpty()) {
								// Subject2
								if (message.getSubject().indexOf(keyWord2) >= 0) {
									logg.info("Subject: " + message.getSubject());
								}
								else {
									continue;
								}
							}
							else {
								continue;
							}
						}
					}
					else {
						// keyWordが空なら無条件
						logg.info("Received: " + message.getReceivedDate() + " Subject: " + message.getSubject());
					}
					String contentType = "";
					// コンテンツタイプ取得
					String[] headers = message.getHeader("Content-Type");
					for (String header : headers) {
						logg.info("Content-Type: " + header);
						if (header.indexOf(Define.CONTENT_TYPE_TEXT) >= 0) {
							contentType = Define.CONTENT_TYPE_TEXT;
						}
						else if (header.indexOf(Define.CONTENT_TYPE_HTML) >= 0) {
							contentType = Define.CONTENT_TYPE_HTML;
						}
						else if (header.indexOf(Define.CONTENT_TYPE_MULTI) >= 0) {
							contentType = Define.CONTENT_TYPE_MULTI;
						}
						break;
					}

					// 本文
					getText(contentType, message.getContent(), urlMap, e.getKey());
				}
				folder.close(false);
				logg.info("■getMail site[" + folderName + "] END");
			}
			System.out.println(urlMap.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (store != null) {
					store.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			logg.info("■■-finished get Mail...");
		}

		//		if (false) {
		//			return;
		//		}
		if (!urlMap.isEmpty()) {
			// URLに対してリクエストをする
			logg.info("■■-Starting request URL...");
			for (Map.Entry<String, ArrayList<String>> e : urlMap.entrySet()) {
				logg.info("■request site[" + e.getKey() + "] START");
				try {
					if (e.getKey().equals(Define.PSITE_CODE_OSA)
							|| e.getKey().equals(Define.PSITE_CODE_ECN)
							|| e.getKey().equals(Define.PSITE_CODE_MOP)
							|| e.getKey().equals(Define.PSITE_CODE_RIN)
							|| e.getKey().equals(Define.PSITE_CODE_PEX)
							|| e.getKey().equals(Define.PSITE_CODE_R01)
							|| e.getKey().equals(Define.PSITE_CODE_PTO
									)) {
						WebDriver driver = getWebDriver();
						if (Define.PSITE_CODE_PTO.equals(e.getKey())) {
							// login!!
							LoginSite.login(Define.PSITE_CODE_PTO, driver, logg);
						}
						else if (Define.PSITE_CODE_PEX.equals(e.getKey())) {
							// login!!
							LoginSite.login(Define.PSITE_CODE_PEX, driver, logg);
						}

						for (String url : e.getValue()) {
							System.out.println("kiiiiii222");
							String uriString = url; // 開くURL
							String logMess = "■request url[" + uriString + "] START";
							try {
								driver.get(uriString);
								Utille.sleep(1000);
								logMess += "-END";
							}
							catch (Throwable t) {
								t.printStackTrace();
								driver.quit();
								logg.error("##Exception##################");
								logg.error(Utille.truncateBytes(t.getLocalizedMessage(), 500));
								logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(t), 500));
								logg.error("##Exception##################");
								driver = getWebDriver();
							}
							logg.info(logMess);
						}
						driver.quit();
					}
					else {
						for (String url : e.getValue()) {
							HttpURLConnection conn;
							conn = (HttpURLConnection) new URL(url).openConnection();
							InputStream in = conn.getInputStream();
							BufferedReader reader = new BufferedReader(new InputStreamReader(in));
							StringBuilder out = new StringBuilder();
							String line;
							while ((line = reader.readLine()) != null) {
								out.append(line).append("\n\r");
							}
							logg.info("request url[" + url + "]");
							//							logg.info("response[" + out.toString() + "]");
							Utille.sleep(2000);
							reader.close();
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				logg.info("■request site[" + e.getKey() + "] END");
			}
			logg.info("■■-finished request URL...");
		}
		logg.info("■■■finished my application...");
	}

	private static void getText(String contentType, Object content, Map<String, ArrayList<String>> urlMap,
			String folederKind)
			throws IOException, MessagingException {
		if (content instanceof String) {
//			logg.info("■■-getContent [" + content);
			String[] contentLow = content.toString().split("\\r\\n", 0);
			ArrayList<String> urlList = new ArrayList<String>();
			for (int i = 0; i < contentLow.length; i++) {
				//												logg.info("■■-getContent [" + contentLow[i]);
				switch (folederKind) {
				case Define.PSITE_CODE_GMY: // GET MONEY
					if (contentLow[i].indexOf("click.php") > 0) {
						String url = contentLow[i].substring(contentLow[i].indexOf("http"));
						urlList.add(url);
					}
					break;
				case Define.PSITE_CODE_OSA: // お財布.com
					for (String indexKey : new String[] { "/clickevent/", "/click/" }) {
						if (contentLow[i].indexOf(indexKey) >= 0) {
							String url = contentLow[i].substring(contentLow[i].indexOf("http"));
							urlList.add(url);
						}
					}
					break;
				case Define.PSITE_CODE_PTO: // PointTown
					if (Define.CONTENT_TYPE_TEXT.equals(contentType)) {
						if (contentLow[i].indexOf("[Point] http") >= 0) {
							String url = contentLow[i].substring(contentLow[i].indexOf("http"));
							urlList.add(Utille.trimWhitespace(url));
						}
					}
					else if (Define.CONTENT_TYPE_HTML.equals(contentType)) {
						int matchCnt = 0;
						for (String indexKey : new String[] { "a href", "/ptu/" }) {
							if (contentLow[i].toLowerCase().indexOf(indexKey) >= 0) {
								matchCnt++;
							}
							if (matchCnt == 2) {
								String url = contentLow[i].substring(contentLow[i].indexOf("http"),
										contentLow[i].indexOf("\"", contentLow[i].indexOf("/ptu/")));
								urlList.add(Utille.trimWhitespace(url));
							}
						}
					}
					break;
				case Define.PSITE_CODE_ECN: // ECナビ
					for (String indexKey : new String[] { "[ポイントGET?] http", "/pointget/" }) {
						if (contentLow[i].indexOf(indexKey) >= 0) {
							String url = contentLow[i].substring(contentLow[i].indexOf("http"));
							if (url.indexOf(" ") > 0) { // ブランクに続いてコメントが記述されている場合
								url = url.substring(0, url.indexOf(" "));
							}
							urlList.add(Utille.trimWhitespace(url));
						}
					}
					break;
				case Define.PSITE_CODE_GEN: // げん玉
					if (contentLow[i].indexOf("/cl/") >= 0) {
						String url = contentLow[i].substring(contentLow[i].indexOf("http"));
						urlList.add(Utille.trimWhitespace(url));
					}
					break;
				case Define.PSITE_CODE_MOP:// モッピー
					for (String indexKey : new String[] { "/cl/", "/clc/" }) {
						if (contentLow[i].indexOf(indexKey) >= 0) {
							String url = contentLow[i].substring(contentLow[i].indexOf("http"));
							urlList.add(Utille.trimWhitespace(url));
						}
					}
					break;
				case Define.PSITE_CODE_RIN: // 楽天infoseek
					if (Define.CONTENT_TYPE_TEXT.equals(contentType)) {
						if (contentLow[i].indexOf("pmrd.rakuten.co.jp/?r=") >= 0) {
							String url = contentLow[i].substring(contentLow[i].indexOf("http"));
							//							urlList = new ArrayList<String>();
							urlList.add(Utille.trimWhitespace(url));
						}
					}
					else if (Define.CONTENT_TYPE_HTML.equals(contentType)) {
						for (String indexKey : new String[] { "http://pmrd.rakuten.co.jp/?r=" }) {
							String url = "";
							if (contentLow[i].indexOf(indexKey) >= 0) {
								if (contentLow[i].indexOf("\"", contentLow[i].indexOf("http://pmrd.rakuten.co.jp/?r=")) > 0) {
									url = contentLow[i].substring(
											contentLow[i].indexOf("http://pmrd.rakuten.co.jp/?r="),
											contentLow[i].indexOf("\"",
													contentLow[i].indexOf("http://pmrd.rakuten.co.jp/?r=")));
								}
								else {
									System.out.println(contentLow[i]);
									url = contentLow[i].substring(contentLow[i]
											.indexOf("http://pmrd.rakuten.co.jp/?r="));
								}
								urlList.add(Utille.trimWhitespace(url));
							}
						}
					}
					break;
				case Define.PSITE_CODE_R01: // 楽天infoseek以外
					if (Define.CONTENT_TYPE_HTML.equals(contentType)) {
						for (String indexKey : new String[] { "https://r.rakuten.co.jp/",
								"http://ac.rakuten-card.co.jp/s.p",
								"http://ac.rakuten-card.co.jp/c.p" }) {
							String url = "";
							if (contentLow[i].indexOf(indexKey) >= 0) {
								if (contentLow[i].indexOf("\"", contentLow[i].indexOf(indexKey)) > 0) {
									url = contentLow[i].substring(
											contentLow[i].indexOf(indexKey),
											contentLow[i].indexOf("\"",
													contentLow[i].indexOf(indexKey)));
								}
								else {
									System.out.println(contentLow[i]);
									url = contentLow[i].substring(contentLow[i]
											.indexOf(indexKey));
								}
								urlList.add(Utille.trimWhitespace(url));
							}
						}
					}
					break;
				case Define.PSITE_CODE_PEX: // PEX
					if (contentLow[i].indexOf("pex.jp/redirector/") >= 0) {
						String url = contentLow[i].substring(contentLow[i].indexOf("https"));
						urlList.add(Utille.trimWhitespace(url));
					}
					break;
				case Define.PSITE_CODE_I2I: // ポイントメールなし
					break;
				// テスト用
				case "test":
					break;
				}
			}

			Set<String> set = new HashSet<>(urlList); // これで値の重複が消える
			List<String> list2 = new ArrayList<>(set);
			if (!urlMap.containsKey(folederKind)) {
				urlMap.put(folederKind, new ArrayList<String>());
			}
			urlMap.get(folederKind).addAll(list2);
		}
		else if (content instanceof Multipart) {
			Multipart mp = (Multipart) content;
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart bp = mp.getBodyPart(i);
				String mContentType = "";
				// コンテンツタイプ取得
				String[] headers = bp.getHeader("Content-Type");
				for (String header : headers) {
					logg.info("Content-Type: " + header);
					if (header.indexOf(Define.CONTENT_TYPE_TEXT) >= 0) {
						mContentType = Define.CONTENT_TYPE_TEXT;
					}
					else if (header.indexOf(Define.CONTENT_TYPE_HTML) >= 0) {
						mContentType = Define.CONTENT_TYPE_HTML;
					}
					else if (header.indexOf(Define.CONTENT_TYPE_MULTI) >= 0) {
						mContentType = Define.CONTENT_TYPE_MULTI;
					}
					break;
				}
				if (Define.CONTENT_TYPE_TEXT.indexOf(mContentType) >= 0) {
					continue;
				}
				//				sb.append(getText(bp.getContent(), urlMap));
				getText(mContentType, bp.getContent(), urlMap, folederKind);
			}
		}
	}
}
