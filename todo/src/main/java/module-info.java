module bonux.yada.todo {
    exports bonux.yada.todo.api;

    requires bonux.yada.auth;

    requires spring.tx;
    requires spring.security.core;
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