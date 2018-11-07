package com.dstz.demo.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.bpm.api.engine.action.cmd.ActionCmd;
import com.dstz.demo.core.dao.DemoDao;
import com.dstz.demo.core.manager.DemoManager;
import com.dstz.demo.core.manager.DemoSubManager;
import com.dstz.demo.core.model.Demo;
import com.dstz.demo.core.model.DemoSub;
/**
 * 案例 Manager处理实现类
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-08-24 18:06:04
 */
@Service("demoManager")
public class DemoManagerImpl extends BaseManager<String, Demo> implements DemoManager{
	@Resource
	DemoDao demoDao;
	@Resource
	DemoSubManager demoSubMananger;

	@Override
	public Demo get(String entityId) {
		Demo demo = super.get(entityId);
		if(demo == null) return null;
		
		List<DemoSub> demoSublist = demoSubMananger.getByFk(entityId);
		demo.setDemoSubList(demoSublist);
		
		return demo;
	}
	
	
	

	/**
	 * URL 表单案例
	 */
	@Override
	public void saveDemoJson(ActionCmd actionCmd) {
		String bizKey = actionCmd.getBusinessKey();
		JSONObject object = actionCmd.getBusData();
		Demo demo = JSON.toJavaObject(object, Demo.class);
		
		if(StringUtil.isEmpty(bizKey)) {
			String id = IdUtil.getSuid();
			actionCmd.setBusinessKey(id);
			demo.setId(id);
			demoDao.create(demo);
		}else {
			demoDao.update(demo);
		}
	}
	
}
