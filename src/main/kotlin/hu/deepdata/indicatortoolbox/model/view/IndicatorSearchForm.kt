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

package hu.deepdata.indicatortoolbox.model.view

import hu.deepdata.indicatortoolbox.model.database.*

/**
 * Represents the search form for indicators.
 *
 * @author Zsolt Jurányi
 */
data class IndicatorSearchForm(
		var autonomy: IndicatorAutonomy? = null,
		var country: String? = null,
		var language: String? = null,
		var page: Int = 0,
		var processPhase: IndicatorProcessPhase? = null,
		var search: String? = null,
		var state: IndicatorState? = null,
		var tags: MutableSet<String> = mutableSetOf(),
		var validation: IndicatorValidation? = null
) {

	/**
	 * Calculates whether the form should be displayed.
	 * Returns true if any field has a non-empty or
	 * non-default value.
	 */
	fun open(): Boolean
			= null != autonomy
			|| !country.isNullOrBlank()
			|| !language.isNullOrBlank()
			|| null != processPhase
			|| !search.isNullOrBlank()
			|| null != state
			|| !tags.isEmpty()
			|| null != validation

	/**
	 * Joins fields in query string format (e.g. "x=1&y=2")
	 * to be used in links.
	 */
	override fun toString(): String {
		val list = mutableListOf<String>()
		autonomy?.let { list += "autonomy=$it" }
		if (!country.isNullOrBlank()) list.add("country=$country")
		if (!language.isNullOrBlank()) list.add("language=$language")
		processPhase?.let { list += "processPhase=$it" }
		if (!search.isNullOrBlank()) list.add("search=$search")
		state?.let { list += "state=$it" }
		validation?.let { list += "validation=$it" }
		tags.forEach { list.add("tags=$it") }
		return list.joinToString("&")
	}
}