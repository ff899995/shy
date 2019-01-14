package com.base.controller;

import com.base.domain.faq.entity.ErrorHandling;
import com.base.http.ResultMap;
import com.base.mapper.FaqMapper;
import com.base.util.Pinyin4jUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/lucene")
public class LuceneController {

    @Autowired
    private FaqMapper faqMapper;

    @GetMapping("/create")
    public ResultMap createIndex() throws Exception{
        // 获取FAQ数据
        List<ErrorHandling> list = new ArrayList<>();
        list = faqMapper.selectAll();
        if (list.size() == 0){
            return new ResultMap("0","没有Faq数据");
        }
        // 定义一个Document集合
        List<Document> documents = new ArrayList<>();
        // 定义document对象
        Document document;
        for (ErrorHandling faq : list){
            // 实例化document对象
            document = new Document();
            // 不进行分词，只进行索引、存储
            Field id = new StringField("id", faq.getRowguid(), Field.Store.YES);
            // 不进行分词，只进行索引、存储
            Field errorcode = new StringField("errorcode", faq.getErrorCode(), Field.Store.YES);
            // 进行分词、索引、存储
            Field question = new TextField("question", faq.getQuestion(), Field.Store.YES);
            // 进行分词、索引、存储
            Field answer = new TextField("answer", faq.getAnswer() , Field.Store.YES);
            System.out.println(answer.toString());
            // 将Field放到document对象中
            document.add(id);
            document.add(errorcode);
            document.add(question);
            document.add(answer);
            // 将document放到documents集合中
            documents.add(document);
        }
        // 创建一个标准分词对象
        Analyzer analyzer = new StandardAnalyzer();
        // 索引库的位置
        Directory directory = FSDirectory.open(new File("./index"));
        // 索引序列化的配置
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
        // 创建索引序列化（写入流）对象
        IndexWriter writer = new IndexWriter(directory, config);
        // 删除旧的索引目录
        writer.deleteAll();
        // 将documents对象循环写入到指定的索引目录 中
        for (Document doc : documents) {
            writer.addDocument(doc);
        }
        // 关闭流对象
        writer.close();
        return new ResultMap("200","索引目录创建完成");
    }


}
