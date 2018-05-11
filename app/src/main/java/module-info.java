module bonux.yada {
    requires bonux.yada.todo;
    requires bonux.yada.auth;

    uses bonux.yada.auth.YadaSecurityConfig;
    uses bonux.yada.auth.YadaUserDetailsService;

    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.tx;
    requires spring.security.config;
}