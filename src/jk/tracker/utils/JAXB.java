package jk.tracker.utils;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public final class JAXB<T> {
	
	private JAXBContext jaxb;
	private File file;
	
	public JAXB(String context, File file) throws JAXBException
	{
		this.jaxb = JAXBContext.newInstance(context);
		this.file = file;
	}
	
	@SuppressWarnings("unchecked")
	public T load() throws JAXBException
	{
		return (T) jaxb.createUnmarshaller().unmarshal(file);
	}
	
	public void save(T object) throws JAXBException
	{
		Marshaller marsh = jaxb.createMarshaller();
		marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marsh.marshal(object, file);
	}
}
