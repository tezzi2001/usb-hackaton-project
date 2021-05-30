package com.heroku.labshare.config;

import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.txt.TXTParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TikaConfig {

    @Bean
    public TXTParser getTXTParser() {
        return new TXTParser();
    }

    @Bean
    public PDFParser getPDFParser() {
        return new PDFParser();
    }

    @Bean
    public OfficeParser getOfficeParser() {
        return new OfficeParser();
    }
}
