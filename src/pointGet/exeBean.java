package pointGet;

import pointGet.common.Utille;

public class exeBean {

	/**
	 * 関数レベルでの実行エントリーポイント
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		//doTest();
		String site = "";
		String strFlag = "0";
		String mission = "";
		if (args.length > 0) {
			mission = args[0];
			if (args.length > 1) {
				strFlag = args[1];
			}
		}
		else {

		}
		site = Utille.getSiteCode(mission);
		if (site.equals(mission)) {
			WebClicker.main(new String[] { strFlag, site });
		}
		else {
			WebClicker.sub(site, strFlag, mission);
		}
	}


}
