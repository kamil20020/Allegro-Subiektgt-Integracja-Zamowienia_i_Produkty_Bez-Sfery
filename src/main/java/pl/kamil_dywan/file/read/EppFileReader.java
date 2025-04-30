package pl.kamil_dywan.file.read;

import pl.kamil_dywan.file.EppSerializable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class EppFileReader<T> implements FileReader<T>{

    private final Map<String, Class<? extends EppSerializable>> schema;
    private final Map<String, Integer[]> readIndexes;
    private final Class<T> type;

    private static final String BEFORE_HEADER_CONTENT = "[NAGLOWEK]";

    public EppFileReader(LinkedHashMap<String, Class<? extends EppSerializable>> schema, LinkedHashMap<String, Integer[]> readIndexes, Class<T> type){

        super();

        this.schema = schema;
        this.readIndexes = readIndexes;
        this.type = type;
    }

    @Override
    public T load(String filePath) throws URISyntaxException, IOException, IllegalStateException {

        File loadedFile = FileReader.loadFile(filePath);
        Path loadedFilePath = loadedFile.toPath();

        String[] gotLines = Files.lines(loadedFilePath)
            .toArray(String[]::new);

        return loadFromLines(gotLines);
    }

    @Override
    public T loadFromStr(String value) throws IllegalStateException{

        String[] lines = value.split("\\n");

        return loadFromLines(lines);
    }

    private T loadFromLines(String[] lines) throws IllegalStateException{

        String headerName = null;

        int numberOfResultArguments = type.getDeclaredFields().length;

        Object[] resultArguments = new Object[numberOfResultArguments];
        int resultObjectIndex = 0;

        for(int i=3; i < lines.length || i <= resultObjectIndex; i++){ //skip info

            String line = lines[i];

            if(line.equals(BEFORE_HEADER_CONTENT)){

                headerName = lines[i + 1];
                headerName = headerName.substring(1, headerName.length() - 1);

                i += 3;

                continue;
            }
            else if(line.isBlank()){
                continue;
            }

            try {
                List<EppSerializable> gotContentList = createContentList(i, lines, headerName);

                resultArguments[resultObjectIndex++] = gotContentList;

                i += gotContentList.size();
            }
            catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {

                e.printStackTrace();

                throw new IllegalStateException(e.getMessage());
            }
        }

        return createResultObject(resultArguments);
    }

    private T createResultObject(Object[] resultArguments) throws IllegalStateException{

        Constructor<T> resultObjConstructor = (Constructor<T>) type.getDeclaredConstructors()[0];

        try {
            return resultObjConstructor.newInstance(resultArguments);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {

            e.printStackTrace();

            throw new IllegalStateException(e.getMessage());
        }
    }

    private List<EppSerializable> createContentList(int i, String[] lines, String headerName) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        List<EppSerializable> contentData = new ArrayList<>();

        for(int j = i; j < lines.length; j++){

            String line = lines[j];

            if(line.equals(BEFORE_HEADER_CONTENT) || line.isBlank()){
                break;
            }

            EppSerializable createdObj = createContentFromLine(line, headerName);

            contentData.add(createdObj);
        }

        return contentData;
    }

    private EppSerializable createContentFromLine(String line, String headerName) throws InvocationTargetException, InstantiationException, IllegalAccessException {

        String[] gotData = line.split(",");

        Class<? extends EppSerializable> gotType = schema.get(headerName);
        Integer[] readIndexesForContent = null;

        if(readIndexes.containsKey(headerName)){

            readIndexesForContent = readIndexes.get(headerName);
        }

        String[] objArguments = null;

        if(readIndexesForContent != null){

            objArguments = new String[readIndexesForContent.length];

            for(int i = 0; i < readIndexesForContent.length; i++){

                int readIndex = readIndexesForContent[i];

                String gotDataPart = gotData[readIndex];

                objArguments[i] = gotDataPart;
            }
        }
        else{
            objArguments = gotData;
        }

        Constructor<?> constructor = gotType.getConstructors()[0];

        return (EppSerializable) constructor.newInstance((Object) objArguments);
    }
}
