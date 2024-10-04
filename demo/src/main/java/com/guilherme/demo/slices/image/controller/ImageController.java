package com.guilherme.demo.slices.image.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.guilherme.demo.slices.image.dto.ImageDTO;
import com.guilherme.demo.slices.image.entity.Image;
import com.guilherme.demo.slices.image.enums.ImageExtencion;
import com.guilherme.demo.slices.image.mapper.ImagesMapper;
import com.guilherme.demo.slices.image.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;



/*
 * URL api azure https://pjbl.ashypond-b411e96c.eastus2.azurecontainerapps.io
 */
@RestController
@RequestMapping("/images")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
public class ImageController {

    private final ImageService service;
    private final ImagesMapper mapper;

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) throws IOException{
       
        
        var possibleImage = service.deleteById(id);
        System.out.println(possibleImage);
        if (possibleImage){
            //http status 404 
            return ResponseEntity.notFound().build();
        }
        
        //http status 200
        return new ResponseEntity<>("Delete image", 
        HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<byte[]> replaceImage(
        @RequestParam("file") MultipartFile file,
        @RequestParam("name")String name,
        @RequestParam("tags") List<String> tags,
        @PathVariable String id
    ) throws IOException{
        var possibleImage = service.findById(id);
        if (possibleImage.isEmpty()){
            //http status 404 
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        Image image = mapper.mapToImage(file, name, tags);
        Image savedImage = service.save(image);
        URI imageUri = buildImageUri(savedImage);
        //http status 201
        return ResponseEntity.created(imageUri).build();
     
       
    }

    @PostMapping()
    public ResponseEntity<String> save(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name")String name,
            @RequestParam("tags") List<String> tags
        ) throws IOException{

        log.info("Imagem recebida: name {}, size {}", file.getOriginalFilename(), file.getSize());
      
       
       
        Image image = mapper.mapToImage(file, name, tags);
        Image savedImage = service.save(image);
        URI imageUri = buildImageUri(savedImage);
        //http status 201
        return ResponseEntity.created(imageUri).build();
    }
    @GetMapping("{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id){
        var possibleImage = service.findById(id);
        if (possibleImage.isEmpty()){
            //http status 404 
            return ResponseEntity.notFound().build();
        }
        var image = possibleImage.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(image.getExtencion().getMediaType());
        headers.setContentLength(image.getSize());
        //inline; filename ="image.PNG"
        headers.setContentDispositionFormData("inline; filename=\"" + image.getFileName() + "\"", image.getFileName());
        //http status 200
        return new ResponseEntity<>(image.getImageFileName(), headers, HttpStatus.OK);
    }

    //http://localhost:8080/images?extencio=PNG&query=Nature

    @GetMapping
    public ResponseEntity<List<ImageDTO>> search(
                @RequestParam(value = "extencion", required = false, defaultValue = "") String extencion,
                @RequestParam(value = "query", required = false)String query){

        var result = service.search(ImageExtencion.ofName(extencion), query);

        var images = result.stream().map(image -> {
            var url = buildImageUri(image);
            return mapper.imageToDto(image, url.toString());
        }).collect(Collectors.toList());
        
        return ResponseEntity.ok(images);
    }

    private URI buildImageUri(Image image){
        String imagePath = "/" + image.getId();
        return ServletUriComponentsBuilder
                    .fromCurrentRequestUri()
                    .path(imagePath).build().toUri();
    }

}
