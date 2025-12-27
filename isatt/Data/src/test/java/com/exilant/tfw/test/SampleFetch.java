package com.exilant.tfw.test;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sssoft.isatt.data.pojo.input.IdentifierType;
import com.sssoft.isatt.data.service.InputService;

public class SampleFetch {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(SampleFetch.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml", "dataAccessContext-local.xml" });
		InputService inputService = (InputService) context.getBean("inputService");
		List<IdentifierType> identifierTypes = inputService.getIdentifierType();
		Iterator<IdentifierType> iterator = identifierTypes.iterator();
		while (iterator.hasNext()) {
			IdentifierType identifierType = (IdentifierType) iterator.next();
			String identifierName = identifierType.getIdentifierTypeName();
			if (identifierName != null) {
				identifierName = StringUtils.substring(identifierName, 14);
				LOG.info(identifierName);
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}

}
