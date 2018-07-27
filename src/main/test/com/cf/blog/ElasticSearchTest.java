package com.cf.blog;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import static org.elasticsearch.common.xcontent.XContentFactory.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * 查询类型:match查询
 *         GET /megacorp/employee/_search  {"query" : {"match" : {"last_name" : "Smith"}}}
 *         {"query" : {"bool": {"must": [{"match": {"last_name": "smith"}}],"filter": {"range": {"age": {"gte": 10,"lte": 35}}}}}}
 *         {
 *          全文搜索："query" : {"match" : {"about" : "rock climbing"}}} //match_phrase精确匹配短语
 *          高亮显示："highlight": {"fields": {"about": {}}}
 * aggregations聚合（类似sql中的group by）：
 *      {"aggs": {"all_interests": {"terms": { "field": "interests" }}}}
 *      {"aggs": {"兴趣": {"terms": {"field": "interests.keyword","size": 10},"aggs": {"平均年龄": {"avg": {"field": "age"}}}}}}
 */

/**
 * 集群健康： GET /_cluster/health
 * green 所有的主分片和副本分片都正常运行。
 * yellow 所有的主分片都正常运行，但不是所有的副本分片都正常运行。
 * red 有主分片没能正常运行。
 */

/** 分片
 * 分配3个主分片和一份副本（每个主分片拥有一个副本分片）
 * PUT /blogs {"settings" : {"number_of_shards" : 3,"number_of_replicas" : 1}}
 *
 * 在运行中的集群上是可以动态调整副本分片数目的 PUT /blogs/_settings {"number_of_replicas" : 2}
 */
////////////////////////////////////////在 Elasticsearch 中， 每个字段的所有数据 都是 默认被索引的 。//////////////////////
/////////////////////////////在 Elasticsearch 中每个文档都有一个版本号。当每次对文档进行修改时（包括删除）， _version 的值会递增。 //////////

/**
 * 返回文档的一部分编辑
 * 默认情况下， GET 请求 会返回整个文档，这个文档正如存储在 _source 字段中的一样。
 * 但是也许你只对其中的 title 字段感兴趣。单个字段能用 _source 参数请求得到，多个字段也能使用逗号分隔的列表来指定。
 * GET /website/blog/123?_source=title,text
 *
 * 或者，如果你只想得到 _source 字段，不需要任何元数据，你能使用 _source 端点：
 * GET /website/blog/123/_source
 */

///在Elasticsearch中文档是不可改变的，不能修改它们。相反，如果想要更新现有的文档，需要重建索引或者进行替换，我们可以使用相同的 index API 进行实现
/// 增加了 _version 字段值    created 标志设置成 false ，是因为相同的索引、类型和 ID 的文档已经存在
/// 尽管你不能再对旧版本的文档进行访问，但它并不会立即消失。当继续索引更多的数据，Elasticsearch 会在后台清理这些已删除文档。

/**
 * 当我们索引一个文档， 怎么确认我们正在创建一个完全新的文档，而不是覆盖现有的呢？
 * 请记住， _index 、 _type 和 _id 的组合可以唯一标识一个文档。所以，确保创建一个新文档的最简单办法是，使用索引请求的 POST 形式让 Elasticsearch 自动生成唯一 _id :
 * POST /website/blog/ { ... }
 * 然而，如果已经有自己的 _id ，那么我们必须告诉 Elasticsearch ，只有在相同的 _index 、 _type 和 _id 不存在时才接受我们的索引请求。这里有两种方式，他们做的实际是相同的事情。使用哪种，取决于哪种使用起来更方便。
 *
 * 第一种方法使用 op_type 查询 -字符串参数：
 * PUT /website/blog/123?op_type=create { ... }
 * 第二种方法是在 URL 末端使用 /_create :
 * PUT /website/blog/123/_create { ... }
 */

/**
 * 处理冲突编辑：
 *  悲观并发控制；
 *  乐观并发控制： Elasticsearch 中使用的这种方法假定冲突是不可能发生的，并且不会阻塞正在尝试的操作。
 *                 然而，如果源数据在读写当中被修改，更新将会失败。应用程序接下来将决定该如何解决冲突。
 *                 例如，可以重试更新、使用新的数据、或者将相关情况报告给用户。
 *  可以利用 _version 号来确保应用中相互冲突的变更不会导致数据丢失。
 *  我们通过指定想要修改文档的 version 号来达到这个目的。 如果该版本不是当前版本号，我们的请求将会失败。
 *  PUT /website/blog/1?version=1
 * {
 *   "title": "My first blog entry",
 *   "text":  "Starting to get the hang of this..."
 * }
 */

/**
 * 取回多个文档 GET /_mget
 * {"docs" : [
 *       {
 *          "_index" : "website",
 *          "_type" :  "blog",
 *          "_id" :    2
 *       },
 *       {
 *          "_index" : "website",
 *          "_type" :  "pageviews",
 *          "_id" :    1,
 *          "_source": "views"
 *       }]}
 */

/**
 * 代价较小的批量操作
 * 与 mget 可以使我们一次取回多个文档同样的方式，
 * bulk API允许在单个步骤中进行多次create、 index、 update或delete请求。如果你需要索引一个数据流比如日志事件，它可以排队和索引数百或数千批次。
 */
//https://www.elastic.co/guide/cn/elasticsearch/guide/current/mapping-analysis.html
    ////分页   ： GET /_search?size=5&from=10

/**
 * 映射：
 *      索引一个包含新域的文档--之前未曾出现-- Elasticsearch 会使用 动态映射 ，通过JSON中基本数据类型，尝试猜测域类型。
 *      GET /megacorp/employee/_search
 *      GET /megacorp/_mapping/employee
 *
 *      PUT /gb {"mappings": {     ---------创建索引 指定映射
 *     "tweet" : {
 *       "properties" : {
 *         "tweet" : {
 *           "type" :    "string",
 *           "analyzer": "english"
 *         },
 *         "date" : {
 *           "type" :   "date"
 *         },
 *         "name" : {
 *           "type" :   "string"
 *         },
 *         "user_id" : {
 *           "type" :   "long"
 *         }}}}}
 *       新增映射：PUT /gb/_mapping/tweet
 * {
 *   "properties" : {
 *     "tag" : {
 *       "type" :    "string",
 *       "index":    "not_analyzed"
 *     }
 *   }
 * }
 */

/**
 * match_all查询：
 *      match_all 查询简单的匹配所有文档。在没有指定查询方式时，它是默认的查询
 * match查询：
 *      无论你在任何字段上进行的是全文搜索还是精确查询，match 查询是你可用的标准查询，
 *      如果你在一个全文字段上使用 match 查询，在执行查询前，它将用正确的分析器去分析查询字符串，
 *      如果在一个精确值的字段上使用它， 例如数字、日期、布尔或者一个 not_analyzed 字符串字段，那么它将会精确匹配给定的值
 *      （对于精确值的查询，你可能需要使用 filter 语句来取代 query，因为 filter 将会被缓存）
 * multi_match查询：
 *      multi_match查询可以在多个字段上执行相同的 match 查询：
 *      {"multi_match": {"query": "full text search","fields": [title", "body"]}}
 * range查询：
 *      rang查询找出那些落在指定区间内的数字或者时间：
 *      {"range": {"age": {"gte":  20,"lt":   30}}}
 * term 查询：
 *      term 查询被用于精确值 匹配，这些精确值可能是数字、时间、布尔或者那些 not_analyzed 的字符串：
 *      { "term": { "age":    26           }}
 *      { "term": { "date":   "2014-09-01" }}
 *      { "term": { "public": true         }}
 *      { "term": { "tag":    "full_text"  }}
 *      term查询对于输入的文本  不分析 ，所以它将给定的值进行精确查询。
 * terms 查询：
 *      terms 查询和 term 查询一样，但它允许你指定多值进行匹配。如果这个字段包含了指定值中的任何一个值，那么这个文档满足条件：
 *      {"terms": { "tag": [ "search", "full_text", "nosql" ] }}
 * exists 查询和 missing 查询：
 *      exists 查询和 missing 查询被用于查找那些指定字段中有值 (exists) 或无值 (missing) 的文档。这与SQL中的 IS_NULL (missing) 和 NOT IS_NULL (exists) 在本质上具有共性：
 *      {"exists":   {"field":    "title"}}
 * 组合多查询：
 *      可以用 bool 查询来实现需求。这种查询将多查询组合在一起，成为用户自己想要的布尔查询。它接收以下参数：
 *      must：文档 必须 匹配这些条件才能被包含进来。
 *      must_not：文档 必须不 匹配这些条件才能被包含进来。
 *      should：如果满足这些语句中的任意语句，将增加 _score ，否则，无任何影响。它们主要用于修正每个文档的相关性得分。
 *      filter：必须匹配，但它以不评分、过滤模式来进行。这些语句对评分没有贡献，只是根据过滤标准来排除或包含文档。
 */
//通常当查找一个精确值的时候，我们不希望对查询进行评分计算。只希望对文档进行包括或排除的计算，
//        所以我们会使用 constant_score 查询以非评分模式来执行 term 查询并以一作为统一评分。
//        最终组合的结果是一个 constant_score 查询，它包含一个 term 查询：
//
//        GET /my_store/products/_search
//        {
//        "query" : {
//        "constant_score" : {
//        "filter" : {
//        "term" : {
//        "price" : 20
//        }
//        }
//        }
//        }
//        }

/**
 * 为了避免这种问题，我们需要告诉 Elasticsearch 该字段具有精确值，要将其设置成 not_analyzed 无需分析的
 * PUT /my_store
 * {
 *     "mappings" : {
 *         "products" : {
 *             "properties" : {
 *                 "productID" : {
 *                     "type" : "string",
 *                     "index" : "not_analyzed"
 *                 }
 *             }
 *         }
 *     }
 * }
 */
public class ElasticSearchTest {
    TransportClient client = null;
    @Before
    public void init() throws UnknownHostException {
        client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
    }
    @After
    public void after(){
        client.close();
        client = null;
    }

    @Test
    public void elasticClient() throws IOException {
//        XContentBuilder builder = jsonBuilder()
//                .startObject()
//                .field("first_name", "Douglas")
//                .field("last_name", "Fir")
//                .field("age", 36)
//                .field("about", "I like to build cabinets")
//                .field("interests", new String[]{"forestry"})
//                .endObject();
//        IndexResponse response = client.prepareIndex("megacorp", "employee", "4")
//                .setSource(builder).get(); // 索引文档
//        System.out.println(response.getIndex());

//        DeleteResponse response2 = client.prepareDelete("megacorp", "employee", "4").get(); //删除

//        GetResponse response3 = client.prepareGet("megacorp", "employee", "4").get(); //获取
//        System.out.println(response3.getSourceAsString());
    }

    @Test
    public void elasticBulk() throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        bulkRequest.add(client.prepareIndex("twitter", "tweet", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy1")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch1")
                        .endObject()
                )
        );

        bulkRequest.add(client.prepareIndex("twitter", "tweet", "2")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy2")
                        .field("postDate", new Date())
                        .field("message", "another post2")
                        .endObject()
                )
        );

        BulkResponse bulkResponse = bulkRequest.get();
        System.out.println(bulkResponse.hasFailures());
        BulkItemResponse[] items = bulkResponse.getItems();
        for (BulkItemResponse item : items) {
            System.out.println(item.getIndex() + " --> " + item.getId());
        }
    }
}
