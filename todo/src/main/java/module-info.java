module bonux.yada.todo {
    requires spring.boot;
    requires spring.boot.autoconfigure;

    requires spring.aop;
    requires spring.tx;
    requires spring.security.core;
    requires spring.security.config;
    requires spring.beans;
    requires spring.web;
    requires spring.webmvc;
    requires spring.hateoas;
    requires spring.jdbc;
    requires spring.context;

    requires slf4j.api;

    requires jackson.annotations;

    requires java.sql;

    requires com.zaxxer.hikari;
}