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

import hu.deepdata.indicatortoolbox.service.*
import org.springframework.beans.factory.annotation.*
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.*

/**
 * Populates the "title" model attribute which contains an
 * auto-generated title using the current URI and the
 * message files.
 *
 * @author Zsolt Jurányi
 */
@ControllerAdvice
class TitleAdvice {

	@Autowired
	var messageService: MessageService? = null

	/**
	 * Generates a message key from the given. Examples:
	 *
	 * - / -> index.title
	 * - /a -> a.title
	 * - /a/ -> a.title
	 * - /a/b -> a.title
	 * - /a/b/ -> a.b.title
	 * - /a/b/c -> a.b.title
	 * - /a/b/c/ -> a.b.c.title
	 *
	 */
	fun generate(uri: String) = uri
			.split('/')
			.filter { it.isNotBlank() }
			.toMutableList()
			.apply {
				if (!uri.trim().endsWith("/") && size > 1) removeAt(size - 1)
				if (isEmpty()) add("index")
				add("title")
			}
			.joinToString(".")

	/**
	 * Generates a message based on the request URI.
	 * Uses generate method and MessageService.
	 */
	@ModelAttribute("title")
	fun title(r: HttpServletRequest, locale: Locale) =
			messageService!!.resolveMessage(generate(r.requestURI), arrayOf(), locale)
}