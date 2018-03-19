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
import hu.deepdata.indicatortoolbox.service.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.*

/**
 * @author Zsolt Jurányi
 */
@Controller
@RequestMapping(Paths.INDICATOR_DELETE_URI)
class AdminIndicatorDeleteController {

	@Autowired
	var indicatorService: IndicatorService? = null

	@GetMapping("{indicator-id}")
	fun deleteIndicatorForm(@PathVariable("indicator-id") id: Long): ModelAndView {
		val indicator = indicatorService!!.get(id) ?: throw PageNotFoundException()
		return ModelAndView(Paths.INDICATOR_DELETE_VIEW, mapOf("indicator" to indicator))
	}

	@PostMapping("{indicator-id}")
	fun deleteIndicator(@PathVariable("indicator-id") id: Long): String {
		indicatorService!!.delete(id)
		return "redirect:${Paths.INDICATOR_LIST_URI}"
	}
}