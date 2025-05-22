package pl.kamil_dywan.file.write;

import lombok.Getter;
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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class EppFileWriter<T> implements FileWriter<T>{

    private List<String> headersNames = new ArrayList<>();
    private List<Integer> toWriteHeadersIndexes = new ArrayList<>();
    private List<Integer> rowsLengths = new ArrayList<>();
    private LinkedHashMap<String, Integer[]> writeIndexes = new LinkedHashMap<>();

    private static final String BEFORE_HEADER_HEADER = "[NAGLOWEK]";
    private static final String BEFORE_INFO_HEADER = "[INFO]";
    private static final String BEFORE_CONTENT_HEADER = "[ZAWARTOSC]";

    public EppFileWriter(List<String> headersNames, List<Integer> toWriteHeadersIndexes, List<Integer> rowsLengths, LinkedHashMap<String, Integer[]> writeIndexes){

        this.headersNames = headersNames;
        this.toWriteHeadersIndexes = toWriteHeadersIndexes;
        this.rowsLengths = rowsLengths;
        this.writeIndexes = writeIndexes;
    }

    public EppFileWriter(){


    }

    @Override
    public void save(String filePath, Object toSave) throws IOException, IllegalStateException {

        File newFile = new File(filePath);

        if(!newFile.exists()){

            newFile.createNewFile();
        }

        Field[] rootFields = toSave.getClass().getDeclaredFields();

        try(BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(newFile, Charset.forName("windows-1250")))){

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

        for(int headerIndex = 0, fieldIndex = 0; headerIndex < headersNames.size(); headerIndex++, fieldIndex++){

            if(!drawHeader(headerIndex, bufferedWriter)){
                headerIndex--;

                continue;
            }

            Field rootField = rootFields[fieldIndex];
            handleRootFieldException(rootField, headerIndex, bufferedWriter, toSave);

            bufferedWriter.newLine();
        }
    }

    private void drawSelectedRows(Field[] rootFields, BufferedWriter bufferedWriter, Object toSave) throws IOException {

        for(int headerIndex = 0, cellIndex = 0; headerIndex < headersNames.size(); headerIndex++){

            if(cellIndex < toWriteHeadersIndexes.size()){

                int toWriteHeaderIndex = toWriteHeadersIndexes.get(cellIndex);

                if(headerIndex == toWriteHeaderIndex){

                    Field rootField = rootFields[toWriteHeaderIndex];
                    handleRootFieldException(rootField, headerIndex, bufferedWriter, toSave);
                    cellIndex++;
                }
            }

            bufferedWriter.newLine();
        }
    }

    private boolean drawHeader(int headerIndex, BufferedWriter bufferedWriter) throws IOException {

        String headerName = headersNames.get(headerIndex);

        bufferedWriter.write(BEFORE_HEADER_HEADER);
        bufferedWriter.newLine();

        if(headerName != null){

            bufferedWriter.write('"' + headerName + '"');
        }

        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.write(BEFORE_CONTENT_HEADER);
        bufferedWriter.newLine();

        return headerName != null;
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

        rootField.setAccessible(true);

        ParameterizedType parameterizedFieldType = (ParameterizedType) rootField.getGenericType();
        Class<?> actualFieldParameter = (Class<?>) parameterizedFieldType.getActualTypeArguments()[0];

        Object fieldObject = rootField.get(obj);

        if(fieldObject instanceof Collection<?> objCollection){

            for(Object childObj : objCollection){

                handleObjFields(headerIndex, childObj, actualFieldParameter, bufferedWriter);
            }
        }
        else{
            handleObjFields(headerIndex, fieldObject, actualFieldParameter, bufferedWriter);
        }
    }

    private void handleObjFields(int headerIndex, Object obj, Class<?> actualObjParameter, BufferedWriter bufferedWriter) throws IOException, IllegalAccessException {

        String headerName = headersNames.get(headerIndex);

        Field[] childFields = actualObjParameter.getDeclaredFields();

        if(writeIndexes.containsKey(headerName)){

            drawSelectedChildFields(headerIndex, childFields, bufferedWriter, obj);
        }
        else{

            drawAllChildFields(childFields, bufferedWriter, obj);
        }

        bufferedWriter.newLine();
    }

    private void drawSelectedChildFields(int headerIndex, Field[] childFields, BufferedWriter bufferedWriter, Object childObj) throws IOException, IllegalAccessException {

        String headerName = headersNames.get(headerIndex);

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

    public void append(EppFileWriter<?> otherFileWriter, int toWriteHeadersIndexesOffset){

        List<Integer> toWriteHeadersIndexes = otherFileWriter.toWriteHeadersIndexes.stream()
            .map(toWriteIndex -> toWriteIndex + toWriteHeadersIndexesOffset)
            .toList();

        headersNames.addAll(otherFileWriter.headersNames);
        toWriteHeadersIndexes.addAll(toWriteHeadersIndexes);
        rowsLengths.addAll(otherFileWriter.rowsLengths);
        writeIndexes.putAll(otherFileWriter.writeIndexes);
    }

    public void append(List<String> headersNames){

        this.headersNames.addAll(headersNames);
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
