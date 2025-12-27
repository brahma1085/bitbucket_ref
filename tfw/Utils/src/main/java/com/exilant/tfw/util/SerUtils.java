package com.exilant.tfw.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

/**
 * The Class SerUtils.
 *
 * @author Tushar Kapila
 */

public class SerUtils {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(SerUtils.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

	}

	/**
	 * Gets the object serialized.
	 *
	 * @param object the object
	 * @return the object serialized
	 */
	public static byte[] getObjectSerialized(Object object) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectSerialized(Object) - start"); //$NON-NLS-1$
		}

		try {
			ObjectOutputStream out;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bos);
			out.writeObject(object);
			out.close();
			byte[] returnbyteArray = bos.toByteArray();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getObjectSerialized(Object) - end"); //$NON-NLS-1$
			}
			return returnbyteArray;
		} catch (Exception e) {
			LOG.fatal("Error: " + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectSerialized(Object) - end"); //$NON-NLS-1$
		}
		return null;
	}

	/**
	 * Retrive object.
	 *
	 * @param bytes the bytes
	 * @return the object
	 */
	public static Object retriveObject(byte bytes[]) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("retriveObject(byte[]) - start"); //$NON-NLS-1$
		}

		try {
			Object object;
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
			object = in.readObject();

			if (LOG.isDebugEnabled()) {
				LOG.debug("retriveObject(byte[]) - end"); //$NON-NLS-1$
			}
			return object;
		} catch (Exception e) {
			LOG.fatal("Error: " + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("retriveObject(byte[]) - end"); //$NON-NLS-1$
		}
		return null;
	}

}
