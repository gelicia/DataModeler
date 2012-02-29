package datamodeler.formats;

import java.io.IOException;
import java.io.Writer;

/**
 * An interface that abstracts away the implementation-specific steps to writing
 * an object to file
 * 
 * @author Kyle Sletten
 * 
 * @param <AnyType>
 *            the type to be written
 */
public interface Format<AnyType> {
	/**
	 * Write a single item using the specified writer
	 * 
	 * @param item
	 *            the item to be written
	 * @param writer
	 *            the writer to use
	 * @throws IOException
	 *             passed on from the <code>Writer</code>
	 */
	public void write(AnyType item, Writer writer) throws IOException;

	/**
	 * Write a sequence of items using the specified writer
	 * 
	 * @param items
	 *            the items to be written
	 * @param writer
	 *            the writer to use
	 * @throws IOException
	 *             passed on from the <code>Writer</code>
	 */
	public void write(Iterable<AnyType> items, Writer writer)
			throws IOException;
}
