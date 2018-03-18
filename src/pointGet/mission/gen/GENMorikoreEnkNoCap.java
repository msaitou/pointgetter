package pointGet.mission.gen;

import java.util.Map;

import org.apache.log4j.Logger;

public class GENMorikoreEnkNoCap extends GENMorikoreEnk {
  /**
   * @param logg
   */
  public GENMorikoreEnkNoCap(Logger logg, Map<String, String> cProps) {
    super(logg, cProps);
    this.skipCapFlag = true;
  }
}
