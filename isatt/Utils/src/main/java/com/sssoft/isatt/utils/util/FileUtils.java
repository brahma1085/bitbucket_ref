package com.sssoft.isatt.utils.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * The Class FileUtils.
 */
public class FileUtils {

	/** The LOG. */
	private static Logger LOG = Logger.getLogger(FileUtils.class);

	/**
	 * Copying files.
	 * 
	 * @param inputFile
	 *            the input file
	 * @param outputFile
	 *            the output file
	 */

	public static void fileCopy(String inputFile, String outputFile) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("fileCopy(String, String) - start"); //$NON-NLS-1$
		}

		LOG.log(Level.INFO, "in [" + inputFile + "] o [" + outputFile);
		File in = new File(inputFile);
		File out = new File(outputFile);
		fileCopy(in, out);

		if (LOG.isDebugEnabled()) {
			LOG.debug("fileCopy(String, String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * File copy.
	 * 
	 * @param inputFile
	 *            the input file
	 * @param outputFile
	 *            the output file
	 */
	public static void fileCopy(File inputFile, File outputFile) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("fileCopy(File, File) - start"); //$NON-NLS-1$
		}

		try {

			BufferedInputStream reader = new BufferedInputStream(new FileInputStream(inputFile), 4096);
			// FileUtils.stream(outputFile);
			BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(outputFile), 4096);
			byte[] buf = new byte[4096];
			int byteRead;
			while ((byteRead = reader.read(buf, 0, 4096)) >= 0) {
				writer.write(buf, 0, byteRead);
			}
			reader.close();
			writer.flush();
			writer.close();

		} catch (FileNotFoundException e) {
			LOG.error("fileCopy(File, File)", e); //$NON-NLS-1$

			LOG.log(Level.ERROR, "err copy " + e + ", in :" + inputFile + ", out :" + outputFile + ".", e);
		} catch (IOException e) {
			LOG.error("fileCopy(File, File)", e); //$NON-NLS-1$

			LOG.log(Level.ERROR, "err copy " + e + ", in :" + inputFile + ", out :" + outputFile + ".", e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("fileCopy(File, File) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Zip it.
	 * 
	 * @param source
	 *            the source
	 * @param zipFile
	 *            output ZIP file location
	 * @param fileList
	 *            the file list
	 */
	public static void zipIt(String source, String zipFile, List<String> fileList) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("zipIt(String, String, List<String>) - start"); //$NON-NLS-1$
		}

		byte[] buffer = new byte[1024];
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
			for (String file : fileList) {
				zos.putNextEntry(new ZipEntry(file));
				FileInputStream in = new FileInputStream(source + File.separator + file);
				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				in.close();
			}
			zos.closeEntry();
			zos.close();
		} catch (IOException ex) {
			LOG.error("Error in FileUtils  :" + ex + "]", ex);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("zipIt(String, String, List<String>) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Traverse a directory and get all files, and add the file into fileList.
	 * 
	 * @param node
	 *            file or directory
	 * @param source
	 *            the source
	 * @param fileList
	 *            the file list
	 */
	public static void generateFileList(File node, String source, List<String> fileList) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("generateFileList(File, String, List<String>) - start"); //$NON-NLS-1$
		}

		if (node.isFile()) {
			fileList.add(FileUtils.generateZipEntry(node.getAbsolutePath(), source));
		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileList(new File(node, filename), source, fileList);
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("generateFileList(File, String, List<String>) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * delete files with extension.
	 * 
	 * @param directoryName
	 *            the directory name
	 * @param extension
	 *            the extension
	 */
	public static void deleteFilesWithExtension(final String directoryName, final String extension) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteFilesWithExtension(String, String) - start"); //$NON-NLS-1$
		}

		final File dir = new File(directoryName);
		final String[] allFiles = dir.list();
		for (final String file : allFiles) {
			if (file.endsWith(extension)) {
				new File(directoryName + "/" + file).delete();
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteFilesWithExtension(String, String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Format the file path for zip.
	 * 
	 * @param file
	 *            file path
	 * @param sourceFolder
	 *            the source folder
	 * @return Formatted file path
	 */
	private static String generateZipEntry(String file, String sourceFolder) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("generateZipEntry(String, String) - start"); //$NON-NLS-1$
		}

		String returnString = file.substring(sourceFolder.length() + 1, file.length());
		if (LOG.isDebugEnabled()) {
			LOG.debug("generateZipEntry(String, String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Gets the base name.
	 * 
	 * @param name
	 *            the name
	 * @return the base name
	 */
	public static String getBaseName(String name) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getBaseName(String) - start"); //$NON-NLS-1$
		}

		if (name == null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("getBaseName(String) - end"); //$NON-NLS-1$
			}
			return null;
		}
		int i = name.lastIndexOf('.');
		if (i < 0) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("getBaseName(String) - end"); //$NON-NLS-1$
			}
			return name;
		}
		String returnString = name.substring(0, i);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getBaseName(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

}
