package com.hbhs.common.ratelimiter.rule;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter@Setter
public class LimitRuleNode {
    private String name;
    private boolean limitNode = Boolean.FALSE;
    private String urlPattern;
    private List<RateLimiterStrategy> strategyList;
    private Map<String, LimitRuleNode> subNodeMap = new HashMap<>();

    private LimitRuleNode(String name){
        this.name = name;
    }

    public static LimitRuleNode rootNode(){
        LimitRuleNode root = new LimitRuleNode("");
        root.setUrlPattern("/");
        return root;
    }

    public void addRateLimiterStrategy(String url, RateLimiterStrategy strategy) {
        if (url == null || "".equalsIgnoreCase(url) || strategy == null) {
            return;
        }
        if (url.startsWith("/")){url = url.substring(1);}
        String[] urlArray = url.split("/");
        addRateLimiterStrategy(this, url, urlArray, 0, strategy);
    }

    private LimitRuleNode addRateLimiterStrategy(LimitRuleNode node, String url, String[] urlArray, int startIndex, RateLimiterStrategy strategy) {
        if (startIndex == urlArray.length) {
            if (node.getStrategyList() == null) {
                node.setStrategyList(new ArrayList<>());
            }
            node.getStrategyList().add(strategy);
            node.setLimitNode(Boolean.TRUE);
            node.setUrlPattern(url);
            return node;
        }
        String name = urlArray[startIndex];
        LimitRuleNode subNode = node.getSubNodeMap().get(name);
        if (subNode == null) {
            subNode = new LimitRuleNode(name);
            node.getSubNodeMap().put(name, subNode);
        }
        return addRateLimiterStrategy(subNode, url, urlArray, startIndex + 1, strategy);
    }

    public LimitRuleNode findMatchedRateLimiterNode(String url) {
        if (url.startsWith("/")){url = url.substring(1);}
        String[] urlArray = url.split("/");
        LimitRuleNode matcheNode = this;
        int index = 0;
        while (index < urlArray.length) {
            String key = urlArray[index];
            LimitRuleNode subNode = matcheNode.getSubNodeMap().get(key);
            if (subNode==null){
                subNode = matcheNode.getSubNodeMap().get("*");
                if (subNode == null){
                    return null;
                }
            }
            matcheNode = subNode;
            index++;
        }
        return matcheNode.isLimitNode()?matcheNode:null;
    }
}
