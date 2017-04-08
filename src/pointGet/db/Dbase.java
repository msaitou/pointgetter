/**
 * 
 */
package pointGet.db;

import java.util.Map.Entry;
import java.util.Properties;
import java.util.function.Supplier;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;

/**
 * @author saitou
 *
 */
public class Dbase {
	protected static String databaseName = "", host = "";

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

	public FindIterable<Document> getCollectionData(String cName) {
		// MongoDBサーバに接続
		MongoClient client = null;
		FindIterable<Document> iterator = null;
		try {
			// MongoDBサーバに接続
			client = new MongoClient(host, 27017);
			// 利用するDBを取得
			MongoDatabase db = client.getDatabase(databaseName);
			MongoCollection<Document> coll = db.getCollection(cName);
			//			long cnt = coll.count();
			//			System.out.println(cName + "の件数[" + cnt);
			iterator = coll.find();
		} catch (Throwable e) {

		} finally {
			client.close();
		}
		return iterator;
	}

	public <R> Double getCollectionData2(String cName) {
		// MongoDBサーバに接続
		MongoClient client = null;
		FindIterable<Document> iterator = null;
		Double total = 0.0;
		try {
			// MongoDBサーバに接続
			client = new MongoClient(host, 27017);
			// 利用するDBを取得
			MongoDatabase db = client.getDatabase(databaseName);
			MongoCollection<Document> coll = db.getCollection(cName);
			//			long cnt = coll.count();
			//			System.out.println(cName + "の件数[" + cnt);
			String key = "_id";
			String param = "0";
			Document query = new Document(key, param);
			iterator = coll.find();
			MongoCursor<Document> cursor = iterator.iterator();
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				
					System.out.println( "keyset[" +doc.entrySet());
				for (Entry<String, Object> k : doc.entrySet()) {
					System.out.println( "key [" +k.getKey());
					System.out.println( "kval [" +k.getValue());
					if (!k.getKey().equals("_id")) {
						total += doc.getDouble(k.getKey());
					}
				}
			}
			total = (double) Math.round(total * 10) / 10;
		} catch (Throwable e) {

			e.printStackTrace();
		} finally {
			client.close();
		}
		return total;
	}

	public <R> void updateData(Supplier<R> f, String cName, Object cond) {
		MongoClient client = null;
		try {
			// MongoDBサーバに接続
			client = new MongoClient(host, 27017);
			// 利用するDBを取得
			MongoDatabase db = client.getDatabase(databaseName);
			MongoCollection<Document> coll = db.getCollection(cName);
			Document doc = (Document) f.get();
			Document finder = null;
			if (cond == null) {
				FindIterable<Document> docc = coll.find();
				finder = docc.first();
			}
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
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
	}

	public <R> void insertData(Supplier<R> f, String cName) {
		MongoClient client = null;
		try {
			// MongoDBサーバに接続
			client = new MongoClient(host, 27017);
			// 利用するDBを取得
			MongoDatabase db = client.getDatabase(databaseName);
			MongoCollection<Document> coll = db.getCollection(cName);
			Document doc = (Document) f.get();
			coll.insertOne(doc);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
	}
}
