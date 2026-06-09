package com.example.web.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.TimeZone;

@Configuration
public class JacksonConfig {

    private static final String SERIALIZE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter SERIALIZE_FORMATTER = DateTimeFormatter.ofPattern(SERIALIZE_DATE_TIME_FORMAT);

    // 标准格式：2026-03-04 12:00:00
    private static final DateTimeFormatter STANDARD_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // ISO格式：2026-03-04T12:00:00
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(SERIALIZE_FORMATTER));
            builder.deserializerByType(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                @Override
                public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                    String raw = p.getValueAsString();
                    if (raw == null || raw.trim().isEmpty()) {
                        return null;
                    }

                    String text = raw.trim();

                    // 兼容异常格式：yyyy-MM-dd HH:mm:ss HH:mm:ss
                    // 例如：2026-03-04 12:00:00 00:00:00 -> 截取前19位作为真实时间
                    if (text.length() > 19
                            && text.charAt(4) == '-'
                            && text.charAt(7) == '-'
                            && (text.charAt(10) == ' ' || text.charAt(10) == 'T')) {
                        text = text.substring(0, 19);
                    }

                    try {
                        if (text.charAt(10) == 'T') {
                            return LocalDateTime.parse(text, ISO_FORMATTER);
                        }
                        return LocalDateTime.parse(text, STANDARD_FORMATTER);
                    } catch (DateTimeParseException ex) {
                        throw ctxt.weirdStringException(
                                raw,
                                LocalDateTime.class,
                                "日期格式不正确，支持 yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd'T'HH:mm:ss"
                        );
                    }
                }
            });
            builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        };
    }
}
