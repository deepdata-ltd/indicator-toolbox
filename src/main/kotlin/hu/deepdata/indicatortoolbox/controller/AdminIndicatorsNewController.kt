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
import hu.deepdata.indicatortoolbox.controller.helper.*
import hu.deepdata.indicatortoolbox.model.database.*
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
@RequestMapping(Paths.INDICATOR_ADD_URI)
class AdminIndicatorsNewController {

	@Autowired
	var helper: IndicatorEditHelper? = null

	@Autowired
	var indicatorService: IndicatorService? = null

	@GetMapping("/")
	fun newIndicator(
			@RequestParam(value = "base", required = false) base: Long?,
			locale: Locale): ModelAndView {

		var indicator: Indicator? = null
		base?.apply { indicator = indicatorService!!.get(this) }
		val form = if (null != indicator) IndicatorEditForm(indicator!!) else IndicatorEditForm()
		form.apply {
			id = null
			name = null
		}
		return helper!!.editView(form, null, locale)
	}

	@PostMapping("/")
	fun saveNewIndicator(
			@Valid @ModelAttribute form: IndicatorEditForm,
			bindingResult: BindingResult, locale: Locale): ModelAndView {

		if (null != form.id) {
			form.id = if (indicatorService!!.isExistingId(form.id!!)) null else form.id
		}
		if (!bindingResult.hasErrors()) {
			val indicator = indicatorService!!.save(form)
			return ModelAndView("redirect:${Paths.INDICATOR_URI}${indicator.id}")
		}
		return helper!!.editView(form, bindingResult, locale)
	}

}