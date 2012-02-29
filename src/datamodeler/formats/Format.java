package datamodeler.formats;

import java.io.IOException;
import java.io.Writer;

public interface Format<AnyType> {
	public void write(AnyType item, Writer writer) throws IOException;

	public void write(Iterable<AnyType> item, Writer writer) throws IOException;
}
