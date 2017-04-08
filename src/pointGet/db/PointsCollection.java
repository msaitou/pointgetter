/**
 * 
 */
package pointGet.db;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

/**
 * @author saitou
 *
 */
public class PointsCollection {
	final static String COLLECTION_NAME_POINTS = "points";
	final static String COLLECTION_NAME_ACHIEVEMENT = "achievements";
	protected Dbase Dbase = null;

	/**
	 * @param prop
	 */
	public PointsCollection(Dbase db) {
		Dbase = db;
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

	public void putPointsData(Map<String, Double> m) {
		Dbase.updateData((() -> {
			Document doc = new Document();
			m.forEach((key, total) -> {
				doc.append(key, total);
			});
			return doc;
		}), COLLECTION_NAME_POINTS, null);
	}

	public void putAchievementData() {
		// やっぱMongoCusrser使わないと取れないかも。
		// pointsから各サイトの値を抽出
		Double total = Dbase.getCollectionData2(COLLECTION_NAME_POINTS);

		// Total算出★
		Double achive = 0.0;
		// Achievementから最後のレコードを抽出
		// Totalを取得
		// nowTotalとの差分を算出★
		Map<String, String> m = new HashMap<String, String>();
		Date date = new Date();
		m.put("time", date.toString());
		m.put("total", total.toString());
		m.put("diff", String.valueOf(total - achive));
		// 今の時刻★
		// 保存内容：Total,実行日時,差分
		Dbase.updateData((() -> {
			Document doc = new Document();
			m.forEach((key, str) -> {
				doc.append(key, str);
			});
			return doc;
		}), COLLECTION_NAME_ACHIEVEMENT, null);
		//		保存
	}
}
