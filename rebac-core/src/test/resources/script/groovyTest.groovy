package groovy

import xyz.wanghongtao.rebac.service.gateway.DatabaseGateway
import xyz.wanghongtao.rebac.util.SpringContextUtils


class groovyTest {

  def fn() {
    def bean = SpringContextUtils.getBean(DatabaseGateway.class)
    return bean.keyList()
  }

}
