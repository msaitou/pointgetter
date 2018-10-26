package pointGet.mission.cms;

import java.util.Map;

import org.apache.log4j.Logger;

public class CMSFarmEnkNoCap extends CMSFarmEnk {

  /**
   * @param logg
   */
  public CMSFarmEnkNoCap(Logger logg, Map<String, String> cProps) {
    super(logg, cProps);
    this.skipCapFlag = true;
  }
}
