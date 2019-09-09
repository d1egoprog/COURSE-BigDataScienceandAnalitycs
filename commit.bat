@echo off
git pull
git add *.*
git rm --cached commit.bat
git remote add coursedba https://github.com/d1egoprog/COURSE-BigDataScienceandAnalitycs.git
git commit -m "Updating %DATE% on %TIME%"
pause
git push -u coursedba master
