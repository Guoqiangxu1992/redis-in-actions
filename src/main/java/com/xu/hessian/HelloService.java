package com.xu.hessian;

import org.springframework.stereotype.Service;

@Service("helloService")
public interface HelloService {
	public void sayHello(String name);
	public Object set(String key,String value);
}
