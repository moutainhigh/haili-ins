package com.haili.ins.handler.v2._001001;

import com.haili.ins.common.exception.ServiceException;
import com.haili.ins.common.utils.JSONUtil;
import com.haili.ins.core.invoke.InvokeHelper;
import com.haili.ins.core.invoke.InvokeLogger;
import com.haili.ins.core.invoke.bussiness.BusinessHandler;
import com.haili.ins.core.invoke.dto.InvokeParameter;
import com.haili.ins.core.invoke.dto.InvokeResponse;
import com.haili.ins.feign.MemberFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jack
 *
 */
@Service("001001.v2")
public class HandlerImpl implements BusinessHandler {
	
	@Autowired
	private MemberFeign memberFeign;

	
	@Override
	public InvokeResponse invokeBusiness(InvokeParameter invokeParam) throws ServiceException {
		InvokeLogger.info("_001001开始");
		InvokeResponse response = new InvokeResponse();

		try {
			Request request = JSONUtil.toObject(invokeParam.getDataMsg(), Request.class);
			// 请求参数校验
			InvokeHelper.validate(request);


			response.buildSuccResp();

		}catch(ServiceException se) {
			throw se;
		}catch (Exception e) {
			InvokeLogger.error("_001001失败", e);
			response.buildFailResp();
		}

		InvokeLogger.info("_001001结束");
		return response;
	}
}