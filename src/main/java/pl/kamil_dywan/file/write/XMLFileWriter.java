package pl.kamil_dywan.file.write;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import pl.kamil_dywan.file.write.FileWriter;

import java.io.File;
import java.io.IOException;

public class XMLFileWriter<T> implements FileWriter<T> {

    private Marshaller marshaller;

    public XMLFileWriter(Class<T> type) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(type);
            marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_ENCODING, "windows-1250");
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        }
        catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void save(String filePath, T toSave) throws IOException {

        File file = new File(filePath);

        if(!file.exists()){

            file.createNewFile();
        }

        try {
            marshaller.marshal(toSave, file);
        }
        catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
