package pointGet.mission.lfm;

import java.util.Map;

import org.apache.log4j.Logger;

public class LFMFarmEnkNoCap extends LFMFarmEnk {
  /**
   * @param logg
   */
  public LFMFarmEnkNoCap(Logger logg, Map<String, String> cProps) {
    super(logg, cProps);
    this.skipCapFlag = true;
  }
}
