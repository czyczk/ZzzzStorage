# ZzzzStorage
ZZZZ Storage for JSP Courses

Descriptions of Folders:

src              -> src

WebContent       -> Dynamic webpages, images, css, scripts and library files.

The root directory of the database:
- Eclipse:

    [WorkspacePath]\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\[ProjectName]\WEB-INF\classes\database

- IDEA:

    [ProjectPath]\out\artifacts\[ModuleName]_war_exploded\WEB-INF\classes\database

where the Derby database is in “zzzz_media_vault” and the files are in the folder “files”.

For testing purpose, if incorrect user info has been submitted or sample files have been used up, deleting the two folders mentioned above will clear the database. A new database will be created on the next run.
