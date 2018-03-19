/*
	Copyright 2018 DeepData Ltd.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/

var filterModel = ko.mapping.fromJS({ tags: [] });

$(function() {
	$('#filters').collapsible({
		accordion: true,
		onOpen: function(el) {
			$('#filters input[type=text]').first().focus();
		}
	});
	ko.applyBindings(filterModel, document.getElementById('filters'));
	initTagsEditor('tags', filterModel);
});


