package esprit.tn.coursesspace;

import org.springframework.aop.TargetSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "esprit.tn.coursesspace.Entity")
@EnableJpaRepositories(basePackages = "esprit.tn.coursesspace.Repository")
public class CoursesSpaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursesSpaceApplication.class, args);
    }

}
