/**
* Data access object that support mutiple locale.
*
* The target model is interface or abtract class.
* Each concrete class is implemented abstract class for a specific language.
*
* This utility support manage factory and repositor class via
* language parameter.
*
* Document will come soon at https://phamsodiep.blogspot.comm
*
* @author phamsodiep
*
* @Website:
*   https://github.com/phamsodiep or 
*   https://phamsodiep.blogspot.comm
*
* License: GNU General Public License v3.0
*/
package org.nhomkinh.hoangtuan.web.customer.utils;


import java.util.Map;
import java.util.HashMap;
import static org.springframework.beans.BeanUtils.instantiateClass;


public class InheritanceDAO<R, M> {
  private Map<Class, R> repositories;


  public InheritanceDAO(
    Object... maps
  ) {
    int i;
    int n = maps.length;

    this.repositories = new HashMap<Class, R>();

    for (i = 0; i < n; i += 2) {
      this.repositories.put((Class)maps[i], (R)maps[i+1]);
    }
  }

  public R getRepository(Class clazz) {
    return this.repositories.get(clazz);
  }

  public <CM extends M> CM createModelObject(Class<CM> clazz) {
    return (CM)instantiateClass(clazz);
  }
}

