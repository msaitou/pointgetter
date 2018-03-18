package pointGet.mission.pic;

import java.util.Map;

import org.apache.log4j.Logger;

public class PICFarmEnkNoCap extends PICFarmEnk {
  /**
   * @param logg
   */
  public PICFarmEnkNoCap(Logger logg, Map<String, String> cProps) {
    super(logg, cProps);
    this.skipCapFlag = true;
  }
}
