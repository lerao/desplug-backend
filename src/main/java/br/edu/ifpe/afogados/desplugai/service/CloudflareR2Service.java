package br.edu.ifpe.afogados.desplugai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Locale;
import java.util.UUID;

@Service
public class CloudflareR2Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final String bucket;

    public CloudflareR2Service(
            S3Client s3Client,
            S3Presigner s3Presigner,
            @Value("${cloudflare.r2.bucket}") String bucket
    ) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.bucket = bucket;
    }

    public String armazenar(MultipartFile arquivo) {

        String extensao = obterExtensao(arquivo.getOriginalFilename());

        String chave = "documentos/" + UUID.randomUUID() + extensao;

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(chave)
                    .contentType(arquivo.getContentType())
                    .build();

            s3Client.putObject(
                    request,
                    RequestBody.fromBytes(arquivo.getBytes())
            );

            return chave;

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Não foi possível ler o arquivo para envio ao R2.", e
            );
        }
    }

    /*public String gerarUrlDownload(Arquivo arquivo, boolean inline) {
        ContentDisposition disposition = (inline
                ? ContentDisposition.inline()
                : ContentDisposition.attachment())
                .filename(arquivo.getNomeOriginal(), StandardCharsets.UTF_8)
                .build();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(arquivo.getChaveArmazenamento())
                .responseContentDisposition(disposition.toString())
                .responseContentType(arquivo.getMimeType())
                .build();

        GetObjectPresignRequest presignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(5))
                        .getObjectRequest(getObjectRequest)
                        .build();

        PresignedGetObjectRequest presignedRequest =
                s3Presigner.presignGetObject(presignRequest);

        return presignedRequest.url().toString();
    }

    @Override
    public void excluir(String chaveArmazenamento) {

        if (chaveArmazenamento == null || chaveArmazenamento.isBlank()) {
            throw new IllegalArgumentException(
                    "A chave de armazenamento não pode ser vazia."
            );
        }

        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(chaveArmazenamento)
                .build();

        s3Client.deleteObject(request);
    }*/

    private String obterExtensao(String nomeOriginal) {

        if (nomeOriginal == null || !nomeOriginal.contains(".")) {
            return "";
        }

        String extensao = nomeOriginal.substring(
                nomeOriginal.lastIndexOf(".")
        ).toLowerCase(Locale.ROOT);

        // Evita inserir uma extensão excessivamente grande na chave.
        if (extensao.length() > 15) {
            return "";
        }

        return extensao;
    }
}
