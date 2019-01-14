package com.base.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.base.domain.faq.dto.AddQuestionDto;
import com.base.domain.faq.dto.FaqPageDto;
import com.base.domain.faq.dto.PageDto;
import com.base.domain.faq.dto.UpdateQuestionDto;
import com.base.domain.faq.entity.ErrorHandling;
import com.base.domain.faq.vo.ErrorCodeFindVo;
import com.base.domain.faq.vo.PageCountFaqListVo;
import com.base.domain.faq.vo.PageFaqListVo;
import com.base.domain.faq.vo.QuestionDetailsVo;
import com.base.http.ErrType;
import com.base.http.ResultMap;
import com.base.mapper.FaqMapper;
import com.base.service.FaqService;
import com.base.util.BaseUtils;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FaqServiceImpl implements FaqService {

    private static final Logger logger = LoggerFactory.getLogger(FaqServiceImpl.class);

    @Autowired
    private FaqMapper errorHandlingMapper;

    @Value("${server.domainName}")
    private String domainName;

    @Override
    @Transactional
    public ResultMap addQuestion(AddQuestionDto addQuestionDto) {
        logger.info(JSON.toJSONString(addQuestionDto));

        // 重写isEmpty
        if (BaseUtils.TrimIsEmpty(addQuestionDto.getQuestion()) || BaseUtils.TrimIsEmpty(addQuestionDto.getErrorCode())){
            logger.info("参数不能为空");
            return new ResultMap(ErrType.PRO_NULL);
        }

        //获取当前时间，并转换为String
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //首先设定日期时间格式,HH指使用24小时制,hh是使用12小时制
        Date date = new Date();
        String dateStr = dateFormat.format(date);

        // 通用mapper errorCode查重
        Example example = new Example(ErrorHandling.class);
        example.createCriteria()
                .andEqualTo("errorCode",BaseUtils.OverTrim(addQuestionDto.getErrorCode()));
        List<ErrorHandling> list = errorHandlingMapper.selectByExample(example);
        if (list.size() > 0){
            logger.info("errCode已存在");
            return new ResultMap(ErrType.ERRORCODE_REPEAT);
        }

//        if (addQuestionDto.getQuestion().isEmpty() || addQuestionDto.getErrorCode().isEmpty()){
//            logger.info("参数不能为空");
//            return new ResultMap(ErrType.PRO_NULL);
//        }
        // ErrorHandling信息入库
        ErrorHandling errorHandling = new ErrorHandling();
        errorHandling.setErrorCode(BaseUtils.OverTrim(addQuestionDto.getErrorCode()));
        errorHandling.setQuestion(BaseUtils.OverTrim(addQuestionDto.getQuestion()));
        errorHandling.setAnswer(JSON.toJSONString(addQuestionDto.getAnswer()));
        errorHandling.setCdate(dateStr);
        errorHandling.setRowguid(UUID.randomUUID().toString());
        errorHandlingMapper.insert(errorHandling);
        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    public ResultMap pageFaqList(FaqPageDto pageDto) throws ParseException, IOException {
        // 防止出现空指针现象
        try{
            pageDto.getStart();
            pageDto.getSize();
        } catch (Exception e){
            e.printStackTrace();
            return new ResultMap(ErrType.PAGE_NULL);
        }
        int allCount = 0;
        if (BaseUtils.TrimIsEmpty(pageDto.getSearch())){
            // 满足要求的数据数量
            allCount = errorHandlingMapper.selectAll().size();

            // 分页插件
            PageHelper.startPage(pageDto.getStart(), pageDto.getSize());
            // 通用mapper 按cdate降序
            Example example = new Example(ErrorHandling.class);
            example.setOrderByClause("cdate DESC");
            List<ErrorHandling> list = errorHandlingMapper.selectByExample(example);
            List<PageFaqListVo> pageFaqListVos = new ArrayList<>();
            PageCountFaqListVo pageCountFaqListVo = new PageCountFaqListVo();
            // 没有FAQ
            if (list.size() == 0){
                logger.info("没有数据可查询");
                List<PageFaqListVo> pageFaqListVos1 = new ArrayList<>();
                pageCountFaqListVo.setAllCount(0);
                pageCountFaqListVo.setFaqList(pageFaqListVos1);
                return new ResultMap(ErrType.SUCCESS,pageCountFaqListVo);
            }
            // 有FAQ
            for (int i = 0; i < list.size(); i++){
                PageFaqListVo pageFaqListVo = new PageFaqListVo();
                BeanUtils.copyProperties(list.get(i),pageFaqListVo);
                pageFaqListVos.add(pageFaqListVo);
            }
            pageCountFaqListVo.setAllCount(allCount);
            pageCountFaqListVo.setFaqList(pageFaqListVos);
            return new ResultMap(ErrType.SUCCESS,pageCountFaqListVo);
        } else {
            // 创建QueryParser对象，并指定要查询的索引域及分词对象
            QueryParser queryParser = new QueryParser("answer", new StandardAnalyzer());
            // 创建Query对象，指定查询条件
            Query query = queryParser.parse(pageDto.getSearch());
            // 指定要查询的lucene索引目录,必须与存储的索引目录一致
            Directory directory = FSDirectory.open(new File("./index"));
            // 通过指定的查询目录文件地址创建IndexReader流对象
            IndexReader reader = DirectoryReader.open(directory);
            // 通过IndexReader流对象创建IndexSearcher
            IndexSearcher searcher = new IndexSearcher(reader);
            // 执行查询语句，获取返回的TopDocs对象（参数中的100为取出的数据条数）
            TopDocs search = searcher.search(query, 100);
            // 获取查询关键字获得的数据总数
            allCount = search.totalHits;
            // 符合条件的id集合
            List<String> list = new ArrayList<>();
            System.out.println();
            // 获取查询到的数据
            ScoreDoc[] scoreDocs = search.scoreDocs;
            for (ScoreDoc doc : scoreDocs) {
                // 获取查询到的索引ID
                int docId = doc.doc;
                // 根据索引查询数据
                Document document = searcher.doc(docId);
                // 输出查询到的数据
                list.add(document.get("id"));
            }
            PageCountFaqListVo pageCountFaqListVo = new PageCountFaqListVo();
            List<PageFaqListVo> listVos = new ArrayList<>();
            if (list.size() > 0){
                // 分页插件
                PageHelper.startPage(pageDto.getStart(), pageDto.getSize());
                listVos = errorHandlingMapper.selectFaqByIds(list);
                pageCountFaqListVo.setAllCount(allCount);
                pageCountFaqListVo.setFaqList(listVos);
            } else {
                pageCountFaqListVo.setAllCount(0);
                pageCountFaqListVo.setFaqList(listVos);
            }
            // 关闭流
            reader.close();

            return new ResultMap(ErrType.SUCCESS,pageCountFaqListVo);
        }

    }

    @Override
    @Transactional
    public ResultMap deleteQuestion(List<String> list) {
        // 遍历删除FAQ
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++){
                errorHandlingMapper.deleteByPrimaryKey(list.get(i));
            }
        }
        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    public ResultMap findQuestionById(String id) {
        // 根据主键查询FAQ详情
        ErrorHandling errorHandling = errorHandlingMapper.selectByPrimaryKey(id);
        if (errorHandling == null){
            logger.info("该问题已不存在，请刷新");
            return new ResultMap(ErrType.QUESTION_NOTEXIST);
        }

        // 数据库中将JSON转换为String存入，返回给前端时需要将String转换为JSON
        QuestionDetailsVo questionDetailsVo = new QuestionDetailsVo();
        questionDetailsVo.setRowguid(errorHandling.getRowguid());
        questionDetailsVo.setErrorCode(errorHandling.getErrorCode());
        questionDetailsVo.setQuestion(errorHandling.getQuestion());
        JSONArray jsonArray = JSON.parseArray(errorHandling.getAnswer());
//        if (jsonArray.size() > 0) {
//            for (int i = 0; i < jsonArray.size(); i++) {
//                if (jsonArray.getJSONObject(i).get("pictureUrl").toString().isEmpty() || jsonArray.getJSONObject(i).get("pictureUrl").toString().equals("")) {
//                    // 图片地址为空  不需要进行拼接，直接返回空
//                } else {
//                    // 有图片地址，需要拼接domain返回给前端
//                    jsonArray.getJSONObject(i).put("pictureUrl", domainName + jsonArray.getJSONObject(i).get("pictureUrl").toString());
//                }
//            }
//        }
        // set处理好的json
        questionDetailsVo.setAnswer(jsonArray);
        return new ResultMap(ErrType.SUCCESS,questionDetailsVo);
    }

    @Override
    @Transactional
    public ResultMap updateQuestion(UpdateQuestionDto updateQuestionDto) {
        //获取当前时间，并转换为String
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //首先设定日期时间格式,HH指使用24小时制,hh是使用12小时制
        Date date = new Date();
        String dateStr = dateFormat.format(date);

        // 重写trim，防止出现空指针现象， ErrorCode和问题名称 不允许为空
        if (BaseUtils.TrimIsEmpty(updateQuestionDto.getQuestion()) || BaseUtils.TrimIsEmpty(updateQuestionDto.getErrorCode())){
            logger.info("参数不能为空");
            return new ResultMap(ErrType.PRO_NULL);
        }
        // 重写trim 去掉前后空格
        updateQuestionDto.setErrorCode(BaseUtils.OverTrim(updateQuestionDto.getErrorCode()));
        updateQuestionDto.setQuestion(BaseUtils.OverTrim(updateQuestionDto.getQuestion()));
        // 更新问题内容
        ErrorHandling errorHandling = new ErrorHandling();
        BeanUtils.copyProperties(updateQuestionDto,errorHandling);
        errorHandling.setAnswer(JSON.toJSONString(updateQuestionDto.getAnswer()));
        errorHandling.setUdate(dateStr);
        errorHandlingMapper.updateByPrimaryKeySelective(errorHandling);
        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    public ResultMap findErrorCode() {
        // 只返回需要的字段，并将id返回为 errorId
        List<ErrorHandling> list = errorHandlingMapper.selectAll();
        List<ErrorCodeFindVo> listVos = new ArrayList<>();
        if (list.size() == 0){
            logger.info("没有数据可查询");
        }
        for (int i = 0; i < list.size(); i++){
            ErrorCodeFindVo errorCodeFindVo = new ErrorCodeFindVo();
            BeanUtils.copyProperties(list.get(i),errorCodeFindVo);
            listVos.add(errorCodeFindVo);
        }
//        BeanUtils.copyProperties(list,listVos);
        return new ResultMap(ErrType.SUCCESS,listVos);
    }
}
