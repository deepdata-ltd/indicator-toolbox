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
import hu.deepdata.indicatortoolbox.model.database.*
import hu.deepdata.indicatortoolbox.service.*
import java.io.*
import java.util.*

/**
 * Mustache lambda definition to reduce the code needed for
 * printing out indicator fields on the details page.
 *
 * @author Zsolt Jurányi
 */
class DetailsField(
		val indicator: Indicator,
		val messageService: MessageService,
		val locale: Locale
) : Mustache.Lambda {

	val map = ObjectMapper().convertValue(indicator, Map::class.java) as Map<*, *>

	var icon: String = ""
	var label: String = ""
	var value: String? = null

	override fun execute(f: Template.Fragment, w: Writer) {
		var field = f.execute()
		val isEnum = field.endsWith("!enum")
		field = field.replace(Regex("!.*"), "")
		icon = messageService.resolveMessage("indicator.$field.icon", arrayOf(), locale)
		label = messageService.resolveMessage("indicator.$field", arrayOf(), locale)
		value = if (map.containsKey(field)) map[field]?.toString() ?: "" else ""
		if (isEnum) {
			value =
					if (value.isNullOrBlank()) "N/A" // TODO move to message
					else messageService.resolveMessage("indicator.$field.$value", arrayOf(), locale)
		} else {
			value = value!!
					.replace(Regex("\n"), "<br>")
					.replace(Regex("(\\W|^)((f|ht)tps?:\\/\\/[^ ]+)"), "<a href='$2' target='_blank'>$2</a>")
			value = if (value.isNullOrBlank()) null else value
		}
	}

}