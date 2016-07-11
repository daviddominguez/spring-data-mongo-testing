package es.amplia.commons.mongodb.configuration;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "es.amplia.commons.mongodb.repositories")
@EnableMongoAuditing
@PropertySource(value={"classpath:mongo.properties"})
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Value("${databaseName}")
    String databaseName;

    @Value("${host}")
    String host;

    @Value("${port:27017}")
    int port;

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(mongo(), getDatabaseName());
    }


    @Override
    protected String getMappingBasePackage() {
        return "es.amplia.commons.mongodb.model";
    }

    protected String getDatabaseName() {
        return databaseName;
    }

    public MongoClient mongo() throws Exception {
        return new MongoClient(host, port);
        // return new MongoClient(new ServerAddress(host, ServerAddress.defaultPort()), MongoClientOptions.builder().build());
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
