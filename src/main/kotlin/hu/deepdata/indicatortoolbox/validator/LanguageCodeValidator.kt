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

package hu.deepdata.indicatortoolbox.validator

import java.util.*
import javax.validation.*

/**
 * Validator for language codes.
 *
 * @author Zsolt Jurányi
 */
class LanguageCodeValidator : ConstraintValidator<LanguageCodeConstraint, String> {

	companion object {
		val VALID_VALUES: Array<String> = Locale.getISOLanguages()
	}

	override fun initialize(a: LanguageCodeConstraint?) {}

	/**
	 * Return true if given language code is a valid language code.
	 */
	override fun isValid(languageCode: String?, context: ConstraintValidatorContext?): Boolean
			= languageCode.isNullOrBlank() || VALID_VALUES.contains(languageCode)
}