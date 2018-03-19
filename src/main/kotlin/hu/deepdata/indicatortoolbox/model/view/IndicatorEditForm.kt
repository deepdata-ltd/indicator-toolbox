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
import hu.deepdata.indicatortoolbox.validator.*
import java.text.*
import java.util.*
import javax.validation.constraints.*

/**
 * Represents an indicator form which is actually an
 * extended indicator definition. This definition is needed
 * for validation and conversion between indicator data
 * and form data (e.g. collection representations differ).
 *
 * @author Zsolt Jurányi
 */
@Suppress("MemberVisibilityCanPrivate", "unused")
class IndicatorEditForm(
		var id: Long? = null,

		var autonomy: IndicatorAutonomy? = null,

		@field:Size(min = 0, max = 2)
		@field:CountryCodeConstraint
		var country: String? = null,

		var dataSource: String? = null,

		var description: String? = null,

		@field:Size(min = 0, max = 255)
		var developer: String? = null,

		var developmentDate: Date? = null,

		@field:Size(min = 0, max = 2)
		@field:LanguageCodeConstraint
		var language: String? = null,

		@field:NotNull
		@field:Size(min = 1, max = 255)
		var name: String? = null,

		var processPhase: IndicatorProcessPhase? = null,

		@field:Size(min = 0, max = 255)
		var programmingLanguage: String? = null,

		@field:Size(min = 0, max = 255)
		var projectUrl: String? = null,

		@field:Size(min = 0, max = 255)
		var sourceCodeUrl: String? = null,

		var state: IndicatorState? = null,

		var validation: IndicatorValidation? = null,

		var validationMethod: String? = null,

		// --------------------
		// helper fields
		// --------------------
		var tags: MutableSet<String> = mutableSetOf()
) {

	var developmentDateStr: String?
		get() =
			if (null == developmentDate) null
			else SimpleDateFormat(DATE_FORMAT).format(developmentDate)
		set(value) {
			if (null != value && value.matches(DATE_PATTERN)) {
				developmentDate = SimpleDateFormat(DATE_FORMAT).parse(value)
			}
		}

	// --------------------
	// conversion
	// --------------------

	constructor(indicator: Indicator) : this(
			id = indicator.id,
			autonomy = indicator.autonomy,
			country = indicator.country,
			dataSource = indicator.dataSource,
			description = indicator.description,
			developer = indicator.developer,
			developmentDate = indicator.developmentDate,
			language = indicator.language,
			name = indicator.name,
			processPhase = indicator.processPhase,
			programmingLanguage = indicator.programmingLanguage,
			projectUrl = indicator.projectUrl,
			sourceCodeUrl = indicator.sourceCodeUrl,
			state = indicator.state,
			tags = indicator.tags.map(Tag::name).map { it!! }.toMutableSet(),
			validation = indicator.validation,
			validationMethod = indicator.validationMethod
	)

	fun toIndicator(): Indicator {
		IndicatorEditForm()
		val indicator = Indicator()
		toIndicator(indicator)
		return indicator
	}

	fun toIndicator(indicator: Indicator) {
		indicator.id = id
		indicator.autonomy = autonomy
		indicator.country = country
		indicator.dataSource = dataSource
		indicator.description = description
		indicator.developer = developer
		indicator.developmentDate = developmentDate
		indicator.language = language
		indicator.name = name
		indicator.processPhase = processPhase
		indicator.programmingLanguage = programmingLanguage
		indicator.projectUrl = projectUrl
		indicator.sourceCodeUrl = sourceCodeUrl
		indicator.state = state
		indicator.validation = validation
		indicator.validationMethod = validationMethod

		indicator.tags.clear()
		indicator.tags.addAll(tags.map { Tag(null, it) })
	}

	companion object {
		const val DATE_FORMAT = "yyyy-MM-dd"
		val DATE_PATTERN = Regex("\\d{4}-\\d{2}-\\d{2}")
	}
}