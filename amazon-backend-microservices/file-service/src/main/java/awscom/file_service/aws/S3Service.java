package awscom.file_service.aws;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

    private final S3Client s3Client = S3Client.builder().build();
    private final String bucket = "bucket1kumarnalla";

    public String uploadFile(String fileName, InputStream inputStream) throws Exception {

        PutObjectRequest putRequest =
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .build();

        long fileSize = inputStream.available();

        RequestBody requestBody =
                RequestBody.fromInputStream(inputStream, fileSize);

        s3Client.putObject(putRequest, requestBody);

        return "Uploaded: " + fileName;
    }

    public byte[] downloadFile(String fileName) throws Exception {

        GetObjectRequest getRequest =
                GetObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .build();

        String localPath = "/tmp/" + fileName;

        GetObjectResponse response =
                s3Client.getObject(getRequest, Paths.get(localPath));

        return Files.readAllBytes(Paths.get(localPath));
    }
}
