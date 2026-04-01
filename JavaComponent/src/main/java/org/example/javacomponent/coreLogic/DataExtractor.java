package org.example.javacomponent.coreLogic;

public class DataExtractor {
    private String directory;

    public DataExtractor(String directory){
        this.directory = directory;
    }

    public void print(){
        System.out.println(this.directory);
    }

}
