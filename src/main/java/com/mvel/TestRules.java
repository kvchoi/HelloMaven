package com.mvel;

import java.util.Arrays;
import java.util.List;

import ch.maxant.rules.Engine;
import ch.maxant.rules.IAction;
import ch.maxant.rules.Rule;
import com.google.common.collect.Lists;
import com.mvel.TestRules.TarifRequest.Account;
import com.mvel.TestRules.TarifRequest.Person;

/**
 * @author by cairongfu.crf
 * @since on 2018/4/28 11:51.
 */
public class TestRules {

    public static void main(String[] args) throws Exception {
        testRules();
    }

    public static void testRules() throws Exception {
        Rule r0 = new Rule("zero", "input.person == null", "ZT2011", 4, "rule_group_1", null);
        Rule r1 = new Rule("one", "input.person != null && input.person.age < 26", "YT2011", 3, "rule_group_1", null);
        Rule r2 = new Rule("two", "input.person.age > 59", "ST2011", 3, "rule_group_1", null);
        Rule r3 = new Rule("three", "!#one && !#two", "DT2011", 3, "rule_group_1", null);
        Rule r4 = new Rule("four", "#three && input.account.ageInMonths > 24", "LT2011", 4, "rule_group_1", null);
        // 默认规则
        Rule defaultRule = new Rule("five", "true", "default", -1, "rule_group_1", null);
        List<Rule> rules = Arrays.asList(r0, r1, r2, r3, r4, defaultRule);

        // 初始化并用MVEL解析器编译规则
        Engine engine = new Engine(rules, "input", true);

        TarifRequest request = new TarifRequest();
        request.setPerson(new Person());
        request.setAccount(new Account());
        request.setDefaultRuleFlag(true);

        request.getPerson().setAge(30);
        request.getAccount().setAgeInMonths(5);

        // 获取优先级最高的匹配规则
        String tarif = engine.getBestOutcome(request);
        System.out.println("getBestOutcome:" + tarif);

        // 获取所有匹配的规则
        List<Rule> matchingRules = engine.getMatchingRules(request);
        System.out.println("matchingRules:" + matchingRules);

        List<IAction<TarifRequest, String>> c = Lists.newArrayList();
        c.add(new ActionAdapter("ZT2011"));
        c.add(new ActionAdapter("YT2011"));
        c.add(new ActionAdapter("ST2011"));
        c.add(new ActionAdapter("DT2011"));
        c.add(new ActionAdapter("LT2011"));
        c.add(new ActionAdapter("default"));

        // 执行最优匹配的规则动作
        String result = engine.executeBestAction(request, c);
        System.out.println("executeBestAction:" + result);

        // 执行所有匹配的规则动作
        engine.executeAllActions(request, c);
    }

    public static class ActionAdapter implements IAction<TarifRequest, String> {
        private String name;

        public ActionAdapter(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String execute(TarifRequest tarifRequest) {
            System.out.println(tarifRequest);
            return this.name;
        }
    }

    public static class TarifRequest {
        private Person person;
        private Account account;
        private boolean defaultRuleFlag;

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }

        public boolean isDefaultRuleFlag() {
            return defaultRuleFlag;
        }

        public void setDefaultRuleFlag(boolean defaultRuleFlag) {
            this.defaultRuleFlag = defaultRuleFlag;
        }

        public static class Person {
            private int age;

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }
        }

        public static class Account {
            public int getAgeInMonths() {
                return ageInMonths;
            }

            public void setAgeInMonths(int ageInMonths) {
                this.ageInMonths = ageInMonths;
            }

            private int ageInMonths;
        }
    }
}
