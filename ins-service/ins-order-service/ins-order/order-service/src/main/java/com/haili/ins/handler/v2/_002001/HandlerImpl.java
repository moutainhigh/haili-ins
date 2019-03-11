package com.haili.ins.handler.v2._002001;

import com.haili.ins.common.exception.ServiceException;
import com.haili.ins.common.utils.JSONUtil;
import com.haili.ins.feign.MemberFeign;
import com.haili.ins.invoke.InvokeHelper;
import com.haili.ins.invoke.InvokeLogger;
import com.haili.ins.invoke.bussiness.BusinessHandler;
import com.haili.ins.invoke.dto.InvokeParameter;
import com.haili.ins.invoke.dto.InvokeRequest;
import com.haili.ins.invoke.dto.InvokeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jack
 *
 */
@Service("002001.v2")
public class HandlerImpl implements BusinessHandler {
	
	@Autowired
	private MemberFeign memberFeign;

	
	@Override
	public InvokeResponse invokeBusiness(InvokeParameter invokeParam) throws ServiceException {
		InvokeLogger.info("_002001开始");
		InvokeResponse response = new InvokeResponse();

		try {
			Request request = JSONUtil.toObject(invokeParam.getDataMsg(), Request.class);
			// 请求参数校验
			InvokeHelper.validate(request);

			InvokeRequest ir = new InvokeRequest();
			ir.setDataMsg("{\"name\":\"111\"}");
			ir.setOriginNo("002");
			ir.setSerCode("001001");
			ir.setTargetNo("001");

			memberFeign.invoke("v2",ir);
			response.buildSuccResp();

		}catch(ServiceException se) {
			throw se;
		}catch (Exception e) {
			InvokeLogger.error("_002001失败", e);
			response.buildFailResp();
		}

		InvokeLogger.info("_002001结束");
		return response;
	}
}
