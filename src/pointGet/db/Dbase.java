/**
 * 
 */
package pointGet.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.util.JSON;

/**
 * @author saitou
 *
 */
public class Dbase {
	protected static String databaseName = "", host = "";

	protected MongoClient client = null;
	protected MongoDatabase db = null;
	protected MongoCollection<Document> coll = null;

	/**
	 * コンストラクタ
	 */
	public Dbase(Properties prop) {
		if (prop.containsKey("db.name")) {
			databaseName = prop.getProperty("db.name");
		}
		if (prop.containsKey("db.host")) {
			host = prop.getProperty("db.host");
		}
	}

	//	@SuppressWarnings("unchecked")
	//	public <R, T> Object findData(Function<T, R> f) {
	//		FindIterable<Document> iterator = coll.find();
	//		MongoCursor<Document> cursor = iterator.iterator();
	//		return f.apply((T) cursor);
	//	}

	/**
	 * 
	 * @param cParams
	 * @param f
	 * @return
	 */
	public <R, T> Object findData(Map<String, Object> cParams, Function<T, R> f) {
		Bson cond = (Bson) JSON.parse("{}"), sort = (Bson) JSON.parse("{}");
		int limit = 0;
		if (cParams.containsKey("cond")) {
			cond = (Bson) cParams.get("cond");
		}
		if (cParams.containsKey("sort")) {
			sort = (Bson) cParams.get("sort");
		}
		if (cParams.containsKey("limit")) {
			limit = Integer.parseInt(cParams.get("limit").toString());
		}
		FindIterable<Document> iterator = coll.find(cond).sort(sort).limit(limit);
		MongoCursor<Document> cursor = iterator.iterator();
		List<HashMap<String, Object>> lm = new ArrayList<HashMap<String, Object>>();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			System.out.println("doc [" + doc);
			HashMap<String, Object> m = new HashMap<String, Object>();
			for (String k : doc.keySet()) {
				System.out.println("key [" + k);
				if (!k.equals("_id")) {
					m.put(k, doc.get(k));
				}
			}
			lm.add(m);
		}
		return lm;
	}

	//
	//	public <R> Double getCollectionData2(String cName) {
	//		// MongoDBサーバに接続
	//		MongoClient client = null;
	//		FindIterable<Document> iterator = null;
	//		Double total = 0.0;
	//		try {
	//			// MongoDBサーバに接続
	//			client = new MongoClient(host, 27017);
	//			// 利用するDBを取得
	//			MongoDatabase db = client.getDatabase(databaseName);
	//			MongoCollection<Document> coll = db.getCollection(cName);
	//			//			long cnt = coll.count();
	//			//			System.out.println(cName + "の件数[" + cnt);
	//			iterator = coll.find();
	//
	//			MongoCursor<Document> cursor = iterator.iterator();
	//			while (cursor.hasNext()) {
	//				Document doc = cursor.next();
	//				for (String k : doc.keySet()) {
	//					System.out.println("key [" + k);
	//					if (!k.equals("_id")) {
	//						total += doc.getDouble(k);
	//					}
	//				}
	//			}
	//			total = (double) Math.round(total * 10) / 10;
	//		} catch (Throwable e) {
	//			e.printStackTrace();
	//		} finally {
	//			client.close();
	//		}
	//		return total;
	//	}

	public <R, T> void updateData(Map<String, Object> cParams, Function<T, R> f) {
		Document doc = (Document) f.apply(null);
		Document finder = null;
		Bson cond = (Bson) JSON.parse("{}");
		if (cParams.containsKey("cond")) {
			cond = (Bson) cParams.get("cond");
		}
		FindIterable<Document> docc = coll.find(cond);
		finder = docc.first();
		if (finder != null) {
			ObjectId id = null;
			for (String key : finder.keySet()) {
				System.out.println(key + " [" + finder.get(key));
				if (key.equals("_id")) {
					id = finder.getObjectId(key);
					break;
				}
			}
			UpdateOptions option = new UpdateOptions().upsert(true);
			coll.updateOne(Filters.eq("_id", id), new Document("$set", doc), option);
		}
		else {
			coll.insertOne(doc);
		}
	}

	public <R, T> void insertData(Function<T, R> f) {
		Document doc = (Document) f.apply(null);
		coll.insertOne(doc);
	}

	/**
	 * 
	 * @param <T>
	 * @param cName
	 * @param cParams
	 * @param f
	 * @return
	 */
	public <R, T> Object accessDb(String type, String cName, Function<T, R> f) {
		return accessDb(type, cName, null, f);
	}

	/**
	 * 
	 * @param <T>
	 * @param cName
	 * @param cParams
	 * @param f
	 * @return
	 */
	public <R, T> Object accessDb(String type, String cName, Map<String, Object> cParams, Function<T, R> f) {
		client = null;
		db = null;
		coll = null;
		try {
			// MongoDBサーバに接続
			client = new MongoClient(host, 27017);
			// 利用するDBを取得
			db = client.getDatabase(databaseName);
			// 利用するコレクションを取得
			coll = db.getCollection(cName);
			if ("find".equals(type)) {
				return findData(cParams, f);
			}
			else if ("insert".equals(type)) {
				insertData(f);
			}
			else if ("update".equals(type)) {
				updateData(cParams, f);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			coll = null;
			db = null;
			client.close();
		}
		return null;
	}

}
