package hu.unideb.inf.tennis.jaxb;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBUtil {
	
	public static String toXML(Object o) throws JAXBException {
		try {
			JAXBContext	context = JAXBContext.newInstance(o.getClass());
			Marshaller	marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			Writer writer = new StringWriter();
			marshaller.marshal(o, writer);
			return writer.toString();
		} catch(JAXBException e) {
			throw e;
		}
	}
	
	public static <T> T fromXML(Class<T> clazz, String xml) throws JAXBException {
		try {
			JAXBContext	context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			return (T) unmarshaller.unmarshal(reader);
		} catch(JAXBException e) {
			throw e;
		}
	}

}
