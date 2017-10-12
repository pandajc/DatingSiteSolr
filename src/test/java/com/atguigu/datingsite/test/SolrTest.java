package com.atguigu.datingsite.test;

import com.atguigu.datingsite.bean.TUser;
import com.atguigu.datingsite.service.TUserService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.*;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-beans.xml",
        "classpath:spring-mybatis.xml",
        "classpath:spring-tx.xml"})
public class SolrTest {

    @Autowired
    private TUserService tUserService;

    @Autowired
    private SolrServer server;

    @Test
    public void testAdd() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "101010");
        document.addField("nick_name", "测试添加的昵称");
        document.addField("picture_group_name", "测试添加的group");
        document.addField("picture_remote_name", "测试添加的remote");
        document.addField("hometown", "测试添加的住址");
        document.addField("job", "测试添加的职业");
        document.addField("gender", "测试添加的性别");
        document.addField("user_describe", "测试添加的描述");
        UpdateResponse updateResponse = server.add(document);
        System.out.println(updateResponse);
        server.commit();
    }

    @Test
    public void testDelete() throws IOException, SolrServerException {
        UpdateResponse updateResponse = server.deleteById("101010");
        System.out.println(updateResponse);
        server.commit();
    }

    @Test
    public void testSimpleQuery() throws SolrServerException {

        SolrQuery query = new SolrQuery("d");
        query.set("df", "item_keywords");

        QueryResponse queryResponse = server.query(query);
        SolrDocumentList results = queryResponse.getResults();
        long numFound = results.getNumFound();
        System.out.println("查询到" + numFound);
        for (SolrDocument document : results) {
            System.out.println(document);
        }
    }

    @Test
    public void testComplicatedQuery() throws SolrServerException {

        SolrQuery query = new SolrQuery("深圳");
        query.set("df", "item_keywords");

        query.setStart(0);
        query.setRows(2);

        query.setHighlight(true);
        query.addHighlightField("nick_name");
        query.addHighlightField("hometown");
        query.addHighlightField("job");
        query.addHighlightField("gender");
        query.addHighlightField("user_describe");

        query.setHighlightSimplePre("<label style='color:red;'>");
        query.setHighlightSimplePost("</label>");

        QueryResponse queryResponse = server.query(query);
        SolrDocumentList results = queryResponse.getResults();
        System.out.println(results.getNumFound());
        //遍历所有高亮字段
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        Set<Map.Entry<String, Map<String, List<String>>>> entries = highlighting.entrySet();
        for (Map.Entry<String, Map<String, List<String>>> entry : entries) {
            String key = entry.getKey();
            System.out.println("outerKey==>" + key);
            Map<String, List<String>> value = entry.getValue();

            Set<Map.Entry<String, List<String>>> innerSet = value.entrySet();
            for (Map.Entry<String, List<String>> innerEntry : innerSet) {
                String innerKey = innerEntry.getKey();
                System.out.println("innerKey==>" + innerKey);
                List<String> innerValue = innerEntry.getValue();
                System.out.println("innerValue==>" + innerValue + innerValue.get(0));
                System.out.println("===================");
            }
            System.out.println("------------------");
        }

        List<Map<String, Object>> finalList = new ArrayList<>();
        //整合无高亮和有高亮的所有字段
        for (SolrDocument document : results) {
            Map<String, Object> finalMap = new HashMap<>();
            System.out.println(document);
            Object id = document.getFieldValue("id");
            Collection<String> fieldNames = document.getFieldNames();
            for (String fieldName : fieldNames) {
                Object fieldValue = document.getFieldValue(fieldName);
                Map<String, List<String>> stringListMap = highlighting.get(id);
                List<String> strings = stringListMap.get(fieldName);
                if (strings != null) {
                    fieldValue = strings.get(0);
                }
                finalMap.put(fieldName, fieldValue);
            }
            finalList.add(finalMap);
        }

        for (Map<String, Object> map : finalList) {
            System.out.println(map);
        }

    }

    @Test
    public void testAddAll() throws IOException, SolrServerException {

        List<TUser> tUsers = tUserService.listAllUsers();
        for (TUser user : tUsers) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", user.getUserId());
            document.addField("nick_name", user.getNickName());
            document.addField("picture_group_name", user.getPictureGroupName());
            document.addField("picture_remote_name", user.getPictureRemoteName());
            document.addField("hometown", user.getHometown());
            document.addField("job", user.getJob());
            document.addField("gender", user.getGender());
            document.addField("user_describe", user.getUserDescribe());
            server.add(document);
        }
        server.commit();
    }

}
