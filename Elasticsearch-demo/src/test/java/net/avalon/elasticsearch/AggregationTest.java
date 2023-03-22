package net.avalon.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * @Author: Weiyin
 * @Create: 2023/3/22 - 9:43
 */
@SpringBootTest
public class AggregationTest {

    @Autowired
    private ElasticsearchClient client;


    /**
     * 1. 年龄分布
     * @throws IOException
     */
    @Test
    public void age() throws IOException {
        SearchResponse<Bank> response = client.search(s -> s.index("bank")
                .size(0)
                .aggregations("all_age", a -> a
                        .terms(t -> t
                                .field("age"))), Bank.class);

        List<LongTermsBucket> buckets = response.aggregations().get("all_age").lterms().buckets().array();
        buckets.forEach(System.out::println);
    }

    /**
     * 2. 性别分布
     * @throws IOException
     */
    @Test
    public void gender() throws IOException {
        SearchResponse<Bank> response = client.search(s -> s.index("bank")
                .size(0)
                .aggregations("all_gender", a -> a
                        .terms(t -> t
                                .field("gender.keyword"))), Bank.class);

        List<StringTermsBucket> buckets = response.aggregations().get("all_gender").sterms().buckets().array();
        buckets.forEach(System.out::println);
    }

    /**
     * 3. 男性中的年龄分布
     * @throws IOException
     */
    @Test
    public void m_age() throws IOException {
        SearchResponse<Bank> response = client.search(s -> s.index("bank")
                .size(0)
                .query(q -> q.match(m -> m.field("gender").query("M")))
                .aggregations("M_age_group", a -> a
                        .terms(t -> t
                                .field("age"))), Bank.class);

        List<LongTermsBucket> buckets = response.aggregations().get("M_age_group").lterms().buckets().array();
        buckets.forEach(System.out::println);
    }

    /**
     * 4. 不同性别的年龄分布
     * @throws IOException
     */
    @Test
    public void gender_avg_age() throws IOException {
        SearchResponse<Bank> response = client.search(s -> s.index("bank")
                .size(0)
                .aggregations("gender_group", a -> a
                        .terms(t -> t
                                .field("gender.keyword"))
                        .aggregations("avg_age",a1 -> a1
                                .avg(avg -> avg.field("age")))
                        .aggregations("max_age",a1 -> a1
                                .max(max -> max.field("age")))
                        .aggregations("min_age",a1 -> a1
                                .min(min -> min.field("age")))
                        ), Bank.class);

        List<StringTermsBucket> buckets = response.aggregations().get("gender_group").sterms().buckets().array();
        buckets.forEach(System.out::println);
    }

    /**
     * 5. 每种年龄的性别分布
     * @throws IOException
     */
    @Test
    public void age_gender() throws IOException {
        SearchResponse<Bank> response = client.search(s -> s.index("bank")
                .size(0)
                .aggregations("age_group", a -> a
                        .terms(t -> t
                                .field("age"))
                        .aggregations("gender_group",a1 -> a1
                                .terms(t -> t.field("gender.keyword")))
                ), Bank.class);

        List<LongTermsBucket> buckets = response.aggregations().get("age_group").lterms().buckets().array();
        buckets.forEach(System.out::println);
    }

}
