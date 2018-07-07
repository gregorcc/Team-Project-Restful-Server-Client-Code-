# Overview Feature For TornadoFX

In this program, the function is split to View and Controller. Maybe model have its independent class. 
In most of case model was private inside View. Most of page was design in MVC pattern and placed inside describle package name. 

All textfield is governed by bind properties. In this case, we can easily access the data inside the property. Meanwhile, model checker will ask validator to check illegal input. 

# Data Structures
Most of the property in tornadoFX is delegate by property.

## Person
Person is the parent class for Admin and User. It takes five parameter, firstname, lastname, username, birthday, password.

## SurveyChoices
Survey Choices contains a question(string), a list of choices(list of string or null). 

### Here is a overview for the program function

![UserPathDiagram](https://github.uiowa.edu/herman/CS2820A01-Team1/blob/master/Combined/TornadoFX/Document/UserPathDiagram.jpg)

# User

## Login
LoginView manage all the UI component, and LoginController manage all the logic like check password, check whether username is correct email address.

#### Background

In login view, there is a button can let user to choose the background as they want. I need make text readable for different background.
So I had color picker to find most common color inside the picture. Then I didn't some color transformation and effect. Now, text color 
will change with the background. 

## New User
New User help create new user. It will ask for firstname lastname username(email) birthday password
It will check correctness of email and password, and Weak password. Once everything finished. New User will auto login.

#### Weak Password Finder
I had a list of common bad password dictionary and when user create a new account. 
It will auto check is the password inside the dictionary. 
I used index finder for efficiency.

## Weclome Page
It has buttons help people logout, change profile, choose survey, take survey.

## Survey Page
This is the page for taking survey.
Each times, it will display only one question, "free response" or "multiple chocies" or "true or false question".
After each question finished, app will record response, load new question or chocies dependent on type, and check whether there exist
old response. If it exist, then it will load that response.

## Finish Page
No thing important here. A button go back to welcome page.

# Anonymous 
Every thing is same with User, except it will not contain profiles. 
I create many interface for Anonymous, and hope when User Pages updates, that won't mess up Anonymous Page. 
Right now it is not perfectly split, I need more work on it. 
And We will record IP address and motherboard number for their identity. 

# Admin
Login will be the same compare to User. 

## Admin Welcome Page
This welcome page cotains, create survey, import survey question, export survey response, view user data.
Export survey response don't have a view. Select survey on combobox then choose the place to export. 

## Create Survey
class @CreateSurveyView allow admin to create a new survey.
The design in this CreateSurvey is simple.
Basically, Left side, there is a table of question and choice List bind by @CreateSurveyModel
Admin need to choose one of type of free response question, true or false question, multiple choices question
and add to new question. Different type of question correlate to different subview. Once type was selected
The new view will be inject to this View and then Admin can add more details about question and choices.

This use different strategy or approach compare to User's take survey. In User's surveyView, I want hide details
as few as possible, so nothing can affect the correctness of the survey. In Admin's createSurvey, I want add more
details, so admin can have a better bigger picture of what was going on.

## Import Survey
class @ImportSurveyView is the UI for people want to import their Survey Question by XLSX, XLS, CSV, those common
files, I have an instruction of how Import works, and there is table view to help people visualize what had been
Imported. If survey has a title, and question list is not empty, it will send questions to server.

## View User Data
class @ExportUserDataView is for export User Data, Admin can select what partial or full data to export. 
It is also editable able so admin can edit before export. 
Indeed, we don't want admin to update edited data to server. It isn't a good idea.
Export button is hidden in right click.

