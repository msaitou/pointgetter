package pointGet;

public class exeBean {

	/**
	 * 関数レベルでの実行エントリーポイント
	 *
	 * @param args
	 */
	public static void main(String[] args) {
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

		// String[] params = new String[]{"0",
		// Define.PSITE_CODE_ECN, // ecnavi
		// Define.PSITE_CODE_PEX, // pex
		// Define.PSITE_CODE_GEN, // gendama
		// Define.PSITE_CODE_GMY, // GetMoney
		// Define.PSITE_CODE_RIN, // raktuten
		// Define.PSITE_CODE_I2I, // i2i
		// Define.PSITE_CODE_MOP, // moppi
		// Define.PSITE_CODE_OSA, // osaifu
		// Define.PSITE_CODE_PTO, // pointtown
		// };

		// if(args.length > 0) {
		// params = args;
		// }
		// WebClicker.main(params);
		WebClicker.sub(
				// Define.PSITE_CODE_ECN, // ecnavi
				// Define.PSITE_CODE_PEX, // pex
				// Define.PSITE_CODE_GEN, // gendama
				// Define.PSITE_CODE_GMY, // GetMoney
				// Define.PSITE_CODE_RIN, // raktuten
				// Define.PSITE_CODE_I2I, // i2i
				 Define.PSITE_CODE_MOP, // moppi
//				Define.PSITE_CODE_OSA, // osaifu
				// Define.PSITE_CODE_PTO, // pointtown
				"0",
				// ★★★★★★ECnavi★★★★★★
				// Define.strECNChinjyu
				// Define.strECNChirachi
				// Define.strECNClickBokin
				// Define.strECNDron
				// Define.strECNGaragara
				// Define.strECNNews
				// Define.strECNSearchBokin
				// Define.strECNTellmeWhich
				// Define.strECNWebSearche
				// ★★★★★★GENDAMA★★★★★★
				// Define.strGENClickBanner
				// Define.strGENMorimoriKakome
				// Define.strGENPointStar
				// Define.strGENShindan
				// Define.strGENUranai
				// ★★★★★★GetMoney★★★★★★
				// Define.strGMYChirachi
				// Define.strGMYClickBanner
				// Define.strGMYShindan
				// ★★★★★★I2I★★★★★★
				// Define.strI2ISeiza
				// ★★★★★★Moppy★★★★★★
				// Define.strMOPChyosatai
				// Define.strMOPClickBanner
				// Define.strMOPQuiz
				// Define.strMOPShindan
				// Define.strMOPNanyoubi
				// Define.strMOPUranai
				 Define.strMOPAnzan
				// ★★★★★★OSAIFU★★★★★★
				// Define.strOSAClickBanner
				// Define.strOSAQuiz
				// Define.strOSAShindan
				// Define.strOSAUranai
//				Define.strOSAAnzan
		// Define.strOSANanyoubi
		// ★★★★★★PEX★★★★★★
		// Define.strPEX4quiz
		// Define.strPEXAnswer
		// Define.strPEXChirachi
		// Define.strPEXClickBanner
		// Define.strPEXMekutte
		// Define.strPEXMitukete
		// Define.strPEXNews
		// Define.strPEXPectan
		// Define.strPEXSearch
		// ★★★★★★楽天★★★★★★
		// Define.strRINClickBanner
		);
	}
}
