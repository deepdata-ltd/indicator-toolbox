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
import hu.deepdata.indicatortoolbox.model.database.*
import java.util.*

/**
 * Helper to make the indicator more readable on the web.
 *
 * @author Zsolt Jurányi
 */
object IndicatorViewHelper {

	/**
	 * Replaces country/language codes with the appropriate i18nalized
	 * name of the country/language. Sorts tags. Replaces line breaks
	 * in the description field with &lt;br&gt; tags.
	 */
	fun prepareIndicatorForView(indicator: Indicator, locale: Locale) {

		val displayLocale = ULocale.forLocale(locale)
		if (null != indicator.country) {
			indicator.country = ULocale("", indicator.country).getDisplayCountry(displayLocale)
		}
		if (null != indicator.language) {
			indicator.language = ULocale(indicator.language).getDisplayLanguage(displayLocale)
		}

		indicator.description = indicator.description?.trim()?.replace(Regex("\n"), "<br>")

		indicator.tags = indicator.tags.sortedBy { it.name }.toMutableSet()
	}
}