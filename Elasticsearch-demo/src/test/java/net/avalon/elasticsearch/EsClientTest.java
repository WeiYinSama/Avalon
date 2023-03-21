package net.avalon.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortMode;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.SourceConfig;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.util.ObjectBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @Author: Weiyin
 * @Create: 2023/3/16 - 7:33
 */
@SpringBootTest
public class EsClientTest {

    @Autowired
    private ElasticsearchClient client;

    private static final String INDEX = "bank";

    /**
     * 1. 索引单个文档
     *
     * @throws IOException
     */
    @Test
    public void indexSingleDoc() throws IOException {

        Wife wife = new Wife("lacia", 16, "weiyin の 老婆~", List.of("可爱"));
        IndexResponse response = client.index(i -> i.index("wife")
//                .id()
                .document(wife));
        System.out.println(response);
    }

    /**
     * 2. 索引多个文档
     *
     * @throws IOException
     */
    @Test
    public void indexMultipleDocs() throws IOException {

        Wife w1 = new Wife("胡桃", 16, "weiyin の 老婆~", List.of("嗷~"));
        Wife w2 = new Wife("里想奈", 14, "weiyin の 老婆~", List.of("古灵精怪"));
        List<Wife> list = List.of(w1, w2);

        BulkRequest.Builder br = new BulkRequest.Builder();
        for (Wife w : list) {
            br.operations(op -> op.index(i -> i.index("wife")
//                    .id("")
                            .document(w)
            ));
        }
        BulkResponse result = client.bulk(br.build());
        System.out.println(result);
    }

    /**
     * 3. 根据id查找文档
     *
     * @throws IOException
     */
    @Test
    public void findById() throws IOException {

        GetResponse<Wife> response = client.get(g -> g
                        .index("wife")
                        .id("3"),
                Wife.class
        );

        if (response.found()) {
            Wife wife = response.source();
            System.out.println(wife);
        } else {
            System.out.println("Not found");
        }
    }

    /**
     * 4. 检索某个索引下的所有文档
     *
     * @throws IOException
     */
    @Test
    public void findAll() throws IOException {
        SearchResponse<Wife> response = client.search(s -> s.index("wife"), Wife.class);
        System.out.println(response.hits().total());
        response.hits().hits().forEach(System.out::println);
    }

    /**
     * 5. 删除某个文档
     *
     * @throws IOException
     */
    @Test
    public void deleteById() throws IOException {
        DeleteResponse response = client.delete(d -> d.index("bank").id("6"));
        System.out.println(response);
    }

    /**
     * 6. 分页、投影（select ...）、排序
     *
     * @throws IOException
     */
    @Test
    public void findByCondition() throws IOException {
        SearchResponse<Bank> response = client.search(s -> s.index("bank")
                //分页
                .from(0)
                .size(10)
                //投影
                .source(c -> c.filter(f -> f.excludes("city")))
                //排序
                .sort(op -> op.field(fs -> fs.field("balance").order(SortOrder.Desc))), Bank.class);
        response.hits().hits().forEach(System.out::println);
    }

    /**
     * 7. 多级排序
     *
     * @throws IOException
     */
    @Test
    public void sort2() throws IOException {
        //年龄降序，余额降序

        SortOptions.Builder b1 = new SortOptions.Builder();
        SortOptions age = b1.field(f -> f.field("age").order(SortOrder.Desc)).build();
        SortOptions.Builder b2 = new SortOptions.Builder();
        SortOptions balance = b2.field(f -> f.field("balance").order(SortOrder.Desc)).build();

        SearchResponse<Bank> response = client.search(search -> search
                .sort(age, balance)
                .size(10), Bank.class);
        response.hits().hits().forEach(System.out::println);
    }

    /**
     * 8. match
     *
     * @throws IOException
     */
    @Test
    public void match() throws IOException {
        SearchResponse<Bank> response = client.search(search -> search.index("bank")
                .query(query -> query
                        .match(match -> match
                                .field("lastname")
                                .query("smith"))), Bank.class);
        response.hits().hits().forEach(System.out::println);
    }

    /**
     * 9. match_phrase 短语搜索，不会被分割
     *
     * @throws IOException
     */
    @Test
    public void match_phrase() throws IOException {
        SearchResponse<Bank> response = client.search(s -> s.index("bank")
                .query(q -> q
                        .matchPhrase(m -> m
                                .field("address")
                                .query("Holmes Lane"))), Bank.class);
        response.hits().hits().forEach(System.out::println);
    }

    /**
     * 10. multi_match 多字段匹配
     *
     * @throws IOException
     */
    @Test
    public void multi_match() throws IOException {
        SearchResponse<Bank> response = client.search(s -> s.index("bank")
                .query(q -> q
                        .multiMatch(m -> m
                                .query("place")
                                .fields("address", "city", "firstname", "lastname"))), Bank.class);
        response.hits().hits().forEach(System.out::println);
    }

    /**
     * 11. range 范围搜索
     *
     * @throws IOException
     */
    @Test
    public void range() throws IOException {
        SearchResponse<Bank> response = client.search(s -> s.index("bank")
                .query(q -> q
                        .range(r -> r
                                .field("age")
                                .gte(JsonData.of(30))
                                .lt(JsonData.of(40)))), Bank.class);
        System.out.println(response.hits().total());
        response.hits().hits().forEach(System.out::println);
    }

    /**
     * 12. term 精确值匹配
     *
     * @throws IOException
     */
    @Test
    public void term() throws IOException {
        SearchResponse<Bank> response = client.search(s -> s.index("bank")
                .query(q -> q
                        .term(t -> t
                                .field("age")
                                .value(25))), Bank.class);
        System.out.println(response.hits().total());
        response.hits().hits().forEach(System.out::println);
    }

    /**
     * 13. terms 精确值匹配，允许指定多个值
     *
     * @throws IOException
     */
    @Test
    public void terms() throws IOException {
        List<FieldValue> values = new ArrayList<>();
        List.of(25, 32, 35, 40).forEach(e -> {
            FieldValue.Builder b = new FieldValue.Builder();
            FieldValue v = b.longValue(e).build();
            values.add(v);
        });
        SearchResponse<Bank> response = client.search(s -> s.index("bank")
                .query(q -> q
                        .terms(t -> t
                                .field("age")
                                .terms(v -> v.value(values)))), Bank.class);
        System.out.println(response.hits().total());
        response.hits().hits().forEach(System.out::println);
    }


    /**
     * 14. exists，查找address不为null的文档
     *
     * @throws IOException
     */
    @Test
    public void exists() throws IOException {
        SearchResponse<Bank> response = client.search(s -> s.index("bank")
                .query(q -> q
                        .exists(e -> e
                                .field("address"))), Bank.class);
        System.out.println(response.hits().total());
        response.hits().hits().forEach(System.out::println);
    }

    /**
     * 15. bool - 年龄24，余额 > 3w
     * @throws IOException
     */
    @Test
    public void bool() throws IOException {
        SearchResponse<Bank> response = client.search(s -> s.index("bank")
                .query(q -> q
                        .bool(b -> b
                                .must(q1 -> q1.term(t -> t.field("age").value(24)))
                                .must(q2 -> q2.range(r -> r.field("balance").gt(JsonData.of(30000)))))), Bank.class);
        System.out.println(response.hits().total());
        response.hits().hits().forEach(System.out::println);
    }

    /**
     * 16. bool - 姓Smith，年龄 > 30
     * @throws IOException
     */
    @Test
    public void bool2() throws IOException {
        SearchResponse<Bank> response = client.search(s -> s.index("bank")
                .query(q -> q
                        .bool(b -> b
                                .must(q1 -> q1.match(m -> m.field("lastname").query("smith")))
                                .filter(f -> f.range(r -> r.field("age").gt(JsonData.of(30)))))), Bank.class);
        System.out.println(response.hits().total());
        response.hits().hits().forEach(System.out::println);
    }

    /**
     * 17. bool - 组合查询，性别女、不姓Meyers，年龄最好32
     * @throws IOException
     */
    @Test
    public void bool3() throws IOException {
        SearchResponse<Bank> response = client.search(s -> s.index("bank")
                .query(q -> q
                        .bool(b -> b
                                .must(m -> m.match(match -> match.field("gender").query("F")))
                                .mustNot(q1 -> q1.match(m1 -> m1.field("lastname").query("Meyers")))
                                .should(s1 -> s1.term(t -> t.field("age").value(32))))), Bank.class);
        System.out.println(response.hits().total());
        response.hits().hits().forEach(System.out::println);
    }

    @Test
    public void test() throws IOException {

    }

    @Test
    public void getDocById() throws IOException {

        GetResponse<Employee> response = client.get(g -> g
                .index("employee")
                .id("100"), Employee.class);

        if (response.found()) {
            Employee source = response.source();
            System.out.println(source);
        } else {
            System.out.println("Employee not found");
        }
    }

    @Test
    public void getAllDoc() throws IOException {


//        SearchResponse<Employee> search = client.search(s -> s.index("employee").query(q -> q.match(m -> m.field("firstname").query("John"))), Employee.class);
//        System.out.println(search.hits().total());
//        search.hits().hits().forEach(System.out::println);

        SearchResponse<Wife> response = client.search(s -> s.index("wife")
                .q("weiyin的老婆"), Wife.class);

        response.hits().hits().forEach(System.out::println);
        System.out.println(response.took());


//        SearchResponse<Employee> search1 = client.search(sr -> sr.index("employee"), Employee.class);
//        System.out.println(search1.hits().total());
//        search1.hits().hits().forEach(System.out::println);

    }
}
