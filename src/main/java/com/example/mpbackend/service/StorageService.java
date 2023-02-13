package com.example.mpbackend.service;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class StorageService {

    private final AmazonS3 space;

    public StorageService() {

        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials("DO00CJ44DXBEH3P98LPN", "JpuNMD7D8O6DZUPgVdz2DvUgWcbP7x/u/ZzwpSzBT5c")
        );

        space = AmazonS3ClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("fra1.digitaloceanspaces.com", "fra1")
                )
                .build();

    }

    public void songUpload(MultipartFile song){
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(song.getContentType());
            space.putObject(new PutObjectRequest("mp-spacebucket", Objects.requireNonNull(song.getOriginalFilename()).replaceAll(" ",""), song.getInputStream(),metadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
