# Administration manual



## Overview

This website is a catalog of red flags indicators. The site is divided into a **public** and a **restricted** area.
 
**Public pages** include the following:

* Welcome page
* Indicator list page
* Indicator details pages

These pages can be accessed from the top menu (or left side menu on mobile).

**Restricted pages** are for administrators only:

* Administrator manual
* Indicator edit pages
* Tag list page

Furthermore, public pages contain extra functions when an administrator is logged in to the website.


## Accessing administrative features

After logging in, editing functions can be accessed in multiple ways:

* the **top navigation bar** provides links to list pages
* **page headers** have action buttons on the right for page related actions
* **list items** show action buttons on the right for list item related actions
* **bottom-right menu button** shows site-wide action buttons and also page related ones if possible

Button coloring tries to be consistent:

* **pink** is used for primary action
* **green** is used for addition
* **blue** is used for edition or secondary action
* **red** is used for deletion
* **purple** is used for tag list management actions



## Managing indicators

Indicators can be **added, cloned, edited,** or **deleted**. The first step is usually to visit the indicator list page.


**Adding an indicator**

* By clicking on the *Add indicator* button, an indicator **edit form** will be displayed, with all empty fields.
* In order to **save the new indicator** using the button blow the form, at least the **identifier and the name must be provided.**
* Also, the **identifier must be unique**, it must not exist in the database at the time of saving the new indicator.
* Country and language field have **select menus** to ensure a valid value. **Typing the prefix** of the country/language can speed up selection.
* Every text field (excluding *identifier*, *name* and *description*) has an **autocomplete** feautre. It means that when the user types, the site makes a search in the background and display search result for that particular field. For example, when the *developer* field is being edited, the site will display search results from all the other *developers* in the database. This feature helps selecting an existing value faster.
* Every text field has the length **limit of 255 characters**, except *description*.
* The *development date* field can be set by using the built-in **datepicker**. On the top of the dialog, the month and the year can be selected from dropdown lists. Below that a traditional month view can be used to select a day by clicking on it. To apply the selected date to the form, one must click on the *OK* button. 
* Indicator status between **active** and **inactive** can be changed by simply clicking on the switch.
* **Tags can be added** to an indicator by typing the tag in the *Tags* field then pressing *Enter*. **Autocomplete** feature works here as well, it helps adding existing tags to the indicator. Note that tags are **converted into lowercase** letters. **Tags can be removed** by clicking on the *X* mark inside them or by selecting them with arrow keys and hitting *Backspace/Delete*
* **Connecting indicators** works a bit similar to tags. **Autocomplete** search helps in finding the right indicator to add. To add a related indicator, one has to type/select an **indicator identifier**. To remove a related indicator, the **red trash icon** on the right of its identifier can be used.


**Cloning an indicator**

* The cloning function aims to speed up adding indicators similar to each other.
* By clicking on the *Clone indicator* button, an indicator **edit form** is displayed and input fields are **prefilled with the cloned indicator's data** except its identifier and name.
* The form has the same features as written above.


**Editing an indicator**

* By clicking on the *Edit indicator* button, an indicator **edit form** is displayed input fields are prefilled with the indicator's data.
* The form has the same features as written above, except the following:
* The **identifier cannot be modified** for an existing indicator.


**Deleting an indicator**

* After clicking on the *Delete indicator* button, a **confirmation page** will show with 2 options.
* **Yes** button will cause the indicator to be deleted permanently from the database. Related indicators or tags will remain untouched.
* **No** button will redirect to the indicator's details page.



## Managing tags

Tags can be attached to (or be detached from) indicators using the *Add/Edit indicator* features described above. Tags themselves can also be **renamed** or **deleted**. In order to do one of these, one should start at the **tags list page**.


**Renaming a tag / merging tags**

* By clicking on the *Rename tag* button, a **dialog** will appear.
* The dialog shows the old name, and provides an input field for setting the new name.
* By clicking on the *Save* button, the tag will be **renamed.** *Cancel* button will hide the dialog.
* **Merging** takes place when the specified new name **already exists** in the database as another tag. In this case, indicators attached to the current tag will be **moved** to the existing tag, and the current tag will be simply deleted.


**Deleting a tag**

* By clicking on the *Delete tag* button, a **confirmation page** will show with 2 options.
* **Yes** button will cause the tag to be deleted permanently from the database. All indicators will be detached first, so no indicator will be deleted.
* **No** button will redirect to the tags list page.


**Deleting unused tags**

* This feature is the extension of tag deletion: it **deletes all tags that have no indicators** attached to them.
* By clicking on the *Delete unused tags* button, a **confirmation page** will show listing all the unused tags and providing 2 options.
* **Yes** button will cause all unused tag to be deleted permanently from the database.
* **No** button will redirect to the tags list page.