package com.example.kgarciainventoryapp.api;

import com.example.kgarciainventoryapp.Domain.Image;

import java.util.Base64;

public class ImageResponseDTO {
    private String imageName;
    private String encoding;
    private String contents;

    public ImageResponseDTO() {}

    public static ImageResponseDTO from(Image image){
        if(image == null || image.getContents() == null || image.getContents().length ==0){
            return null;
        }

        ImageResponseDTO responseDTO = new ImageResponseDTO();
        responseDTO.setImageName(image.getImageName());
        responseDTO.setEncoding(image.getEncoding());
        responseDTO.setContents(Base64.getEncoder().encodeToString(image.getContents()));

        return responseDTO;
    }

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
