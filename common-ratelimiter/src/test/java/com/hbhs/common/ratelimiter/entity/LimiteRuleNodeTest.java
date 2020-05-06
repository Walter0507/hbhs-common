package com.hbhs.common.ratelimiter.entity;


import com.hbhs.common.ratelimiter.rule.LimitRuleNode;
import com.hbhs.common.ratelimiter.rule.RateLimiterStrategy;

public class LimiteRuleNodeTest {

    public static void main(String[] args) {
        RateLimiterStrategy strategy = new RateLimiterStrategy();
        LimitRuleNode node = LimitRuleNode.rootNode();
        node.addRateLimiterStrategy("/endpoint/v1/user/*/take",strategy);
        node.addRateLimiterStrategy("/endpoint/v1/user/my/take",strategy);
        node.addRateLimiterStrategy("/endpoint/v1/user/*/holy",strategy);
        node.addRateLimiterStrategy("/endpoint/v1/user/my/holy",strategy);
        System.out.println(node);

        LimitRuleNode subNode = node.findMatchedRateLimiterNode("/endpoint/v1/user/may/holy");
        System.out.println(subNode);
    }
}