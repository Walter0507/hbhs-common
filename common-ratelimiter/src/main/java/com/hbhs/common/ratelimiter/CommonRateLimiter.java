package com.hbhs.common.ratelimiter;

import com.hbhs.common.ratelimiter.data.ICalculator;
import com.hbhs.common.ratelimiter.handler.ILimiterHandler;
import com.hbhs.common.ratelimiter.rule.LimitRuleNode;
import com.hbhs.common.ratelimiter.rule.RateLimiterRule;
import com.hbhs.common.ratelimiter.rule.RateLimiterStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CommonRateLimiter implements RateLimiter {

    private LimitRuleNode root;
    @Getter@Setter
    private ICalculator calculator;
    @Getter@Setter
    private List<ILimiterHandler> limiterHandlerList;

    public CommonRateLimiter(){
        root = LimitRuleNode.rootNode();
    }

    @Override
    public void addRateLimitRule(RateLimiterRule rule){
        if (rule == null||rule.getStrategyList() == null){
            return ;
        }
        for (RateLimiterStrategy strategy : rule.getStrategyList()) {
            root.addRateLimiterStrategy(rule.getLimitUrl(), strategy);
        }
    }
    @Override
    public RateLimiterResult rateLimit(RateLimiterRequest request) {
        // 查找对应url所匹配的url
        LimitRuleNode ruleNode = root.findMatchedRateLimiterNode(request.getRequestUrl());
        if (ruleNode == null){
            return RateLimiterResult.builder()
                    .resultStatus(RateLimiterResult.ResultStatus.NO_LIMIT)
                    .build();
        }
        RateLimiterStrategy rejectStrategy = null;
        List<RateLimiterStrategy> warningStrategyList = new ArrayList<>();
        for (RateLimiterStrategy strategy : ruleNode.getStrategyList()) {
            String limitKey = generateRateLimitKey(request, strategy);
            Long count = calculator.incrementAndExpired(limitKey, 1, strategy.getTtl());
            if (count > strategy.getThreshold()){
                rejectStrategy = strategy;
                break;
            }else if (count > strategy.getLimitCount()){
                warningStrategyList.add(strategy);
            }
        }

        processHandlerForWarnAndRejectStrategy(request, rejectStrategy, warningStrategyList);

        return RateLimiterResult.builder()
                .resultStatus(buildResultStatus(rejectStrategy, warningStrategyList))
                .rejectStrategy(rejectStrategy)
                .warnStrategyList(warningStrategyList)
                .build();
    }

    private RateLimiterResult.ResultStatus buildResultStatus(RateLimiterStrategy rejectStrategy,
                                                             List<RateLimiterStrategy> warningStrategyList){
        if (rejectStrategy !=null){
            return RateLimiterResult.ResultStatus.REJECT;
        }
        if (warningStrategyList!=null&&warningStrategyList.size()>0){
            return RateLimiterResult.ResultStatus.WARN;
        }
        return RateLimiterResult.ResultStatus.PASS;
    }

    private void processHandlerForWarnAndRejectStrategy(RateLimiterRequest request,
                                                        RateLimiterStrategy rejectStrategy,
                                                        List<RateLimiterStrategy> warnStrategyList){
        if (limiterHandlerList==null||limiterHandlerList.size()==0){
            return ;
        }
        for (ILimiterHandler handler : limiterHandlerList) {
            handler.handlerLimitRequest(request,rejectStrategy,warnStrategyList);
        }
    }

    /**
     * <br>RATE_NORMAL:/public/v1/order/detail
     * <br>RATE_NORMAL:/public/v1/order/detail?showId=xxxxxxxx&userId=xxxxx
     */
    private String generateRateLimitKey(RateLimiterRequest request, RateLimiterStrategy strategy){
        StringBuilder str = new StringBuilder();
        str.append(request.getRequestUrl()).append(":")
                .append(strategy.getLimiterType().name()).append(":")
                .append(request.getRequestUrl());
        if (strategy.getParamList()!=null&&strategy.getParamList().size()>0){
            str.append("?");
            for (String param : strategy.getParamList()) {
                str.append(param).append("=").append(request.getRequestParamMap().get(param)).append("&");
            }
        }
        return str.toString();
    }

    @Override
    public void shutdown(){
        calculator.shutdown();
    }
}
