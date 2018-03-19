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

import org.springframework.context.*
import org.springframework.context.annotation.*
import org.springframework.context.support.*
import org.springframework.web.servlet.*
import org.springframework.web.servlet.config.annotation.*
import org.springframework.web.servlet.i18n.*
import java.util.*

/**
 * Configuration class which enables I18n throughout the
 * website. Locale then can be switched by passing "lang=LOCALE"
 * parameter to any request. The default locale is English
 * of course.
 *
 * @author Zsolt Jurányi
 */
@Configuration
open class I18nConfiguration : WebMvcConfigurerAdapter() {

	companion object {
		val DEFAULT_LOCALE: Locale = Locale.ENGLISH
	}

	override fun addInterceptors(registry: InterceptorRegistry?) {
		registry!!.addInterceptor(localeChangeInterceptor())
	}

	@Bean
	open fun localeChangeInterceptor(): LocaleChangeInterceptor =
			LocaleChangeInterceptor().apply {
				paramName = "lang"
			}

	@Bean
	open fun localeResolver(): LocaleResolver =
			SessionLocaleResolver().apply {
				setDefaultLocale(DEFAULT_LOCALE)
			}

	@Bean
	open fun messageSource(): MessageSource =
			ReloadableResourceBundleMessageSource().apply {
				setBasename("classpath:messages")
				setCacheSeconds(-1)
			}
}