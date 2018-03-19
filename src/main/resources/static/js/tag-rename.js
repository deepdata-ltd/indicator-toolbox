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

var
	tagRenameModalId = 'tag-rename-modal',
	tagRenameModel = {}
;

$(function(){
	tagRenameModel = ko.mapping.fromJS({
		id: 0,
		name: "",
		newName: ""
	});
	ko.applyBindings(tagRenameModel, document.getElementById(tagRenameModalId));

	var $modal = $('#' + tagRenameModalId);
	$modal.modal();

	$('.tag-rename-btn').click(function(){
		console.log('click');
		var $e = $(this);
		$e.blur();
		tagRenameModel.id($e.data('id'));
		tagRenameModel.name($e.data('name'));
		tagRenameModel.newName($e.data('name'));
		Materialize.updateTextFields();
		$modal.modal('open');
		$('#new-name').focus();
	});
});