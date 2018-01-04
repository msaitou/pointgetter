package pointGet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import pointGet.common.Define;
import pointGet.common.Utille;
import pointGet.mission.Mission;
import pointGet.mission.cit.CITBase;
import pointGet.mission.cri.CRIBase;
import pointGet.mission.dmy.DMYBase;
import pointGet.mission.ecn.ECNBase;
import pointGet.mission.gen.GENBase;
import pointGet.mission.gmy.GMYBase;
import pointGet.mission.gpo.GPOBase;
import pointGet.mission.hap.HAPBase;
import pointGet.mission.i2i.I2IBase;
import pointGet.mission.lfm.LFMBase;
import pointGet.mission.mob.MOBBase;
import pointGet.mission.mop.MOPBase;
import pointGet.mission.osa.OSABase;
import pointGet.mission.pex.PEXBase;
import pointGet.mission.pic.PICBase;
import pointGet.mission.pil.PILBase;
import pointGet.mission.pmo.PMOBase;
import pointGet.mission.pst.PSTBase;
import pointGet.mission.pto.PTOBase;
import pointGet.mission.rin.RINBase;
import pointGet.mission.sug.SUGBase;
import pointGet.mission.war.WARBase;

/**
 * get point from the point site
 *
 * @author 雅人
 *
 */
public class WebClicker extends PointGet {
  public static boolean isDoingFlag = false;
  public static long lastRoopTime = 0;
  private static final String loadFilePath = "pGetWeb.properties";
  @SuppressWarnings("unused")
  private static boolean secondFlg = false, thirdFlg = false, forthFlg = false, testFlag = false;
  private static ArrayList<Mission> missionList = new ArrayList<Mission>();
  private static int subRetryCnt = 0;
  private static String[] wordList = null, visitSites = null;
  private static String[] defoSiteList = new String[] {
      Define.PSITE_CODE_PIC, // PointInCome
      Define.PSITE_CODE_SUG, // SUGUTAMA
      Define.PSITE_CODE_CIT, // CHANCEIT
      Define.PSITE_CODE_PMO, // PointMonkey
      Define.PSITE_CODE_PIL, // PointIsland
      Define.PSITE_CODE_PST, // PointStudium
      Define.PSITE_CODE_RIN, // raktuten
      Define.PSITE_CODE_I2I, // i2i
      Define.PSITE_CODE_MOP, // moppi
      Define.PSITE_CODE_ECN, // ecnavi
      Define.PSITE_CODE_PEX, // pex
      Define.PSITE_CODE_GEN, // gendama
      Define.PSITE_CODE_GMY, // GetMoney
      Define.PSITE_CODE_OSA, // osaifu
      Define.PSITE_CODE_PTO, // pointtown
      Define.PSITE_CODE_MOB, // MOBATOKU
      Define.PSITE_CODE_CRI, // ちょびリッチ
      Define.PSITE_CODE_HAP, // ハピタス
      Define.PSITE_CODE_WAR, // ワラウ
      Define.PSITE_CODE_DMY, // ドットマネー
      Define.PSITE_CODE_LFM, // ライフメディア
      Define.PSITE_CODE_GPO, // Gポイント
  };

  protected static void init(String[] args) {
    PointGet.init(WebClicker.class.getSimpleName());
    _setLogger("log4jweb.properties", WebClicker.class);
    // 設定ファイルをローカル変数に展開する
    Properties loadProps = Utille.getProp(loadFilePath);
    if (loadProps.isEmpty()) {
      return;
    }
    wordList = loadProps.getProperty("wordList").split(",");
    if (args.length > 0) {
      if (args[0].equals("1")) {
        secondFlg = true;
      }
      else if (args[0].equals("2")) {
        thirdFlg = true;
      }
      else if (args[0].equals("3")) {
        forthFlg = true;
      }
    }
    if (args.length > 1) {
      if (Arrays.asList(defoSiteList).contains(args[1])) {
        visitSites = new String[] { args[1] };
      }
      else {
        visitSites = new String[0];
      }
    }
    else {
      visitSites = defoSiteList;
    }
    // testFlag =true;
  }

  /**
   * @param args
   */
  public static void exeSite(String[] args, ArrayList<String> missionArr) {
    init(args);
    logg.info("======exeSiteStart======");
    for (String site : visitSites) {
      if (thirdFlg && Arrays.asList(new String[] { Define.PSITE_CODE_ECN, // ecnavi
          Define.PSITE_CODE_PEX, // pex
      // Define.PSITE_CODE_GEN,// gendama
      }).contains(site)) {
        continue;
      }
      else if ((secondFlg || thirdFlg) && Arrays.asList(new String[] { Define.PSITE_CODE_GMY, // GetMoney
          Define.PSITE_CODE_RIN, // raktuten
          Define.PSITE_CODE_I2I,// i2i
      }).contains(site)) {
        continue;
      }
      goToClickSite(site, missionArr);
      missionArr.clear();
    }
    roopMisssion(missionList);
    logg.info("======exeSite End ======");
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    init(args);
    logg.info("======mainStart======");
    ArrayList<String> missionArr = new ArrayList<String>();
    for (String site : visitSites) {
      if (thirdFlg && Arrays.asList(new String[] { Define.PSITE_CODE_ECN, // ecnavi
          Define.PSITE_CODE_PEX, // pex
      // Define.PSITE_CODE_GEN,// gendama
      }).contains(site)) {
        continue;
      }
      else if ((secondFlg || thirdFlg) && Arrays.asList(new String[] { Define.PSITE_CODE_GMY, // GetMoney
          Define.PSITE_CODE_RIN, // raktuten
          Define.PSITE_CODE_I2I,// i2i
      }).contains(site)) {
        continue;
      }
      goToClickSite(site, missionArr);
      missionArr.clear();
    }
    roopMisssion(missionList);
    logg.info("======mainEND======");
  }

  private static void roopMisssion(ArrayList<Mission> missionList) {
    // roop
    if (missionList.size() > 0) {
      logg.info("roopStart!!");
      WebDriver driver = getWebDriver();
      int CompCnt = 0;
      while (true) {
        for (Mission mission : missionList) {
          if (mission.isCompFlag()) {
            logg.info("Complete!!");
            CompCnt++;
            continue;
          }
          mission.roopMission(driver);
        }
        if (isDoingFlag) {
          logg.info("roopEnd!! isDoingFlag:true");
          break;
        }
        else {
          if (CompCnt >= missionList.size()) {
            logg.info("roopEnd!! isDoingFlag:false");
            break;
          }
          logg.info("CompCnt:" + CompCnt + "  missionList.size():" + missionList.size());
          Utille.sleep(306000);
        }
        logg.info("again!!");
      }
//      driver.close();// if not null then execute Quit!
      driver.quit();
    }
  }

  /**
   *
   * @param site
   * @param strFlag
   * @param mission
   */
  public static void sub(String site, String strFlag, String mission) {
    init(new String[] { strFlag });
    logg.info("======subStart======");
    ArrayList<String> missionArr = new ArrayList<String>();
    missionArr.add(mission);
    goToClickSite(site, missionArr);
    roopMisssion(missionList);
    logg.info("======subEnd======");
  }

  /**
   *
   * @param siteType
   */
  private static void goToClickSite(String siteType, ArrayList<String> missionArr) {
    logg.info("■■■■■■missionSite[" + siteType + "]START■■■■■■");
    try {
      switch (siteType) {
        case Define.PSITE_CODE_GMY:
          GMYBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_GEN:
          GENBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_ECN:
          ECNBase.goToClick(logg, commonProps, missionArr, missionList, wordList, Dbase);
          break;
        case Define.PSITE_CODE_MOP:
          MOPBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_PEX:
          PEXBase.goToClick(logg, commonProps, missionArr, missionList, wordList, Dbase);
          break;
        case Define.PSITE_CODE_OSA:
          OSABase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_RIN:
          RINBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_I2I:
          I2IBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_PTO:
          PTOBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_MOB:
          MOBBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_PIC:
          PICBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_SUG:
          SUGBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_PST:
          PSTBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_PIL:
          PILBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_CIT:
          CITBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_PMO:
          PMOBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_CRI:
          CRIBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_HAP:
          HAPBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_WAR:
          WARBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_DMY:
          DMYBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_LFM:
          LFMBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        case Define.PSITE_CODE_GPO:
          GPOBase.goToClick(logg, commonProps, missionArr, Dbase);
          break;
        default:
      }
    } catch (WebDriverException e) {
      e.printStackTrace();
      logg.error("-WebDriverException--------------webclicker-----");
      logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 50000));
      logg.error("-WebDriverException-------------------");
      if (subRetryCnt++ < 4) {
        //リトライ
        goToClickSite(siteType, missionArr);
      }
    } catch (Throwable e) {
      e.printStackTrace();
      logg.error("-Throwable-------------webclicker------");
      logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 50000));
      logg.error("-Throwable-------------------");
    }
    subRetryCnt = 0;
    logg.info("■■■■■■missionSite[" + siteType + "]END■■■■■■");
  }
}
