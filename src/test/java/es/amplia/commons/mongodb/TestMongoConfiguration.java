package es.amplia.commons.mongodb;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import es.amplia.commons.mongodb.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = {PersonRepository.class})
@EnableMongoAuditing
@PropertySource(value={"classpath:mongo.properties"})
public class TestMongoConfiguration extends AbstractMongoConfiguration {

    @Value("${databaseName}")
    String databaseName;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongo(), getDatabaseName());
    }

    @Override
    public MongoClient mongo() {
        return new Fongo("InMemoryMongo").getMongo();
    }
}
