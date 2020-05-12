package com.liaohanqi.gmall.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liaohanqi.gmall.bean.PmsSearchParam;
import com.liaohanqi.gmall.bean.PmsSearchSkuInfo;
import com.liaohanqi.gmall.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.MetricAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    JestClient jestClient;

    @Override
    public List<PmsSearchSkuInfo> search(PmsSearchParam pmsSearchParam) {

        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();

        //通过elasticSearch传入的pmsSearchParam进行dsl语句编辑（dsl语句相当于mysql的sql语句）
        String dsl = getDsl(pmsSearchParam);

        System.out.println(dsl);

        Search search= new Search.Builder(dsl).addIndex("gmallsku0615").addType("pmsSearchSkuInfo").build();

        //搜索传入的入参，需要转化为dsl语句
        //然后jestClient根据dsl语句进行响应的操作
        //执行搜索
        try {
            SearchResult searchResult = jestClient.execute(search);

            List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = searchResult.getHits(PmsSearchSkuInfo.class);

            for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
                //
                PmsSearchSkuInfo pmsSearchSkuInfo = hit.source;
                //处理相关性评分
                Double score = hit.score;
                //处理高亮显示
                Map<String, List<String>> highlight = hit.highlight;

                //高亮
                List<String> list = highlight.get("skuName");
                String skuName = list.get(0);
                pmsSearchSkuInfo.setSkuName(skuName);
                pmsSearchSkuInfos.add(pmsSearchSkuInfo);

            }

            //取聚合值
            MetricAggregation aggregations = searchResult.getAggregations();
            TermsAggregation abc = aggregations.getTermsAggregation("abc");
            List<TermsAggregation.Entry> buckets = abc.getBuckets();

            for (TermsAggregation.Entry bucket : buckets) {
                String key = bucket.getKey();
                Long count = bucket.getCount();
                System.out.println(key + "------" + count);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return pmsSearchSkuInfos;
    }

    //通过elasticSearch传入的pmsSearchParam进行dsl语句编辑（dsl语句相当于mysql的sql语句）
    private String getDsl(PmsSearchParam pmsSearchParam) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String keyword = pmsSearchParam.getKeyword();
        String[] valueIds = pmsSearchParam.getValueId();

        //三级分类参数
        if(StringUtils.isNotBlank(catalog3Id)){

            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id", catalog3Id);
            boolQueryBuilder.filter(termQueryBuilder);

        }
        //关键字参数
        if(StringUtils.isNotBlank(keyword)){

            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", keyword);
            boolQueryBuilder.must(matchQueryBuilder);

            //对关键字进行高亮
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            //高亮部分
            highlightBuilder.field("skuName");
            //哪种高亮方式
            highlightBuilder.preTags("<span style='color:red;font-weight:bolder;'>");
            highlightBuilder.postTags("</span>");
            searchSourceBuilder.highlight(highlightBuilder);
        }
        //平台属性参数
        if (valueIds != null && valueIds.length >0){

            for (String valueId : valueIds) {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId", valueId);
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }

        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);

        //对平台属性参数加入聚合函数
        TermsBuilder aggs = AggregationBuilders.terms("abc").field("skuAttrValueList.valueId");
        searchSourceBuilder.aggregation(aggs);

        //转化为elasticSearch的dsl语句，相当于mysql的sql语句
        String dsl = searchSourceBuilder.toString();

        return dsl;
    }

}
