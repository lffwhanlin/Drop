package drop.everything.hl;

import java.util.List;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class ElasticSearch {
    
    /**
     * 建立索引,索引建立好之后,会在elasticsearch-0.20.6\data\elasticsearch\nodes\0创建所以你看
     * @param indexName  为索引库名，一个es集群中可以有多个索引库。 名称必须为小写
     * @param indexType  Type为索引类型，是用来区分同索引库下不同类型的数据的，一个索引库下可以有多个索引类型。
     * @param jsondata   json格式的数据集合
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
        .setQuery(queryBuilder).setSize(10)
        .execute()
        .actionGet();
        SearchHits hits = searchResponse.getHits();
        client.close();
        return hits;
    }
    
    
    public static void main(String[] args) {
    	ElasticSearch es = new ElasticSearch();
    	//es.addData();
    	//es.boolQuery();
    	es.termQuery();
    }
    
    public void addData(){
    	List<String> persons = DataUtil.productPerson();
    	createIndexResponse("index_study", "person", persons);
    }
    
    public void termQuery(){
    	TermQueryBuilder query = QueryBuilders.termQuery("name", "韩");
    	System.out.println(query.toString());
    	SearchHits hits = search(query, "index_study", "person");
    	SearchHit[] searchHit = hits.getHits();
    	System.out.println("命中条数："+hits.getTotalHits());
    	if(searchHit!=null&&searchHit.length>0){
    		for(SearchHit hit : searchHit){
    			System.out.println(hit.sourceAsString()+"	"+hit.getScore());
    		}
    		
    	}
    }
    
    /**
     * 2015年8月6日
     * 下午5:52:58
     * TODO 多条件查询
     */
    public void boolQuery(){
    	BoolQueryBuilder query = QueryBuilders.boolQuery();
    	query.must(QueryBuilders.matchPhraseQuery("mark", "美女"));
    	query.mustNot(QueryBuilders.matchPhraseQuery("mark", "看书"));
    	query.should(QueryBuilders.matchPhraseQuery("mark", "美食"));
    	query.should(QueryBuilders.matchPhraseQuery("mark", "电影"));
    	System.out.println(query.toString());
    	SearchHits hits = search(query, "index_study", "person");
    	SearchHit[] searchHit = hits.getHits();
    	System.out.println("命中条数："+hits.getTotalHits());
    	if(searchHit!=null&&searchHit.length>0){
    		for(SearchHit hit : searchHit){
    			System.out.println(hit.sourceAsString()+"	"+hit.getScore());
    		}
    		
    	}
    }
    
    public Client getClient(){
    	Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("121.201.13.251", 9300));
    	return client;
    }

    
}
