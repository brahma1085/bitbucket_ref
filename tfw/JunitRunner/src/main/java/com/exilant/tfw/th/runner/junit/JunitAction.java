/**
 * @author soumyamohanty
 */

package com.exilant.tfw.th.runner.junit;

import org.apache.log4j.Logger;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * The Class JunitAction.
 */
public class JunitAction {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(JunitAction.class);

	/**
	 * Check out code.
	 * 
	 * @param svnUrl
	 *            the svn url
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param targetDir
	 *            the target dir
	 */
	public void checkOutCode(String svnUrl, String userName, String password, String targetDir) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("checkOutCode(String, String, String, String) - start"); //$NON-NLS-1$
		}

		Process process;
		try {
			String relativePath = JunitAction.class.getClassLoader().getResource("checkout.sh").getPath();
			String command = relativePath + " " + svnUrl + " " + userName + " " + password + " " + targetDir;
			process = Runtime.getRuntime().exec(command);
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				LOG.info(line);
			}
			process.waitFor();
		} catch (Exception e) {
			LOG.warn("checkOutCode(String, String, String, String) - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("checkOutCode(String, String, String, String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Generate power mock.
	 * 
	 * @param srcDir
	 *            the src dir
	 * @param classpath
	 *            the classpath
	 */
	public void generatePowerMock(String srcDir, String classpath) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("generatePowerMock(String, String) - start"); //$NON-NLS-1$
		}

		if ("ant".equals(getProjectType(srcDir))) {
			final String cmd = "java -cp lib/PowerMock.jar:lib/jxl-2.6.10.jar:lib/velocity-dep-1.5.jar:" + srcDir + "lib/*:" + srcDir
					+ "sarcontent/lib/* com.apple.powermock.PowerMockMain " + srcDir + "src/ " + srcDir + "powermock/";
			Process process;
			try {
				process = Runtime.getRuntime().exec(cmd);
				BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
					LOG.info(line);
				}
			} catch (Exception e) {
				LOG.warn("generatePowerMock(String, String) - exception ignored", e); //$NON-NLS-1$
			}
		} else {
			final String cmd = "java -cp lib/PowerMock.jar:lib/jxl-2.6.10.jar:lib/velocity-dep-1.5.jar:" + srcDir
					+ "lib/* com.apple.powermock.PowerMockMain " + srcDir + "src/ " + srcDir + "powermock/";
			Process process;
			try {
				process = Runtime.getRuntime().exec(cmd);
				BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
					LOG.info(line);
				}

			} catch (Exception e) {
				LOG.warn("generatePowerMock(String, String) - exception ignored", e); //$NON-NLS-1$
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("generatePowerMock(String, String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Parses the power sh.
	 * 
	 * @param project_dependecies
	 *            the project_dependecies
	 * @param project_sourcepath
	 *            the project_sourcepath
	 * @param project_powermockdestPath
	 *            the project_powermockdest path
	 * @return the string
	 */
	public String parsePowerSh(String project_dependecies, String project_sourcepath, String project_powermockdestPath) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("parsePowerSh(String, String, String) - start"); //$NON-NLS-1$
		}

		VelocityEngine ve = new VelocityEngine();
		String cmd = "";
		String path = "/data/branch/tfw/w/w2/JunitRunner/src/main/resources/";
		Properties props = new Properties();
		props.put("file.resource.loader.path", path);
		try {
			ve.init(props);
			VelocityContext context = new VelocityContext();

			context.put("dependencies", project_dependecies);
			context.put("mainFile", "com.apple.powermock.PowerMockMain");
			context.put("src", project_sourcepath);
			context.put("dest", project_powermockdestPath);

			Template t = ve.getTemplate("PowerMockShTemplate.vm");
			StringWriter writer = new StringWriter();

			t.merge(context, writer);
			LOG.info(writer.toString());

			cmd = writer.toString();

		} catch (Exception e) {
			LOG.warn("parsePowerSh(String, String, String) - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("parsePowerSh(String, String, String) - end"); //$NON-NLS-1$
		}
		return cmd;

	}

	/**
	 * Generate cobertura report.
	 * 
	 * @param scriptPath
	 *            the script path
	 * @param targetAndGoals
	 *            the target and goals
	 */
	public void generateCoberturaReport(String scriptPath, String targetAndGoals) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("generateCoberturaReport(String, String) - start"); //$NON-NLS-1$
		}

		final String projectdir = scriptPath;
		try {
			Process process;
			try {
				if ("ant".equals(getProjectType(projectdir))) {
					process = Runtime.getRuntime().exec("ant -file " + scriptPath + "/build.xml coverage");

					LOG.info(projectdir + " **************");
					LOG.info("ant **************");
				} else {
					process = Runtime.getRuntime().exec("/src/main/resources/scripts/cobertura-maven.sh" + " " + scriptPath + " " + targetAndGoals);
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;

				while ((line = in.readLine()) != null) {
					LOG.info(line);
				}
				viewCoberturaReport(scriptPath, targetAndGoals);
			} catch (Exception e) {
				LOG.warn("generateCoberturaReport(String, String) - exception ignored", e); //$NON-NLS-1$
			}
		} catch (Exception ex) {
			LOG.warn("generateCoberturaReport(String, String) - exception ignored", ex); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("generateCoberturaReport(String, String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * View cobertura report.
	 * 
	 * @param scriptPath
	 *            the script path
	 * @param targetAndGoals
	 *            the target and goals
	 */
	public void viewCoberturaReport(String scriptPath, String targetAndGoals) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("viewCoberturaReport(String, String) - start");
		}
		String projectTypeflag = null;
		String projectdir;
		if (scriptPath.contains("pom.xml")) {
			projectdir = scriptPath.substring(0, scriptPath.lastIndexOf("/"));
			projectTypeflag = "pom";
		} else {
			projectdir = scriptPath.substring(0, scriptPath.lastIndexOf("/"));
			projectTypeflag = "ant";
		}
		if ("ant".equals(projectTypeflag)) {
			Desktop desktop = Desktop.getDesktop();
			File file = null;
			file = new File(projectdir + "/Metrics/cobertura/coveragereport/index.html");
			if (!file.exists()) {
				LOG.info("View generated report");
			}
			try {
				desktop.open(file);
			} catch (IOException e) {
				LOG.warn("viewCoberturaReport(String, String) - exception ignored", e);
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("viewCoberturaReport(String, String) - end");
		}
	}

	/**
	 * Gets the project type.
	 * 
	 * @param dirPath
	 *            the dir path
	 * @return the project type
	 */
	public String getProjectType(String dirPath) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getProjectType(String) - start");
		}
		File[] filelist = null;
		LOG.info(dirPath);
		if (new File(dirPath).isDirectory()) {
			filelist = new File(dirPath).listFiles();
		}
		for (int i = 0; i < filelist.length; i++) {
			if ("build.xml".equals(filelist[i].getName())) {
				return "ant";
			}
			if ("pom.xml".equals(filelist[i].getName())) {
				return "maven";
			}
		}
		return null;
	}

}
