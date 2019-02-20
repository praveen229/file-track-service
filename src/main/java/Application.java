

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.qcom.search")
@EnableMongoRepositories({ "com.qcom.search.repository" })

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}