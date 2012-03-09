package datamodeler.awt.components;

import java.awt.Choice;

/**
 * A <code>java.awt.Choice</code> that does not allow items to be added/removed
 * once it has been initialized
 * 
 * @author Kyle Sletten
 */
@SuppressWarnings("serial")
public class ImmutableChoice extends Choice {
	private boolean mutable = true;

	/**
	 * Signal that the list is fully populated and any further attempts to
	 * update the object will result in an
	 * <code>UnsupportedOperationException</code>
	 */
	protected void setMutable(boolean mutable) {
		this.mutable = mutable;
	}

	@Override
	public void add(String item) {
		if (this.mutable) {
			super.add(item);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void addItem(String item) {
		if (this.mutable) {
			super.addItem(item);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void insert(String item, int index) {
		if (this.mutable) {
			super.insert(item, index);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void remove(int position) {
		if (this.mutable) {
			super.remove(position);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void remove(String item) {
		if (this.mutable) {
			super.remove(item);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void removeAll() {
		if (this.mutable) {
			super.removeAll();
		} else {
			throw new UnsupportedOperationException();
		}
	}
}
