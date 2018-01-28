package aibili.ronaldo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by rtf on  2018/1/24.
 */

@SpringBootApplication
@ComponentScan(basePackages = "aibili.ronaldo")
@MapperScan("aibili.ronaldo.dao")
public class Application {

	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}
}
