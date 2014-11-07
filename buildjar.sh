# The reason for this build file is because the free version of IntelliJ Idea does not support distribution, such as
# building Jar files, so this build file does that. This file must be run in project home folder, which is the same
# folder this file rests in.

echo ''
echo 'CURRENT DIRECTORY:'
pwd
echo ''
echo 'Building Jar...'
rm ./dist/MathQuizGame.jar
cd ./out/Production/MathQuizGame
jar -cvfe MathQuizGame.jar mathquizgame.MathQuizGame ./mathquizgame ./res
mv MathQuizGame.jar ../../../dist
echo ''
echo 'Done'
echo ''