package com.tfw.verifiers.parsers.test;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.eel.kitchen.jsonschema.main.JsonSchema;
import org.eel.kitchen.jsonschema.main.JsonSchemaFactory;
import org.eel.kitchen.jsonschema.report.ValidationReport;
import org.eel.kitchen.jsonschema.util.JsonLoader;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * The Class JsonValidationAgainstSchema.
 */
public class JsonValidationAgainstSchema {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(JsonValidationAgainstSchema.class);

	static {
		BasicConfigurator.configure();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		try {
			String jsonData = FileUtils.readFileToString(new File(args[0]));
			String jsonSchema = FileUtils.readFileToString(new File(args[1]));
			boolean flag = validate(jsonData, jsonSchema);
			if (flag) {
				LOG.info("json is validated against its schema and it is valid json");
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Validate.
	 *
	 * @param jsonData the json data
	 * @param jsonSchema the json schema
	 * @return true, if successful
	 */
	public static boolean validate(String jsonData, String jsonSchema) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("validate(String, String) - start"); //$NON-NLS-1$
		}

		try {
			JsonNode schemaNode = JsonLoader.fromString(jsonSchema);
			JsonNode data = JsonLoader.fromString(jsonData);
			JsonSchemaFactory factory = JsonSchemaFactory.defaultFactory();
			JsonSchema schema = factory.fromSchema(schemaNode);
			ValidationReport report = schema.validate(data);
			boolean returnboolean = report.isSuccess();
			if (LOG.isDebugEnabled()) {
				LOG.debug("validate(String, String) - end"); //$NON-NLS-1$
			}
			return returnboolean;
		} catch (Exception e) {
			LOG.error("error occured due to : " + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("validate(String, String) - end"); //$NON-NLS-1$
		}
		return false;
	}
}
