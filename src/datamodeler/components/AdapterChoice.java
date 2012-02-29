package datamodeler.components;

import datamodeler.adapters.Adapter;
import datamodeler.adapters.AdapterManager;

/**
 * An <code>ImmutableChoice</code> that lists the available <code>Adapter</code>s
 * @author Kyle Sletten
 */
@SuppressWarnings("serial")
public class AdapterChoice extends ImmutableChoice {
	public AdapterChoice() {
		for (String name : AdapterManager.getAdapterNames()) {
			super.add(name);
		}
		super.initialize();
	}

	/**
	 * Uses the currently selected value to get an <code>Adapter</code> 
	 * @return The selected <code>Adapter</code>
	 */
	public Adapter getSelectedAdapter() {
		try {
			return AdapterManager.getAdapterByName(super.getSelectedItem());
		} catch (Exception e) {
			return null;
		}
	}
}
