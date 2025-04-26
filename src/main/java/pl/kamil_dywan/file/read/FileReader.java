package pl.kamil_dywan.file.read;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public interface FileReader<T> {

    static File loadFile(String filePath) throws URISyntaxException{

        URL fileURL = XMLFileReader.class.getClassLoader().getResource(filePath);
        URI fileURI = fileURL.toURI();
        Path foundFilePath = Path.of(fileURI);

        return foundFilePath.toFile();
    }

    T load(String filePath) throws URISyntaxException, IOException;
    T loadFromStr(String value) throws Exception;
}
