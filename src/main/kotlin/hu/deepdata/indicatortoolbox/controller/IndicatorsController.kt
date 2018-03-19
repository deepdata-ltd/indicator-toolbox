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

package hu.deepdata.indicatortoolbox.controller

import com.ibm.icu.util.*
import hu.deepdata.indicatortoolbox.configuration.*
import hu.deepdata.indicatortoolbox.controller.helper.*
import hu.deepdata.indicatortoolbox.model.database.*
import hu.deepdata.indicatortoolbox.model.view.*
import hu.deepdata.indicatortoolbox.repository.*
import hu.deepdata.indicatortoolbox.service.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.*
import java.util.*

/**
 * @author Zsolt Jurányi
 */
@Controller
@RequestMapping(Paths.INDICATOR_LIST_URI)
class IndicatorsController {

	@Autowired
	var indicators: IndicatorService? = null

	@Autowired
	var optionGenerator: OptionGenerator? = null

	@Autowired
	var tags: TagRepository? = null

	@GetMapping("/")
	fun indicators(
			@ModelAttribute form: IndicatorSearchForm,
			locale: Locale): ModelAndView {

		val displayULocale = ULocale.forLocale(locale)
		val result = indicators!!.search(form)
		result.indicators.forEach { IndicatorViewHelper.prepareIndicatorForView(it, locale) }
		return ModelAndView(Paths.INDICATOR_LIST_VIEW, mapOf(
				"allTags" to tags!!.findAll().map(Tag::name),
				"autonomyOptions" to optionGenerator?.optionsForEnum("autonomy", form.autonomy, locale),
				"countryOptions" to optionGenerator?.countryOptions(form.country, displayULocale),
				"form" to form,
				"languageOptions" to optionGenerator?.languageOptions(form.language, displayULocale),
				"processPhaseOptions" to optionGenerator?.optionsForEnum("processPhase", form.processPhase, locale),
				"result" to result,
				"stateOptions" to optionGenerator?.optionsForEnum("state", form.state, locale),
				"validationOptions" to optionGenerator?.optionsForEnum("validation", form.validation, locale)
		))
	}
}