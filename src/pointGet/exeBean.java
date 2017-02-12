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
//			// ■■■■■ECnavi■■■■■
//			mission = Define.strECNChinjyu;
//			mission = Define.strECNChirachi;
//			mission = Define.strECNClickBokin;
//			mission = Define.strECNDron;
//			mission = Define.strECNGaragara;
//			mission = Define.strECNNews;
//			mission = Define.strECNSearchBokin;
//			mission = Define.strECNTellmeWhich;
//			mission = Define.strECNWebSearche;
//			// ■■■■■げん玉■■■■■
//			mission = Define.strGENClickBanner;
//			mission = Define.strGENMorimoriKakome;
//			mission = Define.strGENPointStar;
//			mission = Define.strGENShindan;
//			mission = Define.strGENUranai;
//			// ■■■■■げっとま■■■■■
//			mission = Define.strGMYChirachi;
//			mission = Define.strGMYClickBanner;
//			mission = Define.strGMYShindan;
//			mission = Define.strGMYPriceChyosatai;
//			mission = Define.strGMYToidas;
//			// ■■■■■i2i■■■■■
//			mission = Define.strI2ISeiza;
//			// ■■■■■moppy■■■■■
//			mission = Define.strMOPChyosatai;
//			mission = Define.strMOPClickBanner;
//			mission = Define.strMOPQuiz;
//			mission = Define.strMOPShindan;
//			mission = Define.strMOPUranai;
//			mission = Define.strMOPNanyoubi;
//			mission = Define.strMOPAnzan;
//			mission = Define.strMOPChirachi; //XMLiretenai
//			// ■■■■■osaifu■■■■■
//			mission = Define.strOSAClickBanner;
//			mission = Define.strOSAQuiz;
//			mission = Define.strOSAShindan;
//			mission = Define.strOSAUranai;
//			mission = Define.strOSANanyoubi;
//			mission = Define.strOSAAnzan;
//			// ■■■■■PEX■■■■■
//			mission = Define.strPEX4quiz;
//			mission = Define.strPEXAnswer;
//			mission = Define.strPEXChirachi;
//			mission = Define.strPEXClickBanner;
//			mission = Define.strPEXMekutte;
//			mission = Define.strPEXMitukete;
//			mission = Define.strPEXNews;
//			mission = Define.strPEXPectan;
//			mission = Define.strPEXSearch;
//			// ■■■■■rakuten■■■■■
//			mission = Define.strRINClickBanner;
//			// ■■■■■PointTown■■■■■
//			mission = Define.strPTOClickCorner;
//			mission = Define.strPTOKuji;
//			mission = Define.strPTODaily;
//			mission = Define.strPTOUranai;
//			// ■■■■■mobatoku■■■■■
//			mission = Define.strMOBAnzan;
//			mission = Define.strMOBNanyoubi;
//			mission = Define.strMOBQuiz;
//			mission = Define.strMOBClickBanner;
//			// ■■■■■PointInCome■■■■■
//			mission = Define.strPICUranai;
//			mission = Define.strPICClickBanner;
//			mission = Define.strPICPriceChyosatai;
//			// ■■■■■SUGUTAMA■■■■■
//			mission = Define.strSUGUranai;
//			mission = Define.strSUGQuiz;
//			// ■■■■■PointStadium■■■■■
//			mission = Define.strPSTUranai;
//			mission = Define.strPSTQuiz;
//			// ■■■■■PointIsland■■■■■
//			mission = Define.strPILUranai;
//			mission = Define.strPILQuiz;
//			mission = Define.strPILClickBanner;
//			// ■■■■■CHANCEIT■■■■■
//			mission = Define.strCITPriceChyosatai;
//			mission = Define.strCITShindan;
			mission = Define.strCITToidas;
//			// ■■■■■PointMonkey■■■■■
//			mission = Define.strPMOChyosatai;
//			// サイト単位
//			mission = Define.PSITE_CODE_RIN;
//			mission = Define.PSITE_CODE_PEX;
//			mission = Define.PSITE_CODE_PTO;
//			mission = Define.PSITE_CODE_GMY;
//			mission = Define.PSITE_CODE_GEN;
//			mission = Define.PSITE_CODE_ECN;
//			mission = Define.PSITE_CODE_MOP;
//			mission = Define.PSITE_CODE_OSA;
//			mission = Define.PSITE_CODE_I2I;
//			mission = Define.PSITE_CODE_MOB;
//			mission = Define.PSITE_CODE_CIT;
//			mission = Define.PSITE_CODE_CRI;
//			mission = Define.PSITE_CODE_HAP;
//			mission = Define.PSITE_CODE_KOZ;
//			mission = Define.PSITE_CODE_PIC;
//			mission = Define.PSITE_CODE_NTM;
//			mission = Define.PSITE_CODE_PIL;
//			mission = Define.PSITE_CODE_PMO;
//			mission = Define.PSITE_CODE_PNY;
//			mission = Define.PSITE_CODE_SUG;
//			mission = Define.PSITE_CODE_WAR;
//			mission = Define.PSITE_CODE_PST;

			strFlag = "0";
		}
		site = Utille.getSiteCode(mission);
		if (site.equals(mission)) {
			WebClicker.main(new String[] { strFlag, site });
		}
		else {
			WebClicker.sub(site, strFlag, mission);
		}
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
