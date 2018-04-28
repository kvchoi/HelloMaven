package com.mvel;

import ch.maxant.rules.Rule;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 规则定义bean
 *
 * @author by cairongfu.crf
 * @since on 2018/4/28 16:31.
 */
public class RuleDef {
    private String name;
    private String expression;
    private String outcome;
    private Integer priority;
    private String namespace = "default_group";
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Rule toRule() {
        return new Rule(this.getName(), this.getExpression(), this.getOutcome(),
            this.getPriority() != null ? this.getPriority() : 0,
            this.getNamespace(), this.getDescription());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
