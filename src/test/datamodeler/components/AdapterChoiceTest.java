package test.datamodeler.components;

import junit.framework.Assert;

import org.junit.Test;

import datamodeler.adapters.AdapterManager;
import datamodeler.components.AdapterChoice;

/**
 * @author Kyle Sletten
 */
public class AdapterChoiceTest {
	@Test
	public void testAdapterChoiceLoadsAdapters() {
		AdapterChoice adapterChoice = new AdapterChoice();

		for (String name : AdapterManager.getAdapterNames()) {
			for (int i = 0; i < adapterChoice.getItemCount(); i++) {
				if (adapterChoice.getItem(i).equals(name)) {
					break;
				}
				Assert.fail();
			}
		}
	}
}
