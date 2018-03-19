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

import hu.deepdata.indicatortoolbox.model.database.*
import hu.deepdata.indicatortoolbox.repository.*
import mu.*
import org.springframework.beans.factory.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*

/**
 * Utility to create/reset user accounts in the database
 * based on configuration properties.
 *
 * Properties are loaded from application.properties with
 * prefix "admins". There are 3 loaded properties:
 *
 * - accounts: list of Account objects
 * - activate: boolean value telling the program whether
 * listed accounts should be reactivated or not
 * - reset: boolean value telling the program whether
 * specified passwords should be loaded and stored or
 * not
 *
 * @author Zsolt Jurányi
 */
@Component
@ConfigurationProperties(prefix = "admins")
open class AdminsConfiguration : InitializingBean {

	companion object : KLogging()

	var accounts: MutableList<Account> = mutableListOf()
	var activate: Boolean = false
	var reset: Boolean = false

	@Autowired
	var repository: AccountRepository? = null

	override fun afterPropertiesSet() {
		accounts.forEach {
			var account = repository!!.findOne(it.nick)
			var needSave = false
			var verb = ""

			if (null == account || reset) {
				verb = if (null == account) "Creating" else "Resetting"
				account = it
				needSave = true
			}

			if (activate && !account.active) {
				verb += " activating"
				account.active = true
				needSave = true
			}

			if (needSave) {
				verb = verb.trim().split(" ").joinToString(" and ").capitalize()
				logger.info("$verb account: ${account.nick}")
				repository!!.save(account)
			}
		}
	}
}