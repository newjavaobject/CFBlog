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
