package test.datamodeler.components;

import junit.framework.Assert;

import org.junit.Test;

import datamodeler.components.KeyTypeChoice;
import datamodeler.models.KeyType;

public class KeyTypeChoiceTest {
	@Test
	public void testKeyTypeChoiceLoadsKeyTypes() {
		KeyTypeChoice keyTypeChoice = new KeyTypeChoice();

		for (KeyType keyType : KeyType.values()) {
			boolean found = false;
			for (int i = 0; i < keyTypeChoice.getItemCount(); i++) {
				if (keyTypeChoice.getItem(i).equals(keyType.toString())) {
					found = true;
					break;
				}
			}
			Assert.assertTrue(keyType.toString(), found);
		}
	}
}
