package pointGet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
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

import org.bson.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Define;
import pointGet.common.Utille;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * get point from the point mail
 * @author 雅人
 *
 */
public class MailClicker extends PointGet {

  // PointGetMail config variable
  private static String[] siteCodes = null;
  // PointGetMail config variable
  private static Map<String, HashMap<String, String>> targetSites = new HashMap<String, HashMap<String, String>>();
  // mail config variable
  private static Map<String, String> mailConf = new HashMap<String, String>();

  protected static void init() {
    PointGet.init(MailClicker.class.getSimpleName());
    _setLogger("log4jmail.properties", MailClicker.class);

    Map<String, HashMap<String, String>> targetSitesTmp = new HashMap<String, HashMap<String, String>>();
    Map<String, Object> cParams = new HashMap<String, Object>();
    cParams.put("cond", (DBObject) JSON.parse("{'type':'mail'}"));
    // DBから設定を読み込む
    @SuppressWarnings("unchecked")
    List<HashMap<String, Object>> rec = (List<HashMap<String, Object>>) Dbase.accessDb("find",
        "configs", cParams, null);
    if (null == rec || rec.isEmpty() || null == rec.get(0)) {
      System.out.println("not found Conigs..");
      return;
    }
    // 取り出した値をフィールド名で整理
    for (Map.Entry<String, Object> field : rec.get(0).entrySet()) {
      String key = field.getKey();
      Object value = field.getValue();
      switch (key) {
        // 何もしない
        case "targetSiteCodeOrigin":
        case "type":
          break;
        // メールをとってくる対象サイトコートリスト
        case "targetSiteCode":
          if (value instanceof List) {
            List docs = (List) value;
            siteCodes = new String[docs.size()];
            for (int i = 0; i < docs.size(); i++) {
              siteCodes[i] = docs.get(i).toString();
              System.out.println("siteCodes::::" + siteCodes[i]);
            }
          }
          break;
        // メールの取得設定
        case "host":
        case "user":
        case "port":
        case "password":
        case "before":
          mailConf.put(key, value.toString());
          break;
        default:
          if (value instanceof Document) {
            // 各サイト別のメールフォルダ等の情報を抽出(対象とは別)
            Document doc = (Document) value;
            HashMap<String, String> siteConf = new HashMap<String, String>();
            doc.forEach((k, val) -> {
              siteConf.put(k, (String) val);
            });
            targetSitesTmp.put(key, siteConf);
          }
      }
    }
    // 対象サイトだけに絞る
    targetSitesTmp.forEach((k, val) -> {
      if (Arrays.asList(siteCodes).contains(k)) {
        targetSites.put(k, targetSitesTmp.get(k));
      }
    });
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
          String keyWord3 = e.getValue().get("subjectKey3");
          if (!keyWord.isEmpty()) {
            if (message.getSubject().indexOf(keyWord) >= 0) {
              logg.info("Subject: " + message.getSubject());
            } else {
              if (keyWord2 != null && !keyWord2.isEmpty()) {
                // Subject2
                if (message.getSubject().indexOf(keyWord2) >= 0) {
                  logg.info("Subject: " + message.getSubject());
                } else if (keyWord3 != null && !keyWord3.isEmpty()) {
                  if (message.getSubject().indexOf(keyWord3) >= 0) {
                    logg.info("Subject: " + message.getSubject());
                  } else {
                    continue;
                  }
                } else {
                  continue;
                }
              } else {
                continue;
              }
            }
          } else {
            // keyWordが空なら無条件
            logg.info("Received: " + message.getReceivedDate() + " Subject: " + message.getSubject());
          }
          String contentType = "";
          // コンテンツタイプ取得
          String[] headers = message.getHeader("Content-Type");
          if (headers == null) {
            contentType = Define.CONTENT_TYPE_TEXT;
          } else {
            for (String header : headers) {
              logg.info("Content-Type: " + header);
              if (header.indexOf(Define.CONTENT_TYPE_TEXT) >= 0) {
                contentType = Define.CONTENT_TYPE_TEXT;
              } else if (header.indexOf(Define.CONTENT_TYPE_HTML) >= 0) {
                contentType = Define.CONTENT_TYPE_HTML;
              } else if (header.indexOf(Define.CONTENT_TYPE_MULTI) >= 0) {
                contentType = Define.CONTENT_TYPE_MULTI;
              }
              break;
            }
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
              || e.getKey().equals(Define.PSITE_CODE_PTO)
              || e.getKey().equals(Define.PSITE_CODE_SUG)
              || e.getKey().equals(Define.PSITE_CODE_PMO)
              || e.getKey().equals(Define.PSITE_CODE_PST)
              || e.getKey().equals(Define.PSITE_CODE_NTM)
              || e.getKey().equals(Define.PSITE_CODE_PIL)
              || e.getKey().equals(Define.PSITE_CODE_KOZ)
              || e.getKey().equals(Define.PSITE_CODE_HAP)
              || e.getKey().equals(Define.PSITE_CODE_CRI)
              || e.getKey().equals(Define.PSITE_CODE_CIT)
              || e.getKey().equals(Define.PSITE_CODE_I2I)
              || e.getKey().equals(Define.PSITE_CODE_PIC)
              || e.getKey().equals(Define.PSITE_CODE_WAR)) {
            WebDriver driver = getWebDriver();
            // login!!
            if (Define.PSITE_CODE_PTO.equals(e.getKey())) {
              LoginSite.login(Define.PSITE_CODE_PTO, driver, logg);
            } else if (Define.PSITE_CODE_PEX.equals(e.getKey())) {
              LoginSite.login(Define.PSITE_CODE_PEX, driver, logg);
            } else if (Define.PSITE_CODE_WAR.equals(e.getKey())) {
              LoginSite.login(Define.PSITE_CODE_WAR, driver, logg);
            } else if (Define.PSITE_CODE_PIL.equals(e.getKey())) {
              LoginSite.login(Define.PSITE_CODE_PIL, driver, logg);
            }

            for (String url : e.getValue()) {
              System.out.println("kiiiiii222");
              String uriString = url; // 開くURL
              String logMess = "■request url[" + uriString + "] START";
              try {
                driver.get(uriString);
                Utille.sleep(1000);
                if (e.getKey().equals(Define.PSITE_CODE_I2I)) {
                  String sele = "div.detail_btn>a";
                  Utille.sleep(3000);
                  if (Utille.isExistEle(driver, sele, logg)) {
                    driver.findElement(By.cssSelector(sele)).click();
                    Utille.sleep(2000);
                  }
                } else if (e.getKey().equals(Define.PSITE_CODE_KOZ)) {
                  String sele = "div.campaign-menu-button>a[href*='http://okodukai.jp/jump.php']";
                  Utille.sleep(3000);
                  if (Utille.isExistEle(driver, sele, logg)) {
                    driver.findElement(By.cssSelector(sele)).click();
                    Utille.sleep(2000);
                  }
                } else if (e.getKey().equals(Define.PSITE_CODE_PST)) {
                  String sele = "p>a>img.imgover";
                  Utille.sleep(3000);
                  if (Utille.isExistEle(driver, sele, logg)) {
                    driver.findElement(By.cssSelector(sele)).click();
                    Utille.sleep(2000);
                  }
                } else if (e.getKey().equals(Define.PSITE_CODE_PIL)) {
                  String sele = "input[name='getpoint']";
                  Utille.sleep(3000);
                  if (Utille.isExistEle(driver, sele, logg)) {
                    driver.findElement(By.cssSelector(sele)).click();
                    Utille.sleep(2000);
                  }
                }
                logMess += "-END";
              } catch (Throwable t) {
                t.printStackTrace();
                driver.close();
                logg.error("##Exception##################");
                logg.error(Utille.truncateBytes(t.getLocalizedMessage(), 500));
                logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(t), 500));
                logg.error("##Exception##################");
                driver = getWebDriver();
              }
              logg.info(logMess);
            }
            driver.close();
          } else {
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
            } else if (Define.CONTENT_TYPE_HTML.equals(contentType)) {
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
            } else if (Define.CONTENT_TYPE_HTML.equals(contentType)) {
              for (String indexKey : new String[] { "http://pmrd.rakuten.co.jp/?r=" }) {
                String url = "";
                if (contentLow[i].indexOf(indexKey) >= 0) {
                  if (contentLow[i].indexOf("\"",
                      contentLow[i].indexOf("http://pmrd.rakuten.co.jp/?r=")) > 0) {
                    url = contentLow[i].substring(
                        contentLow[i].indexOf("http://pmrd.rakuten.co.jp/?r="),
                        contentLow[i].indexOf("\"",
                            contentLow[i].indexOf("http://pmrd.rakuten.co.jp/?r=")));
                  } else {
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
                  } else {
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
          case Define.PSITE_CODE_WAR: // WARAU
            if (contentLow[i].indexOf("go.warau.jp/") >= 0
                || contentLow[i].indexOf("www.warau.jp/tp/") >= 0) {
              String url = contentLow[i].substring(contentLow[i].indexOf("http"));
              urlList.add(Utille.trimWhitespace(url));
            }
            break;
          case Define.PSITE_CODE_SUG: // すぐたま
          case Define.PSITE_CODE_NTM: // ネットマイル
            if (contentLow[i].indexOf("mag.netmile.co.jp/c") >= 0
                || contentLow[i].indexOf(".sugutama.jp/") >= 0) {
              String url = contentLow[i].substring(contentLow[i].indexOf("http"));
              urlList.add(Utille.trimWhitespace(url));
            }
            break;
          case Define.PSITE_CODE_PST: // ポイントスタジアム
            if (contentLow[i]
                .indexOf("http://www.point-stadium.com/mclick.asp?pid=clonecopyfake&i=2400271") >= 0) {
              String url = contentLow[i].substring(contentLow[i].indexOf("http"));
              urlList.add(Utille.trimWhitespace(url));
            }
            break;
          case Define.PSITE_CODE_PMO: // ポイントモンキー
            String indexKey2 = "http://poimon.jp/tp/";
            if (contentLow[i].indexOf(indexKey2) >= 0) {
              String url = "";
              if (Define.CONTENT_TYPE_HTML.equals(contentType)) {
                url = contentLow[i].substring(
                    contentLow[i].indexOf(indexKey2),
                    contentLow[i].indexOf("\"",
                        contentLow[i].indexOf(indexKey2)));
              } else {
                url = contentLow[i].substring(contentLow[i].indexOf("http"));
              }
              urlList.add(Utille.trimWhitespace(url));
            }
            break;
          case Define.PSITE_CODE_PIL: // ポイントアイランド
            if (contentLow[i]
                .indexOf("http://www.point-island.com/mcd.asp?p=wclonecopyfake&m=k4624611") >= 0) {
              String url = contentLow[i].substring(contentLow[i].indexOf("http"));
              urlList.add(Utille.trimWhitespace(url));
            }
            break;
          case Define.PSITE_CODE_PIC: // PointInCome
            String url = "";
            if (Define.CONTENT_TYPE_HTML.equals(contentType)) {
              String indexKey = "http://pointi.jp/al/click_special.php?site_no=";
              if (contentLow[i].indexOf(indexKey) >= 0) {
                url = contentLow[i].substring(
                    contentLow[i].indexOf(indexKey),
                    contentLow[i].indexOf("\"",
                        contentLow[i].indexOf(indexKey)));
              }
            } else {
              String indexKey = "http://pointi.jp/al/click_mail_magazine.php?no=";
              if (contentLow[i].indexOf(indexKey) >= 0) {
                url = contentLow[i].substring(contentLow[i].indexOf("http"));
              }
            }
            if (url.length() > 0) {
              urlList.add(Utille.trimWhitespace(url));
            }
            break;
          case Define.PSITE_CODE_KOZ: // こずかい
            if (contentLow[i].indexOf("http://okodukai.jp/q/") >= 0) {
              String url2 = contentLow[i].substring(contentLow[i].indexOf("http"));
              urlList.add(Utille.trimWhitespace(url2));
            }
            break;
          case Define.PSITE_CODE_I2I: // i2i
            if (contentLow[i].indexOf("http://point.i2i.jp/click/") >= 0) {
              String url2 = contentLow[i].substring(contentLow[i].indexOf("http"));
              urlList.add(Utille.trimWhitespace(url2));
            }
            break;
          case Define.PSITE_CODE_HAP: // ハピタス
            if (contentLow[i].indexOf("http://r34.smp.ne.jp/u/No/") >= 0) {
              String url2 = contentLow[i].substring(contentLow[i].indexOf("http"));
              urlList.add(Utille.trimWhitespace(url2));
            }
            break;
          case Define.PSITE_CODE_CRI: // ちょびリッチ
            String url2 = "";
            if (Define.CONTENT_TYPE_HTML.equals(contentType)) {
              String indexKey = "http://www.chobirich.com/cm/ad";
              if (contentLow[i].indexOf(indexKey) >= 0) {
                url2 = contentLow[i].substring(
                    contentLow[i].indexOf(indexKey),
                    contentLow[i].indexOf("\"",
                        contentLow[i].indexOf(indexKey)));
              }
            } else {
              String indexKey = "http://www.chobirich.com/cm/om/";
              if (contentLow[i].indexOf(indexKey) >= 0) {
                url2 = contentLow[i].substring(contentLow[i].indexOf("http"));
              }
            }
            if (url2.length() > 0) {
              urlList.add(Utille.trimWhitespace(url2));
            }
            break;
          case Define.PSITE_CODE_CIT: // チャンスイット
            if (contentLow[i].indexOf("http://c.chanceit.jp/c/") >= 0
            || contentLow[i].indexOf("&s=") >= 0) {
              String url3 = contentLow[i].substring(contentLow[i].indexOf("http"));
              urlList.add(Utille.trimWhitespace(url3));
            }
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
    } else if (content instanceof Multipart) {
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
          } else if (header.indexOf(Define.CONTENT_TYPE_HTML) >= 0) {
            mContentType = Define.CONTENT_TYPE_HTML;
          } else if (header.indexOf(Define.CONTENT_TYPE_MULTI) >= 0) {
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
