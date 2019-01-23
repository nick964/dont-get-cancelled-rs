package com.nick.cancan.entity;

import javax.persistence.*;

@Entity
@Table(name = "canceled_words")
public class BadWord {

    @Id
    @Column(name="id")
    @GeneratedValue
    private Integer id;

    @Column(name="text")
    private String text;

    @Column(name = "severity")
    private Integer severity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public BadWord(String text, Integer sev) {
        this.text = text;
        this.severity = sev;
    }

    public BadWord() {
    }


}
