package pointGet.mission.gpo;

import java.util.Map;

import org.apache.log4j.Logger;

public class GPOFarmEnkNoCap extends GPOFarmEnk {

  /**
   * @param logg
   */
  public GPOFarmEnkNoCap(Logger logg, Map<String, String> cProps) {
    super(logg, cProps);
    this.skipCapFlag = true;
  }
}
