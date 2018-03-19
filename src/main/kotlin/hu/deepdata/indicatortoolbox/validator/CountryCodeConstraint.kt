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

import javax.validation.*
import kotlin.reflect.*

/**
 * @author Zsolt Jurányi
 */
@Constraint(validatedBy = arrayOf(CountryCodeValidator::class))
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class CountryCodeConstraint(
		val message: String = "Invalid country code",
		val groups: Array<KClass<*>> = arrayOf(),
		val payload: Array<KClass<out Payload>> = arrayOf()
)