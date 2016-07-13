package es.amplia.commons.mongodb;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import es.amplia.commons.mongodb.configuration.MongoConfiguration;
import es.amplia.commons.mongodb.converters.DateToLocalDateTimeConverter;
import es.amplia.commons.mongodb.converters.LocalDateTimeToDateConverter;
import es.amplia.commons.mongodb.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableMongoRepositories(basePackageClasses = {PersonRepository.class})
@EnableMongoAuditing
@PropertySource(value={"classpath:mongo.properties"})
public class TestMongoConfiguration extends MongoConfiguration {

    @Value("${databaseName}")
    String databaseName;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public MongoClient mongo() {
        return new Fongo("InMemoryMongo").getMongo();
    }

    @Bean
    @Override
    public CustomConversions customConversions() {
        return new CustomConversions(Arrays.asList(new LocalDateTimeToDateConverter(), new DateToLocalDateTimeConverter()));
    }
}
