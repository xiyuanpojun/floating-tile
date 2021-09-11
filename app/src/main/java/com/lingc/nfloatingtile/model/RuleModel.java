package com.lingc.nfloatingtile.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * [
 * {"keyword": ["关键词一","关键词二"],"price":null},
 * {"keyword": ["关键词一","关键词二"],"price":null},
 * {"keyword": ["关键词一","关键词二"],"price":null},
 * {"keyword": ["关键词三","关键词四"],"price":{"low":0,"high":100}}
 * ]
 */
public class RuleModel implements Serializable {
    public List<String> keyword = new ArrayList<>();
    public Price price = null;

    public List<String> getKeyword() {
        return keyword;
    }

    public void setKeyword(List<String> keyword) {
        this.keyword = keyword;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public static class Price {
        private Long low;
        private Long high;

        public Long getLow() {
            return low;
        }

        public void setLow(Long low) {
            this.low = low;
        }

        public Long getHigh() {
            return high;
        }

        public void setHigh(Long high) {
            this.high = high;
        }
    }
}
