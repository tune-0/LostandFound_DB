SETUP INSTRUCTIONS!

IMPORT DATABASE
   - Open phpMyAdmin: http://localhost/phpmyadmin
   - Click "New" to create database
   - Database name: LostandFound_DB
   - Click "Import" tab
   - Choose file: LostandFound_DB.sql
   - Click "Go"

 OPEN PROJECT IN INTELLIJ
   - File → Open → Select project folder
   - Wait for IntelliJ to load

 ADD LIBRARIES
   - File → Project Structure → Libraries
   - Add mysql-connector-j-x.x.x.jar
   - Add jcalendar-1.4.jar

 UPDATE DATABASE CONNECTION (if needed)
   - Open DatabaseConnection.java
   - Check URL, USER, PASSWORD match your MySQL setup
   - Default: URL=localhost:3306, USER=root, PASSWORD=(empty)

 BUILD AND RUN
   - Build → Rebuild Project
   - Run LostAndFoundGUI.java
