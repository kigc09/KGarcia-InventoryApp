package com.example.kgarciainventoryapp.Domain;

import java.util.Base64;

public class Image {
    private String name;
    private String encoding;
    private byte[] contents;

    public Image(){
        name = "";
        encoding = "";
        contents = new byte[0];
    }

    public Image(String name, String encoding, byte[] contents){
        this.name = name;
        this.encoding = encoding;
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getDataUri() {
        return "data:" + encoding + ";base64," + Base64.getEncoder().encodeToString(contents);
    }
}
