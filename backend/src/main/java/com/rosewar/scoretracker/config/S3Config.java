package com.rosewar.scoretracker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
    @Value("${aws.s3.access-key}")
    private String accessKey;

    @Value("${aws.s3.secret-key}")
    private String secretKey;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                accessKey,
                secretKey
        );

        return S3Client.builder()
                .region(Region.AP_NORTHEAST_2) // 필요한 AWS 리전을 설정하세요 (예: 서울 리전)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }
}
