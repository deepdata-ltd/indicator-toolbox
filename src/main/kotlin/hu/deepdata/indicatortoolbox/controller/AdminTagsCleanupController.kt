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
import hu.deepdata.indicatortoolbox.repository.*
import hu.deepdata.indicatortoolbox.service.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.*

/**
 * @author Zsolt Jurányi
 */
@Controller
@RequestMapping(Paths.TAG_CLEANUP_URI)
class AdminTagsCleanupController {

	@Autowired
	var tagRepository: TagRepository? = null

	@Autowired
	var tagService: TagService? = null

	@GetMapping("/")
	fun cleanupConfirmation(): ModelAndView {
		val emptyTags = tagRepository?.queryEmptyTags()
		if (emptyTags?.isEmpty() != false) return ModelAndView("redirect:${Paths.TAG_LIST_URI}")
		return ModelAndView(Paths.TAG_CLEANUP_VIEW, mapOf("emptyTags" to emptyTags))
	}

	@PostMapping("/")
	fun cleanup(): String {
		tagRepository?.queryEmptyTags()?.map({ it.id!! })?.forEach(tagService!!::delete)
		return "redirect:${Paths.TAG_LIST_URI}"
	}

}