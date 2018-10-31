package com.sentinel;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

/**
 * @author by cairongfu.crf
 * @since on 2018/10/31 14:06.
 */
public class SentinelTest {

    public static void main(String[] args) {
        initFlowRules();
        while (true) {
            Entry entry = null;
            try {
                entry = SphU.entry("HelloWorld");
                System.out.println("hello world");
            } catch (BlockException e1) {
                System.out.println("block!");
            } finally {
                if (entry != null) { entry.exit(); }
            }
        }
    }

    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(20);//Set limit QPS to 20.
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
