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

import hu.deepdata.indicatortoolbox.controller.exception.*
import org.springframework.stereotype.*
import org.springframework.web.bind.annotation.*
import javax.servlet.http.*

/**
 * @author Zsolt Jurányi
 */
@Controller
class AutoSlashController {

	@GetMapping(
			"*",
			"/admin/*",
			"/admin/indicators/*",
			"/admin/tags/*"
	) // cannot use only "**", it matches static files too
	fun autoAddTrailingSlash(r: HttpServletRequest): String {
		if (!r.requestURI.endsWith("/")) return "redirect:${r.requestURI}/"
		throw PageNotFoundException()
	}
}