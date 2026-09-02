## Set Url as Favorite


### Problem

* User wants to make url favorite single or multiple

### Solution

* We will show  check box for  url list for make user url favorite 
* We will show  the button make favorite 
* After check the checkbox click the make favorite button
### Backend changes

 #### Create Make Url Favorite API
* create new patch endpoint /api/ums/urls/make-favorite
* Request Headers

```shell
userid : 1
roleid : 3
device : web
```
* Request Payload

```json
{
  "urlIds" : [1,2,3,4,5,6,7]
}
```
* Response Payload

```json
{
  "data": null,
  "message": "Updated successfully",
  "code": 200,
  "status": "SUCCESS"
}
```
* add variable in url entity isFavorite with type boolean
* change fetch urls response add the variable isFavorite for

### DB changes

* Add new column is_favorite in urls table with bit type
* Add new header config in header config table with below info:
    * Header Name = 
    * Type = checkbox
    * Mapping table = 
    * Mapping column = 
    * Mapping name = 
    * sortable = false
    * filterable = false

   * Header Name = 
   * Type = favorite
   * Mapping table = 
   * Mapping column = 
   * Mapping name = isFavorite
   * sortable = false
   * filterable = false 
* Add header mappings for Customer and Admin role,  will be editable show the check box for make url favorite

### UI Changes
  * Add a checkbox column to allow users to select one or multiple URLs.
  * Add a Make Favorite button.
  * Enable the button when at least one URL is selected.
  * On clicking Make Favorite, call the new API with the selected URL IDs.
  * Add a Favorite column/icon using the isFavorite value returned by the backend.
  * For URLs that are already favorites, show the favorite state accordingly.
  * The checkbox column should be available only where the user's role has permission to edit favorites.