package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}

@RestController
@AllArgsConstructor
class RestFooService {

    private final FooService fooService;

    @GetMapping("/foo/{id}")
    public Foo getFoo(@PathVariable String id) {
        return fooService.getFoo(id);
    }

    @GetMapping("/fooWithBaz/{id}")
    public Foo getFooWithBaz(@PathVariable String id) {
        return fooService.getFoo(new Baz("a"+id, "b"+id, "c"+id));
    }
}

@Slf4j
@Service
@CacheConfig(cacheNames = "foos")
class FooService {

    @Cacheable
    public Foo getFoo(String id) {
        log.info("getting foo with id '{}'", id);
        return new Foo(id, "foo");
    }

    @Cacheable
    public Foo getFoo(Baz baz) {
        log.info("getting foo with baz '{}', baz");
        return new Foo(baz.toString(), baz.toString());
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Foo {
    private String id;
    private String name;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Baz {
    private String b;
    private String a;
    private String z;
}