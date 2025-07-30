444COMP Project - The Website for an Imported Sweets Shop
UI/UX: Figma
Frontend: HTML, CSS, JS
Backend: Java, Spring Boot
Database: SQL, unfortunately 

## System Requirements
- Java 21 (Any other JDK version will not be compatible with the spring boot framework utilized)
- MySQL 8.0+

pls npm install before running on your live server

## Run instructions

## Option 1:
Run the following command in the project root directory (444):
```bash
npm start 
# this command will run the live server and start the backend server via the script defined in package.json
```

## Option 2:
1. Open the project in VSCode IDE and navigate to the sweetShopApp.java file located in the backend directory
2. Run the backend using the "run java" option
    - It may ask you to continue if the build fails, click yes
3. Open liver server utilizing the VSCode extension or run the following command in the terminal:
```bash
npx live-server --port=5500 --host=127.0.0.1
```

## Option 3:
Run the following commands in the project backend directory (java\com\sweetshopdb\backend):
```bash
# mvn dependencies are included in the project and can be run using the included wrapper script
./mvnw spring-boot:run

# Otherwise if you have Maven installed on your system, run:
mvn spring-boot:run

# For users on windows using VSCode IDE
launch Live Server directly from VSCode

#Otherwise, run the following command:
npx live-server --port=5500 --host=127.0.0.1
```

## App URLs
- Frontend: http://localhost:5500 (127.0.0.1:5500)
- Backend: http://localhost:8080
- Database: http://localhost:3306 (MySQL Workbench)

##TroubleShooting
- JAVA_HOME environment variable must be set to the JDK 21 installation path
- Path to the JDK 21 bin directory must be added to the system PATH variable
- When utilizing run instruction 2 be sure to be in the backend directory and not the root directory since the mvnw project is located in the backend directory.
- Reload the window in VSCode if you encounter any issues in between running the project to clear any compilation errors.

