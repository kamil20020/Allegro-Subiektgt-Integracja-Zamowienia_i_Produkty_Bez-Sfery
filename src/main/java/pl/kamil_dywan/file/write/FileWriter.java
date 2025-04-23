package pl.kamil_dywan.file.write;

import java.io.IOException;

public interface FileWriter<T> {

    void save(String filePath, T toSave) throws IOException;
}
