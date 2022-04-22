package com.easyrule;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

/**
 * 购物车总价优惠规则
 */
@Rule(name = "cart total rule", description = "Give 10% off when shopping cart is greater than $200" )
public class CartTotalRule {

    @Condition
    public boolean cartTotal(@Fact("cart") Cart cart) {
        System.out.println("Condition: Cart is greater than $200");
        return cart.getAmount() > 200;
    }


    @Action
    public void giveDiscount(@Fact("cart") Cart cart) {
        System.out.println("Action: Give 10% off");
       cart.setTotalDiscount(200);
    }
}