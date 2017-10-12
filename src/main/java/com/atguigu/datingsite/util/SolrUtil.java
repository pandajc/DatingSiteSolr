package com.atguigu.datingsite.util;

import com.atguigu.datingsite.bean.TUser;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.*;

/**
 * 搜索功能
 */
public class SolrUtil {

    private static SolrServer server = new HttpSolrServer("http://192.168.160.210:8080/solr/datingsite_core");

    public static void addUserDocument(TUser user){
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", user.getUserId());
        document.addField("nick_name", user.getNickName());
        document.addField("picture_group_name", user.getPictureGroupName());
        document.addField("picture_remote_name", user.getPictureRemoteName());
        document.addField("hometown", user.getHometown());
        document.addField("job", user.getJob());
        document.addField("gender", user.getGender());
        document.addField("user_describe", user.getUserDescribe());
        try {
            server.add(document);
            server.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUserDocumentById(String id){
        try {
            server.deleteById(id);
            server.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateUserDocument(TUser user){
        deleteUserDocumentById(user.getUserId() + "");
        addUserDocument(user);
    }

    public static List<Object> queryUserDocumentPage(String keyword, Integer pageNum, Integer pageSize) {
        SolrQuery query = new SolrQuery(keyword);
        query.set("df", "item_keywords");
        //分页
        query.setStart((pageNum - 1) * pageSize);
        query.setRows(pageSize);
        //开启高亮显示
        query.setHighlight(true);
        //高亮显示的字段
        query.addHighlightField("nick_name");
        query.addHighlightField("hometown");
        query.addHighlightField("job");
        query.addHighlightField("gender");
        query.addHighlightField("user_describe");
        //高亮显示的标签
        query.setHighlightSimplePre("<label style='color:red;background-color: yellow'>");
        query.setHighlightSimplePost("</label>");
        //执行查询
        QueryResponse queryResponse = null;
        try {
            queryResponse = SolrUtil.server.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        //返回查询结果，文档对象的List
        SolrDocumentList results = queryResponse.getResults();
        //查询结果的记录数
        long numFound = results.getNumFound();
        System.out.println(numFound);
        if (numFound == 0){
            return null;
        }
        //遍历所有高亮字段，打印
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        Set<Map.Entry<String, Map<String, List<String>>>> entries = highlighting.entrySet();
        for (Map.Entry<String, Map<String, List<String>>> entry : entries) {
            String key = entry.getKey();
            System.out.println("outerKey==>" + key);
            //key高亮字段=value包含高亮标签的属性值
            Map<String, List<String>> value = entry.getValue();
            //遍历高亮部分
            Set<Map.Entry<String, List<String>>> innerSet = value.entrySet();
            //纯粹为了打印
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
        //遍历文档对象集合
        for (SolrDocument document : results) {
            Map<String, Object> finalMap = new HashMap<>();
            System.out.println(document);
            //获取文档对象的id
            Object id = document.getFieldValue("id");
            //获取所有属性/字段名
            Collection<String> fieldNames = document.getFieldNames();
            //遍历字段名
            for (String fieldName : fieldNames) {
                //根据字段名获取值
                Object fieldValue = document.getFieldValue(fieldName);
                //根据id获取高亮字段的map
                Map<String, List<String>> stringListMap = highlighting.get(id);
                //根据字段名获取加了高亮的字段/属性值
                List<String> strings = stringListMap.get(fieldName);
                //如果高亮属性值不为空，把原来不含高亮标签的属性值替换成含有标签的
                if (strings != null) {
                    fieldValue = strings.get(0);
                }
                //高亮和非高亮整合后放到map中
                finalMap.put(fieldName, fieldValue);
            }
            //整合后的放到List
            finalList.add(finalMap);
        }

        List<Object> list  = new ArrayList<>();
        //把查询结果数和整合后的list放到list
        list.add(numFound);
        list.add(finalList);
        return list;
    }

}
