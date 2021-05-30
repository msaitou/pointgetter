package pointGet.mission.hap;

import java.util.Map;

import org.apache.log4j.Logger;

public class HAPPointResearchNoCap extends HAPPointResearch {

  /**
   * @param logg
   */
  public HAPPointResearchNoCap(Logger logg, Map<String, String> cProps) {
    super(logg, cProps);
    this.skipCapFlag = true;
  }
}
