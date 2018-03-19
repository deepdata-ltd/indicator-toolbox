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

import hu.deepdata.indicatortoolbox.repository.*
import org.springframework.beans.factory.annotation.*
import org.springframework.context.annotation.*
import org.springframework.security.config.annotation.authentication.builders.*
import org.springframework.security.config.annotation.authentication.configurers.*
import org.springframework.security.core.userdetails.*

/**
 * Configuration class telling Spring how to find user
 * accounts in the database.
 *
 * @author Zsolt Jurányi
 */
@Configuration
open class GlobalAuthenticationConfiguration : GlobalAuthenticationConfigurerAdapter() {

	@Autowired
	var accountRepository: AccountRepository? = null

	override fun init(auth: AuthenticationManagerBuilder?) {
		auth!!.userDetailsService(userDetailsService())
	}

	@Bean
	open fun userDetailsService(): UserDetailsService {
		return UserDetailsService { id ->
			accountRepository!!.findOne(id)?.toUser()
					?: throw UsernameNotFoundException("Could not find user: $id")
		}
	}
}