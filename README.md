# How to Run the Pizza Application

## Prerequisites
- **Java 17+** (You have this)
- **Maven** (You need to install this, or use the VS Code extension)

## Option 1: Using VS Code Maven Extension (Recommended)
1.  Look at the **Explorer Sidebar** on the left.
2.  At the bottom, expand the **Maven** section.
    - *If you don't see it, right-click the empty space in the sidebar and check "Maven".*
3.  Expand `pizza-project` -> `Lifecycle`.
4.  Right-click on `spring-boot:run` and select **Run**.

## Option 2: Using the Terminal
Since you don't have `mvn` installed yet, you need to install it first.

1.  **Install Maven**:
    Run this command in PowerShell:
    ```powershell
    winget install Maven.Maven
    ```
2.  **Restart Terminal**:
    Close your current terminal and open a new one to refresh your PATH.
3.  **Run the App**:
    ```powershell
    mvn spring-boot:run
    ```

## Access the App
Once started, open: [http://localhost:8080](http://localhost:8080)
