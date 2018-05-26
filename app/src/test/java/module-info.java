module bonux.yada {
    requires bonux.yada.auth;
    requires bonux.yada.todo;

    uses bonux.yada.auth.YadaSecurityConfig;
    uses bonux.yada.auth.YadaUserDetailsService;

    requires spring.beans;
    requires spring.context;
    requires spring.test;
    requires spring.security.core;
    requires spring.security.config;
    requires spring.boot.autoconfigure;
    requires spring.boot.test.autoconfigure;
    requires spring.boot.test;
    requires junit;
}