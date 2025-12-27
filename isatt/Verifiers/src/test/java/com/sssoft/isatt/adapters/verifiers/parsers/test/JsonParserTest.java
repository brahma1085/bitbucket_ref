package com.sssoft.isatt.adapters.verifiers.parsers.test;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.log4j.Logger;

import com.sssoft.isatt.adapters.verifiers.bean.CaptureProps;
import com.sssoft.isatt.adapters.verifiers.bean.VerifyResult;
import com.sssoft.isatt.adapters.verifiers.parsers.JSONParser;
import com.sssoft.isatt.adapters.verifiers.validator.CompareJsonRequestValue;

/**
 * The Class JsonParserTest.
 */
public class JsonParserTest {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(JsonParserTest.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		File folder = new File("/data/samples/");
		IOFileFilter filter = new IOFileFilter() {
			VerifyResult verifyResult = new VerifyResult();

			@Override
			public boolean accept(File dir, String name) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("$IOFileFilter.accept(File, String) - start"); //$NON-NLS-1$
				}

				if (LOG.isDebugEnabled()) {
					LOG.debug("$IOFileFilter.accept(File, String) - end"); //$NON-NLS-1$
				}
				return false;
			}

			@Override
			public boolean accept(File file) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("$IOFileFilter.accept(File) - start"); //$NON-NLS-1$
				}

				String[] names = file.getParentFile().list();
				for (String name : names) {
					if (name.endsWith(".json")) {
						String string = null;
						try {
							string = FileUtils.readFileToString(file);
							if (string != null && string.length() != 0 && string.startsWith("{")) {
								LOG.info("required result : "
										+ JSONParser.prepareJson(string, "/orderLookupResponse/deviceId", "deviceId", 1, 1, verifyResult));
								CompareJsonRequestValue cjrValue = new CompareJsonRequestValue();
								CaptureProps captureProps = new CaptureProps();
								captureProps.setActionName("deviceId");
								captureProps.setResponseType("json1");
								captureProps.setxPath("/orderLookupResponse/deviceId");
								captureProps.setxPathMissed("error");
								verifyResult = cjrValue.compareRequest("DNPL1634DTTP", string, captureProps, verifyResult);
								LOG.info("verify Result : " + verifyResult);
							}
						} catch (Exception e) {
							LOG.error("error : " + e, e);
						}

						if (LOG.isDebugEnabled()) {
							LOG.debug("$IOFileFilter.accept(File) - end"); //$NON-NLS-1$
						}
						return true;
					}
				}

				if (LOG.isDebugEnabled()) {
					LOG.debug("$IOFileFilter.accept(File) - end"); //$NON-NLS-1$
				}
				return false;
			}
		};
		FileUtils.iterateFiles(folder, filter, null);

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}

}
