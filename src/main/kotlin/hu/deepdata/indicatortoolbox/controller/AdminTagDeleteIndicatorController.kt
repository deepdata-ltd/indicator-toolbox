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
@RequestMapping(Paths.TAG_DELETE_URI)
class AdminTagDeleteIndicatorController {

	@Autowired
	var tagService: TagService? = null

	@GetMapping("{tag-id}")
	fun deleteIndicatorForm(@PathVariable("tag-id") id: Long): ModelAndView {
		val tag = tagService!!.get(id) ?: throw PageNotFoundException()
		return ModelAndView(Paths.TAG_DELETE_VIEW, mapOf("tag" to tag))
	}

	@PostMapping("{tag-id}")
	fun deleteIndicator(@PathVariable("tag-id") id: Long): String {
		tagService!!.delete(id)
		return "redirect:${Paths.TAG_LIST_URI}"
	}
}