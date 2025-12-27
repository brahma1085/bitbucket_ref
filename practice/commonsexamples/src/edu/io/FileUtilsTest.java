package edu.io;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileUtilsTest {
	public static void main(String[] args) {
		File file = new File("E:/test");
		File file2 = new File("E:/test1/text.txt");
		try {
			// FileUtils.cleanDirectory(file);
			//FileUtils.deleteDirectory(file);
			// String path=FileUtils.getTempDirectoryPath();
			// System.out.println(path);
			//FileUtils.copyDirectory(file, file2);
			FileUtils.copyFile(file, file2);
			//FileFilterUtils filter=new FileFilterUtils();
			//IOFileFilter ioFileFilter=filter.directoryFileFilter();
			//System.out.println(ioFileFilter);
		} catch (IOException e) {
			System.out.println("IOException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
	}
}
