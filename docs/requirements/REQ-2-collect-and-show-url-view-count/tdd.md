## Collect and show url view count

### Problem

* User wants to see how many views happen on his/her created urls

### Solution

* We will show views column on My Urls list

### DB changes

* Add new column in urls table of bigint type
* Add new header config in header config table with below info:
  * Header Name = Views
  * Type = number
  * Mapping table = urls
  * Mapping column = view_count
  * Mapping name = viewCount
* Add header mappings for Customer and Admin role, views column will be non-editable

