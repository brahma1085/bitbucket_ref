package com.sssoft.isatt.runner.javaapirunner.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * The Class JavaAPIClass.
 */
public class JavaAPIClass {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(JavaAPIClass.class);

	/** The output. */

	public static Object output;

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		String src = "/Users/anand.mathur/data/tfw/w/WorkSpace/testJavaAPI/bin/";
		String className = "com.exilant.javaApitest.example.Calci";
		String jsonString = "{\"methodName_add_1_1\":\"add\",\"paramTypes_add_1_1\":\"int\",\"paramTypes_add_2_1\":\"int\",\"paramName_add_1_1\":\"10\",\"paramName_add_2_1\":\"20\",\"returnParamName_add_1_1\":\"add_outparam\",\"returnParamType_add_1_1\":\"int\",\"selected_add_1_1\":true,\"methodName_multi_1_2\":\"multi\",\"paramTypes_multi_1_2\":\"int\",\"paramTypes_multi_2_2\":\"int\",\"paramName_multi_1_2\":\"30\",\"paramName_multi_2_2\":\"40\",\"returnParamName_multi_1_2\":\"multi_outparam\",\"returnParamType_multi_1_2\":\"int\",\"selected_multi_1_2\":true,\"methodName_interest_1_3\":\"interest\",\"paramTypes_interest_1_3\":\"int\",\"paramTypes_interest_2_3\":\"int\",\"paramTypes_interest_3_3\":\"int\",\"paramName_interest_1_3\":\"100\",\"paramName_interest_2_3\":\"2\",\"paramName_interest_3_3\":\"5\",\"returnParamName_interest_1_3\":\"interest_outparam\",\"returnParamType_interest_1_3\":\"int\",\"selected_interest_1_3\":true,\"methodName_addString_1_4\":\"addString\",\"paramTypes_addString_1_4\":\"String\",\"paramTypes_addString_2_4\":\"String\",\"paramName_addString_1_4\":\"hello\",\"paramName_addString_2_4\":\"testing\",\"returnParamName_addString_1_4\":\"addString_outparam\",\"returnParamType_addString_1_4\":\"String\",\"selected_addString_1_4\":true,\"methodName_callOtherobj_1_5\":\"callOtherobj\",\"paramTypes_callOtherobj_1_5\":\"ObjectAsParamTest\",\"paramName_callOtherobj_1_5\":\"objtest\",\"objpName_setA_1_5\":\"80\",\"objpTypes_setA_1_5\":\"int\",\"returnObjpName_setA_1_5\":\"setA_outparam\",\"returnObjpType_setA_1_5\":\"void\",\"objMethodName_setA_1_5\":\"setA\",\"objpName_setB_1_5\":\"90\",\"objpTypes_setB_1_5\":\"int\",\"returnObjpName_setB_1_5\":\"setB_outparam\",\"returnObjpType_setB_1_5\":\"void\",\"objMethodName_setB_1_5\":\"setB\",\"returnParamName_callOtherobj_1_5\":\"callOtherobj_outparam\",\"returnParamType_callOtherobj_1_5\":\"void\",\"selected_callOtherobj_1_5\":true}";

		javaApiValidation(src, className, jsonString);

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Java api validation.
	 * 
	 * @param src
	 *            the src
	 * @param className
	 *            the class name
	 * @param jsonString
	 *            the json string
	 */
	public static void javaApiValidation(String src, String className, String jsonString) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("javaApiValidation(String, String, String) - start"); //$NON-NLS-1$
		}

		Object arg[] = null;
		className = "com.exilant.javaApitest.example.Calci";
		File file = new File(src);
		URL[] urls = null;
		URL url;
		Class[] paramTypes = null;
		try {
			url = file.toURI().toURL();

			urls = new URL[] { url };
			LOG.info(urls[0]);
			URLClassLoader ucl = new URLClassLoader(urls);

			Class cls1 = ucl.loadClass(className);
			LOG.info(cls1);
			LOG.info(cls1.getDeclaredMethods().length);
			Method[] methods = cls1.getDeclaredMethods();
			ParameterGetSet param = new ParameterGetSet();
			Object obj, obj1 = null;
			Map<String, String> map = new TreeMap<String, String>();
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> methodNames = new HashMap<String, Object>();
			Map<String, Object> method1_parameters_details1 = new HashMap<String, Object>();
			Map<String, Object> method_parameters = new HashMap<String, Object>();

			int index = 1;
			int methodIndex = 0;
			int objMethodIndex = 0;

			try {
				LOG.info(jsonString);
				// convert JSON string to Map
				map = mapper.readValue(jsonString, new TypeReference<TreeMap<String, String>>() {
				});

				LOG.info(map);
				Set<String> keySet = map.keySet();
				LOG.info(keySet);
				int i = 0;
				String methodVal = null;
				String paramVal = null;
				String paramType = null;
				String objParamVal = null;
				String objParamType = null;
				List keyList = new LinkedList<String>();

				for (String setKey : keySet) {

					if (setKey.contains("methodName_") || setKey.contains("objMethodName_")) {
						methodVal = map.get(setKey);
						int indexOccurence = setKey.lastIndexOf("_");
						methodIndex = Integer.parseInt(setKey.substring(indexOccurence + 1));
						index = 1;
						// methodIndex++;
						if (setKey.contains("objMethodName_")) {
							objMethodIndex = 2;
						}
						keyList = new LinkedList<String>();
						method_parameters = new HashMap<String, Object>();
						// methodNames=new HashMap<String, Object>();
						for (String keys : keySet) {
							if (keys.contains(methodVal)) {
								keyList.add(keys);
							}
						}

					}
					int m = 0;
					for (Iterator<String> iter = keyList.listIterator(); iter.hasNext(); m++) {
						String temp = iter.next();
						LOG.info(m + 1 + " values " + temp);

						if (keyList.contains("paramName_" + methodVal + "_" + index + "_" + methodIndex)) {
							paramVal = map.get("paramName_" + methodVal + "_" + index + "_" + methodIndex);
						}
						if (keyList.contains("paramTypes_" + methodVal + "_" + index + "_" + methodIndex)) {
							paramType = map.get("paramTypes_" + methodVal + "_" + index + "_" + methodIndex);
						}
						if (keyList.contains("objpName_" + methodVal + "_" + index + "_" + methodIndex)) {
							objParamVal = map.get("objpName_" + methodVal + "_" + index + "_" + methodIndex);
						}
						if (keyList.contains("objpTypes_" + methodVal + "_" + index + "_" + methodIndex)) {
							objParamType = map.get("objpTypes_" + methodVal + "_" + index + "_" + methodIndex);
						}

						if (paramVal != null || objParamVal != null) {
							method1_parameters_details1 = new HashMap<String, Object>();
							method1_parameters_details1.put("Value", paramVal != null ? paramVal : objParamVal);
							method1_parameters_details1.put("Type", paramType != null ? paramType : objParamType);
							method_parameters.put(paramVal != null ? "paramName_" + methodVal + "_" + index + "_" + methodIndex : "objpName_" + methodVal
									+ "_" + index + "_" + methodIndex, method1_parameters_details1);
							if (methodVal != null) {
								methodNames.put(methodVal, method_parameters);
								paramVal = null;
								paramType = null;
								objParamVal = null;
								objParamType = null;
								index++;
							}
						}
					}
				}

				for (Method method : methods) {
					Map<String, Object> methodNamesMap = new HashMap<String, Object>();
					Map<String, Object> classMap = new HashMap<String, Object>();
					classMap.put("methods", methodNames);

					// obj =
					// (ClassLoader.getSystemClassLoader().loadClass("ClassForTest")).newInstance();
					obj = cls1.newInstance();

					for (Map.Entry entry : classMap.entrySet()) {
						// entry.getValue());
						methodNamesMap = (Map<String, Object>) classMap.get(entry.getKey());
					}

					if (methodNamesMap.containsKey(method.getName())) {
						Map<String, Object> parameterList = new HashMap<String, Object>();
						parameterList = (Map<String, Object>) methodNamesMap.get(method.getName());
						arg = new Object[parameterList.size()];
						paramTypes = method.getParameterTypes();
						Object objecttype = paramTypes[i];
						Set<Class<?>> wrapclass = JavaAPIClass.getWrapperTypes();
						if (objecttype.equals(Integer.TYPE) || objecttype.equals(Byte.TYPE) || objecttype.equals(Short.TYPE)
								|| objecttype.equals(Long.TYPE) || objecttype.equals(Float.TYPE) || objecttype.equals(Double.TYPE)
								|| objecttype.equals(Character.TYPE) || objecttype.equals(Boolean.TYPE) || wrapclass.contains(objecttype)) {

							if (parameterList.size() != 0) {
								param = getParameter(parameterList);

								output = invokeMethod(method, param, obj);
								LOG.info("Result: " + output);
							}
						} else {
							Class cl = (Class) objecttype;
							obj1 = cl.newInstance();
							Method[] methodList = cl.getDeclaredMethods();
							for (Method method1 : methodList) {
								if (methodNamesMap.containsKey(method1.getName())) {
									parameterList = new HashMap<String, Object>();
									parameterList = (Map<String, Object>) methodNamesMap.get(method1.getName());
									arg = new Object[parameterList.size()];
									if (parameterList.size() != 0) {
										param = getParameter(parameterList);

										output = invokeMethod(method1, param, obj1);
										if (output != null) {
											LOG.info("Result: " + output);
										}
									}
								}
							}
							LOG.info("Invoking method =====" + method.getName());
							output = method.invoke(obj, obj1);
							if (output != null)
								LOG.info("Result: " + output);

						}
					}
				}
				// ucl.close();

			} catch (Exception e) {
				LOG.warn("javaApiValidation(String, String, String) - exception ignored", e); //$NON-NLS-1$
			}
		} catch (MalformedURLException e1) {
			LOG.warn("javaApiValidation(String, String, String) - exception ignored", e1); //$NON-NLS-1$
		} catch (ClassNotFoundException e1) {
			LOG.warn("javaApiValidation(String, String, String) - exception ignored", e1); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("javaApiValidation(String, String, String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the parameter.
	 * 
	 * @param parameterList
	 *            the parameter list
	 * @return the parameter
	 */
	private static ParameterGetSet getParameter(Map<String, Object> parameterList) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParameter(Map<String,Object>) - start"); //$NON-NLS-1$
		}

		ParameterGetSet param = new ParameterGetSet();
		Class[] paraType = new Class[parameterList.size()];
		Object arg[] = new Object[parameterList.size()];
		Object[] paramValues = new Object[parameterList.size()];
		Set<String> keySet = parameterList.keySet();
		LOG.info(keySet);
		int i = 0;

		for (String setKey : keySet) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter = (Map<String, Object>) parameterList.get(setKey);
			if ("int".equals(parameter.get("Type")) | "Integer".equals(parameter.get("Type"))) {
				paraType[i] = Integer.TYPE;
				paramValues[i] = Integer.parseInt((String) parameter.get("Value"));
			} else if ("String".equals(parameter.get("Type"))) {
				paraType[i] = String.class;
				paramValues[i] = parameter.get("Value").toString();
			} else if ("double".equals(parameter.get("Type"))) {
				paraType[i] = double.class;
				paramValues[i] = parameter.get("Value").toString();
			} else if ("char".equals(parameter.get("Type"))) {
				paraType[i] = char.class;
				paramValues[i] = parameter.get("Value").toString();
			} else if ("boolean".equals(parameter.get("Type"))) {
				paraType[i] = boolean.class;
				paramValues[i] = parameter.get("Value").toString();
			} else if ("long".equals(parameter.get("Type"))) {
				paraType[i] = long.class;
				paramValues[i] = parameter.get("Value").toString();
			} else if ("short".equals(parameter.get("Type"))) {
				paraType[i] = short.class;
				paramValues[i] = parameter.get("Value").toString();
			} else if ("byte".equals(parameter.get("Type"))) {
				paraType[i] = byte.class;
				paramValues[i] = parameter.get("Value").toString();
			} else if ("float".equals(parameter.get("Type"))) {
				paraType[i] = float.class;
				paramValues[i] = parameter.get("Value").toString();
			}
			param.setParamArgVal(arg);
			param.setParamType(paraType);
			param.setValues(paramValues);
			i++;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParameter(Map<String,Object>) - end"); //$NON-NLS-1$
		}
		return param;
	}

	/**
	 * Invoke method.
	 * 
	 * @param method
	 *            the method
	 * @param param
	 *            the param
	 * @param obj
	 *            the obj
	 * @return the object
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public static Object invokeMethod(Method method, ParameterGetSet param, Object obj) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("invokeMethod(Method, ParameterGetSet, Object) - start"); //$NON-NLS-1$
		}

		if (param == null) {
			output = method.invoke(obj, null);
		} else {
			LOG.info("Invoking method=====" + method.getName());
			LOG.info("Invoking object=====" + obj.toString());
			output = method.invoke(obj, param.getValues());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("invokeMethod(Method, ParameterGetSet, Object) - end"); //$NON-NLS-1$
		}
		return output;
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
			LOG.info(ClassFilesPath11 + "::Directory exists.");

			classList = listFilesForFolder(classesDir, ClassFilesPath11, classList);
		} else {
			LOG.info(ClassFilesPath11 + "::Directory does'nt exist.");
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

	/**
	 * Gets the wrapper types.
	 * 
	 * @return the wrapper types
	 */
	public static Set<Class<?>> getWrapperTypes() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getWrapperTypes() - start"); //$NON-NLS-1$
		}

		Set<Class<?>> ret = new HashSet<Class<?>>();
		ret.add(Boolean.class);
		ret.add(Character.class);
		ret.add(Byte.class);
		ret.add(Short.class);
		ret.add(Integer.class);
		ret.add(Long.class);
		ret.add(Float.class);
		ret.add(Double.class);
		ret.add(Void.class);
		ret.add(String.class);
		ret.add(java.sql.Date.class);
		ret.add(java.util.ArrayList.class);
		ret.add(java.util.Set.class);
		ret.add(java.io.File.class);

		if (LOG.isDebugEnabled()) {
			LOG.debug("getWrapperTypes() - end"); //$NON-NLS-1$
		}
		return ret;

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
			LOG.debug("getProjectType(String) - start"); //$NON-NLS-1$
		}

		File[] filelist = null;
		LOG.info(dirPath);
		if (new File(dirPath).isDirectory()) {
			filelist = new File(dirPath).listFiles();
		}
		for (int i = 0; i < filelist.length; i++) {
			if ("build.xml".equals(filelist[i].getName())) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getProjectType(String) - end"); //$NON-NLS-1$
				}
				return "ant";
			}
			if ("pom.xml".equals(filelist[i].getName())) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getProjectType(String) - end"); //$NON-NLS-1$
				}
				return "maven";
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getProjectType(String) - end"); //$NON-NLS-1$
		}
		return null;
	}

}
