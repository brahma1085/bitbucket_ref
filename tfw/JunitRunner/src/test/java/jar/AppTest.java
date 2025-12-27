package jar;

import org.apache.log4j.Logger;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(AppTest.class);
    
    /**
     * Create the test case.
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * Suite.
     *
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
		if (LOG.isDebugEnabled()) {
			LOG.debug("suite() - start"); //$NON-NLS-1$
		}

		Test returnTest = new TestSuite(AppTest.class);
		if (LOG.isDebugEnabled()) {
			LOG.debug("suite() - end"); //$NON-NLS-1$
		}
        return returnTest;
    }

    /**
     * Rigourous Test :-).
     */
    public void testApp()
    {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testApp() - start"); //$NON-NLS-1$
		}

        assertTrue( true );

		if (LOG.isDebugEnabled()) {
			LOG.debug("testApp() - end"); //$NON-NLS-1$
		}
    }
}
