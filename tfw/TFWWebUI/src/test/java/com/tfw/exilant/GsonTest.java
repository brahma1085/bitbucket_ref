package com.tfw.exilant;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.exilant.tfw.pojo.output.ApplicationFlow;
import com.google.gson.Gson;
import com.tfw.exilant.ITAP.TestingFlowController;

public class GsonTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "TFWWebAppl-servlet.xml", "applicationContext.xml",
				"dataAccessContext-local.xml" });
		TestingFlowController mainService = (TestingFlowController) context.getBean("testingFlowController");
		ApplicationFlow application = mainService.getApplicationFlow(1);
		Gson gson = new Gson();
		String json = gson.toJson(application);
		System.out.println(json);
	}

}
