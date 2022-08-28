package com.slvrmn.DnfAssistant.Model;

public class CheckRuleModel {
    public Rectangle rectangle;
    public String rule;

    public CheckRuleModel(Rectangle rectangle, String rule) {
        this.rectangle = rectangle;
        this.rule = rule;
    }

    public CheckRuleModel(int x1, int y1, int x2, int y2, String rule) {
        this.rectangle = new Rectangle(x1, y1, x2, y2);
        this.rule = rule;
    }
}
