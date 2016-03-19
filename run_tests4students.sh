#!/bin/bash

#################################
#####  SETTING HELPER PATHS #####

DIR_NAME=$(dirname $0)

ROOT_DIR=$(cd ${DIR_NAME} && pwd -P)
echo "ROOT_DIR IS: ${ROOT_DIR}"

CALLEE_DIR=$(pwd)

# Setting path to testing source files
TESTS_SRC_DIR=${ROOT_DIR}/tests4students

# Setting path to students source files
# TODO: Use path from zip extraction from previous script

ZIP_FILE=$1

if [ -z $1 ]
then
    DFT_ZIP=$(ls *.zip|head -1)
    read -p "No zipfile supplied. Use default '${DFT_ZIP}'? [Y/n]: " useDefaultZip
    case $useDefaultZip in
	n) exit;;
	*) ZIP_FILE=$DFT_ZIP;;
    esac
fi

ZIP_DIR="${ROOT_DIR}/.${ZIP_FILE}.tmp"

# Clean existing tmp dir
rm -r $ZIP_DIR 2> /dev/null

echo "Unzipping..."

unzip -n -j -q $ZIP_FILE -d $ZIP_DIR || die "Could not read zipfile ${zipfile}"
# Deleting annoying files generated on OSX
cd ${ZIP_DIR}
rm ./._* -f 2> /dev/null
rm -r ./._* -f 2> /dev/null
cd ${ROOT_DIR}

STUDENT_SRC_DIR=${ZIP_DIR}


BUILD_DIR=${ROOT_DIR}/build

OUR_SRC="${ROOT_DIR}/src-override/Board.java ${ROOT_DIR}/src-override/GameTree.java ${ROOT_DIR}/src-override/MoveChannel.java ${ROOT_DIR}/src-override/Player.java"


################################
#####  CREATING BUILD PATH #####
rm -r ${BUILD_DIR} 2> /dev/null
mkdir ${BUILD_DIR}

# Setting dependencies path
DEPEND_DIR=${ROOT_DIR}/dependencies

#################################
#####  SETTING DEPENDENCIES #####

JUNIT="${DEPEND_DIR}/junit-4.12.jar"
HAMCREST="${DEPEND_DIR}/core-1.3.jar"
JAVA_RUN_TYPE="${DEPEND_DIR}/javaruntype-1.2.jar"
QUICK_CHECK_CORE="${DEPEND_DIR}/junit-quickcheck-core-0.6-beta-1.jar"
QUICK_CHECK_GENERATOR="${DEPEND_DIR}/junit-quickcheck-generators-0.6-beta-1.jar"
GENERICS="${DEPEND_DIR}/generics-resolver-2.0.1.jar"

SLF4J="${DEPEND_DIR}/slf4j-api-1.7.18.jar"
SLF4J_SIMPLE="${DEPEND_DIR}/slf4j-simple-1.7.18.jar"
ANTLR="${DEPEND_DIR}/antlr-4.5.2-complete.jar"
OGNL="${DEPEND_DIR}/ognl-3.1.2.jar"

##############################
#####  SETTING CLASSPATH #####

CLASSPATH=${JUNIT}:${HAMCREST}:${QUICK_CHECK_CORE}:${QUICK_CHECK_GENERATOR}:${JAVA_RUN_TYPE}:${GENERICS}:${SLF4J}:${SLF4J_SIMPLE}:${ANTLR}:${OGNL}:${BUILD_DIR}

##############################
#####  SETTING CLASSPATH #####

## START file sanitation
# Replace the interface files with our own to make sure students did not modify them.
cp $OUR_SRC $STUDENT_SRC_DIR
## END file sanitation

# Finding test sources and creating a new source.txt list
find ${TESTS_SRC_DIR} -name "*.java" > ${BUILD_DIR}/source.txt
find ${STUDENT_SRC_DIR} -name "*.java" >> ${BUILD_DIR}/source.txt

################################
#####  COMPILING CLASSPATH #####




# We build tests and student's solution in the same context so test design is easier.
javac -cp ${CLASSPATH} -Xlint:all -d ${BUILD_DIR} @${BUILD_DIR}/source.txt 

##########################
#####  RUNNING TESTS #####

echo "Hold tight. Running tests..."
java -cp ${CLASSPATH} org.junit.runner.JUnitCore TestsTTTClean TestsDomineeringClean #TestsTTT TestsDomineering
# -Xmx8g -Xss512m 

rm -r ${ZIP_DIR}
