package pointGet;

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
			// ■■■■■ECnavi■■■■■
			//		mission = Define.strECNChinjyu;
			//		mission = Define.strECNChirachi;
			//		mission = Define.strECNClickBokin;
			//		mission = Define.strECNDron;
			//		mission = Define.strECNGaragara;
			//		mission = Define.strECNNews;
			//		mission = Define.strECNSearchBokin;
			//		mission = Define.strECNTellmeWhich;
			//		mission = Define.strECNWebSearche;
			//		// ■■■■■げん玉■■■■■
			//		mission = Define.strGENClickBanner;
			//		mission = Define.strGENMorimoriKakome;
			//		mission = Define.strGENPointStar;
			//		mission = Define.strGENShindan;
			//		mission = Define.strGENUranai;
			//		// ■■■■■げっとま■■■■■
			//		mission = Define.strGMYChirachi;
			//		mission = Define.strGMYClickBanner;
			//		mission = Define.strGMYShindan;
			//		// ■■■■■i2i■■■■■
			//		mission = Define.strI2ISeiza;
			//		// ■■■■■moppy■■■■■
			//		mission = Define.strMOPChyosatai;
			//		mission = Define.strMOPClickBanner;
//			mission = Define.strMOPQuiz;
			//		mission = Define.strMOPShindan;
			//		mission = Define.strMOPUranai;
			//		mission = Define.strMOPNanyoubi;
			//		mission = Define.strMOPAnzan;
			//		// ■■■■■osaifu■■■■■
			//		mission = Define.strOSAClickBanner;
			//		mission = Define.strOSAQuiz;
			//		mission = Define.strOSAShindan;
			//		mission = Define.strOSAUranai;
			//		mission = Define.strOSANanyoubi;
			//		mission = Define.strOSAAnzan;
			//		// ■■■■■PEX■■■■■
			//		mission = Define.strPEX4quiz;
			//		mission = Define.strPEXAnswer;
			//		mission = Define.strPEXChirachi;
			//		mission = Define.strPEXClickBanner;
			//		mission = Define.strPEXMekutte;
			//		mission = Define.strPEXMitukete;
			//		mission = Define.strPEXNews;
			//		mission = Define.strPEXPectan;
			//		mission = Define.strPEXSearch;
			//		// ■■■■■rakuten■■■■■
			//		mission = Define.strRINClickBanner;
			//		// ■■■■■PointTown■■■■■
			//		mission = Define.strPTOClickCorner;
//					mission = Define.strPTOKuji;
					mission = Define.strPTODaily;
			//		mission = Define.strPTOUranai;
			strFlag = "0";
		}

		site = getSiteCode(mission);
		WebClicker.sub(site, strFlag, mission);
//		WebClicker.main(new String[]{strFlag, "pto"});
	}

	private static String getSiteCode(String mission) {
		String site = "";
		switch (mission.substring(0, 3).toLowerCase()) {
			case Define.PSITE_CODE_R01:
			case Define.PSITE_CODE_RIN:
				site = Define.PSITE_CODE_RIN;
				break;
			case Define.PSITE_CODE_PEX:
				site = Define.PSITE_CODE_PEX;
				break;
			case Define.PSITE_CODE_PTO:
				site = Define.PSITE_CODE_PTO;
				break;
			case Define.PSITE_CODE_GMY:
				site = Define.PSITE_CODE_GMY;
				break;
			case Define.PSITE_CODE_GEN:
				site = Define.PSITE_CODE_GEN;
				break;
			case Define.PSITE_CODE_ECN:
				site = Define.PSITE_CODE_ECN;
				break;
			case Define.PSITE_CODE_MOP:
				site = Define.PSITE_CODE_MOP;
				break;
			case Define.PSITE_CODE_OSA:
				site = Define.PSITE_CODE_OSA;
				break;
			case Define.PSITE_CODE_I2I:
				site = Define.PSITE_CODE_I2I;
				break;
		}
		return site;
	}

	private static void doTest() {
		// // TODO 数値の比較
		// String str = "5 - 8 ÷ 7";
		// // String str = text;
		// String regex = "(\\d) ([-×+÷]) (\\d) ([-×+÷]) (\\d)";
		// Pattern p = Pattern.compile(regex);
		// Matcher m = p.matcher(str);
		// System.out.println("hajimari");
		// if (m.find()) {
		// String ans = Utille.calcAnzan(m);
		// System.out.println(ans);
		// }
		//// if (m.find()) {
		//// String strAfterDayNum = m.group(1);
		//// String selectYoubi = Utille.getNanyoubi(strAfterDayNum);
		//// System.out.println("なんにちです [" + selectYoubi+"]");
		//// // logg.info("なんにちです [" + matchstr+"]");
		//// }
		// System.out.println("owari");
	}
}
