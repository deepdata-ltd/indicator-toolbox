/*
 *    Copyright 2018 DeepData Ltd.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package hu.deepdata.indicatortoolbox.configuration

import org.springframework.beans.factory.annotation.*
import org.springframework.context.annotation.*
import org.springframework.jdbc.core.*
import org.springframework.security.config.annotation.authentication.builders.*
import org.springframework.security.config.annotation.web.builders.*
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.core.userdetails.*
import org.springframework.security.crypto.bcrypt.*
import org.springframework.security.web.authentication.rememberme.*
import org.springframework.security.web.util.matcher.*
import java.util.concurrent.*

/**
 * Configuration for Spring Security. Sets up form login,
 * and persistent tokens. The "/admin" path will be the
 * restricted area.
 *
 * @author Zsolt Jurányi
 */
@Configuration
@EnableWebSecurity
open class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {

	@Autowired
	private val jdbc: JdbcTemplate? = null

	override fun configure(http: HttpSecurity) {
		http.authorizeRequests()
				// /admin is secured
				.antMatchers("/admin", "/admin/**").authenticated()

				// anything else is public
				.anyRequest().permitAll()
				.and()

				// login page
				.formLogin()
				.defaultSuccessUrl(Paths.ADMIN_URI)
				.loginPage(Paths.LOGIN_URI).permitAll()
				.and()

				// logout settings
				.logout()
				.logoutRequestMatcher(AntPathRequestMatcher(Paths.LOGOUT_URI))
				.deleteCookies("JSESSIONID", "remember-me")
				.invalidateHttpSession(true)
				.logoutSuccessUrl("/")
				.permitAll()

				// remember me feature
				.and()
				.rememberMe()
				.tokenValiditySeconds(TimeUnit.SECONDS.convert(7, TimeUnit.DAYS).toInt())
				.tokenRepository(persistentTokenRepository())
	}

	@Autowired
	fun configureGlobal(auth: AuthenticationManagerBuilder) {
		auth.userDetailsService<UserDetailsService>(userDetailsService()).passwordEncoder(
				BCryptPasswordEncoder())
	}

	private fun persistentTokenRepository(): PersistentTokenRepository {
		val tr = JdbcTokenRepositoryImpl()
		tr.jdbcTemplate = jdbc
		return tr
	}

}