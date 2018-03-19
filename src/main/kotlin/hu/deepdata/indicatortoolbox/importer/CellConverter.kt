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

package hu.deepdata.indicatortoolbox.importer

import hu.deepdata.indicatortoolbox.model.database.*

/**
 * Converts a string to special indicator field values if
 * possible. If conversion fails or is impossible, methods
 * return `null`.
 *
 * @author Zsolt Jurányi
 */
class CellConverter(private val s: String?) {

	/**
	 * Returns the input string as `IndicatorAutonomy` if possible. Returns `null` otherwise.
	 */
	fun asIndicatorAutonomy(): IndicatorAutonomy? = try {
		when (s) {
			"A" -> IndicatorAutonomy.AUTONOMOUS
			"E" -> IndicatorAutonomy.EL_OF_COMPOSITE
			"AE" -> IndicatorAutonomy.AUTONOMOUS_AND_COMPOSITE
			else -> IndicatorAutonomy.valueOf(s!!.toUpperCase().replace(' ', '_'))
		}
	} catch (e: Exception) {
		null
	}

	/**
	 * Returns the input string as `IndicatorProcessPhase` if possible. Returns `null` otherwise.
	 */
	fun asIndicatorProcessPhase(): IndicatorProcessPhase? = try {
		IndicatorProcessPhase.valueOf(s!!.toUpperCase().replace(' ', '_'))
	} catch (e: Exception) {
		null
	}

	/**
	 * Returns the input string as `IndicatorState` if possible. Returns `null` otherwise.
	 */
	fun asIndicatorState(): IndicatorState? = try {
		when (s) {
			"1" -> IndicatorState.ACTIVE
			"0" -> IndicatorState.INACTIVE
			"-1" -> IndicatorState.NOT_DEVELOPED
			else -> IndicatorState.valueOf(s!!.toUpperCase().replace(' ', '_'))
		}
	} catch (e: Exception) {
		null
	}

	/**
	 * Returns the input string as `IndicatorValidation` if possible. Returns `null` otherwise.
	 */
	fun asIndicatorValidation(): IndicatorValidation? = try {
		when (s) {
			"1" -> IndicatorValidation.VALIDATED
			"0" -> IndicatorValidation.NOT_VALIDATED
			else -> IndicatorValidation.valueOf(s!!.toUpperCase().replace(' ', '_'))
		}
	} catch (e: Exception) {
		null
	}

	/**
	 * Returns the input string as `Long` if possible. Returns `null` otherwise.
	 */
	fun asLong(): Long? = try {
		s!!.toLong()
	} catch (e: Exception) {
		null
	}

	/**
	 * Returns the input string if it's not `null` or blank. Returns `null` otherwise.
	 */
	fun asString() = if (s.isNullOrBlank()) null else s

	/**
	 * Returns the input string as a set of `Tag` objects if possible. Returns an empty set otherwise.
	 */
	fun asTagSet(): MutableSet<Tag> {
		val tags = mutableSetOf<Tag>()
		s?.split(",")?.forEach {
			val cleaned = it.trim().replace(Regex(" +"), " ")
			if (!cleaned.isEmpty()) tags.add(Tag(cleaned))
		}
		return tags
	}
}