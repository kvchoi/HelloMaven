package com.mvel;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

import ch.maxant.rules.Engine;
import ch.maxant.rules.IAction;
import ch.maxant.rules.Rule;
import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlReader;
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

    public static List<Rule> loadRules() throws Exception {
        URL url = ClassLoader.getSystemResource("rules.yml");
        YamlConfig config = new YamlConfig();
        config.readConfig.setIgnoreUnknownProperties(true);
        YamlReader reader = new YamlReader(new FileReader(new File(url.toURI())), config);
        List<Rule> rules = Lists.newArrayList();
        while (true) {
            RuleDef ruleDef = reader.read(RuleDef.class);
            if (ruleDef == null) { break; }
            //TODO 检查规则配置是否正确
            rules.add(ruleDef.toRule());
            System.out.println(ruleDef.toString());
        }
        return rules;
    }

    public static void testRules() throws Exception {
        // 加载规则配置
        List<Rule> rules = loadRules();

        // 初始化并用MVEL解析器编译规则
        Engine engine = new Engine(rules, "$", true);

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
