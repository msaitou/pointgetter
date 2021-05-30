package pointGet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.bson.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import pointGet.common.Utille;
import pointGet.db.Dbase;

/**
 * @author saitou
 *
 */
public abstract class PointGet {
  protected static String propPath = "pointGet.properties";
  protected static Properties loadProps = null;
  protected static Dbase Dbase = null;
  private static String[] siteCodeList = null;
  // log
  protected static Logger logg = null;
  // PointGet common properties
  protected static Map<String, HashMap<String, String>> pGetProps = new HashMap<String, HashMap<String, String>>();
  protected static Map<String, String> commonProps = new HashMap<String, String>();

  protected static void init(String clsName) {
    _loadProps();
    System.out.println("[" + clsName + "]");
    //		// 対象のサイトを取得
    //		siteCodeList = loadProps.getProperty("SiteCodeList").split(",");
    //		String[] attrList = loadProps.getProperty("AttrList").split(",");
    //		// PointGet config variable
    //		for (int i = 0; i < siteCodeList.length; i++) {
    //			String siteCode = siteCodeList[i];
    //			if (siteCode != null && !siteCode.equals("")) {
    //				HashMap<String, String> siteConf = new HashMap<String, String>();
    //				for (int j = 0; j < attrList.length; j++) {
    //					if (loadProps.containsKey(siteCode + "." + attrList[j])) {
    //						siteConf.put(attrList[j], loadProps.getProperty(siteCode + "." + attrList[j]));
    //					}
    //				}
    //				pGetProps.put(siteCode, siteConf);
    //			}
    //		}
  }

  /**
   * ログクラスの設定
   */
  protected static void _setLogger(String strPropFile, Class<?> clsObj) {
    PropertyConfigurator.configure(strPropFile);
    logg = Utille.setLogger(clsObj);
  }

  protected static boolean isExistEle(WebDriver driver, String selector) {
    return Utille.isExistEle(driver, selector, logg);
  }

  /**
  *
  * @param driver
  * @param selector
  * @return
  */
  protected static boolean isExistEle(WebDriver driver, String selector, boolean showFlag) {
    return Utille.isExistEle(driver, selector, showFlag, logg);
  }

  /**
   *
   * @param ele
   * @return
   */
  protected static boolean isExistEle(List<WebElement> ele) {
    return Utille.isExistEle(ele, logg);
  }

  /**
   *
   * @param ele
   * @param index
   * @return
   */
  protected static boolean isExistEle(List<WebElement> ele, int index) {
    boolean is = isExistEle(ele.get(index));
    logg.info(index + ":[" + is + "]");
    return is;
  }

  /**
   *
   * @param ele
   * @return
   */
  protected static boolean isExistEle(WebElement ele) {
    List<WebElement> eleL = new ArrayList<WebElement>();
    eleL.add(ele);
    return isExistEle(eleL);
  }

  /**
   * @return
   */
  public static WebDriver getWebDriver() {
    return Utille.getWebDriver(commonProps.get("geckopath"), commonProps.get("ffprofile"));
  }

  /**
   * 設定ファイルをローカル変数に展開する
   */
  private static void _loadProps() {
    loadProps = Utille.getProp(propPath);
    if (loadProps.isEmpty()) {
      return;
    }
    Dbase = new Dbase(loadProps);
    if (loadProps.containsKey("clientCode")) {
      commonProps.put("clientCode", loadProps.getProperty("clientCode"));
    }
    Map<String, Object> cParams = new HashMap<String, Object>();
    cParams.put("cond",
        JSON.parse("{'type':'client', 'client':'" + commonProps.get("clientCode") + "'}"));
    @SuppressWarnings("unchecked")
    List<HashMap<String, Object>> rec = (List<HashMap<String, Object>>) Dbase.accessDb("find",
        "configs", cParams, null);
    if (null == rec || rec.isEmpty() || null == rec.get(0)) {
      System.out.println("not found Conigs..");
      return;
    }
    System.out.println("wwwwwwww");
    System.out.println(rec);
    System.out.println("wwwwwwww");
    for (Map.Entry<String, Object> field : rec.get(0).entrySet()) {
      String key = field.getKey();
      Object value = field.getValue();
      switch (key) {
        case "geckopath":
        case "ffprofile":
          commonProps.put(key, value.toString());
          break;
        default:
          if (value instanceof List) {
            List docs = (List) value;
            siteCodeList = new String[docs.size()];
            for (int i = 0; i < docs.size(); i++) {
              siteCodeList[i] = docs.get(i).toString();
              System.out.println("siteCodes::::" + siteCodeList[i]);
            }
          }
          break;
      }
    }
    cParams = new HashMap<String, Object>();
    cParams.put("cond", JSON.parse("{'type':'login'}"));
    @SuppressWarnings("unchecked")
    List<HashMap<String, Object>> rec1 = (List<HashMap<String, Object>>) Dbase.accessDb("find",
        "configs", cParams, null);
    for (Map.Entry<String, Object> field : rec1.get(0).entrySet()) {
      String key = field.getKey();
      Object value = field.getValue();
      if (Arrays.asList(siteCodeList).contains(key)) {
        if (value instanceof Document) {
          Document doc = (Document) value;
          HashMap<String, String> siteConf = new HashMap<String, String>();
          doc.forEach((k, val) -> {
            siteConf.put(k, (String) val);
          });
          pGetProps.put(key, siteConf);
        }
      }
    }
  }
}
