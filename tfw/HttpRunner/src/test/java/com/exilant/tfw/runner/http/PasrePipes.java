package com.exilant.tfw.runner.http;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PasrePipes {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(PasrePipes.class);

	public static void main(String[] args) {

		String in = "j\\|k|g4|b";
		{
			String[] ex = { "j|k", "g4", "b" };
			tst(in, ex);
		}

		in = "j|k|g4|b";
		{
			String[] ex = { "j", "k", "g4", "b" };
			tst(in, ex);
		}
	}

	private static void tst(String in, String[] ex) {
		LOG.info("----\n" + in);
		List<String> toks = parse(in);
		if (toks.size() != ex.length) {
			LOG.info("size mismatch, got :" + toks.size() + " exp " + ex.length);
		}
		for (int i = 0; i < ex.length; i++) {
			if (toks.size() > i) {
				String n = toks.get(i);
				if (!ex[i].equals(n)) {
					LOG.info(" mismatch :" + i + ", got :" + n + "; exp :" + ex[i]);
				} else {
					LOG.info(" okay :" + i + "; exp :" + ex[i]);
				}
			}
		}

		LOG.info("--");
	}

	// user2088127
	public static List<String> parsec(String in) {
		final String tmp = "[[=tmpReplacement=]]";
		List<String> tokens = new ArrayList<String>();
		// replaces all escaped pipes to a temporary replacement
		in = in.replaceAll("\\\\\\|", tmp);

		// iterate through all parts of the string which is divided by a pipe
		for (String str : in.split("\\|")) {
			str = str.replaceAll(tmp, "\\|"/* or how you need it */);
			// now you can use str; str is each part of the variable string,
			// which has not been escaped
			tokens.add(str);
		}
		return tokens;

	}

	// Syon
	public static List<String> parse(String in) {
		List<String> tokens = new ArrayList<String>();
		String value = in;
		String[] split = value.split("(?<!\\\\)(\\|)");
		for (int i = 0; i < split.length; i++) {
			split[i] = split[i].replaceAll("(\\\\\\|)", "\\|");
			tokens.add(split[i]);
		}
		return tokens;
	}

	public static List<String> parseT(String in) {
		List<String> tokens = new ArrayList<String>();
		int i = in.indexOf('|');
		int old = 0;

		while (i > -1) {
			if (i > 0) {
				if (in.charAt(i - 1) == '\\') {
					i = in.indexOf('|', i + 1);
					continue;
				}
			}
			String s = in.substring(old, i);
			s.replace("\\|", "|");
			tokens.add(s);
			old = i + 1;
			i = in.indexOf('|', i + 1);

		}
		if (i > 0 && i < (in.length() - 1)) {
			String s = in.substring(i + 1);
			s.replace("\\|", "|");
			tokens.add(s);
		}
		return tokens;

	}
}
