package com.auctiontoyapi.common.config

import com.auctiontoyapi.common.jwt.JwtAuthenticationFilter
import com.auctiontoyapi.common.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider
): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {

        http.csrf().disable()
        http.authorizeRequests()
            .antMatchers("/member/sign-up", "/hello", "/member/sign-in").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin()
            .loginPage("/login").permitAll()
            .and()
            .logout()
            .logoutSuccessUrl("/login")
            .and()
            .addFilterBefore(JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }


}