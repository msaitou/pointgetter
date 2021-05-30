package pointGet.mission.sug.old;

import java.util.Map;

import org.apache.log4j.Logger;

public class SUGGetParkNoCap extends SUGGetPark {
  /**
   * @param logg
   */
  public SUGGetParkNoCap(Logger logg, Map<String, String> cProps) {
    super(logg, cProps);
    this.skipCapFlag = true;
  }
}
