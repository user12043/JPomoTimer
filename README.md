[![Build Status](https://travis-ci.org/user12043/JPomoTimer.svg?branch=master)](https://travis-ci.org/user12043/JPomoTimer)

# JPomoTimer

![Icon](https://raw.githubusercontent.com/user12043/JPomoTimer/master/src/main/resources/res/icon-pomodoro-dark.png)

<sub><sub><sub><sub>Icon made by [Freepik](http://www.freepik.com) from [www.flaticon.com](www.flaticon.com) is licensed by [CC 3.0 BY](http://creativecommons.org/licenses/by/3.0/) </sub></sub></sub></sub>

Simple pomodoro timer application with Java running on system tray.

#### Features
- Displays timer anywhere on the screen (can be hidden)
- Displays warning message and plays sound on time up
- Can pause/resume the timer
- Continuous mode. Auto start timer from proprietary value after work and break
- Customizable minutes for work, break and long break
- Customizable timer colors
<hr>

### Build and Run
type `mvn clean package` to build the project
Run the jar file created in `target/` folder.

> If you want to customize icon and alert sound, copy the `src/main/resources/res/` folder to the application directory and change the files.
