package pl.kamil_dywan.file.read;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import pl.kamil_dywan.file.read.FileReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import java.io.*;
import java.net.URISyntaxException;

public class XMLFileReader<T> extends FileReader<T> {

    private Unmarshaller unmarshaller;

    public XMLFileReader(Class<T> type){

        try {
            // unmarshall, note that it's better to reuse JAXBContext, as newInstance()
            // calls are pretty expensive

            JAXBContext jc = JAXBContext.newInstance(type);
            unmarshaller = jc.createUnmarshaller();
        }
        catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static class XMLReaderWithoutNamespace extends StreamReaderDelegate {

        public XMLReaderWithoutNamespace(XMLStreamReader reader) {

            super(reader);
        }

        @Override
        public String getAttributeNamespace(int arg0) {

            return "";
        }

        @Override
        public String getNamespaceURI() {

            return "";
        }
    }

    public T load(String filePath) throws URISyntaxException, IOException {

        File foundFile = loadFile(filePath);

        T result = null;

        FileInputStream is = null;

        try{
            is = new FileInputStream(foundFile);

            XMLStreamReader xsr = XMLInputFactory.newFactory()
                .createXMLStreamReader(is);

            XMLReaderWithoutNamespace xr = new XMLReaderWithoutNamespace(xsr);

            result = (T) unmarshaller.unmarshal(xr);
        }
        catch(JAXBException | XMLStreamException e){
            e.printStackTrace();
        }
        finally {

            if(is != null){
                is.close();
            }
        }

       return result;
    }
}
