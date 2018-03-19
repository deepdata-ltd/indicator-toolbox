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

package hu.deepdata.indicatortoolbox.model.database

import org.springframework.security.core.authority.*
import org.springframework.security.core.userdetails.*
import javax.persistence.*

/**
 * Represents an user account. User have a nickname which is
 * their identifier and a password. Additionally there is an
 * active flag which can be used to disable a user in the
 * system.
 *
 * @author Zsolt Jurányi
 */
@Entity
class Account(

		@Id
		var nick: String? = null,

		@Column(nullable = false)
		var pass: String? = null,

		@Column(nullable = false)
		var active: Boolean = false

) {

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Account) return false
		if (!super.equals(other)) return false
		if (nick != other.nick) return false
		return true
	}

	override fun hashCode(): Int {
		var result = super.hashCode()
		result = 31 * result + (nick?.hashCode() ?: 0)
		return result
	}

	override fun toString(): String {
		return "Account(nick=$nick, pass=$pass, active=$active)"
	}

	fun toUser() = User(
			nick, pass, active,
			true, true, true,
			AuthorityUtils.createAuthorityList("USER")
	)

}