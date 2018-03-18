package pointGet.mission.osa;

import java.util.Map;

import org.apache.log4j.Logger;

public class OSAFarmEnkNoCap extends OSAFarmEnk {

  /**
   * @param logg
   */
  public OSAFarmEnkNoCap(Logger logg, Map<String, String> cProps) {
    super(logg, cProps);
    this.skipCapFlag = true;
  }
}
