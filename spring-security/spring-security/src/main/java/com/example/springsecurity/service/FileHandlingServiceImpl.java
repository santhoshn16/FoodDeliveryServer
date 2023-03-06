package com.example.springsecurity.service;

import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.nio.file.Files;

@Service
public class FileHandlingServiceImpl implements FileHandlingService{
    private final Path root = Paths.get("uploads");
    @Override
    public void init() {
        try{
            Files.createDirectories(root);
        }catch (IOException e){
            throw new RuntimeException("Could not initiliaze folder uploads");
        }
    }

    @Override
    public void save(MultipartFile file) {
        try{
            Files.copy(file.getInputStream(),this.root.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            if(e instanceof FileAlreadyExistsException){
                throw new RuntimeException("File already exists");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try{
            Path path = root.resolve(filename);
            Resource resource = new UrlResource(filename);
            if (resource.exists() || resource.isReadable()){
                return resource;
            } else {
                throw new RuntimeException("Could not load file");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error : "+ e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load files");
        }
    }
}
