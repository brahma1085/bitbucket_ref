package com.exilant.tfw.util.http;

import java.io.Serializable;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.exilant.tfw.util.threads.TfwPools;

/**
 * Post back info helper. With fluent API. See AgentPools for usage sample
 * */
public class PostBackAsync implements Runnable {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(PostBackAsync.class);

	// private List<Serializable> postBakckObjects = new
	// ArrayList<Serializable>(1);
	/** The post bakck objects. */
	private Serializable[] postBakckObjects = null;
	
	/** The obj typ. */
	private String objTyp;
	
	/** The log msg. */
	private String logMsg;
	
	/** The log lvl. */
	private Level logLvl;
	
	/** The log lvl ok. */
	private Level logLvlOk;
	
	/** The h dat. */
	private HttpData hDat;
	
	/** The jvm mode. */
	private boolean jvmMode;

	/**
	 * Instantiates a new post back async.
	 */
	public PostBackAsync() {

	}

	/**
	 * Instantiates a new post back async.
	 *
	 * @param objTypeMsg the obj type msg
	 * @param logmsg the logmsg
	 * @param slogLvl the slog lvl
	 * @param jvmMode the jvm mode
	 * @param hDat the h dat
	 * @param postBakckObjectsp the post bakck objectsp
	 */
	public PostBackAsync(String objTypeMsg, String logmsg, String slogLvl, boolean jvmMode, HttpData hDat, Serializable... postBakckObjectsp) {
		super();
		postBakckObjects = postBakckObjectsp;
		this.hDat = hDat;
		this.jvmMode = jvmMode;
		this.objTyp = objTypeMsg;
		this.logMsg = logmsg;
		setLogLvl(slogLvl);
	}

	/**
	 * Sets the log lvl ok.
	 *
	 * @param l the l
	 * @return the post back async
	 */
	public PostBackAsync setLogLvlOk(Level l) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("setLogLvlOk(Level) - start"); //$NON-NLS-1$
		}

		logLvlOk = l;

		if (LOG.isDebugEnabled()) {
			LOG.debug("setLogLvlOk(Level) - end"); //$NON-NLS-1$
		}
		return this;
	}

	/**
	 * Start.
	 *
	 * @param poolNm the pool nm
	 * @param asyncCheck the async check
	 */
	public void start(String poolNm, Boolean asyncCheck) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("start(String, Boolean) - start"); //$NON-NLS-1$
		}

		if (asyncCheck == null || asyncCheck) {
			if (poolNm == null || poolNm.length() == 0) {
				poolNm = TfwPools.DEFAULT_POOL;
			}
			TfwPools.offer(poolNm, this);

		} else {
			Thread.currentThread().start();
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("start(String, Boolean) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the obj type.
	 *
	 * @return the obj type
	 */
	public String getObjType() {
		return objTyp;
	}

	/**
	 * Sets the obj type.
	 *
	 * @param msg the msg
	 * @return the post back async
	 */
	public PostBackAsync setObjType(String msg) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("setObjType(String) - start"); //$NON-NLS-1$
		}

		this.objTyp = msg;

		if (LOG.isDebugEnabled()) {
			LOG.debug("setObjType(String) - end"); //$NON-NLS-1$
		}
		return this;
	}

	/**
	 * Gets the log msg.
	 *
	 * @return the log msg
	 */
	public String getLogMsg() {
		return logMsg;
	}

	/**
	 * Sets the log msg.
	 *
	 * @param logmsg the logmsg
	 * @return the post back async
	 */
	public PostBackAsync setLogMsg(String logmsg) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("setLogMsg(String) - start"); //$NON-NLS-1$
		}

		this.logMsg = logmsg;

		if (LOG.isDebugEnabled()) {
			LOG.debug("setLogMsg(String) - end"); //$NON-NLS-1$
		}
		return this;
	}

	/**
	 * Gets the log lvl.
	 *
	 * @return the log lvl
	 */
	public Level getLogLvl() {
		return logLvl;
	}

	/**
	 * Checks if is jvm mode.
	 *
	 * @return true, if is jvm mode
	 */
	public boolean isJvmMode() {
		return jvmMode;
	}

	/**
	 * Sets the jvm mode.
	 *
	 * @param jvmMode the new jvm mode
	 */
	public void setJvmMode(boolean jvmMode) {
		this.jvmMode = jvmMode;
	}

	/**
	 * Sets the log lvl.
	 *
	 * @param slogLvl the slog lvl
	 * @return the post back async
	 */
	public PostBackAsync setLogLvl(String slogLvl) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("setLogLvl(String) - start"); //$NON-NLS-1$
		}

		if (slogLvl == null) {
			slogLvl = "";
		}
		if ("info".equalsIgnoreCase(slogLvl)) {
			this.logLvl = Level.INFO;
		} else {
			this.logLvl = Level.WARN;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("setLogLvl(String) - end"); //$NON-NLS-1$
		}
		return this;
	}

	/**
	 * Sets the log lvl ok.
	 *
	 * @param slogLvl the slog lvl
	 * @return the post back async
	 */
	public PostBackAsync setLogLvlOk(String slogLvl) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("setLogLvlOk(String) - start"); //$NON-NLS-1$
		}

		if (slogLvl == null) {
			slogLvl = "";
		}
		if ("info".equalsIgnoreCase(slogLvl)) {
			this.logLvlOk = Level.INFO;
		} else {
			this.logLvlOk = Level.WARN;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("setLogLvlOk(String) - end"); //$NON-NLS-1$
		}
		return this;
	}

	/**
	 * Sets the http params.
	 *
	 * @param hDat the h dat
	 * @return the post back async
	 */
	public PostBackAsync setHttpParams(HttpData hDat) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("setHttpParams(HttpData) - start"); //$NON-NLS-1$
		}

		this.hDat = hDat;

		if (LOG.isDebugEnabled()) {
			LOG.debug("setHttpParams(HttpData) - end"); //$NON-NLS-1$
		}
		return this;
	}

	/**
	 * Adds the.
	 *
	 * @param postBakckObject the post bakck object
	 * @return the post back async
	 */
	public PostBackAsync add(Serializable postBakckObject) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("add(Serializable) - start"); //$NON-NLS-1$
		}

		if (postBakckObject != null) {
			Serializable[] aa = null;
			if (postBakckObjects == null) {
				aa = new Serializable[1];
			} else {
				aa = new Serializable[postBakckObjects.length + 1];
				System.arraycopy(postBakckObjects, 0, aa, 0, aa.length - 1);
			}
			aa[aa.length - 1] = postBakckObject;
			postBakckObjects = aa;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("add(Serializable) - end"); //$NON-NLS-1$
		}
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("run() - start"); //$NON-NLS-1$
		}

		try {
			if (hDat != null && hDat.getUrl() != null) {
				// Serializable[] objs = postBakckObjects.toArray(new
				// Serializable[postBakckObjects.size()]);
				Object rtn = NetSend.sendObjects(objTyp, this.jvmMode, hDat, postBakckObjects);
				if (logLvlOk == null) {
					logLvlOk = logLvl;
				}
				LOG.log(logLvlOk, logMsg + " " + rtn);
			} else {
				LOG.log(logLvl, logMsg + " Url null, not posting back");
			}
		} catch (Exception e) {
			LOG.warn("logMsg :" + logMsg + ", hDat :" + hDat);
			LOG.warn("Post back error :" + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("run() - end"); //$NON-NLS-1$
		}
	}
}
