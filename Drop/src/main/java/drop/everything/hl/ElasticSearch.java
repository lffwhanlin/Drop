package drop.everything.hl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.alibaba.fastjson.JSONObject;

public class ElasticSearch {
    
    /**
     * 建立索引,索引建立好之后,会在elasticsearch-0.20.6\data\elasticsearch\nodes\0创建所以你看
     * @param indexName  为索引库名，一个es集群中可以有多个索引库。 名称必须为小写
     * @param indexType  Type为索引类型，是用来区分同索引库下不同类型的数据的，一个索引库下可以有多个索引类型。
     * @param jsondata   json格式的数据集合
     * 
     * @return
     */
    public void createIndexResponse(String indexname, String type, List<String> jsondata){
        //创建索引库 需要注意的是.setRefresh(true)这里一定要设置,否则第一次建立索引查找不到数据
    	Client client = getClient();
        IndexRequestBuilder requestBuilder = client.prepareIndex(indexname, type).setRefresh(true);
        for(int i=0; i<jsondata.size(); i++){
            requestBuilder.setSource(jsondata.get(i)).execute().actionGet();
        }     
        client.close(); 
    }
    
    /**
     * 创建索引
     * @param client
     * @param jsondata
     * @return
     */
    public IndexResponse createIndexResponse(String indexname, String type,String jsondata){
    	Client client = getClient();
        IndexResponse response = client.prepareIndex(indexname, type).setSource(jsondata).execute().actionGet();
        client.close();
        return response;
    }
    
    /**
     * 执行搜索
     * @param queryBuilder
     * @param indexname
     * @param type
     * @return
     */
    public SearchHits  search(QueryBuilder queryBuilder, String indexname, String type){
    	Client client = getClient();
        SearchResponse searchResponse = client.prepareSearch(indexname).setTypes(type)
        .setQuery(queryBuilder)
        .execute()
        .actionGet();
        SearchHits hits = searchResponse.getHits();
        client.close();
        return hits;
    }
    
    
    public static void main(String[] args) {
    	ElasticSearch es = new ElasticSearch();
    	//List<String> persons = es.getPersonIndex();
    	//es.createIndexResponse("index_study", "person", persons);
    	MatchQueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("name", "车嵌");
    	SearchHits hits = es.search(queryBuilder, "index_study", "person");
    	SearchHit[] searchHit = hits.getHits();
    	System.out.println("命中条数："+hits.getTotalHits());
    	if(searchHit!=null&&searchHit.length>0){
    		for(SearchHit hit : searchHit){
    			System.out.println(hit.sourceAsString());
    		}
    		
    	}
    }
    
    public Client getClient(){
    	Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("121.201.13.251", 9300));
    	return client;
    }

    
    public List<String> getPersonIndex(){
    	List<String> persons = new ArrayList<String>();
    	for(int i=0;i<100;i++){
    		JSONObject object = new JSONObject();
    		object.put("id", UUID.randomUUID().toString());
    		object.put("name",""+RandomHan.getRandomHan()+RandomHan.getRandomHan());
    		object.put("age", new Random().nextInt(80));
    		persons.add(object.toJSONString());
    	}
    	return persons;
    }
    
    static class RandomHan {
        private static Random ran = new Random();
        private final static int delta = 0x9fa5 - 0x4e00 + 1;
          
        public static  char getRandomHan() {
            return (char)(0x4e00 + ran.nextInt(delta)); 
        }
    }
}
