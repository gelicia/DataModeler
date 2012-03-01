package datamodeler.components;

import datamodeler.models.KeyType;

@SuppressWarnings("serial")
public class KeyTypeChoice extends ImmutableChoice {
	public KeyTypeChoice() {
		for (KeyType keyType : KeyType.values()) {
			super.add(keyType.toString());
		}
		this.initialize();
	}

	public KeyType getSelectedKeyType() {
		return KeyType.valueOf(this.getSelectedItem());
	}

	public void setSelectedKeyType(KeyType keyType) {
		if (keyType != null) {
			super.select(keyType.toString());
		} else {
			this.setSelectedKeyType(KeyType.NONE);
		}
	}
}
