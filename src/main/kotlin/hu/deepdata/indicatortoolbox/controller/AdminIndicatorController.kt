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

import hu.deepdata.indicatortoolbox.configuration.*
import hu.deepdata.indicatortoolbox.controller.exception.*
import hu.deepdata.indicatortoolbox.controller.helper.*
import hu.deepdata.indicatortoolbox.model.view.*
import hu.deepdata.indicatortoolbox.service.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*
import org.springframework.validation.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.*
import java.util.*
import javax.validation.*

/**
 * @author Zsolt Jurányi
 */
@Controller
@RequestMapping(Paths.INDICATOR_EDIT_URI)
class AdminIndicatorController {

	@Autowired
	var helper: IndicatorEditHelper? = null

	@Autowired
	var indicatorService: IndicatorService? = null

	@GetMapping("{indicator-id}")
	fun editIndicator(@PathVariable("indicator-id") id: Long, locale: Locale): ModelAndView {
		val indicator = indicatorService!!.get(id) ?: throw PageNotFoundException()
		return helper!!.editView(IndicatorEditForm(indicator), null, locale)
	}

	@PostMapping("{indicator-id}")
	fun saveIndicator(
			@PathVariable("indicator-id") id: Long,
			@Valid @ModelAttribute form: IndicatorEditForm,
			bindingResult: BindingResult,
			locale: Locale): ModelAndView {

		if (id != form.id) {
			throw IllegalArgumentException("Indicator identifier in URI does not match the one in form data, refusing action.")
		}

		if (!bindingResult.hasErrors()) {
			indicatorService!!.save(form)
			return ModelAndView("redirect:${Paths.INDICATOR_URI}${form.id}")
		}
		return helper!!.editView(form, bindingResult, locale)
	}
}