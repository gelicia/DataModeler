package datamodeler.components;

import java.awt.Choice;

/**
 * A <code>java.awt.Choice</code> that does not allow items to be added/removed
 * once it has been initialized
 * 
 * @author Kyle Sletten
 */
@SuppressWarnings("serial")
public class ImmutableChoice extends Choice {
	private boolean initialized = false;

	/**
	 * Signal that the list is fully populated and any further attempts to
	 * update the object will result in an
	 * <code>UnsupportedOperationException</code>
	 */
	protected void initialize() {
		this.initialized = true;
	}

	@Override
	public void add(String item) {
		if (this.initialized) {
			throw new UnsupportedOperationException();
		} else {
			super.add(item);
		}
	}

	@Override
	public void addItem(String item) {
		if (this.initialized) {
			throw new UnsupportedOperationException();
		} else {
			super.addItem(item);
		}
	}

	@Override
	public void insert(String item, int index) {
		if (this.initialized) {
			throw new UnsupportedOperationException();
		} else {
			super.insert(item, index);
		}
	}

	@Override
	public void remove(int position) {
		if (this.initialized) {
			throw new UnsupportedOperationException();
		} else {
			super.remove(position);
		}
	}

	@Override
	public void remove(String item) {
		if (this.initialized) {
			throw new UnsupportedOperationException();
		} else {
			super.remove(item);
		}
	}

	@Override
	public void removeAll() {
		if (this.initialized) {
			throw new UnsupportedOperationException();
		} else {
			super.removeAll();
		}
	}
}
