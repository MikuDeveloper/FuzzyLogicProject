# Robot bank
## Fuzzy Logic Application
### How install jFuzzyLogic library for this project.
***
*NOTE*: You need have installed Maven CGI (apache-maven).
If you don't have it, you can get it in: [Apache Maven - Download Website](https://maven.apache.org/download.cgi?.).
1. Navigate to [jFuzzyLogic Website](https://jfuzzylogic.sourceforge.net/html/index.html) and download the library.
2. Use the following command in terminal:
    ```
   mvn install:install-file -Dfile=<Replace-with-the-library-path> -DgroupId=jfuzzylogic -DartifactId=jfuzzylogic -Dversion=1 -Dpackaging=jar
   ```
3. Synchronize the file [pom.xml](pom.xml) using in terminal:
    ```
   mvn clean compile
   ```
4. Verify that the errors were gone.

### Libraries
***
- JavaFX 21
- jFuzzyLogic

### Sources
***
- [OpenJavaFX - Website](https://openjfx.io/)
- [jFuzzyLogic - Website](https://jfuzzylogic.sourceforge.net/html/index.html)
- [jFuzzyLogic - GitHub](https://github.com/pcingola/jFuzzyLogic)
- [Apache Maven - Download Website](https://maven.apache.org/download.cgi?.)

### Icons
***
By: [Flaticon](https://www.flaticon.com/)
