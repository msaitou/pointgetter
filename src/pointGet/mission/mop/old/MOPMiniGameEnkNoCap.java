package pointGet.mission.mop.old;

import java.util.Map;

import org.apache.log4j.Logger;

public class MOPMiniGameEnkNoCap extends MOPMiniGameEnk {

  /**
   * @param logg
   */
  public MOPMiniGameEnkNoCap(Logger logg, Map<String, String> cProps) {
    super(logg, cProps);
    this.skipCapFlag = true;
  }

}
