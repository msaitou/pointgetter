package pointGet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PBatEntry extends PointGet {

	private static final String xmlFile = "pgBatFileProp.xml";

	public static void main(String[] args) {
		_setLogger("log4jweb.properties", WebClicker.class);
		// 現在日時を取得して、その時間(24形式)を利用
		String nowHour = Utille.getNowTimeStr("HH");
		System.out.println("nowHour:" + nowHour);
		String key = "H" + nowHour;
		// XMLを変数に展開して、時間をキーに値を抽出
		String fileStr = readFileToString(xmlFile);
		Map<String, ArrayList<String>> m = xmlReader(fileStr, key);
		for (Map.Entry<String, ArrayList<String>> s : m.entrySet()) {
			System.out.println("sKey:" + s.getKey());
			for (String n : s.getValue()) {
				System.out.println("\\t mission[" + n + "]");
			}
		}
		System.out.println("m:" + m.toString());

		LinkedHashMap<String, ArrayList<String>> missionMap = new LinkedHashMap<String, ArrayList<String>>();
		if (m.get(key).size() > 0) {
			logg.info("◆◆◆◆◆◆Start!!◆◆◆◆◆◆◆");
			// 抽出した値に対応するMissionを順次(順番)に直列に実行
			for (String strMission : m.get(key)) {
				// mail,point以外なら
				if ("Mail".equals(strMission)) {
					missionMap.put("mail", null);
					break;
				} else if ("Point".equals(strMission)) {
					missionMap.put("point", null);
					break;
				} else {
					// サイトをカテゴリー分けする
					String site = Utille.getSiteCode(strMission);
					if (!missionMap.containsKey(site)) {
						missionMap.put(site, new ArrayList<String>());
					}
					missionMap.get(site).add(strMission);
				}
			}
			// カテゴリーごとにmain関数をコール
			for (Map.Entry<String, ArrayList<String>> map : missionMap.entrySet()) {
				if (map.getValue() != null) {
					WebClicker.isDoingFlag = true;
					WebClicker.exeSite(new String[] { "0", map.getKey() }, map.getValue());
				} else {
					System.out.println(map.getKey() + " is not regist");
				}
			}
			WebClicker.isDoingFlag = false;
			WebClicker.exeSite(new String[] { "0", "000" }, new ArrayList<String>());
			logg.info("◆◆◆◆◆◆END!!◆◆◆◆◆◆◆");
		} else {
			System.out.println("no planed mission ;-<");
		}
	}

	/**
	 * callerから渡されたxmlを配列に格納する処理
	 *
	 * data xmlリスト
	 * return resdata[] ：gds種別 [][] ：インデックス [][][] ：PNRNO
	 */
	private static Map<String, ArrayList<String>> xmlReader(String data, String key) {

		Map<String, ArrayList<String>> resData = new HashMap<String, ArrayList<String>>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		Document document = null;
		try {
			documentBuilder = factory.newDocumentBuilder();
			document = documentBuilder.parse(new ByteArrayInputStream(data.getBytes("UTF-8")));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}

		Element root = document.getDocumentElement();
		// ルート要素のノード名を取得する
		System.out.println("ノード名：" + root.getNodeName());

		// ルート要素の属性を取得する
		System.out.println("ルート要素の属性：" + root.getAttribute("name"));

		// ルート要素の子ノードを取得する
		NodeList rootChildren = root.getChildNodes();

		System.out.println("子要素の数：" + rootChildren.getLength());

		// GDSの配列を作成

		// 子要素の数分回す
		for (int i = 0; i < rootChildren.getLength(); i++) {
			Node node = rootChildren.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;

				// XMLの値をGDS毎にチェック
				if (element.getNodeName().equals(key)) {
					String gdsName = element.getNodeName();
					if (!resData.containsKey(gdsName)) {
						resData.put(gdsName, new ArrayList<String>());
					}
					NodeList missionChildren = node.getChildNodes();
					for (int j = 0; j < missionChildren.getLength(); j++) {
						Node node2 = missionChildren.item(j);
						if (node2.getNodeType() == Node.ELEMENT_NODE) {
							Element ele2 = (Element) node2;
							if (ele2.getNodeName().equals("Mission")) {
								resData.get(gdsName).add(ele2.getTextContent());
								System.out.println(gdsName + "：" + ele2.getTextContent());
							}
						}
					}
					System.out.println("------------------");
				}
			}
		}
		System.out.println("targetArr：" + resData.toString());
		return resData;
	}

	/**
	 * 読み込んだファイルの内容を文字列で返す
	 * @param fPath
	 * @return
	 */
	public static String readFileToString(String fPath) {
		String fileStr = "";
		File readFile = new File(fPath);
		if (readFile.exists() && readFile.isFile() && readFile.canRead()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(readFile));
				String str = null;
				StringBuffer fileRead = new StringBuffer("");
				while ((str = br.readLine()) != null) {
					fileRead.append(str);
				}
				br.close();
				fileStr = fileRead.toString();
				System.out.println("■------------------------------------");
				System.out.println("fPath: " + fPath);
				System.out.println("■------------------------------------");
				System.out.println("fileStr: " + fileStr);
				System.out.println("■------------------------------------");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("fPath[" + fPath + "] is not found");
		}
		return fileStr;
	}
}
