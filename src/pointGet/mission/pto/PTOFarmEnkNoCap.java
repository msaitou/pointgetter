package pointGet.mission.pto;

import java.util.Map;

import org.apache.log4j.Logger;

public class PTOFarmEnkNoCap extends PTOFarmEnk {
  /**
   * @param logg
   */
  public PTOFarmEnkNoCap(Logger logg, Map<String, String> cProps) {
    super(logg, cProps);
    this.skipCapFlag = true;
  }
}
