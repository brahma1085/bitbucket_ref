package com.tfw.verifiers.parsers;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;

/**
 * The Class SimpleNamespaceContext.
 *
 * @author brahma
 * 
 * This class is the implementation of NamespaceContext which gives the
 * namespaceURI based on the given prefix
 */
public class SimpleNamespaceContext implements NamespaceContext {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(SimpleNamespaceContext.class);

	/** The prefix map. */
	private final Map<String, String> PREFIX_MAP = new HashMap<String, String>();

	/**
	 * It stores the all the name spaces in a java.lang.Map<String, String> as
	 * key value pairs
	 *
	 * @param prefixMap the prefix map
	 */
	public SimpleNamespaceContext(final Map<String, String> prefixMap) {
		PREFIX_MAP.putAll(prefixMap);
	}

	/**
	 * It returns the name space URI based on the given prefix.
	 *
	 * @param prefix the prefix
	 * @return the namespace uri
	 */
	public String getNamespaceURI(String prefix) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getNamespaceURI(String) - start"); //$NON-NLS-1$
		}

		String returnString = PREFIX_MAP.get(prefix);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getNamespaceURI(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/* (non-Javadoc)
	 * @see javax.xml.namespace.NamespaceContext#getPrefix(java.lang.String)
	 */
	public String getPrefix(String uri) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPrefix(String) - start"); //$NON-NLS-1$
		}

		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see javax.xml.namespace.NamespaceContext#getPrefixes(java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	public Iterator getPrefixes(String uri) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPrefixes(String) - start"); //$NON-NLS-1$
		}

		throw new UnsupportedOperationException();
	}

}
