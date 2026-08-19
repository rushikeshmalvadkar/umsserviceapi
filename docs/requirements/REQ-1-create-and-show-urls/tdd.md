## Create and show URLs

### Functional Requirement

* We need to create form to create short url and
  need to create My Short URLs menu which will show
  logged-in user's short URLs as per the PRD

### DB changes

* Create roles table with below columns:
    * id : bigint (PK, AI)
    * name : varchar(20) (NN)
    * description : varchar(255) (NN)
* Insert below roles:
    * System : Responsible to automate things
    * Admin : Responsible for everything with all permissions
    * Customer : Responsible to manage URLs with only required permissions
* Create menu table with below columns:
    * id : bigint (PK, AI)
    * name : varchar(45) (NN)
    * parent_id : bigint (FK -> menu)
* Insert below menus:
    * Create Url
    * My Short URLs
* Create users table with below columns:
    * id : bigint (PK, AI)
    * name : varchar(45) (NN)
    * email : varchar(45) (NN, UQ)
    * role_id : bigint (FK -> role, NN)
    * delete_flag : bit(1) (default -> 0, NN)
    * created_by : bigint (FK -> users)
    * created_date : datetime (default -> UTC(), NN)
    * last_updated_by : bigint (FK -> users)
    * last_updated_date : datetime
* Create role_menu table with below columns:
    * id : bigint (PK, AI)
    * role_id : bigint (FK -> role, NN)
    * menu_id : bigint (FK -> menu, NN)
    * delete_flag : bit(1) (default -> 0, NN)
    * created_by : bigint (FK -> users)
    * created_date : datetime (default -> UTC(), NN)
    * last_updated_by : bigint (FK -> users)
    * last_updated_date : datetime
* Insert required below entries in role_menu table
    * Customer role has access of both menus
    * Admin role has access of both menus
* Create option_source table with below columns:
    * id : bigint (PK, AI)
    * mappingName : varchar(50) (NN)
* Create required entry in option_source table for urlStatusList optionSource
* Create header_config table with below columns:
    * id : bigint (PK, AI)
    * header_name : varchar(100) (NN)
    * header_type : varchar(100) (NN)
    * mapping_name : varchar(100) (NN)
    * mapping_table : varchar(100)
    * mapping_column : varchar(100)
    * sortable : bit(1) (default -> 0, NN)
    * filterable : bit(1) (default -> 0, NN)
    * option_source_id : bigint (FK -> option_source)
    * delete_flag : bit(1) (default -> 0, NN)
    * created_by : bigint (FK -> users)
    * created_date : datetime (default -> UTC(), NN)
    * last_updated_by : bigint (FK -> users)
    * last_updated_date : datetime
* Insert required entries in header_config table based on below /fetch-urls endpoint's response JSON
* Create header_mapping table with below columns:
    * id : bigint (PK, AI)
    * header_config_id : bigint (FK → header_config, NN)
    * role_menu_id : bigint (FK -> role_menu, NN)
    * editable : bit(1) (default -> 0, NN)
    * display_order : decimal(15,2) (NN)
    * delete_flag : bit(1) (default -> 0, NN)
    * created_by : bigint (FK -> users)
    * created_date : datetime (default -> UTC(), NN)
    * last_updated_by : bigint (FK -> users)
    * last_updated_date : datetime
* Insert required entries in header_mapping table based on below things
    * Using role_menu_id for Customer and Admin from role_menu table
    * Using header_config_id from header_config table based on /create-url-on-load(For Create Url Menu) and based on /fetch-urls(For My Short Urls) menu response headers
* Create url_status table with below columns:
    * id : bigint (PK, AI)
    * name : varchar(50) (NN)
    * description : varchar(255) (NN)
    * delete_flag : bit(1) (default -> 0, NN)
    * created_by : bigint (FK -> users, NN)
    * created_date : datetime (default -> UTC(), NN)
    * last_updated_by : bigint (FK -> users)
    * last_updated_date : datetime
* Insert below url statues
    * Active : "Represents active urls"
    * Inactive : "Represents inactive urls"
* Create urls table with below columns:
    * id : bigint (PK, AI)
    * title : varchar(128) (NN)
    * original_url : varchar(2048) (NN)
    * slug : varchar(50) (NN)
    * url_status_id : bigint (FK -> url_status, NN)
    * delete_flag : bit(1) (default -> 0, NN)
    * created_by : bigint (FK -> users, NN)
    * created_date : datetime (default -> UTC(), NN)
    * last_updated_by : bigint (FK -> users)
    * last_updated_date : datetime
* Notes
    * On insert, it should save UTC time not CURRENT_TIMESTAMP as default

### Backend changes

#### Temporary Sign in API

* create new GET endpoint /api/auth/sign-in
* Response Payload

```json
{
  "data": {
    "metadata": {
      "urlStatusList": [
        {
          "key": 1,
          "value": "Active"
        },
        {
          "key": 2,
          "value": "Inactive"
        }
      ]
    }
  },
  "message": "Signed in successfully",
  "code": 200,
  "status": "SUCCESS"
}
```

#### Create Url On Load API

* create new GET endpoint /api/ums/urls/create-url-on-load
* Request Headers

```shell
userid : 1
roleid : 3
device : web
```

* Response Payload

```json
{
  "data": {
    "headers" : [
      {
        "displayName": "Title",
        "mappingName": "title",
        "headerType": "text",
        "headerMappingId": 2,
        "editable" : true,
        "filterable": true,
        "sortable": true,
        "optionSource" : null
      },
      {
        "displayName": "Original Url",
        "mappingName": "originalUrl",
        "headerType": "text",
        "headerMappingId": 3,
        "editable" : true,
        "filterable": true,
        "sortable": true,
        "optionSource" : null
      },
      {
        "displayName": "Slug",
        "mappingName": "slug",
        "headerType": "text",
        "headerMappingId": 4,
        "editable" : true,
        "filterable": true,
        "sortable": true,
        "optionSource" : null
      }
    ]
  },
  "message": "Fetched successfully",
  "code": 200,
  "status": "SUCCESS"
}
```

* Headers should come in same order

#### Create Url API

* create new POST endpoint /api/ums/urls/create-url
* Note : ? in request JSON property indicates that property is optional
* Request Headers

```shell
userid : 1
roleid : 3
device : web
```
* Request Payload

```json
{
  "title" : "Database Exploration Checklist for Understanding a New Domain",
  "originalUrl" : "https://abhishekmalvadkar.netlify.app/database-exploration-checklist-for-understanding-a-new-domain/",
  "slug?" : "DbExploration"
}
```
* Response Payload

```json
{
  "data": {
    "id": 1
  },
  "message": "Created successfully",
  "code": 201,
  "status": "CREATED"
}
```

#### Check slug API

* create new GET endpoint /api/ums/urls/check-slug?slug=DbExploration
* Response Payload

```json
{
  "data": {
    "slugAlreadyExists": true
  },
  "message": "Checked successfully",
  "code": 200,
  "status": "SUCCESS",
  "success": true
}
```


#### Fetch Urls API

* create new POST endpoint /api/ums/urls/fetch-urls
* Request Headers

```shell
userid : 1
roleid : 3
device : web
```
* Request Payload

```json
{
  "urlStatusId?" : 1
}
```
* Response Payload

```json
{
  "data": {
    "headers" : [
      {
        "displayName": "Title",
        "mappingName": "title",
        "headerType": "text",
        "headerMappingId": 2,
        "editable" : true,
        "filterable": true,
        "sortable": true,
        "optionSource" : null
      },
      {
        "displayName": "Original Url",
        "mappingName": "originalUrl",
        "headerType": "text",
        "headerMappingId": 3,
        "editable" : true,
        "filterable": true,
        "sortable": true,
        "optionSource" : null
      },
      {
        "displayName": "Slug",
        "mappingName": "slug",
        "headerType": "text",
        "headerMappingId": 4,
        "editable" : true,
        "filterable": true,
        "sortable": true,
        "optionSource" : null
      },
      {
        "displayName": "Status",
        "mappingName": "urlStatusId",
        "headerType": "dropdown",
        "headerMappingId": 5,
        "editable" : true,
        "filterable": true,
        "sortable": true,
        "optionSource" : "urlStatusList"
      },
      {
        "displayName": "Created On",
        "mappingName": "createdDate",
        "headerType": "date",
        "headerMappingId": 6,
        "editable" : false,
        "filterable": false,
        "sortable": true,
        "optionSource" : null
      },
      {
        "displayName": "",
        "mappingName": "",
        "headerType": "copy",
        "headerMappingId": 7,
        "editable" : false,
        "filterable": false,
        "sortable": false,
        "optionSource" : null
      },
      {
        "displayName": "",
        "mappingName": "",
        "headerType": "visit",
        "headerMappingId": 8,
        "editable" : false,
        "filterable": false,
        "sortable": false,
        "optionSource" : null
      }
    ],
    "data" : [
      {
        "id": 1,
        "title" : "My Short URL Title",
        "originalUrl": "https://original-url.com",
        "slug" : "DbExploration",
        "urlStatusId" : 1,
        "createdDate" : "2026-01-05T11:33:00Z"
      }
    ]
  },
  "message": "Fetched successfully",
  "code": 200,
  "status": "SUCCESS"
}
```

* Headers should come in same order

#### Update Url API

* create new PATCH endpoint /api/ums/urls/update-url
* Request Headers

```shell
userid : 1
roleid : 3
device : web
```
* Request Payload

```json
{
  "headerMappingId" : 5,
  "value": "2",
  "recordId": 1
}
```
* Response Payload

```json
{
  "data": {
    "id": 1
  },
  "message": "Updated successfully",
  "code": 200,
  "status": "SUCCESS"
}
```

#### Visit Short Url

* e.g : "https://frontend-url/slug"
* create new GET endpoint /api/ums/urls/visit-url/{slug}
* This is public API
* Http status code : 302 Found (Temporary Redirect)
* Do not use 301 (Moved Permanently) http status code because
  browser will cache response, and we will not able track click counts.
* Increase the view count when user visit the url
* Response Header

```shell
Location : "https://abhishekmalvadkar.netlify.app/database-exploration-checklist-for-understanding-a-new-domain/"
```