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

package hu.deepdata.indicatortoolbox.test

import hu.deepdata.indicatortoolbox.controller.advice.*
import org.junit.Test
import kotlin.test.*

/**
 * @author Zsolt Jurányi
 */
class TitleMessageKeyGenerationTest {

	val g = TitleAdvice()

	@Test
	fun root() {
		assertEquals("index.title", g.generate("/"))
	}

	@Test
	fun oneWithoutTrailingSlash() {
		assertEquals("a.title", g.generate("/a"))
	}

	@Test
	fun oneWithTrailingSlash() {
		assertEquals("a.title", g.generate("/a/"))
	}

	@Test
	fun moreWithoutTrailingSlash() {
		assertEquals("a.title", g.generate("/a/b"))
		assertEquals("a.b.title", g.generate("/a/b/c"))
	}

	@Test
	fun moreWithTrailingSlash() {
		assertEquals("a.b.title", g.generate("/a/b/"))
		assertEquals("a.b.c.title", g.generate("/a/b/c/"))
	}
}