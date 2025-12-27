package edu.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class XMLBeanFactory implements BeanFactory {
	private static String classname;
	private static Map singleTonMap = new HashMap();
	static {
		Properties properties = new Properties();
		String singleton = null;
		String id = null;
		try {
			properties.load(XMLBeanFactory.class.getClassLoader()
					.getResourceAsStream("spring.properties"));
			id = properties.getProperty("id");
			classname = properties.getProperty("classname");
			singleton = properties.getProperty("singleton");
			if ("true".equals(singleton)) {
				Object object = Class.forName(classname).newInstance();
				singleTonMap.put(id, object);
				System.out.println(singleTonMap);
			}
		} catch (Exception e) {
			System.out.println("IOException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		}
	}

	@Override
	public Object getBean(String key) {
		Object object = singleTonMap.get(key);
		if (object != null)
			return object;
		try {
			object = Class.forName(classname).newInstance();
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
		return object;
	}
}
