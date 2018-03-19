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
import org.springframework.beans.factory.annotation.*
import org.springframework.core.io.*
import org.springframework.stereotype.*
import org.springframework.util.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.*
import java.nio.charset.*

/**
 * @author Zsolt Jurányi
 */
@Controller
class AdminController {

	@Value("classpath:admin-manual.md")
	protected var manual: Resource? = null

	@GetMapping(Paths.ADMIN_URI)
	fun index() = "redirect:${Paths.ADMIN_MANUAL_URI}"

	@GetMapping(Paths.ADMIN_MANUAL_URI)
	fun manual(): ModelAndView {
		var markdown = ""
		manual!!.inputStream.use {
			markdown = StreamUtils.copyToString(it, Charset.forName("utf-8"))
		}
		return ModelAndView(Paths.ADMIN_VIEW, mapOf("manual" to markdown))
	}
}