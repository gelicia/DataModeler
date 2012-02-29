package datamodeler.components;

import datamodeler.adapters.Adapter;
import datamodeler.adapters.AdapterManager;

@SuppressWarnings("serial")
public class AdapterChoice extends ImmutableChoice {
	public AdapterChoice() {
		for (String name : AdapterManager.getAdapterNames()) {
			super.add(name);
		}
		super.initialize();
	}

	public Adapter getSelectedAdapter() {
		try {
			return AdapterManager.getAdapterByName(super.getSelectedItem());
		} catch (Exception e) {
			return null;
		}
	}
}
