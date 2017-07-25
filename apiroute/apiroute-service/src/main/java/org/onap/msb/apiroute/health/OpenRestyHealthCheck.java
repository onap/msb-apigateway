package org.onap.msb.apiroute.health;

import org.apache.commons.lang3.StringUtils;
import org.onap.msb.apiroute.wrapper.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.health.HealthCheck;

public class OpenRestyHealthCheck extends HealthCheck {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OpenRestyHealthCheck.class);
	private String CHECK_IP="127.0.0.1";
	private String CHECK_PORT="80";
	private String CHECK_URL = "http://"+CHECK_IP+":"+CHECK_PORT+"/api/microservices/v1/apiRoute/discoverInfo";
	
	@Override
	protected Result check() throws Exception {
		// TODO Auto-generated method stub
		
		if(!StringUtils.isBlank(System.getenv("HTTP_OVERWRITE_PORT")))
		{
			CHECK_PORT=System.getenv("HTTP_OVERWRITE_PORT");
			CHECK_URL = "http://"+CHECK_IP+":"+CHECK_PORT+"/api/microservices/v1/apiRoute/discoverInfo";
			LOGGER.info("check openresty URL:"+CHECK_URL);
		}
		
		int resultStatus = HttpClientUtil.httpGetStatus(CHECK_URL);

		if (resultStatus == 200) {
			return Result.healthy();
		} else {
			return Result
					.unhealthy("check openresty fail:" + resultStatus);
		}

	}

}
