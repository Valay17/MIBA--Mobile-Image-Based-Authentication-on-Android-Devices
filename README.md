# Mobile Image-Based Authentication on Android Devices

This project implements a **Mobile Image-Based Authentication (MIBA)** system for Android devices, which allows users to authenticate by selecting multiple points on an image using multitouch. It is designed to offer better usability compared to traditional text passwords, especially for users with disabilities like dyslexia, while still maintaining strong security.

## Abstract

Graphical password schemes can provide better usability than text passwords, especially on smartphones where typing complex passwords on a virtual keyboard can be tedious. However, to achieve password strength comparable to text passwords, graphical password schemes require multiple rounds and, therefore, have longer entry times. We propose **MIBA** as an image-based authentication method that leverages **multitouch** in order to increase the password space by supporting multiple fingers for click point selection. We outline the **MIBA** concept, report on practical constraints for multitouch click point selection, and discuss preliminary results that indicate short entry times and the usability of **MIBA**.

## Problem Statement

Text input as passwords can be difficult for **differently-abled people** (e.g., those with **Dyslexia**), who may face challenges with typing on a virtual keyboard. This project addresses this issue by offering **visual aids** that allow for more intuitive interaction, while still ensuring **strong protection** for authentication.

## Introduction

We propose a **multitouch image-based authentication method (MIBA)** that allows simultaneous use of multiple fingers for entering graphical passwords. We show that **MIBA** requires shorter and quicker-to-type passwords to achieve the same entropy and security as other graphical password schemes or PIN entry.

## Requirement Analysis

### Functional Requirements
- The system should first obtain the pattern entered by the user and store it permanently on a non-volatile disk.
- The system will then obtain the visual passcode and perform prediction using the following rules:
  - **IF** the pattern matches the pre-entered pattern, the system grants access to the user.
  - **ELSE**, the system denies access and displays a message (e.g., "Invalid input").
- Users should be able to create a new password after answering a predefined question.

### Non-Functional Requirements
- The system must be responsive and provide feedback to the user in a timely manner (e.g., access granted/denied).
- The application should provide a user-friendly interface for entering and verifying the pattern.

### Hardware Requirements
- A multitouch-enabled device to support the use of multiple fingers for entering the graphical password.

### Software Requirements
- The app should be compatible with devices running **Android 5.0 and above**.

## Hardware & Software Details

**Target Platform:**
- Android with **API level 8** (Android 2.2.x).

**Hardware Requirements:**
- **Capacitive touchscreen** with **multitouch** support (up to **4-6 fingers**).
- The system can run on any **Android smartphone** with Android version **5.0 or higher**.
- Devices with a **4.65-inch Super AMOLED screen** and a resolution of **1280 × 720 pixels** are ideal for optimal display and interaction.

**Software Requirements:**
- The app is designed to run on Android devices and will work with devices running Android **5.0 and above**.

## Functional Design

1. **Multiple Rounds and Click Points:**
   - A **MIBA password** can consist of multiple rounds, with each round allowing the user to mark multiple points on an image.
   - **Click points** allow for quick entry (even with multiple fingers simultaneously), whereas drawing complex patterns takes more time.
   - MIBA uses **background images** as cues, and the image for the next round is determined based on the user’s input in the current round.

2. **Back Button and Unique Images:**
   - A **back button** is provided for error correction.
   - Each image should only appear once in the password sequence to prevent memory interference from repeating images.

3. **Grid of Click Points:**
   - In MIBA, the background image is overlaid with a **half-transparent grid** of potential click points to help guide finger placement.
   - Once a user places a finger on a point, that point becomes fully transparent.
   - While the grid of click points may be visible to onlookers (potentially aiding shoulder surfers), the **occlusion** caused by the user’s hand during password entry likely mitigates this issue.

4. **Shift Function for Increased Entropy:**
   - A **shift function** is introduced to extend the theoretical password space.
   - The shift function is activated when the user presses for a slightly longer duration, and it adds entropy to a round without being easily distinguishable from a normal round.
   - In MIBA, a **multi-finger click** leads to the next round, while a **longer press** activates a shift round.
   - Feedback is provided via **vibration** to confirm the activation of the shift function.

## Installation

### Prerequisites
- **Minimum SDK Version**: 11 (Android 3.0, Honeycomb)
- **Target SDK Version**: 15 (Android 4.0, Ice Cream Sandwich)

### Clone the repository
```bash
git clone https://github.com/Valay17/MIBA--Mobile-Image-Based-Authentication-on-Android-Devices.git
```

### Open the project in Android Studio
1. Launch Android Studio.
2. Open the cloned project folder (`mobile-image-based-authentication`).
3. Sync the Gradle files by clicking "Sync Now."

### Run the app
1. Connect an Android device or start an emulator.
2. Click the "Run" button in Android Studio to install and launch the app.

## Video Demo
Here’s a video demo showing how the app works, showcasing all the features of the image-based authentication system and its multitouch functionality.

MIBA:
1. **Opening the App**
   - App Icon and Home Screen Preview.

2. **Selecting MIBA**
   - User selects the **Open MIBA** option.

3. **Option for Round Selection**
   - Display the available options for **Round Selection**.
   - Select **3 rounds** for the password.

4. **Creating Password**
   - Walkthrough of creating a password with **3 rounds**.

5. **Password Memorization**
   - Shows the process of the user memorizing their password.

6. **Password Entry**
   - Shows how the user enters the password using multiple points on the image.

7. **Getting Incorrect Shows Wrong Image**
   - When the user inputs the wrong password, the wrong image is shown.

<video src="https://github.com/user-attachments/assets/22102956-3d63-407e-8c90-d23898b979ae"></video>


Pass-Go:
1. **Selecting Pass Go**
   - User selects the **Open Pass-Go** option.

2. **Option for Round Selection**
   - Display the available options for **Round Selection**.
   - Select **7 lines** for the password.

3. **Creating Password**
   - Walkthrough of creating a password with **7 lines**.

4. **Password Memorization**
   - Shows the process of the user memorizing their password.

5. **Password Entry**
   - Shows how the user enters the password by drawing **7 lines**.

6. **Show Retry Feature**
   - Demonstrate the **retry feature** if the user remembers they entered the password incorrectly.

7. **Dots and Lines Functionality**
   - User can create a password by drawing lines or taping on the intersecting lines to select the dots to form a sequence.

<video src="https://github.com/user-attachments/assets/a0ba5b6d-b086-4c65-808b-8d757ca33710"></video>

## License
This project is **not licensed**. No rights are granted to use, modify, distribute, or otherwise use the code in this repository unless explicitly stated otherwise. By using or accessing the repository, you acknowledge that you are not being granted any rights or licenses to the content or code.
