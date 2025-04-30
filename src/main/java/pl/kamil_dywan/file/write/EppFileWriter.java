package pl.kamil_dywan.file.write;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class EppFileWriter<T> implements FileWriter<T>{

    private List<String> headersNames;
    private Map<String, Integer[]> writeIndexes;

    private static final String BEFORE_HEADER_CONTENT = "[NAGLOWEK]";

    public EppFileWriter(List<String> headersNames, Map<String, Integer[]> writeIndexes){

        this.headersNames = headersNames;
        this.writeIndexes = writeIndexes;
    }

    @Override
    public void save(String filePath, Object toSave) throws IOException, IllegalStateException {

        File newFile = new File(filePath);

        if(!newFile.exists()){

            newFile.createNewFile();
        }

        Field[] rootFields = toSave.getClass().getDeclaredFields();

        try(BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(newFile))){

            for(int i = 0; i < rootFields.length; i++){

                Field rootField = rootFields[i];

                String headerName = headersNames.get(i);

                bufferedWriter.write(BEFORE_HEADER_CONTENT);
                bufferedWriter.newLine();
                bufferedWriter.write(headerName);
                bufferedWriter.newLine();

                try {
                    handleRootField(rootField, headerName, toSave);
                }
                catch (IllegalAccessException e) {

                    e.printStackTrace();

                    throw new IllegalStateException(e.getMessage());
                }
            }
        }
    }

    private void handleRootField(Field rootField, String headerName, Object obj) throws IllegalAccessException{

        rootField.setAccessible(true);

        ParameterizedType parameterizedFieldType = (ParameterizedType) rootField.getGenericType();
        Class<?> actualFieldParameter = (Class<?>) parameterizedFieldType.getActualTypeArguments()[0];

        Collection<?> objCollection = (Collection<?>) rootField.get(obj);



        for(Object childObj : objCollection){

            for(Field listField : actualFieldParameter.getDeclaredFields()){

                listField.setAccessible(true);

                System.out.println(listField.get(childObj));
            }
        }
    }

    @Override
    public String writeToStr(Object value) throws Exception {


        return null;
    }
}
