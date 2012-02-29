package test.datamodeler.adapters;

import junit.framework.Assert;

import org.junit.Test;

import datamodeler.adapters.Adapter;
import datamodeler.adapters.AdapterManager;
import datamodeler.adapters.MySQLAdapter;

/**
 * Test the loading of the <code>AdapterManager</code>
 * 
 * @author Kyle Sletten
 * 
 */
public class AdapterManagerTest {
	@Test
	public void testMySQLAdapterIsAvailable() {
		for (Adapter adapter : AdapterManager.getAdapters()) {
			if (adapter instanceof MySQLAdapter) {
				return;
			}
		}
		Assert.fail();
	}
}
