package com.example.tasklistnotificationservice.config;

import com.jcabi.xml.XML;

public final class TextXPath {

    private final XML xml;

    private final String node;


    public TextXPath(XML xml, String node) {
        this.xml = xml;
        this.node = node;
    }

    @Override
    public String toString() {
        return this.xml.nodes(this.node)
                .get(0)
                .xpath("text()")
                .get(0);
    }
}
