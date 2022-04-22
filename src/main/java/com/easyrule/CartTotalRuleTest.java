package com.easyrule;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

/**
 * 购物车总价优惠规则测试
 */
public class CartTotalRuleTest {

    public static void main(String[] args) {

        // get customer cart
        Cart customerCart = new Cart();
        customerCart.setAmount(200);

        // define facts
        Facts facts = new Facts();
        facts.put("cart", customerCart);

        // define rules
        Rules rules = new Rules();
        rules.register(new CartTotalRule());

        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }
}