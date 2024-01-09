# TextEditor Java Swing Application

This is a simple text editor implemented in Java using Swing. The application provides basic text editing capabilities along with file management options. Users can create new files, open existing ones, save their work, and perform common editing tasks like undo, redo, copy, and select all.

## Features

- **File Management:**
  - **New:** Create a new file, clearing the current text.
  - **Open:** Open an existing text file and display its content in the editor.
  - **Save:** Save the current content to a file. If it's a new file, the user is prompted for a filename.
  - **Exit:** Close the application.

- **Editing Options:**
  - **Undo:** Revert the last editing action.
  - **Redo:** Reapply the last undone action.
  - **Copy:** Copy the selected text to the clipboard.
  - **Select All:** Select all text in the editor.

- **Shortcut Keys:**
  - New: Ctrl + N
  - Open: Ctrl + O
  - Save: Ctrl + S
  - Exit: Ctrl + Q
  - Undo: Ctrl + Z
  - Redo: Ctrl + Y
  - Copy: Ctrl + C
  - Select All: Ctrl + A

## How to Run

To run the TextEditor application, follow these steps:

1. Ensure you have Java installed on your machine.
2. Compile the `TextEditor.java` file.
   ```bash
   javac TextEditor.java
3. Run the compiled class file.
   ```bash
   java TextEditor

## Help Section
The application includes a Help menu with additional information about the editor and its features. Users can find details about each menu option and corresponding keyboard shortcuts.
