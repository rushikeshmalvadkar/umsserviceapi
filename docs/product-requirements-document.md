# URL Management System

## Actors

* Customer
* Admin
* System

## Create Short URL

* Create short URL form should have below fields:
    * Title (Mandatory)
    * Original Url (Mandatory)
    * slug (Optional)
* If user does not give slug then system will generate slug else not

### Constraints

* If user does not give mandatory fields then show message just below that field:
    * "This field is required"
* If user give invalid original url then show message just below that field:
    * "Please enter a valid URL (e.g. https://example.com)"
* User should not be able to enter more than 2048 characters for original link and original link input box should show
  how many character used and how many remaining (e.g 2/2048)
* User should not be able to enter more than 128 characters for title and title input box should show
  how many character used and how many remaining (e.g 4/128)

## My Short URLs

* This will be short url list page
* This will contain below columns in table format:
    * Short Url
        * e.g "https://ourdomain.com/slug..."
        * Will show only first 20 characters and full short url will be shown on tooltip on hover
    * Title
        * e.g: "My Short URL Title..."
        * Will show only first 20 characters and full title will be shown on tooltip on hover
    * Original Url
        * e.g: "https://original-url.com..."
        * Will show only first 20 characters and full original url will be shown on tooltip on hover
    * Slug
        * e.g "DbExploration"
        * Will show only first 20 characters and full slug will be shown on tooltip on hover
    * Status
        * e.g (Active , Inactive)
        * Active should show with white text and green background
        * Inactive should show with white text and light gray background
    * Created On
        * e.g "5 Jan 2026 11:33 AM"
    * Actions
        * copy icon with tooltip "Copy Short Url"
            * When user click on that copy icon then toaster should show message:
                * "Short Url copied successfully!"

## Update URLs

* Below url list table's columns should be inline editable with given role permission
    * Title
        * Admin
        * Customer
    * Original Url
        * Admin
        * Customer
    * Slug
        * Admin
        * Customer
    * Status
        * Admin
        * Customer