package pl.kamil_dywan.file.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.kamil_dywan.file.read.FileReader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class JSONFileReader<T> extends FileReader<T> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final Class<T> type;

    public JSONFileReader(Class<T> type){

        this.type = type;
    }

    public T load(String filePath) throws URISyntaxException, IOException {

        File foundFile = loadFile(filePath);
        String allegroOrderStr = Files.readString(foundFile.toPath());

        return objectMapper.readValue(allegroOrderStr, type);
    }
}
