SETUP INSTRUCTIONS!

1. IMPORT DATABASE
   - Open phpMyAdmin: http://localhost/phpmyadmin
   - Click "New" to create database
   - Database name: LostandFound_DB
   - Click "Import" tab
   - Choose file: LostandFound_DB.sql
   - Click "Go"

2. OPEN PROJECT IN INTELLIJ
   - File → Open → Select project folder
   - Wait for IntelliJ to load

3. ADD LIBRARIES
   - File → Project Structure → Libraries
   - Add mysql-connector-j-x.x.x.jar - Download inside the .idea / libraries
   - Add jcalendar-1.4.jar - Download inside the .idea / libraries

4. UPDATE DATABASE CONNECTION (if needed)
   - Open DatabaseConnection.java
   - Check URL, USER, PASSWORD match your MySQL setup
   - Default: URL=localhost:3306, USER=root, PASSWORD=(empty)

5. BUILD AND RUN
   - Build → Rebuild Project
   - Run LostAndFoundGUI.java
