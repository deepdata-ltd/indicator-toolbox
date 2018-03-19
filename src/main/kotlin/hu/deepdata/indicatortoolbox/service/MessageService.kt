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

package hu.deepdata.indicatortoolbox.service

import hu.deepdata.indicatortoolbox.configuration.*
import mu.*
import org.springframework.beans.factory.annotation.*
import org.springframework.context.*
import org.springframework.stereotype.*
import java.util.*

/**
 * Helper service for handling messages.
 *
 * @author Zsolt Jurányi
 */
@Service
class MessageService {

	companion object : KLogging()

	@Autowired
	var messageSource: MessageSource? = null

	/**
	 * Resolves the message for the given key and locale. If
	 * the message is not found for the given locale, tries
	 * the lookup for the default locale (English). If that
	 * fails too, return the message key itself and produces
	 * a WARN message too to help debugging.
	 */
	fun resolveMessage(key: String, args: Array<Any>, locale: Locale): String {
		var message = tryToResolveMessage(key, args, locale)
		if (!I18nConfiguration.DEFAULT_LOCALE.equals(locale) && key.equals(message))
			message = tryToResolveMessage(key, args, I18nConfiguration.DEFAULT_LOCALE)
		return message
	}

	private fun tryToResolveMessage(key: String, args: Array<Any>, locale: Locale): String {
		try {
			return messageSource?.getMessage(key, args, locale)!!
		} catch (e: Exception) {
			logger.warn("Message not found: $key, locale: $locale")
			return key
		}
	}
}