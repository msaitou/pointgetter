package pointGet.db;

public class ConfigCollection {
	final static String COLLECTION_NAME_CONFIGS = "configs";
	protected Dbase Dbase = null;

	public ConfigCollection(Dbase db) {
		Dbase = db;
	}
}
