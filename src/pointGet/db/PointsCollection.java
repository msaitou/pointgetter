/**
 *
 */
package pointGet.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.bson.Document;

import pointGet.common.MailCommon;
import pointGet.common.Utille;

import com.mongodb.DBObject;
import com.mongodb.client.MongoCursor;
import com.mongodb.util.JSON;

/**
 * @author saitou
 *
 */
public class PointsCollection {
  final static String COLLECTION_NAME_POINTS = "points";
  final static String COLLECTION_NAME_ACHIEVEMENT = "achievements";
  protected Dbase Dbase = null;
  protected String strDate = null,
      strDateTime = null;

  /**
   * constractor
   * @param db
   */
  public PointsCollection(Dbase db) {
    Dbase = db;
    strDate = Utille.getNowTimeStr("yyyy-MM-dd");
    strDateTime = Utille.getNowTimeStr("yyyy-MM-dd HH:mm:ss");
  }

  //	// テスト用
  //	public HashMap<String, ArrayList<String>> getCollectionData(String cName) {
  //		@SuppressWarnings("rawtypes")
  //		MongoCursor cursor = iterator.iterator();
  //		while (cursor.hasNext()) {
  //			Document dc = (Document) cursor.next();
  //			String word = "";
  //			ArrayList<String> means = new ArrayList<String>();
  //			for (String e : dc.keySet()) {
  //				System.out.println(e + " [" + dc.get(e));
  //				if (e.equals("word")) {
  //					word = (String) dc.get(e);
  //				}
  //				else if (e.equals("mean")) {
  //					means.add((String) dc.get(e));
  //				}
  //			}
  //			collectData.put(word, means);
  //		}
  //		System.out.println(doc);
  //		client.close();
  //
  //		return collectData;
  //	}

  /**
   *
   * @param sCode
   * @return
   */
  public List<HashMap<String, Object>> getPointList() {
    List<HashMap<String, Object>> lm = new ArrayList<HashMap<String, Object>>();
    Function<MongoCursor<Document>, Double> f = (cursor) -> {
      Double total = 0.0;
      while (cursor.hasNext()) {
        Document doc = (Document) cursor.next();
        for (String k : doc.keySet()) {
          System.out.println("key [" + k);
          if (!k.equals("_id")) {
            total += doc.getDouble(k);
          }
        }
      }
      return (double) Math.round(total * 10) / 10;
    };
    //    Double total = (Double) Dbase.accessDb("find", COLLECTION_NAME_POINTS, f);
    // 検索情報設定
    Map<String, Object> cParams = new HashMap<String, Object>();
    cParams.put("sort", (DBObject) JSON.parse("{'time':-1}"));
    cParams.put("limit", 2);

    @SuppressWarnings("unchecked")
    List<HashMap<String, Object>> rec = (List<HashMap<String, Object>>) Dbase.accessDb("find",
        COLLECTION_NAME_POINTS, cParams, f);
    return rec;
  }

  public void putPointsData(Map<String, Double> m) {
    // 検索情報設定
    Map<String, Object> cParams = new HashMap<String, Object>();
    cParams.put("cond", (DBObject) JSON.parse("{'Date':'" + strDate + "'}"));
    cParams.put("limit", 2);
    //		Dbase.accessDb("update", COLLECTION_NAME_POINTS, ((a) -> {
    Dbase.accessDb("update", COLLECTION_NAME_POINTS, cParams, ((a) -> {
      Document doc = new Document();
      m.forEach((key, total) -> {
        doc.append(key, total);
      });
      doc.append("Date", strDate);
      doc.append("time", strDateTime);
      return doc;
    }));
  }

  public void putAchievementData() {
    //    // やっぱMongoCusrser使わないと取れないかも。
    //    // pointsから各サイトの値を抽出
    //    //		Double total = Dbase.getCollectionData2(COLLECTION_NAME_POINTS);
    //    Function<MongoCursor<Document>, Double> f = (cursor) -> {
    //      Double total = 0.0;
    //      while (cursor.hasNext()) {
    //        Document doc = (Document) cursor.next();
    //        for (String k : doc.keySet()) {
    //          System.out.println("key [" + k);
    //          if (!k.equals("_id")) {
    //            total += doc.getDouble(k);
    //          }
    //        }
    //      }
    //      return (double) Math.round(total * 10) / 10;
    //    };
    //    //		Double total = (Double) Dbase.accessDb("find", COLLECTION_NAME_POINTS, f);
    //    // 検索情報設定
    //    Map<String, Object> cParams = new HashMap<String, Object>();
    //    cParams.put("sort", (DBObject) JSON.parse("{'time':-1}"));
    //    cParams.put("limit", 2);
    //
    //    @SuppressWarnings("unchecked")
    //    List<HashMap<String, Object>> rec = (List<HashMap<String, Object>>) Dbase.accessDb("find",
    //        COLLECTION_NAME_POINTS, cParams, f);
    //    Map<String, Double> md = new HashMap<String, Double>();
    //    Double total = 0.0, lastTotal = 0.0, diff = 0.0;
    //    // 直前のポイントと今のポイントの2レコードが入ってるいるはず
    //    for (HashMap<String, Object> hm : rec) {
    //      if (md.size() == 0) {
    //        // それぞれのdiffの元と今のTOTALを出す。
    //        for (Map.Entry<String, Object> m : hm.entrySet()) {
    //          String k = m.getKey();
    //          System.out.println("key [" + k);
    //          if (!Arrays.asList(new String[] { "time", "_id", "Date",
    //          }).contains(k)) {
    //            Double d = (Double) m.getValue();
    //            total += d;
    //            md.put(k, d);
    //          }
    //        }
    //        total = (double) Math.round(total * 10) / 10;
    //      }
    //      else {
    //        // それぞれのdiffを計算
    //        for (Map.Entry<String, Object> m : hm.entrySet()) {
    //          String k = m.getKey();
    //          System.out.println("key [" + k);
    //          if (!Arrays.asList(new String[] { "time", "_id", "Date",
    //          }).contains(k)) {
    //            Double d = (Double) m.getValue();
    //            lastTotal += d;
    //            Double nowd = 0.0;
    //            if (md.containsKey(k)) {
    //              nowd = md.get(k);
    //            }
    //            Double sa = nowd - d;
    //            md.put(k, (double) Math.round(sa * 10) / 10);
    //          }
    //        }
    //        diff = (double) Math.round((total - lastTotal) * 10) / 10;
    //      }
    //    }
    //
    //    // Total算出★
    //    // Achievementから最後のレコードを抽出
    //    // Totalを取得
    //    // nowTotalとの差分を算出★
    //    Map<String, String> m = new HashMap<String, String>();
    //    m.put("total", total.toString());
    //    m.put("diff", diff.toString());
    //    md.forEach((s, d) -> m.put(s, d.toString()));
    //    m.put("time", strDateTime);
    Map<String, String> m = getAchievementData();
    // 今の時刻★
    // 保存内容：Total,実行日時,差分
    Dbase.accessDb("insert", COLLECTION_NAME_ACHIEVEMENT, ((a) -> {
      Document doc = new Document();
      m.forEach((key, str) -> {
        if (key.indexOf(":now") < 0 || Arrays.asList(new String[] { "time", "total", "diff", "time", "_id", "Date",
        }).contains(key)) {
          doc.append(key, str);
        }
      });
      return doc;
    }));
    //		保存
  }

  public void sendMailAchievmentDayly(Dbase Dbase, String[] pointSitelist) {
    Map<String, String> md = getAchievementData();
    String contents = "";
    String ls = "\n";
    contents += "sum:" + md.get("total") + "(" + md.get("diff") + ")" + ls + ls;

    // [] をリスト型に変換 ログインできなかったりしたやつもメールで確認したくなったので
    List<String> plist = new ArrayList<String>(Arrays.asList(pointSitelist));

    //    plist.remove("c");
    //    String[] temp = (String[]) plist.toArray(new String[plist.size()]);
    //    for (String str : temp) {
    //      System.out.println(str);
    //    }

    for (Map.Entry<String, String> m : md.entrySet()) {
      String k = m.getKey();
      System.out.println("key [" + k);
      if (!Arrays.asList(new String[] { "total", "diff" }).contains(k)) {
        if (k.indexOf(":now") < 0) {

          // :nowが含まれていないkの場合（その3レターサイトの今日の差分の値が入ってる）
          for (Map.Entry<String, String> md2 : md.entrySet()) {
            String k2 = md2.getKey();
            System.out.println("key2 [" + k2);
            //              if (!Arrays.asList(new String[] { "total", "diff" }).contains(k)) {
            if (k2.equals(k + ":now")) {
              contents += k + ":" + md2.getValue() + "(" + m.getValue() + ")" + ls;
              plist.remove(k);
              break;
            }
            //              }
          }
        }
      }
    }
    if (plist.size() > 0) {
      contents += ls + ls;
      for (String notFoundSite : plist) {
        contents += notFoundSite + ": is not Found! " + ls;
      }
    }
    new MailCommon(Dbase).send(contents, strDate + "分の稼ぎ");
  }

  private Map<String, String> getAchievementData() {
    // やっぱMongoCusrser使わないと取れないかも。
    // pointsから各サイトの値を抽出
    //    Double total = Dbase.getCollectionData2(COLLECTION_NAME_POINTS);
    Function<MongoCursor<Document>, Double> f = (cursor) -> {
      Double total = 0.0;
      while (cursor.hasNext()) {
        Document doc = (Document) cursor.next();
        for (String k : doc.keySet()) {
          System.out.println("key [" + k);
          if (!k.equals("_id")) {
            total += doc.getDouble(k);
          }
        }
      }
      return (double) Math.round(total * 10) / 10;
    };
    //    Double total = (Double) Dbase.accessDb("find", COLLECTION_NAME_POINTS, f);
    // 検索情報設定
    Map<String, Object> cParams = new HashMap<String, Object>();
    cParams.put("sort", (DBObject) JSON.parse("{'time':-1}"));
    cParams.put("limit", 2);

    @SuppressWarnings("unchecked")
    List<HashMap<String, Object>> rec = (List<HashMap<String, Object>>) Dbase.accessDb("find",
        COLLECTION_NAME_POINTS, cParams, f);
    Map<String, Double> md = new HashMap<String, Double>();
    Double total = 0.0, lastTotal = 0.0, diff = 0.0;
    // 直前のポイントと今のポイントの2レコードが入ってるいるはず
    for (HashMap<String, Object> hm : rec) {
      if (md.size() == 0) {
        // それぞれのdiffの元と今のTOTALを出す。
        for (Map.Entry<String, Object> m : hm.entrySet()) {
          String k = m.getKey();
          System.out.println("key [" + k);
          if (!Arrays.asList(new String[] { "time", "_id", "Date",
          }).contains(k)) {
            Double d = (Double) m.getValue();
            total += d;
            md.put(k + ":now", d);
          }
        }
        total = (double) Math.round(total * 10) / 10;
      }
      else {
        // それぞれのdiffを計算
        for (Map.Entry<String, Object> m : hm.entrySet()) {
          String k = m.getKey();
          System.out.println("key [" + k);
          if (!Arrays.asList(new String[] { "time", "_id", "Date",
          }).contains(k)) {
            Double d = (Double) m.getValue();
            lastTotal += d;
            Double nowd = 0.0;
            if (md.containsKey(k + ":now")) {
              nowd = md.get(k + ":now");
            }
            Double sa = nowd - d;
            md.put(k, (double) Math.round(sa * 10) / 10);
          }
        }
        diff = (double) Math.round((total - lastTotal) * 10) / 10;
      }
    }

    // Total算出★
    // Achievementから最後のレコードを抽出
    // Totalを取得
    // nowTotalとの差分を算出★
    Map<String, String> m = new HashMap<String, String>();
    m.put("total", total.toString());
    m.put("diff", diff.toString());
    md.forEach((s, d) -> m.put(s, d.toString()));
    m.put("time", strDateTime);
    return m;
  }
}
