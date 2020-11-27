package com.itheima.springbootsecurity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootSecurityApplicationTests {
     @Value("#{admin}")
	private Object admin;
	@Test
	void contextLoads() {
		System.out.println(("admin".equals(admin)));
	}

}
