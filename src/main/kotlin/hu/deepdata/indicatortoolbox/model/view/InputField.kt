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

package hu.deepdata.indicatortoolbox.model.view

import com.fasterxml.jackson.databind.*
import com.samskivert.mustache.*
import hu.deepdata.indicatortoolbox.service.*
import org.springframework.validation.*
import java.io.*
import java.util.*

/**
 * Mustache lambda definition for indicators' string fields,
 * to reduce markup in the view template.
 *
 * @author Zsolt Jurányi
 */
class InputField(
		val form: IndicatorEditForm,
		val bindingResult: BindingResult?,
		val messageService: MessageService,
		val locale: Locale) : Mustache.Lambda {

	val map = ObjectMapper().convertValue(form, Map::class.java) as Map<*, *>

	var error: Boolean = false
	var errorLabel: String = ""
	var field: String = ""
	var icon: String = ""
	var label: String = ""
	var readOnly: Boolean = false
	var value: String = ""

	override fun execute(f: Template.Fragment, w: Writer) {
		field = f.execute()
		error = bindingResult?.hasFieldErrors(field) ?: false
		errorLabel = messageService.resolveMessage("indicator.$field.error", arrayOf(), locale)
		icon = messageService.resolveMessage("indicator.$field.icon", arrayOf(), locale)
		label = messageService.resolveMessage("indicator.$field", arrayOf(), locale)
		value = if (map.containsKey(field)) map[field]?.toString() ?: "" else ""
	}

}