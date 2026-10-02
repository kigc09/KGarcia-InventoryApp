package com.example.kgarciainventoryapp.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;

import java.util.Base64;

@Embeddable
public class Image {
    private String imageName;
    private String encoding;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] contents;

    public Image(){
        imageName = "";
        encoding = "";
        contents = new byte[0];
    }

    public Image(String name, String encoding, byte[] contents){
        this.imageName = name;
        this.encoding = encoding;
        this.contents = contents;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String name) {
        this.imageName = name;
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
