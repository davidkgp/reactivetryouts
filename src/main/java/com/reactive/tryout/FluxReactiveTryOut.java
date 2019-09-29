package com.reactive.tryout;

import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public class FluxReactiveTryOut {

    public static void main(String[] args) {
        //create flux

        Flux<String> fluxFromArray = Flux.fromArray(new String[]{"a", "b", "c", "d"});
        Flux<String> fluxFromJustVarags = Flux.just("a", "b", "c", "d");
        Flux<Object> emptyFlux = Flux.empty();
        Flux<String> fluxFromIterable = Flux.fromIterable(Arrays.asList("a", "b", "c", "d"));
        Flux<String> fluxFromStream = Flux.fromStream(Arrays.asList("a", "b", "c", "d").stream());
        Flux<String> errorFlux = Flux.range(3, 10).map(value -> {
            if (value % 2 == 0) return value + "";
            throw new IllegalArgumentException();
        }).onErrorMap(excep -> new MyException(excep.getMessage()));

        Flux<String> errorFluxContinue = Flux.range(3, 10).map(value -> {
            if (value % 2 == 0) return value + "";
            throw new IllegalArgumentException();
        }).onErrorContinue((excep,obj)-> System.err.println("Consumed "+excep.toString()+" "+obj.toString()));


        Flux<Integer> integerFlux = Flux.range(3, 20);


        fluxFromIterable.subscribe(System.out::println);
        System.out.println("*****************************");
        errorFlux.subscribe(System.out::println,System.err::println);
        System.out.println("*****************************");
        errorFluxContinue.subscribe(System.out::println,System.err::println);
        System.out.println("*****************************");
        errorFluxContinue.subscribe(System.out::println,System.err::println,()-> System.out.println("I think Done"));
        System.out.println("*****************************");
        integerFlux.subscribe(System.out::println
                ,System.err::println
                ,()->System.out.println("Yo Done")
                ,subscription -> subscription.request(5));
        System.out.println("*****************************");



        //create flux end
    }
}

class MyException extends RuntimeException {
    public MyException() {
        super();
    }

    public MyException(String s) {
        super(s);
    }
}
