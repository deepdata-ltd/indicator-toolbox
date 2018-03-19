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

package hu.deepdata.indicatortoolbox.controller.advice

import com.samskivert.mustache.*
import hu.deepdata.indicatortoolbox.service.*
import org.springframework.beans.factory.annotation.*
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * Populates "locale" model attribute holding the current
 * Locale object, and "m" Mustache lambda object to be
 * able to get i18n messages in templates.
 *
 * It uses MessageService to resolve messages.
 *
 * Populates also a "nonEnglishLanguage" model attribute
 * which has true value if current language is not
 * English.
 *
 * @author Zsolt Jurányi
 */
@ControllerAdvice
class MustacheMessageAdvice {

	@Autowired
	var messageService: MessageService? = null

	@ModelAttribute("locale")
	fun locale(locale: Locale) = locale

	@ModelAttribute("m")
	fun message(locale: Locale) =
			Mustache.Lambda { fragment, writer ->
				val key = fragment.execute()
				// TODO parse message args from frag?
				val output = messageService!!.resolveMessage(key, arrayOf(), locale)
				writer.write(output)
			}

	@ModelAttribute("nonEnglishLocale")
	fun nonEnglishLocale(locale: Locale) = !locale.toString().contains("en")

}