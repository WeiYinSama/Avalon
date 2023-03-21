package net.avalon.elasticsearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;

/**
 * @Author: Weiyin
 * @Create: 2023/3/16 - 3:29
 */
@Configuration
public class ElasticsearchConfig {

    @Bean
    public ElasticsearchClient elasticsearchClient() {

        //通过 openssl x509 -fingerprint -sha256 -in config/certs/http_ca.crt 拿到指纹
        String fingerprint = "02:4E:8D:50:A9:59:4C:0F:4A:BA:CA:B3:61:F0:5F:D4:05:3C:68:B6:84:1D:AF:25:FD:ED:B0:67:AD:4A:52:7D";
        SSLContext sslContext = TransportUtils.sslContextFromCaFingerprint(fingerprint);

        //通过 elasticsearch-reset-password -u elastic 重置密码
        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(
                AuthScope.ANY, new UsernamePasswordCredentials("elastic", "gJ7dbOG31BXjhEo*jT_9")
        );

        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "https")
        ).setHttpClientConfigCallback(
                hc -> hc.setSSLContext(sslContext)
                        .setDefaultCredentialsProvider(credsProv)
        ).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);

    }

}
