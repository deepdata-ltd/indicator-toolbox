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

package hu.deepdata.indicatortoolbox.configuration

import org.springframework.boot.autoconfigure.web.*

/**
 * Utility telling Spring we want to override the
 * whitelabel error page with custom ones.
 *
 * @author Zsolt Jurányi
 */
class ErrorPageConfiguration : ErrorController {
	// don't need annotations nor any other method.
	// Spring detects that I implemented an ErrorController
	// and will kindly serve error pages with my
	// error/*.html files.

	companion object {
		const val PATH = "/error"
	}

	override fun getErrorPath(): String = PATH

}