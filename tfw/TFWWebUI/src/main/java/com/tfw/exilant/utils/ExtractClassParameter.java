package com.tfw.exilant.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * The Class ExtractClassParameter.
 */
public class ExtractClassParameter {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(ExtractClassParameter.class);

	/**
	 * Generate java api.
	 * 
	 * @param src
	 *            the src
	 * @param className
	 *            the class name
	 * @return the list
	 */
	public static List<MethodParameters> generateJavaAPI(String src, String className) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("generateJavaAPI(String, String) - start"); //$NON-NLS-1$
		}

		String jsonMethod = null;
		List<MethodParameters> methodlist = null;
		try {
			GetClasses getclass = new GetClasses();
			List<ClassParameters> classparamlist = new ArrayList<ClassParameters>();
			List<Class> classes = getclass.getClassesInfo(src);

			for (int i = 0; i < classes.size(); i++) {
				classparamlist.add(readClassParams(classes.get(i)));
			}

			for (ClassParameters classparam : classparamlist) {
				if (classparam.getClassName().contains(className)) {
					methodlist = classparam.getMethodParams();
					Gson gson = new Gson();
					String json = gson.toJson(classparam);
					jsonMethod = gson.toJson(methodlist);
				}

			}
		} catch (Exception e) {
			LOG.warn("generateJavaAPI(String, String) - exception ignored", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("generateJavaAPI(String, String) - end"); //$NON-NLS-1$
		}
		return methodlist;

	}

	/**
	 * Read class params.
	 * 
	 * @param classUnderTest
	 *            the class under test
	 * @return the class parameters
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static ClassParameters readClassParams(Class classUnderTest) throws ClassNotFoundException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("readClassParams(Class) - start"); //$NON-NLS-1$
		}

		String className = null;
		Method[] methodList = null;
		String returnType = null;
		Class[] paramTypes = null;
		String methodName = null;
		String returnparamname = null;
		int methodParamCount = 0;
		int count = 0;
		ClassParameters classparams = new ClassParameters();
		List<MethodParameters> methods = new ArrayList<MethodParameters>();
		List<MethodParamsAsObject> objMethods = new ArrayList<MethodParamsAsObject>();

		// Reading className
		className = classUnderTest.getName();
		classparams.setClassName(className);

		// Reading Method Name
		methodList = classUnderTest.getDeclaredMethods();

		for (Method method : methodList) {
			MethodParameters methodparms = new MethodParameters();
			if (!method.getName().startsWith("get")) {
				if (method.getName() != "main") {

					methodName = method.getName();
					methodparms.setMethodName(methodName);
					paramTypes = method.getParameterTypes();
					String[] paramName = null;
					String[] paramType = null;

					// Reading paramTypes
					if (paramTypes.length == 0) {
						methodparms.setParamName(paramName);
						methodparms.setParamTypes(paramType);
					} else {
						// Reading paramName
						paramName = new String[paramTypes.length];
						paramType = new String[paramTypes.length];
						for (int i = 0; i < paramTypes.length; i++) {

							Object objecttype = paramTypes[i];
							Set<Class<?>> wrapclass = ExtractClassParameter.getWrapperTypes();
							if (objecttype.equals(Integer.TYPE) || objecttype.equals(Byte.TYPE) || objecttype.equals(Short.TYPE)
									|| objecttype.equals(Long.TYPE) || objecttype.equals(Float.TYPE) || objecttype.equals(Double.TYPE)
									|| objecttype.equals(Character.TYPE) || objecttype.equals(Boolean.TYPE) || wrapclass.contains(objecttype)) {

								paramName[i] = methodName + "_inparam_" + methodParamCount++;
								paramType[i] = paramTypes[i].getSimpleName();
							} else {
								paramName[i] = methodName + "_inparam_" + methodParamCount++;
								paramType[i] = paramTypes[i].getSimpleName();
								methodparms.setMethodParamsAsObject(readClassObjectParams((Class<?>) objecttype));
							}
						}
						methodparms.setParamName(paramName);
						methodparms.setParamTypes(paramType);
					}
					returnType = method.getReturnType().getSimpleName();
					returnparamname = methodName + "_outparam";
					methodparms.setReturnParamName(returnparamname);
					methodparms.setReturnParamType(returnType);
					methods.add(methodparms);
				}
				count++;
			}
		}
		classparams.setMethodParams(methods);

		if (LOG.isDebugEnabled()) {
			LOG.debug("readClassParams(Class) - end"); //$NON-NLS-1$
		}
		return classparams;

	}

	/**
	 * Read class object params.
	 * 
	 * @param classUnderTest
	 *            the class under test
	 * @return the list
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static List<MethodParamsAsObject> readClassObjectParams(Class classUnderTest) throws ClassNotFoundException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("readClassObjectParams(Class) - start"); //$NON-NLS-1$
		}

		String className = null;
		Method[] methodList = null;
		String returnType = null;
		Class[] paramTypes = null;
		String methodName = null;
		String returnparamname = null;
		int methodParamCount = 0;
		int count = 0;
		MethodParameters methods = new MethodParameters();
		List<MethodParamsAsObject> objMethods = new ArrayList<MethodParamsAsObject>();
		methodList = classUnderTest.getDeclaredMethods();
		for (Method method : methodList) {
			MethodParamsAsObject methodParamsAsObject = new MethodParamsAsObject();
			if (method.getName().startsWith("set")) {
				if (method.getName() != "main") {
					methodName = method.getName();
					paramTypes = method.getParameterTypes();
					String[] paramName = null;
					String[] paramType = null;
					if (paramTypes.length == 0) {
						methodParamsAsObject.setObjpName(paramName);
						methodParamsAsObject.setObjpTypes(paramType);
					} else {
						paramName = new String[paramTypes.length];
						paramType = new String[paramTypes.length];
						for (int i = 0; i < paramTypes.length; i++) {
							Object objecttype = paramTypes[i];
							Set<Class<?>> wrapclass = ExtractClassParameter.getWrapperTypes();
							if (objecttype.equals(Integer.TYPE) || objecttype.equals(Byte.TYPE) || objecttype.equals(Short.TYPE)
									|| objecttype.equals(Long.TYPE) || objecttype.equals(Float.TYPE) || objecttype.equals(Double.TYPE)
									|| objecttype.equals(Character.TYPE) || objecttype.equals(Boolean.TYPE) || wrapclass.contains(objecttype)) {
								paramName[i] = methodName + "_inparam_" + methodParamCount++;
								paramType[i] = paramTypes[i].getSimpleName();
							} else {
								readClassObjectParams((Class<?>) objecttype);
							}
						}
						methodParamsAsObject.setObjpName(paramName);
						methodParamsAsObject.setObjpTypes(paramType);
					}
					returnType = method.getReturnType().getSimpleName();
					returnparamname = methodName + "_outparam";
					methodParamsAsObject.setReturnObjpName(returnparamname);
					methodParamsAsObject.setReturnObjpType(returnType);
					methodParamsAsObject.setObjMethodName(methodName);
					objMethods.add(methodParamsAsObject);
				}
				count++;
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("readClassObjectParams(Class) - end"); //$NON-NLS-1$
		}
		return objMethods;

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

}
