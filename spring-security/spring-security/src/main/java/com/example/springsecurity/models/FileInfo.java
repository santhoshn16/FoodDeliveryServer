package com.example.springsecurity.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class FileInfo {
    private String name;
    private String url;

    public FileInfo(String filename, String url) {
        this.name = filename;
        this.url = url;
    }
}
