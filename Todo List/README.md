# Pre-work - *Todo List*

**Todo List** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Lin Wang**

Time spent: **20** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [ ] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [ ] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] List anything else that you can get done to improve the app functionality!
Added Camera to allow user to take photos for todo list

## Video Walkthrough

Here's a walkthrough of implemented user stories:
http://imgur.com/a/PvmwV
<img src='http://i.imgur.com/a/PvmwV' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."


**Answer:** [I love the debugging and the work flow of android development platform.].

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."


**Answer:** [Adapter helps connect the frontend and the backend as ArrayAdapter create a view per element for a list of elements. Adapter is important because without adapter, the frontend cannot communicate with backend information. convertView is a way to recycling old View objects that are no longer needed. ArrayAdapter populate each list item with a View object by calling getView() on each row].

## Notes

Describe any challenges encountered while building the app.
The biggest challenge and limitation of my app is that I haven’t be able to solve the update of the list in main activity once it’s drafted by the user, the row of task cannot be open and revise the note, otherwise it will be crashed. However, if you open an existing task without taking a picture in the first place. It allows you to take the picture, but it also generates another row of task.
## License

    Copyright [Lin Wang] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
