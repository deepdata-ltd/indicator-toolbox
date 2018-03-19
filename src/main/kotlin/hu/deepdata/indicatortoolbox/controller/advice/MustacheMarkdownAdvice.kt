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

import com.github.rjeschke.txtmark.*
import com.samskivert.mustache.Mustache.*
import org.springframework.web.bind.annotation.*

/**
 * Populates a "markdown" Mustache lambda object to
 * every page to be able to use Markdown format in
 * the templates.
 *
 * @author Zsolt Jurányi
 */
@ControllerAdvice
class MustacheMarkdownAdvice {

	@ModelAttribute("markdown")
	fun message() =
			Lambda { fragment, writer ->
				val markdown = fragment.execute()
				writer.write(Processor.process(markdown))
			}
}