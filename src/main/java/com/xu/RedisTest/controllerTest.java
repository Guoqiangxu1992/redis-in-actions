package com.xu.RedisTest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class controllerTest {
	
	@RequestMapping("/get.do")
	public void get(){
		System.out.println(1111111111);
	}

}
