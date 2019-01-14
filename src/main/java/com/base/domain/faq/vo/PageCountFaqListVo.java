package com.base.domain.faq.vo;

import java.util.List;

/**
 * @author fanl
 * @date 2018/9/20 10:38
 */
public class PageCountFaqListVo {

    private Integer allCount;

    private List<PageFaqListVo> faqList;

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public List<PageFaqListVo> getFaqList() {
        return faqList;
    }

    public void setFaqList(List<PageFaqListVo> faqList) {
        this.faqList = faqList;
    }
}
