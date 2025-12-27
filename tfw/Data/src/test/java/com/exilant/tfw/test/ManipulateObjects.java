package com.exilant.tfw.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.exilant.tfw.pojo.input.IdentifierType;
import com.exilant.tfw.service.InputService;

@SuppressWarnings("unused")
public class ManipulateObjects {

	static int index;
	static ArrayList<String> identifierTypeArr = new ArrayList<String>();
	static ArrayList<String> objType = new ArrayList<String>();
	static ArrayList<String> objGroup = new ArrayList<String>();
	static String key, value, wndw, substr, objtyp, objName;
	static int strtIndex, endIndex;
	public static HashMap<String, String> hMapforWndw = new HashMap<String, String>();
	private static final Logger LOG = Logger.getLogger(ManipulateObjects.class);

	public Map<String, String> parseDocument(Document doc) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("parseDocument(Document) - start"); //$NON-NLS-1$
		}

		HashMap<String, String> map = new HashMap<String, String>();
		try {
			NodeList list = doc.getElementsByTagName("object");
			LOG.info("started parsing the xml document..");
			for (int i = 0; i < list.getLength(); i++) {
				Element el = (Element) list.item(i);
				String symbolicName = el.getAttribute("symbolicName");
				String realName = el.getAttribute("realName");
				map.put(symbolicName, realName);
			}
			LOG.info("parsing of xml document is complete");
			LOG.info("Total no. of objects obtained : " + map.size());
		} catch (Exception e) {
			LOG.warn("parseDocument(Document) - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("parseDocument(Document) - end"); //$NON-NLS-1$
		}
		return map;
	}

	public ArrayList<String> manipulateIdentifierType(Map<String, String> map) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("manipulateIdentifierType(Map<String,String>) - start"); //$NON-NLS-1$
		}

		try {
			String str;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (value.contains("=")) {
					String[] arr = value.split("=");
					for (int i = 0; i < (arr.length - 1); i++) {
						if (arr[i].lastIndexOf("{") > arr[i].lastIndexOf(" "))
							index = arr[i].lastIndexOf("{");
						else if (arr[i].lastIndexOf("{") < arr[i].lastIndexOf(" "))
							index = arr[i].lastIndexOf(" ");
						else
							index = 0;
						str = arr[i].substring(index);
						if (str.startsWith("{"))
							str = str.substring(1);
						if (str.startsWith(" "))
							str = str.substring(1);
						if (str.endsWith("?")) {
							index = str.lastIndexOf("?");
							str = str.substring(0, index);
						}
						String mat = "[\\d|\\s|\\W]";
						Pattern p1 = Pattern.compile(mat);
						Matcher m1 = p1.matcher(str);
						if (!m1.find()) {
							if (!identifierTypeArr.contains(str)) {
								identifierTypeArr.add(str);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.warn("manipulateIdentifierType(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("manipulateIdentifierType(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return identifierTypeArr;
	}

	public ArrayList<String> manipulateObjType(Map<String, String> map) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("manipulateObjType(Map<String,String>) - start"); //$NON-NLS-1$
		}

		try {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();

				if (value.contains("type='")) {
					String[] firstSplit = value.split("type='");
					for (int i = 1; i < firstSplit.length; i++) {
						String[] scndSplit = firstSplit[i].split("'");
						if (objType.size() == 0) {
							objType.add(scndSplit[0]);
						}
						if (objType.contains(scndSplit[0])) {
							;
						} else {
							objType.add(scndSplit[0]);
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.warn("manipulateObjType(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("manipulateObjType(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return objType;
	}

	public ArrayList<String> manipulateObjGroup(Map<String, String> map) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("manipulateObjGroup(Map<String,String>) - start"); //$NON-NLS-1$
		}

		try {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (value.contains("window=")) {
					strtIndex = value.indexOf("window=") + 7;
					endIndex = value.indexOf("}", value.indexOf("window="));
					value = value.substring(strtIndex, endIndex + 1);
					if (objGroup.size() == 0) {
						objGroup.add(value);
					}
					if (!objGroup.contains(value)) {
						objGroup.add(value);
					}
				}
			}
		}

		catch (Exception e) {
			LOG.warn("manipulateObjGroup(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("manipulateObjGroup(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return objGroup;
	}

	public void manipulateObjects(Map<String, String> map) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("manipulateObjects(Map<String,String>) - start"); //$NON-NLS-1$
		}

		try {
			int cont = 1;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (!value.isEmpty()) {
					if (value.contains("window=")) {
						int strtIndex = value.indexOf("window=") + 7;
						int lstIndex = value.indexOf("}", value.indexOf("window="));
						wndw = value.substring(strtIndex, lstIndex + 1);
					} else {
						wndw = "-NULL-";
					}
					int firstBrace = value.indexOf("{");
					if (firstBrace > -1) {
						while (value.lastIndexOf("{") != firstBrace) {
							substr = value.substring(value.lastIndexOf("{"), value.indexOf("}", value.lastIndexOf("{")) + 1);
							String wndwKey = "WND" + cont;
							hMapforWndw.put(wndwKey, substr);
							value = value.replace(substr, "'WND" + cont + "'");
							cont++;
						}
						if (value.contains("type=")) {
							int strtIndex = value.indexOf("type='") + 6;
							int lstIndex = value.indexOf("' ", value.indexOf("type='"));
							if (lstIndex < 0) {
								lstIndex = value.indexOf("'}", value.indexOf("type='"));
							}
							objtyp = value.substring(strtIndex, lstIndex);
						}
						value = value.replace("' ", "'` ");
						value = value.replace("''", "~");
						value = value.replace("'", "");
						value = value.replace("{", "");
						value = value.replace("}", "");
						value = value.replace("?", "");
						String allAttributes[] = value.split("` ");
						for (String attribute : allAttributes) {
							String attrAndData[] = attribute.split("=");
							for (int t = 0; t < attrAndData.length - 1; t += 1) {
								while (attrAndData[t + 1].contains("WND")) {
									Pattern p = Pattern.compile("WND\\d+");
									Matcher mat = p.matcher(attrAndData[t + 1]);
									if (mat.find()) {
										String stb = mat.group();
										attrAndData[t + 1] = attrAndData[t + 1].replace(stb, hMapforWndw.get(stb));
										attrAndData[t + 1] = attrAndData[t + 1].replace("'{", "{");
										attrAndData[t + 1] = attrAndData[t + 1].replace("}'}", "}}");
										attrAndData[t + 1] = attrAndData[t + 1].replace("}'", "}");
									}
								}
								objName = key;
								String IdntfrType = attrAndData[t];
								String Idntfr = attrAndData[t + 1];
								LOG.info("Object Type: " + objtyp);
								LOG.info("Object Name: " + objName);
								LOG.info("Identifier Type: " + IdntfrType);
								LOG.info("Identifier: " + Idntfr);
								LOG.info("Object Group: " + wndw);
								LOG.info("--------END---------");
							}
						}
					} else {
						wndw = "-NULL-";
					}
				}
			}
		} catch (Exception e) {
			LOG.warn("manipulateObjects(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("manipulateObjects(Map<String,String>) - end"); //$NON-NLS-1$
		}
	}
}
