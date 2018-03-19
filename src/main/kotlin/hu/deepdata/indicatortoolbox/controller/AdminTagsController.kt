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
import hu.deepdata.indicatortoolbox.service.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.*

/**
 * @author Zsolt Jurányi
 */
@Controller
@RequestMapping(Paths.TAG_LIST_URI)
class AdminTagsController {

	@Autowired
	var tags: TagService? = null

	@GetMapping("/")
	fun tags() = ModelAndView(Paths.TAG_LIST_VIEW, mapOf("tags" to tags!!.all()))

	@PostMapping("/")
	fun renameTag(
			@RequestParam("id") id: Long,
			@RequestParam("name") name: String
	): String {
		tags?.rename(id, name)
		return "redirect:${Paths.TAG_LIST_URI}"
	}

}