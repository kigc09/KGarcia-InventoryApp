package com.example.kgarciainventoryapp.api;

public class ImageRequestDTO {
    private String imageName;
    private String encoding;
    private String contents;

    public ImageRequestDTO(){}

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
