package com.sssoft.isatt.ui.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.sssoft.isatt.data.pojo.output.ApplicationFlow;
import com.sssoft.isatt.ui2.controller.TestingFlowController2;

public class GsonTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "ISATTUI-servlet.xml", "applicationContext.xml",
				"dataAccessContext-local.xml" });
		TestingFlowController2 mainService = (TestingFlowController2) context.getBean("testingFlowController2");
		ApplicationFlow application = mainService.getApplicationFlow(1);
		Gson gson = new Gson();
		String json = gson.toJson(application);
		System.out.println(json);
	}

}
