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

import com.mongodb.DBObject;
import com.mongodb.client.MongoCursor;
import com.mongodb.util.JSON;

import pointGet.common.Utille;

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
    // やっぱMongoCusrser使わないと取れないかも。
    // pointsから各サイトの値を抽出
    //		Double total = Dbase.getCollectionData2(COLLECTION_NAME_POINTS);
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
    //		Double total = (Double) Dbase.accessDb("find", COLLECTION_NAME_POINTS, f);
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
            md.put(k, d);
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
            if (md.containsKey(k)) {
              nowd = md.get(k);
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

    // 今の時刻★
    // 保存内容：Total,実行日時,差分
    Dbase.accessDb("insert", COLLECTION_NAME_ACHIEVEMENT, ((a) -> {
      Document doc = new Document();
      m.forEach((key, str) -> {
        doc.append(key, str);
      });
      return doc;
    }));
    //		保存
  }
}
