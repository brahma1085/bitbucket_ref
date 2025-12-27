package com.tfw.exilant.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * The Class GetClasses.
 */
public class GetClasses {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(GetClasses.class);

	/**
	 * Gets the classes info.
	 * 
	 * @param ProjectDir
	 *            the project dir
	 * @return the classes info
	 */
	public static ArrayList<Class> getClassesInfo(String ProjectDir) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getClassesInfo(String) - start"); //$NON-NLS-1$
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		if (ProjectDir.endsWith("/bin/") || ProjectDir.endsWith("/classes/")) {
			ArrayList<String> classnames = new ArrayList<String>();
			try {
				URL url = new File(ProjectDir).toURL();
				URL[] urls = new URL[] { url };
				ClassLoader cl = new URLClassLoader(urls);
				classnames = new GetClasses().findClasses(ProjectDir);
				for (int i = 0; i < classnames.size(); i++) {
					if (!classnames.get(i).startsWith(".") && !classnames.get(i).contains("..") && !classnames.get(i).contains(".DS_Store")
							&& !classnames.get(i).contains(".properties") && !classnames.get(i).contains(".xml")) {
						Class cls = cl.loadClass(classnames.get(i));
						classes.add(cls);
					}
				}
			} catch (MalformedURLException e) {
				LOG.warn("getClassesInfo(String) - exception ignored", e); //$NON-NLS-1$
			} catch (ClassNotFoundException e) {
				LOG.warn("getClassesInfo(String) - exception ignored", e); //$NON-NLS-1$
			}
		} else {
			ArrayList<String> classnames = new ArrayList<String>();
			try {
				URL url = new File(ProjectDir).toURL();
				URL[] urls = new URL[] { url };
				ClassLoader cl = new URLClassLoader(urls);
				classnames = new GetClasses().findClasses(ProjectDir);
				for (int i = 0; i < classnames.size(); i++) {
					Class cls = cl.loadClass(classnames.get(i));
					classes.add(cls);
				}
			} catch (MalformedURLException e) {
				LOG.warn("getClassesInfo(String) - exception ignored", e); //$NON-NLS-1$
			} catch (ClassNotFoundException e) {
				LOG.warn("getClassesInfo(String) - exception ignored", e); //$NON-NLS-1$
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getClassesInfo(String) - end"); //$NON-NLS-1$
		}
		return classes;
	}

	/**
	 * Find classes.
	 * 
	 * @param ClassFilesPath11
	 *            the class files path11
	 * @return the array list
	 */
	public ArrayList<String> findClasses(String ClassFilesPath11) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("findClasses(String) - start"); //$NON-NLS-1$
		}

		ArrayList<String> classList = new ArrayList<String>();
		File classesDir = new File(ClassFilesPath11);
		if (classesDir.exists()) {
			classList = listFilesForFolder(classesDir, ClassFilesPath11, classList);
		} else {
			System.out.println(ClassFilesPath11 + "::Directory does'nt exist.");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("findClasses(String) - end"); //$NON-NLS-1$
		}
		return classList;

	}

	/**
	 * List files for folder.
	 * 
	 * @param folder
	 *            the folder
	 * @param ClassFilesPath11
	 *            the class files path11
	 * @param list
	 *            the list
	 * @return the array list
	 */
	public ArrayList<String> listFilesForFolder(final File folder, String ClassFilesPath11, ArrayList<String> list) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("listFilesForFolder(File, String, ArrayList<String>) - start"); //$NON-NLS-1$
		}

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry, ClassFilesPath11, list);
			} else {
				list.add(((fileEntry.getPath().replace(ClassFilesPath11, "")).replace("/", ".")).replace(".class", ""));
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("listFilesForFolder(File, String, ArrayList<String>) - end"); //$NON-NLS-1$
		}
		return list;
	}
}
