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

package hu.deepdata.indicatortoolbox.controller.advice

import com.samskivert.mustache.*
import org.springframework.web.bind.annotation.*
import java.io.*

/**
 * Populates "_admin", "_base" and "_error" Mustache lambda
 * objects to every page, to be used as layouts in the
 * templates.
 *
 * @author Zsolt Jurányi
 */
@ControllerAdvice
class MustacheLayoutAdvice {

	@ModelAttribute("_admin")
	fun adminLayout(): Mustache.Lambda = Layout()

	@ModelAttribute("_base")
	fun baseLayout(): Mustache.Lambda = Layout()

	@ModelAttribute("_error")
	fun errorLayout(): Mustache.Lambda = Layout()

	class Layout : Mustache.Lambda {
		var content: String = ""

		override fun execute(fragment: Template.Fragment, writer: Writer) {
			content = fragment.execute()
		}
	}
}