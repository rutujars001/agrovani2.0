Starting AgroVani from scratch
The sequence
1. MySQL — usually already running as a Windows service. Check:

text
sc query MySQL80
If not running, open Command Prompt as Administrator:

text
net start MySQL80
2. Backend — its own window:

text
cd /d D:\AgroVani\backend\agrovani-backend
mvnw.cmd spring-boot:run
Wait for Started AgrovaniBackendApplication.

3. AI service — its own window:

text
conda activate agrovani-ai
cd /d D:\AgroVani\ai-service
uvicorn main:app --reload --port 8000
Wait for Application startup complete.

4. Metro — its own window:

text
cd /d D:\AgroVani\mobile\AgroVaniApp
npx @react-native-community/cli start
5. Phone — plug in, then in a fourth window:

text
adb devices
adb reverse tcp:8080 tcp:8080
adb reverse tcp:8000 tcp:8000
6. Launch the app — same window:

text
npx @react-native-community/cli run-android
After the first install you can skip step 6 — just open AgroVani from the app drawer, as long as Metro is running.

Automate it
Create D:\AgroVani\start-all.bat:

text
type nul > D:\AgroVani\start-all.bat
notepad D:\AgroVani\start-all.bat
Paste:

text
@echo off
echo Starting AgroVani development environment...

echo [1/4] Backend...
start "AgroVani Backend" cmd /k "cd /d D:\AgroVani\backend\agrovani-backend && mvnw.cmd spring-boot:run"

timeout /t 3 /nobreak > nul

echo [2/4] AI service...
start "AgroVani AI" cmd /k "cd /d D:\AgroVani\ai-service && call conda activate agrovani-ai && uvicorn main:app --reload --port 8000"

timeout /t 3 /nobreak > nul

echo [3/4] Metro...
start "AgroVani Metro" cmd /k "cd /d D:\AgroVani\mobile\AgroVaniApp && npx @react-native-community/cli start"

timeout /t 3 /nobreak > nul

echo [4/4] ADB tunnels...
adb devices
adb reverse tcp:8080 tcp:8080
adb reverse tcp:8000 tcp:8000
adb reverse --list

echo.
echo Done. Three windows opened. Give the backend ~30 seconds.
pause
Save, then run it any time by double-clicking, or:

text
D:\AgroVani\start-all.bat
Each service gets its own titled window, so you can tell them apart. cmd /k keeps them open so you can read the logs.

The call before conda activate matters — without it the batch file exits at that line instead of continuing.

Reconnect script
The tunnels drop every time you unplug the phone. Create D:\AgroVani\reconnect.bat:

text
@echo off
adb devices
adb reverse tcp:8080 tcp:8080
adb reverse tcp:8000 tcp:8000
adb reverse --list
pause
Run that whenever the app shows DOWN after replugging.

Shutdown
Close the three service windows, or Ctrl+C in each. Leave MySQL running — it's a Windows service and costs you nothing.

Add both scripts to Git once they work:

text
cd /d D:\AgroVani
git add start-all.bat reconnect.bat
git commit -m "Add development startup scripts"
Test start-all.bat and tell me if all three windows come up cleanly — then we continue with Step 12.3, the folder structure.