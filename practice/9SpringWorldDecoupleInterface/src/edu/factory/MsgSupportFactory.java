package edu.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import edu.decoupleinterface.MsgRender;

public class MsgSupportFactory {
	private static BeanFactory beanFactory = new XmlBeanFactory(
			new ClassPathResource("applicationContext.xml"));

	public static MsgRender getMsgRender() {
		return (MsgRender) beanFactory.getBean("render");
	}
}