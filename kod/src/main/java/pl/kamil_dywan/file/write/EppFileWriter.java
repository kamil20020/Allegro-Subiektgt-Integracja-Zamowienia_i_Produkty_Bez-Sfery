package pl.kamil_dywan.file.write;

import pl.kamil_dywan.external.subiektgt.own.product.Product;
import pl.kamil_dywan.external.subiektgt.own.product.ProductDetailedPrice;
import pl.kamil_dywan.external.subiektgt.own.product.ProductRelatedData;
import pl.kamil_dywan.file.EppSerializable;
import pl.kamil_dywan.file.read.EppFileReader;
import pl.kamil_dywan.file.read.FileReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.URISyntaxException;
import java.util.*;

public class EppFileWriter<T> implements FileWriter<T>{

    private List<String> headersNames;
    private List<Integer> toWriteHeadersIndexes;
    private List<Integer> rowsLengths;
    private LinkedHashMap<String, Integer[]> writeIndexes;

    private static final String BEFORE_HEADER_HEADER = "[NAGLOWEK]";
    private static final String BEFORE_INFO_HEADER = "[INFO]";
    private static final String BEFORE_CONTENT_HEADER = "[ZAWARTOSC]";

    public EppFileWriter(List<String> headersNames, List<Integer> toWriteHeadersIndexes, List<Integer> rowsLengths, LinkedHashMap<String, Integer[]> writeIndexes){

        this.headersNames = headersNames;
        this.toWriteHeadersIndexes = toWriteHeadersIndexes;
        this.rowsLengths = rowsLengths;
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

            bufferedWriter.write(BEFORE_INFO_HEADER);
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            if(toWriteHeadersIndexes == null){

                drawAllRows(rootFields, bufferedWriter, toSave);
            }
            else{

                drawSelectedRows(rootFields, bufferedWriter, toSave);
            }
        }
    }

    private void drawAllRows(Field[] rootFields, BufferedWriter bufferedWriter, Object toSave) throws IOException {

        for(int i = 0; i < headersNames.size(); i++){

            drawHeader(i, bufferedWriter);

            Field rootField = rootFields[i];
            handleRootFieldException(rootField, i, bufferedWriter, toSave);

            bufferedWriter.newLine();
        }
    }

    private void drawSelectedRows(Field[] rootFields, BufferedWriter bufferedWriter, Object toSave) throws IOException {

        for(int i = 0, j = 0; i < headersNames.size(); i++){

            drawHeader(i, bufferedWriter);

            if(j < toWriteHeadersIndexes.size()){

                int toWriteHeaderIndex = toWriteHeadersIndexes.get(j);

                if(i == toWriteHeaderIndex){

                    Field rootField = rootFields[toWriteHeaderIndex];
                    handleRootFieldException(rootField, i, bufferedWriter, toSave);
                    j++;
                }
            }

            bufferedWriter.newLine();
        }
    }

    private void drawHeader(int headerIndex, BufferedWriter bufferedWriter) throws IOException {

        String headerName = headersNames.get(headerIndex);

        bufferedWriter.write(BEFORE_HEADER_HEADER);
        bufferedWriter.newLine();
        bufferedWriter.write('"' + headerName + '"');
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.write(BEFORE_CONTENT_HEADER);
        bufferedWriter.newLine();
    }

    private void handleRootFieldException(Field rootField, int headerIndex, BufferedWriter bufferedWriter, Object obj){

        try {
            handleRootField(rootField, headerIndex, bufferedWriter,  obj);
        }
        catch (IllegalAccessException | IOException e) {

            e.printStackTrace();

            throw new IllegalStateException(e.getMessage());
        }
    }

    private void handleRootField(Field rootField, int headerIndex, BufferedWriter bufferedWriter, Object obj) throws IllegalAccessException, IOException {

        String headerName = headersNames.get(headerIndex);

        rootField.setAccessible(true);

        ParameterizedType parameterizedFieldType = (ParameterizedType) rootField.getGenericType();
        Class<?> actualFieldParameter = (Class<?>) parameterizedFieldType.getActualTypeArguments()[0];

        Collection<?> objCollection = (Collection<?>) rootField.get(obj);

        for(Object childObj : objCollection){

            Field[] childFields = actualFieldParameter.getDeclaredFields();

            if(writeIndexes.containsKey(headerName)){

                drawSelectedChildFields(headerName, headerIndex, childFields, bufferedWriter, childObj);
            }
            else{

                drawAllChildFields(childFields, bufferedWriter, childObj);
            }

            bufferedWriter.newLine();
        }
    }

    private void drawSelectedChildFields(String headerName, int headerIndex, Field[] childFields, BufferedWriter bufferedWriter, Object childObj) throws IOException, IllegalAccessException {

        Integer[] writeIndexesForContent = writeIndexes.get(headerName);

        int rowLength = rowsLengths.get(headerIndex);

        for(int i = 0, j = 0; i < rowLength; i++){

            if(j < writeIndexesForContent.length){

                int minimumWriteIndex = writeIndexesForContent[j];

                if(i == minimumWriteIndex){

                    Field childField = childFields[j];

                    handleDrawChildField(childField, bufferedWriter, childObj);
                    j++;
                }
            }

            if(i < rowsLengths.get(headerIndex) - 1){
                bufferedWriter.write(',');
            }
        }
    }

    private void drawAllChildFields(Field[] childFields, BufferedWriter bufferedWriter, Object childObj) throws IOException, IllegalAccessException {

        for(int i = 0; i < childFields.length; i++){

            Field childField = childFields[i];

            handleDrawChildField(childField, bufferedWriter, childObj);

            if(i < childFields.length - 1){
                bufferedWriter.write(',');
            }
        }
    }

    private void handleDrawChildField(Field childField, BufferedWriter bufferedWriter, Object childObj) throws IllegalAccessException, IOException {

        childField.setAccessible(true);

        Object value = childField.get(childObj);

        if(value != null){

            bufferedWriter.write(value.toString());
        }
    }

    @Override
    public String writeToStr(Object value) throws Exception {


        return null;
    }

    public static void main(String[] args) throws URISyntaxException, IOException {

        LinkedHashMap<String, Class<? extends EppSerializable>> schema = new LinkedHashMap<>();
        schema.put("TOWARY", Product.class);
        schema.put("CENNIK", ProductDetailedPrice.class);

        LinkedHashMap<String, Integer[]> readIndexes = new LinkedHashMap<>();
        readIndexes.put("TOWARY", new Integer[]{0, 1, 4, 11, 14});

        FileReader<ProductRelatedData> eppFileReader = new EppFileReader<>(schema, readIndexes, ProductRelatedData.class);

        ProductRelatedData productRelatedData = eppFileReader.load("data/subiekt/product.epp");
        System.out.println(productRelatedData);
    }

}
