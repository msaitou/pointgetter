package pointGet.common;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.bson.Document;

import pointGet.db.Dbase;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * @author saitou
 *
 */
public class MailCommon {

  // mail config variable
  private static Map<String, String> mailConf = new HashMap<String, String>();
  //エンコード指定
  private static final String ENCODE = "ISO-2022-JP";


  /**
   * 
   */
  public MailCommon(Dbase Dbase) {
//    Map<String, HashMap<String, String>> targetSitesTmp = new HashMap<String, HashMap<String, String>>();
    Map<String, Object> cParams = new HashMap<String, Object>();
    cParams.put("cond", JSON.parse("{'type':'mail_send'}"));
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
//        // メールをとってくる対象サイトコートリスト
//        case "targetSiteCode":
//          if (value instanceof List) {
//            List docs = (List) value;
//            siteCodes = new String[docs.size()];
//            for (int i = 0; i < docs.size(); i++) {
//              siteCodes[i] = docs.get(i).toString();
//              System.out.println("siteCodes::::" + siteCodes[i]);
//            }
//          }
//          break;
        // メールの取得設定
        case "host":
        case "user":
        case "port":
        case "from_add":
        case "to_add":
        case "password":
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
//            targetSitesTmp.put(key, siteConf);
          }
      }
    }
//    // 対象サイトだけに絞る
//    targetSitesTmp.forEach((k, val) -> {
//      if (Arrays.asList(siteCodes).contains(k)) {
//        targetSites.put(k, targetSitesTmp.get(k));
//      }
//    });
  }

  public static void main(final String[] args) {
    String propPath = "pointGet.properties";
    Properties loadProps = null;
    loadProps = Utille.getProp(propPath);
    if (loadProps.isEmpty()) {
      return;
    }
//    Dbase = new Dbase(loadProps);
    //メール送付
    new MailCommon(new Dbase(loadProps)).send("日本の皆様");
  }

  //ここからメール送付に必要なSMTP,SSL認証などの設定
  public void send(String contents) {
    send(contents, "無題");
  }
  public void send(String contents, String subject) {
    if (mailConf.isEmpty()) {
      System.out.println("mailconf is not found");
      return ;
    }
    final Properties props = new Properties();

    // SMTPサーバーの設定。ここではgooglemailのsmtpサーバーを設定。
    props.setProperty("mail.smtp.host", mailConf.get("host"));

    // SSL用にポート番号を変更。
    props.setProperty("mail.smtp.port", mailConf.get("port"));

    // タイムアウト設定
    props.setProperty("mail.smtp.connectiontimeout", "60000");
    props.setProperty("mail.smtp.timeout", "60000");

    // 認証
    props.setProperty("mail.smtp.auth", "true");

    // SSLを使用するとこはこの設定が必要。
    props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.setProperty("mail.smtp.socketFactory.fallback", "false");
    props.setProperty("mail.smtp.socketFactory.port",  mailConf.get("port"));

    //propsに設定した情報を使用して、sessionの作成
    final Session session = Session.getInstance(props, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(mailConf.get("user"), mailConf.get("password"));
      }
    });

    // ここからメッセージ内容の設定。上記で作成したsessionを引数に渡す。
    final MimeMessage message = new MimeMessage(session);

    try {
      final Address addrFrom = new InternetAddress(
          mailConf.get("from_add"), "送信者の表示名", ENCODE);
      message.setFrom(addrFrom);

      final Address addrTo = new InternetAddress(mailConf.get("to_add"),
          "受信者の表示名", ENCODE);
      message.addRecipient(Message.RecipientType.TO, addrTo);

      // メールのSubject
      message.setSubject(subject, ENCODE);

      // メール本文。
      message.setText(contents, ENCODE);

      // その他の付加情報。
      message.addHeader("X-Mailer", "blancoMail 0.1");
      message.setSentDate(new Date());
    } catch (MessagingException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    // メール送信。
    try {
      Transport.send(message);
    } catch (AuthenticationFailedException e) {
      // 認証失敗
      e.printStackTrace();
    } catch (MessagingException e) {
      // smtpサーバへの接続失敗
      e.printStackTrace();

    }
  }
}
