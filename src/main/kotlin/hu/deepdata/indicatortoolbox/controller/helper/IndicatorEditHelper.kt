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
import hu.deepdata.indicatortoolbox.model.view.*
import hu.deepdata.indicatortoolbox.repository.*
import hu.deepdata.indicatortoolbox.service.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*
import org.springframework.validation.*
import org.springframework.web.servlet.*
import java.util.*

/**
 * Component to generate indicator edit form model. Handles
 * when ID field should be read-only, calls country/language
 * select option generator, sets form target URI, puts data
 * for autocompletes into the model.
 *
 * @author Zsolt Jurányi
 */
@Component
class IndicatorEditHelper {

	@Autowired
	var indicatorRepository: IndicatorRepository? = null

	@Autowired
	var messageService: MessageService? = null

	@Autowired
	var optionGenerator: OptionGenerator? = null

	@Autowired
	var tagService: TagService? = null

	fun editView(form: IndicatorEditForm,
	             bindingResult: BindingResult?,
	             locale: Locale): ModelAndView {

		val displayULocale = ULocale.forLocale(locale)

		val errors = mutableMapOf<String, String>()
		if (null != bindingResult && bindingResult.hasErrors()) {
			bindingResult.fieldErrors.forEach { fe ->
				errors.put(fe.field, fe.defaultMessage)
			}
		}

		return ModelAndView("admin/indicator", mapOf(
				"action" to if (null == form.id) "" else form.id.toString(),
				"allDevelopers" to indicatorRepository?.queryDevelopers()?.filterNotNull(),
				"allProgrammingLanguages" to indicatorRepository?.queryProgrammingLanguages()?.filterNotNull(),
				"allProjectUrls" to indicatorRepository?.queryProjectUrls()?.filterNotNull(),
				"allSourceCodeUrls" to indicatorRepository?.querySourceCodeUrls()?.filterNotNull(),
				"allTags" to tagService?.all()?.map(Tag::name),
				"autonomyOptions" to optionGenerator?.optionsForEnum("autonomy", form.autonomy, locale),
				"countryOptions" to optionGenerator?.countryOptions(form.country, displayULocale),
				"errors" to errors,
				"indicator" to form,
				"input-field" to InputField(form, bindingResult, messageService!!, locale),
				"languageOptions" to optionGenerator?.languageOptions(form.language, displayULocale),
				"processPhaseOptions" to optionGenerator?.optionsForEnum("processPhase", form.processPhase, locale),
				"subtitle" to if (form.name.isNullOrBlank()) null else form.name,
				"stateOptions" to optionGenerator?.optionsForEnum("state", form.state, locale),
				"validationOptions" to optionGenerator?.optionsForEnum("validation", form.validation, locale)
		))
	}

}