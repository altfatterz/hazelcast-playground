package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final BarService barService;

    @GetMapping("/foo/{id}")
    public Foo getFoo(@PathVariable String id) {
        return fooService.getFoo(id);
    }

    @GetMapping("/fooWithParam")
    public Foo getFooWithBaz(@RequestParam(required = false) String a,
                             @RequestParam(required = false) String b) {
        return fooService.getFoo(new Baz(a, b));
    }

    @GetMapping("/fooWithParam2")
    public Foo getFooWithBaz2(@RequestParam(required = false) String a,
                              @RequestParam(required = false) String b) {
        return fooService.getFoo2(new Baz(a, b));
    }


    @GetMapping("/bar/{id}")
    public Bar getBar(@PathVariable String id) {
        return barService.getBar(id);
    }
}

@Service
@Slf4j
class BarService {

    @Cacheable(cacheNames = "bar")
    public Bar getBar(String id) {
        log.info("getting bar with id '{}'", id);
        return new Bar(id, "bar");
    }

}


@Slf4j
@Service
@CacheConfig(cacheNames = "foo")
class FooService {

    @Cacheable
    public Foo getFoo(String id) {
        log.info("getting foo with id '{}'", id);
        return new Foo(id, "foo");
    }

    @Cacheable
    public Foo getFoo(Baz baz) {
        log.info("getting foo with baz '{}'", baz);
        return new Foo(baz.toString(), baz.toString());
    }

    @Cacheable(cacheNames = "foo2", key = "#baz.a")
    public Foo getFoo2(Baz baz) {
        log.info("getting foo with baz '{}'", baz);
        return new Foo(baz.toString(), baz.toString());
    }


}

