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

package hu.deepdata.indicatortoolbox.controller.helper

import com.ibm.icu.util.*
import hu.deepdata.indicatortoolbox.model.view.*
import hu.deepdata.indicatortoolbox.service.*
import hu.deepdata.indicatortoolbox.validator.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*
import java.util.*

/**
 * Utility to generate SelectOption lists from countries/languages where
 * the specified one is marked as selected. This is used by the indicator
 * edit form and the indicator list filter.
 *
 * @author Zsolt Jurányi
 */
@Component
class OptionGenerator {

	val COUNTRIES = CountryCodeValidator.VALID_VALUES.map { ULocale("", it) }
	val LANGUAGES = LanguageCodeValidator.VALID_VALUES.map { ULocale(it) }

	@Autowired
	var messageService: MessageService? = null

	fun countryOptions(selected: String?, displayULocale: ULocale): List<SelectOption> =
			COUNTRIES.map {
				SelectOption(it.country,
						it.getDisplayCountry(displayULocale),
						it.country.equals(selected, true))
			}.sortedBy { it.text }

	fun languageOptions(selected: String?, displayULocale: ULocale): List<SelectOption> =
			LANGUAGES.map {
				SelectOption(it.language,
						it.getDisplayLanguage(displayULocale),
						it.language.equals(selected, true))
			}.sortedBy { it.text }

	inline fun <reified T : Enum<T>> optionsForEnum(field: String, selected: T?, locale: Locale): List<SelectOption> =
			enumValues<T>().sorted().map {
				SelectOption(
						it.toString(),
						messageService?.resolveMessage(
								"indicator.$field.$it", arrayOf(), locale
						) ?: it.toString(),
						it == selected
				)
			}
}